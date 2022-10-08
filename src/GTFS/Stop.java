package GTFS;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Stop {
//these can be updated so not private
	private int stopID;
	private String stopName;
	private double stopLat;
	private double stopLon;
	private String stopDesc;

	public Stop(String stopID, String stopName, String stopLat, String stopLon, String stopDesc){
		this.stopID = toDecimal(stopID);
		this.stopName = stopName;
		this.stopLat = Double.parseDouble(stopLat);
		this.stopLon = Double.parseDouble(stopLon);
		this.stopDesc = stopDesc;

	}

	/**
	 * verify that the stop_id used for finding the stop matches what the stop has.
	 * @param stopId the stop_id used for finding the stop
	 */
	protected boolean stopVerify(String stopId){
		return this.stopID == toDecimal(stopId);
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	protected void setStopId(String stopId){
		this.stopID = toDecimal(stopId);
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param name
	 */
	protected void setStopName(String name){
		this.stopName = name;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param latitutde
	 */
	protected void setStopLat(double latitutde){
		this.stopLat = latitutde;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param longitude
	 */
	protected void setStopLon(double longitude){
		this.stopLon = longitude;
	}

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

	private int toDecimal(String stopID) {
		byte[] stopIdBytes = stopID.getBytes(StandardCharsets.US_ASCII);

		StringBuilder routIDByteString = new StringBuilder();
		for (byte stopIdByte : stopIdBytes) {
			routIDByteString.append(stopIdByte);
		}
		return Integer.parseInt(routIDByteString.toString());
	}

}