import java.util.concurrent.atomic.AtomicMarkableReference;




public interface BucketList<T,K> {
  public boolean contains(K key);
  public boolean remove(K key);
  public void add(K key, T item);
  public int getSize();
  public abstract class Iterator {
    public abstract boolean hasNext();
    public abstract Iterator getNext();
  }
}


class SerialList<T,K> implements BucketList<T,K> {
  int size = 0;
  SerialList<T,K>.Iterator<T,K> head;

  public SerialList() {
    this.head = null;
    this.size = 0;
  }
  public SerialList(K key, T item) {
    this.head = new Iterator<T,K>(key,item,null);
    this.size = 1;
  }
  public Iterator<T,K> getHead() {
    return head;
  }
  public Iterator<T,K> getItem(K key) {
    SerialList<T,K>.Iterator<T,K> iterator = head;
    while( iterator != null ) {
      if( iterator.key.equals(key) )
        return iterator;
      else
        iterator = iterator.next;
    }
    return null;
  }
  public boolean contains(K key) {
    SerialList<T,K>.Iterator<T,K> iterator = getItem(key);
    if( iterator == null )
      return false;
    else
      return true;
  }
  @SuppressWarnings("unchecked")
  public boolean remove(K key) {
    if( contains(key) == false )
      return false;
    SerialList<T,K>.Iterator<T,K> iterator = head;
    if( iterator == null )
      return false;
    if( head.key.equals(key) ) {
      head = head.getNext();
      size--;
      return true;
    }
    while( iterator.hasNext() ) {
      if( iterator.getNext().key.equals(key) ) {
        iterator.setNext(iterator.getNext().getNext());
        size--;
        return true;
      }
      else
        iterator = iterator.getNext();
    }
    return false;
  }
  public void add(K key, T item) {
    SerialList<T,K>.Iterator<T,K> tmpItem = getItem(key);
    if( tmpItem != null ) {
      tmpItem.item = item; // we're overwriting, so the size stays the same
    }
    else {
      @SuppressWarnings("unchecked")      
      SerialList<T,K>.Iterator<T,K> firstItem = new Iterator<T,K>(key, item, head);
      head = firstItem;
      size++;
    }
  }
  public void addNoCheck(K key, T item) {
    SerialList<T,K>.Iterator<T,K> firstItem = new Iterator<T,K>(key, item, head);
    head = firstItem;
    size++;
  }
  public int getSize() {
    return size;
  }
  @SuppressWarnings("unchecked")
  public void printList() {
    SerialList<T,K>.Iterator<T,K> iterator = head;
    System.out.println("Size: " + size);
    while( iterator != null ) {
      System.out.println(iterator.getItem());
      iterator = iterator.getNext();
    }
  }
  @SuppressWarnings("hiding")
public class Iterator<T,K> {
    public final K key;
    private T item;
    private Iterator<T,K> next;
    public Iterator(K key, T item, Iterator<T,K> next) {
      this.key = key;
      this.item = item;
      this.next = next;
    }
    @SuppressWarnings("unchecked")
    public Iterator() {
      this.key = (K) new Object();
      this.item = (T) new Object();
      this.next = null;
    }
    public boolean hasNext() {
      return next != null;
    }
    public Iterator<T, K> getNext() {
      return next;
    }
    public void setNext(Iterator<T,K> next) {this.next = next; }
    public T getItem() { return item; }
    public void setItem(T item) { this.item = item; }
  }
}

class BucketLockFreeList<T> {

	private static final int SEN_MASK = 0x00FFFFFF;
	private static final int REG_MASK = 0x00800000;
	private Node head;
	
	public BucketLockFreeList() {
		this(Integer.MAX_VALUE,null);
	}
	
	public BucketLockFreeList(Integer key, T item) {
		this.head = new Node(key,item);
	}
	
	public boolean add(Integer key,T item) {
		
		while (true)
		{
			Window window = find(head, key);
			Node pred = window.pred, curr = window.curr;
			if (curr.key == key) 
			{
				return false;
			}
			else 
			{
				Node node = new Node(key,item);
				node.next = new AtomicMarkableReference<Node>(curr, false);
				if (pred.next.compareAndSet(curr, node, false, false)) {
					return true;
				}
			}
		}
	}
	
	public boolean remove(Integer key){
		
		boolean snip;
		while (true) 
		{
			Window window = find(head, key);
			Node pred = window.pred, curr = window.curr;
			if (curr.key != key) {
				return false;
			} 
			else
			{
				Node succ = curr.next.getReference();
				snip = curr.next.attemptMark(succ, true);
				if (!snip)
					continue;
				pred.next.compareAndSet(curr, succ, false, false);
				return true;
			}
		}
	}
	public boolean contains(Integer key){
		boolean[] marked = {false};
		Node curr = head;
		while (curr.key < key) {
			curr = curr.next.getReference();
			Node succ = curr.next.get(marked);
		}
		return (curr.key == key && !marked[0]);
	}
	
	public BucketLockFreeList<T> getSentinel(int index) {
		int key = makeSentinelKey(index);
		boolean splice;
		while (true) {
		Window window = find(head,key);
		Node pred = window.pred;
		Node curr = window.curr;
		if (curr.key == key) {
			return new BucketLockFreeList<T>(key,curr.item);
		} 
		else 
		{
			Node node = new Node(key,null);
			node.next.set(pred.next.getReference(), false);
			splice = pred.next.compareAndSet(curr, node, false, false);
		if (splice)
			return new BucketLockFreeList<T>(node.key,node.item);
		else
			continue;
		}
		}
	}
	
	private static int makeSentinelKey(int key) {
		 return Integer.reverse(key & SEN_MASK);
		}
	
	public static int makeRegularKey(int key) {
		 return Integer.reverse(key & REG_MASK);
		}
	
	
	private Window find(Node head, int key) {
		Node pred = null, curr = null, succ = null;
		boolean[] marked = {false};
		boolean snip,retry = false;
		while (true) 
		{
			retry = false;
			pred = head;
			curr = pred.next.getReference();
			while (true) {
				succ = curr.next.get(marked);
				while (marked[0]) 
				{
					snip = pred.next.compareAndSet(curr, succ, false, false);
					if (!snip) {
						retry = true;
						break;
					}
					curr = succ;
					succ = curr.next.get(marked);
				}
				if(retry)
					break;
				if (curr.key >= key)
					 return new Window(pred, curr);
				pred = curr;
				curr = succ;
			}
		}
		}
	
	public boolean contains(int key) {
		int regkey = makeRegularKey(key);
		 Window window = find(head, regkey);
		 Node curr = window.curr;
		 return (curr.key == key);
	}
	
	public class Node {
		public int key;
		public T item;
		public AtomicMarkableReference<Node> next;
		public Node(int key,T item) {
			this.key = key;
			this.item=item;
			next = new AtomicMarkableReference<Node>(null, false);
		}
	}
	
	public class Window {
		 public Node pred, curr;
		 Window(Node myPred, Node myCurr) {
		 pred = myPred; curr = myCurr;
		 }  
	}
	
}
class BucketListTest {
  public static void main(String[] args) {  
    SerialList<Long,Long> list = new SerialList<Long,Long>();
    for( long i = 0; i < 15; i++ ) {
      list.add(i,i*i);
      list.printList();
    }
    for( long i = 14; i > 0; i -= 2 ) {
      list.remove(i);
      list.printList();
    }
  }
}
