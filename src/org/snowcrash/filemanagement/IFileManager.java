package org.snowcrash.filemanagement;

import org.snowcrash.critter.Critter;

public interface IFileManager {
	public boolean saveCritters(Critter[] critter, String filepath,
			String filename);

	public Critter loadCritters(String filepath, String filename);
}
