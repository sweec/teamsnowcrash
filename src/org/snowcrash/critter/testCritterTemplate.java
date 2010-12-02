package org.snowcrash.critter;

import java.util.HashMap;
import java.util.UUID;

import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.filemanagement.FileManager;
import org.snowcrash.world.World;

public class testCritterTemplate extends CritterTemplate {
	private String uuid;	// test use, since uuid in super class is not writable

	private int startPopulation = 0, endPopulation = 0, totalPopulation = 0;
	private int minAge = 0, maxAge = 0, totalAge = 0;
	// average values of traits
	private HashMap<Trait, Float> startTraits, endTraits;

	public testCritterTemplate() {
		startTraits = new HashMap<Trait, Float>();
		endTraits = new HashMap<Trait, Float>();
		this.uuid = UUID.randomUUID().toString();
	}

	public testCritterTemplate(CritterPrototype prototype, String name) {
		super(prototype, name);
		startTraits = new HashMap<Trait, Float>();
		endTraits = new HashMap<Trait, Float>();
		this.uuid = UUID.randomUUID().toString();
	}
	
	// make a copy, but with different uuid
	public testCritterTemplate(testCritterTemplate source) {
		super(source.getPrototype(), source.getName());
		super.setSize(source.getSize());
		super.setTraitRange(Trait.CAMO, source.getTraitRange(Trait.CAMO));
		super.setTraitRange(Trait.COMBAT, source.getTraitRange(Trait.COMBAT));
		super.setTraitRange(Trait.ENDURANCE, source.getTraitRange(Trait.ENDURANCE));
		super.setTraitRange(Trait.SPEED, source.getTraitRange(Trait.SPEED));
		super.setTraitRange(Trait.VISION, source.getTraitRange(Trait.VISION));
		
		startTraits = new HashMap<Trait, Float>();
		endTraits = new HashMap<Trait, Float>();
		this.uuid = UUID.randomUUID().toString();
	}
	
