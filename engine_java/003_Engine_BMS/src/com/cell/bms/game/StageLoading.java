package com.cell.bms.game;

import com.cell.bms.BMSFile;
import com.cell.bms.BMSPlayer;
import com.cell.bms.BMSFile.LoadingListener;

import com.g2d.Canvas;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.ui.Form;
import com.g2d.util.Drawing;

public class StageLoading extends Stage implements Runnable, LoadingListener
{
	String		bms_file ;
	
	int			progress_max		= 1;
	int			progress_current	= 0;
	String		progress_text		= "";
	
	public StageLoading() 
	{
		setSize(Config.STAGE_WIDTH, Config.STAGE_HEIGHT);	
	}
	
	@Override
	public void inited(Canvas root, Object[] args) {
		if (args != null && args.length > 0) {
			if (args[0] instanceof String) {
				this.bms_file = (String) args[0];
				new Thread(this).start();
			}
		}
	}
	
	public void added(DisplayObjectContainer parent) {}
	public void removed(DisplayObjectContainer parent) {}
	public void update() {}
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fill(local_bounds);
		g.setColor(Color.WHITE);
		g.drawRect(0, getHeight()-11, getWidth(), 10);
		g.fillRect(0, getHeight()-11, 
				getWidth() * progress_current / progress_max, 
				10);
		Drawing.drawString(g, progress_current + "/" + progress_max, 10, 10);
		Drawing.drawString(g, progress_text, 10, 30);
	}
	
	@Override
	public void run() {
		BMSFile bms = new BMSFile(bms_file, this);
		getRoot().changeStage(StageGame.class, bms);
	}
	
	@Override
	public void beginLoading(BMSFile bmsFile) {}
	
	@Override
	public void endLoading(BMSFile bmsFile) {}
	
	@Override
	public void loadline(BMSFile bmsFile, int max, int current, String lineData) {
		progress_max = max;
		progress_current = current;
		progress_text = lineData;
	}
}
