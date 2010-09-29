package org.snowcrash.critter;

import org.snowcrash.Constants;

public class CritterFactory {
	
	private CritterFactory instance = null;
	
	/**
	 * Gets the singleton.
	 * @return
	 */
	public CritterFactory getInstance() {
		if (instance == null) {
			return new CritterFactory();
		} else {
			return instance;
		}
	}
	
	private CritterFactory() {};
	
	/**
	 * Returns a Critter based on the template.
	 */
	public static Critter getCritter(CritterTemplate template) {
		
		switch (template.getType()) {
			case Constants.PLANT:
				return new Plant(template);
			case Constants.PREDATOR:
				return new Predator(template);
			case Constants.PREY:
				return new Prey(template);
		}
		return null;
	}

	/**
	 * Returns a Critter based on the two types.
	 */
	public static Critter getCritter(Critter critter1, Critter critter2) {
		
		if (critter1 != null && critter2 != null) {
			if (critter1 instanceof Plant && critter2 instanceof Plant) {
				return new Plant(critter1, critter2);
			}
			if (critter1 instanceof Predator && critter2 instanceof Predator) {
				return new Plant(critter1, critter2);
			}
			if (critter1 instanceof Prey && critter2 instanceof Prey) {
				return new Plant(critter1, critter2);
			}
		}
		return null;
	}

	
}
