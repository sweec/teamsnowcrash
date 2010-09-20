package org.snowcrash.prototype;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtilities {

	public static ArrayList<ProtoCritter> getCritterFileContents(String filename) {
		ArrayList<ProtoCritter> contents = new ArrayList<ProtoCritter>();
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filename)); 
			String str; 
			ProtoCritter critter;
			
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
			in.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 		
		return contents;
	}

	public static void saveCritterToFile(ArrayList<ProtoCritter> contents, String filename) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(filename)); 
			ProtoCritter critter;
			int i;
			for (i = 0;i < contents.size();i++) {
				critter = contents.get(i);
				out.write(critter.getName()+","+critter.getType()+","+critter.getVision()+",");
				out.write(critter.getAttack()+","+critter.getMovement()+","+critter.getHealth());
				out.newLine();
			} 
			out.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 		
	}
	public static void main(String[] args) {
		ArrayList<ProtoCritter> contents = getCritterFileContents("critters.csv");
		System.out.println(contents.toString());
		//saveCritterToFile(contents, "testOut.csv");
	}
}
