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
public class Stop {

	private final String stopID;
	private String stopName;
	private double stopLat;
	private double stopLon;
	private String stopDesc;

	private final BigInteger stopIDHash;

	/**
	 * constructor for Stop
	 * @param stopID the string of the stopID
	 */
	protected Stop(String stopID){
		this.stopID = stopID;
		this.stopName = "";
		this.stopLat = 0;
		this.stopLon = 0;
		this.stopDesc = "";
		this.stopIDHash = GTFS.toDecimal(stopID);
	}

	/**
	 * verify that the stop_id used for finding the stop matches what the stop has.
	 * @param stopId the stop_id used for finding the stop
	 */
	protected boolean verifyStop(String stopId){
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
	protected String getStopId(){
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

	protected BigInteger getStopIDHash(){
		return stopIDHash;
	}

	/**
	 * method to convert the data stored into a string
	 * @return string of all data in the class
	 */
	public String toString(){
		return "" + stopID + "," + stopName + "," + stopDesc + "," + stopLat + "," + stopLon;
	}

	/**
	 * method to check if passed in class is the same as this class
	 * @param other class to be compared to current class
	 * @return if classes are similar or not
	 */
	@Override
	public boolean equals(Object other){
		Stop temp = (Stop) other;
		return this.stopID.equals(temp.stopID) && this.stopName.equals(temp.stopName) && this.stopLat == temp.stopLat
				&& this.stopLon == temp.stopLon && this.stopDesc.equals(temp.stopDesc);
	}

}