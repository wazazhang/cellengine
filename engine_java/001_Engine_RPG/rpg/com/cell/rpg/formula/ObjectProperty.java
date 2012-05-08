package com.cell.rpg.formula;

import com.g2d.annotation.Property;

@Property("单位-属性")
public abstract class ObjectProperty extends AbstractValue
{
	@Property("单位字段")
	public String						filed_name;

	public ObjectProperty() {}
	
	public ObjectProperty(String field_name) {
		this.filed_name = field_name;
	}
	
	@Override
	public String toString() {
		return filed_name;
	}
}
	

