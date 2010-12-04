/*  
 * Searching: Initial state all critters start in to determine what action to pursue. 
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

package org.snowcrash.state;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.CritterPrototype;

/**
 * 
 * @author dearnest
 * Initial state all critters start in to determine what action to pursue.
 * 11/20/10	DE 	Implemented
 * 
 */

public class Searching implements State {

	private static final double REPRODUCE_MIN_HEALTH = .75;

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		System.out.println(myCritter.getCritterName() + " entered Searching.");
		if ((double)myCritter.getHealth() /(double)myCritter.getMaxHealth() >= REPRODUCE_MIN_HEALTH) {
			System.out.println(myCritter.getCritterName() + " goes to Reproducing.");
			stateContext.setState(new Reproducing());
			stateContext.act(myCritter);
		} else {
			if (myCritter.getPrototype().equals(CritterPrototype.PLANT)) {
				System.out.println(myCritter.getCritterName() + " goes to Growing.");
				stateContext.setState(new Growing());
				stateContext.act(myCritter);
			} else {
				System.out.println(myCritter.getCritterName() + " goes to Hunting.");
				stateContext.setState(new Hunting());
				stateContext.act(myCritter);
			}
		}
		
	}

}
