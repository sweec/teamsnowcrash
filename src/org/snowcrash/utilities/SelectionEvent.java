package org.snowcrash.utilities;

import java.awt.AWTEvent;


@SuppressWarnings("serial")
public class SelectionEvent extends AWTEvent
{
	private Object proxy = null;
	
	public SelectionEvent(Object source)
	{
		super(source, 0);
	}
	
	public void setProxy( Object proxy )
	{
		this.proxy = proxy;
	}
	
	public Object getProxy()
	{
		return proxy;
	}
}
