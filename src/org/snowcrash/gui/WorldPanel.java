package org.snowcrash.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class WorldPanel extends JPanel
{
	public JPanel worldObjects()
	{
		JPanel cPanelInner = new JPanel();
		JPanel cPanelOuter = new JPanel();
		cPanelOuter.add(cPanelInner);
		
		return cPanelOuter;
	}
}