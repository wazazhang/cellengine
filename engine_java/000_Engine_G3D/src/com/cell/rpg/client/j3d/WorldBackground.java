package com.cell.rpg.client.j3d;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class WorldBackground extends BranchGroup
{
	public WorldBackground(String backgroundImageFile, Bounds backgroundBounds) 
	{
		try{
			TransformGroup objTrans = new TransformGroup();
			
			Background bg = new Background();
			bg.setApplicationBounds(backgroundBounds);
			BranchGroup backGeoBranch = new BranchGroup();
			Sphere sphereObj = new Sphere(1.0f, 
					Sphere.GENERATE_NORMALS | 
					Sphere.GENERATE_NORMALS_INWARD | 
					Sphere.GENERATE_TEXTURE_COORDS, 
					100);
			Appearance backgroundApp = sphereObj.getAppearance();
			backGeoBranch.addChild(sphereObj);
			bg.setGeometry(backGeoBranch);
			objTrans.addChild(bg);
			
			Color3f write = new Color3f(1f, 1f, 1f);
			Material wMat = new Material(write, write, write, write, 100.0f);
			wMat.setLightingEnable(true);
			//backgroundApp.setMaterial(wMat);
			
			TextureLoader tex = new TextureLoader(
					ImageIO.read(getClass().getResourceAsStream(backgroundImageFile)),
					TextureLoader.BY_REFERENCE | 
					TextureLoader.Y_UP);
			
			backgroundApp.setTexture(tex.getTexture());
		
			addChild(objTrans);
			
		}catch(Exception err){
			err.printStackTrace();
		}
		
		
	}
	
}
