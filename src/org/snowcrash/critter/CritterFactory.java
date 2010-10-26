/*  
 * CritterFactory: Returns critter instances based on CritterTemplate or parents. 
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

/**
 * @author dearnest
 * Generates instances of critters based on the critter template or
 * the two parent critters (aka, reproduction).
 * 
 * 10/23/10	DE 	PLANT/PREY/PREDATOR moved to enum CritterPrototype. Added License notice.
 * 
 */

public class CritterFactory {
	
	private CritterFactory instance = null;
	
	/**
	 * Gets the CritterFactory singleton.
	 * @return
	 */
	public CritterFactory getInstance() {
		if (instance == null) {
			return new CritterFactory();
		} else {
			return instance;
		}
	}
	
	private CritterFactory() {};
	
	/**
	 * Returns a Critter based on the template.
	 * @param template
	 * @return
	 */
	public static Critter getCritter(CritterTemplate template) {
		
		switch (template.getPrototype()) {
			case PLANT:
				return new Plant(template);
			case PREDATOR:
				return new Predator(template);
			case PREY:
				return new Prey(template);
		}
		return null;
	}

	/**
	 * Returns a Critter based on the two types.
	 * @param critter1
	 * @param critter2
	 * @return
	 */
	public static Critter getCritter(Critter critter1, Critter critter2) {
		
		if (critter1 != null && critter2 != null) {
			if (critter1 instanceof Plant && critter2 instanceof Plant) {
				return new Plant(critter1, critter2);
			}
			if (critter1 instanceof Predator && critter2 instanceof Predator) {
				return new Plant(critter1, critter2);
			}
			if (critter1 instanceof Prey && critter2 instanceof Prey) {
				return new Plant(critter1, critter2);
			}
		}
		return null;
	}

}
