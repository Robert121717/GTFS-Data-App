package GTFS;


import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Stop {
//these can be updated so not private
	private final int stopID;
	private String stopName;
	private double stopLat;
	private double stopLon;
	private String stopDesc;

	public Stop(String stopID){
		this.stopID = toDecimal(stopID);
		this.stopName = "";
		this.stopLat = 0;
		this.stopLon = 0;
		this.stopDesc = "";
	}

	/**
	 * verify that the stop_id used for finding the stop matches what the stop has.
	 * @param stopId the stop_id used for finding the stop
	 */
	protected boolean stopVerify(String stopId){
		return this.stopID == toDecimal(stopId);
	}

	/**
	 * set the name of the stop
	 * @param name of the stop
	 */
	protected void setStopName(String name){
		this.stopName = name;
	}

	/**
	 * set latitude of the stop
	 * @param latitude of the stop
	 */
	protected void setStopLat(double latitude){
		this.stopLat = latitude;
	}

	/**
	 * set longitude of the stop
	 * @param longitude of stop
	 */
	protected void setStopLon(double longitude){
		this.stopLon = longitude;
	}

	/**
	 * set a description of the stop
	 * @param description description of the stop
	 */
	protected void setStopDesc(String description) {
		this.stopDesc = description;
	}

	// start of getters
	protected int getStopId(){
		return stopID;
	}

	protected String getStopName(){
		return stopName;
	}

	protected double getStopLat(){
		return stopLat;
	}

	protected double getStopLon(){
		return stopLon;
	}

	protected String getStopDesc() {
		return stopDesc;
	}

	/**
	 * Change each ASCII character from the string into their decimal values
	 * and concatenates them into one int
	 * @param stopID the stops stop_id
	 * @return value of the stop_id
	 */
	private int toDecimal(String stopID) {
		byte[] stopIdBytes = stopID.getBytes(StandardCharsets.US_ASCII);

		StringBuilder routIDByteString = new StringBuilder();
		for (byte stopIdByte : stopIdBytes) {
			routIDByteString.append(stopIdByte);
		}
		return Integer.parseInt(routIDByteString.toString());
	}

}