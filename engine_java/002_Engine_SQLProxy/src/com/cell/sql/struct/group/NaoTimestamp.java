package com.cell.sql.struct.group;

import java.lang.reflect.Field;

import com.cell.sql.SQLFieldGroup;
import com.cell.sql.SQLType;
import com.cell.sql.anno.SQLField;

public class NaoTimestamp implements SQLFieldGroup
{
	@SQLField(type=SQLType.LONG, default_value="'2009-08-05 13:08:06'")
	public long militime;
	
	@SQLField(type=SQLType.LONG, default_value="0")
	public long naotime;
	
	
	public NaoTimestamp(long militime, long naotime) {
		this.militime	= militime;
		this.naotime	= naotime;
	}
	
	public NaoTimestamp() {
		this.militime	= System.currentTimeMillis();
		this.naotime	= System.nanoTime();
	}
	public void setField(Field f , Object obj) throws Exception
	{
		f.set(this, obj);
	}
	
	public Object getField(Field field) throws Exception
	{
		return field.get(this);
	}
	
	@Override
	public String toString() {
		return militime+":"+naotime;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof NaoTimestamp) {
			return 
			militime == ((NaoTimestamp)obj).militime &&
			naotime  == ((NaoTimestamp)obj).naotime;
		}
		return false;
	}
}
