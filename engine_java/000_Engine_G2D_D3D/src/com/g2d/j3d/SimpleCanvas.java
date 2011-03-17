package com.g2d.j3d;

import ActorImpl;
import CameraImpl;
import SetInput;
import SetMap2D;
import SetSprite2D;
import WorldBackground;
import WorldImpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.io.IOException;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.universe.SimpleUniverse;


public class SimpleCanvas extends Canvas3D
{
	GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
	
    public SimpleCanvas(GraphicsConfiguration config)
    {	
		super(config);

	

	}
    
    public void setFPS(int fps) {
    	stop();
		start(fps);
    }
    
    
}
