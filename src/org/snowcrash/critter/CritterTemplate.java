/*  
 * CritterTemplate: Describes a template upon which a critter is derived. 
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
import org.snowcrash.utilities.Pair;

/**
 * 
 * @author dearnest
 * Describes a template upon which a critter is derived.
 * 10/24/10	DE	Traits converted to trait range, uses Pair. Added all class
 * 				variables, getters and setters, and constructor.
 * 
 */

public class CritterTemplate {

	private Size size;
	private String name;
	private String uuid;
	private CritterPrototype prototype;
	private HashMap<Trait, Pair<Integer,Integer>> traitRange;

	public CritterTemplate() {
		traitRange = new HashMap<Trait, Pair<Integer,Integer>>();
		uuid = UUID.randomUUID().toString();
	}
	
	public CritterPrototype getPrototype() {
		return prototype;
	}

	public void setPrototype(CritterPrototype prototype) {
		this.prototype = prototype;
	}
	
	public void setTraitRange(Trait trait, Pair<Integer,Integer> range) {
		traitRange.put(trait, range);
	}
	
	public Pair<Integer,Integer> getTraitRange(Trait trait) {
		return traitRange.get(trait);
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}
}
