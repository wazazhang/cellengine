package com.cell.rpg.client.j3d;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCondition;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


import com.cell.rpg.client.World;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;

public class ActorImpl extends com.cell.rpg.client.Actor implements J3dNode
{
	protected BranchGroup Root;
	
	protected TransformGroup Group;
	
	private Vector3d pos = new Vector3d();
	private Transform3D t1 = new Transform3D();
	
	protected ActorImpl(String name) 
	{
		super(name);
	}
	
	public ActorImpl(String name, boolean def) 
	{
		this(name, new ColorCube(0.5));
	}
	
	public ActorImpl(String name, Shape3D shape) 
	{
		super(name);
		init(shape);
	}
	
	protected void init(Shape3D shape)
	{
		Group = new TransformGroup();
		Group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Group.addChild(shape);
		
		Root = new BranchGroup();
		Root.setCapability(BranchGroup.ALLOW_DETACH);
		Root.addChild(Group);
		Root.compile();
	}
	
	public WorldImpl getParent() {
		return (WorldImpl)Parent;
	}
	
	public void added(World world) {
		((WorldImpl)world).SceneRoot.addChild(Root);
	}
	
	public void removed(World world) {
		((WorldImpl)world).SceneRoot.removeChild(Root);
	}
	
	public void update() {}
	
	public double getX(){
		return pos.x;
	}
	
	public double getY(){
		return pos.z;
	}
	
	public void setPos(double x, double y){
		pos.z = y;
		pos.x = x;
		t1.setTranslation(pos);
		Group.setTransform(t1);
	}
	
	public void move(float dx, float dy){
		pos.z += dy;
		pos.x += dx;
		t1.setTranslation(pos);
		Group.setTransform(t1);
	}
	
	public void moveTo(float speed, float degree){
		pos.z += Math.sin(degree) * speed;
		pos.x += Math.cos(degree) * speed;
		t1.setTranslation(pos);
		Group.setTransform(t1);
	}
	
	
}
