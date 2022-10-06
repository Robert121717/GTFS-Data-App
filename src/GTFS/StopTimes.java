package GTFS;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class StopTimes {

	private int stopTimeID; //COMBO OF THE TRIP ID AND STOP ID (used to make key for HASHTABLE)
	private String arrivalTime;
	private String departureTime;
	private String stopId;
	private String tripId;
	//private int sequence; not in file change on UML
	private String stopHeadSign;
	private String pickUpType;
	private String dropOffType;
	private String stopSequence;

	public StopTimes(String tripId, String arrivalTime, String departureTime, String stopId,
					 String stopSequence, String stopHeadSign, String pickUpType, String dropOffType){
		//converting stopTimeID into int for keys
		byte[] stopTimeTripIdBytes = tripId.getBytes(StandardCharsets.US_ASCII);
		StringBuilder stopTimeTripIDByteString = new StringBuilder();
		for (byte stopIdByte : stopTimeTripIdBytes) {
			stopTimeTripIDByteString.append(stopIdByte);
		}
		byte[] stopTimeStopIdBytes = stopId.getBytes(StandardCharsets.US_ASCII);
		StringBuilder stopTimeStopIDByteString = new StringBuilder();
		for (byte stopIdByte : stopTimeStopIdBytes) {
			stopTimeStopIDByteString.append(stopIdByte);
		}

		this.stopTimeID = Integer.parseInt(stopTimeTripIDByteString.toString()
				+ stopTimeStopIDByteString.toString());
		this.tripId = tripId;
		this.stopId = stopId;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopHeadSign = stopHeadSign;
		this.pickUpType = pickUpType;
		this.dropOffType = dropOffType;
		this.stopSequence = stopSequence;

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