package GTFS;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Stops {
//these can be updated so not private
	private int stopID;
	private String stopName;
	private double stopLat;
	private double stopLon;
	private String stopDesc;





	public Stops(String stopID, String stopName, String stopLat, String stopLon, String stopDesc){
		//converting routID into int for keys
		byte[] stopIdBytes = stopID.getBytes(StandardCharsets.US_ASCII);
		StringBuilder routIDByteString = new StringBuilder();
		for (byte stopIdByte : stopIdBytes) {
			routIDByteString.append(stopIdByte);
		}
		this.stopID = Integer.parseInt(routIDByteString.toString());
		this.stopName = stopName;
		this.stopLat = Double.parseDouble(stopLat);
		this.stopLon = Double.parseDouble(stopLon);
		this.stopDesc = stopDesc;

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	public Stops searchStopId(String stopId){
		return null;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param file
	 */
	public boolean importStop(File file){
		return false;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param file
	 */
	public File exportStops(File file){
		return null;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeID
	 */
	public boolean displayStop(String routeID){
		return false;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	public void stopVerify(String stopId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	public void setStopId(String stopId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param name
	 */
	public void setStopName(String name){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param latitutde
	 */
	public void setStopLat(double latitutde){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param longitude
	 */
	public void setStopLon(double longitude){

	}

	public int getStopId(){
		return stopID;
	}

	public String getStopName(){
		return stopName;
	}

	public double getStopLat(){
		return stopLat;
	}

	public double getStopLon(){
		return stopLon;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param obj
	 */
	public void update(Object obj){

	}

}