/*  
 * RandomNumbers: Instantiates a random number generator and uses the same seed throughout
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

package org.snowcrash.utilities;

import java.util.Random;

/**
 * 
 * @author dearnest
 * Instantiates a random number generator and uses the same seed throughout
 * 11/21/10	DE	Synchronizes the singleton.
 * 
 */

public final class RandomNumbers {

	private static RandomNumbers instance = null;
	private static Random generator = null;
	
	public static synchronized RandomNumbers getInstance() {
		if (instance == null) {
			instance = new RandomNumbers();
		}
		
		return instance;
	}
	
	private RandomNumbers() {
		generator = new Random(System.currentTimeMillis());
	}
	
	/**
	 * Returns an integer that exists in the range of the Pair, inclusive.
	 * @param range
	 * @return
	 */
	public Integer getInteger(final Pair<Integer, Integer> range) {
		return generator.nextInt(range != null ? range.getRight().intValue() - range.getLeft().intValue() + 1 : 1) + (range != null ? range.getLeft().intValue() : 1);
	}

	/**
	 * Returns an Integer Pair whose two Integers exists within the range.
	 * @param range
	 * @return
	 */
	public Pair<Integer, Integer> getIntegerPair(final Pair<Integer, Integer> range) {
		return new Pair<Integer, Integer>(getInteger(range), getInteger(range));
	}
	
	/**
	 * Returns a random selection of one of the pair of integers passed in.
	 * @param pair
	 * @return
	 */
	public Integer selectOne(final Pair<Integer, Integer> pair) {
		boolean select = generator.nextBoolean();
		if (select) {
			return pair.getLeft();
		} else {
			return pair.getRight();
		}
	}
	
	/**
	 * Returns a random integer based on the range passed in.
	 * @param range
	 * @return
	 */
	public int getInt(final int range) {
		return generator.nextInt(range);
	}
}
