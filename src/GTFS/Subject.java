package GTFS;


import java.util.List;

/**
 * @author nairac
 * @version 1.0
 * @created 05-Oct-2022 8:14:32 PM
 */
public abstract class Subject extends GTFS {

	public static final List<Observer> observers = null;
	public static final Observer m_Observer = null;

	/**
	 * 
	 * @param observer
	 */
	public abstract void attach(Observer observer);

	/**
	 * 
	 * @param observer
	 */
	public abstract Observer detach(Observer observer);

	public abstract void notifyObserver();

}