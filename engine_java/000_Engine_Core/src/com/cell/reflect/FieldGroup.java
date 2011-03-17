package com.cell.reflect;

import java.lang.reflect.Field;

public interface FieldGroup 
{
	public void setField(Field field, Object value) throws Exception;
	
	public Object getField(Field field) throws Exception;
}
