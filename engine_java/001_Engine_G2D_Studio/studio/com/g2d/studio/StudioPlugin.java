package com.g2d.studio;

import java.io.File;
import java.io.IOException;

import com.cell.rpg.item.ItemPropertyManager;
import com.cell.rpg.scene.SceneAbilityManager;
//import com.cell.rpg.scene.script.SceneScriptManager;
import com.cell.util.anno.ConfigField;
import com.cell.util.anno.ConfigSeparator;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.io.file.FileIO;

/**
 * 动态加载的类(插件类)
 * @author zhangyifei
 */
public interface StudioPlugin 
{
	/**所有道具属性*/
	public ItemPropertyManager createItemPropertyManager();
	/**场景能力属性管理器*/
	public SceneAbilityManager createSceneAbilityManager();
//	/**场景脚本管理器*/
//	public SceneScriptManager createSceneScriptManager();
	
	/**与TriggerUnitMethod映射的类型PLAYER*/
	public Class<?> getPlayerClass();
	/**与TriggerUnitMethod映射的类型PET*/
	public Class<?> getPetClass();
	/**与TriggerUnitMethod映射的类型NPC*/
	public Class<?> getNpcClass();
	
	/**XML持久化工具*/
	public String getPersistanceManager();
	/**SQL持久化工具*/
	public String getSQLDriver();
	
//	--------------------------------------------------------------------------------------------------------
	
////角色，场景，特效资源目录
//	@ConfigField("角色精灵资源子目录名")
//	public static String RES_ACTOR_ROOT				= "actor";
//	@ConfigField("AVATAR部件资源子目录名")
//	public static String RES_AVATAR_ROOT			= "avatar";
//	@ConfigField("特效精灵资源子目录名")
//	public static String RES_EFFECT_ROOT			= "effect";
//	@ConfigField("场景资源子目录名")
//	public static String RES_SCENE_ROOT				= "scene";
//
////	 声音和图像资源目录
//	@ConfigField("声音资源子目录名")
//	public static String RES_SOUND_ROOT				= "sound";
//	@ConfigField("图标资源子目录名")
//	public static String RES_ICON_ROOT				= "icons";
//	@ConfigField("对话资源子目录名")
//	public static String RES_TALK_ROOT				= "npctalk";
//
//	public static String SOUND_SUFFIX				= ".ogg";
//	public static String ICON_SUFFIX				= ".png";
//	public static String TALK_SUFFIX				= ".js";
//	
////	 XLS模板数据目录
//	public static String XLS_TPLAYER				= "xls/tplayer.xls";
//	public static String XLS_TUNIT					= "xls/tnpc.xls";
//	public static String XLS_TPET					= "xls/tservant.xls";
//	public static String XLS_TITEM					= "xls/titem.xls";
//	public static String XLS_TSHOPITEM				= "xls/tshopitem.xls";
//	public static String XLS_TSKILL					= "xls/tskill.xls";

	
	
//	--------------------------------------------------------------------------------------------------------
	
	/**资源打包工具, 执行Builder的类*/
	public Builder	createResourceBuilder();
	
}
