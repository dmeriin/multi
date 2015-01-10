



public class Dispatcher implements Runnable {
	PaddedPrimitiveNonVolatile<Boolean> done;
	final PacketSource pkt;
	final int numSources;
	final boolean uniformBool;
	final LamportQueue<Packet>[] queues;
	long totalPackets = 0;
	
	public Dispatcher(
		    PaddedPrimitiveNonVolatile<Boolean> done, 
		    PacketSource pkt,
		    boolean uniformBool,
		    int numSources,
		    LamportQueue<Packet>[] queues){
		this.done = done;
	    this.pkt = pkt;
	    this.uniformBool = uniformBool;
	    this.numSources = numSources;
	    this.queues = queues;
		
	}
	
	@Override
	public void run() {
		Packet tmp;
	    while( !done.value ) {
	      for( int i = 0; i < numSources; i++ ) {
	    	boolean isEnqueued = false;  
	    	
	        if( uniformBool )
	          tmp = pkt.getUniformPacket(i);
	        else
	          tmp = pkt.getExponentialPacket(i);
	        
	        while ( !isEnqueued ){
	        	try {
	        		queues[i].enq(tmp);
	        		isEnqueued = true;
	        		totalPackets++;
	        	 } catch (FullException e) {continue;}
	        	
	        }
	      }
	    }

	}

}


 class HashDispatcher implements Runnable {
	PaddedPrimitiveNonVolatile<Boolean> done;
	 final HashPacketGenerator source;
	final int numSources;
	final LamportQueue<HashPacket<Packet>>[] queues;
	long totalPackets = 0;
	
	public HashDispatcher(
		    PaddedPrimitiveNonVolatile<Boolean> done, 
		    HashPacketGenerator source,
		    int numSources,
		    LamportQueue<HashPacket<Packet>>[] queues){
		this.done = done;
	    this.source = source;
	    this.numSources = numSources;
	    this.queues = queues;
		
	}
	
	@Override
	public void run() {
		HashPacket<Packet> pkt;
	    while( !done.value ) {
	      for( int i = 0; i < numSources; i++ ) {
	    	boolean isEnqueued = false;  
	    	
	    	pkt = source.getRandomPacket();
	    	
	        while ( !isEnqueued ){
	        	try {
	        		queues[i].enq(pkt);
	        		isEnqueued = true;
	        		totalPackets++;
	        	 } catch (FullException e) {continue;}
	        	
	        }
	      }
	    }

	}

}
