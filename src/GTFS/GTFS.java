package GTFS;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author nairac
 * @version 1.0
 */
public class GTFS {

	//Change UML to show that GTFS has four hashtables, not Lists.
	//Do we need to put all of these in a single list??? or keep separate?
	protected Hashtable<Integer, Route> routes;
	protected Hashtable<Integer, Stop> stops;
	protected Hashtable<Integer, StopTime> stopTimes;
	protected Hashtable<Integer, Trip> trips;


	public GTFS(){
		routes = new Hashtable<>();
		stops = new Hashtable<>();
		stopTimes = new Hashtable<>();
		trips = new Hashtable<>();

	}

	protected void importText(String text) {

	}
	protected void importFile(File file){
		try(Scanner in = new Scanner(file)){
			String header = in.nextLine();
			header = header.toUpperCase();
			if(header.contains("ROUTE_ID")){
				if(header.contains("STOP_ID")){
					importStopTime(file);
				} else {
					importRoute(file);
				}
			} else if(header.contains("STOP_ID")){
				if(header.contains("TRIP_ID")){
					importTrip(file);
				} else {
					importStop(file);
				}
			} else{
				throw new IllegalArgumentException();
			}
		} catch(NumberFormatException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Unexpected Value");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch(FileNotFoundException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("File Not Found");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch(IllegalArgumentException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Invalid Data");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * puts a new route into the routes hashmap
	 * @param newRoute- route being added to hashtable
	 * @return returns the newly added Route
	 */
	public void importRoute(File file){
		try (Scanner in = new Scanner(file)){
			in.nextLine();
			importRoute(in.hasNextLine(), in);
		} catch(NumberFormatException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Unexpected Value");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch(FileNotFoundException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("File Not Found");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	private void importRoute(boolean hasLine, Scanner in){
		if(hasLine) {
			String line = in.nextLine();
			String[] parts = line.split(",");
			Route route = new Route(toDecimal(parts[0]));
			route.setAgencyID(parts[1]);
			route.setShortName(parts[2]);
			route.setLongName(parts[3]);
			route.setRouteDesc(parts[4]);
			route.setRouteType(parts[5]);
			route.setRouteURL(parts[6]);
			route.setRouteColor(parts[7]);
			route.setRouteTextColor(parts[8]);
			routes.put(route.getRouteId(), route);
			importRoute(in.hasNextLine(), in);
		}
	}

	/**
	 * puts a new stop into the stops hashtable
	 * @param newStop- stop being added to hashtable
	 * @return returns the newly added Stop
	 */
	public void importStop(File file){
		try (Scanner in = new Scanner(file)){
			in.nextLine();
			importStop(in.hasNextLine(), in);
		} catch(NumberFormatException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Unexpected Value");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch(FileNotFoundException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("File Not Found");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	private void importStop(boolean hasLine, Scanner in){
		if(hasLine) {
			String line = in.nextLine();
			String[] parts = line.split(",");
			Stop stop = new Stop(toDecimal(parts[0]));
			stop.setStopName(parts[1]);
			stop.setStopDesc(parts[2]);
			stop.setStopLat(Double.parseDouble(parts[3]));
			stop.setStopLon(Double.parseDouble(parts[4]));
			stops.put(stop.getStopId(), stop);
			importStop(in.hasNextLine(), in);
		}
	}

	/**
	 * puts a new stoptime into the stoptimes hashtable
	 * @param newStopTime - stoptime being added to hashtable
	 * @return returns newly added stoptime
	 */
	public void importStopTime(File file){
		try(Scanner in = new Scanner(file)){
			in.nextLine();
			importStopTime(in.hasNextLine(), in);
		} catch(NumberFormatException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Unexpected Value");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch(FileNotFoundException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("File Not Found");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	private void importStopTime(boolean hasLine, Scanner in){
		if(hasLine){
			String line = in.nextLine();
			String[] parts = line.split(",");
			StopTime ST = new StopTime(toDecimal(parts[3]), toDecimal(parts[0]),
					mergeIDs(toDecimal(parts[3]), toDecimal(parts[0])));
			ST.setArrivalTime(parts[1]);
			ST.setDepartureTime(parts[2]);
			ST.setStopSequence(parts[4]);
			ST.setStopHeadSign(parts[5]);
			ST.setPickUpType(parts[6]);
			ST.setDropOffType(parts[7]);
			stopTimes.put(ST.getHashId(), ST);
			importStop(in.hasNextLine(), in);
		}
	}

	/**
	 * puts a new trip into the trips hashtable
	 * @param newTrip- trip being added to hashtable
	 * @return returns newly added stoptime
	 */
	public void importTrip(File file){
		try(Scanner in = new Scanner(file)){
			in.nextLine();
			importTrip(in.hasNextLine(), in);
		} catch(NumberFormatException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Unexpected Value");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch(FileNotFoundException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("File Not Found");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	protected void importTrip(boolean hasLine, Scanner in){
		if(hasLine){
			String line = in.nextLine();
			String[] parts = line.split(",");
			Trip trip = new Trip(toDecimal(parts[2]), toDecimal(parts[0]),
					mergeIDs(toDecimal(parts[2]), toDecimal(parts[0])));
			trip.setBlockId(parts[5]);
			trip.setDirectionId(parts[4]);
			trip.setServiceId(parts[1]);
			trip.setHeadSign(parts[3]);
			trip.setShapeId(parts[6]);
			trips.put(trip.getHashId(), trip);
			importTrip(in.hasNextLine(), in);
		}
	}

	/**
	 * NOT IMPLEMENTED YET
	 * @param String
	 */
	public boolean export(String String){
		return false;
	}

	/**
	 * Searches for a stop, given the stopID. Returns the Stop
	 * @param stopId- ID turned into ascii decimal used as a key for each stop.
	 */
	public Stop searchStopId(int stopId){
		return stops.get(stopId);
	}

	/**
	 * Searches for a route, given the routeId. Returns the route
	 * @param routeId- ID turned into ascii decimal used as a key for each stop
	 */
	public Route searchRouteId(int routeId){
		return routes.get(routeId);
	}

	/**
	 * Searches for a trip, given the tripId. Returns the trip
	 * @param tripId- ID turned into ascii decimal used as a key for each trip
	 */
	public Trip searchTrips(int tripId){
		return trips.get(tripId);
	}
	/**
	 * Searches for a stop time, given the stopTimeId. Returns the stop time.
	 * @param stopTimeId- ID turned into ascii decimal used as a key for each stop time
	 */
	public StopTime searchStopTimes(int stopTimeId){
		return stopTimes.get(stopTimeId);
	}

	protected String getNewestImport() {
		return "";
	}

	/**
	 * Converts each ASCII character in the ID to its decimal representation and appends it to an integer.
	 * @param id The object's ID as a String.
	 * @return The ID as an appended integer. This ID will represent an attribute of the relative class,
	 * but may not be the ID be used when storing this object in a hash table.
	 */
	private int toDecimal(String id) {
		byte[] idBytes = id.getBytes(StandardCharsets.US_ASCII);

		StringBuilder idByteString = new StringBuilder();
		for (byte idByte : idBytes) {
			idByteString.append(idByte);
		}
		return Integer.parseInt(idByteString.toString());
	}

	/**
	 * Appends one ID onto another. This method should only be used when both IDs correspond to a single object.
	 * @param v1i Integer representation of the first ID.
	 * @param v2i Integer representation of the second ID.
	 * @return A single ID to be used when storing this object in a hash table,
	 * where the first IDs value comes before the second IDs value (such that hashId: [v1i][v2i]).
	 */
	private int mergeIDs(int v1i, int v2i) {
		String v1 = String.valueOf(v1i), v2 = String.valueOf(v2i);

		return Integer.parseInt(v1 + v2);
	}
}