package GTFS;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:31 PM
 */
public class Controller implements Initializable {
	private final GTFS gtfs;
	private Stage stage;

	private Popup importPu;

	@FXML
	private TextArea importEntry;
	@FXML
	private TextArea recentUploadDisp;
	@FXML
	private Label recentUploadLabel;

	@FXML
	private Label test;

	@FXML
	private Button importFile;

	public Controller(){
		gtfs = new GTFS();
	}

	public void initialize(URL url, ResourceBundle rb) {
		recentUploadLabel.setVisible(false);
	}

	/**
	 *checks what kind of file we are importing and calls the related methods
	 * @param
	 */
	@FXML
	private void importFiles() {
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

//		try{
//			int size = files.size();
//
//			for(int i = 0; i < size; i++) {
//				file = files.get(i);
//				if(file != null){
//					Scanner in = new Scanner(file);
//					ArrayList<String> lines = new ArrayList<>();
//					while (in.hasNextLine()) {
//						lines.add(in.nextLine());
//					}
//					if (lines.size() < 2) {
//						Alert alert = new Alert(Alert.AlertType.ERROR);
//						alert.setTitle("Error Dialog");
//						alert.setHeaderText("Incorrect Format");
//						alert.setContentText("File has too few lines, cannot process");
//						alert.showAndWait();
//						continue;
//					}
//
//					String[] splitHeader = lines.get(1).split(",");
//					header1 = splitHeader[0];
//					header2 = splitHeader[1];
//					if(header1.equalsIgnoreCase("stop_id")){
//
//					} else if (header1.equalsIgnoreCase("route_id")
//							&& header2.equalsIgnoreCase("agency_id")){
//
//
//					} else if (header1.equalsIgnoreCase("trip_id")
//							&& header2.equalsIgnoreCase("arrival_time")){
//
//					} else if (header1.equalsIgnoreCase("route_id")
//							&& header2.equalsIgnoreCase("service_id")){
//
//					} else {
//						Alert alert = new Alert(Alert.AlertType.ERROR);
//						alert.setTitle("Error Dialog");
//						alert.setHeaderText("Incorrect Format");
//						alert.setContentText("Cannot import this file");
//						alert.showAndWait();
//					}
//					in.close();
//				}
//			}
//		} catch (FileNotFoundException ex){
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setTitle("Error Dialog");
//			alert.setHeaderText("File Not Found");
//			alert.setContentText(ex.getMessage());
//			alert.showAndWait();
//		}
	}

	@FXML
	private void importPopup() {
		if (importPu != null && importPu.isShowing()) importPu.hide();

		importPu = new Popup();								//create prompt for user to type data into
		importPu.setWidth(400); importPu.setHeight(200);

		Pane background = new Pane();
		background.setPrefWidth(400);
		background.setPrefHeight(200);

		VBox stack = new VBox(18);
		stack.setPrefWidth(400); stack.setPrefHeight(200);
		stack.setAlignment(Pos.CENTER);
		stack.setPadding(new Insets(5, 5, 10, 5));

		HBox header = new HBox(5);
		header.setPrefWidth(400); header.setPrefHeight(50);
		header.setAlignment(Pos.TOP_LEFT);

		Button closeButton = new Button("Minimize");
		closeButton.setOnAction(e -> importPu.hide()); 			//close popup
		header.getChildren().addAll(closeButton,
				new Label("Please enter the data you'd like to import below:"));
		Label formatRequired = new Label("Format: {Stop Time/Stop/Route/Trip}, {data being imported}");

		importEntry = new TextArea();
		importEntry.setPadding(new Insets(0, 10, 0, 10));

		Button send = new Button("Import");
		send.setOnAction(e -> {
			gtfs.importText(importEntry.getText());				//import the user input into the gtfs data structures
			recentUploadDisp.setText(gtfs.getNewestImport()); 		//display the imported data to user to show it was successful
			recentUploadLabel.setVisible(true);
			importPu.hide();
		});

		stack.getChildren().addAll(header, formatRequired, importEntry, send);
		background.getChildren().add(stack);

		background.setStyle("-fx-background-color: white;");
		DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 15, 0.05, 0, 0);
		background.setEffect(shadow);

		importPu.getContent().add(background);
		importPu.show(stage);
	}

	@FXML
	private void exportFiles() {

	}


	private void searchStopId() {

	}

	private void searchRouteId() {

	}

	private boolean displayDistance() {
		return false;
	}

	private boolean displaySpeed() {
		return false;
	}

	private boolean displayRoute() {
		return false;
	}

	private boolean displayStop() {
		return false;
	}

	private boolean displayTrip() {
		return false;
	}

	private void plotCord() {

	}

	private void plotLocation() {

	}

	private void showLegend(){

	}

	private void exportFile() {

	}

	private void filterRoutes(){

	}

	private Trip searchForNextTrip(){
		return null;
	}

	private void search(){

	}

	protected void setStage(Stage stage) {
		this.stage = stage;
	}

	protected static void newAlert(Alert.AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}