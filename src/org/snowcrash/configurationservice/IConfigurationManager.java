package org.snowcrash.configurationservice;

import org.snowcrash.critter.Critter;
import org.snowcrash.critter.CritterTemplate;
import org.snowcrash.critter.data.CritterPrototype;

public interface IConfigurationManager {
	public CritterTemplate createCritterTemplate(CritterPrototype prototype, String name);

	public void updateCritterTemplate(CritterTemplate template);

	public void deleteCritterTemplate(CritterTemplate template);

	public Critter createCritterFromTemplate();
}
