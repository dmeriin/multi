



/*class SerialFirewall1 {
  public static void main(String[] args) {
	  MppRunner.test2();
  }
  }
*/

class SerialFirewall {
  public static void main(String[] args) {
    final int numMilliseconds = Integer.parseInt(args[0]);   
    final int numSources = Integer.parseInt(args[1]);
    final long mean = Long.parseLong(args[2]);
    final boolean uniformFlag = Boolean.parseBoolean(args[3]);
    final short experimentNumber = Short.parseShort(args[4]);
    
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
    args[5] = "" + totalCount  + "";
    args[6] = "" + timer.getElapsedTime() + "";
  }
}

class SerialQueueFirewall {
  public static void main(String[] args) {
    final int numMilliseconds = Integer.parseInt(args[0]);   
    final int numSources = Integer.parseInt(args[1]);
    final long mean = Long.parseLong(args[2]);
    final boolean uniformFlag = Boolean.parseBoolean(args[3]);
    final int queueDepth = Integer.parseInt(args[4]);
    final short experimentNumber = Short.parseShort(args[5]);
    
    StopWatch timer = new StopWatch();
    PacketSource pkt = new PacketSource(mean, numSources, experimentNumber);
    PaddedPrimitiveNonVolatile<Boolean> done = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFence = new PaddedPrimitive<Boolean>(false);
    
    final LamportQueue<Packet>[] queues = (LamportQueue<Packet>[]) new LamportQueue[numSources] ;
    for (int i = 0 ; i < numSources ; i++ ){
    	queues[i] = new LamportQueue<Packet>(queueDepth);
    }

    SerialQueuePacketWorker workerData = new SerialQueuePacketWorker(done, pkt, uniformFlag, numSources, queues);
  
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
    args[6] = "" + totalCount  + "";
    args[7] = "" + timer.getElapsedTime() + "";
    
  }
}

class ParallelFirewall {
  public static void main(String[] args) {
    final int numMilliseconds = Integer.parseInt(args[0]);     
    final int numSources = Integer.parseInt(args[1]);
    final long mean = Long.parseLong(args[2]);
    final boolean uniformFlag = Boolean.parseBoolean(args[3]);
    final int queueDepth = Integer.parseInt(args[4]);
    final short experimentNumber = Short.parseShort(args[5]);

    StopWatch timer = new StopWatch();
    PacketSource pkt = new PacketSource(mean, numSources, experimentNumber);
    
    
    final LamportQueue<Packet>[] queues = (LamportQueue<Packet>[]) new LamportQueue[numSources] ;
    for (int i = 0 ; i < numSources ; i++ ){
    	queues[i] = new LamportQueue<Packet>(queueDepth);
    }
    
    PaddedPrimitiveNonVolatile<Boolean> doneDispatcher = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceDispatcher = new PaddedPrimitive<Boolean>(false);
    PaddedPrimitiveNonVolatile<Boolean> doneWorkers = new PaddedPrimitiveNonVolatile<Boolean>(false);
    PaddedPrimitive<Boolean> memFenceWorkers = new PaddedPrimitive<Boolean>(false);
    
    final Dispatcher dispatcher = new Dispatcher(doneDispatcher, pkt, uniformFlag, numSources, queues);
    Thread dispatcherThread = new Thread(dispatcher);
    
    final ParallelPacketWorker[] workers = new ParallelPacketWorker[numSources] ;
    Thread[] workerThreads = new Thread[numSources];
    for (int i = 0 ; i < numSources ; i++ ){
    	workers[i] = new ParallelPacketWorker(doneWorkers,numSources,queues[i]);
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
    
    //Count num of total packets
    long totalCount = 0;
    for (int i = 0 ; i < numSources ; i++ ){
    	totalCount += workers[i].totalPackets;
    }
    
    System.out.println("count: " + totalCount);
    System.out.println("time: " + timer.getElapsedTime());
    System.out.println(totalCount/timer.getElapsedTime() + " pkts / ms");
    args[6] = "" + totalCount  + "";
    args[7] = "" + timer.getElapsedTime() + "";
    
    // Output the statistics
  }
}
