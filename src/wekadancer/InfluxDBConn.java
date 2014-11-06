package wekadancer;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;


/**
 * <p>The InfluxDBConn Class</p>
 * <p>
 * This class is able to initialise a connection to an InfluxDB database
 * host and perform queries on the sensor data. It then it able to retrieve
 * these data in the form of a hash-map with the time-stamp as a key.
 * </p>
 */
public class InfluxDBConn {
	
	//The class variables for the InfluxDB 
	InfluxDB influxDB = null;
	String dbHost = "serusl11";
	String dbName = "DANCER";
	
	
	/**********************************************************************
	 * <p>The class constructor</p>
	 * <p>
	 * It requires the names of the database host and the database name,
	 * in order to initialise the class.
	 * </p>
	 * @param db_host The host name of the database server to connect to
	 * @param db_name The name of the database that will be read
	 **********************************************************************/
	public InfluxDBConn(String db_host, String db_name) {
		dbHost = db_host;
		dbName = db_name;
	}

	
	 /**********************************************************************
     * <p>The InfluxDBConnect function</p>
     * 
     * <p>This function connect to the main InfluxDB repository, to allow
     * the following queries to take place.<p>
     * 
     **********************************************************************/
    public void InfluxDBConnect() {
    	//Create a new connection
    	influxDB = InfluxDBFactory.connect("http://"+dbHost+":8086", "root", "root");  	
	}
	
    
    /**********************************************************************
     * <p>The getDistinctItems function</p>
     * <p>
     * This function queries the InfluxDB database to identify the 
     * distinct items that are contained in the selected tables. Notice 
     * that the time duration of the query should also be specified.
     * </p>
     * @param tables The tables to be queried
     * @param item The item to be examined for actions
     * @param from The start time of the query
     * @param until The stop time of the query
     * @return The Distinct Actions discovered
     * 
     **********************************************************************/
    public ArrayList<String> getDistinctItems(String tables, String item,  String from, String until) {
    	//Initialise an array list for the distinct actions
    	ArrayList<String> distItems = new ArrayList<String>();
    	
    	//Query the database
    	List<Serie> distinct = influxDB.query(dbName, 
    			"SELECT DISTINCT(" + item + ") FROM " + tables + 
    			" where time>'" + from +".000' AND time<'" + until +".000';", 
    			TimeUnit.SECONDS);
    	
    	//Get the resulting elements
		for (Serie element : distinct) {
			List<Map<String,Object>> rows = element.getRows();
			
			//For each Series, get the rows, containing the distinct actions
			for (Map<String,Object> row : rows) {
				distItems.add(row.get("distinct").toString());
			}
		}
		
		//Return the outcome
		return distItems;
    }
    
    
    /**********************************************************************
     * <p>The getAttributeNames function<p>
     * <p>
     * It returns the available series names for a given time frame. 
     * These can be used as attributes for a learning algorithm.
     * </p>
     * @param table The table (series) that will be queried
     * @param from The start time-stamp of the query
     * @param until The stop time-stamp of the query
     * @return Attribute Names
     **********************************************************************/
    public ArrayList<String> getAttributeNames(String table, String from, String until) {
    	//Initialise an array list for the distinct actions
    	ArrayList<String> names = new ArrayList<String>();
    	
    	//Query the database
    	List<Serie> result = influxDB.query(dbName, 
    			"SELECT * FROM " + table +
    			" limit 1;", 
    			TimeUnit.SECONDS);
    	
    	//Get the resulting elements
		for (Serie element : result) {
			String[] columns = element.getColumns();
			
			//For each Series, get the rows, containing the distinct actions
			for (String col: columns) {
				//For all apart from time and sequence number
				if(col.compareTo("time")!=0 && col.compareTo("sequence_number")!=0) {
					names.add(col);
				}
			}				
		}
		
		//Return the outcome
		return names;
    }

    
    /**********************************************************************
     * <p>The requestData function</p>
     * <p>
     * This function queries the InfluxDB database to received all the 
     * series databases contained, that belong to a specific DANCER
     * client. It then returns the results in the form of a TreeMap that
     * has as a key the time-stamps of the samples, while the remaining
     * columns are the individual values. It uses the series2map function
     * in order to translate the data in a friendly to Weka format,
     * adding two extra columns, the status and action over the 
     * queried device.
     * </p>
     * @param table The table (series) that will be queried
     * @param attrs The attributes to be taken into consideration
     * @param from The start time-stamp of the query
     * @param until The stop time-stamp of the query
     * @return InfluxDBData
     **********************************************************************/
	public InfluxDBData requestLearnData(String table, 
										ArrayList<String> attrs,
										String device,
										String from, 
										String until) 
	{
		//Create the query for the data
		String query = new String("select");
		for(int i=0; i<attrs.size(); i++) {
			query += " mean(" + attrs.get(i) + ") as " + attrs.get(i);
			
			if(i<attrs.size()-1)
				query += ",";
		}
		query += " from " + table;
		query += " where time>'" +from + ".000' AND time<'" +until +".000' group by time(30s);";
		
		//Create the query for the actions
		String act_query = new String("select");
		act_query += " *";
		act_query += " from " + table + ".actions";
		act_query += " where device='" + device + "' AND";
		//act_query += " where";
		act_query += " time>'" +from + ".000' AND time<'" +until +".000'";

		//Get the distinct actions and add the DoNothing one
		ArrayList<String> classes = getDistinctItems(table+".actions", "action",  from, until);
		classes.add(0, "DoNothing()");
				
        //Query the database for the attributes and actions
		List<Serie> result = influxDB.query(dbName, query, TimeUnit.SECONDS);
		List<Serie> actions = influxDB.query(dbName, act_query, TimeUnit.SECONDS);
		
		//Create the InfluxDBData reply
		InfluxDBData response = new InfluxDBData(getKeySet(result), 
												 series2map(result, actions, classes));
		
		//Return the translated data
		return response;
	}
	
	
	/**********************************************************************
	 * <p>The getKeySet function</p>
	 * <p>
	 * Returns the key set of the InfluxDB query result in an array list.
	 * This is used because the order of the returned results might not
	 * necessarily follow the specified order. Therefore, it's a good 
	 * idea to double check.
	 * </p> 
	 * @param result
	 * @return The array list of the result's key set.
	 */
	public ArrayList<String> getKeySet(List<Serie> result) {
		if(result.size()>0) {
			//Get the first row and remove the time if exists
			Map<String,Object> row = result.get(0).getRows().get(0);
			row.remove("time"); 
			
			//Return the rest of the key-set 
			return new ArrayList<String>(row.keySet());
		}
		
		return null;
	}


