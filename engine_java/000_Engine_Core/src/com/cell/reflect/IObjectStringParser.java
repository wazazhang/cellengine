package com.cell.reflect;





public interface IObjectStringParser
{
	public Object parseFrom(String str, Class<?> return_type);

	public String toString(Object obj);
	
};




