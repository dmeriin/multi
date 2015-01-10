import java.util.concurrent.locks.Lock;

public interface HashPacketWorker<T> extends Runnable {
  public void run();
}

class SerialHashPacketWorker implements HashPacketWorker {
  PaddedPrimitiveNonVolatile<Boolean> done;
  final HashPacketGenerator source;
  final SerialHashTable<Packet> table;
  long totalPackets = 0;
  long residue = 0;
  Fingerprint fingerprint;
  public SerialHashPacketWorker(
    PaddedPrimitiveNonVolatile<Boolean> done, 
    HashPacketGenerator source,
    SerialHashTable<Packet> table) {
    this.done = done;
    this.source = source;
    this.table = table;
    fingerprint = new Fingerprint();
  }
  
  public void run() {
    HashPacket<Packet> pkt;
    while( !done.value ) {
      totalPackets++;
      pkt = source.getRandomPacket();
      residue += fingerprint.getFingerprint(pkt.getItem().iterations,pkt.getItem().seed);
      switch(pkt.getType()) {
        case Add: 
          table.add(pkt.mangleKey(),pkt.getItem());
          break;
        case Remove:
          table.remove(pkt.mangleKey());
          break;
        case Contains:
          table.contains(pkt.mangleKey());
          break;
      }
    }
  }  
}



class ParallelHashPacketWorker_RandomQueue implements HashPacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint fingerprint = new Fingerprint();
	  long residue = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue< HashPacket<Packet>>[] queues;
	  final RandomGenerator rand;
	  final HashTable<Packet> table;
	  
	  public ParallelHashPacketWorker_RandomQueue(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    int numSources,
	    LamportQueue<HashPacket<Packet>>[] queues,
	    byte seed,
	    HashTable<Packet> table) {
	    this.done = done;
	    this.numSources = numSources;
	    this.queues = queues;
	    this.rand = new RandomGenerator();
	    this.table = table;
	    rand.setSeed(seed);
	  }
	  
	  private boolean deq(){
		  HashPacket<Packet> pkt;
		//Get a random queue every time dequeue is called.
		LamportQueue<HashPacket<Packet>> queue = queues[(int) (this.rand.getRand() % numSources)] ;
		try {
			queue.lock.lock();
			pkt = queue.deq();
       } 
		catch (EmptyException e) {
			return false;
		}
		finally{
			queue.lock.unlock();
		}
       totalPackets++;
       residue += fingerprint.getFingerprint(pkt.getItem().iterations, pkt.getItem().seed);
       switch(pkt.getType()) {
       case Add: 
         table.add(pkt.mangleKey(),pkt.getItem());
         break;
       case Remove:
         table.remove(pkt.mangleKey());
         break;
       case Contains:
         table.contains(pkt.mangleKey());
         break;
     }
       
       
       return true;
	  }
	  
	  public void run() {
	    
	    while( !done.value ) {
	      deq();
	    }
	    //Dequeue until queue is empty.
	    while (deq()){};
	  }
	 
}


class ParallelHashPacketWorker_RandomQueue_Drop implements HashPacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint fingerprint = new Fingerprint();
	  long residue = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue< HashPacket<Packet>>[] queues;
	  final RandomGenerator rand;
	  
	  public ParallelHashPacketWorker_RandomQueue_Drop(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    int numSources,
	    LamportQueue<HashPacket<Packet>>[] queues,
	    byte seed) {
	    this.done = done;
	    this.numSources = numSources;
	    this.queues = queues;
	    this.rand = new RandomGenerator();
	    rand.setSeed(seed);
	  }
	  
	  private boolean deq(){
		  HashPacket<Packet> pkt;
		//Get a random queue every time dequeue is called.
		LamportQueue<HashPacket<Packet>> queue = queues[(int) (this.rand.getRand() % numSources)] ;
		try {
			queue.lock.lock();
			pkt = queue.deq();
      } 
		catch (EmptyException e) {
			return false;
		}
		finally{
			queue.lock.unlock();
		}
      totalPackets++;
      residue += fingerprint.getFingerprint(pkt.getItem().iterations, pkt.getItem().seed);   
      
      return true;
	  }
	  
	  public void run() {
	    
	    while( !done.value ) {
	      deq();
	    }
	    //Dequeue until queue is empty.
	    while (deq()){};
	  }
	 
}



