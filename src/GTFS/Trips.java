package GTFS;


import java.io.File;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Trips {

	private String tripId;
	private String routeId;
	private String tripHeadsign;
	private StopTimes stopTimesInTrip;
	private String serviceId;
	private String directionId;
	private String blockId;
	private String shapeId;
	private StopTimes allStopTimes;



	public void finalize() throws Throwable {

	}

	public Trips(){

	}

	public String getTripId(){
		return "";
	}

	public String getRouteId(){
		return "";
	}

	/**
	 * 
	 * @param tripId
	 */
	public void setTripId(String tripId){

	}

	/**
	 * 
	 * @param routeId
	 */
	public void setRouteId(String routeId){

	}

	public void updateTrip(){

	}

	/**
	 * 
	 * @param tripId
	 */
	public Trips searchTripId(String tripId){
		return null;
	}

	/**
	 * 
	 * @param routeId
	 */
	public Trips searchByRouteId(String routeId){
		return null;
	}

	/**
	 * 
	 * @param tripId
	 */
	public void tripVerify(String tripId){

	}

	/**
	 * 
	 * @param stopId
	 */
	public void searchForNextTrip(String stopId){

	}

	/**
	 * 
	 * @param file
	 */
	public void importTrip(File file){

	}

	/**
	 * 
	 * @param file
	 */
	public void exportTrip(File file){

	}

	/**
	 * 
	 * @param tripId
	 */
	public void displayTrip(String tripId){

	}

	/**
	 * 
	 * @param tripId
	 */
	public Trips searchForTrip(String tripId){
		return null;
	}

	/**
	 * 
	 * @param obj
	 */
	public void update(Object obj){

	}

}