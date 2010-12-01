package org.snowcrash.commands;

public class ResetCommand implements Command {

	@Override
	public void execute() {
		CommandMediator.reset();
	}

}
