package com.g2d.studio.res;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import com.cell.CIO;

import com.g2d.awt.util.*;


public class Res 
{
//	---------------------------------------------------------------------------------------------------------------
//	icon
	static public BufferedImage icon_cpj			= Tools.readImage("/com/g2d/studio/res/icon_cpj.png");
	static public BufferedImage	icon_edit			= Tools.readImage("/com/g2d/studio/res/icon_edit.png");
	static public BufferedImage	icon_error			= Tools.readImage("/com/g2d/studio/res/icon_error.png");
	static public BufferedImage icon_hd				= Tools.readImage("/com/g2d/studio/res/icon_hd.png");
	static public BufferedImage	icon_grid			= Tools.readImage("/com/g2d/studio/res/icon_grid.png");
	static public BufferedImage icon_quest			= Tools.readImage("/com/g2d/studio/res/icon_quest.png");
	static public BufferedImage icon_quest_group	= Tools.readImage("/com/g2d/studio/res/icon_quest_group.png");

	static public BufferedImage	icon_res_1			= Tools.readImage("/com/g2d/studio/res/icon_res_1.png");
	static public BufferedImage	icon_res_2			= Tools.readImage("/com/g2d/studio/res/icon_res_2.png");
	static public BufferedImage	icon_res_3			= Tools.readImage("/com/g2d/studio/res/icon_res_3.png");
	static public BufferedImage	icon_res_4			= Tools.readImage("/com/g2d/studio/res/icon_res_4.png");
	static public BufferedImage	icon_res_5			= Tools.readImage("/com/g2d/studio/res/icon_res_5.png");
	static public BufferedImage	icon_res_6			= Tools.readImage("/com/g2d/studio/res/icon_res_6.png");
	static public BufferedImage	icon_res_7			= Tools.readImage("/com/g2d/studio/res/icon_res_7.png");
	static public BufferedImage	icon_res_8			= Tools.readImage("/com/g2d/studio/res/icon_res_8.png");
	static public BufferedImage	icon_res_9			= Tools.readImage("/com/g2d/studio/res/icon_res_9.png");
	
	static public BufferedImage	icon_quest_condition= Tools.readImage("/com/g2d/studio/res/icon_quest_condition.png");
	static public BufferedImage	icon_quest_result	= Tools.readImage("/com/g2d/studio/res/icon_quest_result.png");
	static public BufferedImage	icon_quest_event	= Tools.readImage("/com/g2d/studio/res/icon_quest_event.png");
	
	static public BufferedImage	icon_action			= Tools.readImage("/com/g2d/studio/res/icon_action.png");
	static public BufferedImage	icon_camera			= Tools.readImage("/com/g2d/studio/res/icon_camera.png");
	static public BufferedImage	icon_layer			= Tools.readImage("/com/g2d/studio/res/icon_layer.png");
	static public BufferedImage	icon_affect			= Tools.readImage("/com/g2d/studio/res/icon_affect.png");
	static public BufferedImage	icon_talk			= Tools.readImage("/com/g2d/studio/res/icon_talk.png");
	
	static public BufferedImage	icon_condition		= Tools.readImage("/com/g2d/studio/res/icon_condition.png");
	static public BufferedImage	icon_run			= Tools.readImage("/com/g2d/studio/res/icon_run.png");
	static public BufferedImage	icon_scene			= Tools.readImage("/com/g2d/studio/res/icon_scene.png");
	static public BufferedImage	icon_trigger		= Tools.readImage("/com/g2d/studio/res/icon_trigger.png");
	static public BufferedImage	icon_event			= Tools.readImage("/com/g2d/studio/res/icon_event.png");

	static public BufferedImage	icon_scene_graph	= Tools.readImage("/com/g2d/studio/res/icon_scene_graph.png");
	
//	---------------------------------------------------------------------------------------------------------------
//	icons
	static public BufferedImage icons_bar[]			= clipImage("/com/g2d/studio/res/icons_bar.png", 10, 1);
	static public BufferedImage icons_tool_bar[]	= clipImage("/com/g2d/studio/res/icons_tool_bar.png", 3, 1);
	static public BufferedImage icon_refresh		= Tools.readImage("/com/g2d/studio/res/icon_refresh.png");

//	---------------------------------------------------------------------------------------------------------------
//	images
	static public BufferedImage	img_splash			= Tools.readImage("/com/g2d/studio/res/splash.jpg");
	
	static public BufferedImage	img_item_info		= Tools.readImage("/com/g2d/studio/res/img_item_info.png");
	static public BufferedImage	img_quest_info		= Tools.readImage("/com/g2d/studio/res/img_quest_info.png");
	static public BufferedImage	img_quest_info2		= Tools.readImage("/com/g2d/studio/res/img_quest_info2.png");
	static public BufferedImage	img_npc_bank		= Tools.readImage("/com/g2d/studio/res/img_npc_bank.png");
	static public BufferedImage	img_mail			= Tools.readImage("/com/g2d/studio/res/img_mail.png");
	static public BufferedImage	img_skill_trainer	= Tools.readImage("/com/g2d/studio/res/img_skill_trainer.png");
	static public BufferedImage	img_sell_item		= Tools.readImage("/com/g2d/studio/res/img_sell_item.png");
	static public BufferedImage	img_transport		= Tools.readImage("/com/g2d/studio/res/img_transport.png");
	static public BufferedImage	img_job_trainer		= Tools.readImage("/com/g2d/studio/res/img_job_trainer.png");
	static public BufferedImage	img_talk			= Tools.readImage("/com/g2d/studio/res/img_talk.png");
	static public BufferedImage	img_script			= Tools.readImage("/com/g2d/studio/res/img_script.png");
	
//	---------------------------------------------------------------------------------------------------------------
	
	
//	---------------------------------------------------------------------------------------------------------------
	
	static public BufferedImage[] clipImage(String src, int column, int row) {
		BufferedImage src_img = Tools.readImage(src);	
		BufferedImage[] ret = new BufferedImage[column * row];
		int sw = src_img.getWidth() / column;
		int sh = src_img.getHeight() / row;
		int i = 0;
		for (int c = 0; c < column; c++) {
			for (int r = 0; r < row; r++) {
				ret[i] = src_img.getSubimage(c*sw, r*sh, sw, sh);
				i ++;
			}
		}
		return ret;
	}
	
	
	
	
	
	
	
	
	
}
