



public class MppRunner {
	
	static final String TIME_TO_RUN = "2000";
	static final String DEPTH = "8";


	// argsSerial args
	// 0 = time to run
	// 1 = num sources
	// 2 = mean - expected work
	// 3 = uniform flag
	// 4 = experimentNumber
	// 5 == totalCount (out)
	// 6 == time (out)
	
	// argsSerialQueue/parallel args
	// 0 = time to run
	// 1 = num sources
	// 2 = mean - expected work
	// 3 = uniform flag
	// 4 = depth
	// 5 = experimentNumber
	// 6 == totalCount (out)
	// 7 == time (out)
	
	public static void test(int testNum, String[] argsWith7, String args1Name,
										 String[] argsWith8, String args2Name,
										 String[] arrNumSources,String[] arrExcpectedWork){
		
		System.out.println(" Test " + testNum + " : \n\n");
		
		for (int i = 0 ; i < arrNumSources.length ; i ++){
			for (int k = 0 ; k < arrExcpectedWork.length ; k ++){
				long totalCount_args7 = 0, time_args7 = 0,totalCountAvg_args7 = 0, timeAvg_args7 = 0;
				long totalCount_args8 = 0, time_args8 = 0, totalCountAvg_args8 = 0, timeAvg_args8 = 0;
				
				if (argsWith7 != null){
					argsWith7[1] = arrNumSources[i]; // Num sources
					argsWith7[2] = arrExcpectedWork[k]; // mean - expected work
				}
				
				argsWith8[1] = arrNumSources[i]; // Num sources
				argsWith8[2] = arrExcpectedWork[k]; // mean - expected work
								
				for (int j = 0 ; j < 5 ; j ++){
					argsWith8[4] = DEPTH;
					
					if (argsWith7 != null)
						argsWith7[4] = 	 "" + (j + 1) + "";   // Experiment number
					
					argsWith8[5] = "" + (j + 1) + "";	// Experiment number
					
					System.out.println("\nRun " + (j + 1));
					if (argsWith7 != null){
						System.out.println("Serial : ");
						SerialFirewall.main(argsWith7);
						totalCount_args7+= Long.parseLong(argsWith7[5]);
						time_args7+= Long.parseLong(argsWith7[6]);
					}
					
					
					System.out.println(args2Name +": ");
					if (args2Name == "Parallel"){
						ParallelFirewall.main(argsWith8);
					}
					else{
						SerialQueueFirewall.main(argsWith8);
					}
					totalCount_args8+= Long.parseLong(argsWith8[6]);
					time_args8+= Long.parseLong(argsWith8[7]);
					
					
				}
				
				totalCountAvg_args7 = totalCount_args7 / 5;
				timeAvg_args7		= time_args7 / 5 ;
				totalCountAvg_args8 = totalCount_args8 / 5;
				timeAvg_args8		= time_args8 / 5 ;
				
				System.out.println("\n******* Summary  Case " + (i*arrExcpectedWork.length + k + 1) + ". n = " +  arrNumSources[i] + " W = " + arrExcpectedWork[k] + "**************");
				if (argsWith7 != null)
					System.out.println( args1Name + ": " + "Total Count :  " + totalCountAvg_args7 +  " . Total Time = " + timeAvg_args7 +  " . pkts / ms " +  totalCountAvg_args7 / timeAvg_args7 );
				System.out.println( args2Name + ": " + "Total Count :  " + totalCountAvg_args8 +  " . Total Time = " + timeAvg_args8 +  " . pkts / ms " +  totalCountAvg_args8 / timeAvg_args8 );
			}
		}
		
		
		
	}
	
	public static void test1(){
		
		String[] argsSerial = 		{TIME_TO_RUN,"","","1","","",""};
		
		String[] argsSerialQueue = 	{TIME_TO_RUN,"","","1","","","",""};
		
		String[] arrNumSources = {"1","4","10"};
		String[] arrExcpectedWork = {"25","200","800"};
		
		test(1, argsSerial, "Serial",
				argsSerialQueue, "SerialQueue",
				  arrNumSources, arrExcpectedWork);
	
	}
	
	public static void test2(){
					String[] argsSerial = 		null;
					
					String[] argsParallel = 	{TIME_TO_RUN,"","","1","","","",""};
					
					String[] arrNumSources = {"1","4","10"};
					String[] arrExcpectedWork = {"1"};
					
					test(2, argsSerial, "None",
							argsParallel, "Parallel",
				  arrNumSources, arrExcpectedWork);
		}
	
	public static void test3(){
		
		String[] argsSerial = 		{TIME_TO_RUN,"","","1","","",""};
		
		String[] argsParallel = 	{TIME_TO_RUN,"","","1","","","",""};
		
		String[] arrNumSources = {"1","4","10"};
		String[] arrExcpectedWork = {"1000","6000"};
		
		test(3, argsSerial, "Serial",
				argsParallel, "Parallel",
	  arrNumSources, arrExcpectedWork);
		
	}
	
public static void test4(){
		
		String[] argsSerial = 		{TIME_TO_RUN,"","","1","","",""};
		
		String[] argsParallel = 	{TIME_TO_RUN,"","","1","","","",""};
		
		String[] arrNumSources = {"1","8","64"};
		String[] arrExcpectedWork = {"1000","6000"};
		
		test(4, argsSerial, "Serial",
				argsParallel, "Parallel",
	  arrNumSources, arrExcpectedWork);
		
	}

	
	public static void main(String[] args) {
		
		SerialFirewall1.main(null);
	}
	
}
