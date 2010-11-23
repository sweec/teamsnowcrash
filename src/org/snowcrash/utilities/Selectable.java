package org.snowcrash.utilities;


public interface Selectable
{
	public boolean isSelected();
	public void select();
	public void deselect();
	
	public void addSelectionListener( SelectionListener listener );
	public void removeSelectionListener( SelectionListener listener );
	public void removeSelectionListeners();
}
