
public class OptimisticHash<T> extends LockingHashTable<T> {

	public OptimisticHash(int logSize, int maxBucketSize,int numThreads) {
		super(logSize, maxBucketSize,numThreads);
	}
	
	@Override
	 public boolean contains(int key) {
		SerialList<T,Integer>[] myTable = table;
	    int myMask = myTable.length - 1;
	    if( myTable[key & myMask] != null )
	      return myTable[key & myMask].contains(key);
	    else
	      return false;
	}

}
