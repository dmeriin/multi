import java.util.concurrent.atomic.*;

public interface HashTable<T> {
  public void add(int key, T x);
  public boolean remove(int key);
  public boolean contains(int key);
}

class SerialHashTable<T> implements HashTable<T> {
  protected SerialList<T,Integer>[] table;
  protected int logSize;
  protected int mask;
  protected final int maxBucketSize;
  @SuppressWarnings("unchecked")
  public SerialHashTable(int logSize, int maxBucketSize) {
    this.logSize = logSize;
    this.mask = (1 << logSize) - 1;
    this.maxBucketSize = maxBucketSize;
    this.table = new SerialList[1 << logSize];
  }
  public void resizeIfNecessary(int key) {
    while( table[key & mask] != null 
          && table[key & mask].getSize() >= maxBucketSize )
      resize();
  }
  protected void addNoCheck(int key, T x) {
    int index = key & mask;
    if( table[index] == null )
      table[index] = new SerialList<T,Integer>(key,x);
    else
      table[index].add(key,x);
  }
  public void add(int key, T x) {
    addNoCheck(key,x);
    resizeIfNecessary(key);
  }
  public boolean remove(int key) {
    if( table[key & mask] != null )
      return table[key & mask].remove(key);
    else
      return false;
  }
  public boolean contains(int key) {
    SerialList<T,Integer>[] myTable = table;
    int myMask = myTable.length - 1;
    if( myTable[key & myMask] != null )
      return myTable[key & myMask].contains(key);
    else
      return false;
  }
  @SuppressWarnings("unchecked")
  public void resize() {
    SerialList<T,Integer>[] newTable = new SerialList[2*table.length];
    for( int i = 0; i < table.length; i++ ) {
      if( table[i] == null )
        continue;
      SerialList<T,Integer>.Iterator<T,Integer> iterator = table[i].getHead();
      while( iterator != null ) {
        if( newTable[iterator.key & ((2*mask)+1)] == null )
          newTable[iterator.key & ((2*mask)+1)] = new SerialList<T,Integer>(iterator.key, iterator.getItem());
        else
          newTable[iterator.key & ((2*mask)+1)].addNoCheck(iterator.key, iterator.getItem());
        iterator = iterator.getNext();
      }
    }
    table = newTable;
    logSize++;
    mask = (1 << logSize) - 1;
  }
  public void printTable() {
    for( int i = 0; i <= mask; i++ ) {
      System.out.println("...." + i + "....");
      if( table[i] != null)
        table[i].printList();
    }
  }
}

class SerialHashTableTest {
  public static void main(String[] args) {  
    SerialHashTable<Integer> table = new SerialHashTable<Integer>(2, 8);
    for( int i = 0; i < 256; i++ ) {
      table.add(i,i*i);
    }
    table.printTable();    
  }
}
