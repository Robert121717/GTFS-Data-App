package GTFS;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
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
	private final List<String> recentUploadList = new ArrayList<>();

	@FXML
	private HBox mainHBox;
	private final VBox updateMenu = new VBox(10);
	private TranslateTransition updateTranslate;
	private ScaleTransition updateScale;

	private final VBox exportMenu = new VBox();
	private TranslateTransition exportTranslate;
	private List<ToggleButton> options;
	private final long translateDuration = 400;
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
		initExportOptions();
		initializeExportMenu();

		initializeDropBox();
	}

	@FXML
	private void updatePopup() {
		if (mainHBox.getChildren().contains(updateMenu)) {
			closePopup(updateTranslate, updateMenu);
			return;
		}
		long delay = 0;
		if (mainHBox.getChildren().contains(exportMenu)) {
			closePopup(exportTranslate, exportMenu);
			delay = translateDuration;
		}
		Timer myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(() -> {
					updateMenu.setTranslateX(0);
					mainHBox.getChildren().add(updateMenu);
					updateScale.setRate(1);
					updateScale.setCycleCount(1);
					updateScale.play();
				});
			}
		}, delay);
	}

	private void initializeUpdateMenu() {
		final int height = 150;
		final int width = 285;

		//TODO add button for choosing file type

		updateMenu.setId("update-menu");
		updateMenu.setMinWidth(width); updateMenu.setMinHeight(height);
		updateMenu.setMaxWidth(width); updateMenu.setMaxHeight(height);

		updateMenu.setAlignment(Pos.TOP_CENTER);
		updateMenu.setPadding(new Insets(8, 8, 8, 8));
		addUpdateMenuComponents();

		updateScale = new ScaleTransition(Duration.millis(300), updateMenu);
		updateScale.setFromX(0); updateScale.setToX(1);
		updateTranslate = new TranslateTransition(Duration.millis(translateDuration), updateMenu);

		updateTranslate.setFromX(0);
		updateTranslate.setToX(3 * width);
	}

	private void addUpdateMenuComponents() {
		HBox header = new HBox(5);
		header.setPrefWidth(260); header.setPrefHeight(50);
		header.setAlignment(Pos.TOP_RIGHT);

		Label inputPrompt = new Label("Enter New Data");
		inputPrompt.setFont(new Font(14));
		inputPrompt.setPadding(new Insets(10, 90, 0, 0));

		Button closeButton = new Button("Cancel");
		closeButton.setId("update-cancel-button");
		closeButton.setOnAction(e -> closePopup(updateTranslate, updateMenu));
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

	@FXML
	private void exportPopup() {
		if (mainHBox.getChildren().contains(exportMenu)) {
			closePopup(exportTranslate, exportMenu);
			return;
		}
		long delay = 0;
		if (mainHBox.getChildren().contains(updateMenu)) {
			closePopup(updateTranslate, updateMenu);
			delay = translateDuration;
		}
		Timer myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(() -> {
					mainHBox.getChildren().add(exportMenu);
					exportTranslate.setRate(1);
					exportTranslate.play();
					for (ToggleButton option : options) {
						option.setSelected(false);
					}
				});
			}
		}, delay);
	}

	private void initializeExportMenu() {
		final int height = 260;
		final int width = 110;

		exportMenu.setId("export-menu");
		exportMenu.setMinWidth(width); exportMenu.setMinHeight(height);
		exportMenu.setMaxWidth(width); exportMenu.setMaxHeight(height);

		exportMenu.setAlignment(Pos.TOP_CENTER);
		addExportMenuComponents();

		double translateX = mainHBox.getPrefWidth();
		exportMenu.setTranslateX(translateX);

		exportTranslate = new TranslateTransition(Duration.millis(translateDuration), exportMenu);

		exportTranslate.setFromX(translateX);
		exportTranslate.setToX(200);
	}

	private void addExportMenuComponents() {
		VBox header = new VBox();
		header.setMinHeight(32); header.setMaxHeight(32);
		header.setPadding(new Insets(4, 0, 8, 0));
		header.setAlignment(Pos.CENTER);
		header.setId("export-header");

		header.setOnMouseClicked(e -> {
			closePopup(exportTranslate, exportMenu);
			for (ToggleButton option : options) {
				option.setSelected(false);
			}
		});
		Label close = new Label("Cancel");
		close.setFont(new Font(16));
		header.getChildren().add(close);

		Separator headerLine = new Separator(Orientation.HORIZONTAL);

		VBox optionsVBox = new VBox(3);
		optionsVBox.setPadding(new Insets(15, 14, 15, 8));
		optionsVBox.setAlignment(Pos.CENTER);
		optionsVBox.getChildren().addAll(options);

		VBox send = new VBox();
		send.setId("export-send-vbox");
		send.setMinHeight(32); send.setMaxHeight(32);
		send.setAlignment(Pos.CENTER);

		Separator exportLine = new Separator(Orientation.HORIZONTAL);

		Label export = new Label("Export");
		export.setFont(new Font(16));
		send.setOnMouseClicked(e -> {
			initializeFileExport();
			closePopup(exportTranslate, exportMenu);

			for (ToggleButton option : options) {
				option.setSelected(false);
			}
		});
		send.getChildren().add(export);

		exportMenu.getChildren().addAll(header, headerLine, optionsVBox, exportLine, send);
	}

	private void initExportOptions() {
		options = new ArrayList<>();
		options.add(new ToggleButton("Routes"));
		options.add(new ToggleButton("Stops"));
		options.add(new ToggleButton("Stop Times"));
		options.add(new ToggleButton("Trips"));

		for (ToggleButton option : options) {
			option.setPrefWidth(90);
			option.setPrefHeight(40);
		}
	}

	private void closePopup(TranslateTransition translate, VBox node) {
		int rate = node == updateMenu ? 1 : -1;
		translate.setRate(rate);
		translate.play();

		Timer myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(() -> mainHBox.getChildren().remove(node));
			}
		}, translateDuration);
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
		if (listSize == 0) {
			return;
		}
		int currentIndex = recentUploadList.indexOf(recentUploadLabel.getText());

		int newIndex = currentIndex - 1 < 0 ? listSize - 1 : currentIndex - 1;
		String prevUpload = recentUploadList.get(newIndex);

		recentUploadLabel.setText(prevUpload);
	}

	@FXML
	private void nextRecentUpload() {
		int listSize = recentUploadList.size();
		if (listSize == 0) {
			return;
		}
		int currentIndex = recentUploadList.indexOf(recentUploadLabel.getText());

		int newIndex = currentIndex + 1 > listSize - 1 ? 0 : currentIndex + 1;
		String nextUpload = recentUploadList.get(newIndex);

		recentUploadLabel.setText(nextUpload);
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

	/**
	 * Gets the requested files from the GTFS class and saves the data to a file.
	 */
	private void initializeFileExport() {
		for (ToggleButton option : options) {
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
}