/*  
 * StateContext: Manages states for an object. 
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

/**
 * @author dearnest
 * Manages states for an object. 
 * 
 * 10/23/10	DE 	Added License notice.
 * 11/03/10	DE	No more critter interface
 * 
 */

import org.snowcrash.critter.Critter;

public class StateContext {
	private State myState;
	
	/**
	 * All StateContexts are initialized with a state.  This prevents an issue
	 * where the calling object attempts to act but there is no state to act upon.
	 * @param state
	 */
	public StateContext(State state) {
		setState(state);
	}
	
	/**
	 * For when the state needs to change.
	 * @param state
	 */
	public void setState(State state) {
		this.myState = state;
	}
	
	/**
	 * This tells the state to act.
	 */
	public void act(Critter myCritter) {
		this.myState.act(this, myCritter);
	}

}
