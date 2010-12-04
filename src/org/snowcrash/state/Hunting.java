package org.snowcrash.state;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.rule.NoSuchRuleException;
import org.snowcrash.rule.RuleFactory;
import org.snowcrash.rule.Rules;
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;

public class Hunting implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		System.out.println(myCritter.getCritterName() + " entered Hunting.");
		World map = World.getInstance();
		Pair<Integer, Integer> targetPos = map.search(myCritter, myCritter.getPrototype().equals(CritterPrototype.PREDATOR) ? CritterPrototype.PREY : CritterPrototype.PLANT);
		if (targetPos != null) {
			Critter target = map.get(targetPos);
			try {
				if (isCaught(myCritter, target)) {
					myCritter.setActed(true);
					if (target.getPrototype().equals(CritterPrototype.PLANT)) {
						int lostHealth = myCritter.getMaxHealth() - myCritter.getHealth();
						if (target.getHealth() < lostHealth) {
							System.out.println(target.getCritterName() + " is killed by " + myCritter.getCritterName());
							target.die(myCritter.getCritterName());
							myCritter.setHealth(myCritter.getHealth() + target.getHealth() > myCritter.getMaxHealth() ? myCritter.getMaxHealth() : myCritter.getHealth() + target.getHealth());
							map.move(map.getCurrentPos(), targetPos);
						} else {
							System.out.println("Nom nom nom");
							target.setHealth(target.getHealth() - lostHealth);
							myCritter.setHealth(myCritter.getHealth() + target.getHealth() > myCritter.getMaxHealth() ? myCritter.getMaxHealth() : myCritter.getHealth() + target.getHealth());
						}
					} else {
						System.out.println(target.getCritterName() + " is killed by " + myCritter.getCritterName());
						target.die(myCritter.getCritterName());
						myCritter.setHealth(myCritter.getHealth() + target.getHealth() > myCritter.getMaxHealth() ? myCritter.getMaxHealth() : myCritter.getHealth() + target.getHealth());
						map.move(map.getCurrentPos(), targetPos);
					}
					myCritter.getMyStateContext().setState(new Searching());
				} else {
					System.out.println(myCritter.getCritterName() + " goes to Moving after failing to catch.");
					myCritter.getMyStateContext().setState(new Moving());
					myCritter.getMyStateContext().act(myCritter);
				}
			} catch (NoSuchRuleException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(myCritter.getCritterName() + " goes to Moving after not finding anything to catch.");
			myCritter.getMyStateContext().setState(new Moving());
			myCritter.getMyStateContext().act(myCritter);
		}
	}
	
	private boolean isCaught(Critter myCritter, Critter target) throws NoSuchRuleException {
		return RuleFactory.getInstance().getRule(Rules.HUNTING).determine(myCritter, target);
	}

}
