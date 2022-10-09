package GTFS;

import java.math.BigInteger;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class StopTime {

	private final BigInteger stopId;
	private final BigInteger tripId;
	private final BigInteger hashId;
	private String arrivalTime = "";
	private String departureTime = "";
	private String stopHeadSign = "";
	private String pickUpType = "";
	private String dropOffType = "";
	private String stopSequence = "";

	protected StopTime(BigInteger stopId, BigInteger tripId, BigInteger hashId) {
		this.stopId = stopId;
		this.tripId = tripId;
		this.hashId = hashId;
	}

	/**
	 * Verify that the stop time ID used for the search matches the ID of this stop time.
	 * @param id The ID used for the search. This ID can be either of the following:
	 *          the stop ID (String representation).
	 *          the hash ID (ID used to store this object in the hash table).
	 */
	protected boolean verifyStopTime(BigInteger id) {
		return stopId.equals(id) || hashId.equals(id);
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

	protected BigInteger getStopId() {
		return stopId;
	}

	protected BigInteger getTripId() {
		return tripId;
	}

	protected BigInteger getHashId() {
		return hashId;
	}
}