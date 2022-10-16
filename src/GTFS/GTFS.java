package GTFS;

import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static GTFS.Controller.newAlert;

/**
 * This class represents the database for the Routes, Stops, StopTimes, and Trips
 *
 *
 * @author nairac, atkinsonr, morrowc, schmidtrj
 * @version 1.0
 */
public class GTFS {

	private final ArrayList<Route> routes;
	private final ArrayList<Stop> stops;
	private final ArrayList<StopTime> stopTimes;
	private final ArrayList<Trip> trips;
	private String lastAdded;
	private final StringBuilder stringBuilder = new StringBuilder();

	/**
	 * This method represents the constructor for the GTFS class
	 */
	public GTFS() {
		routes = new ArrayList<>();
		stops = new ArrayList<>();
		stopTimes = new ArrayList<>();
		trips = new ArrayList<>();
	}

	protected void updateText(String text) {

	}

	/**
	 * This method imports and loads the file. This method reads the file and reads the header of the file to determine
	 * the type of import it should call
	 *
	 * @param file is the file that is to be imported
	 */
	protected void importFile(File file) {
		try (Scanner in = new Scanner(file)) {

			String header = in.nextLine();

			if(verifyRouteHeader(header)) {
				importRoute(file);
			} else if(verifyStopHeader(header)) {
				importStop(file);
			} else if(verifyTripHeader(header)) {
				importTrip(file);
			} else if(verifyStopTimeHeader(header)) {
				importStopTime(file);
			} else {
				throw new IllegalArgumentException();
			}

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", e.getMessage());

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found", e.getMessage());

		} catch (IllegalArgumentException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Invalid Data", e.getMessage());
		}
	}

	/**
	 * This method puts a new route into the routes arraylist
	 *
	 * @param file is the route file being added to arraylist
	 */
	protected void importRoute(File file) throws IllegalArgumentException{
		try (Scanner in = new Scanner(file)) {
			in.nextLine();
			importRoute(in.hasNextLine(), in);

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", e.getMessage());

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found", e.getMessage());
		}
	}

	private void importRoute(boolean hasLine, Scanner in) throws IllegalArgumentException{
		int lineCount = 0;
		while (hasLine) {
			String line = in.nextLine();
			if (lineCount < 3) {

				stringBuilder.append(line);
			}
			lineCount++;
			String[] parts = line.split(",");
			if(validateRouteData(parts)) {

				Route route = new Route(parts[0]);

				route.setAgencyID(parts[1]);
				route.setShortName(parts[2]);
				route.setLongName(parts[3]);
				route.setRouteDesc(parts[4]);
				route.setRouteType(parts[5]);
				route.setRouteURL(parts[6]);
				route.setRouteColor(parts[7]);
				if (parts.length == 9) {
					route.setRouteTextColor(parts[8]);
				} else {
					route.setRouteTextColor("");
				}
				routes.add(route);
			} else {
				System.out.println(line);

				for(int i = 0; i < parts.length; i++) {
					System.out.println(parts[i]);
				}
				throw new IllegalArgumentException("Incorrect File data: Line " + lineCount+1);

			}

			if (!in.hasNextLine()) {
				lastAdded = stringBuilder.toString();
				stringBuilder.setLength(0);
				hasLine = false;
			}
		}
	}

	/**
	 * This method puts a new stop into the stops arraylist
	 *
	 * @param file represent the stop file being added to arraylist
	 */
	protected void importStop(File file) throws IllegalArgumentException{
		try (Scanner in = new Scanner(file)) {
			in.nextLine();
			importStop(in.hasNextLine(), in);

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", e.getMessage());

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found", e.getMessage());
		}
	}

	private void importStop(boolean hasLine, Scanner in) throws IllegalArgumentException {
		int lineCount = 0;
		while (hasLine) {
			String line = in.nextLine();

			if (lineCount < 3) {

				stringBuilder.append(line);
			}
			lineCount++;
			String[] parts = line.split(",");
			if(validateStopData(parts)) {

				Stop stop = new Stop(parts[0]);

				stop.setStopName(parts[1]);
				stop.setStopDesc(parts[2]);
				stop.setStopLat(Double.parseDouble(parts[3]));
				stop.setStopLon(Double.parseDouble(parts[4]));

				stops.add(stop);
			} else {
				throw new IllegalArgumentException("Incorrect File data: Line " + lineCount+1);
			}
			if (!in.hasNextLine()) {
				lastAdded = stringBuilder.toString();
				stringBuilder.setLength(0);
				hasLine = false;
			}
		}
	}

