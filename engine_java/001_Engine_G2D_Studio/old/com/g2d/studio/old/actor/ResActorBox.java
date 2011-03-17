package com.g2d.studio.old.actor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.g2d.studio.old.ATreeNodeSet;

public class ResActorBox extends JPanel implements ItemListener
{
	FormActorViewerGroup	group;
	
	JScrollPane				actors_pan;
	
	public ResActorBox(FormActorViewerGroup group) 
	{
		this.group = group;
		this.setLayout(new BorderLayout());
		{
			JComboBox parents = new JComboBox();
			
			for (int i=0; i<group.getChildCount(); i++) {
				ATreeNodeSet<FormActorViewer> obj = group.getChildAt(i);
				parents.addItem(obj);
				if (i==0) {
					initActors(obj) ;
				}
			}
			parents.addItemListener(this);
			
			this.add(parents, BorderLayout.NORTH);
		}
		
		this.setMinimumSize(new Dimension(160, 160));
	}
	
	public void itemStateChanged(ItemEvent e) 
	{
		System.out.println(e.getItem());
		
		if (e.getItem() instanceof ATreeNodeSet) {
			initActors((ATreeNodeSet<FormActorViewer>)e.getItem()) ;
		}
	}
	
	
	protected void initActors(ATreeNodeSet<FormActorViewer> parent) 
	{
		if (actors_pan!=null) {
			remove(actors_pan);
			actors_pan = null;
		}
		
		int sw = 34; 
		int sh = 34;
		int count = parent.getChildCount();
		
		JToggleButton[] actors = new JToggleButton[count];
		
		JPanel pan = new JPanel(new GridLayout(count/2, 2));
		ButtonGroup bg = new ButtonGroup();
		
		for (int i=0; i<actors.length; i++) 
		{
			final FormActorViewer actor = parent.getChildAt(i).user_data;
			actors[i] = new JToggleButton(actor.getSnapshot(false));
			actors[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					group.studio.setSelectedNode(actor);
				}
			});
			bg.add(actors[i]);
			pan.add(actors[i]);
		}
		
		actors_pan = new JScrollPane(pan);
		actors_pan.setAutoscrolls(true);
		actors_pan.getVerticalScrollBar().setUnitIncrement(34);
		
		this.add(actors_pan, BorderLayout.CENTER);
		
		this.updateUI();
		
		this.setVisible(true);
	}
}
