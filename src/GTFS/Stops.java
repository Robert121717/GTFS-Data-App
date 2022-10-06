package GTFS;


import java.io.File;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Stops {

	private String stopID;
	private String stopName;
	private double stopLat;
	private double stopLon;
	private String stopDesc;





	public Stops(){

	}

	/**
	 * 
	 * @param stopId
	 */
	public Stops searchStopId(String stopId){
		return null;
	}

	/**
	 * 
	 * @param file
	 */
	public boolean importStop(File file){
		return false;
	}

	/**
	 * 
	 * @param file
	 */
	public File exportStops(File file){
		return null;
	}

	/**
	 * 
	 * @param routeID
	 */
	public boolean displayStop(String routeID){
		return false;
	}

	/**
	 * 
	 * @param stopId
	 */
	public void stopVerify(String stopId){

	}

	/**
	 * 
	 * @param stopId
	 */
	public void setStopId(String stopId){

	}

	/**
	 * 
	 * @param name
	 */
	public void setStopName(String name){

	}

	/**
	 * 
	 * @param latitutde
	 */
	public void setStopLat(double latitutde){

	}

	/**
	 * 
	 * @param longitude
	 */
	public void setStopLon(double longitude){

	}

	public String getStopId(){
		return "";
	}

	public String getStopName(){
		return "";
	}

	public double getStopLat(){
		return 0;
	}

	public double getStopLon(){
		return 0;
	}

	/**
	 * 
	 * @param obj
	 */
	public void update(Object obj){

	}

}