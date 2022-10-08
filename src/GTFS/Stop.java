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
	 * NOT IMPLEMENTED YET
	 * @param routeID
	 */
	protected boolean displayStop(String routeID){
		return false;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	protected void stopVerify(String stopId){

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

	/**
	 * NOT IMPLEMENTED YET
	 * @param obj
	 */
	protected void update(Object obj){
	}

}