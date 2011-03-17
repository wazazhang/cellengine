package com.g2d.display.ui.event;

import com.cell.DObject;

public class Action extends DObject
{
	final public String action;
	
	public Action(String action) {
		this.action = action;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Action) {
			return action.equals(((Action) obj).action);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return action;
	}
}
