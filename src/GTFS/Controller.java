package GTFS;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 * @created 05-Oct-2022 8:14:31 PM
 */
public class Controller implements Initializable {
	private final GTFS gtfs;
	private Stage stage;
	private List<String> recentUploadList = new ArrayList<>();

	@FXML
	private VBox rootVBox;
	@FXML
	private HBox mainHBox;
	private VBox updateMenu = new VBox(10);
	private TranslateTransition updateTranslate;

	private VBox exportMenu = new VBox();
	@FXML
	private VBox dropImportVBox;
	private TextArea input;
	@FXML
	private TextArea textDisplay;
	@FXML
	private TextField searchTF;
	@FXML
	private Label recentUploadLabel;
	@FXML
	private Label leftRecent;
	@FXML
	private Label rightRecent;
	@FXML
	private BorderPane dropBorderPane;

	public Controller(){
		gtfs = new GTFS();
	}

	/**
	 * Initializes the components in the UI when an object of this class is created.
	 */
	public void initialize(URL url, ResourceBundle rb) {
		leftRecent.setVisible(false);
		rightRecent.setVisible(false);

		recentUploadLabel.setVisible(false);
		searchTF.setDisable(true);

		initializeUpdateMenu();
		initializeDropBox();
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
				recentUploadList.addAll(List.of(gtfs.getNewestImports().split("\\n")));
			}
			searchTF.setDisable(false);
			showRecentUploadList();
		}
	}

	private void showRecentUploadList() {
		leftRecent.setVisible(true);
		rightRecent.setVisible(true);
		recentUploadLabel.setVisible(true);
	}

	@FXML
	private void prevRecentUpload() {
		int listSize = recentUploadList.size();
		if (listSize > 1) {
			int currentIndex = recentUploadList.indexOf(recentUploadLabel.getText());

			int newIndex = currentIndex - 1 < 0 ? listSize - 1 : currentIndex - 1;
			String prevUpload = recentUploadList.get(newIndex);

			recentUploadLabel.setText(prevUpload);
		}
	}

	@FXML
	private void nextRecentUpload() {
		int listSize = recentUploadList.size();
		if (listSize > 1) {
			int currentIndex = recentUploadList.indexOf(recentUploadLabel.getText());

			int newIndex = currentIndex + 1 > listSize - 1 ? 0 : currentIndex + 1;
			String nextUpload = recentUploadList.get(newIndex);

			recentUploadLabel.setText(nextUpload);
		}
	}

	@FXML
	private void search() {
		if(searchTF.getText().trim().equals("")) {
			textDisplay.setText("");
		} else {
			searchStopId();
		}
		searchTF.setText("");
	}

	@FXML
	private void showUpdatePopup() {

		if (mainHBox.getChildren().contains(updateMenu)) {
			closePopup(updateTranslate, updateMenu);

		} else {
			mainHBox.getChildren().add(updateMenu);
			updateTranslate.setRate(1);
			updateTranslate.play();
		}
	}

	private void initializeUpdateMenu() {
		final int height = 200;
		final int width = 285;

		updateMenu.setId("update-vbox");
		updateMenu.setMinWidth(width); updateMenu.setMinHeight(height);
		updateMenu.setPrefWidth(width); updateMenu.setPrefHeight(height);
		updateMenu.setMaxWidth(width); updateMenu.setMaxHeight(height);

		updateMenu.setAlignment(Pos.TOP_CENTER);
		updateMenu.setPadding(new Insets(8, 25, 10, 8));
		addUpdateMenuComponents();

		double translateX = rootVBox.getPrefWidth();
		updateMenu.setTranslateX(translateX);

		updateTranslate = new TranslateTransition(Duration.millis(550), updateMenu);

		updateTranslate.setFromX(translateX);
		updateTranslate.setToX(25);
	}

	private void addUpdateMenuComponents() {
		HBox header = new HBox(5);
		header.setPrefWidth(260); header.setPrefHeight(50);
		header.setAlignment(Pos.TOP_RIGHT);
		header.setPadding(new Insets(0, 10, 0, 0));

		Label inputPrompt = new Label("Enter New Data");
		inputPrompt.setFont(new Font(14));
		inputPrompt.setPadding(new Insets(10, 70, 0, 10));

		Button closeButton = new Button("Cancel");
		closeButton.setId("update-cancel-button");
		closeButton.setOnAction(e -> {
			closePopup(updateTranslate, updateMenu);
		});
		header.getChildren().addAll(inputPrompt, closeButton);

		input = new TextArea();
		input.setPromptText("Enter new data");
		input.setPadding(new Insets(0, 10, 0, 10));

		Button send = new Button("Update");
		send.setId("update-send-button");
		send.setOnAction(e -> {
			gtfs.updateText(input.getText());	// TODO update files
			closePopup(updateTranslate, updateMenu);
			input.clear();
		});
		updateMenu.getChildren().addAll(header, input, send);
	}

	private void closePopup(TranslateTransition translate, VBox node) {
		translate.setRate(-1);
		translate.play();
		Timer myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(() -> {
					mainHBox.getChildren().remove(node);
				});
			}
		}, 550);
	}

