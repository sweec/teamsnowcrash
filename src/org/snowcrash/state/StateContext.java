package org.snowcrash.state;

import org.snowcrash.critter.Critter;

/**
 * This is a small wrapping class that manages what the current state is.
 * @author dearnest
 *
 */

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
