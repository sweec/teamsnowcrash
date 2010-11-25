/*  
 * Size: Enumerates the sizes critters come in. 
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

package org.snowcrash.critter.data;

/**
 * 
 * @author dearnest
 * Enumerates the sizes critters come in.
 * 10/24/10	DE	Created.
 * 11/21/10	DE	Added size data constant information. Moved from Critter.
 * 
 */

public enum Size {
	SMALL(30,2,15), MEDIUM(60,4,30), LARGE(90,6,45);
	
	private int maxHealth;
	private int actionCost;
	private int initialHealth;
		
	private Size(int maxHealth, int actionCost, int initialHealth) {
		this.maxHealth = maxHealth;
		this.actionCost = actionCost;
		this.initialHealth = initialHealth;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getActionCost() {
		return actionCost;
	}
	public int getInitialHealth() {
		return initialHealth;
	}
	
}
