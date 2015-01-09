
import java.util.concurrent.atomic.AtomicInteger;


public class LockFreeHash<T> implements HashTable<T>{

	private static final int THRESHOLD = 8;
	protected BucketLockFreeList<T>[] bucket;
	protected final int maxBucketSize;
	protected AtomicInteger setSize;
	protected AtomicInteger mask;
	protected AtomicInteger logSize;
	
	@SuppressWarnings("unchecked")
	public LockFreeHash(int capacity, int logsize) {

		this.logSize.set(logsize);
	    this.mask.set((1 << logSize.get()) - 1);
	    this.maxBucketSize = capacity;
	    this.bucket = new BucketLockFreeList[1 << logSize.get()];
		
	}
	
	private BucketLockFreeList<T> getBucketList(int myBucket) {
		 if (bucket[myBucket] == null)
		 initializeBucket(myBucket);
		 return bucket[myBucket];
		 }
	
	
	private void initializeBucket(int myBucket) {
		 int parent = getParent(myBucket);
		 if (bucket[parent] == null)
			 initializeBucket(parent);
		 BucketLockFreeList<T> b = bucket[parent].getSentinel(myBucket);
		 if (b != null)
			 bucket[myBucket] = b;
		}
	
	private int getParent(int myBucket){
		int  bucketSize = 2^logSize.get();
		int parent = bucketSize;
		do {
			 parent = parent >> 1;
		} while (parent > myBucket);
		parent = myBucket - parent;
		return parent;
	}
	
	
	@Override
	public void add(int key, T x) {

		int index = key & mask.get();
		BucketLockFreeList<T> b = getBucketList(index);
		if(b.add(BucketLockFreeList.makeRegularKey(key), x)){
			int setSizeNow = setSize.getAndIncrement();
			int bucketSizeNow = 2^logSize.get();
			if (setSizeNow / bucketSizeNow > THRESHOLD)
				logSize.compareAndSet(logSize.get(), logSize.get()+1);		
		}
	}

	@Override
	public boolean remove(int key) {
		int index = key & mask.get();
		BucketLockFreeList<T> b = getBucketList(index);
		boolean status = b.remove(key);
		if (status){
			setSize.decrementAndGet();
		}
		return status;
	}

	@Override
	public boolean contains(int key) {
		int index = key & mask.get();
		BucketLockFreeList<T> b = getBucketList(index);
		return b.contains(key);		
	}
	
}



