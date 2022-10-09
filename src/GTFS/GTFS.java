package GTFS;


import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

/**
 * @author nairac
 * @version 1.0
 */
public class GTFS {

	//Change UML to show that GTFS has four hashtables, not Lists.
	//Do we need to put all of these in a single list??? or keep separate?
	protected Hashtable<Integer, Route> routes;
	protected Hashtable<Integer, Stop> stops;
	protected Hashtable<Integer, StopTime> stopTimes;
	protected Hashtable<Integer, Trip> trips;


	public GTFS(){
		routes = new Hashtable<>();
		stops = new Hashtable<>();
		stopTimes = new Hashtable<>();
		trips = new Hashtable<>();

	}

	protected void importText(String text) {

	}

	/**
	 * puts a new route into the routes hashmap
	 * @param newRoute- route being added to hashtable
	 * @return returns the newly added Route
	 */
	public Route importRoute(Route newRoute){
		return routes.put(newRoute.getRouteId(), newRoute);
		//maybe we can use the Route for something in the future. Otherwise, change to void/boolean
		//Same goes for other import methods in GTFS


	}

	/**
	 * puts a new stop into the stops hashtable
	 * @param newStop- stop being added to hashtable
	 * @return returns the newly added Stop
	 */
	public Stop importStop(Stop newStop){
		return stops.put(newStop.getStopId(), newStop);
	}

	/**
	 * puts a new stoptime into the stoptimes hashtable
	 * @param newStopTime - stoptime being added to hashtable
	 * @return returns newly added stoptime
	 */
	public StopTime importStopTime(StopTime newStopTime){
		return stopTimes.put(newStopTime.getHashId(), newStopTime);
	}

	/**
	 * puts a new trip into the trips hashtable
	 * @param newTrip- trip being added to hashtable
	 * @return returns newly added stoptime
	 */
	public Trip importTrip(Trip newTrip){
		return trips.put(newTrip.getHashId(), newTrip);
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param String
	 */
	public boolean export(String String){
		return false;
	}

	/**
	 * Searches for a stop, given the stopID. Returns the Stop
	 * @param stopId- ID turned into ascii decimal used as a key for each stop.
	 */
	public Stop searchStopId(int stopId){
		return stops.get(stopId);
	}

	/**
	 * Searches for a route, given the routeId. Returns the route
	 * @param routeId- ID turned into ascii decimal used as a key for each stop
	 */
	public Route searchRouteId(int routeId){
		return routes.get(routeId);
	}

	/**
	 * Searches for a trip, given the tripId. Returns the trip
	 * @param tripId- ID turned into ascii decimal used as a key for each trip
	 */
	public Trip searchTrips(int tripId){
		return trips.get(tripId);
	}
	/**
	 * Searches for a stop time, given the stopTimeId. Returns the stop time.
	 * @param stopTimeId- ID turned into ascii decimal used as a key for each stop time
	 */
	public StopTime searchStopTimes(int stopTimeId){
		return stopTimes.get(stopTimeId);
	}

	protected String getNewestImport() {
		return "";
	}

	/**
	 * Converts each ASCII character in the ID to its decimal representation and appends it to an integer.
	 * @param id The object's ID as a String.
	 * @return The ID as an appended integer. This ID will represent an attribute of the relative class,
	 * but may not be the ID be used when storing this object in a hash table.
	 */
	private int toDecimal(String id) {
		byte[] idBytes = id.getBytes(StandardCharsets.US_ASCII);

		StringBuilder idByteString = new StringBuilder();
		for (byte idByte : idBytes) {
			idByteString.append(idByte);
		}
		return Integer.parseInt(idByteString.toString());
	}

	/**
	 * Appends one ID onto another. This method should only be used when both IDs correspond to a single object.
	 * @param v1i Integer representation of the first ID.
	 * @param v2i Integer representation of the second ID.
	 * @return A single ID to be used when storing this object in a hash table,
	 * where the first IDs value comes before the second IDs value (such that hashId: [v1i][v2i]).
	 */
	private int mergeIDs(int v1i, int v2i) {
		String v1 = String.valueOf(v1i), v2 = String.valueOf(v2i);

		return Integer.parseInt(v1 + v2);
	}
}