/**
 * <one line to give the program's name and a brief idea of what it does.>
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
public class StopTime {

	private final String stopId;
	private final String tripId;
	private String arrivalTime = "";
	private String departureTime = "";
	private String stopHeadSign = "";
	private String pickUpType = "";
	private String dropOffType = "";
	private String stopSequence = "";
	private final BigInteger hashId;

	protected StopTime(String stopId, String tripId) {
		this.stopId = stopId;
		this.tripId = tripId;
		this.hashId = GTFS.mergeIDs(GTFS.toDecimal(stopId), GTFS.toDecimal(tripId));
	}

	/**
	 * Verify that the stop time ID used for the search matches the ID of this stop time.
	 * @param id The ID used for the search. This must be the actual string representation of the stop time ID.
	 */
	protected boolean verifyStopTime(String id) {
		return stopId.equals(id);
	}

	protected void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	protected void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	protected void setStopHeadSign(String stopHeadSign) {
		this.stopHeadSign = stopHeadSign;
	}

	protected void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}

	protected void setDropOffType(String dropOffType) {
		this.dropOffType = dropOffType;
	}

	protected void setStopSequence(String stopSequence) {
		this.stopSequence = stopSequence;
	}

	protected boolean hasStop(String stopId) {
		return this.stopId.equals(stopId);
	}

	protected String getStopHeadSign() {
		return stopHeadSign;
	}

	protected String getPickUpType() {
		return pickUpType;
	}

	protected String getDropOffType() {
		return dropOffType;
	}

	protected String getStopSequence() {
		return stopSequence;
	}

	protected String getArrivalTime() {
		return arrivalTime;
	}

	protected String getDepartureTime() {
		return departureTime;
	}

	protected String getStopId() {
		return stopId;
	}

	protected String getTripId() {
		return tripId;
	}

	protected BigInteger getHashId(){
		return hashId;
	}


	public String toString(){
		return "" + tripId + "," + arrivalTime + "," + departureTime + "," + stopId +
				"," + stopSequence + "," + stopHeadSign + "," + pickUpType + "," + dropOffType;
	}
}