package GTFS;


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

	private void importRoute(Route rout){

	}

	private void importStop(){

	}

	private void importStopTime(){

	}

	private void importTrip(){

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

}