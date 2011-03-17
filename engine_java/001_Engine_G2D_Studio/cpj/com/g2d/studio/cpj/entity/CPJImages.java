package com.g2d.studio.cpj.entity;

public class CPJImages
{
	final public CPJFile parent;
	final public String images_name;
	
	public CPJImages(CPJFile parent, String images_name) 
	{
		this.parent = parent;
		this.images_name = images_name;
	}
	
	@Override
	public String toString() {
		return this.parent.name + "/" + this.images_name;
	}
}
