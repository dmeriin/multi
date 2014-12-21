


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

 class ParallelPacketWorker implements PacketWorker{

	 PaddedPrimitiveNonVolatile<Boolean> done;
	  
	  final Fingerprint residue = new Fingerprint();
	  long fingerprint = 0;
	  long totalPackets = 0;
	  final int numSources;
	  final LamportQueue<Packet> queue;
	  // Also all lamport queues.
	  public ParallelPacketWorker(
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
