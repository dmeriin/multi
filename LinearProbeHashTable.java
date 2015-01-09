
public class LinearProbeHashTable<T> implements HashTable<T> {
	
  protected SerialList<T,Integer>[] table;
  protected int logSize;
  protected int mask;
  protected final int maxBucketSize;
	  
	@SuppressWarnings("unchecked")
	public LinearProbeHashTable(int logSize, int maxBucketSize) {
		this.logSize = logSize;
	    this.mask = (1 << logSize) - 1;
	    this.maxBucketSize = maxBucketSize;
	    this.table = new SerialList[1 << logSize];		
	}


	@Override
	public void add(int key, T x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(int key) {
		// TODO Auto-generated method stub
		return false;
	}

}
