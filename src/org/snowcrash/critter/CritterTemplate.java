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
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.utilities.Pair;

/**
 * 
 * @author dearnest
 * Describes a template upon which a critter is derived.
 * 10/24/10	DE	Traits converted to trait range, uses Pair. Added all class
 * 				variables, getters and setters, and constructor.
 * 11/07/10	DE	Updated constructor.
 * 11/21/10	DE	Added toString().
 * 
 */

public class CritterTemplate implements DatabaseObject {

	protected Size size;
	protected CritterPrototype prototype;
	protected String name;
	protected String uuid;
	private HashMap<Trait, Pair<Integer,Integer>> traitRange;

	/**
	 *  No-args constructor required by gson
	 */
	public CritterTemplate() {
		this.traitRange = new HashMap<Trait, Pair<Integer,Integer>>();
		
		// -- Set default values.
		for ( Trait trait : Trait.values() )
		{
			traitRange.put( trait, new Pair<Integer,Integer>( 1, 1 ) );
		}
		
		size = Size.SMALL;
	}
	
	public CritterTemplate(CritterPrototype prototype, String name) {
		this();
		this.prototype = prototype;
		this.name = name;
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Object getId()
	{
		return name;
	}

	public String getName() {
		return name;
	}
	
	public CritterPrototype getPrototype() {
		return prototype;
	}
	
	public Size getSize() {
		return size;
	}

	public Pair<Integer,Integer> getTraitRange(Trait trait) {
		return traitRange.get(trait);
	}

	public String getUuid() {
		return uuid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrototype(CritterPrototype prototype) {
		this.prototype = prototype;
	}
	
	public void setSize(Size size) {
		this.size = size;
	}

	public void setTraitRange(Trait trait, Pair<Integer,Integer> range) {
		traitRange.put(trait, range);
	}

	@Override
	public String toString() {
		return "CritterTemplate [size=" + size + ", name=" + name + ", uuid="
				+ uuid + ", prototype=" + prototype + ", traitRange="
				+ traitRange + "]";
	}
}
