package wekadancer;


import java.util.Date;
import wekadancer.Learner.LEARNER_ALGORITHM;


/**
 * <p>The WekaDancer Class</p>
 * <p>
 * This class just calls a main function that creates a learner
 * thread. This thread is then responsible for the remainder of the
 * learning procedure. The data provided in the thread can be in an
 * '.arff' file or through an InfluxDb connection.
 * <p>
 */
public class WekaDancer {
	
	/**********************************************************************
	 * <p>The main method of the program.</p> 
	 * @param args The arguments provided in the main.
	 **********************************************************************/
	public static void main(String[] args) {
		//Initialize the variables
		String db_host = new String("localhost");
		String dbName = new String("default");
		String series = new String("default");
		String arffName = null;								//format: name.arff. 
															//Leave empty if loading 
															//directly from influxDB
		LEARNER_ALGORITHM algorithm = LEARNER_ALGORITHM.J48;//JRip (RIPPER), //J48 (C4.5)
		Date from = new Date(0);	
		Date until = new Date(System.currentTimeMillis());
		
		//=============== Parsing Main Arguments ===============//
		// Check all arguments that start with a "-"
		int i = 0;
		while (i < args.length && args[i].startsWith("-")) {
			String arg = args[i++];

			// If the argument specifies the InfluxDB host name
			if (arg.equals("-h")) {
				db_host = args[i++];
			}
			// If the argument specifies the InfluxDB database name
			else if (arg.equals("-d")) {
				dbName = args[i++];
			}
			// If the argument specifies the InfluxDB series name
			else if (arg.equals("-s")) {
				series = args[i++];
			}
			// If the argument specifies the '.arff' file name
			else if (arg.equals("-f")) {
				series = args[i++];
			}
			// If the argument specifies the learning algorithm to be used
			else if (arg.equals("-a")) {
				algorithm = LEARNER_ALGORITHM.valueOf(args[i++]);
			}
			// If the argument specifies the start time
			else if (arg.equals("-b")) {
				//times 1000 to get milliseconds
				from =  new Date(new Long(args[i++])*1000);
			}
			// If the argument specifies the stop time
			else if (arg.equals("-e")) {
				//times 1000 to get milliseconds
				until =  new Date(new Long(args[i++])*1000);
			}
			else {
				System.err.println("Usage: WekaDancer [-h <InfluxDB_host>] " + 
													 "[-d <InfluxDB_database>]" +
													 "[-s <InfluxDB_series>]" +
													 "[-f <ARFF_filename>]" +
													 "[-a <algorithm>]" + 
													 "[-b <begin_timestamp>]" +
													 "[-e <end_timestamp>]");
				return;
			}
		}		
		//======================================================//
		
		//Initialize the thread
		Thread th = null;
		try {
			th = new Thread(new Learner(db_host, 
										dbName, 
										series, 
										arffName, 
										algorithm, 
										from, 
										until));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Run the thread
		th.start();
		
		//Wait until it finishes
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
