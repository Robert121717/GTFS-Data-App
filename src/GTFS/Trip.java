package GTFS;


import java.math.BigInteger;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Trip {

	private final String tripId;
	private final String routeId;
	private String headSign = "";
	private String serviceId = "";
	private String directionId = "";
	private String blockId = "";
	private String shapeId = "";
	private final BigInteger hashId;



	protected Trip(String tripId, String routeId) {
		this.tripId = tripId;
		this.routeId = routeId;
		this.hashId = GTFS.mergeIDs(GTFS.toDecimal(tripId), GTFS.toDecimal(routeId));


	}

	/**
	 * Verify that the trip ID used for the search matches the ID of this trip.
	 * @param id The ID used for the search. This must be the actual string representation of the trip ID.
	 */
	protected boolean verifyTrip(String id){
		return tripId.equals(id);
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

	protected String getTripId(){
		return tripId;
	}

	protected String getRouteId(){
		return routeId;
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

	protected BigInteger getHashId(){
		return hashId;
	}

	public String toString(){
		return "" + routeId + "," + serviceId + "," + tripId + "," + headSign +
				"," + directionId + "," + blockId + "," + shapeId;
	}

	@Override
	public boolean equals(Object other){
		Trip temp = (Trip) other;
		return this.routeId.equals(temp.routeId) && this.serviceId.equals(temp.serviceId) && this.tripId.equals(temp.tripId)
				&& this.headSign.equals(temp.headSign) && this.directionId.equals(temp.directionId) && this.blockId.equals(temp.blockId)
				&& this.shapeId.equals(temp.shapeId);
	}
}