package com.g2d.studio.scene.editor;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gameedit.SetResource;
import com.cell.gameedit.object.WorldSet.SpriteObject;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.Region;
import com.cell.rpg.scene.SceneAbilityManager;
import com.cell.rpg.scene.SceneSprite;
import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.sound.util.StaticSoundPlayer;
import com.cell.util.Pair;

import com.g2d.BufferedImage;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.awt.util.AbstractFrame;
import com.g2d.awt.util.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSprite;

import com.g2d.cell.game.Scene;
import com.g2d.cell.game.ui.ScenePanel;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.PropertyEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.geom.AffineTransform;
import com.g2d.geom.Point;
import com.g2d.geom.Point2D;
import com.g2d.geom.Rectangle;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.AbilityAdapter;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.scene.script.TriggerGeneratorPanel;
import com.g2d.studio.scene.units.SceneActor;
import com.g2d.studio.scene.units.SceneEffect;
import com.g2d.studio.scene.units.SceneImmutable;
import com.g2d.studio.scene.units.ScenePoint;
import com.g2d.studio.scene.units.SceneRegion;
import com.g2d.studio.scene.units.SceneUnitTag;
import com.g2d.studio.sound.SoundFile;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.util.Drawing;

@SuppressWarnings("serial")
public class SceneEditor extends AbstractFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 1L;

	private static float	default_mask_alpha 	= 0.5f;
	private static int		default_mask_color	= 0x808080;
//	--------------------------------------------------------------------------------------------------------------
//	game

	final SceneNode			scene_node;
	final CPJWorld			scene_world;
	final StudioResource	scene_resource;
	
	DisplayObjectPanel		display_object_panel;
	SceneStage				scene_stage;
	ScenePanel				scene_panel;
	SceneContainer			scene_container;
	SceneMiniMap			scene_mini_map;

//	--------------------------------------------------------------------------------------------------------------
//	ui
	
	private G2DWindowToolBar	tool_bar;
	private JToggleButton		tool_selector	= new JToggleButton	(Tools.createIcon(Res.icons_bar[0]), true);
	private JToggleButton		tool_addactor	= new JToggleButton	(Tools.createIcon(Res.icons_bar[8]));
	private JToggleButton		tool_show_grid	= new JToggleButton	(Tools.createIcon(Res.icon_grid));	
	private JButton				tool_edit_prop	= new JButton		(Tools.createIcon(Res.icons_bar[1]));
	private JButton				tool_triggers	= new JButton		(Tools.createIcon(Res.icon_action));
	private JToggleButton		tool_play_bgm	= new JToggleButton	(Tools.createIcon(Res.icons_bar[3]));
	private JButton				tool_mask_alpha	= new JButton		("MA");
	private JButton				tool_mask_color	= new JButton		("MC");
	private JToggleButton		tool_show_sc	= new JToggleButton	("a");

	private JTabbedPane			unit_page;
	private JToolBar			status_bar		= new JToolBar();
	private JLabel				status_rule		= new JLabel("尺子");
//	private SceneUnitTagAdapter<SceneActor>		page_actors;
//	private SceneUnitTagAdapter<SceneRegion>	page_regions;
//	private SceneUnitTagAdapter<ScenePoint>		page_points;
//	private SceneUnitTagAdapter<SceneImmutable>	page_immutables;

//	--------------------------------------------------------------------------------------------------------------
//	transient
	private StaticSoundPlayer v_bgm_sound_player;
	private SceneUnitTag<?> v_selected_unit;
	
