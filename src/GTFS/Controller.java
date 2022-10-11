package GTFS;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:31 PM
 */
public class Controller implements Initializable {
	private final GTFS gtfs;
	private Stage stage;
	private Popup importPu;
	@FXML
	private VBox dropImportVBox;
	private TextArea importEntry;
	@FXML
	private TextArea recentUploadDisplay;
	@FXML
	private Label recentUploadLabel;
	@FXML
	private TextField searchTF;
	@FXML
	private VBox mainVBox;
	@FXML
	private MenuItem stopMI;
	@FXML
	private MenuItem stopTimeMI;
	@FXML
	private MenuItem routeMI;
	@FXML
	private MenuItem tripMI;
	@FXML
	private MenuItem closeMI;
	@FXML
	private Menu menu;
	@FXML
	private BorderPane dropBorderPane;
	private String searchType;

	public Controller(){
		gtfs = new GTFS();
	}

	public void initialize(URL url, ResourceBundle rb) {
		searchTF.setDisable(true);
		recentUploadLabel.setVisible(false);
		initializeDropBox();
		initializeMenuItems();
		mainVBox.setStyle("-fx-background-color: " +
				"radial-gradient(focus-distance 0% , center 50% 50% , radius 40% , #E5E6E4, #F9F9F8);");
		dropBorderPane.setStyle("-fx-border-width: 3; -fx-border-color: #9e9e9e; -fx-border-style: segments(10, 10, 10, 10);");
	}

	@FXML
	private void importFiles() {
		FileChooser fc = new FileChooser();

		fc.setTitle("Import Files");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.txt"));
		List<File> files = fc.showOpenMultipleDialog(null);

		if (files != null) {
			for (File file : files) {
				gtfs.importFile(file);
			}
			recentUploadDisplay.setText(gtfs.getNewestImport());
			recentUploadLabel.setVisible(true);
		}
	}

	@FXML
	private void search() {
		if(menu.getText().equals(routeMI.getText())) {
			searchRouteId();
		} else if(menu.getText().equals(stopMI.getText())) {
			searchStopId();
		}
	}

	@FXML
	private void importPopup() {
		if (importPu != null && importPu.isShowing()) importPu.hide();

		importPu = new Popup();								//create popup prompt for user to type data into
		importPu.setWidth(400); importPu.setHeight(200);

		Pane background = new Pane();
		background.setPrefWidth(400); background.setPrefHeight(200);

		VBox stack = new VBox(18);
		stack.setPrefWidth(400); stack.setPrefHeight(200);
		stack.setAlignment(Pos.CENTER);
		stack.setPadding(new Insets(5, 5, 10, 5));

		addPopupVBoxComponents(stack);
		background.getChildren().add(stack);

		background.setStyle("-fx-background-radius: 8 8 8 8; -fx-background-color: " +
				"radial-gradient(focus-distance 0% , center 50% 50% , radius 40% , #E5E6E4, #F9F9F8);");
		DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#9e9e9e"), 15, 0.05, 0, 0);
		background.setEffect(shadow);

		importPu.getContent().add(background);
		importPu.show(stage);
	}

	private void addPopupVBoxComponents(VBox stack) {
		HBox header = new HBox(5);
		header.setPrefWidth(400); header.setPrefHeight(50);
		header.setAlignment(Pos.TOP_RIGHT);

		Button closeButton = new Button("Cancel");
		closeButton.setOnAction(e -> importPu.hide()); 	//close popup
		header.getChildren().addAll(closeButton);

		Label inputPrompt = new Label("Please Enter the Relevant Data Below");
		inputPrompt.setFont(new Font(15));

		importEntry = new TextArea();
		importEntry.setPromptText("Format: {Stop / Stop Time / Route / Trip}, data");
		importEntry.setPadding(new Insets(0, 10, 0, 10));

		Button send = new Button("Update");
		send.setOnAction(e -> {
			gtfs.updateText(importEntry.getText());					//update the user input into the gtfs data structures
			recentUploadDisplay.setText(gtfs.getNewestImport()); 	//display the imported data to user to show it was successful
			recentUploadLabel.setVisible(true); importPu.hide();
		});
		stack.getChildren().addAll(header, inputPrompt, importEntry, send);
	}

	@FXML
	private void exportFiles() {

	}

	private void searchStopId() {
	//TODO check for incorrect inputs.
		//In future this method could reveal lots of info. For now it gives info for #4
		int numTripsWithStop = gtfs.numTripsWithStop(searchTF.getText());
		System.out.println(numTripsWithStop);
		String searchRouteInfo = "Number of Trips with stop: " + numTripsWithStop  + "\n";
		recentUploadDisplay.setText(searchRouteInfo);

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

	private void initializeDropBox() {
		dropImportVBox.setOnDragOver(e -> {			// allows user to drag files into VBox
			Dragboard dropBox = e.getDragboard();

			if (dropBox.hasFiles()) {
				e.acceptTransferModes(TransferMode.COPY);
			} else {
				e.consume();
			}
		});
		dropImportVBox.setOnDragDropped(e -> {			// handles files once dropped into VBox
			Dragboard dropBox = e.getDragboard();

			if (dropBox.hasFiles()) {
				boolean imported = false;
				for (File file : dropBox.getFiles()) {
					if (file.getName().endsWith(".txt")) {
						gtfs.importFile(file);
						imported = true;
					}
				}
				if (imported) {
					recentUploadDisplay.setText(gtfs.getNewestImport());
					recentUploadLabel.setVisible(true);
				}
			}
			e.consume();
		});
	}

	private void initializeMenuItems() {
		stopMI.setOnAction(e -> {
			menu.setText("Stop"); searchType = "Stop";
			searchTF.setDisable(false);
		});
		stopTimeMI.setOnAction(e -> {
			menu.setText("Time"); searchType = "Stop Time";
			searchTF.setDisable(false);
		});
		routeMI.setOnAction(e -> {
			menu.setText("Route"); searchType = "Route";
			searchTF.setDisable(false);
		});
		tripMI.setOnAction(e -> {
			menu.setText("Trip"); searchType = "Trip";
			searchTF.setDisable(false);
		});
		closeMI.setOnAction(e -> {
			menu.setText("Select"); searchType = "";
			searchTF.setDisable(true);
			searchTF.setText("");
		});
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