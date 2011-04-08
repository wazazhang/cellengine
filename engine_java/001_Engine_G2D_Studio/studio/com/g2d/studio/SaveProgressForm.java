package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.g2d.awt.util.AbstractFrame;
import com.g2d.awt.util.Drawing;
import com.g2d.awt.util.Tools;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.res.Res;

public class SaveProgressForm extends AbstractFrame implements IProgress
{	
	private static final long serialVersionUID = 1L;
	
	private JProgressBar progress = new JProgressBar();

	public SaveProgressForm()
	{
		this.setLayout(new BorderLayout());
		this.setIconImage(Res.icon_edit);
		this.setSize(300, 60);
		this.progress.setStringPainted(true);
		this.add(progress, BorderLayout.CENTER);	
		
		AbstractFrame.setCenter(this);

		new Thread(){
			public void run() {
				setVisible(true);
			};
		}.start();
	}
	
	@Override
	public void setMaximum(String prefix, int total) 
	{
		progress.setMaximum(total);			
		progress.setValue(0);
		progress.setString(prefix + " " + (progress.getValue())+"/"+progress.getMaximum());
	}
	
	@Override
	public void setValue(String prefix, int n) 
	{
		progress.setValue(n);
		progress.setString(prefix + " " + (progress.getValue()+1)+"/"+progress.getMaximum());
	}
	
	@Override
	public int getMaximum()
	{
		return progress.getMaximum();
	}
	
	@Override
	public int getValue()
	{
		return progress.getValue();
	}

}
