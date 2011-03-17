package com.g2d.studio.cpj;

import java.io.Serializable;

import com.g2d.studio.cpj.entity.CPJObject;

final public class CPJIndex<T extends CPJObject<?>> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	final public CPJResourceType	res_type;
	final public String 			cpj_file_name;
	final public String 			set_object_name;
	transient T						object;
	
	public CPJIndex(CPJResourceType type, String cpj, String set) {
		this.res_type			= type;
		this.cpj_file_name		= cpj;
		this.set_object_name	= set;
	}
	
	public T getObject() {
		return object;
	}
	
	@Override
	public String toString() {
		return cpj_file_name + "/" + set_object_name;
	}
}
