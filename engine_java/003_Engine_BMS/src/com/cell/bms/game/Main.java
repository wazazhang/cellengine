package com.cell.bms.game;


import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.sound.SoundManager;
import com.cell.sound.mute_impl.NullSoundManager;
import com.cell.sound.openal_impl.JALSoundManager;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.java2d.impl.SimpleCanvas;
import com.g2d.java2d.impl.SimpleFrame;
import com.g2d.java2d.impl.test.Test;

public class Main {

	static SimpleCanvas	canvas;
	static SimpleFrame	frame;
	
	public static void main(String[] args) throws Exception
	{
		CObject.initSystem(new CStorage("bms_player"), new CAppBridge(
				Main.class.getClassLoader(),
				Main.class));
		new AwtEngine();
		String config_file = "/game.properties";
		Config.load(Config.class, config_file);
		
		try {
			SoundManager.setSoundManager(JALSoundManager.getInstance());
		} catch (Throwable e1) {
			SoundManager.setSoundManager(new NullSoundManager());
			e1.printStackTrace();
		}
		
//		canvas = new SimpleCanvas(
//				Config.STAGE_WIDTH, 
//				Config.STAGE_HEIGHT);
//		canvas.getCanvasAdapter().setFPS(40);
//		canvas.getCanvasAdapter().setUnactiveFPS(40);
		
		frame = new SimpleFrame(
				Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		frame.setTitle("BMSPlayer"); 
        frame.setVisible(true);
        frame.start(60, StageTitle.class);
//		frame.addComponentListener(new ComponentAdapter(){
//			public void componentResized(ComponentEvent e) {
//				frame.fillCanvasSize();
//				super.componentResized(e);
//			}
//		});
	}
	
}
