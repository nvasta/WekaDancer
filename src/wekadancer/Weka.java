package wekadancer;


import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;

import org.dancer.PandaLib.Group;

import context.arch.intelligibility.expression.DNF;
import context.arch.intelligibility.weka.j48.J48Parser;
import wekadancer.parsers.*;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.rules.JRip;
import weka.classifiers.trees.J48;


/**
 * <p>The Weka Class</p>
 * <p>
 * This class implements a caller to the Weka library that allows
 * learning algorithm to be run on the provided data. These data can
 * be given either via an '.arff' file, or straight from the database
 * through a Java Tree-map structure
 * </p>
 */
public class Weka{

	//The class data source
	DataSource source = null;


	/**********************************************************************
	 * <p>The class constructor</p>
	 * <p>
	 * It requires the name of the database to initialise the class. 
	 * This constructor is to be used if using data from an ARFF file.
	 * </p>
	 * @param filename The file containing the data in the ARFF format
	 **********************************************************************/
	public Weka(String filename) throws Exception{
		source = new DataSource(filename);
	}


	/**********************************************************************
	 * <p>The class constructor</p>
	 * 
	 * <p>Constructor with no arguments. To be used when using data from 
	 * influxDB.</p>
	 * 
	 **********************************************************************/
	public Weka(){

	}


	/**********************************************************************
	 * <p>The readDataFile function</p>
	 * <p>
	 * This function reads the data file and returns it in an Instances 
	 * format
	 * </p>
	 * @param headers The name of the attributes to be examined
	 * @param data The data to be examined
	 * @param action The action list to be considered during learning
	 * @return The Weka Instances generated
	 **********************************************************************/
	public Instances readData(ArrayList<String> headers, 
			TreeMap<Long, Vector<Double>> data, 
			ArrayList<String> action) throws Exception
	{
		Instances dataInstances = (source != null) ? 
				source.getDataSet():
					parseInfluxData(headers, data, action);

				//Setting class attribute if the data format does not provide this information
				//For example, the ARFF format saves the class attribute information as well
				if (dataInstances.classIndex() == -1)
					dataInstances.setClassIndex(dataInstances.numAttributes() - 1);

				return dataInstances;
	}


