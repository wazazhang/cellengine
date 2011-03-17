package com.cell.rpg.client.j3d;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.cell.rpg.client.Actor;
import com.cell.rpg.client.Camera;
import com.cell.rpg.client.World;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class CameraImpl extends Camera  implements J3dNode
{
	private Actor LockedActor;
	
	private Vector3d pos = new Vector3d();
	private Transform3D t1 = new Transform3D();
	private TransformGroup Group;
	
	
	public CameraImpl(String name) {
		super(name);
	}
	
	public WorldImpl getParent() {
		return (WorldImpl)Parent;
	}
	
	public void lock(Actor actor) {
		LockedActor = actor;
	}

	public void added(World world) {
		ViewingPlatform vp = getParent().Univ.getViewingPlatform();
		vp.setNominalViewingTransform();
		Group = vp.getViewPlatformTransform();
		setPos(0, 0); 
	}

	public void removed(World world) {}

	public void update() {
		if (LockedActor != null) {
			setPos(LockedActor.getX(), LockedActor.getY()) ;
		}
	}

	public double getX(){
		return pos.x;
	}
	
	public double getY(){
		return pos.z;
	}
	
	public void move(float dx, float dy) {
		setPos(getX() + dx, getY() + dy);
	}

	public void moveTo(float speed, float degree) {
		move((float)(Math.cos(degree) * speed), (float)(Math.sin(degree) * speed));
	}
	
	
	public void setPos(double x, double y) 
	{
		pos.x = x;
		pos.y = 0;
		pos.z = y;
		
		Group.getTransform(t1);

	    t1.lookAt(
	    		new Point3d(pos.x, pos.y + 10, pos.z + 5), 
	    		new Point3d(pos.x, pos.y +  0, pos.z + 0), 
	    		new Vector3d(0,10,0)
	    		);
	   	t1.invert();

	   	Group.setTransform(t1);
	}
	
}
