import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LinearProbeHashTable<T> implements HashTable<T> {
	
  public final static int NO_KEY = -1;	
	
  protected LinearProbeCell<T>[] table;
  protected int logSize;
  protected int mask;
  protected final int maxBucketSize;
  final ReentrantLock[] locks;
  
	@SuppressWarnings("unchecked")
	public LinearProbeHashTable(int logSize, int maxBucketSize, int numThreads) {
		this.logSize = logSize;
	    this.mask = (1 << logSize) - 1;
	    this.maxBucketSize = maxBucketSize;
	    this.table = new LinearProbeCell[1 << logSize];	
	    for (int i = 0; i < this.table.length ; i++ )
	    {
	    	this.table[i] = new LinearProbeCell<T>(NO_KEY, null, 0);
	    }
	    
	    locks = new ReentrantLock[numThreads];
		for (int i = 0 ; i < locks.length ; i ++){
			locks[i] = new ReentrantLock();
		}
	}


	@Override
	public void add(int key, T x) {
		while (!tryAdd(table,key, x))
		{
			resize(table.length);
		}
	}
	// Tries to add the key, return true is add was successful and false if resize is needed.
	public boolean tryAdd( LinearProbeCell<T>[] myTable, int key, T x) {
		boolean addFinished = false;
		boolean resizeNeeded = false;
		
		//Exit loop when add was successful or discovered that a resize is needed
		while (!addFinished && !resizeNeeded )
		{
		    int myMask,startIndex;
		    
		    myMask = myTable.length - 1;
		    startIndex =  key & myMask;
		    
		    // If the table is smaller then the size of locks , use only some of the locks.
			int startCellLockIndex = mask > (locks.length - 1) ? key & (locks.length - 1) : key & mask;
			
			int stepCount;
			//Lock cell to increment stepSize later if needed
			locks[startCellLockIndex].lock();
			
			try
			{
				 //Linearizable since if myTable[startIndex].StepSize is increased during run it means add ran parallely to this add.
			    //					 if myTable[startIndex].StepSize is decreased during run it means remove ran parallely to this add.
		    	for ( stepCount = 0 ; stepCount <= maxBucketSize ; stepCount ++){
		    		// mod myTable.length to ensure no overrun occurs.
		    		int index = (startIndex + stepCount) % myTable.length;
		    		if (myTable[index].key == key)
		    		{
		    			boolean newItemAdded = 	tryUpdateCell(key, x, myTable, startCellLockIndex, index);
		    			// no need to increment step size only mark add as finished
		    			if ( newItemAdded  )
		    			{
		    				addFinished = true;
		    			}
		    			break;
		    		}
		    		else if (myTable[index].key == NO_KEY)
		    		{
		    			boolean newItemAdded = 	tryUpdateCell(key, x, myTable, startCellLockIndex, index);
		    			if ( newItemAdded  )
		    			{
		    				if ( stepCount > myTable[startIndex].StepSize )
		    				{
		    					myTable[startIndex].StepSize = stepCount;
		    				}
		    				addFinished = true;
		    			}
		    			// if item was added addFinished  is not set to true and the add proccess will begin all over again. 
		    			break;
		    		}
		    	}
	    	
		    	// if couldn't find an empty cell, notify that a resize is needed.
		    	if ( stepCount > maxBucketSize  )
		    	{
		    		resizeNeeded = true;
		    	}
			}
			finally{
				locks[startCellLockIndex].unlock();				
			}
	    	
		}
		
		// returns false if resize is needed.
		return addFinished;
				
	}
	

	private boolean tryUpdateCell(int key, T x, LinearProbeCell<T>[] myTable,
									int startCellLockIndex, int index) {
		boolean newItemAdded = false;
		// If the table is smaller then the size of locks , use only some of the locks.
		int lockIndex  = mask > (locks.length - 1) ? index & (locks.length - 1) : index & mask;
		// To avoid deadlocks force lock only if startCellLockIndex > lockIndex, otherwise only try to lock and start over if lock was not successful.
		if ( startCellLockIndex < lockIndex )
		{
			locks[lockIndex].lock();
			try{
				myTable[index].key = key;
				myTable[index].item = x;
			}
			finally{
				locks[lockIndex].unlock();
				newItemAdded = true;
			}
		}
		else
		{
			if (locks[lockIndex].tryLock()){
				try{
					myTable[index].key = key;
					myTable[index].item = x;
				}
				finally{
					locks[lockIndex].unlock();
					newItemAdded = true;
				}
				
			}
			else
			{
				// try lock failed - start over.
			}
		}
		
		return newItemAdded;
	}
	
	@SuppressWarnings("unchecked")
	 public void resize(int currentLength) {
			//Lock all locks before starting resize
			for (ReentrantLock lock : locks){
				lock.lock();
			}
			try{
				
				// if resize already happened
				if (currentLength < table.length)
				{
					return;
				}
				
				LinearProbeCell<T>[] newTable = new LinearProbeCell[2*table.length];
				for ( int i = 0; i < newTable.length; i++ ) {
					newTable[i] = new LinearProbeCell<T>(NO_KEY, null, 0);
				}
			    for( int i = 0; i < table.length; i++ ) {
                  //tryAdd shoudln't fail since this is only a copy of the old table.
			      tryAdd(newTable, table[i].key, table[i].item);
			    }
			    table = newTable;
			    logSize++;
			    mask = (1 << logSize) - 1;
			}finally{
				//Release all locks after resizing
				for (ReentrantLock lock : locks){
					lock.unlock();
				}
			}
		}



	@Override
	public boolean remove(int key) {
		boolean removeSuccessful = false, itemWasntFound = false;
		LinearProbeCell<T>[] myTable = table;
	    int myMask,startIndex,maxStep;
	    
	    myMask = myTable.length - 1;
	    startIndex =  key & myMask;
	    maxStep = myTable[startIndex].StepSize;
	    
	    //Iterate till remove is successful or discovering that item isn't present in array
	    while ( !removeSuccessful && !itemWasntFound)
	    {
			// If the table is smaller then the size of locks , use only some of the locks.
			int startCellLockIndex  = mask > (locks.length - 1) ? key & (locks.length - 1) : key & mask;
				
			int stepCount;
			//Lock cell to increment stepSize later if needed
			locks[startCellLockIndex].lock();
			
			try
			{
				//Linearizable since if myTable[startIndex].StepSize is increased during run it means add ran parallely to this remove.
			    //					 if myTable[startIndex].StepSize is decreased during run it means remove ran parallely to this remove.
		    	for ( stepCount = 0 ; stepCount <= maxStep ; stepCount ++){
		    		// mod myTable.length to ensure no overrun occurs.
		    		int index = (startIndex + stepCount) % myTable.length;
		    		if (myTable[index].key == key)
		    		{
		    			// If update of cell was successful break from the loop and finish, else break from the loop and try again.
		    			if (tryUpdateCell(NO_KEY, null,myTable,startCellLockIndex, index))
		    			{
		    				// update step size if needed.
		    				if ( stepCount < myTable[startIndex].StepSize )
		    				{
		    					myTable[startIndex].StepSize = stepCount;
		    				}
		    				removeSuccessful = true;
		    			}
		    			break;
		    		}
		    	}
		    	
		    	if ( stepCount > maxStep)
		    	{
		    		itemWasntFound = true;
		    	}
			}
			finally
			{
				locks[startCellLockIndex].unlock();	
			}
	    }
	    
	    // removeSuccessful == false means that the item wasn't found.
	    return removeSuccessful;
    	
    	
	}

	@Override
	public boolean contains(int key) {
		boolean retVal = false;
		LinearProbeCell<T>[] myTable = table;
	    int myMask,startIndex,maxStep;
	    
	    myMask = myTable.length - 1;
	    startIndex =  key & myMask;
	    maxStep = myTable[startIndex].StepSize;
	    
	    //Linearizable since if myTable[startIndex].StepSize is increased during run it means add ran parallely to "contains".
	    //					 if myTable[startIndex].StepSize is decreased during run it means remove ran parallely to "contains".
    	for (int stepCount = 0 ; stepCount <= maxStep ; stepCount ++){
    		// mod myTable.length to ensure no overrun occurs.
    		int index = (startIndex + stepCount) % myTable.length;
    		if (myTable[index].key == key)
    		{
    			retVal = true;
    			break;
    		}
    	}
	    	
    	return retVal;
	    
	}

}
