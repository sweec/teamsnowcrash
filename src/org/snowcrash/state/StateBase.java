/*  
 * StateBase: State class from which all state classes inherit. 
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

/**
 * @author dearnest
 * State class from which all state classes inherit.
 * 
 * 10/24/10	DE 	Added License notice.
 * 11/03/10	DE	No more critter interface
 * 
 */

public class StateBase implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		// TODO Auto-generated method stub

	}

}
