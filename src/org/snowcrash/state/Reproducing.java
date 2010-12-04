package org.snowcrash.state;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterFactory;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;

public class Reproducing implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		System.out.println(myCritter.getCritterName() + " entered Reproducing.");
		World world = World.getInstance();
		Pair<Integer,Integer> pair = world.search(myCritter, false);
		if (pair != null) {
			Pair<Integer,Integer> empty = world.search(myCritter, true);
			if (empty != null) {
				world.adjustCritterHealth(pair, world.get(pair).getHealth()/2);
				world.adjustCritterActed(pair, true);

				myCritter.setHealth(myCritter.getHealth()/2);
				myCritter.setActed(true);
				
				Critter child = CritterFactory.getCritter(myCritter, world.get(pair));
				child.setActed(true);
				child.setHealth((world.get(pair).getHealth() + myCritter.getHealth()) * 3 / 4);
				world.add(empty, child);
				world.addTurnLogEntry(child.getCritterName() + " was born!");
			} else {
				stateContext.setState(new Moving());
				stateContext.act(myCritter);
			}
		} else {
			if (myCritter.getPrototype().equals(CritterPrototype.PLANT)) {
				Pair<Integer,Integer> empty = world.search(myCritter, true);
				if (empty != null) {
					Critter child = CritterFactory.getClone(myCritter);
					myCritter.setHealth(myCritter.getHealth()/2);
					myCritter.setActed(true);
					child.setHealth(myCritter.getHealth() * 3 / 4);
					child.setActed(true);
					world.add(empty, child);
				}
			} else {
				stateContext.setState(new Hunting());
				stateContext.act(myCritter);
			}
		}
	}

}
