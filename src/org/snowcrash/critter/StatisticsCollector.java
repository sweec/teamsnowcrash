package org.snowcrash.critter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import org.snowcrash.critter.data.CritterPrototype;
import org.snowcrash.critter.data.Size;
import org.snowcrash.critter.data.Trait;
import org.snowcrash.dataaccess.DAO;
import org.snowcrash.dataaccess.DAOException;
import org.snowcrash.dataaccess.DAOFactory;
import org.snowcrash.dataaccess.DatabaseObject;
import org.snowcrash.world.World;
import org.snowcrash.world.WorldObserver;

public class StatisticsCollector implements WorldObserver {
	private HashMap<String, CritterPrototype> types;
	private HashMap<String, Size> sizes;
	private HashMap<String, Integer> startPopulation, endPopulation, totalPopulation;
	private HashMap<String, Integer> minAge, maxAge, totalAge;
	private HashMap<String, HashMap<Trait, Float>> startTraits, endTraits;
	boolean isStatisticsAvailable;

	private static StatisticsCollector instance = null;
	
	public static StatisticsCollector getInstance() {
		if (instance == null) instance = new StatisticsCollector();
		return instance;
	}
	
	public StatisticsCollector() {
		types = new HashMap<String, CritterPrototype>();
		sizes = new HashMap<String, Size>();
		startPopulation = new HashMap<String, Integer>();
		endPopulation = new HashMap<String, Integer>();
		totalPopulation = new HashMap<String, Integer>();
		minAge = new HashMap<String, Integer>();
		maxAge = new HashMap<String, Integer>();
		totalAge = new HashMap<String, Integer>();
		startTraits = new HashMap<String, HashMap<Trait, Float>>();
		endTraits = new HashMap<String, HashMap<Trait, Float>>();
		isStatisticsAvailable = false;
		instance = this;
	}

	public Set<String> getNameSet() {
		return types.keySet();	// all HashMaps should have same keySet
	}
	
	public CritterPrototype getPrototype(String name) {
		if (types.containsKey(name))
			return types.get(name);
		return CritterPrototype.PLANT;	// if failed to read, return a dumb default type
	}
	
	public Size getSize(String name) {
		if (sizes.containsKey(name))
			return sizes.get(name);
		return Size.MEDIUM;	// if failed to read, return a dumb default size
	}
	
	public int getStartPopulation(String name) {
		if (startPopulation.containsKey(name))
			return startPopulation.get(name);
		return 0;
	}
	
	public int getEndPopulation(String name) {
		if (endPopulation.containsKey(name))
			return endPopulation.get(name);
		return 0;
	}
	
	public int getTotalPopulation(String name) {
		if (totalPopulation.containsKey(name))
			return totalPopulation.get(name);
		return 0;
	}
	
	public int getMinAge(String name) {
		if (minAge.containsKey(name))
			return minAge.get(name);
		return 0;
	}
	
	public int getMaxAge(String name) {
		if (maxAge.containsKey(name))
			return maxAge.get(name);
		return 0;
	}
	
	public int getAverageAge(String name) {
		if (totalAge.containsKey(name) && totalPopulation.containsKey(name)
				&& (totalPopulation.get(name) != 0))
			return totalAge.get(name) / totalPopulation.get(name);
		return 0;
	}
	
	public float getStartTrait(String name, Trait trait) {
		if (startTraits.containsKey(name)) {
			HashMap<Trait, Float> traits = startTraits.get(name);
			if (traits.containsKey(trait))
				return traits.get(trait);
		}
		return 0;
	}
	
	public float getEndTrait(String name, Trait trait) {
		if (endTraits.containsKey(name)) {
			HashMap<Trait, Float> traits = endTraits.get(name);
			if (traits.containsKey(trait))
				return traits.get(trait);
		}
		return 0;
	}
	
	private void incrStartPopulation(String name, int p) {
		if (startPopulation.containsKey(name))
			startPopulation.put(name, startPopulation.get(name)+p);
		else
			startPopulation.put(name, p);
	}
	
	private void incrEndPopulation(String name, int p) {
		if (endPopulation.containsKey(name))
			endPopulation.put(name, endPopulation.get(name)+p);
		else
			endPopulation.put(name, p);
	}
	
	private void incrTotalPopulation(String name, int p) {
		if (totalPopulation.containsKey(name))
			totalPopulation.put(name, totalPopulation.get(name)+p);
		else
			totalPopulation.put(name, p);
	}
	
	private void updateAges(String name, int age) {
		if (totalAge.containsKey(name))
			totalAge.put(name, totalAge.get(name)+age);
		else
			totalAge.put(name, age);
		if (minAge.containsKey(name)) {
			if (age < minAge.get(name))
				minAge.put(name, age);
		} else
			minAge.put(name, age);
		if (maxAge.containsKey(name)) {
			if (age > maxAge.get(name))
				maxAge.put(name, age);
		} else
			maxAge.put(name, age);
	}
	
