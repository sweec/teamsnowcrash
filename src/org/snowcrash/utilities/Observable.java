package org.snowcrash.utilities;

import java.util.Observer;


public interface Observable
{
	public void addObserver( Observer o );
	
	public int countObservers();
	
	public void deleteObserver( Observer o );
	
	public void deleteObservers();
	
	public boolean hasChanged();
	
	public void notifyObservers();
	
	public void notifyObservers( Object arg );
}
