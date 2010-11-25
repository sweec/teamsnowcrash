package org.snowcrash.state;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;

public class Hunting implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		World map = World.getInstance();
		Pair<Integer, Integer> target = map.search(myCritter, myCritter.getPrototype().equals(CritterPrototype.PREDATOR) ? CritterPrototype.PREY : CritterPrototype.PLANT);
		
	}
	
	private boolean isCaught(Critter myCritter, Critter target) {
		return true;
	}

}