	/**********************************************************************
	 * <p>The series2map function</p>
	 * <p>
	 * This function translates the incoming series data from InfluxDB 
	 * into a hash-map with UNIX time-stamps used as keys. It basically
	 * parses the influxData input and add it to the final hash-map. Then,
	 * according to the provided actionData, it add two more columns in the
	 * output hash-map, the status of the examined device and the action
	 * indexes, when they were performed. To do so, it finds the time-stamps
	 * of the actions and add them in the hash-map if they coincide with
	 * an entry (that was taken by the sensors a bit earlier). If another
	 * action was already added in that time-stamp, the new action will
	 * be inserted with its original time-stamp, using the same sensor
	 * data that are included in the previous entry. The class parameter
	 * is used to identify the index of each action on the array list
	 * and this is inserted in the final hash-map as a double. Notice
	 * that the status of each device that is also provided in the hash-
	 * map, is also in the form of indexes on the given classes. The
	 * function cannot know the actual status and it is assumed that there
	 * can be a one-to-one correlations between the status and the last
	 * performed action.
	 * </p>
	 * @param influxData The sensor data to be examined
	 * @param actionData The action that were performed and related info 
	 * @param classes The types of actions that will be considered
	 * @return TreeMap
	 **********************************************************************/
	private TreeMap<Long,Vector<Double>> series2map(List<Serie> influxData,
													List<Serie> actionData,
													ArrayList<String> classes)
	{   
        //Initialise a hash-map to hold all the data received
        TreeMap<Long,Vector<Double>> data = new TreeMap<Long,Vector<Double>>();
        
        //===================================================================//
        //                      Populate the Tree-Map                        //
        //===================================================================//        
		//Get the resulting elements to add row by row
		for (int i=0; i<influxData.size(); i++) {
			//Get the series element
			Serie element = influxData.get(i);

			//Get all the rows of this series
			List<Map<String,Object>> rows = element.getRows();

			//For each Series, get the rows
			for (Map<String,Object> row : rows) {
				//Get the time-stamp of this series point
				Long timestamp = Double.valueOf(row.get("time").toString()).longValue();				
				row.remove("time"); /// Fucking SDK8

				//Now that we have the row add the element
				List<Double> temp = Arrays.asList(row.
												values().
												toArray(new Double[row.values().
												size()]));
				
				data.put(timestamp, new Vector<Double>(temp));
			}
		}
		//===================================================================//

		//===================================================================//
        //                Adding the status and the actions                  //
        //===================================================================//
		//Get the resulting elements to add row by row
		for (int i=0; i<actionData.size(); i++) {
			//Get the series element
			Serie element = actionData.get(i);
			
			//Get the column of the series
			Vector<String> colums = new Vector<String>
									( Arrays.asList(element.getColumns()) );
			
			
			//Check the series columns
			for (String col : colums) {
				
				//If there is an actions column
				if(col.compareTo("action")==0) {

					//Initialise the operations variables 
					double status = 0;					/// (Pointing to System.DoNothing())
					Long prev_timestamp = new Long(0); 	/// Used for pointing the previous 
														/// action's time-stamp
					
					//Get all the rows of this series
					List<Map<String,Object>> rows = element.getRows();
					
					//Go through all the rows checking for the actions
					//Notice the rows start from the newest one
					for(int r=rows.size()-1;r>=0;r--) {

						//Get only the action row of the action series
						Map<String,Object> row = rows.get(r);
						
						//Get the time-stamp of this series point
						Long timestamp = Double.valueOf(row.get("time").toString()).longValue();
						timestamp = data.lowerKey(timestamp+1);
						
						//Get the sub-map contained in the examined time-stamps
						SortedMap<Long,Vector<Double>> submap = data.subMap(prev_timestamp+1, timestamp+1);

						/// Now iterate through it and add the status and action rows ///
						Iterator<Entry<Long, Vector<Double>>> it = submap.entrySet().iterator();
			            while (it.hasNext()) {	
			            	Map.Entry<Long, Vector<Double>> pairs = (Map.Entry<Long, Vector<Double>>)it.next();
			            		
			            	//Add the current status
			            	submap.get(pairs.getKey()).add(status);
			            	
			            	//Add the index of the action performed at the correct time
			            	if(pairs.getKey()==timestamp) {
			            		submap.get(pairs.getKey()).add(new Double
			            									  (classes.indexOf
			            									  (row.get("action"))));
			            		
			            		//Update the status of the device
			            		status = new Double(classes.indexOf(row.get("action")));
			            	}
			            	//Otherwise add the DoNothing Function identifier
			            	else
			            		submap.get(pairs.getKey()).add(new Double(0));
			            	
			            }
			            
			            /// Check if another action was already added ///
		            	if(submap.size()==0) {
		            		//Get the new time-stamp and the row
		            		Long newkey = Double.valueOf(row.get("time").toString()).longValue();
	            			Vector<Double> newrow = new Vector<Double>(data.get(timestamp));

	            			//Update the state and action of this new row
	            			newrow.set(newrow.size()-2, status);
	            			newrow.set(newrow.size()-1, new Double
													  (classes.indexOf
													  (row.get("action"))));

		            		//Then add the action in its actual time
		            		data.put(newkey, newrow);
		            		
		            		//Update the status of the device and last time-stamp
		            		status = new Double(classes.indexOf(row.get("action")));
		            		timestamp = newkey;
		            	}

						//Renew the time-stamp and status
						prev_timestamp = timestamp;
					}
					
					// === Now fill the remaining rows === //
					//Get the sub-map contained in the examined time-stamps
					SortedMap<Long,Vector<Double>> submap = data.tailMap(prev_timestamp+1);

					//Now iterate through it and add the status and action rows
					Iterator<Entry<Long, Vector<Double>>> it = submap.entrySet().iterator();
		            while (it.hasNext()) {		
		            	Map.Entry<Long, Vector<Double>> pairs = (Map.Entry<Long, Vector<Double>>)it.next();
		            	submap.get(pairs.getKey()).add(status);
		            	submap.get(pairs.getKey()).add(new Double(0));
		            }
		            // ==================================== //
				}
			}
		}
		//===================================================================//
        
		//Return the hash-map
        return data;
	}
	
	
	/**********************************************************************
	 * <p>A testing main function</p>
	 * <p>
	 * The provided arguments should be:
	 * </p>
	 * 1) The database host name<br>
	 * 2) The database name<br>
	 * 3) The database series to be examined<br>
	 * 4) The device to be examined<br>
	 * 5) The start time-stamp of the examination time frame<br>
	 * 6) The stop time-stamp of the examination time frame<br>
	 * @param args The arguments of the main function
	 **********************************************************************/
	public static void main(String[] args) {
		//Initialise the data outputs
    	ArrayList<String> attrs = new ArrayList<String>();
    	TreeMap<Long,Vector<Double>> map = new TreeMap<Long,Vector<Double>> ();
    	
    	//Read the input arguments
    	String host = new String(args[0]);
    	String DB = new String(args[1]);
    	String series = new String(args[2]);
    	String device = new String(args[3]);
    	Long from = new Long(args[4])*1000;
    	Long to = new Long(args[5])*1000;
    	
		//Create a new InfluxDB Connector
		InfluxDBConn dbconn = new InfluxDBConn(host,DB);
    	
    	//Connect to the database
    	dbconn.InfluxDBConnect();
    	
    	//Create the time-stamps that will be used
    	SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    	Date from_date = new Date(from);
    	Date until_date = new Date(to);
    	
    	//Get the Attribute Names
    	attrs = dbconn.getAttributeNames(series,
    									dateFormat.format(from_date), 
    									dateFormat.format(until_date));
    	
    	//Request the data
    	InfluxDBData response = dbconn.requestLearnData(series, 
							    						attrs,
							    						device,
							    						dateFormat.format(from_date), 
							    						dateFormat.format(until_date));
    	
    	//Get the response items
    	attrs = response.headers;
    	map = response.influx_data;
    	
    	//Print the output
    	Iterator<Entry<Long, Vector<Double>>> it = map.entrySet().iterator();    	
    	int i=0;
        while (it.hasNext()) {
            Map.Entry<Long, Vector<Double>> pairs = (Map.Entry<Long, Vector<Double>>)it.next();
            System.out.println( pairs.getKey() + " -> " + pairs.getValue() );
            System.out.println(i++);
            it.remove(); // avoids a ConcurrentModificationException
        }
	}
}
