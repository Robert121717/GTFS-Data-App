package GTFS;


import java.util.Hashtable;
import java.util.List;

/**
 * @author nairac
 * @version 1.0
 */
public class GTFS {

	//Change UML to show that GTFS has four hashtables, not Lists.
	protected Hashtable<Integer, Routes> routes;
	protected Hashtable<Integer, Stops> stops;
	protected Hashtable<Integer,StopTimes> stopTimes;
	protected Hashtable<Integer, Trips> trips;


	public GTFS(){
		routes = new Hashtable<>();
		stops = new Hashtable<>();
		stopTimes = new Hashtable<>();
		trips = new Hashtable<>();

	}

	/**
	 * 
	 * @param path
	 */
	public boolean importFile(String path){
		return false;
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
	 * @param stopId
	 */
	public Stops searchStopId(int stopId){
		return stops.get(stopId);


	}

	/**
	 * 
	 * @param routeId
	 */
	public Routes searchRouteId(String routeId){

		return null;
	}

	/**
	 * 
	 * @param tripId
	 */
	public Trips searchForNextTrips(String tripId){
		return null;
	}

}