//	--------------------------------------------------------------------------------------------------------------
	
	public SceneEditor(SceneNode scene)
	{
		super.setSize(AbstractFrame.getScreenWidth()-Studio.getInstance().getWidth(), Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setIconImage(Res.icon_edit);
		super.setTitle("场景 : " + scene.getName() + " (" + scene.getID() + ") : " + 
				scene.getData().getAbilitiesCount()+"能力");
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.addWindowListener(this);
		
		this.scene_node			= scene;
		this.scene_world		= scene_node.getWorldDisplay();
		this.scene_resource		= scene_world.getCPJFile().getSetResource();
		// tool bar
		{
			tool_bar = new G2DWindowToolBar(this);
			
			
			tool_edit_prop.setToolTipText("查看场景属性");
			tool_edit_prop.addActionListener(this);
			tool_bar.add(tool_edit_prop);
			
			tool_triggers.setToolTipText("场景触发事件");
			tool_triggers.addActionListener(this);
			tool_bar.add(tool_triggers);
			
			tool_bar.addSeparator();
			
			{
				tool_selector.setToolTipText("选择");
				tool_addactor.setToolTipText("添加");
				tool_addactor.addActionListener(this);
				tool_bar.add(tool_selector);
				tool_bar.add(tool_addactor);
				ButtonGroup button_group = new ButtonGroup();
				button_group.add(tool_selector);
				button_group.add(tool_addactor);
				tool_bar.addSeparator();
			}
			{
				tool_show_grid.setToolTipText("显示碰撞");
				tool_show_grid.addActionListener(this);
				tool_bar.add(tool_show_grid);
				
				tool_show_sc.setToolTipText("显示能力数");
				tool_show_sc.setSelected(true);
				tool_bar.add(tool_show_sc);
				
				tool_mask_alpha.setToolTipText("改变MASK透明度");
				tool_mask_color.setToolTipText("改变MASK颜色");
				tool_mask_alpha.addActionListener(this);
				tool_mask_color.addActionListener(this);
				tool_bar.add(tool_mask_alpha);
				tool_bar.add(tool_mask_color);
				
				tool_bar.addSeparator();
			}
			{
				tool_play_bgm.setToolTipText("播放BGM");
				tool_play_bgm.addActionListener(this);
				tool_bar.add(tool_play_bgm);
			}

			
		}
		this.add(tool_bar, BorderLayout.NORTH);
		
		
		// g2d stage
		{			
			scene_stage				= new SceneStage();
			scene_container			= new SceneContainer();
			scene_panel				= new ScenePanel(scene_container);
			scene_mini_map			= new SceneMiniMap();
			
			display_object_panel	= new DisplayObjectPanel(scene_stage);
			load();
		}
		
		JSplitPane split_h = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// left
		{
			JSplitPane split_v = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			// top
			{
				split_v.setTopComponent(scene_mini_map);
			} 
			// bottom
			{
				unit_page		= new JTabbedPane();
				unit_page.addTab("单位",		new SceneActorAdapter());
				unit_page.addTab("不可破坏", new SceneImmutableAdapter());
				unit_page.addTab("特效", 	new SceneEffectAdapter());
				unit_page.addTab("区域", 	new SceneRegionAdapter());
				unit_page.addTab("路点", 	new ScenePointAdapter());
				split_v.setBottomComponent(unit_page);
			}
			split_h.setLeftComponent(split_v);
		}
		// right
		{
			split_h.setRightComponent(display_object_panel);
		}
		
		this.add(split_h, BorderLayout.CENTER); 
		
		{
			status_bar.add(status_rule);
		}
		this.add(status_bar, BorderLayout.SOUTH);
		
		refreshAll();
		

		System.out.println("Open Scene Editor : " + scene_node.getName());
	}
	
	public SceneNode getSceneNode() {
		return scene_node;
	}

	@SuppressWarnings("unchecked")
	private void load()
	{		
		if (scene_node.getData().scene_units!=null) {
			for (SceneUnit unit : scene_node.getData().scene_units) {
				SceneUnitTag<?> unit_tag = null;
				try{
					if (unit instanceof Actor) {
						unit_tag = new SceneActor(this, (Actor)unit);
					} 
					else if (unit instanceof Region) {
						unit_tag = new SceneRegion(this, (Region)unit);
					} 
					else if (unit instanceof com.cell.rpg.scene.Point) {
						unit_tag = new ScenePoint(this, (com.cell.rpg.scene.Point)unit);
					}
					else if (unit instanceof com.cell.rpg.scene.Immutable) {
						unit_tag = new SceneImmutable(this, (com.cell.rpg.scene.Immutable)unit);
					}
					else if (unit instanceof com.cell.rpg.scene.Effect) {
						unit_tag = new SceneEffect(this, (com.cell.rpg.scene.Effect)unit);
					}
					if (unit_tag != null) {
						scene_container.getWorld().addChild(unit_tag.getGameUnit());
					}
				} catch (Throwable err) {
					err.printStackTrace();
					System.err.println("unit " +
							": id=" + unit.id +
							": name=" + unit.name+
							"");
					if (unit instanceof Actor) {
						Actor actor = (Actor)unit;
						System.err.println("actor " +
								": template_unit_id=" + actor.template_unit_id +
								"");
					}
					if (unit instanceof com.cell.rpg.scene.Immutable) {
						com.cell.rpg.scene.Immutable actor = (com.cell.rpg.scene.Immutable)unit;
						System.err.println("immutable " +
								": display_node=" + actor.getDisplayNode() +
								"");
					}
				}
			}
		}

		Vector<SceneUnitTag> list = scene_container.getWorld().getChildsSubClass(SceneUnitTag.class);
		for (SceneUnitTag tag : list) {
			tag.onReadComplete(list);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Vector<SceneUnit> getList() {
		Vector<SceneUnitTag> list = scene_container.getWorld().getChildsSubClass(SceneUnitTag.class);
		for (SceneUnitTag tag : list) {
			tag.onWriteReady(list);
		}
		TreeSet<SceneUnit> ret = new TreeSet<SceneUnit>(new Comparator<SceneUnit>() {
			public int compare(SceneUnit o1, SceneUnit o2) {
				return CUtil.getStringCompare().compare(o1.id, o2.id);
			}
		});
		for (SceneUnitTag tag : list) {
			try {
				ret.add(tag.onWrite());
			} catch (Throwable err) {
				err.printStackTrace();
			}
		}
		scene_node.getData().scene_units.clear();
		scene_node.getData().scene_units.addAll(ret);
		return new Vector<SceneUnit>(ret);
	}
	
	
	private void save()
	{
		getList();
		try {
			if (scene_node.getWorldDisplay() != null &&
				scene_node.getWorldDisplay().scene_snapshoot == null) {
				com.g2d.BufferedImage icon = scene_container.getWorld().createMiniMap(
						scene_container.getWorld().getWidth(),
						scene_container.getWorld().getHeight());
				scene_node.saveSnapshot(Tools.unwrap(icon));
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	

	@SuppressWarnings("unchecked")
	public ArrayList<SceneUnit> getRuntimeUnits() {
		Vector<SceneUnitTag> list = scene_container.getWorld()
				.getChildsSubClass(SceneUnitTag.class);
		ArrayList<SceneUnit> ret = new ArrayList<SceneUnit>();
		for (SceneUnitTag tag : list) {
			ret.add(tag.getUnit());
		}
		return ret;
	} 
	
//	-----------------------------------------------------------------------------------------------------------------------------

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			display_object_panel.start();
			scene_resource.initAllStreamImages();
		} else {
			clean();
		}
	}
	
	private void clean() {
		display_object_panel.stop();
		for (SceneUnitTag<?> t : scene_container.getWorld().getChildsSubClass(SceneUnitTag.class)) {
			t.onHideFrom();
		}
		scene_resource.destoryAllStreamImages();
		if (scene_mini_map != null){
			scene_mini_map.killSnapshot();
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {
		getList();
		clean();
		System.out.println("SceneEditor closed !");
	}
	@Override
	public void windowClosing(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		super.setTitle("场景 : " + scene_node.getName() + " (" + scene_node.getID() + ") : " + 
				scene_node.getData().getAbilitiesCount()+"能力");
		
		if (e.getSource() == tool_bar.save) {
			save();
			Studio.getInstance().getSceneManager().saveScene(scene_node);
		}
		else if (e.getSource() == tool_triggers) {
			SceneTriggers st = new SceneTriggers(this);
			st.setVisible(true);
		}
		else if (e.getSource() == tool_edit_prop) {
			CellEditAdapter<?>[] adapters = new CellEditAdapter[] {
					new SceneAbilityAdapters.SceneBGMAdapter(),
			};
			SceneAbilityManager sam = SceneAbilityManager.getManager();
			if (sam != null) {
				Set<CellEditAdapter<?>>	sa = new LinkedHashSet<CellEditAdapter<?>>();
				if (sam.getEditAdapters() != null) {
					for (PropertyEditor<?> p : sam.getEditAdapters()) {
						if (p instanceof CellEditAdapter<?>) {
							sa.add((CellEditAdapter<?>)p);
						}
					}
				}
				if (sa != null) {
					CellEditAdapter<?> append[] = new CellEditAdapter<?>[sa.size()];
					append = sa.toArray(append);
					Vector<CellEditAdapter<?>> vc =  CUtil.linkv(adapters, append);
					adapters = vc.toArray(new CellEditAdapter[vc.size()]);
				}
			}
			DisplayObjectEditor<SceneContainer> editor = new DisplayObjectEditor<SceneContainer>(
					scene_container,
					new RPGObjectPanel(scene_node.getData(), adapters),
					new AbilityPanel(scene_node.getData(), adapters),
					new TriggerGeneratorPanel(
							scene_node.getData().getBindedTriggers(),
							scene_node.getData().getTriggersPackage(),
							com.cell.rpg.scene.script.entity.Scene.class)
			);
			editor.setCenter();
			editor.setVisible(true);
		}
		else if (e.getSource() == tool_mask_alpha) {
			String ret = JOptionPane.showInputDialog(this, 
					"输入0～1的小数", 
					default_mask_alpha + "");
			try {
				if (ret != null) {
					float v = Float.parseFloat(ret.trim());
					if (v >= 0 && v <= 1f) {
						default_mask_alpha = v;
					} else {
						JOptionPane.showMessageDialog(this, "输入错误!");
					}
				}
			} catch (Throwable ex) {
				JOptionPane.showMessageDialog(this, "输入错误!");
			}
		}
		else if (e.getSource() == tool_mask_color) {
			String ret = JOptionPane.showInputDialog(this, 
					"输入16进制颜色值，比如 (红色)\"ff0000\"", 
					Integer.toString(default_mask_color, 16));
			try {
				if (ret != null) {
					int v = Integer.parseInt(ret.trim(), 16);
					default_mask_color = v;
				}
			} catch (Throwable ex) {
				JOptionPane.showMessageDialog(this, "输入错误!");
			}
		}
		else if (e.getSource() == tool_addactor) {
			if (isToolAdd() && getSelectedPage().isShowSelectUnitTool()){
				SelectUnitTool.getUnitTool().setVisible(true);
			}
		}
		else if (e.getSource() == tool_show_grid) {
			scene_container.getWorld().setDebug(tool_show_grid.isSelected());
		}
		else if (e.getSource() == tool_play_bgm) {
			if (v_bgm_sound_player!=null) {
				v_bgm_sound_player.stop();
				v_bgm_sound_player.dispose();
				v_bgm_sound_player = null;
			}
			if (tool_play_bgm.isSelected()) {
				if (scene_node.getData().bgm_sound_name!=null) {
					SoundFile sound = Studio.getInstance().getSoundManager().getSound(scene_node.getData().bgm_sound_name);
					if (sound != null) {
						v_bgm_sound_player = sound.createSoundPlayer();
						v_bgm_sound_player.play(true);
					}
				}
			}
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	public Scene getGameScene()
	{
		return scene_container;
	}
	
	public SceneUnitTag<?> getSelectedUnit() {
		return v_selected_unit;
	}
	
	public void selectUnit(SceneUnitTag<?> u, boolean updatelist){
		v_selected_unit = u;
		if (updatelist) {
			for (int p=0; p<unit_page.getTabCount(); p++) {
				SceneUnitTagAdapter<?,?> ad = (SceneUnitTagAdapter<?,?>)unit_page.getComponentAt(p);
				ad.setSelecte(u.getGameUnit());
			}
		}
	}
	
	public <T extends Unit> T getUnit(Class<T> type, Object id) {
		Vector<T> list = scene_container.getWorld().getChildsSubClass(type);
		for (T t : list) {
			if (t.getID().equals(id)) {
				return t;
			}
		}
		return null;
	}

	public void addTagUnit(SceneUnitTag<?> unit) {
		try{
			scene_container.getWorld().addChild(unit.getGameUnit());
		}catch(Exception err){
			err.printStackTrace();
		}
		refreshAll();
	}
	
	public void removeUnit(SceneUnitTag<?> unit) {
		try{
			scene_container.getWorld().removeChild(unit.getGameUnit());
		}catch(Exception err){
			err.printStackTrace();
		}
		refreshAll();
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------

	public void refreshAll() 
	{
		for (int p = 0; p < unit_page.getTabCount(); p++) {
			SceneUnitTagAdapter<?,?> ad = (SceneUnitTagAdapter<?,?>)unit_page.getComponentAt(p);
			ad.repaint(500);
		}
		scene_mini_map.repaint(500);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------------------------
	
	public boolean isToolSelect(){
		return tool_selector.isSelected();
	}
	
	public boolean isToolAdd(){
		return tool_addactor.isSelected();
	}
	
	public SceneUnitTagAdapter<?,?> getSelectedPage() {
		return (SceneUnitTagAdapter<?,?>)unit_page.getSelectedComponent();
	}

	public boolean isShowScript() {
		return tool_show_sc.isSelected();
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneStage extends Stage
	{
		public SceneStage(){}
		
		public void added(DisplayObjectContainer parent) {			
			addChild(scene_panel);
			getRoot().setFPS(Config.DEFAULT_FPS);
		}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {
		}

		public void update() {
			scene_panel.setSize(getWidth(), getHeight());
			if (getRoot().isMouseDown(MouseEvent.BUTTON1)) {
				StringBuilder sb = new StringBuilder();
				sb.append("屏幕:("+getMouseX()+","+getMouseY()+") ");
				sb.append("场景:("+scene_container.getWorld().getMouseX()+","+scene_container.getWorld().getMouseY()+") ");
				if (getSelectedUnit()!=null) {
					sb.append("单位:("+getSelectedUnit().getGameUnit().getX()+","+getSelectedUnit().getGameUnit().getY()+") ");
				}
				status_rule.setText(sb.toString());
			}
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------

	class SceneContainer extends Scene implements CUtil.ICompare<SceneUnitTag<?>, SceneUnitTag<?>>
	{
		Point			add_region_sp	= null;
		Point			add_region_dp	= new Point();
		
		Point2D.Double	pre_right_pos;
		Point2D.Double	pre_right_camera_pos;

		ScenePoint 		pre_added_point;
		
		public SceneContainer() 
		{
			this.setWorld(new EatWorldMap(scene_resource, scene_world.name));
			this.enable_input				= true;
			this.getWorld().runtime_sort 	= false;
		}

		@Override
		protected boolean enable_click_focus() {
			return true;
		}
		
		@Override
		protected void onCameraChanged(double cx, double cy, double cw, double ch) {
			if (scene_mini_map != null) {			
				scene_mini_map.repaint(500);
			}
		}
		
		public int compare(SceneUnitTag<?> a, SceneUnitTag<?> b) {
			return CUtil.getStringCompare().compare(a.getGameUnit().getID()+"", b.getGameUnit().getID()+"");
		}
		
		@Override
		protected void renderAfter(Graphics2D g)
		{
			g.pushTransform();
			// 添加单位时显示在鼠标上的单位
			renderAddUnitObject(g);
			g.popTransform();
			
			g.setColor(Color.WHITE);
			Drawing.drawStringBorder(g, "FPS="+getRoot().getFPS(), 1, 1, 0);

		}
		
		
		public void update()
		{
			boolean catch_mouse = local_bounds.contains(getMouseX(), getMouseY());
			int worldx = getWorld().getMouseX();
			int worldy = getWorld().getMouseY();
			
			if (isToolSelect()) {
				updateSelectUnit(catch_mouse, worldx, worldy);
			}
			else if (catch_mouse && isToolAdd()) {
				updateAddUnit(catch_mouse, worldx, worldy);
			}
			
			updateLocateCamera(catch_mouse, worldx, worldy);
			
			getWorld().sort();

		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 右键拖动摄像机
		void updateLocateCamera(boolean catch_mouse, int worldx, int worldy) 
		{
			if (catch_mouse && scene_panel.isCatchedScene() && getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (pre_right_pos == null) {
					pre_right_pos = new Point2D.Double(getMouseX(), getMouseY());
					pre_right_camera_pos = new Point2D.Double(getCameraX(), getCameraY());
				}
			}
			else if (getRoot().isMouseHold(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (pre_right_pos != null) {
					double dx = pre_right_pos.x - getMouseX();
					double dy = pre_right_pos.y - getMouseY();
					scene_panel.locationCamera(
							pre_right_camera_pos.x+dx, 
							pre_right_camera_pos.y+dy);
				}
			}
			else if (getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				pre_right_pos = null;
			}
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 选择单位
		
		void updateSelectUnit(boolean catch_mouse, int worldx, int worldy)
		{
			// 鼠标左/右键按下
			if (catch_mouse && scene_panel.isCatchedScene() && getRoot().isMouseDown(
					com.g2d.display.event.MouseEvent.BUTTON_LEFT,
					com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) 
			{
				try
				{
					getSelectedPage().updateSelectUnit(this, catch_mouse, worldx, worldy);
					scene_mini_map.repaint(500);
				}catch (Throwable e) {}
			}
			// 鼠标右键松开
			else if (catch_mouse && getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT))
			{
				if (getSelectedUnit()!=null && pre_right_pos!=null) {
					if ((pre_right_pos.x == getMouseX() && pre_right_pos.y == getMouseY())) {
						if (getSelectedUnit().getGameUnit().isCatchedMouse()) {
							try{
								Menu menu = ((SceneUnitTag<?>)getSelectedUnit()).getEditMenu();
								menu.show(scene_panel, getMouseX(), getMouseY());
							}catch(Exception err){
								err.printStackTrace();
							}
						}
					}
				}
			}
			// 删除单位
			else if (getRoot().isKeyDown(java.awt.event.KeyEvent.VK_DELETE)) {
				if (getSelectedUnit() != null) {
					removeUnit(getSelectedUnit());				
				}
			}
			// ctrl + c 复制名字
			else if (getRoot().isKeyHold(KeyEvent.VK_CONTROL) && getRoot().isKeyDown(KeyEvent.VK_C)) {
				if (getSelectedUnit() != null) {
					CObject.getAppBridge().setClipboardText(getSelectedUnit().getGameUnit().getID()+"");
				}
			}
			
			for (int p=0; p<unit_page.getTabCount(); p++) {
				SceneUnitTagAdapter<?,?> ad = (SceneUnitTagAdapter<?,?>)unit_page.getComponentAt(p);
				ad.clearAddUnitObject(this);
			}
			
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 添加单位
		void updateAddUnit(boolean catch_mouse, int worldx, int worldy) 
		{
			for (int p=0; p<unit_page.getTabCount(); p++) {
				SceneUnitTagAdapter<?,?> ad = (SceneUnitTagAdapter<?,?>)unit_page.getComponentAt(p);
				if (ad!=getSelectedPage()) {
					ad.clearAddUnitObject(this);
				} else {
					ad.updateAddUnit(this, catch_mouse, worldx, worldy);
				}
			}
		}
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		渲染当添加单位时，在鼠标上的单位

		void renderAddUnitObject(Graphics2D g) {
			if (isToolAdd()) {
				getSelectedPage().renderAddUnitObject(this, g);
			}
		}
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		场景单位
		
		class EatWorldMap extends WorldMap
		{
			public EatWorldMap(SetResource resource, String worldname) {
				super(SceneContainer.this, resource, resource.WorldTable.get(worldname));
			}
			
			@Override
			protected Unit createWorldObject(SetResource set, SpriteObject worldSet) {
				return new EatWorldObject(set, worldSet);
			}
			
		}
		
		class EatWorldObject extends WorldObject 
		{
			boolean is_png = false;
			int old_color = 0;
			float old_alpha = 0;
			BufferedImage mask;
			
			public EatWorldObject(SetResource set, SpriteObject worldSet) {
				super(set, worldSet);
			}
			@Override
			public synchronized void loaded(SetResource set, CSprite cspr,
					com.cell.gameedit.object.SpriteSet spr) {
				super.loaded(set, cspr, spr);
				this.is_png = !spr.ImagesName.startsWith("jpg");
				if (spr.ImagesName.startsWith("jpg")) {
					this.priority = Integer.MIN_VALUE;
				}
			}
			
			@Override
			public void render(Graphics2D g) 
			{
				if (csprite != null)
				{
					if (old_color != default_mask_color || 
						old_alpha != default_mask_alpha) {
						old_color = default_mask_color;
						old_alpha = default_mask_alpha;
						mask = null;
					}
					CCD cd = csprite.getFrameBounds();
					if (mask == null) {
						mask = com.g2d.Tools.createImage(cd.getWidth(), cd.getHeight());
						Graphics2D g2d = mask.createGraphics();
						csprite.render(g2d, -cd.X1, -cd.Y1);
						g2d.dispose();
						if (is_png) {
							mask = com.g2d.Tools.createAlpha(mask,
									default_mask_alpha, 
									default_mask_color);
						}
					}
					if (mask != null) {
						g.drawImage(mask, cd.X1, cd.Y1);
					}
				}
			}
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneMiniMap extends JPanel implements MouseMotionListener, MouseListener
	{
		private static final long serialVersionUID = 1L;
		
		java.awt.image.BufferedImage snapshot;
		
		public SceneMiniMap() 
		{			
			this.setMinimumSize(new java.awt.Dimension(200, 200));
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
		}
		
		@Override
		public void update(java.awt.Graphics g) {
			paint(g);
		}
		
		@Override
		public void paint(java.awt.Graphics sg)
		{
			java.awt.Graphics2D g = (java.awt.Graphics2D)sg;

			double rate_x = ((double)getWidth()) / scene_container.getWorld().getWidth();
			double rate_y = ((double)getHeight())/ scene_container.getWorld().getHeight();
			// draw bg
			if (snapshot == null || snapshot.getWidth() != getWidth() || snapshot.getHeight() != getHeight()){
				snapshot = Tools.unwrap(scene_container.getWorld().createMiniMap(getWidth(), getHeight()));
				java.awt.Graphics2D mg = (java.awt.Graphics2D)snapshot.getGraphics();
				Tools.setAlpha(mg, 0.3f);
				mg.setColor(java.awt.Color.BLACK);
				mg.fillRect(0, 0, getWidth(), getHeight());
				mg.dispose();
//				System.out.println("create snapshot !");
			}
			g.drawImage(snapshot, 0, 0, this);
			// draw units
			{
				for (SceneUnitTag<?> u : scene_container.getWorld().getChildsSubClass(SceneUnitTag.class)) {
					if (u.getSnapColor() != null) {
						double tx = (u.getGameUnit().x * rate_x);
						double ty = (u.getGameUnit().y * rate_y);
						g.translate(tx, ty);
						if (getSelectedUnit() != u) {
							g.setColor(new java.awt.Color(u.getSnapColor().getARGB(), true));
						} else {
							g.setColor(java.awt.Color.WHITE);
						}
						Rectangle rect = u.getSnapShape().getBounds();
						g.fillRect(rect.x, rect.y, rect.width, rect.height);
						g.translate(-tx, -ty);
					}
				}
			}
			// draw camera shape
			{
				java.awt.Color camera_bounds_color = java.awt.Color.WHITE;
				java.awt.geom.Rectangle2D.Double camera_bounds = new java.awt.geom.Rectangle2D.Double(
						scene_container.getCameraX() * rate_x,
						scene_container.getCameraY() * rate_y,
						scene_container.getCameraWidth() * rate_x,
						scene_container.getCameraHeight()* rate_y
				);
				g.setColor(camera_bounds_color);
				g.draw(camera_bounds);
			}
		}

		void killSnapshot() {
			snapshot = null;
		}
		
		void locateCamera(double x, double y) {
			double rate_x = ((double)scene_container.getWorld().getWidth()) / ((double)getWidth());
			double rate_y = ((double)scene_container.getWorld().getHeight())/ ((double)getHeight());
			scene_panel.locationCameraCenter(x * rate_x, y * rate_y);
		}
		
		public void mouseDragged(MouseEvent e) {
			locateCamera(e.getX(), e.getY());
		}
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				locateCamera(e.getX(), e.getY());
			}
		}
		public void mouseMoved(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	public abstract class SceneUnitTagAdapter<T extends SceneUnitTag<?>, V extends DisplayObject> extends SceneUnitList<T>
	{
		private static final long serialVersionUID = 1L;

		public SceneUnitTagAdapter(Class<T> unit_type) {
			super(SceneEditor.this, unit_type);
		}
		
		/**
		 * 指定类型是否符合当前编辑器
		 * @param unit_type
		 * @return
		 */
		final public boolean isSelectedType(Class<?> unit_type) {
			return this.unit_type.equals(unit_type);
		}
		
		/**
		 * 是否已经选择当前页
		 * @return
		 */
		final public boolean isSelectedPage() {
			return unit_page.getSelectedComponent() == this;
		}
		
		/**
		 * 指定的类型是否存在
		 * @param unit
		 * @return
		 */
		final public boolean containsUnit(Unit unit) {
			if (unit_type.isInstance(unit)) {
				return scene_container.getWorld().contains(unit);
			}
			return false;
		}
				
		/**
		 * 当按下添加单位工具条时，是否显示单位(资源)选择工具。
		 * @return
		 */
		public boolean isShowSelectUnitTool(){
			return false;
		}
		
		/**
		 * 测试某单位被选中时的更新。
		 * @param scene
		 * @param catch_mouse
		 * @param worldx
		 * @param worldy
		 */
		public void updateSelectUnit(SceneContainer scene, boolean catch_mouse, int worldx, int worldy){
			if (scene.getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) {
				selectUnit(scene.getWorld().getChildAtPos(worldx, worldy, unit_type), true);
			}
		}

		/**
		 * 当添加单位时，场景的更新
		 * @param scene
		 * @param catch_mouse
		 * @param worldx
		 * @param worldy
		 */
		abstract public void updateAddUnit(SceneContainer scene, boolean catch_mouse, int worldx, int worldy);

		/**
		 * 当添加单位时，场景的最上层渲染
		 * @param scene
		 * @param g
		 */
		abstract public void renderAddUnitObject(SceneContainer scene, Graphics2D g);
		
		/**
		 * 一般用来清理添加单位时的数据
		 * @param scene
		 */
		abstract public void clearAddUnitObject(SceneContainer scene);
		
	}
	

//	-----------------------------------------------------------------------------------------------------------------------------
	
	
	class SceneActorAdapter extends SceneUnitTagAdapter<SceneActor, CellSprite>
	{
		private static final long serialVersionUID = 1L;
		
		private Pair<CPJSprite, CellSprite> selected_sprite = new Pair<CPJSprite, CellSprite>();

		public SceneActorAdapter() {
			super(SceneActor.class);
		}

		@Override
		protected JList createList() 
		{
			JList list = new JList();
			list.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean isSelected, boolean cellHasFocus) {
					Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					SceneActor actor = (SceneActor)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(actor.getListName());
					if (actor.getUnit().getBindedTriggers().getTriggerCount()>0) {
						sb.append("<font color=0000ff>(S)</font>");
					}
					sb.append("<font color=808080> - " + actor.xls_unit.getName() + "</font>");
					sb.append("</p>");
					sb.append("</body></html>");
					this.setText(sb.toString());
					return ret;
				}
			});
			return list;
		}
		
		@Override
		public boolean isShowSelectUnitTool() {
			return true;
		}

		public CellSprite getToolSprite() {
			if (SelectUnitTool.getUnitTool().isVisible()) {
				if (getSelectedPage() == this && SelectUnitTool.getUnitTool().getSelectedUnit() != null) {
					CPJSprite cpj = SelectUnitTool.getUnitTool().getSelectedUnit().getCPJSprite();
					if (cpj != selected_sprite.getKey()) {
						selected_sprite.setKey(cpj);
						selected_sprite.setValue(cpj.createCellSprite());
					}
					return selected_sprite.getValue();
				}
			}
			return null;
		}
		
		@Override
		public void updateAddUnit(SceneContainer scene, boolean catchMouse, int worldx, int worldy) 
		{

			CellSprite cspr = getToolSprite();
			
			if (cspr != null)
			{
				if (scene.getRoot().isMouseWheelUP()) {
					cspr.getSprite().nextAnimate(-1);
				}
				if (scene.getRoot().isMouseWheelDown()) {
					cspr.getSprite().nextAnimate(1);
				}

				if (scene.getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
				{
					SceneActor actor = new SceneActor(
							SceneEditor.this, 
							SelectUnitTool.getUnitTool().getSelectedUnit(),
							worldx, 
							worldy,
							cspr.getSprite().getCurrentAnimate());
					addTagUnit(actor);
				}
			}
		}
		@Override
		public void clearAddUnitObject(SceneContainer scene) {
			
		}
		public void renderAddUnitObject(SceneContainer scene, Graphics2D g)
		{
			CellSprite cspr = getToolSprite();
			
			if (cspr != null)
			{
				g.translate(scene.getMouseX(), scene.getMouseY());
				scene.setAlpha(g, 0.75f);
				{
					cspr.render(g);
					cspr.getSprite().nextCycFrame();
					g.setColor(Color.WHITE);
					Drawing.drawStringBorder(g, 
							cspr.getSprite().getCurrentAnimate() + "/" + cspr.getSprite().getAnimateCount(), 
							0, cspr.getSprite().getVisibleBotton() + 1, 
							Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP
							);
				}
				scene.setAlpha(g, 1f);
				g.translate(-scene.getMouseX(), -scene.getMouseY());
			}
		
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneImmutableAdapter extends SceneUnitTagAdapter<SceneImmutable, CellSprite>
	{
		private static final long serialVersionUID = 1L;
		
		private Pair<CPJSprite, CellSprite> selected_sprite = new Pair<CPJSprite, CellSprite>();
		
		public SceneImmutableAdapter() {
			super(SceneImmutable.class);
		}
		
		@Override
		protected JList createList() 
		{
			JList list = new JList();
			list.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean isSelected, boolean cellHasFocus) {
					Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					SceneImmutable actor = (SceneImmutable)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(actor.getListName());	
					if (actor.getUnit().getBindedTriggers().getTriggerCount()>0) {
						sb.append("<font color=0000ff>(S)</font>");
					}
					sb.append("<font color=808080> - " + actor.cpj_spr.getName() + "</font>");
					sb.append("</p>");
					sb.append("</body></html>");
					this.setText(sb.toString());
					return ret;
				}
			});
			return list;
		}
		
		@Override
		public boolean isShowSelectUnitTool() {
			return true;
		}

		public CellSprite getToolSprite() {
			if (SelectUnitTool.getUnitTool().isVisible()) {
				if (getSelectedPage() == this && SelectUnitTool.getUnitTool().getSelectedSpr() != null) {
					CPJSprite cpj = SelectUnitTool.getUnitTool().getSelectedSpr();
					if (cpj != selected_sprite.getKey()) {
						selected_sprite.setKey(cpj);
						selected_sprite.setValue(cpj.createCellSprite());
					}
					return selected_sprite.getValue();
				}
			}
			return null;
		}
		
		@Override
		public void updateAddUnit(SceneContainer scene, boolean catchMouse, int worldx, int worldy) 
		{
			CellSprite cspr = getToolSprite();
			
			if (cspr != null)
			{
				if (scene.getRoot().isMouseWheelUP()) {
					cspr.getSprite().nextAnimate(-1);
				}
				if (scene.getRoot().isMouseWheelDown()) {
					cspr.getSprite().nextAnimate(1);
				}
			
				if (scene.getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
				{
					SceneImmutable spr = new SceneImmutable(
							SceneEditor.this, 
							SelectUnitTool.getUnitTool().getSelectedSpr(),
							worldx, 
							worldy,
							cspr.getSprite().getCurrentAnimate());
					addTagUnit(spr);
				}
			}
		}
		@Override
		public void clearAddUnitObject(SceneContainer scene) {
			
		}
		public void renderAddUnitObject(SceneContainer scene, Graphics2D g)
		{
			CellSprite cspr = getToolSprite();
			
			if (cspr != null)
			{
				g.translate(scene.getMouseX(), scene.getMouseY());
				scene.setAlpha(g, 0.75f);
				{
					cspr.render(g);
					cspr.getSprite().nextCycFrame();
					g.setColor(Color.WHITE);
					Drawing.drawStringBorder(g, 
							cspr.getSprite().getCurrentAnimate() + "/" + cspr.getSprite().getAnimateCount(), 
							0, cspr.getSprite().getVisibleBotton() + 1, 
							Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP
							);
				}
				scene.setAlpha(g, 1f);
				g.translate(-scene.getMouseX(), -scene.getMouseY());
			}
		
		}
		
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneRegionAdapter extends SceneUnitTagAdapter<SceneRegion, DisplayObject>
	{
		private static final long serialVersionUID = 1L;

		Point			add_region_sp	= null;
		Point			add_region_dp	= new Point();
		
		public SceneRegionAdapter() {
			super(SceneRegion.class);
		}

		@Override
		protected JList createList() {
			JList list = new JList();
			list.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean isSelected, boolean cellHasFocus) {
					Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					SceneRegion actor = (SceneRegion)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(actor.getListName());	
					if (actor.getUnit().getBindedTriggers().getTriggerCount()>0) {
						sb.append("<font color=0000ff>(S)</font>");
					}
					sb.append("</p>");
					sb.append("</body></html>");
					this.setText(sb.toString());
					return ret;
				}
			});
			return list;
		
		}
		
		@Override
		public void updateAddUnit(SceneContainer scene, boolean catchMouse, int worldx, int worldy) 
		{
			add_region_dp.x = worldx; 
			add_region_dp.y = worldy;
			
			if (scene.getRoot().isMouseDown(MouseEvent.BUTTON1)) 
			{
				add_region_sp = new Point();
				add_region_sp.x = worldx; 
				add_region_sp.y = worldy;
			}
			else if (scene.getRoot().isMouseUp(MouseEvent.BUTTON1)) 
			{
				if (add_region_sp!=null)
				{
					int sx = Math.min(add_region_sp.x, add_region_dp.x);
					int sy = Math.min(add_region_sp.y, add_region_dp.y);
					int sw = Math.abs(add_region_sp.x- add_region_dp.x);
					int sh = Math.abs(add_region_sp.y- add_region_dp.y);
					
					if (sw>0 && sh>0)
					{
						SceneRegion region = new SceneRegion(SceneEditor.this, new Rectangle(0, 0, sw, sh));
						region.setLocation(sx, sy);
						addTagUnit(region);
					}
				}
				add_region_sp = null;
			}
		}
		
		@Override
		public void clearAddUnitObject(SceneContainer scene) {
			add_region_sp = null;
		}
		
		@Override
		public void renderAddUnitObject(SceneContainer scene, Graphics2D g)
		{
			if (add_region_sp != null)
			{
				g.translate(-scene.getCameraX(), -scene.getCameraY());
				int sx = Math.min(add_region_sp.x, add_region_dp.x);
				int sy = Math.min(add_region_sp.y, add_region_dp.y);
				int sw = Math.abs(add_region_sp.x- add_region_dp.x);
				int sh = Math.abs(add_region_sp.y- add_region_dp.y);
				g.setColor(new Color(0x8000ff00));
				g.fillRect(sx, sy, sw, sh);
				g.translate(+scene.getCameraX(), +scene.getCameraY());
			}
		}
		
	}
	
	
	

//	-----------------------------------------------------------------------------------------------------------------------------

	class ScenePointAdapter extends SceneUnitTagAdapter<ScenePoint, DisplayObject>
	{
		private static final long serialVersionUID = 1L;

		ScenePoint 		pre_added_point;
		
		public ScenePointAdapter() {
			super(ScenePoint.class);
		}
		
		@Override
		protected JList createList() {
			JList list = new JList();
			list.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean isSelected, boolean cellHasFocus) {
					Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					ScenePoint actor = (ScenePoint)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(actor.getListName());	
					if (actor.getUnit().getBindedTriggers().getTriggerCount()>0) {
						sb.append("<font color=0000ff>(S)</font>");
					}
					sb.append("</p>");
					sb.append("</body></html>");
					this.setText(sb.toString());
					return ret;
				}
			});
			return list;
		}
		
		@Override
		public void clearAddUnitObject(SceneContainer scene) {
		}
		
		@Override
		public void updateSelectUnit(SceneContainer scene, boolean catchMouse, int worldx, int worldy) 
		{
			super.updateSelectUnit(scene, catchMouse, worldx, worldy);
			// 已选择节点并且右击了另一个
			if (scene.getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)){
				ScenePoint next = scene.getWorld().getChildAtPos(worldx, worldy, ScenePoint.class);
				if (next != null && (next != getSelectedUnit()) && getSelectedUnit() instanceof ScenePoint) {
					try{
						Menu menu = ((ScenePoint)getSelectedUnit()).getLinkMenu(next);
						menu.show(scene_panel, scene.getMouseX(), scene.getMouseY());
					}catch(Exception err){
						err.printStackTrace();
					}
				}
			}
		}
		@Override
		public void updateAddUnit(SceneContainer scene, boolean catchMouse, int worldx, int worldy) 
		{
			if (scene.getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
			{
				ScenePoint spr = new ScenePoint(SceneEditor.this, worldx, worldy);
				addTagUnit(spr);
				
				// 添加新节点并自动链接
				if (scene.getRoot().isKeyHold(KeyEvent.VK_SHIFT)) {
					if (pre_added_point!=null){
						pre_added_point.linkNext(spr);
						if (scene.getRoot().isKeyHold(KeyEvent.VK_CONTROL)) {
							spr.linkNext(pre_added_point);
						}
					}
				}
				pre_added_point = spr;
			}
		}

		@Override
		public void renderAddUnitObject(SceneContainer scene, Graphics2D g) {
			g.setColor(Color.WHITE);
			g.fillRect(scene.getMouseX()-4, scene.getMouseY()-4, 8, 8);
		}
		
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneEffectAdapter extends SceneUnitTagAdapter<SceneEffect, DisplayObject>
	{
		int high = 0;
		
		public SceneEffectAdapter() {
			super(SceneEffect.class);
		}

		@Override
		protected JList createList() {
			JList list = new JList();
			list.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;
				@Override
				public Component getListCellRendererComponent(JList list, Object value,
						int index, boolean isSelected, boolean cellHasFocus) {
					Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					SceneEffect actor = (SceneEffect)value;
					StringBuffer sb = new StringBuffer();
					sb.append("<html><body>");
					sb.append("<p>");
					sb.append(actor.getListName());	
					if (actor.getUnit().getBindedTriggers().getTriggerCount()>0) {
						sb.append("<font color=0000ff>(S)</font>");
					}
					sb.append("</p>");
					sb.append("</body></html>");
					this.setText(sb.toString());
					return ret;
				}
			});
			return list;
		}
		
		@Override
		public boolean isShowSelectUnitTool() {
			return true;
		}

		@Override
		public void clearAddUnitObject(SceneContainer scene) {
		}
		
		public DEffect getToolEffect() {
			if (SelectUnitTool.getUnitTool().isVisible()) {
				DEffect de = SelectUnitTool.getUnitTool().getSelectedEffect();
				if (getSelectedPage() == this && de != null) {
					return de;
				}
			}
			return null;
		}

		@Override
		public void updateAddUnit(SceneContainer scene, boolean catchMouse, int worldx, int worldy)
		{
			if (scene.getRoot().isKeyHold(KeyEvent.VK_SHIFT)) {
				if (scene.getRoot().isMouseWheelUP()) {
					high += 10;
				}
				if (scene.getRoot().isMouseWheelDown()) {
					high -= 10;
				}
			} else {
				if (scene.getRoot().isMouseWheelUP()) {
					high += 1;
				}
				if (scene.getRoot().isMouseWheelDown()) {
					high -= 1;
				}
			}
			
			
			if (scene.getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) {
				DEffect de = getToolEffect();
				if (de != null) {
					SceneEffect effect = new SceneEffect(
							SceneEditor.this, 
							worldx, 
							worldy,
							high,
							de);
					addTagUnit(effect);
				}
			}
		}
		
		@Override
		public void renderAddUnitObject(SceneContainer scene, Graphics2D g) 
		{
			int sx = scene.getMouseX();
			int sy = scene.getMouseY();
			
			g.setColor(Color.WHITE);
			g.drawRect(sx-20, sy-20, 40, 40);
			Drawing.drawStringBorder(g, "高度="+high, sx + 42, sy, 0);
			Drawing.drawStringBorder(g, "鼠标滚轮调整高度", sx + 42, sy+20, 0);
			
			g.setColor(Color.GREEN);
			g.drawLine(sx, sy, sx, sy-high);
			
			sy -= high;
			g.drawLine(sx-16, sy-16, sx+16, sy+16);
			g.drawLine(sx+16, sy-16, sx-16, sy+16);
			
		}
	}

	
//	-----------------------------------------------------------------------------------------------------------------------------
	
}

