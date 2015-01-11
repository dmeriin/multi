
/*String[][] test1 = new String[][]
				{{TIME_TO_RUN,"1","0"},{TIME_TO_RUN,"1","1"},{TIME_TO_RUN,"1","4"},{TIME_TO_RUN,"1","5"}};
		
		SerialCounter.main(new String[] {TIME_TO_RUN});
		for (int i =0 ; i < 4 ; i ++){
			ParallelCounter.main(test1[i]);
		}
		
		////////////// END TEST 1 ////////////////////
		
		
		// Fine tune backoff first 
		ParallelCounter.main( new String[]{TIME_TO_RUN,"32","1"});
		
		
		String[][] test2 = new String[][]
				{{TIME_TO_RUN,"1","0"},{TIME_TO_RUN,"1","1"},{TIME_TO_RUN,"1","4"},{TIME_TO_RUN,"1","5"},
				 {TIME_TO_RUN,"8","0"},{TIME_TO_RUN,"8","1"},{TIME_TO_RUN,"8","4"},{TIME_TO_RUN,"8","5"},
				 {TIME_TO_RUN,"64","0"},{TIME_TO_RUN,"64","1"},{TIME_TO_RUN,"64","4"},{TIME_TO_RUN,"64","5"}};
		
		
		for (int i =0 ; i < 12 ; i ++){
			ParallelCounter.main(test2[i]);
		}
		////////////// END TEST 2 ////////////////////
		
		
		String[][] test3 = new String[][]
				{ {TIME_TO_RUN,"4","0"},{TIME_TO_RUN,"4","1"},{TIME_TO_RUN,"4","4"},{TIME_TO_RUN,"4","5"}};
		
		
		for (int i =0 ; i < 4 ; i ++){
			ParallelCounter.main(test3[i]);
		}
		
		//////////////END TEST 3 ////////////////////
		
		
		///////////// Packet Test ////////////
	
		
		
		String[][] test4 = new String[][]
				{ 
				// W = 25 , S= Lock Free 
				{TIME_TO_RUN,"1","25","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","25","1","",DEPTH,"1","0"},{TIME_TO_RUN,"1","25","1","",DEPTH,"4","0"},{TIME_TO_RUN,"1","25","1","",DEPTH,"5","0"}, 
				// W = 25 , S= HomeQueue
				{TIME_TO_RUN,"1","25","1","",DEPTH,"0","1"} ,{TIME_TO_RUN,"1","25","1","",DEPTH,"1","1"},{TIME_TO_RUN,"1","25","1","",DEPTH,"4","1"},{TIME_TO_RUN,"1","25","1","",DEPTH,"5","1"},
				// W = 200 , S= Lock Free 
				{TIME_TO_RUN,"1","200","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","200","1","",DEPTH,"1","0"},{TIME_TO_RUN,"1","200","1","",DEPTH,"4","0"},{TIME_TO_RUN,"1","200","1","",DEPTH,"5","0"},
				// W = 200 , S= HomeQueue
				{TIME_TO_RUN,"1","200","1","",DEPTH,"0","1"} ,{TIME_TO_RUN,"1","200","1","",DEPTH,"1","1"},{TIME_TO_RUN,"1","200","1","",DEPTH,"4","1"},{TIME_TO_RUN,"1","200","1","",DEPTH,"5","1"},
				// W = 800 , S= Lock Free 
				{TIME_TO_RUN,"1","800","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","800","1","",DEPTH,"1","0"},{TIME_TO_RUN,"1","800","1","",DEPTH,"4","0"},{TIME_TO_RUN,"1","800","1","",DEPTH,"5","0"},
				// W = 800 , S= HomeQueue 
				{TIME_TO_RUN,"1","800","1","",DEPTH,"0","1"} ,{TIME_TO_RUN,"1","800","1","",DEPTH,"1","1"},{TIME_TO_RUN,"1","800","1","",DEPTH,"4","1"},{TIME_TO_RUN,"1","800","1","",DEPTH,"5","1"},
				
				};
		
		
		for (int i =0 ; i < test4.length ; i ++){
			test4[i][4] = "" + i + "";
			ParallelPacket.main(test4[i]);
		}
		
		//////////////END TEST 4 ////////////////////
		
		
		String[][] test5s = new String[][]
			{ 
			// W = 1000 
			{TIME_TO_RUN,"1","1000","1",""} ,{TIME_TO_RUN,"4","1000","1",""},{TIME_TO_RUN,"10","1000","1",""}, 
			// W = 6000 
			{TIME_TO_RUN,"1","6000","1",""} ,{TIME_TO_RUN,"4","6000","1",""},{TIME_TO_RUN,"10","6000","1",""},
			
			};
		
		
		String[][] test5p = new String[][]
				{ 
				// W = 1000 , n = 1 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"1","1000","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","1000","1","",DEPTH,"0","2"},{TIME_TO_RUN,"1","1000","1","",DEPTH,"0","3"},
				// W = 1000 , n = 1 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"1","1000","1","",DEPTH,"1","0"} ,{TIME_TO_RUN,"1","1000","1","",DEPTH,"1","2"},{TIME_TO_RUN,"1","1000","1","",DEPTH,"1","3"},
				// W = 1000 , n = 4 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"4","1000","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"4","1000","1","",DEPTH,"0","2"},{TIME_TO_RUN,"4","1000","1","",DEPTH,"0","3"},
				// W = 1000 , n = 4 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"4","1000","1","",DEPTH,"1","0"} ,{TIME_TO_RUN,"4","1000","1","",DEPTH,"1","2"},{TIME_TO_RUN,"4","1000","1","",DEPTH,"1","3"},
				// W = 1000 , n = 10 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"10","1000","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"10","1000","1","",DEPTH,"0","2"},{TIME_TO_RUN,"10","1000","1","",DEPTH,"0","3"},
				// W = 1000 , n = 10 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"10","1000","1","",DEPTH,"1","0"} ,{TIME_TO_RUN,"10","1000","1","",DEPTH,"1","2"},{TIME_TO_RUN,"10","1000","1","",DEPTH,"1","3"},
				
				// W = 6000 , n = 1 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"1","6000","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","6000","1","",DEPTH,"0","2"},{TIME_TO_RUN,"1","6000","1","",DEPTH,"0","3"},
				// W = 6000 , n = 1 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"1","6000","1","",DEPTH,"1","0"} ,{TIME_TO_RUN,"1","6000","1","",DEPTH,"1","2"},{TIME_TO_RUN,"1","6000","1","",DEPTH,"1","3"},
				// W = 6000 , n = 4 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"4","6000","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"4","6000","1","",DEPTH,"0","2"},{TIME_TO_RUN,"4","6000","1","",DEPTH,"0","3"},
				// W = 6000 , n = 4 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"4","6000","1","",DEPTH,"1","0"} ,{TIME_TO_RUN,"4","6000","1","",DEPTH,"1","2"},{TIME_TO_RUN,"4","6000","1","",DEPTH,"1","3"},
				// W = 6000 , n = 10 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"10","6000","1","",DEPTH,"0","0"} ,{TIME_TO_RUN,"10","6000","1","",DEPTH,"0","2"},{TIME_TO_RUN,"10","6000","1","",DEPTH,"0","3"},
				// W = 6000 , n = 10 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"10","6000","1","",DEPTH,"1","0"} ,{TIME_TO_RUN,"10","6000","1","",DEPTH,"1","2"},{TIME_TO_RUN,"10","6000","1","",DEPTH,"1","3"},
				};
		
		System.out.println("Test 5 - Serial");
		for (int i =0 ; i < test5s.length ; i ++){
			test5s[i][4] = "" + i + "";
			SerialPacket.main(test5s[i]);
		}
		System.out.println("Test 5 - Parallel");
		for (int i =0 ; i < test5p.length ; i ++){
			test5p[i][4] = "" + i + "";
			ParallelPacket.main(test5p[i]);
		}
		
		//////////////END TEST 5 ////////////////////
		

		String[][] test6s = new String[][]
			{ 
			// W = 1000 
			{TIME_TO_RUN,"1","1000","0",""} ,{TIME_TO_RUN,"8","1000","0",""},{TIME_TO_RUN,"64","1000","0",""}, 
			// W = 6000 
			{TIME_TO_RUN,"1","6000","0",""} ,{TIME_TO_RUN,"8","6000","0",""},{TIME_TO_RUN,"64","6000","0",""},
			
			};
		
		
		String[][] test6p = new String[][]
				{ 
				// W = 1000 , n = 1 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"1","1000","0","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","1000","0","",DEPTH,"0","2"},{TIME_TO_RUN,"1","1000","0","",DEPTH,"0","3"},
				// W = 1000 , n = 1 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"1","1000","0","",DEPTH,"1","0"} ,{TIME_TO_RUN,"1","1000","0","",DEPTH,"1","2"},{TIME_TO_RUN,"1","1000","0","",DEPTH,"1","3"},
				// W = 1000 , n = 8 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"8","1000","0","",DEPTH,"0","0"} ,{TIME_TO_RUN,"8","1000","0","",DEPTH,"0","2"},{TIME_TO_RUN,"8","1000","0","",DEPTH,"0","3"},
				// W = 1000 , n = 8 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"8","1000","0","",DEPTH,"1","0"} ,{TIME_TO_RUN,"8","1000","0","",DEPTH,"1","2"},{TIME_TO_RUN,"8","1000","0","",DEPTH,"1","3"},
				// W = 1000 , n = 64 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"64","1000","0","",DEPTH,"0","0"} ,{TIME_TO_RUN,"64","1000","0","",DEPTH,"0","2"},{TIME_TO_RUN,"64","1000","0","",DEPTH,"0","3"},
				// W = 1000 , n = 64 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"64","1000","0","",DEPTH,"1","0"} ,{TIME_TO_RUN,"64","1000","0","",DEPTH,"1","2"},{TIME_TO_RUN,"64","1000","0","",DEPTH,"1","3"},
				
				// W = 6000 , n = 1 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"1","6000","0","",DEPTH,"0","0"} ,{TIME_TO_RUN,"1","6000","0","",DEPTH,"0","2"},{TIME_TO_RUN,"1","6000","0","",DEPTH,"0","3"},
				// W = 6000 , n = 1 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"1","6000","0","",DEPTH,"1","0"} ,{TIME_TO_RUN,"1","6000","0","",DEPTH,"1","2"},{TIME_TO_RUN,"1","6000","0","",DEPTH,"1","3"},
				// W = 6000 , n = 8 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"8","6000","0","",DEPTH,"0","0"} ,{TIME_TO_RUN,"8","6000","0","",DEPTH,"0","2"},{TIME_TO_RUN,"8","6000","0","",DEPTH,"0","3"},
				// W = 6000 , n = 8 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"8","6000","0","",DEPTH,"1","0"} ,{TIME_TO_RUN,"8","6000","0","",DEPTH,"1","2"},{TIME_TO_RUN,"8","6000","0","",DEPTH,"1","3"},
				// W = 6000 , n = 64 , L=TasLock(0), S = 0,2,3
				{TIME_TO_RUN,"64","6000","0","",DEPTH,"0","0"} ,{TIME_TO_RUN,"64","6000","0","",DEPTH,"0","2"},{TIME_TO_RUN,"64","6000","0","",DEPTH,"0","3"},
				// W = 6000 , n = 64 , L=BackOffLock(1), S = 0,2,3
				{TIME_TO_RUN,"64","6000","0","",DEPTH,"1","0"} ,{TIME_TO_RUN,"64","6000","0","",DEPTH,"1","2"},{TIME_TO_RUN,"64","6000","0","",DEPTH,"1","3"},
				};
		
		System.out.println("Test 6 - Serial");
		for (int i =0 ; i < test6s.length ; i ++){
			test6s[i][4] = "" + i + "";
			SerialPacket.main(test6s[i]);
		}
		System.out.println("Test 6 - Parallel");
		for (int i =0 ; i < test6p.length ; i ++){
			test6p[i][4] = "" + i + "";
			ParallelPacket.main(test6p[i]);
		}
		
		//////////////END TEST 6 ////////////////////
		 */
		 


