package GTFS;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import javafx.scene.text.TextAlignment;
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
	private Popup exportPu;

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
	private MenuItem routeMI;
	@FXML
	private MenuItem closeMI;
	@FXML
	private Menu menu;
	@FXML
	private BorderPane dropBorderPane;

	public Controller(){
		gtfs = new GTFS();
	}

	/**
	 * Initializes the components in the UI when an object of this class is created.
	 */
	public void initialize(URL url, ResourceBundle rb) {
		searchTF.setDisable(true);
		recentUploadLabel.setVisible(false);
		initializeDropBox();
		initializeMenuItems();
		mainVBox.setStyle("-fx-background-color: " +
				"radial-gradient(focus-distance 0% , center 50% 50% , radius 40% , #E5E6E4, #F9F9F8);");
		dropBorderPane.setStyle("-fx-border-width: 3; -fx-border-color: #9e9e9e; -fx-border-style: segments(10, 10, 10, 10);");
	}

	/**
	 * Opens a file chooser for the user to select files they'd like to import,
	 * then sends them to the GTFS database.
	 */
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
		recentUploadLabel.setVisible(false);

	}

	/**
	 * @author Robert Schmidt
	 * Creates and displays a popup allowing the user to manually update data in the GTFS files.
	 */
	@FXML
	private void importPopup() {
		final int height = 200;
		final int width = 400;

		if (importPu != null && importPu.isShowing()) importPu.hide();
		else if (exportPu != null && exportPu.isShowing()) exportPu.hide();

		importPu = new Popup();								//create popup prompt for user to type data into
		importPu.setWidth(width); importPu.setHeight(height);

		Pane background = new Pane();
		background.setPrefWidth(width); background.setPrefHeight(height);

		VBox stack = new VBox(18);
		stack.setPrefWidth(width); stack.setPrefHeight(height);
		stack.setAlignment(Pos.CENTER);
		stack.setPadding(new Insets(8, 8, 10, 8));

		addImportPuComponents(stack);
		background.getChildren().add(stack);

		background.setStyle("-fx-background-radius: 8 8 8 8; -fx-background-color: " +
				"radial-gradient(focus-distance 0% , center 50% 50% , radius 40% , #E5E6E4, #F9F9F8);");
		DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#9e9e9e"), 15, 0.05, 0, 0);
		background.setEffect(shadow);

		importPu.getContent().add(background);
		importPu.show(stage);
	}

	/**
	 * @author Robert Schmidt
	 * Helper method to importPopup().
	 * Creates and adds the nodes to the main component in the popup to give it the necessary functionality.
	 * Including:
	 * 		a close window button,
	 * 		a text area for user input
	 * 		an update button, which well send the data to the GTFS files.
	 * @param stack Main component of the popup.
	 */
	private void addImportPuComponents(VBox stack) {
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

	/**
	 * @author Robert Schmidt
	 * Creates and displays a popup allowing users to select files to export.
	 */
	@FXML
	private void exportPopup() {
		final int width = 230;
		final int height = 310;

		if (exportPu != null && exportPu.isShowing()) exportPu.hide();
		else if (importPu != null && importPu.isShowing()) importPu.hide();

		exportPu = new Popup();
		exportPu.setHeight(height); exportPu.setWidth(width);

		Pane background = new Pane();
		background.setPrefHeight(height); background.setPrefWidth(width);

		VBox stack = new VBox(5);
		stack.setPrefHeight(height); stack.setPrefWidth(width);
		stack.setAlignment(Pos.TOP_CENTER);
		stack.setPadding(new Insets(8, 8, 8, 8));

		addExportPuComponents(stack);
		background.getChildren().add(stack);

		background.setStyle("-fx-background-radius: 8 8 8 8; -fx-background-color: " +
				"radial-gradient(focus-distance 0% , center 50% 50% , radius 40% , #E5E6E4, #F9F9F8);");
		DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#9e9e9e"), 15, 0.05, 0, 0);
		background.setEffect(shadow);

		exportPu.getContent().add(background);
		exportPu.show(stage);
	}

	/**
	 * @author Robert Schmidt
	 * Helper method to exportPopup().
	 * Creates and adds the nodes to the main component in the popup to give it the necessary functionality.
	 * Including:
	 * 		a close window button,
	 * 		checkboxes to select which files to export,
	 * 		an export button to begin the process of exporting the files.
	 * @param stack Main component of the popup.
	 */
	private void addExportPuComponents(VBox stack) {
		HBox header = new HBox();
		header.setPrefWidth(230); header.setPrefHeight(30);
		header.setAlignment(Pos.TOP_RIGHT);

		Button closeButton = new Button("Cancel");
		closeButton.setOnAction(e -> exportPu.hide()); 	//close popup
		header.getChildren().add(closeButton);

		Label instruct = new Label("Please Select the Files to Export");
		instruct.setWrapText(true); instruct.setPrefWidth(150);
		instruct.setFont(new Font(16));
		instruct.setTextAlignment(TextAlignment.CENTER);

		List<CheckBox> options = getExportCheckBoxes();

		VBox centerStack = new VBox(8);
		centerStack.setAlignment(Pos.CENTER_LEFT);
		centerStack.setPadding(new Insets(15, 0, 15, 60));
		centerStack.getChildren().addAll(options);

		Button send = new Button("Export");
		send.setOnAction(e -> {
			exportPu.hide();
			initializeFileExport(options);
		});

		stack.getChildren().addAll(header, instruct, centerStack, send);
	}

	/**
	 * @author Robert Schmidt
	 * Gets the requested files from the GTFS class and saves the data to a file.
	 * @param options List of CheckBoxes, where each checkbox represents a file that can be exported.
	 */
	private void initializeFileExport(List<CheckBox> options) {
		for (CheckBox option : options) {
			if (option.isSelected()) {
				String data = gtfs.exportFile(option.getText());

				if (data.equals("")) {
					newAlert(Alert.AlertType.ERROR,
							"Error Dialog",
							"The requested data could not be found.",
							"Please import the data first.");
				} else {
					export(data, option.getText());
				}
			}
		}
	}

	/**
	 * helper method to export data to files for the user to see
	 * @author Cody Morrow
	 * @param data - what is to be stored for the user
	 * @param fileName - name what is being exported to use as file name
	 */
	private void export(String data, String fileName) {
		try(FileWriter out = new FileWriter(fileName + ".txt")) {
			out.write(data);

		} catch (IOException e){
			newAlert(Alert.AlertType.ERROR, "Error Dialog", "File Error",
					"A problem with the location of the export was found");
		}
	}

	private List<CheckBox> getExportCheckBoxes() {
		List<CheckBox> options = new ArrayList<>();
		options.add(new CheckBox("Stops"));
		options.add(new CheckBox("Stop Times"));
		options.add(new CheckBox("Routes"));
		options.add(new CheckBox("Trips"));

		for (CheckBox option : options) {
			option.setPrefWidth(120);
			option.setPrefHeight(25);
			option.setStyle("-fx-font-size: 14;");
		}
		return options;
	}

	private void displayFile(String text) {

	}
	private void searchStopId() {
		int numTripsWithStop = gtfs.numTripsWithStop(searchTF.getText().toUpperCase(Locale.ROOT));
		String routeIdWithStop = gtfs.routesWithStop(searchTF.getText().toUpperCase(Locale.ROOT));
		if(gtfs.hasStopTime() && gtfs.hasTrip()) {
			String searchRouteInfo = "Stop ID: " + searchTF.getText().toUpperCase(Locale.ROOT) + "\n\n" +
					"Number of Trips with stop: " + numTripsWithStop + "\n\n" + "Routes with Stop:" + "\n" +
					routeIdWithStop;
			recentUploadDisplay.setText(searchRouteInfo);
		} else if(gtfs.hasStopTime() && !gtfs.hasTrip()) {
			String searchRouteInfo = "Stop ID: " + searchTF.getText().toUpperCase(Locale.ROOT) + "\n\n" +
					"Number of Trips with stop: " + numTripsWithStop + "\n\n" +
					"NOTICE: Must import a trip file to see more data.";
			recentUploadDisplay.setText(searchRouteInfo);

		} else if(!gtfs.hasStopTime() && !gtfs.hasTrip()) {
			String searchRouteInfo = "NOTICE: Must import StopTime and Trip files to see data.";
			recentUploadDisplay.setText(searchRouteInfo);
		}


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

	private void filterRoutes(){

	}

	/**
	 * Adds event handlers to the drop box shown in the UI,
	 * allowing the user to drag and drop files onto the screen to import them.
	 */
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

	/**
	 * Adds event handlers to the menu items shown in the UI
	 */
	private void initializeMenuItems() {
		stopMI.setOnAction(e -> {
			menu.setText("Stop");
			searchTF.setDisable(false);
		});

		routeMI.setOnAction(e -> {
			menu.setText("Route");
			searchTF.setDisable(false);
		});

		closeMI.setOnAction(e -> {
			menu.setText("Select");
			searchTF.setDisable(true);
			searchTF.setText("");
		});
	}

	/**
	 * Assigns the current stage to an instance variable of this class
	 * @param stage The stage currently in use
	 */
	protected void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Displays an alert when called
	 * @param type Alert type
	 * @param title Title of alert
	 * @param header Header of alert
	 * @param content Content within the alert
	 */
	protected static void newAlert(Alert.AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}