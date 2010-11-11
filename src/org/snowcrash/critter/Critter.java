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
import java.util.UUID;

import org.snowcrash.critter.data.CritterPrototype;
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
 * 10/27/10	DE	Added age.
 * 11/03/10	DE	Removed Interface
 * 11/09/10	DE	Added UUID
 * 11/10/10	DE	Added getMyStateContext()
 * 
 */

public class Critter {

	private HashMap<String, Pair<Integer, Integer>> traits;
	private boolean acted = false;
	private int actionCost;
	private int health;
	private int maxHealth;
	private Size size;
	private StateContext myStateContext;
	private int age = 0;
	private CritterPrototype prototype;
	private String templateUuid;
	private String uuid;

	// add no-arguments constructor as required by gson
	public Critter() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Critter(Critter critter1, Critter critter2) {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Critter(CritterTemplate template) {
		this.uuid = UUID.randomUUID().toString();
		setSizeData(template.getSize());
	}
	
	public void die() {
		
	}
	
	public int getActionCost() {
		return actionCost;
	}

	public int getAge() {
		return age;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public StateContext getMyStateContext() {
		return myStateContext;
	}

	public CritterPrototype getPrototype() {
		return prototype;
	}
	
	public Size getSize() {
		return size;
	}
	
	public String getTemplateUuid() {
		return templateUuid;
	}
	
	public int getTrait(Trait trait) {
		Pair<Integer,Integer> pair = traits.get(trait);
		return (pair.getLeft() + pair.getRight()) / 2;
	}
	
	
	public String getUuid() {
		return uuid;
	}

	public boolean isActed() {
		return acted;
	}
	
	public void setActed(boolean acted) {
		this.acted = acted;
		this.age++;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	private void setSizeData(Size trait) {
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

	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}

	public String toString() {
		return "Critter [traits=" + traits + ", acted=" + acted
				+ ", actionCost=" + actionCost + ", health=" + health
				+ ", maxHealth=" + maxHealth + ", size=" + size
				+ ", myStateContext=" + myStateContext + ", age=" + age
				+ ", prototype=" + prototype + ", templateUuid=" + templateUuid
				+ "]";
	}

}
