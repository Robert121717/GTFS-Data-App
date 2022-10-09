package GTFS;

import java.math.BigInteger;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Trip {

	private final BigInteger tripId;
	private final BigInteger routeId;
	private final BigInteger hashId;
	private String headSign = "";
	private String serviceId = "";
	private String directionId = "";
	private String blockId = "";
	private String shapeId = "";

	protected Trip(BigInteger tripId, BigInteger routeId, BigInteger hashId) {
		this.tripId = tripId;
		this.routeId = routeId;
		this.hashId = hashId;
	}

	/**
	 * Verify that the trip ID used for the search matches the ID of this trip.
	 * @param id The ID used for the search. This ID can be either of the following:
	 *          the trip ID (String representation).
	 *          the hash ID (ID used to store this object in the hash table).
	 */
	protected boolean verifyTrip(BigInteger id){
		return tripId.equals(id) || hashId.equals(id);
	}

	protected void setHeadSign(String headSign) {
		this.headSign = headSign;
	}

	protected void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	protected void setDirectionId(String directionId) {
		this.directionId = directionId;
	}

	protected void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	protected void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	protected BigInteger getTripId(){
		return tripId;
	}

	protected BigInteger getRouteId(){
		return routeId;
	}

	protected BigInteger getHashId() {
		return hashId;
	}

	protected String getHeadSign() {
		return headSign;
	}

	protected String getServiceId() {
		return serviceId;
	}

	protected String getDirectionId() {
		return directionId;
	}

	protected String getBlockId() {
		return blockId;
	}

	protected String getShapeId() {
		return shapeId;
	}

	public String toString(){
		return "" + tripId + "," + routeId + "," + hashId + "," + headSign + "," +
				serviceId + "," + directionId + "," + blockId + "," + shapeId;
	}
}