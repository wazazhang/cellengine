package com.cell.rpg.client;

public abstract class Node
{
	final public String Name;
	
	protected World Parent;
	
	public Node(String name) {
		Name = name;
	}

	//
	abstract public void update();

	abstract public void added(World world);
	
	abstract public void removed(World world);
	
	
	// 
	abstract public double getX();
	
	abstract public double getY();
	
	abstract public void setPos(double x, double y);
	
	abstract public void move(float dx, float dy);
	
	abstract public void moveTo(float speed, float degree);
}
