package org.snowcrash.timeengine;

public interface TimeListener {
	public void tickOccurred();

	public void timeExpired();

	public void timerStopped();
}
