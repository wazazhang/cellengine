package com.cell.bms.game;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.sound.SoundManager;
import com.cell.sound.mute_impl.NullSoundManager;
import com.cell.sound.openal_impl.JALSoundManager;

import com.g2d.SimpleCanvas;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.SimpleFrame;

public class MainApplet  extends com.g2d.SimpleApplet
{
	private static final long serialVersionUID = 1L;

	public MainApplet()
	{
		super(new SimpleCanvas().getCanvasAdapter(), null);
		
		CObject.initSystem(
				new CStorage("bms_player"), 
				new CAppBridge(
						getClass().getClassLoader(), 
						getClass()));
	}
	
	@Override
	public void init() 
	{
		super.init();

		String config_file = "/game.properties";
		Config.load(Config.class, config_file);
	
		try {
			SoundManager.setSoundManager(JALSoundManager.getInstance());
		} catch (Throwable e1) {
			SoundManager.setSoundManager(new NullSoundManager());
			e1.printStackTrace();
		}
		
		getCanvas().getComponent().setSize(
				Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		
		getCanvas().setStageSize(
				Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		
		setSize(
				Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		
		resize(Config.STAGE_WIDTH, 
				Config.STAGE_HEIGHT);
		
		getCanvas().setFPS(40);
		getCanvas().setUnactiveFPS(40);
		getCanvas().changeStage(StageTitle.class);
	}
	
	
}

