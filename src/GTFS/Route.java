package GTFS;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Route {

	//none should be final because we can update info.
	private int routeID;
	private String routeColor;
	private String agencyId;
	private String routeShortName;
	private String routeLongName;
	private String routeDesc;
	private String routeType;
	private String routeURL;
	private String routeTextColor;

	public Route(String routeID, String agencyId, String routeShortName, String routeLongName, String routeDesc,
				 String routeType, String routeURL, String routeColor, String routeTextColor){

		this.routeID = toDecimal(routeID);
		this.agencyId = agencyId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeDesc = routeDesc;
		this.routeType = routeType;
		this.routeURL = routeURL;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	protected void displayRoute(String stopId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	protected void routeVerify(String routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	protected void filterRoute(String routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	protected void plotCoord(Route routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	protected void plotLocation(Route routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	protected Route searchRouteId(String routeId){
		return null;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	protected void searchRouteForStop(String stopId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param
	 */
	protected void setRouteId(String routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeColor
	 */
	protected void setRouteColor(String routeColor){

	}

	protected int getRouteId(){
		return routeID;
	}

	protected String getRouteColor(){
		return routeColor;
	}

	protected double getDistance(){
		return 0;
	}

	protected double getDuration(){
		return 0;
	}

	protected double getSpeed(){
		return 0;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param obj
	 */
	protected void update(Object obj){

	}

	private int toDecimal(String routeID) {
		byte[] routeIdBytes = routeID.getBytes(StandardCharsets.US_ASCII);

		StringBuilder routeIDByteString = new StringBuilder();
		for (byte routeIdByte : routeIdBytes) {
			routeIDByteString.append(routeIdByte);
		}
		return Integer.parseInt(routeIDByteString.toString());
	}
}