	/**
	 * This method puts a new StopTime into the StopTimes ArrayList
	 *
	 * @param file represents the StopTime file being added to ArrayList
	 */
	protected void importStopTime(File file)throws IllegalArgumentException {
		try (Scanner in = new Scanner(file)) {
			in.nextLine();
			importStopTime(in.hasNextLine(), in);

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", e.getMessage());

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found", e.getMessage());
		}
	}

	private void importStopTime(boolean hasLine, Scanner in) throws IllegalArgumentException{
		int lineCount = 0;
		while (hasLine) {
			String line = in.nextLine();

			if (lineCount < 3) {

				stringBuilder.append(line);
			}
			lineCount++;
			String[] parts = line.split(",");
			if(validateStopTimeData(parts)) {
				StopTime st = new StopTime(parts[3], parts[0]);

				st.setArrivalTime(parts[1]);
				st.setDepartureTime(parts[2]);
				st.setStopSequence(parts[4]);
				st.setStopHeadSign(parts[5]);
				st.setPickUpType(parts[6]);
				if(parts.length == 7) {
					st.setDropOffType("");
				} else {
					st.setDropOffType(parts[7]);
				}

				stopTimes.add(st);
			} else {

				throw new IllegalArgumentException("Incorrect File data: Line " + lineCount+1);
			}
			if (!in.hasNextLine()) {
				lastAdded = stringBuilder.toString();
				stringBuilder.setLength(0);
				hasLine = false;
			}
		}
	}

	/**
	 * This method puts a new trip into the trips ArrayList
	 *
	 * @param file represents the trip file being added to ArrayList
	 */
	protected void importTrip(File file) throws IllegalArgumentException {
		try (Scanner in = new Scanner(file)) {
			in.nextLine();
			importTrip(in.hasNextLine(), in);

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", e.getMessage());

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found", e.getMessage());
		}
	}

	private void importTrip(boolean hasLine, Scanner in) throws IllegalArgumentException{
		int lineCount = 0;
		while (hasLine) {
			String line = in.nextLine();

			if (lineCount < 3) {
				stringBuilder.append(line);
			}
			lineCount++;

			String[] parts = line.split(",");
			if(validateTripData(parts)) {
				Trip trip = new Trip(parts[2], parts[0]);

				trip.setBlockId(parts[5]);
				trip.setDirectionId(parts[4]);
				trip.setServiceId(parts[1]);
				trip.setHeadSign(parts[3]);
				if(parts.length == 6) {
					trip.setShapeId("");
				} else {
					trip.setShapeId(parts[6]);
				}

				trips.add(trip);
			} else {
				throw new IllegalArgumentException("Incorrect File data: Line " + lineCount+1);
			}
			if (!in.hasNextLine()) {
				lastAdded = stringBuilder.toString();
				stringBuilder.setLength(0);
				hasLine = false;
			}
		}
	}

	public boolean verifyRouteHeader(String header) {
		return header.equals("route_id,agency_id,route_short_name,route_long_name," +
				"route_desc,route_type,route_url,route_color,route_text_color");
	}

	public boolean verifyStopHeader(String header) {
		return header.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
	}