	private void incrStartTrait(String name, Trait trait, int value) {
		HashMap<Trait, Float> traits = null;
		if (startTraits.containsKey(name))
			traits = startTraits.get(name);
		else
			traits = new HashMap<Trait, Float>();
		if (traits.containsKey(trait))
			traits.put(trait, traits.get(trait) + value);
		else
			traits.put(trait, (float)value);
		startTraits.put(name, traits);
	}

	private void incrEndTrait(String name, Trait trait, int value) {
		HashMap<Trait, Float> traits = null;
		if (endTraits.containsKey(name))
			traits = endTraits.get(name);
		else
			traits = new HashMap<Trait, Float>();
		if (traits.containsKey(trait))
			traits.put(trait, traits.get(trait) + value);
		else
			traits.put(trait, (float)value);
		endTraits.put(name, traits);
	}

	private void setAverageStatistics(String name) {
		if (startTraits.containsKey(name)) {
			HashMap<Trait, Float> traits = startTraits.get(name);
			if (startPopulation.containsKey(name) && (startPopulation.get(name)!= 0)) {
				int sP = startPopulation.get(name);
				traits.put(Trait.CAMO, traits.get(Trait.CAMO) / sP);
				traits.put(Trait.COMBAT, traits.get(Trait.COMBAT) / sP);
				traits.put(Trait.ENDURANCE, traits.get(Trait.ENDURANCE) / sP);
				traits.put(Trait.SPEED, traits.get(Trait.SPEED) / sP);
				traits.put(Trait.VISION, traits.get(Trait.VISION) / sP);
			}
		}
		if (endTraits.containsKey(name)) {
			HashMap<Trait, Float> traits = endTraits.get(name);
			if (endPopulation.containsKey(name) && (endPopulation.get(name)!= 0)) {
				int eP = endPopulation.get(name);
				traits.put(Trait.CAMO, traits.get(Trait.CAMO) / eP);
				traits.put(Trait.COMBAT, traits.get(Trait.COMBAT) / eP);
				traits.put(Trait.ENDURANCE, traits.get(Trait.ENDURANCE) / eP);
				traits.put(Trait.SPEED, traits.get(Trait.SPEED) / eP);
				traits.put(Trait.VISION, traits.get(Trait.VISION) / eP);
			}
		}
	}
	
	public void calculateStatistics() {
		Critter[][] map = World.getInstance().getInitMap();
		if (map == null) return;
		
		for (int i = 0;i < map.length;i++)
		for (int j = 0;j < map[0].length;j++) {
			Critter critter = map[i][j];
			if (critter == null) continue;
			String name = critter.getName();
			incrStartPopulation(name, 1);
			incrStartTrait(name, Trait.CAMO, critter.getTrait(Trait.CAMO));
			incrStartTrait(name, Trait.COMBAT, critter.getTrait(Trait.COMBAT));
			incrStartTrait(name, Trait.ENDURANCE, critter.getTrait(Trait.ENDURANCE));
			incrStartTrait(name, Trait.SPEED, critter.getTrait(Trait.SPEED));
			incrStartTrait(name, Trait.VISION, critter.getTrait(Trait.VISION));
		}
		map = World.getInstance().getMap();
		if (map != null) {
			for (int i = 0;i < map.length;i++)
			for (int j = 0;j < map[0].length;j++) {
				Critter critter = map[i][j];
				if (critter == null) continue;
				if (!critter.isAlive()) continue;
				String name = critter.getName();
				incrEndPopulation(name, 1);
				incrTotalPopulation(name, 1);
				updateAges(name, critter.getAge());
				incrEndTrait(name, Trait.CAMO, critter.getTrait(Trait.CAMO));
				incrEndTrait(name, Trait.COMBAT, critter.getTrait(Trait.COMBAT));
				incrEndTrait(name, Trait.ENDURANCE, critter.getTrait(Trait.ENDURANCE));
				incrEndTrait(name, Trait.SPEED, critter.getTrait(Trait.SPEED));
				incrEndTrait(name, Trait.VISION, critter.getTrait(Trait.VISION));
			}
		}
		DAO dao = DAOFactory.getDAO();
		DatabaseObject[] objects = null;
		try {
			objects = dao.read(CritterTemplate.class);
		} catch (DAOException e) {
			throw new RuntimeException( e );
		}
		if (objects != null) {
			for (int i = 0;i < objects.length;i++) {
				CritterTemplate template = (CritterTemplate)objects[i];
				types.put(template.getName(), template.getPrototype());
				sizes.put(template.getName(), template.getSize());
				setAverageStatistics(template.getName());
			}
		}
		isStatisticsAvailable = true;
	}
	
	@Override
	public void updateStatistics(ArrayList<Critter> critters) {
		if (critters == null) return;
		for (int i = 0;i < critters.size();i++) {
			Critter critter = critters.get(i);
			String name = critter.getName();
			incrTotalPopulation(name, 1);
			updateAges(name, critter.getAge());
		}
	}

	@Override
	public void updateWorld(World world) {
		// Nothing need do here
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// Nothing need do here
	}

	public void notifyTheEnd() {
		// Nothing need do here
		// ResultsPanel will call the calculation method instead
	}

}
