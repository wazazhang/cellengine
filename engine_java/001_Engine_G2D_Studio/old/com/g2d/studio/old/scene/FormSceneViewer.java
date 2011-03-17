package com.g2d.studio.old.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.game.CSprite;
import com.cell.rpg.entity.Actor;
import com.cell.rpg.entity.Region;
import com.cell.rpg.entity.Unit;
import com.cell.rpg.io.Util;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;

import com.g2d.cell.CellSetResource.WorldSet.SpriteObject;
import com.g2d.cell.game.Scene;
import com.g2d.cell.game.Scene.WorldMap;
import com.g2d.cell.game.Scene.WorldObject;
import com.g2d.cell.game.ui.ScenePanel;

import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseMoveListener;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.ResourceManager;
import com.g2d.studio.Version;
import com.g2d.studio.old.AFormDisplayObjectViewer;
import com.g2d.studio.old.ATreeNodeLeaf;
import com.g2d.studio.old.Studio;
import com.g2d.studio.old.Studio.SetResource;
import com.g2d.studio.old.actor.FormActorViewer;
import com.g2d.studio.old.actor.ResActorBox;
import com.g2d.studio.old.swing.SceneUnitPanel;
import com.g2d.util.AbstractFrame;
import com.g2d.util.Drawing;

public class FormSceneViewer extends AFormDisplayObjectViewer<ScenePanel> 
{
	private static final long serialVersionUID = Version.VersionGS;

//	-----------------------------------------------------------------------------------------------------------------------------
	
	final SetResource set_resource;
	
//	ui compontent
	
	public SceneUnitTag<?>	selected_unit		= null;
	
	JSplitPane		split_h				= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	JSplitPane 		left_panel_split 	= new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//	
	JToggleButton	tool_selector	= new JToggleButton("选择", true);
	JToggleButton	tool_addactor	= new JToggleButton("添加");
	
//	
	JTabbedPane 				tabel			=	new JTabbedPane();
	ResActorBox					tb_actor_res;
	SceneUnitPanel<SceneActor>	tb_actors;
	SceneUnitPanel<SceneRegion>	tb_regions;
	SceneUnitPanel<ScenePoint>	tb_points;
	
//
	SceneContainer					scene;
	SceneMiniMap				mini_map;
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	public FormSceneViewer(ATreeNodeLeaf<FormSceneViewer> leaf, SetResource set, String worldID)
	{
		super(leaf, new ScenePanel());
		set_resource	= set;
		scene			= new SceneContainer(this, set, worldID);		
		super.view_object.setScene(scene);

		// set left comp
		{
			// mini map
			{
				mini_map		= new SceneMiniMap(this, scene);
				left_panel_split.setTopComponent(mini_map);
			}
			// units
			{
				ButtonGroup 	tool_group 	= new ButtonGroup();
				super.addToolButton(tool_selector, "select", 				tool_group);
				super.addToolButton(tool_addactor, "add an unit to scene", 	tool_group);
				// actors
				{
					tb_actor_res 	= new ResActorBox(studio.group_actor);
					tb_actors		= new SceneUnitPanel<SceneActor>(this, SceneActor.class);
					JSplitPane unit_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tb_actor_res, tb_actors);
					tabel.add("unit", unit_split);
				}
				// region
				{
					tb_regions		= new SceneUnitPanel<SceneRegion>(this, SceneRegion.class);
					tabel.add("region",	tb_regions);
				}
				// poing
				{
					tb_points		= new SceneUnitPanel<ScenePoint>(this, ScenePoint.class);
					tabel.add("point",	tb_points);
				}
				left_panel_split.setBottomComponent(tabel);
			}
			
			split_h.setLeftComponent(left_panel_split);
		}
		
		// set right comp
		{
			this.remove(canvas);
			canvas.getCanvasAdapter().setStage(new SceneStage(this));
			canvas.addComponentListener(new ComponentListener(){
				public void componentResized(ComponentEvent e) {
					view_object.setSize(
							e.getComponent().getWidth(),
							e.getComponent().getHeight());
				}
				public void componentHidden(ComponentEvent e) {}
				public void componentMoved(ComponentEvent e) {}
				public void componentShown(ComponentEvent e) {}
			});
			split_h.setRightComponent(canvas);
		}
	
		this.add(split_h, BorderLayout.CENTER);
		this.setTitle("scene " + worldID);
		this.setSize(
				AbstractFrame.getScreenWidth() - Studio.getInstance().getWidth(),
				Studio.getInstance().getHeight());
		
