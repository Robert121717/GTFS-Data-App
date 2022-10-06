package GTFS;


import java.io.File;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class StopTimes {

	private String arrivalTime;
	private String departureTime;
	private String stopId;
	private String tripId;
	private int sequence;
	private String stopHeadSign;
	private String pickUpTime;
	private String dropOffTime;



	public void finalize() throws Throwable {

	}

	public StopTimes(){

	}

	/**
	 * 
	 * @param file
	 */
	public boolean importStopTimes(File file){
		return false;
	}

	/**
	 * 
	 * @param file
	 */
	public File exportStopTimes(File file){
		return null;
	}

	/**
	 * 
	 * @param stopId
	 */
	public StopTimes searchStopIDTime(String stopId){
		return null;
	}

	public void searchStopTimesForStop(){

	}

	/**
	 * 
	 * @param arriveTime
	 */
	public void setArrivalTime(String arriveTime){

	}

	/**
	 * 
	 * @param departureTime
	 */
	public void setDepartureTime(String departureTime){

	}

	/**
	 * 
	 * @param stopId
	 */
	public void setStopId(String stopId){

	}

	/**
	 * 
	 * @param tripId
	 */
	public void setTripId(String tripId){

	}

	public String getArrivalTime(){
		return "";
	}

	public String getDepartureTime(){
		return "";
	}

	public String getStopId(){
		return "";
	}

	public String getTripId(){
		return "";
	}

	/**
	 * 
	 * @param
	 */
	public boolean displaySpeed(String arrivalTime, String departureTime){
		return false;
	}

	/**
	 * 
	 * @param tripId
	 */
	public StopTimes searchTripIdTimes(String tripId){
		return null;
	}

	/**
	 * 
	 * @param obj
	 */
	public void update(Object obj){

	}

}