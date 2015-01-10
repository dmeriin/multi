
class SerialHashPacket {
  public static void main(String[] args) {

    final int numMilliseconds = Integer.parseInt(args[0]);    
    final float fractionAdd = Float.parseFloat(args[1]);
    final float fractionRemove = Float.parseFloat(args[2]);
    final float hitRate = Float.parseFloat(args[3]);
    final int maxBucketSize = Integer.parseInt(args[4]);
    final long mean = Long.parseLong(args[5]);
    final int initSize = Integer.parseInt(args[6]);

    @SuppressWarnings({"unchecked"})
    StopWatch timer = new StopWatch();
    HashPacketGenerator source = new HashPacketGenerator(fractionAdd,fractionRemove,hitRate,mean);
    PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
    SerialHashTable<Packet> table = new SerialHashTable<Packet>(1, maxBucketSize);
    
    for( int i = 0; i < initSize; i++ ) {
      HashPacket<Packet> pkt = source.getAddPacket();
      table.add(pkt.mangleKey(), pkt.getItem());
    }
    SerialHashPacketWorker workerData = new SerialHashPacketWorker(done, source, table);
    Thread workerThread = new Thread(workerData);
    
    workerThread.start();
    timer.startTimer();
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    done.value = true;
    memFence.value = true;
    try {
      workerThread.join();
    } catch (InterruptedException ignore) {;}      
    timer.stopTimer();
    final long totalCount = workerData.totalPackets;
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}


class ParallelHashPacket {
	
  public final static int QueueDepth = 8;

  public static void main(String[] args) {

    final int numMilliseconds = Integer.parseInt(args[0]);    
    final float fractionAdd = Float.parseFloat(args[1]);
    final float fractionRemove = Float.parseFloat(args[2]);
    final float hitRate = Float.parseFloat(args[3]);
    final int maxBucketSize = Integer.parseInt(args[4]);
    final long mean = Long.parseLong(args[5]);
    final int initSize = Integer.parseInt(args[6]);
    final int numWorkers = Integer.parseInt(args[7]);
    final int tableType = Integer.parseInt(args[8]);

    
    
    StopWatch timer = new StopWatch();
    
    @SuppressWarnings("unchecked")
	final LamportQueue<HashPacket<Packet>>[] queues = (LamportQueue<HashPacket<Packet>>[]) new LamportQueue[numWorkers] ;
    for (int i = 0 ; i < numWorkers ; i++ ){
    	queues[i] = new LamportQueue<HashPacket<Packet>>(QueueDepth,new BackoffLock());
    }
    
    PaddedPrimitiveNonVolatile<Boolean> doneDispatcher = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceDispatcher = new PaddedPrimitive<Boolean>(false);
    PaddedPrimitiveNonVolatile<Boolean> doneWorkers = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceWorkers = new PaddedPrimitive<Boolean>(false);
    
    HashTable<Packet> table ;
    
    
    // 0 - Lock-based, 1 -Optimistic, 2 - LockFree, 3 - Linearly Probed  
    switch (tableType)
    {
    	case 0:
    		table = new LockingHashTable<Packet>(1, maxBucketSize, numWorkers);
    		break;
    	case 1:
    		table = new OptimisticHash<Packet>(1, maxBucketSize, numWorkers);
    		break;
    	case 2:
    		table = new LockFreeHash<Packet>(maxBucketSize, 1);
    		break;
    	case 3:
    		table = new LinearProbeHashTable<Packet>(1, maxBucketSize, numWorkers);
    		break;
    	default:
    		System.out.println("not a valid table type");
    		return;
    }
    
    
   
    HashPacketGenerator source = new HashPacketGenerator(fractionAdd,fractionRemove,hitRate,mean);

    for( int i = 0; i < initSize; i++ ) {
        HashPacket<Packet> pkt = source.getAddPacket();
        table.add(pkt.mangleKey(), pkt.getItem());
      }
    
 
    final HashDispatcher dispatcher = new HashDispatcher(doneDispatcher, source,  numWorkers, queues);
    Thread dispatcherThread = new Thread(dispatcher);
    
    final ParallelHashPacketWorker_RandomQueue[] workers = new ParallelHashPacketWorker_RandomQueue[numWorkers] ;
    Thread[] workerThreads = new Thread[numWorkers];
    for (int i = 0 ; i < numWorkers ; i++ ){
    	workers[i] = new ParallelHashPacketWorker_RandomQueue(
    			doneWorkers,  
    			numWorkers,
    		    queues,
    		     (byte)4,
    		    table);
    	workerThreads[i] = new Thread(workers[i]);
    }
    
    for (int i = 0 ; i < numWorkers ; i++ ){
    	workerThreads[i].start();
    }
    
    timer.startTimer();
    
    dispatcherThread.start();
    
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    
    
    doneDispatcher.value = true;
    memFenceDispatcher.value = true;  	// memFence is a 'volatile' forcing a memory fence
    try {                   			// which means that done.value is visible to the workers
    	dispatcherThread.join();
    } catch (InterruptedException ignore) {;}    
    
    doneWorkers.value = true;
    memFenceWorkers.value = true;  	// memFence is a 'volatile' forcing a memory fence
    								// which means that done.value is visible to the workers
    for (int i = 0 ; i < numWorkers ; i++ ){
    	try {                   			
    		workerThreads[i].join();
        } catch (InterruptedException ignore) {;}
    }
       
    timer.stopTimer();

    final long totalCount = dispatcher.totalPackets;
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}


class ParallelHashPacket_Drop {
	