		this.setVisible(false);
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b){
			set_resource.initAllStreamImages();
//			System.out.println("initAllStreamImages");
		}else{
			set_resource.destoryAllStreamImages();
			if (mini_map != null){
				mini_map.killSnapshot();
			}
//			System.out.println("destoryAllStreamImages");
		}
	}
	
	public void refreshAll() {
		tb_actors.repaint(500);
		tb_regions.repaint(500);
		tb_points.repaint(500);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	public boolean isBushSelect() {
		return tool_selector.isSelected();
	}
	public boolean isBushAdd() {
		return tool_addactor.isSelected();
	}
	
	
	public boolean isSelectedActorBox(){
		return tabel.getSelectedIndex() == 0;
	}
	
	public boolean isSelectedRegionBox() {
		return tabel.getSelectedIndex() == 1;
	}
	
	public boolean isSelectedPointBox() {
		return tabel.getSelectedIndex() == 2;
	}

	public void removeUnit(SceneUnitTag<?> unit) {
		try{
			view_object.getScene().getWorld().removeChild(unit.getSceneUnit());
			view_object.getScene().getWorld().processEvent();
			tb_actors.repaint(500);
			tb_regions.repaint(500);
			tb_points.repaint(500);
			mini_map.repaint(500);
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public<T extends Unit> SceneUnitTag<T> getTagUnit(T unit){
		for (SceneUnitTag<?> u : view_object.getScene().getWorld().getChildsSubClass(SceneUnitTag.class)) {
			if (u.getUnit() == unit) {
				return (SceneUnitTag<T>)u;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void sortName(Vector<SceneUnitTag> tunits) {
		CUtil.sort(tunits, new CUtil.ICompare<SceneUnitTag, SceneUnitTag>() {
			public int compare(SceneUnitTag a, SceneUnitTag b) {
				return CUtil.getStringCompare().compare(a.getSceneUnit().getID()+"", b.getSceneUnit().getID()+"");
			}
		});
	}
	
	@Override
	public ImageIcon getSnapshot(boolean update)
	{
		if (snapshot==null || update){
			String path = studio.project_path.getPath() + "/" + leaf_node.parent.path + "/jpg.jpg";
			BufferedImage jpg = Tools.readImage(path);
			float rate = 80f / (float)jpg.getWidth();
			snapshot = Tools.createIcon(Tools.combianImage(80, (int)(jpg.getHeight()*rate), jpg));
		}
		return snapshot;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void loadObject(ObjectInputStream ois, File file) throws Exception
	{
		if (view_object!=null) 
		{
			ArrayList<Unit>	tobjects	= Util.readObjects(ois);
			
			for (Unit obj : tobjects)
			{
				try {
					if (obj instanceof Actor) {
						view_object.getScene().getWorld().addChild(
								new SceneActor((Actor) obj, this));
					} else if (obj instanceof Region) {
						view_object.getScene().getWorld().addChild(
								new SceneRegion((Region) obj, this));
					} else if (obj instanceof com.cell.rpg.entity.Point) {
						view_object.getScene().getWorld().addChild(
								new ScenePoint((com.cell.rpg.entity.Point) obj, this));
					}
				} catch (Exception e) {
					System.err.println(this.getCpjObjectID() + " : " + obj.name + " : " + file);
					e.printStackTrace();
				}
			}
			
			view_object.getScene().getWorld().processEvent();
			
			for (SceneUnitTag<?> unit : view_object.getScene().getWorld().getChildsSubClass(SceneUnitTag.class)) {
				unit.onReadComplete(tobjects);
			}
			
			refreshAll();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void saveObject(ObjectOutputStream oos, File file) throws Exception
	{
		if (view_object!=null) 
		{
			Vector<SceneUnitTag> tunits = view_object.getScene().getWorld().getChildsSubClass(SceneUnitTag.class);
			sortName(tunits);
			
			ArrayList<Unit>		tobjects		= new ArrayList<Unit>();
			
			for (SceneUnitTag<?> unit : tunits) {
				tobjects.add(unit.onWrite());
			}
			for (SceneUnitTag<?> unit : tunits) {
				unit.onWriteComplete(tobjects);
			}
			
			Util.wirteObjects(tobjects, oos);
			
			refreshAll();
		}			

	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	

	static class SceneStage extends Stage
	{
		final Scene				scene;
		final FormSceneViewer	view;
		
		public SceneStage(FormSceneViewer view)
		{
			this.addChild(view.view_object);
			this.view	= view;
			this.scene	= view.view_object.getScene();
		}
		
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {}
		public void update() {}
		
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	static class SceneContainer extends Scene
	{
		final FormSceneViewer	view;
		
		Point			add_region_sp	= null;
		Point			add_region_dp	= new Point();
		
		Point2D.Double	pre_right_pos;
		Point2D.Double	pre_right_camera_pos;
		
		ScenePoint 		pre_added_point;
		
		public SceneContainer(FormSceneViewer view, CellSetResource resource, String worldname) 
		{
			super(resource, worldname);

			this.enable_input	= true;
			this.view			= view;
			this.getWorld().runtime_sort = false;
		}
		@Override
		protected WorldObject createWorldObject(CellSetResource set, SpriteObject worldSet) {
			return new EatWorldObject(set, worldSet);
		}
		@Override
		protected void onCameraChanged(double cx, double cy, double cw, double ch) {
			if (view != null && view.mini_map != null) {			
				view.mini_map.repaint(500);
			}
		}
		
		@Override
		protected void renderAfter(Graphics2D g)
		{
			AffineTransform trans = g.getTransform();
			
			if (view.tool_addactor.isSelected())
			{
				// 画Actor信息
				if (view.isSelectedActorBox())
				{
					FormActorViewer actorv = view.studio.getSelectedNodeAsActor();
					if (actorv!=null)
					{
						g.translate(getMouseX(), getMouseY());
						actorv.getViewObject().render(g);
						g.setColor(Color.WHITE);
						Drawing.drawStringBorder(g, 
								actorv.getViewObject().getSprite().getCurrentAnimate() + "/" + actorv.getViewObject().getSprite().getAnimateCount(), 
								0, actorv.getViewObject().getSprite().getVisibleBotton() + 1, 
								Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP
								);
					}
				}
				// 画Region信息
				else if (view.isSelectedRegionBox())
				{
					if (add_region_sp != null)
					{
						g.translate(-getCameraX(), -getCameraY());
						
						int sx = Math.min(add_region_sp.x, add_region_dp.x);
						int sy = Math.min(add_region_sp.y, add_region_dp.y);
						int sw = Math.abs(add_region_sp.x- add_region_dp.x);
						int sh = Math.abs(add_region_sp.y- add_region_dp.y);
						g.setColor(new Color(0x8000ff00,true));
						g.fillRect(sx, sy, sw, sh);
					}
				}
				// 画Point信息
				else if (view.isSelectedPointBox())
				{
					g.setColor(Color.WHITE);
					g.fillRect(getMouseX()-4, getMouseY()-4, 8, 8);
				}
			}
			
			g.setTransform(trans);
		}
		
		public void update()
		{
			boolean catch_mouse = local_bounds.contains(getMouseX(), getMouseY());
			int worldx = getWorld().getMouseX();
			int worldy = getWorld().getMouseY();
			
			if (view.tool_selector.isSelected()) {
				updateSelectUnit(catch_mouse, worldx, worldy);
			}
			else if (catch_mouse && view.tool_addactor.isSelected()) {
				updateAddUnit(catch_mouse, worldx, worldy);
			}
			
			updateLocateCamera(catch_mouse, worldx, worldy);
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 右键拖动摄像机
		void updateLocateCamera(boolean catch_mouse, int worldx, int worldy) 
		{
			if (catch_mouse && view.view_object.isCatchedScene() && getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (pre_right_pos == null) {
					pre_right_pos = new Point2D.Double(getMouseX(), getMouseY());
					pre_right_camera_pos = new Point2D.Double(getCameraX(), getCameraY());
				}
			}
			else if (getRoot().isMouseHold(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (pre_right_pos != null) {
					double dx = pre_right_pos.x - getMouseX();
					double dy = pre_right_pos.y - getMouseY();
					view.view_object.locationCamera(
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
			if (catch_mouse && view.view_object.isCatchedScene() && getRoot().isMouseDown(
					com.g2d.display.event.MouseEvent.BUTTON_LEFT,
					com.g2d.display.event.MouseEvent.BUTTON_RIGHT
					)) 
			{
				try
				{
					// actor
					if (view.isSelectedActorBox()) {
						view.selected_unit = getWorld().getChildAtPos(worldx, worldy, SceneActor.class);
						if (view.selected_unit != null) {
							view.tb_actors.setSelecte((SceneActor) view.selected_unit);
						}
					}
					// region
					else if (view.isSelectedRegionBox()) {
						view.selected_unit = getWorld().getChildAtPos(worldx, worldy, SceneRegion.class);
						if (view.selected_unit != null) {
							view.tb_regions.setSelecte((SceneRegion) view.selected_unit);
						}
					}
					// point
					else if (view.isSelectedPointBox())
					{
						// 选择节点
						if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) {
							view.selected_unit = getWorld().getChildAtPos(worldx, worldy, ScenePoint.class);
							if (view.selected_unit != null) {
								view.tb_points.setSelecte((ScenePoint) view.selected_unit);
							}
						}
						// 已选择节点并且右击了另一个
						else if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)){
							ScenePoint next = getWorld().getChildAtPos(worldx, worldy, ScenePoint.class);
							if (next != null && (next != view.selected_unit) && view.selected_unit instanceof ScenePoint) {
								try{
									Menu menu = ((ScenePoint)view.selected_unit).getLinkMenu(next);
									menu.show(view.view_object, getMouseX(), getMouseY());
								}catch(Exception err){
									err.printStackTrace();
								}
							}
						}
					}
					
					view.mini_map.repaint(500);
					
				}catch (Throwable e) {}
			}
			else if (catch_mouse && getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (view.selected_unit!=null && pre_right_pos!=null) {
					if ((pre_right_pos.x == getMouseX() && pre_right_pos.y == getMouseY())) {
						if (view.selected_unit.getSceneUnit().hitTestMouse()) {
							try{
								Menu menu = ((SceneUnitTag<?>)view.selected_unit).getEditMenu();
								menu.show(view.view_object, getMouseX(), getMouseY());
							}catch(Exception err){
								err.printStackTrace();
							}
						}
					}
				}
			}
			// 删除单位
			else if (getRoot().isKeyDown(java.awt.event.KeyEvent.VK_DELETE)) {
				if (view.selected_unit != null) {
					view.removeUnit(view.selected_unit);
				}
			}
			// ctrl + c 复制名字
			else if (getRoot().isKeyHold(KeyEvent.VK_CONTROL) && getRoot().isKeyDown(KeyEvent.VK_C)) {
				if (view.selected_unit != null) {
					CObject.AppBridge.setClipboardText(view.selected_unit.getSceneUnit().getID()+"");
				}
			}
			
			pre_added_point = null;
			add_region_sp = null;
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 添加单位
		void updateAddUnit(boolean catch_mouse, int worldx, int worldy) 
		{
			// 添加单位
			if (view.isSelectedActorBox())
			{
				FormActorViewer actorv = view.studio.getSelectedNodeAsActor();
				
				if (actorv!=null)
				{
					if (getRoot().isMouseWheelUP()) {
						actorv.getViewObject().getSprite().nextAnimate(-1);
					}
					if (getRoot().isMouseWheelDown()) {
						actorv.getViewObject().getSprite().nextAnimate(1);
					}

					if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
					{
						SceneActor spr = new SceneActor(actorv, view);
						spr.setLocation(worldx, worldy);
						getWorld().addChild(spr);
						getWorld().processEvent();
						
						view.tb_actors.repaint(500);
						view.mini_map.repaint(500);
					}
				}
				pre_added_point = null;
				add_region_sp = null;
			}
			// 添加区域
			else if (view.isSelectedRegionBox())
			{
				add_region_dp.x = worldx; 
				add_region_dp.y = worldy;
				
				if (getRoot().isMouseDown(MouseEvent.BUTTON1)) 
				{
					add_region_sp = new Point();
					add_region_sp.x = worldx; 
					add_region_sp.y = worldy;
				}
				else if (getRoot().isMouseUp(MouseEvent.BUTTON1)) 
				{
					if (add_region_sp!=null)
					{
						int sx = Math.min(add_region_sp.x, add_region_dp.x);
						int sy = Math.min(add_region_sp.y, add_region_dp.y);
						int sw = Math.abs(add_region_sp.x- add_region_dp.x);
						int sh = Math.abs(add_region_sp.y- add_region_dp.y);
						
						if (sw>0 && sh>0)
						{
							SceneRegion region = new SceneRegion(new Rectangle(0, 0, sw, sh), view);
							region.setLocation(sx, sy);
							getWorld().addChild(region);
							getWorld().processEvent();
							
							view.tb_regions.repaint(500);
							view.mini_map.repaint(500);
						}
					}
					pre_added_point = null;
					add_region_sp = null;
				}
			}
			// 添加点
			else if (view.isSelectedPointBox())
			{
				if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
				{
					ScenePoint spr = new ScenePoint(worldx, worldy, view);
					spr.setLocation(worldx, worldy);
					getWorld().addChild(spr);
					getWorld().processEvent();
					
					// 添加新节点并自动链接
					if (getRoot().isKeyHold(KeyEvent.VK_SHIFT)) {
						pre_added_point.linkNext(spr);
						if (getRoot().isKeyHold(KeyEvent.VK_CONTROL)) {
							spr.linkNext(pre_added_point);
						}
					}
					
					pre_added_point = spr;
					
					view.tb_points.repaint(500);
					view.mini_map.repaint(500);
				}
			
				add_region_sp = null;
			}
		
		}
	}

	/**
	 * CellGameEdit 场景编辑器精灵
	 * 
	 * @author WAZA
	 * 
	 */
	static class EatWorldObject extends WorldObject {
		public EatWorldObject(CellSetResource set, SpriteObject worldSet) {
			super(set, worldSet);
		}
		@Override
		public synchronized void loaded(CellSetResource set, CSprite cspr,
				com.g2d.cell.CellSetResource.SpriteSet spr) {
			super.loaded(set, cspr, spr);
			if (spr.ImagesName.startsWith("jpg")) {
				this.priority = Integer.MIN_VALUE;
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	static class SceneMiniMap extends JPanel implements MouseMotionListener, MouseListener
	{
		private static final long serialVersionUID = 1L;
		
		final FormSceneViewer	view;
		final SceneContainer 		scene;
		
		BufferedImage			snapshot;
		
		public SceneMiniMap(FormSceneViewer view, SceneContainer scene) 
		{			
			this.scene	= scene;
			this.view	= view;
			this.setMinimumSize(new Dimension(200, 200));
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
		}
		
		@Override
		public void update(Graphics g) {
			paint(g);
		}
		
		@Override
		public void paint(Graphics sg)
		{
			Graphics2D g = (Graphics2D)sg;

			double rate_x = ((double)getWidth()) / scene.getWorld().getWidth();
			double rate_y = ((double)getHeight())/ scene.getWorld().getHeight();
			
			if (snapshot == null || snapshot.getWidth() != getWidth() || snapshot.getHeight() != getHeight()){
				snapshot = scene.getWorld().createMiniMap(getWidth(), getHeight());
				Graphics2D mg = (Graphics2D)snapshot.getGraphics();
				Tools.setAlpha(mg, 0.3f);
				mg.setColor(Color.BLACK);
				mg.fillRect(0, 0, getWidth(), getHeight());
				mg.dispose();
//				System.out.println("create snapshot !");
			}
			g.drawImage(snapshot, 0, 0, this);
			{
				for (SceneUnitTag<?> u : scene.getWorld().getChildsSubClass(SceneUnitTag.class)) {
					if (u.getSnapColor() != null) {
						double tx = (u.getSceneUnit().x * rate_x);
						double ty = (u.getSceneUnit().y * rate_y);
						g.translate(tx, ty);
						if (view.selected_unit != u) {
							g.setColor(u.getSnapColor());
						} else {
							g.setColor(Color.WHITE);
						}
						g.fill(u.getSnapShape());
						g.translate(-tx, -ty);
					}
				}
			}
			
			{
				Color camera_bounds_color = Color.WHITE;
				Rectangle2D.Double camera_bounds = new Rectangle2D.Double(
					scene.getCameraX() * rate_x,
					scene.getCameraY() * rate_y,
					scene.getCameraWidth() * rate_x,
					scene.getCameraHeight()* rate_y
				);
				g.setColor(camera_bounds_color);
				g.draw(camera_bounds);
			}
		}

		void killSnapshot() {
			snapshot = null;
		}
		
		void locateCamera(double x, double y) {
			double rate_x = ((double)scene.getWorld().getWidth()) / ((double)getWidth());
			double rate_y = ((double)scene.getWorld().getHeight())/ ((double)getHeight());
			view.view_object.locationCameraCenter(
					x * rate_x, 
					y * rate_y);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