public class MppRunner {
	
	static final String TIME_TO_RUN = "2000";
	static final String DEPTH = "8";
	static final int numRunTest = 1;
	
	public static void main(String[] args) {
		
		
	
	    
	    // Serial - {TIME_TO_RUN,fractionAdd,fractionRemove,hitRate,maxBucketSize,mean,initSize}
	    // Parallel  - {TIME_TO_RUN,fractionAdd,fractionRemove,hitRate,maxBucketSize,mean,initSize,numWorkers,tableType} 
	    // ParallelNoLoad - {TIME_TO_RUN,fractionAdd,fractionRemove,hitRate,maxBucketSize,mean,initSize,numWorkers} 
	    
		
		
		//SerialHashPacket.main(TestSerial[0]);
		
		
		
		//ParallelHashPacket_Drop.main(TestParallel_Drop[0]);
		
		
		
		//Test 1
		/*
		System.out.println("Test 1");
		
		String[][] TestParallel_Drop = new String[][] {{TIME_TO_RUN,"0.09","0.01","0.9","16","1","0","8"}};
		//ParallelHashPacket_Drop.main(TestParallel_Drop[0]);
		
		//Test 2
		
		String[][] TestSerial = new String[][] {
				{TIME_TO_RUN,"0.09","0.01","0.9","16","4000","0"},
				
				{TIME_TO_RUN,"0.45","0.05","0.9","16","4000","0"}
				};
		String[][] TestParallel = new String[][] {
				{TIME_TO_RUN,"0.09","0.01","0.9","16","4000","0","1","0"},
				{TIME_TO_RUN,"0.09","0.01","0.9","16","4000","0","1","1"},
				{TIME_TO_RUN,"0.09","0.01","0.9","16","4000","0","1","2"},
				{TIME_TO_RUN,"0.09","0.01","0.9","16","4000","0","1","3"},
				
				{TIME_TO_RUN,"0.45","0.05","0.9","16","4000","0","1","0"},
				{TIME_TO_RUN,"0.45","0.05","0.9","16","4000","0","1","1"},
				{TIME_TO_RUN,"0.45","0.05","0.9","16","4000","0","1","2"},
				{TIME_TO_RUN,"0.45","0.05","0.9","16","4000","0","1","3"},
				};
		
		System.out.println("Test 2");
		System.out.println("Mostly Reads");
		
		System.out.println("Serial");
		
		//SerialHashPacket.main(TestSerial[0]);
		
		System.out.println("Parallel");
		
		//ParallelHashPacket.main(TestParallel[0]);
		//ParallelHashPacket.main(TestParallel[1]);
		//ParallelHashPacket.main(TestParallel[2]);
		//ParallelHashPacket.main(TestParallel[3]);
		
		System.out.println("Heavy Writes");
		
		System.out.println("Serial");
		
		//SerialHashPacket.main(TestSerial[1]);
		
		System.out.println("Parallel");
		
		ParallelHashPacket.main(TestParallel[5]);
		//ParallelHashPacket.main(TestParallel[5]);
		//ParallelHashPacket.main(TestParallel[6]);
		//ParallelHashPacket.main(TestParallel[7]);
		*/
		// Test 3
		
		System.out.println("Test 3");
		
		String[][] TestSerial3 = new String[][] {
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0"}
				};
		String[][] TestParallel3 = new String[][] {
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","1","0"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","1","0"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","1","0"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","1","1"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","1","1"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","1","1"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","1","2"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","1","2"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","1","2"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","1","3"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","1","3"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","1","3"},
				// 4 threads
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","4","0"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","4","0"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","4","0"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","4","1"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","4","1"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","4","1"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","4","2"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","4","2"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","4","2"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","4","3"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","4","3"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","4","3"},

