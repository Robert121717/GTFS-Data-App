package GTFS;

import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Route {

	private final int routeID;
	private String agencyID;
	private String shortName;
	private String longName;
	private String routeType;
	private String routeDesc;
	private String routeURL;
	private String routeColor;
	private String routeTextColor;

	protected Route(int routeID) {
		this.routeID = routeID;
	}

	protected void setAgencyID(String agencyID) {
		this.agencyID = agencyID;
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
	 * Verify that the Route_ID used for the search matches the actual Route_ID.
	 * @param routeID the Route_ID used for the search.
	 */
	protected boolean verifyRoute(int routeID){
		return this.routeID == routeID;
	}

	protected int getRouteId(){
		return routeID;
	}

	protected String getAgencyID() {
		return agencyID;
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
	} //TODO
}