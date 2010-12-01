package org.snowcrash.commands;

public class OpenLogCommand implements Command {

	@Override
	public void execute() {
		CommandMediator.openLog();
	}

}
