package GTFS;


import java.io.File;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Routes {

	private String routeID;
	private String routeColor;
	private String agencyId;
	private String routeShortName;
	private String routeLongName;
	private String routeDesc;
	private String routeType;
	private String routeURL;
	private String routeTextColor;



	public void finalize() throws Throwable {

	}

	public Routes(){

	}

	/**
	 * 
	 * @param file
	 */
	public boolean importRoute(File file){
		return false;
	}

	/**
	 * 
	 * @param file
	 */
	public File exportRoute(File file){
		return null;
	}

	public void updateRoute(){

	}

	/**
	 * 
	 * @param stopId
	 */
	public void displayRoute(String stopId){

	}

	/**
	 * 
	 * @param routeId
	 */
	public void routeVerify(String routeId){

	}

	/**
	 * 
	 * @param routeId
	 */
	public void filterRoute(String routeId){

	}

	/**
	 * 
	 * @param routeId
	 */
	public void plotCoord(Routes routeId){

	}

	/**
	 * 
	 * @param routeId
	 */
	public void plotLocation(Routes routeId){

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
	 * @param stopId
	 */
	public void searchRouteForStop(String stopId){

	}

	/**
	 * 
	 * @param
	 */
	public void setRouteId(String routeId){

	}

	/**
	 * 
	 * @param routeColor
	 */
	public void setRouteColor(String routeColor){

	}

	public String getRouteId(){
		return "";
	}

	public String getRouteColor(){
		return "";
	}

	public double getDistance(){
		return 0;
	}

	public double getDuration(){
		return 0;
	}

	public double getSpeed(){
		return 0;
	}

	/**
	 * 
	 * @param obj
	 */
	public void update(Object obj){

	}

}