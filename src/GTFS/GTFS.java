package GTFS;


import java.util.List;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class GTFS {

	private List<Routes> routes;
	private List<Stops> stops;
	private List<StopTimes> stopTimes;
	private List<Trips> trips;
	public Routes m_Routes;
	public Stops m_Stops;
	public StopTimes m_StopTimes;
	public Trips m_Trips;

	public GTFS(){

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
	 * 
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