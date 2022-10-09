package GTFS;

import java.math.BigInteger;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Stop {

	private final BigInteger stopID;
	private String stopName;
	private double stopLat;
	private double stopLon;
	private String stopDesc;

	/**
	 * constructor for Stop
	 * @param stopID the string of the stopID
	 */
	protected Stop(BigInteger stopID){
		this.stopID = stopID;
		this.stopName = "";
		this.stopLat = 0;
		this.stopLon = 0;
		this.stopDesc = "";
	}

	/**
	 * verify that the stop_id used for finding the stop matches what the stop has.
	 * @param stopId the stop_id used for finding the stop
	 */
	protected boolean verifyStop(BigInteger stopId){
		return this.stopID.equals(stopId);
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
	protected BigInteger getStopId(){
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

	public String toString(){
		String info = "";
		info = "" + stopID + "," + stopName + "," + stopDesc + "," + stopLat + "," + stopLon;
		return info;
	}
}