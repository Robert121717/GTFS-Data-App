/**
 * GTFS, program to import, export, and search information on bus transit data
 *     Copyright (C) 2022  nairac, atkinsonr, morrowc, schmidtrj
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package GTFS;

import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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

	private final HashMap<BigInteger, Route> routesSet;
	private final HashMap<BigInteger, Stop> stopsSet;
	private final HashMap<BigInteger, StopTime> stopTimesSet;
	private final HashMap<BigInteger, Trip> tripsSet;
	private String lastAdded;
	private String badData;
	private final StringBuilder stringBuilder = new StringBuilder();

	/**
	 * This method represents the constructor for the GTFS class
	 */
	public GTFS() {
		routes = new ArrayList<>();
		stops = new ArrayList<>();
		stopTimes = new ArrayList<>();
		trips = new ArrayList<>();

		routesSet = new HashMap<>();
		stopsSet = new HashMap<>();
		stopTimesSet = new HashMap<>();
		tripsSet = new HashMap<>();
	}

	protected void updateText(String file, String text) {
		String[] attributes = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		boolean valid;
		try {
			switch (file) {
				case "Routes" -> {
					valid = validateRouteData(attributes);
					if (valid) updateRoute(attributes);
				}
				case "Trips" -> {
					valid = validateTripData(attributes);
					if (valid) updateTrip(attributes);
				}
				case "Stop Times" -> {
					valid = validateStopTimeData(attributes);
					if (valid) updateStopTime(attributes);
				}
				default -> {
					valid = validateStopData(attributes);
					if (valid) updateStop(attributes);
				}
			}
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			valid = false;
		}
		if (!valid) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value",
					"The given data is not formatted properly.");
		}
	}

	private void updateRoute(String[] attributes) throws IndexOutOfBoundsException {
		String routeId = attributes[0];
		Route updatedRoute = new Route(routeId);

		updatedRoute.setAgencyID(attributes[1]);
		updatedRoute.setShortName(attributes[2]);
		updatedRoute.setLongName(attributes[3]);
		updatedRoute.setRouteDesc(attributes[4]);
		updatedRoute.setRouteType(attributes[5]);
		updatedRoute.setRouteURL(attributes[6]);
		updatedRoute.setRouteColor(attributes[7]);
		updatedRoute.setRouteTextColor(attributes[8]);

		boolean updated = false;
		for (int i = 0; i < routes.size(); ++i) {
			Route route = routes.get(i);
			if (route.getRouteId().equals(routeId)) {
				routes.set(i, updatedRoute);
				updated = true;
				break;
			}
		}
		if (!updated) routes.add(updatedRoute);
	}

	private void updateTrip(String[] attributes) throws IndexOutOfBoundsException {
		String tripId = attributes[2];
		String routeId = attributes[0];
		Trip updatedTrip = new Trip(tripId, routeId);

		updatedTrip.setServiceId(attributes[1]);
		updatedTrip.setHeadSign(attributes[3]);
		updatedTrip.setDirectionId(attributes[4]);
		updatedTrip.setBlockId(attributes[5]);
		updatedTrip.setShapeId(attributes[6]);

		boolean updated = false;
		for (int i = 0; i < trips.size(); ++i) {
			Trip trip = trips.get(i);
			if (trip.getTripId().equals(tripId)) {
				trips.set(i, updatedTrip);
				updated = true;
				break;
			}
		}
		if (!updated) trips.add(updatedTrip);
	}

	private void updateStopTime(String[] attributes) throws IndexOutOfBoundsException {
		String stopId = attributes[3];
		String tripId = attributes[0];
		StopTime updatedStop = new StopTime(stopId, tripId);

		updatedStop.setArrivalTime(attributes[1]);
		updatedStop.setDepartureTime(attributes[2]);
		updatedStop.setStopSequence(attributes[4]);
		updatedStop.setStopHeadSign(attributes[5]);
		updatedStop.setPickUpType(attributes[6]);
		updatedStop.setDropOffType(attributes[7]);

		boolean updated = false;
		for (int i = 0; i < stopTimes.size(); ++i) {
			StopTime stop = stopTimes.get(i);
			boolean equalStopIds = stop.getStopId().equals(stopId);
			boolean equalTripIds = stop.getTripId().equals(tripId);

			if (equalStopIds && equalTripIds) {
				stopTimes.set(i, updatedStop);
				updated = true;
				break;
			}
		}
		if (!updated) stopTimes.add(updatedStop);
	}

	private void updateStop(String[] attributes)
			throws IndexOutOfBoundsException, NumberFormatException{
		String stopId = attributes[0];
		Stop updatedStop = new Stop(stopId);

		updatedStop.setStopName(attributes[1]);
		updatedStop.setStopDesc(attributes[2]);
		updatedStop.setStopLat(Double.parseDouble(attributes[3]));
		updatedStop.setStopLon(Double.parseDouble(attributes[4]));

		boolean updated = false;
		for (int i = 0; i < stops.size(); ++i) {
			Stop stop = stops.get(i);
			if (stop.getStopId().equals(stopId)) {
				stops.set(i, updatedStop);
				updated = true;
				break;
			}
		}
		if (!updated) stops.add(updatedStop);
	}

	/**
	 * This method imports and loads the file. This method reads the file and reads the header of the file to determine
	 * the type of import it should call
	 *
	 * @param file is the file that is to be imported
	 */
	protected void importFile(File file) {
		lastAdded = "";
		badData = "";
		try (Scanner in = new Scanner(file)) {
			String fileName = file.getName();
			String header = in.nextLine();

			if(verifyRouteHeader(header)) {
				importRoute(in.hasNextLine(), in, fileName);
			} else if(verifyStopHeader(header)) {
				importStop(in.hasNextLine(), in, fileName);
			} else if(verifyTripHeader(header)) {
				importTrip(in.hasNextLine(), in, fileName);
			} else if(verifyStopTimeHeader(header)) {
				importStopTime(in.hasNextLine(), in, fileName);
			} else {
				throw new IllegalArgumentException();
			}

		} catch (NumberFormatException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Unexpected Value", "Please reselect the file to be imported");

		} catch (FileNotFoundException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "File Not Found",
					"The file chosen for import could not be found. Please select a different file");

		} catch (IllegalArgumentException e) {
			newAlert(AlertType.ERROR, "Error Dialog", "Invalid Data",
					"The file chosen for import is not a valid importable file. Please select a valid file to import");
		}
	}


	private void importRoute(boolean hasLine, Scanner in, String filename){
		int priorListSize = routes.size();
		ArrayList<String> incorrectRouteData = new ArrayList<>();
		while (hasLine) {
			String line = in.nextLine();

			String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

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

				if(!routesSet.containsKey(route.getRouteIDHash())){

					routes.add(route);
					routesSet.put(route.getRouteIDHash(), route);
				}
			} else {
				incorrectRouteData.add(line);
			}

			if (!in.hasNextLine()) {
				if(!incorrectRouteData.isEmpty()) {
					stringBuilder.append("\n\n");
					stringBuilder.append("Bad Data Lines: DID NOT IMPORT");
					stringBuilder.append("\n\n");
					for (String badData : incorrectRouteData) {
						stringBuilder.append(badData);
						stringBuilder.append("\n\n");
					}
				}

				hasLine = false;
			}
		}
		if(routes.size() != priorListSize){
			lastAdded += filename + "\n";

		}
		badData += stringBuilder.toString();

		stringBuilder.setLength(0);
	}


	private void importStop(boolean hasLine, Scanner in, String filename){
		int priorListSize = stops.size();
		ArrayList<String> incorrectStopData = new ArrayList<>();
		while (hasLine) {
			String line = in.nextLine();

			String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			if(validateStopData(parts)) {

				Stop stop = new Stop(parts[0]);

				stop.setStopName(parts[1]);
				stop.setStopDesc(parts[2]);
				stop.setStopLat(Double.parseDouble(parts[3]));
				stop.setStopLon(Double.parseDouble(parts[4]));

				if(!stopsSet.containsKey(stop.getStopIDHash())){
					stops.add(stop);
					stopsSet.put(stop.getStopIDHash(), stop);
				}


			} else {
				incorrectStopData.add(line);
			}
			if (!in.hasNextLine()) {
				if(!incorrectStopData.isEmpty()) {
					stringBuilder.append("\n\n");
					stringBuilder.append("Bad Data Lines: DID NOT IMPORT");
					stringBuilder.append("\n\n");
					for (String badData : incorrectStopData) {
						stringBuilder.append(badData);
						stringBuilder.append("\n\n");
					}
				}

				hasLine = false;
			}
		}
		if(stops.size() != priorListSize){
			lastAdded += filename + "\n";

		}
		badData += stringBuilder.toString();
		stringBuilder.setLength(0);
	}


	private void importStopTime(boolean hasLine, Scanner in, String filename){
		int priorListSize = stopTimes.size();
		ArrayList<String> incorrectStopTimeData = new ArrayList<>();

		while (hasLine) {
			String line = in.nextLine();

			String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
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

				if(!stopTimesSet.containsKey(st.getHashId())){
					stopTimes.add(st);
					stopTimesSet.put(st.getHashId(), st);
				}


			} else {
				incorrectStopTimeData.add(line);
			}
			if (!in.hasNextLine()) {
				if(!incorrectStopTimeData.isEmpty()) {
					stringBuilder.append("\n\n");
					stringBuilder.append("Bad Data Lines: DID NOT IMPORT");
					stringBuilder.append("\n\n");
					for (String badData : incorrectStopTimeData) {
						stringBuilder.append(badData);
						stringBuilder.append("\n\n");
					}
				}

				hasLine = false;
			}
		}
		if(stopTimes.size() != priorListSize){
			lastAdded += filename + "\n";

		}
		badData += stringBuilder.toString();
		stringBuilder.setLength(0);
	}

	private void importTrip(boolean hasLine, Scanner in, String filename){
		int priorListSize = trips.size();
		ArrayList<String> incorrectTripData = new ArrayList<>();
		while (hasLine) {
			String line = in.nextLine();
			String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
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

				if(!tripsSet.containsKey(trip.getHashId())){
					trips.add(trip);
					tripsSet.put(trip.getHashId(), trip);
				}



			} else {
				incorrectTripData.add(line);
			}
			if (!in.hasNextLine()) {
				if(!incorrectTripData.isEmpty()) {
					stringBuilder.append("\n\n");
					stringBuilder.append("Bad Data Lines: DID NOT IMPORT");
					stringBuilder.append("\n\n");
					for (String badData : incorrectTripData) {
						stringBuilder.append(badData);
						stringBuilder.append("\n\n");
					}
				}

				hasLine = false;
			}
		}
		if(trips.size() != priorListSize){
			lastAdded += filename + "\n";

		}
		badData += stringBuilder.toString();
		stringBuilder.setLength(0);
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
			if(!data[8].equals("") && !data[8].matches("^[0-9A-Fa-f]+$")) {
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
			if (!data[2].matches("")) {
				if (!data[2].matches("(?:[012]\\d|2[0123]):[012345]\\d:[012345]\\d")) {
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
			if (!data[7].equals("") && !(data[7].equals("0") || data[7].equals("1")
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
	 * search a stop and returns the routeID's of the routes with teh stop.
	 *
	 * @param stopId - searched stop
	 * @return returns route ID's that contain the stop.
	 */
	public String routesWithStop(String stopId) {
		String tripId = "";
		StringBuilder sb = new StringBuilder();
		ArrayList<String> currentRoutes = new ArrayList<>();
		HashSet<String> currentRouteSet = new HashSet<>();

		for (StopTime stopTime : stopTimes) {
			if (stopTime.hasStop(stopId)) {
				tripId = stopTime.getTripId();
				for (Trip trip : trips) {
					if(trip.getTripId().equals(tripId)) {
						if(!currentRouteSet.contains(trip.getRouteId())) {
							currentRouteSet.add(trip.getRouteId());
							currentRoutes.add(trip.getRouteId());
						}
					}
				}
			}
		}
		for(String routeId: currentRoutes) {
			sb.append("RouteID: ");
			sb.append(routeId);
			sb.append("\n");
		}
		if(currentRoutes.isEmpty()) {
			sb.append("No Routes with StopID");
		}
		return sb.toString();
	}

	/**
	 * search a route and returns the stopID's of the stops on the route.
	 *
	 * @param routeId - searched route
	 * @return returns stop ID's that are on the route.
	 */
	public String stopsOnRoute(String routeId){
		String tripId = "";
		StringBuilder sb = new StringBuilder();
		ArrayList<String> currentStops = new ArrayList<>();
		HashSet<String> currentStopSet = new HashSet<>();

		for(Trip trip: trips){
			if(trip.getRouteId().equals(routeId)){
				tripId = trip.getTripId();
				for(StopTime stopTime: stopTimes){
					if(stopTime.getTripId().equals(tripId)){
						if(!currentStopSet.contains(stopTime.getStopId())){
							currentStopSet.add(stopTime.getStopId());
							currentStops.add(stopTime.getStopId());
						}

					}
				}
			}
		}
		for(String stopId: currentStops){
			sb.append("StopId: ");
			sb.append(stopId);
			sb.append("\n");
		}
		if(currentStops.isEmpty()) {
			sb.append("No Routes with StopID");
		}
		return sb.toString();
	}

	protected ArrayList<Object[]> getNextTrips(String id, boolean isStop) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String time = dateFormat.format(Calendar.getInstance().getTime());

		String[] currentTimeStamp = time.split(" ")[1].split(":");
		int hour = Integer.parseInt(currentTimeStamp[0]);
		int minute = Integer.parseInt(currentTimeStamp[1]);
		int seconds = Integer.parseInt(currentTimeStamp[2]);

		int currentTime = (hour * 3600) + (minute * 60) + seconds;
		ArrayList<Object[]> tripsWithId = new ArrayList<>();
		if(isStop) {
			for (StopTime stop : stopTimes) {
				if (stop.getStopId().equals(id)) {
					String[] timeStamp = stop.getArrivalTime().split(":");
					int numSeconds = toSeconds(timeStamp);

					int timeDifference = numSeconds - currentTime;
					if (timeDifference > 0) {

						sortNewStop(tripsWithId, stop, timeDifference);
					}
				}
			}
		} else {
			for(Trip trip: trips) {
				if(trip.getRouteId().equals(id)) {
					for(StopTime stop: stopTimes) {
						if(stop.getTripId().equals(trip.getTripId())) {
							String[] timeStamp = stop.getArrivalTime().split(":");
							int numSeconds = toSeconds(timeStamp);
							int timeDifference = numSeconds - currentTime;
							if (timeDifference > 0) {
								tripsWithId.add(new Object[]{stop, timeDifference});
							}
						}
					}
				}
			}
		}

		return tripsWithId;

	}




	private void sortNewStop(ArrayList<Object[]> trips, StopTime stop, int timeDifference) {
		if (trips.size() < 3) {
			trips.add(new Object[]{stop, timeDifference});
		} else {
			boolean sorted = false;

			for (int i = 0; i < trips.size() && !sorted; ++i) {
				Object[] array = trips.get(i);
				int oldTimeDifference = (int) array[1];

				if (timeDifference < oldTimeDifference) {
					trips.set(i, new Object[]{stop, timeDifference});
					sorted = true;
				}
			}
		}
	}

	private int toSeconds(String[] timeStamp) {
		int hours = Integer.parseInt(timeStamp[0]);
		int minutes = Integer.parseInt(timeStamp[1]);
		int seconds = Integer.parseInt(timeStamp[2]);

		if (hours == 24) {
			hours = 0;
		} else if (hours == 25) {
			hours = 1;
		}
		return (hours * 3600) + (minutes * 60) + seconds;
	}

	/**
	 * method to export a file from the GTFS to the main system
	 * @author Cody Morrow
	 * @param item - what is to be exported
	 */
	public String exportFile(String item) {
		String content = "";
		if(item.equalsIgnoreCase("Stops")) {
			if(!stops.isEmpty()) {
				content = exportStop();
			}

		} else if(item.equalsIgnoreCase("Routes")) {
			if(!routes.isEmpty()) {
				content = exportRoute();
			}

		} else if(item.equalsIgnoreCase("Trips")) {
			if(!trips.isEmpty()) {
				content = exportTrips();
			}

		} else if(item.equalsIgnoreCase("Stop Times")) {
			if(!stopTimes.isEmpty()) {
				content = exportStopTime();
			}
		}
		return content;
	}

	/**
	 * method to have a string to export all stops with a string
	 * @author Cody Morrow
	 * @return string of all stops separated by a \n
	 */
	private String exportStop(){
		String header = "stop_id,stop_name,stop_desc,stop_lat,stop_lon\n";
		StringBuilder sb = new StringBuilder(header);

		for(Stop stop : stops){
			sb.append(stop).append("\n");
		}
		return sb.toString();
	}

	/**
	 * method to have a string to export all routes with a string
	 * @author Cody Morrow
	 * @return string of all routes separated by a \n
	 */
	private String exportRoute(){
		String header = "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type," +
				"route_url,route_color,route_text_color\n";
		StringBuilder sb = new StringBuilder(header);

		for(Route route : routes){
			sb.append(route).append("\n");
		}
		return sb.toString();
	}

	/**
	 * method to have a string to export all stoptimes with a string
	 * @author Cody Morrow
	 * @return string of all stoptimes separated by a \n
	 */
	private String exportStopTime(){
		String header = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign," +
				"pickup_type,drop_off_type\n";
		StringBuilder sb = new StringBuilder(header);

		for(StopTime st : stopTimes){
			sb.append(st.toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * method to have a string to export all Trips with a string
	 * @author Cody Morrow
	 * @return string of all Trips separated by a \n
	 */
	private String exportTrips(){
		String header = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id\n";
		StringBuilder sb = new StringBuilder(header);

		for(Trip trip : trips){
			sb.append(trip.toString()).append("\n");
		}
		return sb.toString();
	}

	protected String getNewestImports() {
		return lastAdded;
	}
	protected String getBadData() {
		return badData;
	}
	public boolean hasTrip() {
		return !trips.isEmpty();
	}
	public boolean hasStopTime() {
		return !stopTimes.isEmpty();
	}

	/**
	 * Converts each ASCII character in the ID to its decimal representation and appends it to an integer.
	 * @param id The object's ID as a String.
	 * @return The ID as an appended integer. This ID will represent an attribute of the relative class,
	 * but may not be the ID be used when storing this object in a hash table.
	 */
	protected static BigInteger toDecimal(String id) {
		byte[] idBytes = id.getBytes(StandardCharsets.US_ASCII);

		StringBuilder idByteString = new StringBuilder();
		for (byte idByte : idBytes) {
			idByteString.append(idByte);
		}

		return new BigInteger(idByteString.toString());
	}

	/**
	 * Appends one ID onto another. This method should only be used when both IDs correspond to a single object.
	 * @param v1i Integer representation of the first ID.
	 * @param v2i Integer representation of the second ID.
	 * @return A single ID to be used when storing this object in a hash table,
	 * where the first IDs value comes before the second IDs value (such that hashId: [v1i][v2i]).
	 */
	protected static BigInteger mergeIDs(BigInteger v1i, BigInteger v2i) {
		String v1 = String.valueOf(v1i), v2 = String.valueOf(v2i);

		return new BigInteger(v1 + v2);
	}


}