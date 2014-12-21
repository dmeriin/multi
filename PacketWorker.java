


public interface PacketWorker extends Runnable {
  public void run();
}

class SerialPacketWorker implements PacketWorker {
	  PaddedPrimitiveNonVolatile<Boolean> done;
	  final PacketSource pkt;
	  final Fingerprint residue = new Fingerprint();
	  long fingerprint = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final boolean uniformBool;
	  public SerialPacketWorker(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    PacketSource pkt,
	    boolean uniformBool,
	    int numSources) {
	    this.done = done;
	    this.pkt = pkt;
	    this.uniformBool = uniformBool;
	    this.numSources = numSources;
	  }
	  
	  public void run() {
	    Packet tmp;
	    while( !done.value ) {
	      for( int i = 0; i < numSources; i++ ) {
	        if( uniformBool )
	          tmp = pkt.getUniformPacket(i);
	        else
	          tmp = pkt.getExponentialPacket(i);
	        totalPackets++;
	        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);        
	      }
	    }
	  }  
	}

class SerialQueuePacketWorker implements PacketWorker {
  PaddedPrimitiveNonVolatile<Boolean> done;
  final PacketSource pkt;
  final Fingerprint residue = new Fingerprint();
  long fingerprint = 0;
  long totalPackets = 0;
  final int numSources;
  final boolean uniformBool;
  final LamportQueue<Packet>[] queues;
  // Also all lamport queues.
  public SerialQueuePacketWorker(
    PaddedPrimitiveNonVolatile<Boolean> done, 
    PacketSource pkt,
    boolean uniformBool,
    int numSources,
    LamportQueue<Packet>[] queues) {
    this.done = done;
    this.pkt = pkt;
    this.uniformBool = uniformBool;
    this.numSources = numSources;
    this.queues = queues;
  }
  
  public void run() {
    Packet tmp;
    while( !done.value ) {
      for( int i = 0; i < numSources; i++ ) {
        if( uniformBool )
          tmp = pkt.getUniformPacket(i);
        else
          tmp = pkt.getExponentialPacket(i);
        // Should never by full or empty because worker always enqueues and then immediately dequeues.
         try {
           queues[i].enq(tmp);
         } catch (FullException e) {;}
	 
         try {
           queues[i].deq();
        } catch (EmptyException e) {;}
         
        totalPackets++;
        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);        
      }
    }
  }  
}

 class ParallelPacketWorker_LockFree implements PacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint residue = new Fingerprint();
	  long fingerprint = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue<Packet> queue;
	  // Also all lamport queues.
	  public ParallelPacketWorker_LockFree(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    int numSources,
	    LamportQueue<Packet> queue) {
	    this.done = done;
	    this.numSources = numSources;
	    this.queue = queue;
	  }
	  
	  private boolean deq(){
		Packet tmp;
		
		try {
        	tmp = queue.deq();
        } catch (EmptyException e) {return false;}
        totalPackets++;
        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);
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

 class ParallelPacketWorker_HomeQueue implements PacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint residue = new Fingerprint();
	  long fingerprint = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue<Packet> queue;
	  // Also all lamport queues.
	  public ParallelPacketWorker_HomeQueue(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    int numSources,
	    LamportQueue<Packet> queue) {
	    this.done = done;
	    this.numSources = numSources;
	    this.queue = queue;
	  }
	  
	  private boolean deq(){
		Packet tmp;
		
		try {
			queue.lock.lock();
        	tmp = queue.deq();
        } 
		catch (EmptyException e) {
			return false;
		}
		finally{
			queue.lock.unlock();
		}
        totalPackets++;
        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);
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
 
 class ParallelPacketWorker_RandomQueue implements PacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint residue = new Fingerprint();
	  long fingerprint = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue<Packet>[] queues;
	  final RandomGenerator rand;
	  
	  public ParallelPacketWorker_RandomQueue(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    int numSources,
	    LamportQueue<Packet>[] queues,
	    byte seed) {
	    this.done = done;
	    this.numSources = numSources;
	    this.queues = queues;
	    this.rand = new RandomGenerator();
	    rand.setSeed(seed);
	  }
	  
	  private boolean deq(){
		Packet tmp;
		//Get a random queue every time dequeue is called.
		LamportQueue<Packet> queue = queues[(int) (this.rand.getRand() % numSources)] ;
		try {
			queue.lock.lock();
        	tmp = queue.deq();
        } 
		catch (EmptyException e) {
			return false;
		}
		finally{
			queue.lock.unlock();
		}
        totalPackets++;
        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);
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
 
 class ParallelPacketWorker_LastQueue implements PacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint residue = new Fingerprint();
	  long fingerprint = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue<Packet>[] queues;
	  final RandomGenerator rand;
	  
	  // Also all lamport queues.
	  public ParallelPacketWorker_LastQueue(
	    PaddedPrimitiveNonVolatile<Boolean> done, 
	    int numSources,
	    LamportQueue<Packet>[] queues,
	    byte seed) {
	    this.done = done;
	    this.numSources = numSources;
	    this.queues = queues;
	    this.rand = new RandomGenerator();
	    rand.setSeed(seed);
	  }
	  
	  private boolean deq( LamportQueue<Packet> queue ){
		Packet tmp;
		
		try {
        	tmp = queue.deq();
        } catch (EmptyException e) {return false;}
        totalPackets++;
        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);
        return true;
	  }
	  
	  private boolean deqWithLock( LamportQueue<Packet> queue ){
		Packet tmp;
		// Run until queue is empty
		while (true)
		{
			try {
				queue.lock.lock();
	        	tmp = queue.deq();
			}
	    	catch (EmptyException e) {
				return false;
			}
			finally{
				queue.lock.unlock();
			}
	        totalPackets++;
	        fingerprint += residue.getFingerprint(tmp.iterations, tmp.seed);
		}
        
	  }
	  
	  public void run() {
	    
	    while( !done.value ) {
	    	//Get a random queue;
			LamportQueue<Packet> queue = queues[(int) (this.rand.getRand() % numSources)] ;
			//Start dequeuing only if succeeded locking.
			if (queue.lock.tryLock()){
				try{
					// If queue is empty on first dequeue, continue and don't call deqWithLock.
					if (!deq(queue)){
						continue;
					}
				}
				finally{
					queue.lock.unlock();
				}
				//Dequeue with locking on every attempt until queue is empty.
				deqWithLock(queue);
			}
	    }
	    // No need to dequeue here until empty, because deqwithLock, dequeues until queue is empty.
	  }
	 
 }
