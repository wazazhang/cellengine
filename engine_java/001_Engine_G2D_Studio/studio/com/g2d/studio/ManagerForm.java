package com.g2d.studio;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;

import com.g2d.awt.util.*;

public abstract class ManagerForm extends JFrame
{
	private static final long serialVersionUID = 1L;

	final public Studio studio;
	
	public ManagerForm(Studio studio, ProgressForm progress, String title, Image icon) 
	{
		super.setSize(AbstractFrame.getScreenWidth()-Studio.getInstance().getWidth(), Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setIconImage(icon);		
		super.setTitle(title);
		this.studio = studio;
		System.out.println("init : " + title + " \t: " +  getClass().getSimpleName());
		progress.startReadBlock("初始化 [" + title + "] ...");
	}
	
	abstract public void saveAll(IProgress progress) throws Throwable;
	
	abstract public void saveSingle() throws Throwable;
	
	
}
