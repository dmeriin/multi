import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LockingHashTable<T> extends SerialHashTable<T> {
	final ReentrantReadWriteLock[] locks;
	public LockingHashTable(int logSize, int maxBucketSize) {
		super(logSize, maxBucketSize);
		locks = new ReentrantReadWriteLock[(1 << logSize)];
		for (int i = 0 ; i < locks.length ; i ++){
			locks[i] = new ReentrantReadWriteLock();
		}
	}
	
	@Override
	public void addNoCheck(int key, T x){
		locks[key & this.mask].writeLock().lock();
		try{
			super.addNoCheck(key, x);
		} finally {
			locks[key & this.mask ].writeLock().unlock();;
		}
	}
	
	@Override
	public boolean remove(int key) {
		locks[key & this.mask].writeLock().lock();
		try{
			return super.remove(key);
		} finally {
			locks[key & this.mask ].writeLock().unlock();;
		}
	}
	
	@Override
    public boolean contains(int key) {
		locks[key & this.mask].readLock().lock();
		try{
			return super.contains(key);
		} finally {
			locks[key & this.mask ].readLock().unlock();
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
