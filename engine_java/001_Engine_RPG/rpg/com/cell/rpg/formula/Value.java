package com.cell.rpg.formula;

import com.g2d.annotation.Property;

@Property("值")
public class Value extends AbstractValue
{
	@Property("值")
	public double value = 0;
	
	public Value() {}
	
	public Value(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value+"";
	}
	
	
}
