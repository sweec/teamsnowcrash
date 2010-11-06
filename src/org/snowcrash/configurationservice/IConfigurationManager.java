package org.snowcrash.configurationservice;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterPrototype;
import org.snowcrash.critter.CritterTemplate;

public interface IConfigurationManager {
	public CritterTemplate createCritterTemplate(CritterPrototype prototype, String name);

	public void updateCritterTemplate(CritterTemplate template);

	public void deleteCritterTemplate(CritterTemplate template);

	public Critter createCritterFromTemplate();
}
