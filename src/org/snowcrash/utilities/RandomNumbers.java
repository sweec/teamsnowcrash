package org.snowcrash.utilities;

import java.util.Random;

public class RandomNumbers {

	private static RandomNumbers instance = null;
	private static Random generator = null;
	
	public static RandomNumbers getInstance() {
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
	public Integer getInteger(Pair<Integer, Integer> range) {
		return generator.nextInt(range.getRight().intValue() - range.getLeft().intValue() + 1) + range.getLeft().intValue();
	}

	/**
	 * Returns an Integer Pair whose two Integers exists within the range.
	 * @param range
	 * @return
	 */
	public Pair<Integer, Integer> getIntegerPair(Pair<Integer, Integer> range) {
		return new Pair<Integer, Integer>(getInteger(range), getInteger(range));
	}
	
	/**
	 * Returns a random selection of one of the pair of integers passed in.
	 * @param pair
	 * @return
	 */
	public Integer selectOne(Pair<Integer, Integer> pair) {
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
	public int getInt(int range) {
		return generator.nextInt(range);
	}
}
