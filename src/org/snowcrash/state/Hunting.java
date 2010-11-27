package org.snowcrash.state;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.rule.NoSuchRuleException;
import org.snowcrash.rule.RuleFactory;
import org.snowcrash.rule.Rules;
import org.snowcrash.utilities.Pair;
import org.snowcrash.world.World;

public class Hunting implements State {

	@Override
	public void act(StateContext stateContext, Critter myCritter) {
		World map = World.getInstance();
		Pair<Integer, Integer> targetPos = map.search(myCritter, myCritter.getPrototype().equals(CritterPrototype.PREDATOR) ? CritterPrototype.PREY : CritterPrototype.PLANT);
		if (targetPos != null) {
			Critter target = map.get(targetPos);
			try {
				if (isCaught(myCritter, target)) {
					myCritter.setActed(true);
					if (target.getPrototype().equals(CritterPrototype.PLANT)) {
						if (target.getSize().equals(Size.LARGE) || target.getSize().equals(Size.MEDIUM)) {
							int lostHealth = myCritter.getMaxHealth() - myCritter.getHealth();
							if (target.getHealth() < lostHealth) {
								target.die();
								myCritter.setHealth(myCritter.getHealth() + target.getHealth() > myCritter.getMaxHealth() ? myCritter.getMaxHealth() : myCritter.getHealth() + target.getHealth());
							} else {
								target.setHealth(target.getHealth() - lostHealth);
								myCritter.setHealth(myCritter.getHealth() + target.getHealth() > myCritter.getMaxHealth() ? myCritter.getMaxHealth() : myCritter.getHealth() + target.getHealth());
							}
						} else {
							target.die();
							myCritter.setHealth(myCritter.getHealth() + target.getHealth() > myCritter.getMaxHealth() ? myCritter.getMaxHealth() : myCritter.getHealth() + target.getHealth());
						}
					}
					map.move(map.getCurrentPos(), targetPos);
				} else {
					myCritter.getMyStateContext().setState(new Moving());
					myCritter.getMyStateContext().act(myCritter);
				}
			} catch (NoSuchRuleException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isCaught(Critter myCritter, Critter target) throws NoSuchRuleException {
		return RuleFactory.getInstance().getRule(Rules.HUNTING).determine(myCritter, target);
	}

}
