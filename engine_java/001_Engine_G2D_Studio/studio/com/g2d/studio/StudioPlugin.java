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
//	--------------------------------------------------------------------------------------------------------
	
	/**资源打包工具, 执行Builder的类*/
	public Builder	createResourceBuilder();
	
}
