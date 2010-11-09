package org.snowcrash.prototype;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.JsonStreamParser;
import com.google.gson.JsonElement;
import com.google.gson.Gson;

public class FileUtilities {
	static private boolean useCSV = false;
	static private boolean useJson = true;

	public static ArrayList<ProtoCritter> getCritterFileContents(String filename) {
		ArrayList<ProtoCritter> contents = new ArrayList<ProtoCritter>();
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filename)); 
			ProtoCritter critter;
			
			if (useCSV) {
				String str; 
				while ((str = in.readLine()) != null) { 
					String[] tokens = str.split(",");
					if (tokens.length < 6) continue;
					critter = new ProtoCritter();
					critter.setName(tokens[0]);
					critter.setType(tokens[1]);
					critter.setVision(Integer.parseInt(tokens[2]));
					critter.setAttack(Integer.parseInt(tokens[3]));
					critter.setMovement(Integer.parseInt(tokens[4]));
					critter.setHealth(Integer.parseInt(tokens[5]));
					contents.add(critter);
				}
			}
			if (useJson) {
				JsonStreamParser parser = new JsonStreamParser(in);
				JsonElement element;
				Gson gson = new Gson();
				while (parser.hasNext()) {
					element = parser.next();
					critter = gson.fromJson(element, ProtoCritter.class);
					contents.add(critter);
				}
			}
			in.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 		
		return contents;
	}

	/**
	 * 
	 * @param contents
	 * @param filename
	 */
	public static void saveCritterToFile(ArrayList<ProtoCritter> contents, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filename)); 
			ProtoCritter critter;
			Gson gson = new Gson();
			
			int i;
			for (i = 0;i < contents.size();i++) {
				critter = contents.get(i);
				if (useCSV) {
					out.write(critter.getName()+","+critter.getType()+","+critter.getVision()+",");
					out.write(critter.getAttack()+","+critter.getMovement()+","+critter.getHealth());
					out.newLine();
				}
				if (useJson) {
					gson.toJson(critter, out);
				}
			}
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 		
	}
	public static void main(String[] args) {
		useCSV = true;
		useJson = false;
		ArrayList<ProtoCritter> contents = getCritterFileContents("critters.csv");
		System.out.println(contents);
		useCSV = false;
		useJson = true;
		saveCritterToFile(contents, "critters.JSon");
		contents = getCritterFileContents("critters.JSon");
		System.out.println(contents);
	}
}
