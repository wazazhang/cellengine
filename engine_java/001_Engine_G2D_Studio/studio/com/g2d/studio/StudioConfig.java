package com.g2d.studio;

import java.awt.Font;

import com.cell.CIO;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.cell.util.anno.ConfigType;




@ConfigType("G2DStudio编辑器配置文件")
public class StudioConfig extends com.cell.util.Config
{
//
	public static String 	TITLE					= "G2DStudio";

//	--------------------------------------------------------------------------------------------------------
	@ConfigSeparator("资源")
//	--------------------------------------------------------------------------------------------------------

//	 角色，场景，特效资源目录
	@ConfigField("角色精灵资源子目录名")
	public static String RES_ACTOR_ROOT				= "actor";
	@ConfigField("AVATAR部件资源子目录名")
	public static String RES_AVATAR_ROOT			= "avatar";
	@ConfigField("特效精灵资源子目录名")
	public static String RES_EFFECT_ROOT			= "effect";
	@ConfigField("场景资源子目录名")
	public static String RES_SCENE_ROOT				= "scene";

//	 声音和图像资源目录
	@ConfigField("声音资源子目录名")
	public static String RES_SOUND_ROOT				= "sound";
	@ConfigField("图标资源子目录名")
	public static String RES_ICON_ROOT				= "icons";
	@ConfigField("对话资源子目录名")
	public static String RES_TALK_ROOT				= "npctalk";

	public static String SOUND_SUFFIX				= ".ogg";
	public static String ICON_SUFFIX				= ".png";
	public static String TALK_SUFFIX				= ".js";
	
//	 XLS模板数据目录
	public static String XLS_TPLAYER				= "xls/tplayer.xls";
	public static String XLS_TUNIT					= "xls/tnpc.xls";
	public static String XLS_TPET					= "xls/tservant.xls";
	public static String XLS_TITEM					= "xls/titem.xls";
	public static String XLS_TSHOPITEM				= "xls/tshopitem.xls";
	public static String XLS_TSKILL					= "xls/tskill.xls";

	@ConfigField("G2D存储目录")
	public static String G2D_SAVE_NAME				= "project.g2d.save";

//	--------------------------------------------------------------------------------------------------------
	@ConfigSeparator("动态加载的类(插件类)")
//	--------------------------------------------------------------------------------------------------------
	public static String DYNAMIC_STUDIO_PLUGIN_CLASS;

//	--------------------------------------------------------------------------------------------------------
	@ConfigSeparator("低级界面")
//	--------------------------------------------------------------------------------------------------------
	
	/**低级界面默认的FPS*/
	public static Integer DEFAULT_FPS				= 30;
	public static Integer DEFAULT_BACK_COLOR		= 0xffff00ff;
	public static String  DEFAULT_FONT				= "宋体";
	public static Integer DEFAULT_FONT_SIZE			= 12;
	
//	--------------------------------------------------------------------------------------------------------
	static private String path;
	static private String root;
	static public void load(String file) {
		path = file.replace('\\', '/');
		root = path.substring(0, path.lastIndexOf("/")+1);
		load(StudioConfig.class, file);
	}
	static public String getRoot() {
		return root;
	}
	
}
