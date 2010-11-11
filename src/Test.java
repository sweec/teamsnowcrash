import org.snowcrash.commands.Command;
import org.snowcrash.commands.CommandFactory;
import org.snowcrash.timeengine.TimeEngine;
import org.snowcrash.timeengine.TimeListener;

public class Test {
	public static void main(String... args) {
		/*
		 * Insert your own test code here. If you want to check-in changes,
		 * please split your added code into a different method.
		 */

		testCommands();

		testTimer();
	}

	private static void testCommands() {
		Command command2 = CommandFactory.getTestCommand();
		command2.execute();
	}

	private static void testTimer() {
		TimeEngine.addTimeListener(new TimeListener() {
			private int counter = 0;

			@Override
			public void tickOccurred() {
				counter++;
				System.out.println("Tick occurred!");
				System.out.println("Tick #" + counter);
			}

			@Override
			public void timeExpired() {
				System.out.println("Time expired!");
				System.out.println("Number of ticks: " + counter);
			}

			@Override
			public void timerStopped() {
				// TODO Auto-generated method stub

			}
		});

		TimeEngine.setTimeLimit(10);

		TimeEngine.startTimer();
	}
}
