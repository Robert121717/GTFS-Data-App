package GTFS;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
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
	@FXML
	private HBox updateHBox;
	private boolean searchSelected = true;
	private boolean searchRouteSelected = false;
	@FXML
	private MenuBar searchMenuBar;
	@FXML
	private Menu searchMenu;
	private final Menu updateOptions = new Menu("File Type");

	@FXML
	private Button exportButton;
	private Tooltip exportHint;
	private final VBox exportMenu = new VBox();
	private TranslateTransition exportTranslate;
	private List<ToggleButton> options;
	private final long translateDuration = 400;
	@FXML
	private VBox dropImportVBox;
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
		exportHint = new Tooltip("Please import a file before exporting");
		exportHint.setShowDelay(Duration.millis(200));
		exportHint.setHideDelay(Duration.ZERO);

		exportButton.setTooltip(exportHint);

		leftRecent.setVisible(false);
		rightRecent.setVisible(false);

		recentUploadLabel.setVisible(false);
		searchTF.setDisable(true);

		initExportOptions();
		initializeExportMenu();
		initializeDropBox();
	}

	@FXML
	private void exportPopup(ActionEvent e) {
		if (mainHBox.getChildren().contains(exportMenu)) {
			closePopup();
			return;
		}
		mainHBox.getChildren().add(exportMenu);
		exportTranslate.setRate(1);
		exportTranslate.play();

		for (ToggleButton option : options) {
			option.setSelected(false);
		}
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
		exportTranslate.setToX(130);
	}

	private void addExportMenuComponents() {
		VBox header = new VBox();
		header.setMinHeight(32); header.setMaxHeight(32);
		header.setPadding(new Insets(4, 0, 8, 0));
		header.setAlignment(Pos.CENTER);
		header.setId("export-header");

		header.setOnMouseClicked(e -> {
			closePopup();
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
			closePopup();

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

	private void closePopup() {
		exportTranslate.setRate(-1);
		exportTranslate.play();

		Timer myTimer = new Timer();
		myTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				Platform.runLater(() -> mainHBox.getChildren().remove(exportMenu));
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
				addToRecentUploadList();
			}
			allowFileInteraction();
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

	private void allowFileInteraction() {
		searchTF.setDisable(false);
		searchTF.setPromptText("Search by Stop ID");
		searchMenuBar.setDisable(false);

		exportButton.setOnAction(this::exportPopup);
		Tooltip.uninstall(exportButton, exportHint);
	}

	@FXML
	private void updateSelected() {
		MenuBar optionsMenuBar = new MenuBar();
		updateHBox.getChildren().add(optionsMenuBar);

		if (updateOptions.getItems().size() == 0) {
			List<MenuItem> menuItems = new ArrayList<>();
			menuItems.add(new MenuItem("Routes"));
			menuItems.add(new MenuItem("Trips"));
			menuItems.add(new MenuItem("Stops"));
			menuItems.add(new MenuItem("Stop Times"));

			for (MenuItem item : menuItems) {
				updateOptions.getItems().add(item);
				item.setOnAction(e -> updateOptions.setText(item.getText()));
			}
		}
		optionsMenuBar.getMenus().add(updateOptions);

		searchTF.setPromptText("Enter new data");
		searchMenu.setText("Update");

		searchSelected = false;
		searchRouteSelected = false;

		searchMenu.getItems().get(0).setDisable(false);
		searchMenu.getItems().get(1).setDisable(false);

		searchMenu.getItems().get(2).setDisable(true);
	}


	@FXML
	private void searchSelected() {
		if (updateHBox.getChildren().size() > 1) {
			updateHBox.getChildren().remove(1);

		}
		searchTF.setPromptText("Search by Stop ID");
		searchMenu.setText("...");

		searchSelected = true;
		searchRouteSelected = false;
		searchMenu.getItems().get(0).setDisable(true);
		searchMenu.getItems().get(1).setDisable(false);
		searchMenu.getItems().get(2).setDisable(false);

	}
	@FXML
	public void searchRouteSelected() {
		if (updateHBox.getChildren().size() > 1) {
			updateHBox.getChildren().remove(1);
		}
		searchTF.setPromptText("Search by Route ID");
		searchMenu.setText("...");

		searchSelected = false;
		searchRouteSelected = true;
		searchMenu.getItems().get(1).setDisable(true);
		searchMenu.getItems().get(0).setDisable(false);
		searchMenu.getItems().get(2).setDisable(false);
	}

	@FXML
	private void search() {
		if (searchTF.getText().trim().equals("")) {
			textDisplay.clear();
			return;
		}
		if (searchSelected) {
			searchStopId();
		} else if(searchRouteSelected) {
			searchRouteId();
		}
		else {
			if (searchMenu.getText().equals("File Type")) {
				return;
			}
			gtfs.updateText(searchMenu.getText(), searchTF.getText());
		}
		searchTF.clear();
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
							"The data trying to be export hasn't yet been imported. " +
									"If you wish to still export the file, please import a file first");
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
					"The file selected for export is invalid. Please select a valid file to export to.");
		}
	}

	private void searchStopId() {
		String stopId = searchTF.getText();

		int numTripsWithStop = gtfs.numTripsWithStop(searchTF.getText().toUpperCase(Locale.ROOT));
		String routeIdWithStop = gtfs.routesWithStop(searchTF.getText().toUpperCase(Locale.ROOT));
		if(gtfs.hasStopTime() && gtfs.hasTrip()) {
			if(routeIdWithStop.equals("No Routes with StopID")) {
				String searchStopInfo = "Stop ID: " + searchTF.getText() + "\n\n" +
						"Number of Trips with stop: " + numTripsWithStop + "\n\n" + routeIdWithStop  + searchNextTrips(stopId);
				textDisplay.setText(searchStopInfo);
			} else {
				String searchStopInfo = "Stop ID: " + searchTF.getText() + "\n\n" +
						"Number of Trips with stop: " + numTripsWithStop + "\n\n" + "Routes with Stop:" + "\n" +
						routeIdWithStop + searchNextTrips(stopId);
				textDisplay.setText(searchStopInfo);
			}

		} else if(gtfs.hasStopTime() && !gtfs.hasTrip()) {
			String searchStopInfo = "Stop ID: " + searchTF.getText() + "\n\n" +
					"Number of Trips with stop: " + numTripsWithStop + searchNextTrips(stopId) + "\n\n" +
					"NOTICE: Must import a trip file to see more data.";
			textDisplay.setText(searchStopInfo);

		} else if(!gtfs.hasStopTime() && !gtfs.hasTrip()) {
			String searchStopInfo = "NOTICE: Must import StopTime and Trip files to see data.";
			textDisplay.setText(searchStopInfo);
		}
	}

	private void searchRouteId() {
		String routeId = searchTF.getText();
		if(gtfs.hasTrip() && gtfs.hasStopTime()) {
			String searchRouteInfo = "Stop ID: " + searchTF.getText() + "\n\n"
					+ searchFutureTrips(routeId);
			textDisplay.setText(searchRouteInfo);
		} else {
			String searchRouteInfo = "NOTICE: Must import StopTime and Trip files to see data.";
			textDisplay.setText(searchRouteInfo);
		}



	}

	private String searchNextTrips(String stopId) {
		ArrayList<Object[]> trips = gtfs.getNextTrips(stopId, true);

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
			content = "\n\nNo subsequent trips to the given ID were found for today.";
		}
		return content;
	}

	private String searchFutureTrips(String routeId) {
		ArrayList<Object[]> trips = gtfs.getNextTrips(routeId, false);

		String content;
		if (!trips.isEmpty()) {
			String header;
			HashSet<String> allTrips = new HashSet<>();
				header = "\nAll future trips on this route:";

				StringBuilder text = new StringBuilder(header);

				for (Object[] array : trips) {
					StopTime stop = (StopTime) array[0];
					String tripId = stop.getTripId();
					if(!allTrips.contains(tripId)) {
						text.append("\n  -ID: ").append(tripId).append("\n");
						allTrips.add(tripId);
					}
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
						addToRecentUploadList();
						imported = true;
					}
				}
				if (imported) {
					allowFileInteraction();
					recentUploadLabel.setVisible(true);
					showRecentUploadList();
				}
			}
			e.consume();
		});
	}

	private void addToRecentUploadList() {
		String newestImports = gtfs.getNewestImports();
		String badData = gtfs.getBadData();
		textDisplay.setText(badData);
		List<String> filesNames = List.of(newestImports.split("\\n"));

		for (String fileName : filesNames) {
			if (!fileName.equals("")) {
				recentUploadList.add(fileName);
			}
		}
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