  public final static int QueueDepth = 8;

  public static void main(String[] args) {

    final int numMilliseconds = Integer.parseInt(args[0]);    
    final float fractionAdd = Float.parseFloat(args[1]);
    final float fractionRemove = Float.parseFloat(args[2]);
    final float hitRate = Float.parseFloat(args[3]);
    final int maxBucketSize = Integer.parseInt(args[4]);
    final long mean = Long.parseLong(args[5]);
    final int initSize = Integer.parseInt(args[6]);
    final int numWorkers = Integer.parseInt(args[7]);

    
    
    StopWatch timer = new StopWatch();
    
    @SuppressWarnings("unchecked")
	final LamportQueue<HashPacket<Packet>>[] queues = (LamportQueue<HashPacket<Packet>>[]) new LamportQueue[numWorkers] ;
    for (int i = 0 ; i < numWorkers ; i++ ){
    	queues[i] = new LamportQueue<HashPacket<Packet>>(QueueDepth,new BackoffLock());
    }
    
    PaddedPrimitiveNonVolatile<Boolean> doneDispatcher = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceDispatcher = new PaddedPrimitive<Boolean>(false);
    PaddedPrimitiveNonVolatile<Boolean> doneWorkers = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceWorkers = new PaddedPrimitive<Boolean>(false);
    
    
   
    HashPacketGenerator source = new HashPacketGenerator(fractionAdd,fractionRemove,hitRate,mean);

 
    final HashDispatcher dispatcher = new HashDispatcher(doneDispatcher, source,  numWorkers, queues);
    Thread dispatcherThread = new Thread(dispatcher);
    
    final ParallelHashPacketWorker_RandomQueue_Drop[] workers = new ParallelHashPacketWorker_RandomQueue_Drop[numWorkers] ;
    Thread[] workerThreads = new Thread[numWorkers];
    for (int i = 0 ; i < numWorkers ; i++ ){
    	workers[i] = new ParallelHashPacketWorker_RandomQueue_Drop(
    			doneWorkers,  
    			numWorkers,
    		    queues,
    		     (byte)4);
    	workerThreads[i] = new Thread(workers[i]);
    }
    
    for (int i = 0 ; i < numWorkers ; i++ ){
    	workerThreads[i].start();
    }
    
    timer.startTimer();
    
    dispatcherThread.start();
    
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    
    
    doneDispatcher.value = true;
    memFenceDispatcher.value = true;  	// memFence is a 'volatile' forcing a memory fence
    try {                   			// which means that done.value is visible to the workers
    	dispatcherThread.join();
    } catch (InterruptedException ignore) {;}    
    
    doneWorkers.value = true;
    memFenceWorkers.value = true;  	// memFence is a 'volatile' forcing a memory fence
    								// which means that done.value is visible to the workers
    for (int i = 0 ; i < numWorkers ; i++ ){
    	try {                   			
    		workerThreads[i].join();
        } catch (InterruptedException ignore) {;}
    }
       
    timer.stopTimer();

    final long totalCount = dispatcher.totalPackets;
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}