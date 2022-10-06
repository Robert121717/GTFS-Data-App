package GTFS;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public class Routes {

	//none should be final because we can update info.
	private int routeID;
	private String routeColor;
	private String agencyId;
	private String routeShortName;
	private String routeLongName;
	private String routeDesc;
	private String routeType;
	private String routeURL;
	private String routeTextColor;




	public Routes(String routeID, String agencyId, String routeShortName, String routeLongName, String routeDesc,
				  String routeType, String routeURL, String routeColor, String routeTextColor){

		//converting routID into int for keys
		byte[] routeIdBytes = routeID.getBytes(StandardCharsets.US_ASCII);
		StringBuilder routIDByteString = new StringBuilder();
		for (byte routeIdByte : routeIdBytes) {
			routIDByteString.append(routeIdByte);
		}
		this.routeID = Integer.parseInt(routIDByteString.toString());
		this.agencyId = agencyId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeDesc = routeDesc;
		this.routeType = routeType;
		this.routeURL = routeURL;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;




	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param file
	 */
	public boolean importRoute(File file){
		return false;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param file
	 */
	public File exportRoute(File file){
		return null;
	}

	public void updateRoute(){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	public void displayRoute(String stopId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	public void routeVerify(String routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	public void filterRoute(String routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	public void plotCoord(Routes routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	public void plotLocation(Routes routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeId
	 */
	public Routes searchRouteId(String routeId){
		return null;
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param stopId
	 */
	public void searchRouteForStop(String stopId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param
	 */
	public void setRouteId(String routeId){

	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param routeColor
	 */
	public void setRouteColor(String routeColor){

	}

	public int getRouteId(){
		return routeID;
	}

	public String getRouteColor(){
		return routeColor;
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
	 * NOT IMPLEMENTED YET
	 * @param obj
	 */
	public void update(Object obj){

	}

}