	public boolean verifyTripHeader(String header){
		return header.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
	}
	public boolean verifyStopTimeHeader(String header) {
		return header.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
				"stop_headsign,pickup_type,drop_off_type");
	}

	public boolean validateRouteData(String[] data) {
		boolean isValid = true;
		if(data.length < 8 || data.length > 9) {
			isValid = false;
		} else {
			if(data[0].equals("")) {
				isValid = false;
			}
			if(data[7].equals("")) {
				isValid = false;
			}
			if(!data[7].matches("^[0-9A-Fa-f]+$")) {
				isValid = false;
			}
			if(!data[5].equals("") && !(data[5].equals("1") || data[5].equals("2") ||data[5].equals("3")
					||data[5].equals("4") || data[5].equals("5") || data[5].equals("6") || data[5].equals("7")
					|| data[5].equals("11") || data[5].equals("12"))) {
				isValid = false;
			}
			if(data.length == 9 && !data[8].matches("^[0-9A-Fa-f]+$")) {
				isValid = false;
			}
			if(!data[6].equals("") && !data[6]
					.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
				isValid = false;
			}
		}
		return isValid;
	}

	public boolean validateStopData(String[] data) {
		boolean isValid = true;
		if(data.length != 5) {
			isValid = false;
		} else {
			if(data[0].equals("")) {
				isValid = false;
			}
			if(data[3].equals("")) {
				isValid = false;
			} else if(Double.parseDouble(data[3]) >= 90 || Double.parseDouble(data[3]) <= -90) {
				isValid = false;
			}
			if(data[4].equals("")) {
				isValid = false;
			} else if(Double.parseDouble(data[4]) >= 180 || Double.parseDouble(data[4]) <= -180) {
				isValid = false;
			}
		}
		return isValid;
	}

	public boolean validateTripData(String[] data) {
		boolean isValid = true;
		if(data.length < 6 || data.length > 7) {
			isValid = false;
		} else {
			if(data[0].equals("")) {
				isValid = false;
			}
			if(data[2].equals("")) {
				isValid = false;
			}
			if(!data[4].equals("") && !(data[4].equals("0") || data[4].equals("1"))) {
				isValid = false;
			}
		}
		return isValid;
	}

	public boolean validateStopTimeData(String[] data) {
		boolean isValid = true;
		if(data.length < 7 || data.length > 8) {
			isValid = false;
		}  else {
			if (data[3].equals("")) {
				isValid = false;
			}
			if (data[0].equals("")) {
				isValid = false;
			}
			if (!data[1].equals("")) {
				if (!data[1].matches("(?:[012]\\d|2[0123]):[012345]\\d:[012345]\\d")) {
					isValid = false;
				} else if (Integer.parseInt(data[1].substring(3, 5)) > 59) {
					isValid = false;
				} else if (Integer.parseInt(data[1].substring(6)) > 59) {
					isValid = false;
				}
			}
			if (!data[2].matches("(?:[012]\\d|2[0123]):[012345]\\d:[012345]\\d")) {
				if (!data[2].matches("")) {
					isValid = false;
				} else if (Integer.parseInt(data[2].substring(3, 5)) > 59) {
					isValid = false;
				} else if (Integer.parseInt(data[2].substring(6)) > 59) {
					isValid = false;
				}
			}
			if (data[4].equals("")) {
				isValid = false;
			}
			if (!data[4].matches("[0-9]+")) {
				isValid = false;
			}
			if (!data[6].equals("") && !(data[6].equals("0") || data[6].equals("1")
					|| data[6].equals("2") || data[6].equals("3"))) {
				isValid = false;
			}
			if (data.length == 8 && !data[7].equals("") && !(data[7].equals("0") || data[7].equals("1")
					|| data[7].equals("2") || data[7].equals("3"))) {
				isValid = false;
			}
		}

		return isValid;
	}

	/**
	 * search a stop and returns the number of trips that have the stop.
	 *
	 * @param stopId - searched stop
	 * @return returns the number of trips that contain the stop
	 */
	public int numTripsWithStop(String stopId) {
		int numTrips = 0;
		for (StopTime stopTime : stopTimes) {
			if (stopTime.hasStop(stopId)) {
				numTrips++;
			}
		}
		return numTrips;
	}

	/**
	 * method to export a file from the GTFS to the main system
	 * @author Cody Morrow
	 * @param item - what is to be exported
	 */
	public String exportFile(String item) {
		String content = "";
		if(item.equalsIgnoreCase("Stops")){
			content = exportStop();
		} else if(item.equalsIgnoreCase("Routes")){
			content = exportRoute();
		} else if(item.equalsIgnoreCase("Trips")){
			content = exportTrips();
		} else if(item.equalsIgnoreCase("Stop Times")){
			content = exportStopTime();
		}
		return content;
	}

	/**
	 * method to have a string to export all stops with a string
	 * @author Cody Morrow
	 * @return string of all stops separated by a \n
	 */
	private String exportStop(){
		String allStops = "";
		for(Stop s : stops){
			allStops += s.toString() + "\n";
		}
		return allStops;
	}

	/**
	 * method to have a string to export all routes with a string
	 * @author Cody Morrow
	 * @return string of all routes separated by a \n
	 */
	private String exportRoute(){
		String allRoutes = "";
		for(Route r : routes){
			allRoutes += r.toString() + "\n";
		}
		return allRoutes;
	}

	/**
	 * method to have a string to export all stoptimes with a string
	 * @author Cody Morrow
	 * @return string of all stoptimes separated by a \n
	 */
	private String exportStopTime(){
		String allStopTimes = "";
		for(Stop st : stops){
			allStopTimes += st.toString() + "\n";
		}
		return allStopTimes;
	}

	/**
	 * method to have a string to export all Trips with a string
	 * @author Cody Morrow
	 * @return string of all Trips separated by a \n
	 */
	private String exportTrips(){
		String allTrips = "";
		for(Stop t : stops){
			allTrips += t.toString() + "\n";
		}
		return allTrips;
	}

	protected String getNewestImport() {
		return lastAdded;
	}
}