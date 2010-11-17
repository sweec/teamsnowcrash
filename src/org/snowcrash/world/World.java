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

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.utilities.Pair;
import org.snowcrash.utilities.RandomNumbers;

/**
 *   
 * @author dearnest
 * Describes the world map on which critters play.
 * 11/01/2010	DE	Created.
 * 11/03/10	DE	No more critter interface
 * 11/10/10	DE	Added processTurn(); added constructor; added currentTurn
 * 
 */

public class World {

	private static World instance;

	public static World getInstance() {
		if (instance == null) {
			instance = new World();
		}
		return instance;
	}
	
	public static World reset() {
		instance = null;
		return getInstance();
	}
	
	private Critter[][] map;
	private int sizeX;
	private int sizeY;
	private int turns;
	private int currentTurn;
	private Pair<Integer, Integer> currentPos;
	private boolean isNext = false;
	
	private World() {
		this.currentTurn = 0;
		this.sizeX = 50;
		this.sizeY = 50;
		this.turns = 1;
	}
	
	/**
	 * Adds a critter to a specific x,y location.
	 * @param x
	 * @param y
	 * @param critter
	 * @return
	 */
	public boolean add(int x, int y, Critter critter) {
		if (checkX(x) && checkY(y)) {
			map[x][y] = critter;
			return true;
		}
		return false;
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
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	
	public Pair<Integer, Integer> getCurrentPos() {
		return currentPos;
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
		isNext = false;
		int currX = currentPos.getLeft();
		int currY = currentPos.getRight();
		for (;currX < sizeX; currX++) {
			for (;currY < sizeY; currY++) {
				if (map[currX][currY] != null) {
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
	 * Searches for a critter of the passed in prototype based on the passed in critter's traits.
	 * @param critter
	 * @param prototype
	 * @return
	 */
	public Pair<Integer, Integer> search(Critter critter, CritterPrototype prototype) {
		return search(critter, prototype, null);
	}
	
	/**
	 * Searches for another critter of the same type as the one passed in based on the passed in critter's traits.
	 * @param critter
	 * @return
	 */
	public Pair<Integer, Integer> search(Critter critter) {
		return search(critter, null, critter.getTemplateUuid());
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
		for (int i = xLow; (i < sizeX) || (i < x + radius); i++) {
			for (int j = yLow; (j < sizeY) || (j < y + radius); j++) {
				if (x == i && y == j) {
					continue;
				}
				target = get(new Pair<Integer, Integer> (i,j));
				if (target == null) {
					continue;
				}
				if (prototype != null && prototype.equals(target.getPrototype())) {
					targets.add(new Pair<Integer, Integer> (i,j));
					continue;
				}
				if (templateUuid != null && templateUuid.equals(target.getTemplateUuid())) {
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
				isNext = false;
				next = currentPos;
			}
		}
		if (!(currentPos.getRight() + 1 < sizeY)) {
			currentPos = new Pair<Integer, Integer> (currentPos.getLeft() + 1, 0);
		}
		return next;
	}

	/*
	 * Iterates through the world and causes critters that haven't acted to act.
	 */
	public void processTurn() {
		while (hasNext()) {
			Pair<Integer, Integer> next = next();
			Critter critter = get(next);
			if (!critter.isActed()) {
				critter.getMyStateContext().act(critter);
			}
		}
		currentTurn++;
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
		return "World [map=" + Arrays.toString(map) + ", sizeX=" + sizeX
				+ ", sizeY=" + sizeY + ", turns=" + turns + ", currPos="
				+ currentPos + ", isNext=" + isNext + "]";
	}
	

}
