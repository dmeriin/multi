
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
	    
		String[][] TestSerial = new String[][] {{TIME_TO_RUN,"0.09","0.01","0.9","8","4000","0"}};
		
		//SerialHashPacket.main(TestSerial[0]);
		
		String[][] TestParallel_Drop = new String[][] {{TIME_TO_RUN,"0.09","0.01","0.9","8","4000","0","4"}};
		
		//ParallelHashPacket_Drop.main(TestParallel_Drop[0]);
		
		String[][] TestParallel = new String[][] {{TIME_TO_RUN,"0.09","0.01","0.9","8","4000","0","4","0"}};
		
		ParallelHashPacket.main(TestParallel[0]);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
