package GTFS;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Trip {

	private final int tripId;
	private final int routeId;
	private final int hashId;
	private String headSign = "";
	private String serviceId = "";
	private String directionId = "";
	private String blockId = "";
	private String shapeId = "";

	protected Trip(int tripId, int routeId, int hashId) {
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
	protected boolean verifyTrip(int id){
		return tripId == id || hashId == id;
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

	protected int getTripId(){
		return tripId;
	}

	protected int getRouteId(){
		return routeId;
	}

	protected int getHashId() {
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
}