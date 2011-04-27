package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JProgressBar;

import com.g2d.awt.util.AbstractFrame;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.res.Res;

public class SaveProgressForm extends AbstractFrame implements IProgress
{	
	private static final long serialVersionUID = 1L;
	
	private JProgressBar progress = new JProgressBar();
	
	private boolean auto_close = false;
	public SaveProgressForm() {
		this(true);
	}
	
	public SaveProgressForm(boolean auto_close)
	{
		this.setLayout(new BorderLayout());
		this.setIconImage(Res.icon_edit);
		this.setSize(300, 60);
		this.progress.setStringPainted(true);
		this.add(progress, BorderLayout.CENTER);	
		
		AbstractFrame.setCenter(this);
		
		this.auto_close = auto_close;
		
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
		progress.setString(prefix + " " + (n+1)+"/"+progress.getMaximum());
		
		if (auto_close) {
			if (n + 1 >= progress.getMaximum()) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						setVisible(false);
					}
				});
			}
		}
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
