package org.snowcrash.gui.widgets;


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
		object.setOpaque(false);
		object.setBorder(null);
	}
}
