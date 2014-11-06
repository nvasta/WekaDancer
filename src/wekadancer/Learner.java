package wekadancer;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

import org.dancer.PandaLib.Group;
import org.dancer.PandaLib.PervasiveService;

import weka.core.Instances;


/**
 * <p>The Learner class</p>
 * <p>
 * This class implement the operations of a thread that will read
 * the specified data, either from an '.anff' file or an InfluxDB
 * database and will call the Weka library to identify any possible
 * patterns.
 * </p>
 */
public class Learner implements Runnable {

	/**
	 * <p>The LEARNER_ALGORITHM structure</p>
	 * <p>
	 * The type of learning algorithms supported. Up to now only two
	 * learning algorithms have been integrated in the learner.
	 * <p>
	 * - JRip: The well known JRip algorithm.<br>
	 * - J48: The well known J48 algorithm.<br>
	 */
	public enum LEARNER_ALGORITHM {
		JRip(0), J48(1);
        private int type;

        private LEARNER_ALGORITHM(int type) {
        	this.type = type;
        }
	 };
	
	 
	//Class Variables
	private String db_host = "";			///< The InfluxDB host
	private String dbName = "";				///< The InfluxDB database name		
	private String series = "";				///< The examined series name
	private String arffName = "";			///< The '.arff' file is used
	private LEARNER_ALGORITHM algorithm;	///< The employed learning algorithm
	private Date from = null;				///< The start time-stamp
	private Date until = null;				///< The stop time-stamp
	
	 
	/**********************************************************************
	 * <p>The class constructor<p>
	 * <p>
	 * It requires the names of the database host, the database name,
	 * the ARFF file name, the learning algorithm, and the from and until
	 * dates that we'll be getting the data (data range).
	 * <p>
	 * @param db_host The database host to connect to
	 * @param db_name The database name to be queried
	 * @param arffName The name of the '.arff' file, if needed
	 * @param algorithm The name of the learning algorithm to be used
	 * @param from The start time-stamp of the learning procedure
	 * @param until The stop time-stamp of the learning procedure
	 **********************************************************************/
	public Learner(String db_host, 
					String db_name, 
					String series, 
					String arffName, 
					LEARNER_ALGORITHM algorithm, 
					Date from, 
					Date until) throws Exception
	{
		
		this.db_host = db_host;
		this.dbName = db_name;
		this.series = series;
		this.arffName = arffName;
		this.algorithm = algorithm;
		this.from = from;
		this.until = until;
	}
	

	/**********************************************************************
	 * <p>The run function</p>
	 * </p>
	 * This function connects to the database, obtains the data, and 
	 * calls the Weka library.
	 * </p>
	 **********************************************************************/
	public void run() {
		//Initialise and connect to the database
		PervasiveService ps = new PervasiveService();
		InfluxDBConn dbConn = new InfluxDBConn(db_host,dbName);
		dbConn.InfluxDBConnect();

		//Set the appropriate date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

		//Obtaining the headers/names of the attributes, the actions and devices
		ArrayList<String> headers = dbConn.getAttributeNames(series, 
															dateFormat.format(from), 
															dateFormat.format(until));
		ArrayList<String> devices = dbConn.getDistinctItems(series+".actions", 
															"device", 
															dateFormat.format(from), 
															dateFormat.format(until));
		ArrayList<String> actions = dbConn.getDistinctItems(series+".actions", 
															"action", 
															dateFormat.format(from), 
															dateFormat.format(until));

		//Don't forget the "DoNothing"
		actions.add(0, "DoNothing()");
		
		//Start for-loop for each device
		for (String device:devices){
			InfluxDBData response = dbConn.requestLearnData(series, 
															headers, 
															device, 
															dateFormat.format(from), 
															dateFormat.format(until));
			
			//Calls the Weka Library
			try {
				ps.addGroup(callWeka(device, 
									 response.headers, 
									 response.influx_data, 
									 actions));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//End for-loop
		
		//Print the output
		for(int i=0;i<ps.getNumOfGroups();i++)
			System.out.println(ps.getGroup(i).toString());
	}
	
	
	/**********************************************************************
	 * <p>The callWeka function</p>
	 * <p>
	 * This function loads the data to Weka and runs the  selected 
	 * learning algorithm.
	 * </p>
	 * @param module The module for which the learning will be run
	 * @param headers The names of the attributes to be examined
	 * @param data The data to be examined
	 * @param action The names of the actions to be mined
	 * @return rules
	 **********************************************************************/
	public Group callWeka(String module,
						  ArrayList<String> headers, 
						  TreeMap<Long, Vector<Double>> data, 
						  ArrayList<String> action) throws Exception
	{
		//Initialise local variables
		Weka wk = null;
		Group rules = null;
		
		//Run with data from influxDB
		if (arffName == null)
			wk = new Weka();
		//Run with data loaded in an ARFF file
		else
			wk = new Weka(arffName);

		//Create the learning data format
		Instances dataInstances = wk.readData(headers, data, action);

		switch(algorithm){
			case JRip: 
				rules = wk.JRip(module, dataInstances); 
				break;
			case J48: 
				rules = wk.J48(module, dataInstances);
				break;
		}
		
		//Return the acquired rules
		return rules;
	}
}