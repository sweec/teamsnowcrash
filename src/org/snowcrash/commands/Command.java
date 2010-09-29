package org.snowcrash.commands;

/**
 * 
 * The Command enum provides a pre-defined number of ways for the GUI (or other
 * parts of the application) to interact with the services. Basically, each
 * command is a portal to a use case implementation.
 * 
 * @author Mike
 * 
 */
public enum Command {
	// -- Configuration commands.
	IMPORT_CRITTER, EXPORT_CRITTER, CREATE_CRITTER, MODIFY_CRITTER, DELETE_CRITTER,

	// -- Simulation commands.
	PLAY_SIM, PAUSE_SIM, RESUME_SIM, STOP_SIM, LOAD_SIM, SAVE_SIM,

	// -- Results commands.
	VIEW_RESULTS, LOAD_RESULTS, SAVE_RESULTS,

	// -- Other commands.
	RESET, EXIT, TEST;

	private Command() {
		// -- Enum.
	}

	/**
	 * 
	 * This method launches the Command so it can be handled appropriately.
	 * 
	 */
	public void launch() {
		CommandMediator.handleCommand(this);
	}
}
