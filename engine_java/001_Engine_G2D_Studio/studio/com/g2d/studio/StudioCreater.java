package com.g2d.studio;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.cell.CObject;
import com.cell.io.CFile;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.util.anno.ConfigField;
import com.cell.xls.XLSColumns;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.io.IO;
import com.g2d.studio.io.file.FileIO;
import com.g2d.studio.io.http.FileHttp;

public class StudioCreater {

	
	static public void createWorkSpace(String root_dir)
	{
		CAppBridge.initNullStorage();
		
		File root = new File(root_dir);
		
		File project_file = new File(root, "project.g2d");
		
		if (!project_file.exists()) 
		{
			CFile.writeText(project_file, 
					Config.toProperties(Config.class), "UTF-8");
			
			new File(root, Config.RES_ACTOR_ROOT).mkdirs();
			new File(root, Config.RES_AVATAR_ROOT).mkdirs();
			new File(root, Config.RES_EFFECT_ROOT).mkdirs();
			new File(root, Config.RES_SCENE_ROOT).mkdirs();
			
			new File(root, Config.RES_ICON_ROOT).mkdirs();
			new File(root, Config.RES_SOUND_ROOT).mkdirs();
			new File(root, Config.RES_TALK_ROOT).mkdirs();

			createXLS(new File(root, Config.XLS_TPLAYER));
			createXLS(new File(root, Config.XLS_TPET));
			createXLS(new File(root, Config.XLS_TITEM));
			createXLS(new File(root, Config.XLS_TSHOPITEM));
			createXLS(new File(root, Config.XLS_TSKILL));
			createXLS(new File(root, Config.XLS_TUNIT));
		}
		else 
		{
			System.out.println(project_file.getName() + " already exist!");
		}
	}
	
	static private void createXLS(File file) {
		try {
			file.getParentFile().mkdirs();
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet(file.getName(), 0);
			wb.write();
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static public void main(String[] args) 
	{
		try
		{
			if (args == null || args.length == 0) {
				System.err.println("usage : g2dstudio.jar [root]");
				return;
			} else {
				createWorkSpace(args[0]);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			String message = "Open workspace error ! \n" + e.getClass().getName() + " : " + e.getMessage() + "\n";
			for (StackTraceElement stack : e.getStackTrace()) {
				message += "\t"+stack.toString()+"\n";
			}
			JOptionPane.showMessageDialog(null, message);
			System.exit(1);
		}
	}
	
}
