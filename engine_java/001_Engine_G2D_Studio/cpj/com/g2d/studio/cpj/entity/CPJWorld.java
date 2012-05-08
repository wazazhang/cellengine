package com.g2d.studio.cpj.entity;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.cell.gameedit.object.WorldSet;
import com.g2d.awt.util.Tools;
import com.g2d.cell.game.Scene;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.io.File;

public class CPJWorld extends CPJObject<WorldSet>
{	
//	Scene scene; 
	
	public BufferedImage scene_snapshoot;
	
	public CPJWorld(CPJFile parent, String name) {
		super(parent, name, WorldSet.class, CPJResourceType.WORLD);
	}
	
//	@Override
//	public Scene createDisplayObject() {
////		if (scene==null) {
////			scene = new Scene(parent.getSetResource(), name);
////		}
////		return scene;
//		return null;
//	}
	
	@Override
	public void setSetObject(WorldSet obj) {
		super.setSetObject(obj);
		resetIcon();
		snapshoot = null;
		scene_snapshoot=null;
	}
	
	@Override
	public BufferedImage getSnapShoot() {
		if (snapshoot==null) {
			try{
				File snap_file = Studio.getInstance().getIO().createFile(
						parent.getCPJDir(), name + ".png");
				if (snap_file.exists()) {
					byte[] data = snap_file.readBytes();
					if (data != null) {
						snapshoot = Tools.readImage(new ByteArrayInputStream(data));
						float rate = 80f / (float)snapshoot.getWidth();
						snapshoot = Tools.combianImage(80, (int)(snapshoot.getHeight()*rate), snapshoot);
						scene_snapshoot = snapshoot;
					}  
				} else {
					System.err.println(snap_file + " not exist !");
				}
			}catch(Exception err){
				System.err.println(err.getMessage());
			}
		}
		return snapshoot;
	}

	public void saveSnapshot(BufferedImage image) {
		if (scene_snapshoot == null) {
			File snap_file = Studio.getInstance().getIO().createFile(parent.getCPJDir(), name + ".png");
			scene_snapshoot = image;
			float rate = 80f / (float)scene_snapshoot.getWidth();
			scene_snapshoot = Tools.combianImage(80, (int)(scene_snapshoot.getHeight()*rate), scene_snapshoot);
//			Tools.writeImage(snap_file.getPath(), scene_snapshoot);
			try {
				ByteArrayOutputStream buff = new ByteArrayOutputStream();
				ImageOutputStream ios = ImageIO.createImageOutputStream(buff);
				ImageIO.write(scene_snapshoot, "png", ios);
				ios.flush();
				ios.close();
				snap_file.writeBytes(buff.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
			snapshoot = scene_snapshoot;
			getIcon(true);
		}
	}
	
//	@Override
//	public String toString() {
//		return super.toString() + "\n" + getSetObject().Width+"x"+getSetObject().Height;
//	}
}
