/**
 * GTFS, program to import, export, and search information on bus transit data
 *     Copyright (C) 2022  nairac, atkinsonr, morrowc, schmidtrj
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package GTFS;

import java.math.BigInteger;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Route {

	private final String routeID;
	private String agencyID = "";
	private String shortName = "";
	private String longName = "";
	private String routeType = "";
	private String routeDesc = "";
	private String routeURL = "";
	private String routeColor = "";
	private String routeTextColor = "";

	private final BigInteger routeIDHash;



	protected Route(String routeID) {
		this.routeID = routeID;
		this.routeIDHash = GTFS.toDecimal(routeID);
	}

	/**
	 * Verify that the Route_ID used for the search matches the actual Route_ID.
	 * @param routeID the Route_ID used for the search.
	 */
	protected boolean verifyRoute(String routeID){
		return this.routeID.equals(routeID);
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

	protected String getRouteId(){
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

	protected BigInteger getRouteIDHash(){
		return routeIDHash;
	}

	/**
	 * method to convert the data stored into a string
	 * @return string of all data in the class
	 */
	public String toString(){
		return "" + routeID + "," + agencyID + "," + shortName + "," + longName +
				"," +routeType + "," + routeDesc + "," + routeURL  + "," + routeColor + "," + routeTextColor;
	}
	/**
	 * method to check if passed in class is the same as this class
	 * @param other class to be compared to current class
	 * @return if classes are similar or not
	 */
	public boolean equals(Object other){
		Route temp = (Route) other;
		return this.routeID.equals(temp.routeID) && this.agencyID.equals(temp.agencyID) && this.shortName.equals(temp.shortName)
				&& this.longName.equals(temp.longName) && this.routeType.equals(temp.routeType) && this.routeDesc.equals(temp.routeDesc)
				&& this.routeURL.equals(temp.routeURL) && this.routeColor.equals(temp.routeColor)
				&& this.routeTextColor.equals(temp.routeTextColor);
	}

}