/*  
 * World: Describes the world map on which critters play. 
 * Copyright (C) 2010  Team Snow Crash
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Artistic License/GNU GPL as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Artistic License/GNU General Public License for more details.
 *
 * You should have received a copy of the Artistic license/GNU General 
 * Public License along with this program.  If not, see
 * <http://dev.perl.org/licenses/artistic.html> and 
 * <http://www.gnu.org/licenses/>.
 * 
 */

package org.snowcrash.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterFactory;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.NameGenerator;
import org.snowcrash.critter.StatisticsCollector;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.state.Searching;
import org.snowcrash.timeengine.TimeListener;
import org.snowcrash.utilities.Pair;
import org.snowcrash.utilities.RandomNumbers;

/**
 *   
 * @author dearnest
 * Describes the world map on which critters play.
 * 11/01/2010	DE	Created.
 * 11/03/10	DE	No more critter interface
 * 11/10/10	DE	Added processTurn(); added constructor; added currentTurn
 * 11/20/10	DE	Added clearCritterActedFlags
 * 11/21/10	DE	Set initial currentPos.
 * 11/22/10	DE	Fixed iterating bugs with search and processTurn
 * 12/01/10	DE	Added randomPopulate(); saves initial template list
 * 
 */

public class World implements DatabaseObject, TimeListener {

	private static World instance;

	public static synchronized World getInstance() {
		if (instance == null) {
			instance = new World();
		}
		return instance;
	}
	
	public static World reset() {
		instance = null;
		NameGenerator.reset();
		return getInstance();
	}
	
	private Critter[][] map;
	private int sizeX;
	private int sizeY;
	private int turns;
	private int currentTurn;
	private Pair<Integer, Integer> currentPos;
	private boolean isNext = false;
	private LinkedList<String> turnLog = null;
	private static Set<WorldObserver> observers = new HashSet<WorldObserver>();
	private ArrayList<Pair<CritterTemplate,Integer>> initTemplateList = null;
	private Critter[][] initMap = null;
	private ArrayList<Critter> turnDeaths = null;
	private FileManager logFile = null;
	
	public static void addObserver(WorldObserver observer) {
		observers.add(observer);
	}

	public static void removeObserver(WorldObserver observer) {
		observers.remove(observer);
	}

	public static void clearObservers() {
		observers.clear();
	}
	
	public World() {
		this.sizeX = 50;
		this.sizeY = 50;
		this.turns = 30;
		restart();
		this.logFile = new FileManager(); 
		instance = this;	// needed for load simulation since instance is not saved
	}
	
	public void restart()
	{
		NameGenerator.reset();
		currentTurn = 0;
		isNext = false;
		map = new Critter[sizeX][sizeY];
		initMap = null;
		currentPos = new Pair<Integer,Integer>(0,0);
		turnLog = new LinkedList<String>();
		turnDeaths = new ArrayList<Critter>();
		
		World.removeObserver(StatisticsCollector.getInstance());
		World.addObserver(new StatisticsCollector());
	}
	
	private void resetTurnLog() {
		this.turnLog = new LinkedList<String>();
	}
	
	private void resetTurnDeaths() {
		this.turnDeaths = new ArrayList<Critter>();
	}
	
	public void addTurnLogEntry(String entry) {
		this.turnLog.add(entry);
	}
	
	public void printTurnLogContents() {
		System.out.println("Turn Log: ");
		System.out.println(Arrays.toString(this.turnLog.toArray()));
	}
	
	public LinkedList<String> getTurnLog()
	{
		return this.turnLog;
	}
	/**
	 * Adds a critter to a specific x,y location.
	 * @param pair 
	 * @param critter
	 * @return
	 */
	public boolean add(Pair<Integer, Integer> pair, Critter critter) {
		if (checkX(pair.getLeft()) && checkY(pair.getRight())) {
			map[pair.getLeft()][pair.getRight()] = critter;
			return true;
		}
		return false;
	}

	/**
	 * Adjusts the acted flag of a target location's critter.
	 * @param target
	 * @param health
	 */
	public void adjustCritterActed(Pair<Integer, Integer> target, boolean isActed) {
		if (map[target.getLeft()][target.getRight()] != null) {
			map[target.getLeft()][target.getRight()].setActed(isActed);
		}
	}
	
	/**
	 * Adjusts the health of a target location's critter.
	 * @param target
	 * @param health
	 */
	public void adjustCritterHealth(Pair<Integer, Integer> target, int health) {
		if (map[target.getLeft()][target.getRight()] != null) {
			map[target.getLeft()][target.getRight()].setHealth(health);
		}
	}

