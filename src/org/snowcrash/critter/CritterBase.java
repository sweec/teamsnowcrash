/*  
 * CritterBase: Base critter class that all critter prototypes extend. 
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

package org.snowcrash.critter;

import java.util.HashMap;

import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.state.StateContext;
import org.snowcrash.utilities.Pair;

/**
 * 
 * @author dearnest
 * Base critter class that all critter prototypes extend.
 * 10/23/10	DE	Added License.  Added Comments.
 * 10/24/10	DE	traits HashMap now uses Pair. Implemented stub methods
 * 
 */

public abstract class CritterBase implements Critter {

	private HashMap<String, Pair<Integer, Integer>> traits;
	private boolean acted = false;
	private int actionCost;
	private int health;
	private int maxHealth;
	private Size size;
	private StateContext myStateContext;
	
	@Override
	public boolean isActed() {
		return acted;
	}

	@Override
	public void setActed(boolean acted) {
		this.acted = acted;
	}

	@Override
	public int getActionCost() {
		return actionCost;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public int getMaxHealth() {
		return maxHealth;
	}

	@Override
	public Size getSize() {
		return size;
	}

	@Override
	public void die() {
		
	}

	@Override
	public String toString() {
		return "CritterBase [traits=" + traits + ", acted=" + acted
				+ ", actionCost=" + actionCost + ", health=" + health
				+ ", maxHealth=" + maxHealth + ", size=" + size
				+ ", myStateContext=" + myStateContext + "]";
	}

	@Override
	public int getTrait(Trait trait) {
		Pair<Integer,Integer> pair = traits.get(trait);
		return (pair.getLeft() + pair.getRight()) / 2;
	}
	
	protected void setSizeData(Size trait) {
		size = trait;
		switch (trait) {
			case SMALL:
				maxHealth = 30;
				actionCost = 2;
				break;
			case MEDIUM:
				maxHealth = 60;
				actionCost = 4;
				break;
			case LARGE:
				maxHealth = 90;
				actionCost = 6;
				break;
		}
	}
}
