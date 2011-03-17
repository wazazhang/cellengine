package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.g2d.awt.util.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;

public class ObjectSelectList<T extends ObjectNode<?>> extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	final public Class<T> type;
	
	private HashMap<JToggleButton, T> unit_map = new HashMap<JToggleButton, T>();

	private JPanel 		panel = new JPanel(null);
	
	private T 			selected_unit;
	
	private JLabel		selected_label	= new JLabel();
	
	public ObjectSelectList(Class<T> type, int wcount) 
	{
		super(new BorderLayout());
		this.type = type;
		
		this.add(selected_label, BorderLayout.NORTH);
		{
			ButtonGroup button_group = new ButtonGroup();
	
			Vector<T> tunits = Studio.getInstance().getObjectManager().getObjects(type);
	
			int w = 0;
			int h = 0;
			int wc = wcount;

			for (T tunit : tunits) {
				if (w == 0 || h == 0) {
					ImageIcon icon = tunit.getIcon(true);
					if (icon != null) {
						w = icon.getIconWidth() + 4;
						h = icon.getIconHeight() + 4;
						break;
					}
				}
			}
			int mw = 0;
			int mh = 0;
			int i=0;
			for (T tunit : tunits)
			{
				JToggleButton btn = new JToggleButton();
				btn.setToolTipText(tunit.getName());
				btn.setLocation(i%wc * w, i/wc * h);
				btn.setSize(w, h);
				btn.setMinimumSize(new Dimension(w, h));
				btn.setIcon(Tools.createIcon(Tools.combianImage(w-4, h-4, tunit.getIcon(true).getImage())));
				btn.addActionListener(this);
				unit_map.put(btn, tunit);
				button_group.add(btn);
				panel.add(btn);
				i++;
				mw = Math.max(btn.getLocation().x + btn.getWidth(), mw);
				mh = Math.max(btn.getLocation().y + btn.getHeight(), mh);
			}
//			System.out.println();
			Dimension size = new Dimension(mw, mh);
			panel.setAutoscrolls(true);
			panel.setMinimumSize(size);
			panel.setPreferredSize(size);
			panel.setSize(size);
		}
		this.add(new JScrollPane(panel), BorderLayout.CENTER);
		this.setAutoscrolls(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		selected_unit = unit_map.get(e.getSource());
		if (selected_unit != null) {
			selected_label.setText("物品 : " + selected_unit.getName());
		}
	}	

	public T getSelectedUnit() {
		return selected_unit;
	}
	
	public void setSelectedUnit(T object)
	{
		for (JToggleButton bt : unit_map.keySet()) {
			T e = unit_map.get(bt);
			if (object.equals(e)) {
				bt.setSelected(true);
				selected_unit = e;
				break;
			}
		}
		if (selected_unit != null) {
			selected_label.setText("物品 : " + selected_unit.getName());
		}
	}
	
//	-----------------------------------------------------------------------------------------
	
}
