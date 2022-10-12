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
	 * This method puts a new route into the routes hashtable
	 *
	 * @param file is the route file being added to hashtable
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
	 * This method puts a new stop into the stops hashtable
	 *
	 * @param file represent the stop file being added to hashtable
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
	protected void importStopTime(File file) {
		try (Scanner in = new Scanner(file)) {
			in.nextLine();
			importStopTime(in.hasNextLine(), in);

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", e.getMessage());

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found", e.getMessage());
		}
	}

	private void importStopTime(boolean hasLine, Scanner in) {
		int lineCount = 0;
		while (hasLine) {
			String line = in.nextLine();

			if (lineCount < 3) {

				stringBuilder.append(line);
			}
			lineCount++;
			String[] parts = line.split(",");
			StopTime st = new StopTime(parts[3], parts[0]);

			st.setArrivalTime(parts[1]);
			st.setDepartureTime(parts[2]);
			st.setStopSequence(parts[4]);
			st.setStopHeadSign(parts[5]);
			st.setPickUpType(parts[6]);
			st.setDropOffType(parts[7]);

			stopTimes.add(st);
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

	protected void importTrip(boolean hasLine, Scanner in) throws IllegalArgumentException{
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

	private boolean verifyRouteHeader(String header) {
		return header.equals("route_id,agency_id,route_short_name,route_long_name," +
				"route_desc,route_type,route_url,route_color,route_text_color");
	}

	private boolean verifyStopHeader(String header) {
		return header.equals("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
	}

	private boolean verifyTripHeader(String header){
		return header.equals("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
	}
	private boolean verifyStopTimeHeader(String header) {
		return header.equals("trip_id,arrival_time,departure_time,stop_id,stop_sequence," +
				"stop_headsign,pickup_type,drop_off_type");
	}

	private boolean validateRouteData(String[] data) {
		boolean isValid = true;
		if(data.length < 8 || data.length > 9) {
			isValid = false;
		} else {
			if(data[0].equals("")) {
				isValid = false;
			} else if(data[7].equals("")) {
				isValid = false;
			}

		}
		return isValid;
	}

	private boolean validateStopData(String[] data) {
		boolean isValid = true;
		if(data.length != 5) {
			isValid = false;
		} else {
			if(data[0].equals("")) {
				isValid = false;
			} else if(data[3].equals("")) {
				isValid = false;
			} else if(data[4].equals("")) {
				isValid = false;
			}

		}

		return isValid;
	}

	private boolean validateTripData(String[] data) {
		boolean isValid = true;

		if(data.length < 6 || data.length > 7) {
			isValid = false;
		} else {
			if(data[0].equals("")) {
				isValid = false;
			} else if(data[2].equals("")) {
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
	 * NOT IMPLEMENTED YET
	 *
	 * @param string- not implemented yet
	 */
	public boolean export(String string) {
		return false;
	}

	/**
	 * NOT IMPLEMENTED
	 * Searches for a stop, given the stopID. Returns the Stop
	 *
	 * @param stopId- ID turned into ascii decimal used as a key for each stop.
	 */
	public Stop searchStopId(String stopId) {
		return null;
	}

	/**
	 * NOT IMPLEMENTED
	 * Searches for a route, given the routeId. Returns the route
	 *
	 * @param routeId- ID turned into ascii decimal used as a key for each stop
	 */
	public Route searchRouteId(BigInteger routeId) {
		return null;
	}

	/**
	 * NOT IMPLEMENTED
	 * Searches for a trip, given the tripId. Returns the trip
	 *
	 * @param tripId- ID turned into ascii decimal used as a key for each trip
	 */
	public Trip searchTrips(BigInteger tripId) {
		return null;
	}

	/**
	 * NOT IMPLEMENTED
	 * Searches for a stop time, given the stopTimeId. Returns the stop time.
	 *
	 * @param stopTimeId- ID turned into ascii decimal used as a key for each stop time
	 */
	public StopTime searchStopTimes(BigInteger stopTimeId) {
		return null;
	}

	protected String getNewestImport() {
		return lastAdded;
	}

}