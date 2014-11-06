package wekadancer;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

public class InfluxDBData {
	
	//Class Variables
	ArrayList<String> headers;
	TreeMap<Long, Vector<Double>> influx_data;
	
	
	/**********************************************************************
	 * <p>The class constructor</p>
	 * <p>
	 * It create an InfluxDBData item with the headers and data variables.
	 * </p>
	 * @param keySet
	 * @param series2map
	 **********************************************************************/
	public InfluxDBData(ArrayList<String> keySet, TreeMap<Long, Vector<Double>> data) 
	{
		this.headers  = keySet;
		influx_data = data;
	}
}
