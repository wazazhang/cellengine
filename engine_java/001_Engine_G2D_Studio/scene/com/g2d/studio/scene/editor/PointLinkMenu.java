package com.g2d.studio.scene.editor;

import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Menu;
import com.g2d.studio.scene.units.ScenePoint;

public class PointLinkMenu extends Menu
{
	final static String item_1 = "单向链接";
	final static String item_2 = "反向链接";
	final static String item_3 = "双向链接";
	final static String itemd_1 = "单向解开";
	final static String itemd_2 = "反向解开";
	final static String itemd_3 = "双向解开";

	final ScenePoint unit;
	final ScenePoint next;
	
	public PointLinkMenu(ScenePoint point, ScenePoint next) {
		super(100, new String[]{item_1, item_2, item_3, itemd_1, itemd_2, itemd_3});
		this.unit = point;
		this.next = next;
	}
	

	protected void onClickMenuItem(MouseEvent e, MenuItem item) {
		try{
			synchronized(unit.getNextNodes()) {
				synchronized(next.getNextNodes()) {
					if (item.getUserData().equals(item_1)) {
						unit.getNextNodes().add(next);
					} else if (item.getUserData().equals(item_2)) {
						next.getNextNodes().add(unit);
					} else if (item.getUserData().equals(item_3)) {
						unit.getNextNodes().add(next);
						next.getNextNodes().add(unit);
					} else if (item.getUserData().equals(itemd_1)) {
						unit.getNextNodes().remove(next);
					} else if (item.getUserData().equals(itemd_2)) {
						next.getNextNodes().remove(unit);
					} else if (item.getUserData().equals(itemd_3)) {
						unit.getNextNodes().remove(next);
						next.getNextNodes().remove(unit);
					}
				}
			}
		}catch(Exception err){}
	}


}