	@Override
	public Object getId()
	{
		return uuid;
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

	public void clearStatistics() {
		startPopulation = 0;
		endPopulation = 0;
		totalPopulation = 0;
		minAge = 0;
		maxAge = 0;
		totalAge = 0;
		if (startTraits == null) {
			startTraits = new HashMap<Trait, Float>();
		}
		if (endTraits == null) {
			endTraits = new HashMap<Trait, Float>();
		}
		startTraits.put(Trait.CAMO, new Float(0));
		startTraits.put(Trait.COMBAT, new Float(0));
		startTraits.put(Trait.ENDURANCE,new Float(0));
		startTraits.put(Trait.SPEED,new Float(0));
		startTraits.put(Trait.VISION, new Float(0));
	}
	
	public float getStartTrait(Trait trait) {
		if (startTraits == null) {
			startTraits = new HashMap<Trait, Float>();
			return 0;
		}
		return startTraits.get(trait).floatValue();
	}
	
	public float getEndTrait(Trait trait) {
		if (endTraits == null) {
			endTraits = new HashMap<Trait, Float>();
			return 0;
		}
		return endTraits.get(trait).intValue();
	}
	
	public void setStartTrait(Trait trait, float value) {
		if (startTraits == null) {
			startTraits = new HashMap<Trait, Float>();
		}
		startTraits.put(trait, new Float(value));
	}

	public void setEndTrait(Trait trait, float value) {
		if (endTraits == null) {
			endTraits = new HashMap<Trait, Float>();
		}
		endTraits.put(trait, new Float(value));
	}
	
	public void incrStartTrait(Trait trait, int value) {
		if ((startTraits == null) || !(startTraits.containsKey(trait)))
			setStartTrait(trait, value);
		else
			startTraits.put(trait, new Float(startTraits.get(trait).floatValue() + value));
	}
	
	public void incrEndTrait(Trait trait, int value) {
		if ((endTraits == null) || !(endTraits.containsKey(trait)))
			setEndTrait(trait, value);
		else
			endTraits.put(trait, new Float(endTraits.get(trait).floatValue() + value));
	}

	public boolean isStartTraitsEmpty() {
		if (startTraits == null)
			startTraits = new HashMap<Trait, Float>();
		
		return startTraits.isEmpty();
	}
	
	public boolean isEndTraitsEmpty() {
		if (endTraits == null)
			endTraits = new HashMap<Trait, Float>();
		
		return endTraits.isEmpty();
	}
	
	public String toString() {
		return 		"\nName: "+super.getName()+";Size: "+super.getSize()
		+"\nStartPopulation: "+startPopulation+";EndPopulation: "+endPopulation+";totalPopulation: "
		+endPopulation+";minAge: "+minAge+";maxAge: "+maxAge
		+"\nCAMO: ("+startTraits.get(Trait.CAMO)+","+endTraits.get(Trait.CAMO)+")"
		+"\nCOMBAT: ("+startTraits.get(Trait.COMBAT)+","+endTraits.get(Trait.COMBAT)+")"
		+"\nENDURANCE: ("+startTraits.get(Trait.ENDURANCE)+","+endTraits.get(Trait.ENDURANCE)+")"
		+"\nSPEED: ("+startTraits.get(Trait.SPEED)+","+endTraits.get(Trait.SPEED)+")"
		+"\nVISION: ("+startTraits.get(Trait.VISION)+","+endTraits.get(Trait.VISION)+")"
		;
	}
	
	// call this when simulation start
	public static void initializeStatistics() {
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
			template.clearStatistics();
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
			object = dao.read(World.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object == null) return;
		//FileManager mgr = new FileManager();
		//World world = mgr.loadWorld("testWorld.Json");
		World world = (World)object[0];
		Critter[][] map = world.getMap();
		if (map == null) return;
		for (i = 0;i < map.length;i++)
		for (int j = 0;j < map[0].length;j++) {
			Critter critter = (Critter) (map[i][j]);
			if (critter == null) continue;
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
			try {
				template = (testCritterTemplate) dao.read(testCritterTemplate.class, critter.getTemplateUuid());
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// now pull out the updated templates 
		try {
			// get templates
			templatesObject = dao.read(testCritterTemplate.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (templatesObject == null) return;
		System.out.println("Start: ");
		for (i = 0;i < templatesObject.length;i++) {
			testCritterTemplate template = (testCritterTemplate) (templatesObject[i]);
			
			int population = template.getStartPopulation();
			if (population == 0) continue;	// this template is not used in the simulation
			template.setStartTrait(Trait.CAMO, template.getStartTrait(Trait.CAMO)/population);
			template.setStartTrait(Trait.COMBAT, template.getStartTrait(Trait.COMBAT)/population);
			template.setStartTrait(Trait.ENDURANCE,template.getStartTrait(Trait.ENDURANCE)/population);
			template.setStartTrait(Trait.SPEED,template.getStartTrait(Trait.SPEED)/population);
			template.setStartTrait(Trait.VISION, template.getStartTrait(Trait.VISION)/population);
			
			System.out.println(template);
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// call this when simulation end
	public static void calculateStatistics() {
		DAO dao = DAOFactory.getDAO();
		DatabaseObject[] object = null;
		try {
			// get Critters
			object = dao.read(World.class);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (object == null) return;
		//FileManager mgr = new FileManager();
		//World world = mgr.loadWorld("testWorld.Json");
		World world = (World)object[0];
		Critter[][] map = world.getMap();
		if (map == null) return;
		int i;
		for (i = 0;i < map.length;i++)
		for (int j = 0;j < map[0].length;j++) {
			Critter critter = (Critter) (map[i][j]);
			if (critter == null) continue;
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
		System.out.println("\nEnd: ");
		for (i = 0;i < templatesObject.length;i++) {
			testCritterTemplate template = (testCritterTemplate) (templatesObject[i]);
			
			int population = template.getEndPopulation();
			if (population == 0) continue;	// critters from this template die out before simulation finished
			template.setEndTrait(Trait.CAMO, template.getEndTrait(Trait.CAMO)/population);
			template.setEndTrait(Trait.COMBAT, template.getEndTrait(Trait.COMBAT)/population);
			template.setEndTrait(Trait.ENDURANCE,template.getEndTrait(Trait.ENDURANCE)/population);
			template.setEndTrait(Trait.SPEED,template.getEndTrait(Trait.SPEED)/population);
			template.setEndTrait(Trait.VISION, template.getEndTrait(Trait.VISION)/population);
			
			System.out.println(template);
			try {
				dao.update(template);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
