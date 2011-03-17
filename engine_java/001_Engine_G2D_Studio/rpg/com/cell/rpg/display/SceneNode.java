package com.cell.rpg.display;

public class SceneNode extends Node
{
	private static final long serialVersionUID = 1L;

	public int	width, height;
	
	public SceneNode(String cpj, String scene_id)
	{
		super(cpj, scene_id, Type.WORLD);
	}
}
