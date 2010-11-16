/*  
 * Growing: Plants grow (gain health) by default if they can. 
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
import org.snowcrash.utilities.Constants;

/**
 * @author dearnest
 * Plants grow (gain health) by default if they can.  They are limited
 * by their possible max health.
 * 11/10/10	DE 	Implemented
 * 	
 */

public class Growing implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		if (myCritter.getHealth() + Constants.PLANT_REGEN < myCritter.getMaxHealth()) {
			myCritter.setHealth(myCritter.getHealth() + Constants.PLANT_REGEN);
		} else {
			myCritter.setHealth(myCritter.getMaxHealth());
		}
		
	}
	
}
