package org.snowcrash.prototype;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtilities {

	public static ArrayList<ProtoCritter> getCritterFileContents(String filename) {
		ArrayList<ProtoCritter> contents = new ArrayList<ProtoCritter>();
		try { 
			BufferedReader in = new BufferedReader(new FileReader(filename)); 
			String str; 

			while ((str = in.readLine()) != null) { 
				String[] tokens = str.split(",");
				//TODO: Implement tokenization
			} 
			in.close(); 
		} catch (IOException e) { 
			e.printStackTrace();
		} 		
		return contents;
	}

	public static void main(String[] args) {
		ArrayList<ProtoCritter> contents = getCritterFileContents("critters.csv");
		contents.toString();
	}
}
