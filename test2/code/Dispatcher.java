

public class Dispatcher implements Runnable {
	PaddedPrimitiveNonVolatile<Boolean> done;
	final PacketSource pkt;
	final int numSources;
	final boolean uniformBool;
	final LamportQueue<Packet>[] queues;
	
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
	        	 } catch (FullException e) {continue;}
	        	
	        }
	      }
	    }

	}

}
