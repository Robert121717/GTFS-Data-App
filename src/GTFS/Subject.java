/*
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