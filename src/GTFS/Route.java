package GTFS;

import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Route {

	// route ID should not be changed to prevent bugs in the HashTable.
	// The route ID is the primary identifier of the object, thus it should not be changed.
	private final int routeID;
	private String routeColor;
	private String agency_ID;
	private String shortName;
	private String longName;
	private String routeDesc;
	private String routeType;
	private String routeURL;
	private String routeTextColor;

	/**
	 * Creates a new Route object.
	 * @param routeID The ID of the Route as a String, where the integer value of the ID will be determined automatically.
	 */
	public Route(String routeID) {
		this.routeID = toDecimal(routeID);
	}

	protected void setAgency_ID(String agency_ID) {
		this.agency_ID = agency_ID;
	}

	protected void setShortName(String shortName) {
		this.shortName = shortName;
	}

	protected void setLongName(String longName) {
		this.longName = longName;
	}

	protected void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	protected void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	protected void setRouteURL(String routeURL) {
		this.routeURL = routeURL;
	}

	protected void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}

	protected void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}

	/**
	 * verify that the Route_ID used for the search matches the actual Route_ID
	 * @param routeId the Route_ID used for the search
	 */
	protected boolean verifyRoute(String routeId){
		return this.routeID == toDecimal(routeId);
	}

	protected int getRouteId(){
		return routeID;
	}

	protected String getAgencyID() {
		return agency_ID;
	}

	protected String getShortName() {
		return shortName;
	}

	protected String getLongName() {
		return longName;
	}

	protected String getRouteDesc() {
		return routeDesc;
	}

	protected String getRouteType() {
		return routeType;
	}

	protected String getRouteURL() {
		return routeURL;
	}

	protected String getRouteColor(){
		return routeColor;
	}

	protected String getRouteTextColor() {
		return routeTextColor;
	}
	protected double getDistance(){
		return 0;
	}

	/**
	 * Converts each ASCII character in the Route_ID to its decimal representation and appends it an integer.
	 * @param routeID The corresponding Route_ID as a String.
	 * @return The Route_ID as an integer, where it will hold the key to the Route object in the relative data structure.
	 */
	private int toDecimal(String routeID) {
		byte[] routeIdBytes = routeID.getBytes(StandardCharsets.US_ASCII);

		StringBuilder routeIDByteString = new StringBuilder();
		for (byte routeIdByte : routeIdBytes) {
			routeIDByteString.append(routeIdByte);
		}
		return Integer.parseInt(routeIDByteString.toString());
	}
}