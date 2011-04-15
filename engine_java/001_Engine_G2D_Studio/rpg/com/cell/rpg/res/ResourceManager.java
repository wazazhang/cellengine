package com.cell.rpg.res;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.g2d.studio.Config;


/**
 * @author WAZA
 * 第三方程序用来读入G2D Studio对象的类。
 * 该类的初始化方法只能在构造中调用。
 * 该类不需要同步，因为运行过程中只读，不能改动其内容。
 */	
@SuppressWarnings("unused")
public abstract class ResourceManager extends CellSetResourceManager
{
	public static boolean	PRINT_VERBOS 	= false;
//	--------------------------------------------------------------------------------------------------------------------
	
	final public String res_root;
	
	// icons , sounds, talks
	protected LinkedHashMap<String, AtomicReference<BufferedImage>>	all_icons;
	protected LinkedHashMap<String, AtomicReference<ISound>>		all_sounds;
	protected LinkedHashMap<String, AtomicReference<String>>		all_npc_talks;
	
	final protected ResourceMetaData meta_data;
	
//	--------------------------------------------------------------------------------------------------------------------

	/**
	 * 子类自定义初始化什么
	 * @param res_root
	 * @throws Exception
	 */
	public ResourceManager(String res_root) throws Exception
	{
		this.res_root	= res_root;
		this.meta_data	= new ResourceMetaData();
	}

	public ResourceManager(
			String persistance_manager, 
			String res_root, 
			boolean init_set,
			boolean init_xml,
			boolean init_icon,
			boolean init_sound)  throws Exception
	{
		this.res_root	= res_root;
		this.meta_data	= new ResourceMetaData();
		
		if (init_set) 
			initAllSet();
		
		if (init_xml) 
			initAllXml(persistance_manager);
		
		if (init_icon) 
			initIcons();
		
		if (init_sound) 
			initSounds();
	}
	
	public ResourceManager(String res_root, ResourceMetaData meta)  throws Exception
	{
		this.res_root	= res_root;
		this.meta_data	= meta;

		if (meta_data.all_icons != null) {
			this.all_icons = new LinkedHashMap<String, AtomicReference<BufferedImage>>();
			for (String name : meta_data.all_icons) {
				all_icons.put(name, new AtomicReference<BufferedImage>());
			}
		}
		if (meta_data.all_sounds != null) {
			this.all_sounds = new LinkedHashMap<String, AtomicReference<ISound>>();
			for (String name : meta_data.all_sounds) {
				all_sounds.put(name, new AtomicReference<ISound>());
			}
		}
		if (meta_data.all_npc_talks != null) {
			this.all_npc_talks = new LinkedHashMap<String, AtomicReference<String>>();
			for (String name : meta_data.all_npc_talks) {
				all_npc_talks.put(name, new AtomicReference<String>());
			}
		}
	}

	
//	--------------------------------------------------------------------------------------------------------------------

//	--------------------------------------------------------------------------------------------------------------------

//	abstract protected ThreadPoolService getLoadingService() ;
	
	abstract protected CellSetResource createSet(String path) throws Exception;

	@Override
	public CellSetResource getSet(String path) throws Exception {
		return super.getSet(path);
	}
	
	protected byte[] getResource(String sub_file_name) {
		byte[] data = CIO.loadData(res_root+sub_file_name);
		return data;
	}

	protected String readAllText(String path)
	{
		return readAllText(path, CObject.getEncoding());
	}
	
	protected String readAllText(String path, String encoding)
	{
		byte[] data = getResource(path);
		if (data != null) {
			return CIO.stringDecode(data, encoding);
		}
		return null;
	}

	protected String[] readAllLine(String path) {
		return readAllLine(path, CObject.getEncoding());
	}
	
