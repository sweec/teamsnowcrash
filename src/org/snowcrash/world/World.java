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

import org.snowcrash.critter.Critter;
import org.snowcrash.utilities.Pair;

/**
 *   
 * @author dearnest
 * Describes the world map on which critters play.
 * 11/01/2010	DE	Created.
 * 
 */

public class World {

	private static World instance;
	private Critter[][] map;
	private int sizeX;
	private int sizeY;
	
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
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
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

	public Critter[][] getMap() {
		return map;
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (map[i][j] != null) {
					sb.append("(" + i + "," + j + "): ");
					sb.append(map[i][j].toString() + "\n");
				}
			}
		}
		return sb.toString();
	}
	

}
