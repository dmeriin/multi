



public class MppRunner {
	
	static final String TIME_TO_RUN = "2000";
	static final String DEPTH = "8";
	static final int numRunTest = 1;
	
	public static void main(String[] args) {
		
		String[][] test1 = new String[][]
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
				 {TIME_TO_RUN,"8","0"},{TIME_TO_RUN,"8","1"},{TIME_TO_RUN,"1","4"},{TIME_TO_RUN,"8","5"},
				 {TIME_TO_RUN,"64","0"},{TIME_TO_RUN,"64","1"},{TIME_TO_RUN,"64","4"},{TIME_TO_RUN,"64","5"}};
		
		
		for (int i =0 ; i < 12 ; i ++){
			ParallelCounter.main(test2[i]);
		}
		
		
		
	}
	
}
