import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LockingHashTable<T> extends SerialHashTable<T> {
	final ReentrantReadWriteLock[] locks;
	public LockingHashTable(int logSize, int maxBucketSize,int numThreads) {
		super(logSize, maxBucketSize);
		locks = new ReentrantReadWriteLock[numThreads];
		for (int i = 0 ; i < locks.length ; i ++){
			locks[i] = new ReentrantReadWriteLock();
		}
	}
	
	@Override
	public void add(int key, T x){
		// If the table is smaller then the size of locks , use only some of the locks.
		int lockIndex = mask > (locks.length - 1) ? key & (locks.length - 1) : key & mask;  
		locks[lockIndex].writeLock().lock();
		try{
			addNoCheck(key, x);
			//If resize is needed
			if (table[key & mask].getSize() >= maxBucketSize)
			{
				locks[lockIndex ].writeLock().unlock();
				resize();
			}
		} finally {
			//The write lock might have been unlocked in resize earlier.
			if (locks[lockIndex ].writeLock().isHeldByCurrentThread())
				locks[lockIndex ].writeLock().unlock();
		}
	}
	
	@Override
	public boolean remove(int key) {
		//If the table is smaller then the size of locks , use only some of the locks.
		int lockIndex = mask > (locks.length - 1) ? key & (locks.length - 1) : key & mask;  
		locks[lockIndex].writeLock().lock();
		try{
			return super.remove(key);
		} finally {
			locks[lockIndex ].writeLock().unlock();
		}
	}
	
	@Override
    public boolean contains(int key) {
		//If the table is smaller then the size of locks , use only some of the locks.
		int lockIndex = mask > (locks.length - 1) ? key & (locks.length - 1) : key & mask;
		locks[lockIndex ].readLock().lock();
		try{
			return super.contains(key);
		} finally {
			locks[lockIndex].readLock().unlock();
		}
	}
	
	@Override
	 public void resize() {
		//Lock all locks before starting resize
		for (ReentrantReadWriteLock lock : locks){
			lock.writeLock().lock();
		}
		try{
			super.resize();
		}finally{
			//Release all locks after resizing
			for (ReentrantReadWriteLock lock : locks){
				lock.writeLock().unlock();
			}
		}
	}

	
}
