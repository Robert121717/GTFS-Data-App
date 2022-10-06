package GTFS;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Trip {

	private int tripId;
	private String routeId;
	private String tripHeadsign;
	private StopTime stopTimesInTrip;
	private String serviceId;
	private String directionId;
	private String blockId;
	private String shapeId;
	//private StopTimes allStopTimes; Do we need this?


	public Trip(String tripId, String routeId, String serviceId, String tripHeadsign, String directionId,
				String blockId, String shapeId){
		//converting tripID into int for keys
		byte[] tripIdBytes = tripId.getBytes(StandardCharsets.US_ASCII);
		StringBuilder tripIDByteString = new StringBuilder();
		for (byte tripIdByte : tripIdBytes) {
			tripIDByteString.append(tripIdByte);
		}
		this.tripId = Integer.parseInt(tripIDByteString.toString());
		this.routeId = routeId;
		this.serviceId = serviceId;
		this.tripHeadsign = tripHeadsign;
		this.directionId = directionId;
		this.blockId = blockId;
		this.shapeId = shapeId;

	}

	public int getTripId(){
		return tripId;
	}

	public String getRouteId(){
		return routeId;
	}

	/**
	 * NOT IMPLEMENTED
	 * @param tripId
	 */
	public void setTripId(String tripId){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param routeId
	 */
	public void setRouteId(String routeId){

	}

	public void updateTrip(){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param tripId
	 */
	public Trip searchTripId(String tripId){
		return null;
	}

	/**
	 * NOT IMPLEMENTED
	 * @param routeId
	 */
	public Trip searchByRouteId(String routeId){
		return null;
	}

	/**
	 * NOT IMPLEMENTED
	 * @param tripId
	 */
	public void tripVerify(String tripId){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param stopId
	 */
	public void searchForNextTrip(String stopId){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param file
	 */
	public void importTrip(File file){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param file
	 */
	public void exportTrip(File file){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param tripId
	 */
	public void displayTrip(String tripId){

	}

	/**
	 * NOT IMPLEMENTED
	 * @param tripId
	 */
	public Trip searchForTrip(String tripId){
		return null;
	}

	/**
	 * NOT IMPLEMENTED
	 * @param obj
	 */
	public void update(Object obj){

	}

}