	/**
	 * Verifies that x is in the boundaries of the map
	 * @param x
	 * @return
	 */
	private boolean checkX(int x) {
		if (sizeX > x && x >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifies that y is in the boundaries of the map
	 * @param y
	 * @return
	 */
	private boolean checkY(int y) {
		if (sizeY > y && y >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Resets all the map Critter's isActed flag to false. 
	 */
	public void turnCleanUp() {
		for (int i = 0; i < sizeX; i++ ) {
			for (int j = 0; j < sizeY; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isAlive()) {
						map[i][j].setActed(false);
						map[i][j].getMyStateContext().setState(new Searching());
						map[i][j].incrementAge();
					} else {
						map[i][j] = null;
					}
				}
			}
		}
		resetTurnLog();
		resetTurnDeaths();
	}
	
	/**
	 * Retrieves a critter at the coordinate or returns null if nothing is there.
	 * @param coordinate
	 * @return
	 */
	public Critter get(Pair<Integer, Integer> coordinate) {
		try {
			return map[coordinate.getLeft()][coordinate.getRight()];
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Pair<Integer, Integer> getCurrentPos() {
		return currentPos;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}
	
	public Critter[][] getMap() {
		return map;
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}
	
	public int getTurns() {
		return turns;
	}
	
	private boolean hasNext() {
		if (isNext) {
			isNext = false;
			if (currentPos.getRight() + 1 >= sizeY) {
				currentPos = new Pair<Integer, Integer> (currentPos.getLeft() + 1, 0);
			} else {
				currentPos = new Pair<Integer, Integer> (currentPos.getLeft(), currentPos.getRight() + 1);
			}
		}
		int currX = currentPos.getLeft();
		int currY = currentPos.getRight();
		for (;currX < sizeX; currX++) {
			for (;currY < sizeY; currY++) {
				if (map[currX][currY] != null) {
					System.out.println("Map At: " + currX + "," + currY);
					isNext = true;
					currentPos = new Pair<Integer, Integer>(currX, currY);
					return true;
				}
			}
			currY = 0;
		}
		return false;
	}
	
	/**
	 * Moves a critter from one map space to another.
	 * @param origin
	 * @param target
	 * @return
	 */
	public boolean move(Pair<Integer, Integer> origin, Pair<Integer, Integer> target) {
		if (checkX(target.getLeft()) && checkY(target.getRight()) 
				&& checkX(origin.getLeft()) && checkY(origin.getRight())) {
			map[target.getLeft()][target.getRight()] = map[origin.getLeft()][origin.getRight()];
			map[origin.getLeft()][origin.getRight()] = null;
			return true;
		}
		return false;
	}
	
	private Pair<Integer, Integer> next() {
		Pair<Integer, Integer> next = null;
		if (isNext) {
			next = currentPos;
		} else {
			if (hasNext()) {
				next = currentPos;
			}
		}
		
		return next;
	}
	
	/**
	 * Iterates through the world and causes critters that haven't acted to act.
	 */
	public void processTurn() {
		System.out.println("Turn: " + currentTurn);
		this.logFile.logMessage("Turn: " + (currentTurn + 1));
		while (hasNext()) {
			Pair<Integer, Integer> next = next();
			Critter critter = get(next);
			if (!critter.isActed()) {
				critter.act();
			}
		}
		printTurnLogContents();
		currentPos = new Pair<Integer, Integer> (0,0);
		currentTurn++;
	}

	/**
	 * Randomly initializes the map from a list of critters
	 * @param list
	 */
	private void randomPopulate(Iterator<Critter> list) {
		while (list.hasNext()) {
			boolean isPlaced = false;
			Critter critter = list.next();
			while(!isPlaced) {
				int randomX = RandomNumbers.getInstance().getInt(sizeX);
				int randomY = RandomNumbers.getInstance().getInt(sizeY);
				if (map[randomX][randomY] == null) {
					map[randomX][randomY] = critter;
					isPlaced = true;
				}
			}
		}
	}
	
	/**
	 * Populates the world.  Accepts an ArrayList of Pair containing the critter template
	 * and the number of critters of that template to put in the world.
	 * @param list
	 */
	public void randomPopulate(ArrayList<Pair<CritterTemplate,Integer>> list) {
		initTemplateList = list;
		Iterator<Pair<CritterTemplate,Integer>> iter = list.iterator();
		ArrayList<Critter> critterList = new ArrayList<Critter>();
		while (iter.hasNext()) {
			Pair<CritterTemplate,Integer> nextPair = iter.next();
			for (int i = 0; i < nextPair.getRight(); i++) {
				critterList.add(CritterFactory.getCritter(nextPair.getLeft()));
			}
		}
		randomPopulate(critterList.iterator());
		initMap = new Critter[sizeX][sizeY];
		for(int i=0;i < sizeX;i++)
			for (int j = 0;j < sizeY;j++)
				initMap[i][j] = map[i][j];
		logFile.setLogger();
	}

	/**
	 * Sets the coordinate to null.
	 * @param coordinate
	 */
	public void remove(Pair<Integer, Integer> coordinate) {
		try {
			map[coordinate.getLeft()][coordinate.getRight()] = null;
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches for either an empty grid spot in range (if isEmpty is true) or another 
	 * critter of the same type as the one passed in based on the passed in critter's traits.
	 * @param critter 
	 * @param isEmpty 
	 * @return
	 */
	public Pair<Integer, Integer> search(Critter critter, boolean isEmpty) {
		if (isEmpty) {
			return search(critter, null, null);
		}
		return search(critter, null, critter.getTemplateUuid());
	}

	/**
	 * Searches for a critter of the passed in prototype based on the passed in critter's traits.
	 * @param critter
	 * @param prototype
	 * @return
	 */
	public Pair<Integer, Integer> search(Critter critter, CritterPrototype prototype) {
		return search(critter, prototype, null);
	}

	private Pair<Integer, Integer> search(Critter critter, CritterPrototype prototype, String templateUuid) {
		int radius = critter.getTrait(Trait.VISION);
		int x = currentPos.getLeft();
		int y = currentPos.getRight();
		int xLow = 0;
		int yLow = 0;
		
		if (!((x - radius) < 0)) {
			xLow = x - radius; 
		}
		if (!((y - radius) < 0)) {
			yLow = y - radius; 
		}
		
		ArrayList<Pair<Integer, Integer>> targets = new ArrayList<Pair<Integer,Integer>>();
		Critter target = null;
		for (int i = xLow; (i < sizeX) && (i <= x + radius); i++) {
			for (int j = yLow; (j < sizeY) && (j <= y + radius); j++) {
				if (x == i && y == j) {
					continue;
				}
				target = get(new Pair<Integer, Integer> (i,j));
				if (target == null && prototype == null && templateUuid == null) {
					targets.add(new Pair<Integer, Integer> (i,j));
					continue;
				}
				if (prototype != null && target != null && prototype.equals(target.getPrototype())) {
					targets.add(new Pair<Integer, Integer> (i,j));
					continue;
				}
				if (templateUuid != null && target != null && templateUuid.equals(target.getTemplateUuid())) {
					targets.add(new Pair<Integer, Integer> (i,j));
					continue;
				}
			}
		}

		if (targets.size() > 0) {
			return targets.get(RandomNumbers.getInstance().getInt(targets.size()));
		} else {
			return null;
		}
	}
	
	/**
	 * Sets the world size to x by y coordinates.  This initializes
	 * the size of the Critter map.
	 * @param x
	 * @param y
	 */
	public void setSize(int x, int y) {
		sizeX = x;
		sizeY = y;
		map = new Critter[sizeX][sizeY];
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	@Override
	public String toString() {
		return "World [map=" + Arrays.deepToString(map) + ", sizeX=" + sizeX
				+ ", sizeY=" + sizeY + ", turns=" + turns + ", currentTurn="
				+ currentTurn + ", currentPos=" + currentPos + ", isNext="
				+ isNext + "]";
	}

	@Override
	public void tickOccurred() {
		this.processTurn();
		this.logTurn();
		for (WorldObserver observer : observers) {
			observer.updateWorld(this);
			observer.updateStatistics(turnDeaths);
		}
		this.turnCleanUp();
	}

	@Override
	public void timeExpired() {
		for (WorldObserver observer : observers) {
			observer.notifyTheEnd();
		}
		
	}

	@Override
	public void timerStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getId() {
		// Always a constant as it is a singleton.
		return 0;
	}

	public ArrayList<Pair<CritterTemplate, Integer>> getInitTemplateList() {
		return initTemplateList;
	}
	
	public Critter[][] getInitMap() {
		return initMap;
	}
	
	public void reportDeath(Critter critter) {
		this.turnDeaths.add(critter);
	}
	
	public void logTurn() {
		Iterator<String> iterator = turnLog.iterator();
		while (iterator.hasNext()) {
			this.logFile.logMessage(iterator.next());
		}
	}
}
