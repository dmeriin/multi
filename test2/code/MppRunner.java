
public class MppRunner {
	public static void main(String[] args) {
		String[][] Exp1 = {{"2000", "1",  "25",  "1", "4"},
			  	   {"2000", "1",  "200", "1", "4"},
				   {"2000", "1",  "800", "1", "4"},
			           {"2000", "4",  "25",  "1", "4"},
				   {"2000", "4",  "200", "1", "4"},
				   {"2000", "4",  "800", "1", "4"},
				   {"2000", "10", "25",  "1", "4"},
				   {"2000", "10", "200", "1", "4"},
				   {"2000", "10", "800", "1", "4"}};

		String[][] ExpQ1 = {{"2000", "1",  "25",  "1", "8", "4"},
			  	   {"2000", "1",  "200", "1", "8", "4"},
				   {"2000", "1",  "800", "1", "8", "4"},
			           {"2000", "4",  "25",  "1", "8", "4"},
				   {"2000", "4",  "200", "1", "8", "4"},
				   {"2000", "4",  "800", "1", "8", "4"},
				   {"2000", "10", "25",  "1", "8", "4"},
				   {"2000", "10", "200", "1", "8", "4"},
				   {"2000", "10", "800", "1", "8", "4"}};
		
		String[][] Exp2 = {{"2000", "1",  "1",  "1", "8", "5"},
				   {"2000", "4",  "1",  "1", "8", "5"},
		  		   {"2000", "10", "1",  "1", "8", "5"}};

		String[][] Exp3 = {{"2000", "1",  "1000", "1", "6"},
	  	 		   {"2000", "1",  "6000", "1", "6"},
		 		   {"2000", "4",  "1000", "1", "6"},
				   {"2000", "4",  "6000", "1", "6"},
				   {"2000", "10", "1000", "1", "6"},
				   {"2000", "10", "6000", "1", "6"}};
		
		String[][] ExpP3 = {{"2000", "1",  "1000", "1", "8", "6"},
	  	 		   {"2000", "1",  "6000", "1", "8", "6"},
		 		   {"2000", "4",  "1000", "1", "8", "6"},
				   {"2000", "4",  "6000", "1", "8", "6"},
				   {"2000", "10", "1000", "1", "8", "6"},
				   {"2000", "10", "6000", "1", "8", "6"}};
		
		String[][] Exp4 = {{"2000", "1",  "1000", "0", "7"},
		   		   {"2000", "1",  "6000", "0", "7"},
		   		   {"2000", "8",  "1000", "0", "7"},
		   		   {"2000", "8",  "6000", "0", "7"},
		   		   {"2000", "64", "1000", "0", "7"},
		   		   {"2000", "64", "6000", "0", "7"}};

		String[][] ExpP4 = {{"2000", "1", "1000", "0", "8", "7"},
		   		   {"2000", "1",  "6000", "0", "8", "7"},
		   		   {"2000", "8",  "1000", "0", "8", "7"},
		   		   {"2000", "8",  "6000", "0", "8", "7"},
		   		   {"2000", "64", "1000", "0", "8", "7"},
		   		   {"2000", "64", "6000", "0", "8", "7"}};


			///test 1

			
			//SerialFirewall.main(new String[]{"2000", "1",  "25",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "1",  "25",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "1",  "200",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "1",  "200",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "1",  "800",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "1",  "800",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "4",  "25",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "4",  "25",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "4",  "200",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "4",  "200",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "4",  "800",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "4",  "800",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "10",  "25",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "10",  "25",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "10",  "200",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "10",  "200",  "1", "8", "4"});

			//SerialFirewall.main(new String[]{"2000", "10",  "800",  "1", "4"});
			//SerialQueueFirewall.main(new String[]{"2000", "10",  "800",  "1", "8", "4"});

			//test2
			
			ParallelFirewall.main(new String[]{"2000", "1",  "1",  "1", "8", "5"});
			//ParallelFirewall.main(new String[]{"2000", "4",  "1",  "1", "8", "5"});
			//ParallelFirewall.main(new String[]{"2000", "10",  "1",  "1", "8", "5"});

			//test3


			//SerialFirewall.main(new String[]{"2000", "1",  "1000", "1", "6"});
			//ParallelFirewall.main(new String[]{"2000", "1",  "1000", "1", "8", "6"});

			//SerialFirewall.main(new String[]{"2000", "1",  "6000", "1", "6"});
			//ParallelFirewall.main(new String[]{"2000", "1",  "6000", "1", "8", "6"});

			//SerialFirewall.main(new String[]{"2000", "4",  "1000", "1", "6"});
			//ParallelFirewall.main(new String[]{"2000", "4",  "1000", "1", "8", "6"});

			//SerialFirewall.main(new String[]{"2000", "4",  "6000", "1", "6"});
			//ParallelFirewall.main(new String[]{"2000", "4",  "6000", "1", "8", "6"});

			//SerialFirewall.main(new String[]{"2000", "10",  "1000", "1", "6"});
			//ParallelFirewall.main(new String[]{"2000", "10",  "1000", "1", "8", "6"});

			//SerialFirewall.main(new String[]{"2000", "10",  "6000", "1", "6"});
			//ParallelFirewall.main(new String[]{"2000", "10",  "6000", "1", "8", "6"});

			//TEST4
			
			//SerialFirewall.main(new String[]{"2000", "1",  "1000", "0", "7"});
			//ParallelFirewall.main(new String[]{"2000", "1",  "1000", "0", "8", "7"});

			//SerialFirewall.main(new String[]{"2000", "1",  "6000", "0", "7"});
			//ParallelFirewall.main(new String[]{"2000", "1",  "6000", "0", "8", "7"});

			//SerialFirewall.main(new String[]{"2000", "8",  "1000", "0", "7"});
			//ParallelFirewall.main(new String[]{"2000", "8",  "1000", "0", "8", "7"});

			//SerialFirewall.main(new String[]{"2000", "8",  "6000", "0", "7"});
			//ParallelFirewall.main(new String[]{"2000", "8",  "6000", "0", "8", "7"});

			//SerialFirewall.main(new String[]{"2000", "64",  "1000", "0", "7"});
			//ParallelFirewall.main(new String[]{"2000", "64",  "1000", "0", "8", "7"});

			//SerialFirewall.main(new String[]{"2000", "64",  "6000", "0", "7"});
			//ParallelFirewall.main(new String[]{"2000", "64",  "6000", "0", "8", "7"});
			
			
		//}
	}
}
