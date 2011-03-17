package com.cell.bms.game;

import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.sound.SoundManager;
import com.cell.sound.mute_impl.NullSoundManager;
import com.cell.sound.openal_impl.JALSoundManager;
import com.g2d.CanvasAdapter;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.SimpleFrame;

public class Main {

	static SimpleCanvasNoInternal	canvas;
	static SimpleFrame 				frame;
	
	public static void main(String[] args) throws Exception
	{
		CObject.initSystem(new CStorage("bms_player"), new CAppBridge(
				Main.class.getClassLoader(),
				Main.class));
		
		String config_file = "/game.properties";
		Config.load(Config.class, config_file);
		
		try {
			SoundManager.setSoundManager(JALSoundManager.getInstance());
		} catch (Throwable e1) {
			SoundManager.setSoundManager(new NullSoundManager());
			e1.printStackTrace();
		}
		
		canvas = new SimpleCanvasNoInternal(
				Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		canvas.getCanvasAdapter().setFPS(40);
		canvas.getCanvasAdapter().setUnactiveFPS(40);
		
		frame = new SimpleFrame(
				canvas.getCanvasAdapter(),
				StageTitle.class.getName());
		frame.setTitle("BMSPlayer");
		frame.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				frame.fillCanvasSize();
				super.componentResized(e);
			}
		});
	}
	
}
