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

import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.state.Searching;
import org.snowcrash.state.StateContext;
import org.snowcrash.utilities.Pair;
import org.snowcrash.utilities.RandomNumbers;
import org.snowcrash.world.World;

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
 * 11/19/10	DE	Updated constructors
 * 11/20/10	DE	Sets the initial StateContext in constructors.
 * 11/21/10	DE	Extracted size data constants and added them to the Size enum. 
 * 				Sets initial health to half maxHealth.
 * 
 */

public class Critter extends CritterTemplate implements Cloneable {

	private HashMap<Trait, Pair<Integer, Integer>> traits;
	private boolean acted = false;
	private boolean isAlive = true;
	private int actionCost;
	private int health;
	private int maxHealth;
	private StateContext myStateContext;
	private int age = 0;
	private String templateUuid;
	private String critterName;

	// add no-arguments constructor as required by gson
	public Critter() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Critter(Critter critter1, Critter critter2) {
		this.uuid = UUID.randomUUID().toString();
		setSizeData(critter1.getSize());
		setPrototype(critter1.getPrototype());
		setTemplateUuid(critter1.getTemplateUuid());
		this.name = critter1.getName();
		critterName = NameGenerator.getName(name);
		RandomNumbers rn = RandomNumbers.getInstance();
		traits = new HashMap<Trait, Pair<Integer,Integer>>();
		traits.put(Trait.CAMO, new Pair<Integer, Integer>(rn.selectOne(critter1.traits.get(Trait.CAMO)), rn.selectOne(critter2.traits.get(Trait.CAMO))));
		traits.put(Trait.COMBAT, new Pair<Integer, Integer>(rn.selectOne(critter1.traits.get(Trait.COMBAT)), rn.selectOne(critter2.traits.get(Trait.COMBAT))));
		traits.put(Trait.ENDURANCE, new Pair<Integer, Integer>(rn.selectOne(critter1.traits.get(Trait.ENDURANCE)), rn.selectOne(critter2.traits.get(Trait.ENDURANCE))));
		traits.put(Trait.SPEED, new Pair<Integer, Integer>(rn.selectOne(critter1.traits.get(Trait.SPEED)), rn.selectOne(critter2.traits.get(Trait.SPEED))));
		traits.put(Trait.VISION, new Pair<Integer, Integer>(rn.selectOne(critter1.traits.get(Trait.VISION)), rn.selectOne(critter2.traits.get(Trait.VISION))));
		myStateContext = new StateContext(new Searching());
	}
	
	public Critter(CritterTemplate template) {
		this.uuid = UUID.randomUUID().toString();
		setSizeData(template.getSize());
		setPrototype(template.getPrototype());
		setTemplateUuid(template.getUuid());
		this.name = template.getName();
		critterName = NameGenerator.getName(name);
		traits = new HashMap<Trait, Pair<Integer,Integer>>();
		RandomNumbers rn = RandomNumbers.getInstance();
		traits.put(Trait.CAMO, rn.getIntegerPair(template.getTraitRange(Trait.CAMO) != null ? template.getTraitRange(Trait.CAMO) : new Pair<Integer,Integer> (1,1)));
		traits.put(Trait.COMBAT, rn.getIntegerPair(template.getTraitRange(Trait.COMBAT) != null ? template.getTraitRange(Trait.COMBAT): new Pair<Integer,Integer> (1,1)));
		traits.put(Trait.ENDURANCE, rn.getIntegerPair(template.getTraitRange(Trait.ENDURANCE) != null ? template.getTraitRange(Trait.ENDURANCE): new Pair<Integer,Integer> (1,1)));
		traits.put(Trait.SPEED, rn.getIntegerPair(template.getTraitRange(Trait.SPEED) != null ? template.getTraitRange(Trait.SPEED): new Pair<Integer,Integer> (1,1)));
		traits.put(Trait.VISION, rn.getIntegerPair(template.getTraitRange(Trait.VISION) != null ? template.getTraitRange(Trait.VISION): new Pair<Integer,Integer> (1,1)));
		myStateContext = new StateContext(new Searching());
	}
	
	/**
	 * Tells this critter to act, calling the StateContext's act method.
	 */
	public void act() {
		System.out.println(this.critterName + " is acting.");
		this.myStateContext.act(this);
	}
	
	public Critter clone() throws CloneNotSupportedException {
		Critter copy = (Critter) super.clone();
		copy.getMyStateContext().setState(new Searching());
		copy.age = 0;
		copy.critterName = NameGenerator.getName(copy.name);
		return copy;
	}
	
	/**
	 * Default death by starvation.
	 */
	public void die() {
		die("starvation");
	}
	
	/**
	 * Death by a killer besides starvation.
	 * @param killer
	 */
	public void die(String killer) {
		this.isAlive = false;
		World.getInstance().addTurnLogEntry(this.critterName + " was killed by " + killer + ".");
	}
	
	public int getActionCost() {
		return actionCost;
	}

	public int getAge() {
		return age;
	}
	
	public String getCritterName() {
		return critterName;
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
	
	public String getTemplateUuid() {
		return templateUuid;
	}
	
	
	public int getTrait(Trait trait) {
		Pair<Integer,Integer> pair = traits.get(trait);
		return (pair.getLeft() + pair.getRight()) / 2;
	}

	public HashMap<Trait, Pair<Integer, Integer>> getTraits() {
		return traits;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void incrementAge() {
		this.age = age + 1;
	}

	public boolean isActed() {
		return acted;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setActed(boolean acted) {
		this.acted = acted;
	}

	public void setActionCost(int actionCost) {
		this.actionCost = actionCost;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void setCritterName(String critterName) {
		this.critterName = critterName;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setMyStateContext(StateContext myStateContext) {
		this.myStateContext = myStateContext;
	}

	private void setSizeData(Size trait) {
		size = trait;
		maxHealth = trait.getMaxHealth();
		actionCost = trait.getActionCost();
		health = trait.getInitialHealth();
	}

	public void setTemplateUuid(String templateUuid) {
		this.templateUuid = templateUuid;
	}

	public void setTraits(HashMap<Trait, Pair<Integer, Integer>> traits) {
		this.traits = traits;
	}

	public String toString() {
		return "Critter [traits=" + traits + ", acted=" + acted
				+ ", actionCost=" + actionCost + ", health=" + health
				+ ", maxHealth=" + maxHealth + ", size=" + size
				+ ", myStateContext=" + myStateContext + ", age=" + age
				+ ", prototype=" + prototype + ", templateUuid=" + templateUuid
				+ ", uuid=" + uuid + "]";
	}

}
