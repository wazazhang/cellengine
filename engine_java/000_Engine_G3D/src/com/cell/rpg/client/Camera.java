package com.cell.rpg.client;

public abstract class Camera extends Node
{
	public Camera(String name) {
		super(name);
	}
	
	public abstract void lock(Actor actor);
	
	
}
