package org.snowcrash.state;

import org.snowcrash.critter.Critter;

public interface State {
	public abstract void act(StateContext stateContext, Critter myCritter);
}
