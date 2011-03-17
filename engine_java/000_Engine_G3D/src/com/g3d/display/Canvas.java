package com.g3d.display;

import java.awt.GraphicsConfiguration;
import java.awt.Panel;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.VirtualUniverse;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class Canvas extends Panel
{
	private SimpleUniverse universe = null;
	
	public Canvas() 
	{
		// Get the preferred graphics configuration for the default screen
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		// Create a Canvas3D using the preferred configuration
		Canvas3D canvas = new Canvas3D(config);

		// Create simple universe with view branch
		universe = new SimpleUniverse(canvas);
		
		
		this.add(canvas);
		
	}


	public VirtualUniverse getUniverse() {
		return universe;
	}

}
