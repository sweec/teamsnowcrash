package org.snowcrash.gui.widgets;

import java.awt.Color;


public class CritterTemplateDeselectionBehavior implements SelectionStateBehavior<CritterTemplateWidget>
{
	private static final CritterTemplateDeselectionBehavior instance = new CritterTemplateDeselectionBehavior();
	
	public static CritterTemplateDeselectionBehavior getInstance()
	{
		return instance;
	}
	
	private CritterTemplateDeselectionBehavior()
	{
		// -- Singleton.
	}
	
	public void execute( CritterTemplateWidget object )
	{
		object.setBackground(Color.LIGHT_GRAY);
		object.setOpaque(false);
	}
}
