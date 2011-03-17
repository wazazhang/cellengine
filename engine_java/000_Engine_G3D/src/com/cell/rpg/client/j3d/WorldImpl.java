package com.cell.rpg.client.j3d;

import java.awt.AWTEvent;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.cell.rpg.client.Actor;
import com.cell.rpg.client.Camera;
import com.cell.rpg.client.Node;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class WorldImpl extends com.cell.rpg.client.World 
{
	// 使当前场景有定时更新能力
	protected class UpdateBehavior extends Behavior 
	{
		private WakeupCondition timeOut;

		public UpdateBehavior(int timeDelay) {
			timeOut = new WakeupOnElapsedTime(timeDelay);
			setSchedulingBounds(WorldBounds);
		}

		public void initialize() {
			wakeupOn(timeOut);
		}

		@SuppressWarnings("unchecked")
		public void processStimulus(Enumeration criteria) {
			// notify all node to update
			synchronized (KeyHoldMap){
				update();
				KeyPressedMap.clear();
				KeyReleasedMap.clear();
			}
			Thread.yield();
			wakeupOn(timeOut);
			
		}
	}
	
	// 使当前场景有按键响应的能力 
	protected class KeyPressBehavior extends Behavior 
	{
		private WakeupCondition wc;

		public KeyPressBehavior() {
			wc = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
			setSchedulingBounds(WorldBounds);
		}

		public void initialize() {
			wakeupOn(wc);
		}
		
		@SuppressWarnings("unchecked")
		public void processStimulus(Enumeration criteria) {
			WakeupCriterion wakeup;
			AWTEvent[] event;
			while (criteria.hasMoreElements()) {
				wakeup = (WakeupCriterion) criteria.nextElement();
				if (wakeup instanceof WakeupOnAWTEvent){
					event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
					for (int i = 0; i < event.length; i++) 
					{
						synchronized (KeyHoldMap) {
							if (event[i].getID() == KeyEvent.KEY_PRESSED) {
								KeyPressedMap.put(((KeyEvent)event[i]).getKeyCode(), "");
								KeyHoldMap.put(((KeyEvent)event[i]).getKeyCode(), "");
							}
						}
					}
				}
			}
			wakeupOn(wc);
		}
		
	}
	
	protected class KeyReleaseBehavior extends Behavior 
	{
		private WakeupCondition wc;

		public KeyReleaseBehavior() {
			wc = new WakeupOnAWTEvent(KeyEvent.KEY_RELEASED);
			setSchedulingBounds(WorldBounds);
		}

		public void initialize() {
			wakeupOn(wc);
		}
		
		@SuppressWarnings("unchecked")
		public void processStimulus(Enumeration criteria) {
			WakeupCriterion wakeup;
			AWTEvent[] event;
			while (criteria.hasMoreElements()) {
				wakeup = (WakeupCriterion) criteria.nextElement();
				if (wakeup instanceof WakeupOnAWTEvent){
					event = ((WakeupOnAWTEvent) wakeup).getAWTEvent();
					for (int i = 0; i < event.length; i++) 
					{
						synchronized (KeyHoldMap) {
							if (event[i].getID() == KeyEvent.KEY_RELEASED){
								KeyReleasedMap.put(((KeyEvent)event[i]).getKeyCode(), "");
								KeyHoldMap.remove(((KeyEvent)event[i]).getKeyCode());
							}
						}
					}
				}
			}
			wakeupOn(wc);
		}
		
	}
	
	//-------------------------------------------------------------
	
	public static float MainSizeRate = 0.1f;
	
	
	final public SimpleUniverse Univ;
	
	final public Bounds WorldBounds;
	
	final public BranchGroup SceneRoot;
	
	final public int FPS;
	
	public WorldImpl (SimpleUniverse univ, Bounds worldBounds, int fps)
	{
		Univ = univ;
		WorldBounds = worldBounds;
		SceneRoot = new BranchGroup();
		FPS = fps;
		
		init();
	}
	
	protected void init ()
	{
		SceneRoot.setCapability(Group.ALLOW_CHILDREN_READ);
		SceneRoot.setCapability(Group.ALLOW_CHILDREN_WRITE);
		SceneRoot.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		SceneRoot.setCapability(BranchGroup.ALLOW_DETACH);
		
		// set up the ambient light
		{
			Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		    AmbientLight ambientLightNode = new AmbientLight(white);
		    ambientLightNode.setInfluencingBounds(WorldBounds);
		    //SceneRoot.addChild(ambientLightNode);
		}

		// set up default camera
		{
			ViewingPlatform vp = Univ.getViewingPlatform();
			vp.setNominalViewingTransform();
			TransformGroup steerTG = vp.getViewPlatformTransform();
			Transform3D t3d = new Transform3D();
		    steerTG.getTransform(t3d);

		    t3d.lookAt(new Point3d(0,10,10), new Point3d(0,0,0), new Vector3d(0,1,0));
		   	t3d.invert();

		    steerTG.setTransform(t3d);
			
			OrbitBehavior orbit = new OrbitBehavior(Univ.getCanvas(), OrbitBehavior.REVERSE_ALL);
			orbit.setSchedulingBounds(WorldBounds);
			vp.setViewPlatformBehavior(orbit);
			
			//p.setViewPlatformBehavior(null);
		}
		
		{
			SceneRoot.addChild(new KeyPressBehavior());
			SceneRoot.addChild(new KeyReleaseBehavior());
			SceneRoot.addChild(new UpdateBehavior((int)(1000/FPS)));
		}
		
		SceneRoot.compile();
		
		Univ.addBranchGraph(SceneRoot);
	
	}
	
	public void setBackground(WorldBackground bg) {
		
		SceneRoot.addChild(bg);
	}
	
	public void update() {
		super.update();
	}

	
	
}
