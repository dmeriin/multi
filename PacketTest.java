
class SerialPacket {
  public static void main(String[] args) {

    final int numMilliseconds = Integer.parseInt(args[0]);    
    final int numSources = Integer.parseInt(args[1]);
    final long mean = Long.parseLong(args[2]);
    final boolean uniformFlag = Boolean.parseBoolean(args[3]);
    final short experimentNumber = Short.parseShort(args[4]);

    @SuppressWarnings({"unchecked"})
    StopWatch timer = new StopWatch();
    PacketSource pkt = new PacketSource(mean, numSources, experimentNumber);
    PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
        
    SerialPacketWorker workerData = new SerialPacketWorker(done, pkt, uniformFlag, numSources);
    Thread workerThread = new Thread(workerData);
    
    workerThread.start();
    timer.startTimer();
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    done.value = true;
    memFence.value = true;  // memFence is a 'volatile' forcing a memory fence
    try {                   // which means that done.value is visible to the workers
      workerThread.join();
    } catch (InterruptedException ignore) {;}      
    timer.stopTimer();
    final long totalCount = workerData.totalPackets;
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}

class ParallelPacket {
  public static void main(String[] args) {

    final int numMilliseconds = Integer.parseInt(args[0]);    
    final int numSources = Integer.parseInt(args[1]);
    final long mean = Long.parseLong(args[2]);
    final boolean uniformFlag = Boolean.parseBoolean(args[3]);
    final short experimentNumber = Short.parseShort(args[4]);
    final int queueDepth = Integer.parseInt(args[5]);
    final int lockType = Integer.parseInt(args[6]);
    final short strategy = Short.parseShort(args[7]);

    @SuppressWarnings({"unchecked"})
    //
    // Allocate and initialize your Lamport queues
    //
    StopWatch timer = new StopWatch();
    PacketSource pkt = new PacketSource(mean, numSources, experimentNumber);
<<<<<<< HEAD
    // 
    // Allocate and initialize locks and any signals used to marshal threads (eg. done signals)
    // 
    // Allocate and initialize Dispatcher and Worker threads
    //
    // call .start() on your Workers
    //
    timer.startTimer();
    // 
    // call .start() on your Dispatcher
    // 
    try {
      Thread.sleep(numMilliseconds);
    } catch (InterruptedException ignore) {;}
    // 
    // assert signals to stop Dispatcher - remember, Dispatcher needs to deliver an 
    // equal number of packets from each source
    //
    // call .join() on Dispatcher
    //
    // assert signals to stop Workers - they are responsible for leaving the queues
    // empty - use whatever protocol you like, but one easy one is to have each
    // worker verify that it's corresponding queue is empty after it observes the
    // done signal set to true
    //
    // call .join() for each Worker
    timer.stopTimer();
    final long totalCount = dispatchData.totalPackets;
=======
    
    LockAllocator la = new LockAllocator();

    final LamportQueue<Packet>[] queues = (LamportQueue<Packet>[]) new LamportQueue[numSources] ;
    for (int i = 0 ; i < numSources ; i++ ){
    	queues[i] = new LamportQueue<Packet>(queueDepth, la.getLock( lockType));
    }
    
    PaddedPrimitiveNonVolatile<Boolean> doneDispatcher = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceDispatcher = new PaddedPrimitive<Boolean>(false);
    PaddedPrimitiveNonVolatile<Boolean> doneWorkers = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceWorkers = new PaddedPrimitive<Boolean>(false);
    
    final Dispatcher dispatcher = new Dispatcher(doneDispatcher, pkt, uniformFlag, numSources, queues);
    Thread dispatcherThread = new Thread(dispatcher);
    
    final PacketWorker[] workers = new PacketWorker[numSources] ;
    Thread[] workerThreads = new Thread[numSources];
    for (int i = 0 ; i < numSources ; i++ ){
    	// Create new worker with the given strategy
    	switch ( strategy ){
    		case 0 :
    			workers[i] = new ParallelPacketWorker_LockFree(doneWorkers,numSources,queues[i]);
    			break;
    		case 1 :
    			workers[i] = new ParallelPacketWorker_HomeQueue(doneWorkers,numSources,queues[i]);
    			break;
    		case 2 :
    			workers[i] = new ParallelPacketWorker_RandomQueue(doneWorkers,numSources,queues, (byte) experimentNumber);
    			break;
    		case 3 :
    			workers[i] = new ParallelPacketWorker_LastQueue(doneWorkers,numSources, queues, (byte) experimentNumber);
    			break;
    		default :
    			//Use lock free if startegy is invalid.
    			workers[i] = new ParallelPacketWorker_LockFree(doneWorkers,numSources,queues[i]);
    			break;
    	}
    	
    	workerThreads[i] = new Thread(workers[i]);
    }
    
    for (int i = 0 ; i < numSources ; i++ ){
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
    for (int i = 0 ; i < numSources ; i++ ){
    	try {                   			
    		workerThreads[i].join();
        } catch (InterruptedException ignore) {;}
    }
       
    timer.stopTimer();

    final long totalCount = dispatcher.totalPackets;
>>>>>>> origin/master
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
  }
}