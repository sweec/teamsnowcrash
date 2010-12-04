package org.snowcrash.gui.widgets;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;


public class CritterTemplateSelectionBehavior implements SelectionStateBehavior<CritterTemplateWidget>
{
	private static final CritterTemplateSelectionBehavior instance = new CritterTemplateSelectionBehavior();
	
	private static final Border selectedBorder = new BorderUIResource.LineBorderUIResource( Color.BLACK );
	
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
		object.setOpaque(true);
		object.setBorder( selectedBorder );
	}
}

