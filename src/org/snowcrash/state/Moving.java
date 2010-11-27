/*  
 * Moving: Critter attempts to move to an empty spot within range. 
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
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;

/**
 * 
 * @author dearnest
 * Critter attempts to move to an empty spot within range.
 * 10/21/10	DE	Created.
 *
 */

public class Moving implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		Pair<Integer,Integer> target = World.getInstance().search(myCritter, true);
		myCritter.setHealth(myCritter.getHealth() - myCritter.getActionCost());
		if (target != null) {
			if (myCritter.getHealth() > 0) {
				myCritter.setActed(true);
				myCritter.getMyStateContext().setState(new Searching());
				World.getInstance().move(World.getInstance().getCurrentPos(), target);
			} else {
				// TODO Register death.
				// TODO Add to LinkedList log
				// TODO Implement die()
				myCritter.setActed(true);
				myCritter.die();
			}
		}
	}

}
