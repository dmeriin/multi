import java.util.concurrent.atomic.AtomicMarkableReference;


public class LockFreeBucketList<T> {
	private static final int SEN_MASK = 0x00FFFFFF;
	private static final int REG_MASK = 0x00800000;
	private Node head;
	
	public LockFreeBucketList() {
		this(0,null);
	}
	
	
	public LockFreeBucketList(int key, T item) {
		head = new Node(key,item);
		head.next = new AtomicMarkableReference<Node>(new Node(Integer.MAX_VALUE,null), false);
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
			@SuppressWarnings("unused")
			Node succ = curr.next.get(marked);
		}
		return (curr.key == key && !marked[0]);
	}
	
	public LockFreeBucketList<T> getSentinel(int index) {
		int key = makeSentinelKey(index);
		boolean splice;
		while (true) {
		Window window = find(head,key);
		Node pred = window.pred;
		Node curr = window.curr;
		if (curr.key == key) {
			return new LockFreeBucketList<T>(key,curr.item);
		} 
		else 
		{
			Node node = new Node(key,null);
			node.next.set(pred.next.getReference(), false);
			splice = pred.next.compareAndSet(curr, node, false, false);
		if (splice)
			return new LockFreeBucketList<T>(node.key,node.item);
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
			this.next = new AtomicMarkableReference<Node>(null, false);
		}
	}
	
	public class Window {
		 public Node pred, curr;
		 Window(Node myPred, Node myCurr) {
		 pred = myPred; curr = myCurr;
		 }  
	}
	
}