	/**********************************************************************
	 * <p>The J48 function</p>
	 * <p>
	 * This function runs the J48 algorithm and returns an ArrayList of 
	 * the rules in a String format.
	 * </p>
	 * @param data The data to be examined
	 * @return The parsed three found as a group of policies
	 **********************************************************************/
	public Group J48(String module, Instances data) {

		String[] options = {"-U"};		// unpruned tree
		J48 tree = new J48(); 		// new instance of tree

		//Evaluate J48 with cross validation
		tree.setUnpruned(false);
		try {

			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(tree, data, 10, new Random(1));
			System.out.println("Percent correct: " +
					Double.toString(eval.pctCorrect()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		//Build the classifier
		try {
			tree.setOptions(options);	// set the options
			tree.buildClassifier(data); // build classifier
		} catch (Exception e1) {
			e1.printStackTrace();
		} 

		//Print the tree
		try {
			System.out.println(tree);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	

		try {
			Map<String, DNF> valueTraces = J48Parser.parse(tree, data);

			for (String value : valueTraces.keySet()) {
				DNF traces = valueTraces.get(value);
				System.out.println(value + "(size=" + traces.size() + "): " + traces);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Remove the useless info
		/*String rules = tree.toString().substring(
						tree.toString().indexOf("------------------") + 18,
						tree.toString().indexOf("Number of"));*/

		//Give the lines as a reader to the parses
		//J48Parser parser = new J48Parser(module, new StringReader(rules));

		/*try {
			parser.RuleSet();
		} catch (wekadancer.parsers.ParseException e) {
			e.printStackTrace();
		}*/

		//Return the output
		return (new Group());//parser.group;
	}


	/**********************************************************************
	 * <p>The JRip function</p>
	 * <p>
	 * This function runs the JRip algorithm and returns an ArrayList of
	 * the rules in a String format.
	 * </p>
	 * @param data The data to be examined
	 * @return The parsed three found as a group of policies
	 **********************************************************************/
	public Group JRip(String module, Instances data){

		String[] options = {"-S", "1"};
		JRip tree = new JRip();         	// new instance of tree

		//Evaluate J48 with cross validation
		try {

			Evaluation eval = new Evaluation(data);
			eval.crossValidateModel(tree, data, 10, new Random(1));
			System.out.println("Percent correct: " +
					Double.toString(eval.pctCorrect()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {	
			tree.setOptions(options);		// set the options
			tree.buildClassifier(data);		// build classifier
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Print the tree
		System.out.println(tree);	

		//Remove the useless info
		String rules = tree.toString().substring(
				tree.toString().indexOf("===========")+11,
				tree.toString().indexOf("Number of"));

		//Give the lines as a reader to the parses
		JRipParser parser = new JRipParser(module, new StringReader(rules));
		try {
			parser.RuleSet();
		} catch (wekadancer.parsers.ParseException e) {
			e.printStackTrace();
		}

		//Return the output
		return parser.group;
	}


	/**********************************************************************
	 * <p>The parseInfluxData function</p>
	 * <p>
	 * This function parses the data provided from influxDB in the form
	 * of a Java Tree-map. It then returns them in the appropriate format
	 * for the learning operation. This is in Instances, a structure 
	 * specified by the Weka library.
	 * </p>
	 * @param headers The attributes to be examined
	 * @param data The data to be parsed
	 * @param actions The actions to be taken into account
	 * @return The Weka Instances generated from the InfluxDB data
	 **********************************************************************/
	public Instances parseInfluxData(ArrayList<String> headers, 
			TreeMap<Long,Vector<Double>> data, 
			ArrayList<String> actions) throws ParseException
	{
		//Date Formatters
		Calendar c = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		//Code from http://stackoverflow.com/questions/12118132/adding-a-new-instance-in-weka
		//Also see Weka's manual Chapter 16
		ArrayList<Attribute> atts = new ArrayList<Attribute>();

		//Removing the "()" from the status. This is for the Parser to differentiate 
		//between status and action, one has (), the other doesn't.
		ArrayList<String> status = new ArrayList<String>();
		for (int i = 0; i < actions.size(); i++)
			status.add(actions.get(i).substring(0, actions.get(i).length()));	
		//	status.add(actions.get(i).substring(0, actions.get(i).length()-2));	

		//=========== Adding the attributes ===========//
		//Add always attributes of time
		atts.add(new Attribute("Day", Attribute.NUMERIC));
		atts.add(new Attribute("Month", Attribute.NUMERIC));
		atts.add(new Attribute("Time",  Attribute.NUMERIC));

		//Add remaining attributes (for in Serie)
		for (String s:headers)
			atts.add(new Attribute(s, Attribute.NUMERIC));

		//Now add the status and action attributes
		atts.add(new Attribute("Status", status));
		atts.add(new Attribute("Action", actions));
		//=============================================//	

		//Obtaining the first entry, so that we can get the size/number of attributes (including the Class)
		Map.Entry<Long, Vector<Double>> entry = data.entrySet().iterator().next();
		Instances dataRaw = new Instances("RunInstances", atts, entry.getValue().size()+3); //plus 3 for Day, Month, Time

		//Go through the data, by entry
		Iterator<Entry<Long, Vector<Double>>> it = data.entrySet().iterator();
		int counter = 0;
		while (it.hasNext()) {
			//Initialise the new instance
			double[] instanceValue = new double[entry.getValue().size()+3];//plus 3 for Day, Month, Time

			//Get the values
			Map.Entry<Long, Vector<Double>> pairs = (Map.Entry<Long, Vector<Double>>)it.next();

			//Get the sample date
			Date sampleDate = new Date(pairs.getKey()*1000);//multiply by 1000 to get milliseconds
			c.setTime(sampleDate);

			//Populate the first three entries of the array, since they are fixed to Day-Month-Time format.
			instanceValue[0] = c.get(Calendar.DAY_OF_WEEK);
			instanceValue[1] = c.get(Calendar.MONTH); 
			instanceValue[2] = Double.parseDouble(Long.toString(timeFormat.parse(timeFormat.format(sampleDate)).getTime())); 
			//instanceValue[2] = dataRaw.attribute(2).parseDate(timeFormat.format(sampleDate));

			//========= Checking for null values ==========//
			Vector<Double> v2 = pairs.getValue();
			ArrayList<Integer> missingValuesIndex = new ArrayList<Integer>();
			for (int i = 0; i < v2.size()-2; i++){
				Double value = pairs.getValue().get(i);
				if (value != null)
					//Save the value to the array
					instanceValue[3+i] = (double)pairs.getValue().get(i);
				else{
					//Record the index, so that we can go and set it as missing value "?".
					missingValuesIndex.add(3+i);
				}
			}
			//=============================================//

			//======== Mapping the status/actions =========//
			//size()-2 is the status. This is for mapping the 
			//Status index to the actual Status string. Go to 
			//the dataRaw instance, under the specific attribute 
			//index, and get the index of the current Status.
			instanceValue[instanceValue.length-2] = 
					dataRaw.attribute(dataRaw.numAttributes()-2).
					indexOfValue(
							(String)status.get((
									pairs.getValue().
									get(v2.size()-2)).
									intValue()
									)
							);

			//Last index in the array is the class/action. Also 
			//for mapping, as above with the Status.
			instanceValue[instanceValue.length-1] = 
					dataRaw.attribute(dataRaw.numAttributes()-1).
					indexOfValue(
							(String) actions.get((
									pairs.getValue().
									get(v2.size()-1)).
									intValue()
									)
							);
			//=============================================//

			//Add the instance
			dataRaw.add(new DenseInstance(1.0, instanceValue));

			//Set missing values to "?"
			for (Integer i:missingValuesIndex)
				dataRaw.instance(counter).setMissing(i);

			//Increase now the counter
			counter++;
		}

		//Return the parsed data
		return dataRaw;
	}
}

