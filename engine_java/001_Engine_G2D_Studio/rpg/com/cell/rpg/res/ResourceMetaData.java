package com.cell.rpg.res;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gameedit.StreamTiles;
import com.cell.gfx.IImage;
import com.cell.gfx.game.CSprite;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.item.ItemProperties;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.quest.QuestGroup;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.res.ResourceSet.SceneSet;
import com.cell.rpg.res.ResourceSet.SpriteSet;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.scene.instance.InstanceZone;
import com.cell.rpg.template.TAvatar;
import com.cell.rpg.template.TEffect;
import com.cell.rpg.template.TItem;
import com.cell.rpg.template.TItemList;
import com.cell.rpg.template.TShopItem;
import com.cell.rpg.template.TShopItemList;
import com.cell.rpg.template.TSkill;
import com.cell.rpg.template.TUnit;
import com.cell.sound.ISound;
import com.cell.util.concurrent.ThreadPoolService;
import com.g2d.BufferedImage;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;


public class ResourceMetaData implements Serializable
{
	private static final long serialVersionUID = 1L;

	// res objects
	public LinkedHashMap<String, SceneSet>			all_scene_set;
	public LinkedHashMap<String, SpriteSet>			all_actor_set;
	public LinkedHashMap<String, SpriteSet>			all_avatar_set;
	public LinkedHashMap<String, SpriteSet>			all_effect_set;

	// xml templates dynamic object and scenes
	public LinkedHashMap<Integer, ItemProperties>	item_properties;
	public LinkedHashMap<Integer, TUnit>			tunits;
	public LinkedHashMap<Integer, TItem>			titems;
	public LinkedHashMap<Integer, TShopItem>		tshopitems;
	public LinkedHashMap<Integer, TAvatar>			tavatars;
	public LinkedHashMap<Integer, TSkill>			tskills;
	public LinkedHashMap<Integer, TEffect>			teffects;
	public LinkedHashMap<Integer, TItemList>		titemlists;
	public LinkedHashMap<Integer, TShopItemList>	tshopitemlists;
	public LinkedHashMap<Integer, Quest> 			quests;
	public LinkedHashMap<Integer, QuestGroup> 		questgroups;
	public LinkedHashMap<Integer, Scene>			scenes;
	public LinkedHashMap<Integer, InstanceZone>		instance_zones;

	public LinkedHashMap<Integer, String> 			names_item_properties;
	public LinkedHashMap<Integer, String>			names_tunits;
	public LinkedHashMap<Integer, String>			names_titems;
	public LinkedHashMap<Integer, String>			names_tshopitems;
	public LinkedHashMap<Integer, String>			names_tavatars;
	public LinkedHashMap<Integer, String>			names_tskills;
	public LinkedHashMap<Integer, String>			names_teffects;
	public LinkedHashMap<Integer, String>			names_titemlists;
	public LinkedHashMap<Integer, String>			names_tshopitemlists;
	public LinkedHashMap<Integer, String> 			names_quests;
	public LinkedHashMap<Integer, String> 			names_questgroups;
	public LinkedHashMap<Integer, String>			names_scenes;
	public LinkedHashMap<Integer, String>			names_instance_zones;
	
	// icons , sounds, talks
	public LinkedHashSet<String>					all_icons;
	public LinkedHashSet<String>					all_sounds;
	public LinkedHashSet<String>					all_npc_talks;
	
//	--------------------------------------------------------------------------------------------------------------------

}
