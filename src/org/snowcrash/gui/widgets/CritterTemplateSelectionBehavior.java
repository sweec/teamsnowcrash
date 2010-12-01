package org.snowcrash.gui.widgets;

import java.awt.Color;


public class CritterTemplateSelectionBehavior implements SelectionStateBehavior<CritterTemplateWidget>
{
	private static final CritterTemplateSelectionBehavior instance = new CritterTemplateSelectionBehavior();
	
	public static CritterTemplateSelectionBehavior getInstance()
	{
		return instance;
	}
	
	private CritterTemplateSelectionBehavior()
	{
		// -- Singleton.
	}
	
	public void execute( CritterTemplateWidget object )
	{
		object.setBackground(Color.BLUE);
		object.setOpaque(true);
	}
}

