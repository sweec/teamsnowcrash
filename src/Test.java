import org.snowcrash.commands.Command;

public class Test {
	public static void main(String... args) {
		/*
		 * Insert your own test code here. If you want to check-in changes,
		 * please split your added code into a different method.
		 */

		testCommands();
	}

	private static void testCommands() {
		Command command = Command.TEST;
		command.launch();
	}
}
