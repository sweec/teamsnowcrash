/*  
 * Detecting: Adjudicates whether or not the actor critter can find the target critter. 
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

package org.snowcrash.rule;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.utilities.RandomNumbers;

/**
 * @author dearnest
 * Adjudicates whether or not the actor critter can find the target critter.
 * 10/21/10	DE	Created.
 * 11/27/10	DE	Adjusted for balance.
 * 
 */

public class Detecting implements Rule {

	/* (non-Javadoc)
	 * @see org.snowcrash.rule.Rule#determine(org.snowcrash.critter.Critter, org.snowcrash.critter.Critter)
	 */
	@Override
	public boolean determine(Critter actor, Critter target) {
		boolean detected = false;
		double calc = (double) actor.getTrait(Trait.VISION) - (double) target.getTrait(Trait.VISION) + (double) target.getSize().getVisionMod();
		if (calc + RandomNumbers.getInstance().getInt(2) > 0) {
			detected = true;
		} else {
			if (RandomNumbers.getInstance().getInt(10) == 0) {
				detected = true;
			}
		}
		return detected;
	}

}
