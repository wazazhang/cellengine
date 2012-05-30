package com.g2d.studio.ui.edit;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.g2d.editor.property.ObjectPropertyPanel;

public class UIPropertyPanel extends JPanel
{
	public UIPropertyPanel(int mw, int mh) {
		super(new CardLayout());
		this.setMaximumSize(new Dimension(mw, mh));
	}
	
	public void setCompoment(UITreeNode node) {
		this.removeAll();
		if (node != null) {
			node.opp.setVisible(false);
			this.add(node.opp, BorderLayout.CENTER);
			node.opp.setVisible(true);
		}
		this.repaint(10);
	}
	
	
}
