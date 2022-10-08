package GTFS;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

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
	private Button importFile;

	public Controller(){
		gtfs = new GTFS();
	}

	/**
	 *checks what kind of file we are importing and calls the related methods
	 * @param
	 */
	@FXML
	private void importFiles(){
		//TODO
		//if stop file call importstop method.
		//else if trip file call importtrip
		//else if route file call importroute
		//else if stopTime file call importstopTime
		//else error cannot import that file

		File file;
		String header1 = "";
		String header2 = "";
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open File");
		chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
		List<File> files = chooser.showOpenMultipleDialog(null);

		try{
			int size = files.size();

			for(int i = 0; i < size; i++) {
				file = files.get(i);
				if(file != null){
					Scanner in = new Scanner(file);
					ArrayList<String> lines = new ArrayList<>();
					while (in.hasNextLine()) {
						lines.add(in.nextLine());
					}
					if (lines.size() < 2) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Incorrect Format");
						alert.setContentText("File has too few lines, cannot process");
						alert.showAndWait();
						continue;
					}

					String[] splitHeader = lines.get(1).split(",");
					header1 = splitHeader[0];
					header2 = splitHeader[1];
					if(header1.equalsIgnoreCase("stop_id")){
						importStop(file);
					} else if (header1.equalsIgnoreCase("route_id")
							&& header2.equalsIgnoreCase("agency_id")){
						importRoute(file);

					} else if (header1.equalsIgnoreCase("trip_id")
							&& header2.equalsIgnoreCase("arrival_time")){
						importStopTime(file);
					} else if (header1.equalsIgnoreCase("route_id")
							&& header2.equalsIgnoreCase("service_id")){
						importTrip(file);
					} else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Incorrect Format");
						alert.setContentText("Cannot import this file");
						alert.showAndWait();
					}
					in.close();
				}
			}
		} catch (FileNotFoundException ex){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("File Not Found");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * NOT IMPLEMENTED
	 * @param
	 */
	private void exportFiles(){

	}

	/**
	 *
	 * @param
	 */
	private void importRoute(File file){
		//TODO
		//make file into a list of Route objects
	//make list of all the routes in the file and call the importRoute in gtfs to import each stop in list
	}

	private void importStop(File file){
		//TODO
		//make file into a list of Stop objects
		//make list of all the stops in the file and call the importStop in gtfs to import each stop in list

	}

	private void importStopTime(File file){
		//TODO
		//make file into a list of StopTime objects
		//make list of all the stopTimes in the file and call the importStopTimes in gtfs to import each stoptime in list

	}

	private void importTrip(File file){
		//TODO
		//make file into a list of Route objects
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