				// 10 threads
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","10","0"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","10","0"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","10","0"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","10","1"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","10","1"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","10","1"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","10","2"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","10","2"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","10","2"},
				
				{TIME_TO_RUN,"0.09","0.01","0.5","16","4000","0","10","3"},
				{TIME_TO_RUN,"0.09","0.01","0.75","16","4000","0","10","3"},
				{TIME_TO_RUN,"0.09","0.01","0.99","16","4000","0","10","3"},
				
				//Heavy Writes
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","1","0"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","1","0"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","1","0"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","1","1"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","1","1"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","1","1"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","1","2"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","1","2"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","1","2"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","1","3"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","1","3"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","1","3"},
				// 4 threads
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","4","0"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","4","0"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","4","0"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","4","1"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","4","1"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","4","1"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","4","2"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","4","2"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","4","2"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","4","3"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","4","3"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","4","3"},

				// 10 threads
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","10","0"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","10","0"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","10","0"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","10","1"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","10","1"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","10","1"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","10","2"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","10","2"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","10","2"},
				
				{TIME_TO_RUN,"0.45","0.05","0.5","16","4000","0","10","3"},
				{TIME_TO_RUN,"0.45","0.05","0.75","16","4000","0","10","3"},
				{TIME_TO_RUN,"0.45","0.05","0.99","16","4000","0","10","3"},
				};
		
		
		
		System.out.println("Mostly Reads");
		
		System.out.println("Serial");
		
		SerialHashPacket.main(TestSerial3[0]);
		SerialHashPacket.main(TestSerial3[1]);
		SerialHashPacket.main(TestSerial3[2]);
		
		System.out.println("Parallel");
		
		System.out.println("Thread 1 - table 0 ");
		
		ParallelHashPacket.main(TestParallel3[0]);
		ParallelHashPacket.main(TestParallel3[1]);
		ParallelHashPacket.main(TestParallel3[2]);
		
		System.out.println("Thread 1 - table 1 ");
		
		ParallelHashPacket.main(TestParallel3[3]);
		ParallelHashPacket.main(TestParallel3[4]);
		ParallelHashPacket.main(TestParallel3[5]);
		
		System.out.println("Thread 1 - table 2 ");
		
		ParallelHashPacket.main(TestParallel3[6]);
		ParallelHashPacket.main(TestParallel3[7]);
		ParallelHashPacket.main(TestParallel3[8]);
		
		System.out.println("Thread 1 - table 3 ");
		
		ParallelHashPacket.main(TestParallel3[9]);
		ParallelHashPacket.main(TestParallel3[10]);
		ParallelHashPacket.main(TestParallel3[11]);
		
		System.out.println("Thread 4 - table 0 ");
		
		ParallelHashPacket.main(TestParallel3[12]);
		ParallelHashPacket.main(TestParallel3[13]);
		ParallelHashPacket.main(TestParallel3[14]);
		
		System.out.println("Thread 4 - table 1 ");
		
		ParallelHashPacket.main(TestParallel3[15]);
		ParallelHashPacket.main(TestParallel3[16]);
		ParallelHashPacket.main(TestParallel3[17]);
		
		System.out.println("Thread 4 - table 2 ");
		
		ParallelHashPacket.main(TestParallel3[18]);
		ParallelHashPacket.main(TestParallel3[19]);
		ParallelHashPacket.main(TestParallel3[20]);
		
		System.out.println("Thread 4 - table 3 ");
		
		ParallelHashPacket.main(TestParallel3[21]);
		ParallelHashPacket.main(TestParallel3[22]);
		ParallelHashPacket.main(TestParallel3[23]);
		
		System.out.println("Thread 10 - table 0 ");
		
		ParallelHashPacket.main(TestParallel3[24]);
		ParallelHashPacket.main(TestParallel3[25]);
		ParallelHashPacket.main(TestParallel3[26]);
		
		System.out.println("Thread 10 - table 1 ");
		
		ParallelHashPacket.main(TestParallel3[27]);
		ParallelHashPacket.main(TestParallel3[28]);
		ParallelHashPacket.main(TestParallel3[29]);
		
		System.out.println("Thread 10 - table 2 ");
		
		ParallelHashPacket.main(TestParallel3[30]);
		ParallelHashPacket.main(TestParallel3[31]);
		ParallelHashPacket.main(TestParallel3[32]);
		
		System.out.println("Thread 10 - table 3 ");
		
		ParallelHashPacket.main(TestParallel3[33]);
		ParallelHashPacket.main(TestParallel3[34]);
		ParallelHashPacket.main(TestParallel3[35]);
		
		/*
		
		
		System.out.println("Heavy Writes");
		
		System.out.println("Serial");
		
		SerialHashPacket.main(TestSerial3[3]);
		SerialHashPacket.main(TestSerial3[4]);
		SerialHashPacket.main(TestSerial3[5]);
		
		System.out.println("Parallel");
		
		System.out.println("Thread 1 - table 0 ");
		
		ParallelHashPacket.main(TestParallel3[0+ 36 ]);
		ParallelHashPacket.main(TestParallel3[1+ 36 ]);
		ParallelHashPacket.main(TestParallel3[2+ 36 ]);
		
		System.out.println("Thread 1 - table 1 ");
		
		ParallelHashPacket.main(TestParallel3[3+ 36 ]);
		ParallelHashPacket.main(TestParallel3[4+ 36 ]);
		ParallelHashPacket.main(TestParallel3[5+ 36 ]);
		
		System.out.println("Thread 1 - table 2 ");
		
		ParallelHashPacket.main(TestParallel3[6+ 36 ]);
		ParallelHashPacket.main(TestParallel3[7+ 36 ]);
		ParallelHashPacket.main(TestParallel3[8+ 36 ]);
		
		System.out.println("Thread 1 - table 3 ");
		
		ParallelHashPacket.main(TestParallel3[9+ 36 ]);
		ParallelHashPacket.main(TestParallel3[10+ 36 ]);
		ParallelHashPacket.main(TestParallel3[11+ 36 ]);
		
		System.out.println("Thread 4 - table 0 ");
		
		ParallelHashPacket.main(TestParallel3[12+ 36 ]);
		ParallelHashPacket.main(TestParallel3[13+ 36 ]);
		ParallelHashPacket.main(TestParallel3[14+ 36 ]);
		
		System.out.println("Thread 4 - table 1 ");
		
		ParallelHashPacket.main(TestParallel3[15+ 36 ]);
		ParallelHashPacket.main(TestParallel3[16+ 36 ]);
		ParallelHashPacket.main(TestParallel3[17+ 36 ]);
		
		System.out.println("Thread 4 - table 2 ");
		
		ParallelHashPacket.main(TestParallel3[18+ 36 ]);
		ParallelHashPacket.main(TestParallel3[19+ 36 ]);
		ParallelHashPacket.main(TestParallel3[20+ 36 ]);
		
		System.out.println("Thread 4 - table 3 ");
		
		ParallelHashPacket.main(TestParallel3[21+ 36 ]);
		ParallelHashPacket.main(TestParallel3[22+ 36 ]);
		ParallelHashPacket.main(TestParallel3[23+ 36 ]);
		
		System.out.println("Thread 10 - table 0 ");
		
		ParallelHashPacket.main(TestParallel3[24+ 36 ]);
		ParallelHashPacket.main(TestParallel3[25+ 36 ]);
		ParallelHashPacket.main(TestParallel3[26+ 36 ]);
		
		System.out.println("Thread 10 - table 1 ");
		
		ParallelHashPacket.main(TestParallel3[27+ 36 ]);
		ParallelHashPacket.main(TestParallel3[28+ 36 ]);
		ParallelHashPacket.main(TestParallel3[29+ 36 ]);
		
		System.out.println("Thread 10 - table 2 ");
		
		ParallelHashPacket.main(TestParallel3[30+ 36 ]);
		ParallelHashPacket.main(TestParallel3[31+ 36 ]);
		ParallelHashPacket.main(TestParallel3[32+ 36 ]);
		
		System.out.println("Thread 10 - table 3 ");
		
		ParallelHashPacket.main(TestParallel3[33+ 36 ]);
		ParallelHashPacket.main(TestParallel3[34+ 36 ]);
		ParallelHashPacket.main(TestParallel3[35+ 36 ]);
		
		
		
		
		// Test 4
		
		System.out.println("Test 4");
		
		String[][] TestSerial4 = new String[][] {
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000"}
				};
		String[][] TestParallel4 = new String[][] {
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","1","0"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","1","1"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","1","2"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","1","3"},
				// 8 threads
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","8","0"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","8","1"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","8","2"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","8","3"},

				// 32 threads
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","32","0"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","32","1"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","32","2"},
				{TIME_TO_RUN,"0.25","0.25","0.5","16","4000","1000000","32","3"},
				
				
				};
		/*
		System.out.println("Serial");
		SerialHashPacket.main(TestSerial4[0]);
		
		System.out.println("Parallel");
		
		System.out.println("1 thread");
		
		ParallelHashPacket.main(TestParallel4[0]);
		ParallelHashPacket.main(TestParallel4[1]);
		ParallelHashPacket.main(TestParallel4[2]);
		ParallelHashPacket.main(TestParallel4[3]);
		
		System.out.println("8 thread");
		
		ParallelHashPacket.main(TestParallel4[4]);
		ParallelHashPacket.main(TestParallel4[5]);
		ParallelHashPacket.main(TestParallel4[6]);
		ParallelHashPacket.main(TestParallel4[7]);
		
		System.out.println("32 thread");
		
		ParallelHashPacket.main(TestParallel4[8]);
		ParallelHashPacket.main(TestParallel4[9]);
		ParallelHashPacket.main(TestParallel4[10]);*/
		//ParallelHashPacket.main(TestParallel4[11]);
	}
	
}
