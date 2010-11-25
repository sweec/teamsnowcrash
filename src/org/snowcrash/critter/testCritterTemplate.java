package org.snowcrash.critter;

import java.util.HashMap;
import java.util.UUID;

import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.utilities.Pair;

public class testCritterTemplate extends CritterTemplate {
	private String uuid;

	private int startPopulation = 0, endPopulation = 0, totalPopulation = 0;
	private int minAge = 0, maxAge = 0, totalAge = 0;
	// average values of traits
	private HashMap<Trait, Integer> startTraits = new HashMap<Trait, Integer>(), endTraits = new HashMap<Trait, Integer>();

	public testCritterTemplate() {
		this.uuid = UUID.randomUUID().toString();
	}
	
	public Object getId()
	{
		return uuid;
	}
	
	public testCritterTemplate(CritterPrototype prototype, String name) {
		super(prototype, name);
		this.uuid = UUID.randomUUID().toString();
	}
	
	public int getStartPopulation() {
		return startPopulation;
	}
	
	public int getEndPopulation() {
		return endPopulation;
	}
	
	public int getTotalPopulation() {
		return totalPopulation;
	}

	public int getMinAge() {
		return minAge;
	}
	
	public int getMaxAge() {
		return maxAge;
	}
	
	public int getAverageAge() {
		if (totalPopulation == 0) return 0;
		return (totalAge / totalPopulation);
	}
	
	public void setStartPopulation(int p) {
		startPopulation = p;
	}

	public void setEndPopulation(int p) {
		endPopulation = p;
	}

	public void setTotalPopulation(int p) {
		totalPopulation = p;
	}
	
	public void setMinAge(int a) {
		if (a < minAge) minAge = a;
	}

	public void setMaxAge(int a) {
		if (a > maxAge) maxAge = a;
	}

	public void setTotalAge(int a) {
		totalAge = a;
	}
	
	public void incrStartPopulation(int p) {
		startPopulation += p;
	}

	public void incrEndPopulation(int p) {
		endPopulation += p;
	}

	public void incrTotalPopulation(int p) {
		totalPopulation += p;
	}
	
	public void incrTotalAge(int a) {
		totalAge += a;
	}

	public void clearTraits() {
		startTraits.clear();
		endTraits.clear();
	}
	
	public int getStartTrait(Trait trait) {
		return startTraits.get(trait).intValue();
	}
	
	public int getEndTrait(Trait trait) {
		return endTraits.get(trait).intValue();
	}
	
	public void setStartTrait(Trait trait, int value) {
		startTraits.put(trait, new Integer(value));
	}

	public void setEndTrait(Trait trait, int value) {
		endTraits.put(trait, new Integer(value));
	}
	
	public void incrStartTrait(Trait trait, int value) {
		startTraits.put(trait, startTraits.get(trait).intValue() + value);
	}
	
	public void incrEndTrait(Trait trait, int value) {
		endTraits.put(trait, endTraits.get(trait).intValue() + value);
	}

	public boolean isStartTraitsEmpty() {
		return startTraits.isEmpty();
	}
	
	public boolean isEndTraitsEmpty() {
		return endTraits.isEmpty();
	}
	
