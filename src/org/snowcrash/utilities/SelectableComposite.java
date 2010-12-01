package org.snowcrash.utilities;


public interface SelectableComposite
{
	public boolean hasSelected();
	public void select( Selectable object );
	public void clearSelections();
}
