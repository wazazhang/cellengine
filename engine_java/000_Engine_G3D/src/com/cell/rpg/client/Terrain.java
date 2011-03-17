package com.cell.rpg.client;

public abstract class Terrain extends Node
{
	public Terrain(String name) {
		super(name);
	}
	
	abstract public double getWidth();
	
	abstract public double getHeight();
	
	
}
