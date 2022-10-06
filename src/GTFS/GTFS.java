package GTFS;


import java.util.Hashtable;
import java.util.List;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class GTFS {

	//Change UML to show that GTFS has four hashtables, not Lists.
	private Hashtable<Integer, Routes> routes;
	private Hashtable<Integer, Stops> stops;
	private Hashtable<Integer,StopTimes> stopTimes;
	private Hashtable<Integer, Trips> trips;
	public Routes m_Routes;
	public Stops m_Stops;
	public StopTimes m_StopTimes;
	public Trips m_Trips;

	public GTFS(){
		routes = new Hashtable<Integer, Routes>();
		stops = new Hashtable<Integer, Stops>();
		stopTimes = new Hashtable<Integer, StopTimes>();
		trips = new Hashtable<Integer, Trips>();

	}

	public void finalize() throws Throwable {

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
	 * 
	 * @param stopId
	 */
	public Stops searchStopId(String stopId){
		return null;
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