//	@FXML
//	private void exportPopup() {
//		final int width = 230;
//		final int height = 310;
//
//		if (exportPu != null && exportPu.isShowing()) exportPu.hide();
//		else if (importPu != null && importPu.isShowing()) importPu.hide();
//
//		exportPu = new Popup();
//		exportPu.setHeight(height); exportPu.setWidth(width);
//
//		Pane background = new Pane();
//		background.setPrefHeight(height); background.setPrefWidth(width);
//
//		VBox stack = new VBox(5);
//		stack.setPrefHeight(height); stack.setPrefWidth(width);
//		stack.setAlignment(Pos.TOP_CENTER);
//		stack.setPadding(new Insets(8, 8, 8, 8));
//
//		addExportPuComponents(stack);
//		background.getChildren().add(stack);
//
//		background.setStyle("-fx-background-radius: 8 8 8 8; -fx-background-color: " +
//				"radial-gradient(focus-distance 0% , center 50% 50% , radius 60% , #E5E6E4, #F9F9F8);");
//		DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#9e9e9e"), 15, 0.05, 0, 0);
//		background.setEffect(shadow);
//
//		exportPu.getContent().add(background);
//		exportPu.show(stage);
//	}
//
//	private void addExportPuComponents(VBox stack) {
//		HBox header = new HBox();
//		header.setPrefWidth(230); header.setPrefHeight(30);
//		header.setAlignment(Pos.TOP_RIGHT);
//
//		Button closeButton = new Button("Cancel");
//		closeButton.setOnAction(e -> exportPu.hide()); 	//close popup
//		header.getChildren().add(closeButton);
//
//		Label instruct = new Label("Please Select the Files to Export");
//		instruct.setWrapText(true); instruct.setPrefWidth(150);
//		instruct.setFont(new Font(16));
//		instruct.setTextAlignment(TextAlignment.CENTER);
//
//		List<CheckBox> options = getExportCheckBoxes();
//
//		VBox centerStack = new VBox(8);
//		centerStack.setAlignment(Pos.CENTER_LEFT);
//		centerStack.setPadding(new Insets(15, 0, 15, 60));
//		centerStack.getChildren().addAll(options);
//
//		Button send = new Button("Export");
//		send.setOnAction(e -> {
//			exportPu.hide();
//			initializeFileExport(options);
//		});
//
//		stack.getChildren().addAll(header, instruct, centerStack, send);
//	}

	/**
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
					export(data);
				}
			}
		}
	}

	/**
	 * helper method to export data to files for the user to see
	 * @author Cody Morrow
	 * @param data - what is to be stored for the user
	 */
	private void export(String data) {
		try {
			FileChooser out = new FileChooser();
			out.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.csv", "*.txt"));
			out.setTitle("File Chooser");
			File file = out.showSaveDialog(null);
			if (file != null) {
				FileWriter writer = new FileWriter(file);
				writer.write(data);
				writer.close();
			}

		} catch (IOException e){
			newAlert(Alert.AlertType.ERROR, "Error Dialog", "File Error",
					"A problem with the location of the export was found.");
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

	private void searchStopId() {
		String stopId = searchTF.getText();

		int numTripsWithStop = gtfs.numTripsWithStop(searchTF.getText().toUpperCase(Locale.ROOT));
		String routeIdWithStop = gtfs.routesWithStop(searchTF.getText().toUpperCase(Locale.ROOT));
		if(gtfs.hasStopTime() && gtfs.hasTrip()) {
			if(routeIdWithStop.equals("No Routes with StopID")) {
				String searchRouteInfo = "Stop ID: " + searchTF.getText().toUpperCase(Locale.ROOT) + "\n\n" +
						"Number of Trips with stop: " + numTripsWithStop + "\n\n" + routeIdWithStop  + searchNextTrips(stopId);
				textDisplay.setText(searchRouteInfo);
			} else {
				String searchRouteInfo = "Stop ID: " + searchTF.getText().toUpperCase(Locale.ROOT) + "\n\n" +
						"Number of Trips with stop: " + numTripsWithStop + "\n\n" + "Routes with Stop:" + "\n" +
						routeIdWithStop + searchNextTrips(stopId);
				textDisplay.setText(searchRouteInfo);
			}

		} else if(gtfs.hasStopTime() && !gtfs.hasTrip()) {
			String searchRouteInfo = "Stop ID: " + searchTF.getText().toUpperCase(Locale.ROOT) + "\n\n" +
					"Number of Trips with stop: " + numTripsWithStop + searchNextTrips(stopId) + "\n\n" +
					"NOTICE: Must import a trip file to see more data.";
			textDisplay.setText(searchRouteInfo);

		} else if(!gtfs.hasStopTime() && !gtfs.hasTrip()) {
			String searchRouteInfo = "NOTICE: Must import StopTime and Trip files to see data.";
			textDisplay.setText(searchRouteInfo);
		}
	}

	private String searchNextTrips(String stopId) {
		ArrayList<Object[]> trips = gtfs.getNextTrips(stopId);

		String content;
		if (!trips.isEmpty()) {
			String header;
			if (trips.size() > 1) {
				header = "\nNext trips to arrive at this stop:";
			} else {
				header = "\nNext trip to arrive at this stop:";
			}
			StringBuilder text = new StringBuilder(header);

			for (Object[] array : trips) {
				StopTime stop = (StopTime) array[0];
				String tripId = stop.getTripId();
				String arrivalTime = formatTimeStamp(stop.getArrivalTime());

				text.append("\n  -ID: ").append(tripId)
						.append("\n  -Arrival time: ").append(arrivalTime)
						.append("\n");
			}
			content = text.toString();
		} else {
			content = "\n\nNo subsequent trips to the given stop ID were found for today.";
		}
		return content;
	}

	public String formatTimeStamp(String timeStamp) {
		String[] split = timeStamp.split(":");

		int hour = Integer.parseInt(split[0]);
		String minute = split[1];
		String second = split[2];

		boolean am;
		if (hour == 24) {
			hour = 12;
			am = true;
		} else if (hour == 25) {
			hour = 1;
			am = true;
		} else if (hour > 12) {
			hour -= 12;
			am = false;
		} else if (hour == 0) {
			hour = 12;
			am = true;
		} else {
			am = true;
		}
		return hour + ":" + minute + ":" + second + " " + (am ? "am" : "pm");
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
						recentUploadList.addAll(List.of(gtfs.getNewestImports().split("\\n")));
						imported = true;
					}
				}
				if (imported) {
					searchTF.setDisable(false);
					recentUploadLabel.setVisible(true);
					showRecentUploadList();
				}
			}
			e.consume();
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