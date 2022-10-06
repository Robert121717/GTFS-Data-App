package GTFS;

import java.awt.*;

import javafx.fxml.FXML;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:31 PM
 */
public class Controller{
	private final GTFS gtfs;

	@FXML
	private Label test;

	@FXML
	protected void testButton(){
		test.setText("TEST");
	}






	public Controller(){
		gtfs = new GTFS();
	}

	/**
	 *checks what kind of file we are importing and calls the related methods
	 * @param
	 */
	private boolean importFiles(){
		//if stop file call importstop method.
		//else if trip file call importtrip
		//else if route file call importroute
		//else if stopTime file call importstopTime
		//else error cannot import that file
		return false;
	}

	/**
	 *
	 * @param
	 */
	private void exportFiles(){

	}

	/**
	 *
	 * @param
	 */
	private void importRoute(){
	//make list of all the routes in the file and call the importRoute in gtfs to import each stop in list
	}

	private void importStop(){
		//make list of all the stops in the file and call the importStop in gtfs to import each stop in list

	}

	private void importStopTime(){
		//make list of all the stopTimes in the file and call the importStopTimes in gtfs to import each stoptime in list

	}

	private void importTrip(){
		//make list of all the trips in the file and call the importTrip in gtfs to import each trip in list

	}

	private void searchStopId(){

	}

	private void searchRouteId(){

	}

	private boolean displayDistance(){
		return false;
	}

	private boolean displaySpeed(){
		return false;
	}

	private boolean displayRoute(){
		return false;
	}

	private boolean displayStop(){
		return false;
	}

	private boolean displayTrip(){
		return false;
	}

	private void plotCoord(){

	}

	private void plotLocation(){

	}

	private void showLegend(){

	}

	private void exportStops(){

	}

	private void exportRoutes(){

	}

	private void exportStopTimes(){

	}

	private void exportTrips(){

	}

	private void filterRoutes(){

	}

	private void routeVerify(){

	}

	private void stopVerify(){

	}

	private void tripVerify(){

	}

	private Trip searchForNextTrip(){
		return null;
	}

	private void update(){

	}

	private void search(){

	}


}