	protected String[] readAllLine(String path, String encoding)
	{
		try {
			String src = readAllText(path, encoding);
			String[] ret = CUtil.splitString(src, "\n");
			for (int i = ret.length - 1; i >= 0; i--) {
				int ld = ret[i].lastIndexOf('\r');
				if (ld >= 0) {
					ret[i] = ret[i].substring(0, ld);
				}
			}
			return ret;
		} catch (Exception err) {
			err.printStackTrace();
			return new String[] { "" };
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

//	--------------------------------------------------------------------------------------------------------------------

	final protected void initAllSet() throws Exception{
		initAllSet(new AtomicReference<Float>(0f));
	}

	final protected void initAllSet(AtomicReference<Float> percent) throws Exception
	{
		meta_data.all_scene_set	= readSets("/" + Config.G2D_SAVE_NAME + "/resources/scene_list.list",	SceneSet.class,		percent, 0 , 4);
		meta_data.all_actor_set	= readSets("/" + Config.G2D_SAVE_NAME + "/resources/actor_list.list",	SpriteSet.class,	percent, 1 , 4);
		meta_data.all_avatar_set= readSets("/" + Config.G2D_SAVE_NAME + "/resources/avatar_list.list",	SpriteSet.class,	percent, 2 , 4);
		meta_data.all_effect_set= readSets("/" + Config.G2D_SAVE_NAME + "/resources/effect_list.list",	SpriteSet.class,	percent, 3 , 4);
	}
	
	
	final protected void initAllXml(String persistance_manager)  throws Exception
	{
		initAllXml(persistance_manager, new AtomicReference<Float>(0f));
	}
	
	final protected void initAllXml(String persistance_manager, AtomicReference<Float> percent)  throws Exception
	{
		meta_data.item_properties		= new LinkedHashMap<Integer, ItemProperties>();
		meta_data.tunits				= new LinkedHashMap<Integer, TUnit>();
		meta_data.titems				= new LinkedHashMap<Integer, TItem>();
		meta_data.tshopitems			= new LinkedHashMap<Integer, TShopItem>();
		meta_data.tavatars				= new LinkedHashMap<Integer, TAvatar>();
		meta_data.tskills				= new LinkedHashMap<Integer, TSkill>();
		meta_data.teffects				= new LinkedHashMap<Integer, TEffect>();
		meta_data.titemlists			= new LinkedHashMap<Integer, TItemList>();
		meta_data.tshopitemlists		= new LinkedHashMap<Integer, TShopItemList>();
		meta_data.quests				= new LinkedHashMap<Integer, Quest>();
		meta_data.questgroups			= new LinkedHashMap<Integer, QuestGroup>();
		meta_data.scenes				= new LinkedHashMap<Integer, Scene>();
		meta_data.instance_zones		= new LinkedHashMap<Integer, InstanceZone>();

		meta_data.names_item_properties	= new LinkedHashMap<Integer, String>();
		meta_data.names_tunits			= new LinkedHashMap<Integer, String>();
		meta_data.names_titems			= new LinkedHashMap<Integer, String>();
		meta_data.names_tshopitems		= new LinkedHashMap<Integer, String>();
		meta_data.names_tavatars		= new LinkedHashMap<Integer, String>();
		meta_data.names_tskills			= new LinkedHashMap<Integer, String>();
		meta_data.names_teffects		= new LinkedHashMap<Integer, String>();
		meta_data.names_titemlists		= new LinkedHashMap<Integer, String>();
		meta_data.names_tshopitemlists	= new LinkedHashMap<Integer, String>();
		meta_data.names_quests			= new LinkedHashMap<Integer, String>();
		meta_data.names_questgroups		= new LinkedHashMap<Integer, String>();
		meta_data.names_scenes			= new LinkedHashMap<Integer, String>();
		meta_data.names_instance_zones	= new LinkedHashMap<Integer, String>();
		
		
		RPGObjectMap.setPersistanceManagerDriver(persistance_manager);
		
		readRPGObjects(ItemProperties.class,	
				meta_data.item_properties, meta_data.names_item_properties,	percent, 0,  13);
		readRPGObjects(TUnit.class,			
				meta_data.tunits, meta_data.names_tunits,					percent, 1,  13);
		readRPGObjects(TItem.class, 
				meta_data.titems, meta_data.names_titems,					percent, 2, 13);
		
		readRPGObjects(TShopItem.class, 
				meta_data.tshopitems, meta_data.names_tshopitems,			percent, 3, 13);
		readRPGObjects(TAvatar.class, 
				meta_data.tavatars,meta_data.names_tavatars, 				percent, 4, 13);
		readRPGObjects(TSkill.class, 
				meta_data.tskills, meta_data.names_tskills,					percent, 5, 13);
		readRPGObjects(TEffect.class,
				meta_data.teffects, meta_data.names_teffects,				percent, 6, 13);
		readRPGObjects(TItemList.class, 
				meta_data.titemlists, meta_data.names_titemlists,			percent, 7, 13);
		readRPGObjects(TShopItemList.class,
				meta_data.tshopitemlists, meta_data.names_tshopitemlists, 	percent, 8, 13);

		readRPGObjects(Quest.class,
				meta_data.quests, meta_data.names_quests,					percent, 9, 13);
		readRPGObjects(QuestGroup.class,
				meta_data.questgroups, meta_data.names_questgroups, 		percent, 10, 13);

		readRPGObjects(Scene.class,
				meta_data.scenes, meta_data.names_scenes,					percent, 11, 13);
		readRPGObjects(InstanceZone.class,
				meta_data.instance_zones, meta_data.names_instance_zones, 	percent, 12, 13);
	}
	
	final protected void initAllXmlNames()  throws Exception
	{
		initAllXmlNames(new AtomicReference<Float>(0f));
	}
	
	final protected void initAllXmlNames(AtomicReference<Float> percent)  throws Exception
	{
		meta_data.names_item_properties	= new LinkedHashMap<Integer, String>();
		meta_data.names_tunits			= new LinkedHashMap<Integer, String>();
		meta_data.names_titems			= new LinkedHashMap<Integer, String>();
		meta_data.names_tshopitems		= new LinkedHashMap<Integer, String>();
		meta_data.names_tavatars		= new LinkedHashMap<Integer, String>();
		meta_data.names_tskills			= new LinkedHashMap<Integer, String>();
		meta_data.names_teffects		= new LinkedHashMap<Integer, String>();
		meta_data.names_titemlists		= new LinkedHashMap<Integer, String>();
		meta_data.names_tshopitemlists	= new LinkedHashMap<Integer, String>();
		meta_data.names_quests			= new LinkedHashMap<Integer, String>();
		meta_data.names_questgroups		= new LinkedHashMap<Integer, String>();
		meta_data.names_scenes			= new LinkedHashMap<Integer, String>();
		meta_data.names_instance_zones	= new LinkedHashMap<Integer, String>();
		
		percent.set(0f);
		
		readRPGObjects(ItemProperties.class,	
				null, meta_data.names_item_properties,	percent, 0,  13);
		readRPGObjects(TUnit.class,			
				null, meta_data.names_tunits,			percent, 1,  13);
		readRPGObjects(TItem.class, 
				null, meta_data.names_titems,			percent, 2, 13);
		
		readRPGObjects(TShopItem.class, 
				null, meta_data.names_tshopitems,		percent, 3, 13);
		readRPGObjects(TAvatar.class, 
				null, meta_data.names_tavatars, 		percent, 4, 13);
		readRPGObjects(TSkill.class, 
				null, meta_data.names_tskills,			percent, 5, 13);
		readRPGObjects(TEffect.class,
				null, meta_data.names_teffects,			percent, 6, 13);
		readRPGObjects(TItemList.class, 
				null, meta_data.names_titemlists,		percent, 7, 13);
		readRPGObjects(TShopItemList.class,
				null, meta_data.names_tshopitemlists, 	percent, 8, 13);

		readRPGObjects(Quest.class,
				null, meta_data.names_quests,			percent, 9, 13);
		readRPGObjects(QuestGroup.class,
				null, meta_data.names_questgroups, 		percent, 10, 13);

		readRPGObjects(Scene.class,
				null, meta_data.names_scenes,			percent, 11, 13);
		readRPGObjects(InstanceZone.class,
				null, meta_data.names_instance_zones, 	percent, 12, 13);
	}
	
	public static class WrapperPercent
	{
		final AtomicReference<Float> 				src;
		final ArrayList<AtomicReference<Float>> 	subs;
		
		public WrapperPercent(AtomicReference<Float> src, int size) {
			this.src	= src;
			this.subs	= new ArrayList<AtomicReference<Float>>(size);
			for (int i=0; i<size; i++) {
				this.subs.add(new AtomicReference<Float>());
			}
		}
		
		private void set(float percent) {
			
		}
	}
	
	final protected void initIcons()
	{
		meta_data.all_icons = readIcons("/" + Config.G2D_SAVE_NAME + "/icons/icon.list" );
		this.all_icons = new LinkedHashMap<String, AtomicReference<BufferedImage>>();
		for (String name : meta_data.all_icons) {
			all_icons.put(name, new AtomicReference<BufferedImage>());
		}
	}
	
	final protected void initSounds()
	{
		meta_data.all_sounds	= readSounds("/" + Config.G2D_SAVE_NAME + "/sounds/sound.list" );
		this.all_sounds = new LinkedHashMap<String, AtomicReference<ISound>>();
		for (String name : meta_data.all_sounds) {
			all_sounds.put(name, new AtomicReference<ISound>());
		}
	}
	
	final protected void initNpcTalks() 
	{
		meta_data.all_npc_talks = readNpcTalks("/" + Config.G2D_SAVE_NAME + "/talks/talks.list" );
		this.all_npc_talks = new LinkedHashMap<String, AtomicReference<String>>();
		for (String name : meta_data.all_npc_talks) {
			all_npc_talks.put(name, new AtomicReference<String>());
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	
	final protected <T extends ResourceSet<?>> LinkedHashMap<String, T> readSets(
			String file, 
			Class<T> type, 
			AtomicReference<Float> percent, int step, int total
			) throws Exception
	{		
		float init = (float)step / (float)total;
		percent.set(init);

		System.out.println("list resource : " + file);
		
		LinkedHashMap<String, T> table = new LinkedHashMap<String, T>();
		
		String[] res_list = readAllLine(file, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split = CUtil.splitString(res_list[i].trim(), ";");
			String res_path		= split[0];
			String cpj_name 	= split[1];
			String obj_name 	= split[2];
			
			T set = null;
			if (type.equals(SceneSet.class)) {
				set = type.cast(new SceneSet(cpj_name, obj_name, res_path));
			} else if (type.equals(SpriteSet.class)) {
				set = type.cast(new SpriteSet(cpj_name, obj_name, res_path));
			}
			if (PRINT_VERBOS)
			System.out.println("\tget " + type.getSimpleName() + " : " + cpj_name + "(" + obj_name + ")");
			
			table.put(set.getID(), set);
			
			percent.set(init  + ((float)i / (float)res_list.length) / total);
		}
		
		System.out.println("size : " + table.size());
		
		return table;
	}

	final protected LinkedHashSet<String> readIcons(String icon_list)
	{
		System.out.println("list icons : " + icon_list);

		LinkedHashSet<String> table = new LinkedHashSet<String>();
		
		String[] res_list = readAllLine(icon_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			String[] split 	= CUtil.splitString(res_list[i].trim(), ",");
			String icon_id 	= split[0];
			String icon_w 	= split[1];
			String icon_h 	= split[2];
			table.add(icon_id);
			
			if (PRINT_VERBOS)
			System.out.println("\tget icon : " + icon_id + "(" + icon_w + "x" + icon_h + ")");
		}
		
		System.out.println("size : " + table.size());

		return table;
	}


	final protected LinkedHashSet<String> readSounds(String sound_list)
	{
		System.out.println("list sounds : " + sound_list);

		LinkedHashSet<String> table = new LinkedHashSet<String>();
		
		String[] res_list = readAllLine(sound_list, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.add(res_list[i].trim());
			if (PRINT_VERBOS)
			System.out.println("\tget sound : " + res_list[i]);
		}
		
		System.out.println("list sounds : " + table.size());

		return table;
	}
	
	final protected LinkedHashSet<String> readNpcTalks(String talklist)
	{
		System.out.println("list npc talks : " + talklist);

		LinkedHashSet<String> table = new LinkedHashSet<String>();
		
		String[] res_list = readAllLine(talklist, "UTF-8");
		
		for (int i=0; i<res_list.length; i++)
		{
			table.add(res_list[i].trim());
			if (PRINT_VERBOS)
			System.out.println("\tget npc talk : " + res_list[i]);
		}
		
		System.out.println("list npc talks : " + table.size());

		return table;
	}

//	--------------------------------------------------------------------------------------------------------------------------------------
	
	public <T extends RPGObject>  String toListFile(Class<T> type) 
	{
		if (type.equals(ItemProperties.class)) {
			return "/" + Config.G2D_SAVE_NAME + "/item_properties/item_properties.list";
		}
		else if (type.equals(Quest.class)) {
			return "/" + Config.G2D_SAVE_NAME + "/quests/quest.list";					
		}
		else if (type.equals(QuestGroup.class)) {
			return "/" + Config.G2D_SAVE_NAME + "/questgroups/questgroups.list";					
		}
		else if (type.equals(Scene.class)) {
			return "/" + Config.G2D_SAVE_NAME + "/scenes/scene.list";					
		}
		else if (type.equals(InstanceZone.class)) {
			return "/" + Config.G2D_SAVE_NAME + "/instance_zones/zones.list";					
		}
		else {
			String type_name = type.getSimpleName().toLowerCase();
			return "/" + Config.G2D_SAVE_NAME + "/objects/" + type_name + ".obj" + "/" + type_name + ".list";
		}
	}
	
//	public <T extends RPGObject>  String toNameListFile(Class<T> type) 
//	{
//		if (type.equals(ItemProperties.class)) {
//			return "/" + Config.G2D_SAVE_NAME + "/item_properties/name_item_properties.list";
//		}
//		else if (type.equals(Quest.class)) {
//			return "/" + Config.G2D_SAVE_NAME + "/quests/name_quest.list";					
//		}
//		else if (type.equals(QuestGroup.class)) {
//			return "/" + Config.G2D_SAVE_NAME + "/questgroups/name_questgroups.list";					
//		}
//		else if (type.equals(Scene.class)) {
//			return "/" + Config.G2D_SAVE_NAME + "/scenes/name_scene.list";					
//		}
//		else if (type.equals(InstanceZone.class)) {
//			return "/" + Config.G2D_SAVE_NAME + "/instance_zones/name_zones.list";					
//		}
//		else {
//			String type_name = type.getSimpleName().toLowerCase();
//			return "/" + Config.G2D_SAVE_NAME + "/objects/" + type_name + ".obj" + "/name_" + type_name + ".list";
//		}
//	}
//	
//
//	final protected <T extends RPGObject>  readRPGObjectNames(Class<T> type) throws Exception
//	{
//		String list_file = toListFile(type);
//		
//		System.out.println("list rpg object names : " + list_file);
//
//		LinkedHashMap<Integer, String> table = new LinkedHashMap<Integer, String>();
//		
//		String[] res_list = readAllLine(list_file, "UTF-8");
//		
//		for (String line : res_list) {
//			line = line.trim();
//			int new_i = line.lastIndexOf("?");
//			line = line.substring(new_i+1);
//			String[] fields = line.split("&");
//			for (String f : fields) {
//				if (f.startsWith("name=")) {
//					table.put(Integer.parseInt(id), line.substring(br+1));
//				}
//			}
////			
////			int bl = line.indexOf("(");
////			int br = line.indexOf(")");
////			String id = line.substring(bl+1, br);
////			if (br < line.length()) {
////				table.put(Integer.parseInt(id), line.substring(br+1));
////			} else {
////				table.put(Integer.parseInt(id), "");
////			}
//		}
//		
//		return table;
//	}
	
	
	final protected <T extends RPGObject> void readRPGObjects(
			Class<T> type, LinkedHashMap<Integer, T> table, 
			LinkedHashMap<Integer, String> name_table,
			AtomicReference<Float> percent, 
			int step, int total) throws Exception
	{	
		float init = (float)step / (float)total;
		percent.set(init);
		
		String list_file = toListFile(type);
		
		System.out.println("list rpg objects : " + list_file);
		
		String tdir = list_file;
		tdir		= tdir.replace('\\', '/');
		tdir		= tdir.substring(0, tdir.lastIndexOf("/"));
		
		String[] res_list = readAllLine(list_file, "UTF-8");
		
		Pattern f_id 	= Pattern.compile("\\d+\\.xml");

		int count = 0;
		for (String line : res_list) 
		{
			line = line.trim();
			int new_i 	= line.lastIndexOf("?");
			if (new_i >=0) 
			{
				String 	path 	= line.substring(0, new_i);
				String 	query 	= line.substring(new_i + 1);
				Matcher	m_id	= f_id.matcher(path); m_id.find();
				String	id		= path.substring(m_id.start(), m_id.end()-4);

				if (name_table != null)
				{
					String[] fields = query.split("&");
					for (String f : fields) {
						if (f.startsWith("name=")) {
							name_table.put(Integer.parseInt(id), f.substring(5));
//							System.out.println(type.getSimpleName() + " : " + id + " - " + f);
							break;
						}
					}
				}
				
				if (table != null)
				{
					String display_list = path;
					int last_split = path.lastIndexOf("/");
					if (last_split >= 0) {
						path = path.substring(last_split + 1);
					}
					
					String xml_file = tdir +"/"+ path;
					
					T set = RPGObjectMap.readNode(new ByteArrayInputStream(getResource(xml_file)), xml_file, type);
					set.loadTreePath(this, display_list);
					if (PRINT_VERBOS)
					System.out.println("\tget " + type.getSimpleName() + " : " + set + "(" + set.id + ")");
		
					table.put(Integer.parseInt(set.id), set);
				}
			}

			percent.set(init  + ((float)count / (float)res_list.length) / total);
			count ++;
		}
		
		System.out.println("size : " + count);
	}

	
//	final public <T extends RPGObject> T readRPGObject(String xml_file, Class<T> type) 
//	{
//		T set = RPGObjectMap.readNode(new ByteArrayInputStream(getResource(xml_file)), xml_file, type);			
//		if (PRINT_VERBOS)
//		System.out.println("readRPGObject : " + type.getSimpleName() + " : " + set + "(" + set.id + ")");
//		return set;
//	}

//	--------------------------------------------------------------------------------------------------------------------
//	SetResources
//	--------------------------------------------------------------------------------------------------------------------

	private static void SetResources_____________________________________________________(){}
	
	public SceneSet getSceneSet(String cpj_name, String obj_name) throws Exception{
		SceneSet set = meta_data.all_scene_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}

	public SpriteSet getActorSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = meta_data.all_actor_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public SpriteSet getAvatarSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = meta_data.all_avatar_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public SpriteSet getEffectSet(String cpj_name, String obj_name) throws Exception{
		SpriteSet set = meta_data.all_effect_set.get(ResourceSet.toID(cpj_name, obj_name));
		set.loadSetObject(this);
		return set;
	}
	
	public Vector<SceneSet> getAllScenes() {
		return new Vector<SceneSet>(meta_data.all_scene_set.values());
	}
	
	public Vector<SpriteSet> getAllActors() {
		return new Vector<SpriteSet>(meta_data.all_actor_set.values());
	}
	
	public Vector<SpriteSet> getAllAvatars() {
		return new Vector<SpriteSet>(meta_data.all_avatar_set.values());
	}
	
	public Vector<SpriteSet> getAllEffects() {
		return new Vector<SpriteSet>(meta_data.all_effect_set.values());
	}

	public StreamTiles getEffectImages(String cpj_project_name, String cpj_sprite_name) {
		try
		{
			SpriteSet 							effect_set 	= getEffectSet(cpj_project_name, cpj_sprite_name);
			CellSetResource 					resource 	= effect_set.getSetResource(this);
			com.cell.gameedit.object.SpriteSet 	spr_set 	= effect_set.getSetObject(this);
			StreamTiles							images		= resource.getImages(spr_set.ImagesName);
			return images;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage getEffectImage(String cpj_project_name, String cpj_sprite_name, int index)
	{
		try
		{
			StreamTiles images = getEffectImages(cpj_project_name, cpj_sprite_name);
			IImage image = images.getImage(index);
			return Tools.createImage(image);
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public CSprite getEffectSprite(String cpj_project_name, String cpj_sprite_name) {
		try
		{
			SpriteSet							effect_set	= getEffectSet(cpj_project_name, cpj_sprite_name);
			CellSetResource						resource 	= effect_set.getSetResource(this);
			com.cell.gameedit.object.SpriteSet 	spr_set 	= effect_set.getSetObject(this);
			CSprite								sprite		= resource.getSprite(spr_set);
			return sprite;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	
//	--------------------------------------------------------------------------------------------------------------------
//	Templates
//	--------------------------------------------------------------------------------------------------------------------
	private static void Templates_____________________________________________________(){}
	
	public TUnit getTUnit(int id) {
		return meta_data.tunits.get(id);
	}
	public TItem getTItem(int id) {
		return meta_data.titems.get(id);
	}
	public TShopItem getTShopItem(int id) {
		return meta_data.tshopitems.get(id);
	}
	public TAvatar getTAvatar(int id) {
		return meta_data.tavatars.get(id);
	}
	public TSkill getTSkill(int id) {
		return meta_data.tskills.get(id);
	}
	public TEffect getTEffect(int id) {
		return meta_data.teffects.get(id);
	}
	public TItemList getItemList(int id) {
		return meta_data.titemlists.get(id);
	}
	public TShopItemList getShopItemList(int id) {
		return meta_data.tshopitemlists.get(id);
	}
	
	
	public String getTUnitName(int id) {
		return meta_data.names_tunits.get(id);
	}
	public String getTItemName(int id) {
		return meta_data.names_titems.get(id);
	}
	public String getTShopItemName(int id) {
		return meta_data.names_tshopitems.get(id);
	}
	public String getTAvatarName(int id) {
		return meta_data.names_tavatars.get(id);
	}
	public String getTSkillName(int id) {
		return meta_data.names_tskills.get(id);
	}
	public String getTEffectName(int id) {
		return meta_data.names_teffects.get(id);
	}
	public String getTItemListName(int id) {
		return meta_data.names_titemlists.get(id);
	}
	public String getTShopItemListName(int id) {
		return meta_data.names_tshopitemlists.get(id);
	}
	
	public Vector<TItemList> getAllItemList() {
		return new Vector<TItemList>(meta_data.titemlists.values());
	}
	public Vector<TShopItemList> getAllShopItemList() {
		return new Vector<TShopItemList>(meta_data.tshopitemlists.values());
	}

	public Vector<TAvatar> getAllTAvatar() {
		return new Vector<TAvatar>(meta_data.tavatars.values());
	}
//	--------------------------------------------------------------------------------------------------------------------
//	ItemProperties
//	--------------------------------------------------------------------------------------------------------------------
	private static void ItemProperties_____________________________________________________(){}
	
	public LinkedHashMap<Integer, ItemProperties> getAllItemProperties() {
		return new LinkedHashMap<Integer, ItemProperties>(meta_data.item_properties);
	}

	public ItemProperties getItemProperties(int id) {
		return meta_data.item_properties.get(id);
	}

	public String getItemPropertiesName(int id) {
		return meta_data.names_item_properties.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Quests
//	--------------------------------------------------------------------------------------------------------------------
	private static void Quests_____________________________________________________(){}
	
	public LinkedHashMap<Integer, Quest> getAllQuests(){
		return new LinkedHashMap<Integer, Quest>(meta_data.quests);
	}

	public Quest getQuest(int quest_id) {
		return meta_data.quests.get(quest_id);
	}
	
	public LinkedHashMap<Integer, QuestGroup> getAllQuestGroups() {
		return new LinkedHashMap<Integer, QuestGroup>(meta_data.questgroups);
	}
	
	public QuestGroup getQuestGroup(int quest_group_id) {
		return meta_data.questgroups.get(quest_group_id);
	}

	public String getQuestName(int id) {
		return meta_data.names_quests.get(id);
	}
	
	public String getQuestGroupName(int id) {
		return meta_data.names_questgroups.get(id);
	}
	
//	--------------------------------------------------------------------------------------------------------------------
//	Scenes
//	--------------------------------------------------------------------------------------------------------------------
	private static void Scenes_____________________________________________________(){}
	
	public Scene getRPGScene(int id) {
		return meta_data.scenes.get(id);
	}

	public String getRPGSceneName(int id) {
		return meta_data.names_scenes.get(id);
	}
	
	public LinkedHashMap<Integer, Scene> getAllRPGScenes() {
		return new LinkedHashMap<Integer, Scene>(meta_data.scenes);
	}
	
	public SceneGraph createSceneGraph() {
		return new SceneGraph(meta_data.scenes.values());
	}
	
	
	

	public InstanceZone getInstanceZone(int id) {
		return meta_data.instance_zones.get(id);
	}

	public String getInstanceZoneName(int id) {
		return meta_data.names_instance_zones.get(id);
	}
	
	public LinkedHashMap<Integer, InstanceZone> getAllInstanceZone() {
		return new LinkedHashMap<Integer, InstanceZone>(meta_data.instance_zones);
	}
	
	
	
//	--------------------------------------------------------------------------------------------------------------------
//	EditResources
//	--------------------------------------------------------------------------------------------------------------------
	private static void EditResources_____________________________________________________(){}
	
	public AtomicReference<BufferedImage> getIcon(String index) {
		return all_icons.get(index);
	}

	public ISound getSound(String index) {
		return all_sounds.get(index).get();
	}

	public String getNpcTalk(String index) {
		return all_npc_talks.get(index).get();
	}

	

	protected void check() throws Exception
	{
		ArrayList<String> errors = new ArrayList<String>();
		
		for (TUnit unit : meta_data.tunits.values()) {
			checkQuest(unit, errors,
					" unit="+unit.getName() + "("+unit.getIntID()+")");
		}
		
		for (Scene s : getAllRPGScenes().values()) {
			for (SceneUnit su : s.scene_units) {
				checkQuest(su, errors,
						" scene="+s.getName()+"("+s.getIntID()+")" + 
						" unit="+su.getName());
			}
		}
		
		if (!errors.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (String err : errors) {
				sb.append(err + "\n");
			}
			throw new Exception("resource check failed !\n" + sb);
		}
	}
	
	protected void checkQuest(RPGObject obj, ArrayList<String> errors, String obj_info) 
	{
		for (QuestPublisher qp : obj.getAbilities(QuestPublisher.class)) {
			if (getQuest(qp.quest_id) == null) {
				errors.add("quest not found : quest_id=" + qp.quest_id + obj_info);
			}
		}
		for (QuestAccepter qa : obj.getAbilities(QuestAccepter.class)) {
			if (getQuest(qa.quest_id) == null) {
				errors.add("quest not found : quest_id=" + qa.quest_id + obj_info);
			}
		}
	}
	
}