	// call this when simulation start
	static void initializeStatistics() {
		DAO dao = DAOFactory.getDAO();
		DatabaseObject[] templatesObject = null;
		try {
			// get templates
			templatesObject = dao.read(testCritterTemplate.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (templatesObject == null) return;
		int i;
		for (i = 0;i < templatesObject.length;i++) {
			testCritterTemplate template = (testCritterTemplate) (templatesObject[i]);
			template.setStartPopulation(0);
			template.setEndPopulation(0);
			template.setTotalPopulation(0);
			template.setMinAge(0);
			template.setMaxAge(0);
			template.setTotalAge(0);
			template.clearTraits();
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// now set start data from Critters
		DatabaseObject[] object = null;
		try {
			// get Critters
			object = dao.read(Critter.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object == null) return;
		for (i = 0;i < object.length;i++) {
			Critter critter = (Critter) (object[i]);
			testCritterTemplate template = null;
			try {
				template = (testCritterTemplate) dao.read(testCritterTemplate.class, critter.getTemplateUuid());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (template == null) continue;
			template.incrStartPopulation(1);
			template.incrStartTrait(Trait.CAMO, critter.getTrait(Trait.CAMO));
			template.incrStartTrait(Trait.COMBAT, critter.getTrait(Trait.COMBAT));
			template.incrStartTrait(Trait.ENDURANCE, critter.getTrait(Trait.ENDURANCE));
			template.incrStartTrait(Trait.SPEED, critter.getTrait(Trait.SPEED));
			template.incrStartTrait(Trait.VISION, critter.getTrait(Trait.VISION));
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (i = 0;i < templatesObject.length;i++) {
			testCritterTemplate template = (testCritterTemplate) (templatesObject[i]);
			template.setStartTrait(Trait.CAMO, template.getStartTrait(Trait.CAMO)/template.getStartPopulation());
			template.setStartTrait(Trait.COMBAT, template.getStartTrait(Trait.COMBAT)/template.getStartPopulation());
			template.setStartTrait(Trait.ENDURANCE,template.getStartTrait(Trait.ENDURANCE)/template.getStartPopulation());
			template.setStartTrait(Trait.SPEED,template.getStartTrait(Trait.SPEED)/template.getStartPopulation());
			template.setStartTrait(Trait.VISION, template.getStartTrait(Trait.VISION)/template.getStartPopulation());
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// call this when simulation end
	static void calculateStatistics() {
		DAO dao = DAOFactory.getDAO();
		DatabaseObject[] object = null;
		try {
			// get latest version of Critters
			object = dao.read(Critter.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object == null) return;
		int i;
		for (i = 0;i < object.length;i++) {
			Critter critter = (Critter) (object[i]);
			testCritterTemplate template = null;
			try {
				template = (testCritterTemplate) dao.read(testCritterTemplate.class, critter.getTemplateUuid());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (template == null) continue;
			// startPopulation are set in initialStatistics()
			template.incrEndPopulation(1);
			template.incrEndTrait(Trait.CAMO, critter.getTrait(Trait.CAMO));
			template.incrEndTrait(Trait.COMBAT, critter.getTrait(Trait.COMBAT));
			template.incrEndTrait(Trait.ENDURANCE, critter.getTrait(Trait.ENDURANCE));
			template.incrEndTrait(Trait.SPEED, critter.getTrait(Trait.SPEED));
			template.incrEndTrait(Trait.VISION, critter.getTrait(Trait.VISION));
			// assume below data are updated when Critter die
			// now add in data from final population
			template.incrTotalPopulation(1);
			int age = critter.getAge();
			template.incrTotalAge(age);
			//template.setMinAge(age); // current critters still alive, no need call this
			template.setMaxAge(age);
			
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// calculate average traits
		DatabaseObject[] templatesObject = null;
		try {
			// get templates
			templatesObject = dao.read(testCritterTemplate.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (templatesObject == null) return;
		for (i = 0;i < templatesObject.length;i++) {
			testCritterTemplate template = (testCritterTemplate) (templatesObject[i]);
			template.setEndTrait(Trait.CAMO, template.getEndTrait(Trait.CAMO)/template.getEndPopulation());
			template.setEndTrait(Trait.COMBAT, template.getEndTrait(Trait.COMBAT)/template.getEndPopulation());
			template.setEndTrait(Trait.ENDURANCE,template.getEndTrait(Trait.ENDURANCE)/template.getEndPopulation());
			template.setEndTrait(Trait.SPEED,template.getEndTrait(Trait.SPEED)/template.getEndPopulation());
			template.setEndTrait(Trait.VISION, template.getEndTrait(Trait.VISION)/template.getEndPopulation());
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
