package org.snowcrash.timeengine;

import java.util.HashSet;
import java.util.Set;

public final class TimeEngine {
	private static final int DEFAULT_INTERVAL = 1000;

	private static boolean paused = false;
	private static boolean stopped = true;

	private static int interval = DEFAULT_INTERVAL;
	private static int timeLimit = 0;
	private static int currTime = 0;

	private static Set<TimeListener> listeners = new HashSet<TimeListener>();

	private static Thread timerThread = null;

	private TimeEngine() {
		// -- Static class.
	}

	public static void setTickInterval(int interval) {
		TimeEngine.interval = interval;
	}

	public static void setTimeLimit(int turnsLimit) {
		timeLimit = turnsLimit;
	}

	public static void startTimer() {
		Timer timer = new Timer();

		stopped = false;

		timer.run();
	}

	public static void stopTimer() {
		stopped = true;
		paused = false;
	}

	public static void pauseTimer() {
		paused = true;
	}

	public static void resumeTimer() {
		paused = false;
		timerThread.notify();
	}

	public static void addTimeListener(TimeListener timeListener) {
		listeners.add(timeListener);
	}

	public static void removeTimeListener(TimeListener timeListener) {
		listeners.remove(timeListener);
	}

	public static void removeAllTimeListeners() {
		listeners.clear();
	}

	private static boolean isTimeLimitExceeded() {
		return (timeLimit != 0) && currTime > timeLimit;
	}

	private static class Timer implements Runnable {
		public void run() {
			timerThread = Thread.currentThread();

			while (!stopped && !isTimeLimitExceeded()) {
				// -- Sleep for the preset interval.
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {

				}

				// -- Increment the current time counter.
				currTime++;

				// -- Do not continue until everything is done.
				boolean cont = false;

				// -- Notify all listeners.
				for (TimeListener listener : listeners) {
					listener.tickOccurred();
				}

				// -- Continue?
				while (!cont || paused) {
					try {
						timerThread.wait();
					} catch (InterruptedException e) {

					}
				}
			}
		}
	}
}
