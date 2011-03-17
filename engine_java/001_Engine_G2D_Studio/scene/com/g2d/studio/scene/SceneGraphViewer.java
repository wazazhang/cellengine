package com.g2d.studio.scene;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import com.cell.game.ai.pathfind.AbstractAstar.WayPoint;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.scene.graph.SceneGraphAstar;
import com.cell.rpg.scene.graph.SceneGraphNode;
import com.cell.rpg.scene.graph.SceneGraphNode.LinkInfo;
import com.cell.util.Pair;
import com.g2d.BasicStroke;
import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Stroke;
import com.g2d.awt.util.*;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;
import com.g2d.display.TextTip;
import com.g2d.display.Tip;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Menu;
import com.g2d.display.ui.Panel;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.geom.Point2D;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.util.Drawing;


public class SceneGraphViewer extends AbstractDialog
{
	DisplayObjectPanel display_object_panel;
	
	public SceneGraphViewer(Component owner) 
	{
		super(owner);
		super.setTitle("场景图查看器");
		super.setIconImage(Res.icon_scene_graph);
		super.setLocation(Studio.getInstance().getIconManager().getLocation());
		super.setSize(Studio.getInstance().getIconManager().getSize());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.display_object_panel = new DisplayObjectPanel(
				new DisplayObjectPanel.ObjectStage(new Color(Config.DEFAULT_BACK_COLOR)));
		this.add(display_object_panel, BorderLayout.CENTER);
		
		refreshSceneGraph(Studio.getInstance().getSceneManager().createSceneGraph());
	}
	
	public void refreshSceneGraph(SceneGraph sg)
	{
		SceneGraphStage stage = new SceneGraphStage(sg);
		display_object_panel.getCanvas().changeStage(stage);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			display_object_panel.start();
		} else {
			display_object_panel.stop();
		}
		super.setVisible(b);
	}
//	-------------------------------------------------------------------------------------------------------------
	
	static class SceneGraphStage extends Stage
	{
		public SceneGraphStage(SceneGraph sg) {
			addChild(new SceneGraphPanel(sg));
		}
		public void added(com.g2d.display.DisplayObjectContainer parent) {
			getRoot().setFPS(Config.DEFAULT_FPS);
		}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.fill(local_bounds);
			g.setColor(Color.WHITE);
			Drawing.drawString(g, "fps="+getRoot().getFPS(), 1, 1);
		}
		public void update() {}
	}
	
	static class SceneGraphPanel extends Panel
	{
		SceneGraphNode 				path_start;
		SceneGraphAstar				path_finder;
		
		Point2D.Double	pre_right_pos;
		Point2D.Double	pre_right_camera_pos;
		
		HashMap<Integer, SceneFrame> all_nodes = new HashMap<Integer, SceneFrame>();
		
		public SceneGraphPanel(SceneGraph sg) {
			super.setContainer(new ScenePanelContainer());
			for (SceneGraphNode node : sg.getAllNodes()) {
				SceneFrame scene_frame = new SceneFrame(node);
				all_nodes.put(node.scene_id, scene_frame);
				this.getContainer().addChild(scene_frame);
			}
			for (SceneFrame sf : all_nodes.values()) {
				for (LinkInfo link : sf.node.next_links.values()) {
					SceneFrame next_sf = all_nodes.get(link.next_scene_id);
					if (next_sf != null) {
						sf.addNext(next_sf, link);
					} else {
						System.err.println(
								"场景["+sf.node.scene_name+"("+sf.node.scene_id+")"+"]" +
								"传送点["+link.this_scene_obj_name+"] \t-> " + 
								"场景[("+link.next_scene_id+")"+"]" +
								"传送点["+link.next_scene_obj_name+"] \t" + 
								 " 不存在！");
					}
				}
			}
			path_finder		= new SceneGraphAstar(sg);
		}
		
		@Override
		protected void updateChilds()
		{
			if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				pre_right_pos 			= new Point2D.Double(getMouseX(), getMouseY());
				pre_right_camera_pos 	= new Point2D.Double(getHScrollBar().getValue(), getVScrollBar().getValue());
			}
			else if (getRoot().isMouseHold(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) 
			{
				if (pre_right_pos != null) 
				{
					double dx = pre_right_camera_pos.x + pre_right_pos.x - getMouseX();
					double dy = pre_right_camera_pos.y + pre_right_pos.y - getMouseY();
					
					double ox = dx + getHScrollBar().getValueLength() - getHScrollBar().getMax();
					double oy = dy + getVScrollBar().getValueLength() - getVScrollBar().getMax();
					
					if (ox > 0) {
						getContainer().local_bounds.width += ox;
						getHScrollBar().setMax(getContainer().local_bounds.width);
					}
					if (oy > 0) {
						getContainer().local_bounds.height += oy;
						getVScrollBar().setMax(getContainer().local_bounds.height);
					}
					
					getHScrollBar().setValue(dx);
					getVScrollBar().setValue(dy);
				}
			}
			else if (getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				pre_right_pos = null;
			}
			super.updateChilds();
		}
		
		public void render(Graphics2D g) {
			super.setSize(getParent().getWidth(), getParent().getHeight());
		}
		
		void clearAStar()
		{
			for (SceneFrame sf : all_nodes.values()) {
				sf.finded = null;
			}
		}
		
		void findPath(SceneGraphNode path_start, SceneGraphNode end)
		{
			clearAStar();
			
			try{
				for (WayPoint p : path_finder.findPath(path_start, end)) 
				{
					SceneGraphNode	node	= (SceneGraphNode)p.map_node;
					SceneFrame		dnode	= all_nodes.get(node.scene_id);
					
					if (p.getNext()!=null) {
						SceneGraphNode		next_node 	= (SceneGraphNode)(p.getNext().map_node);
						LinkInfo 			link_tp 	= node.getLinkTransport(next_node);
						SceneFrame.LinkTP 	link 		= dnode.tp_map.get(link_tp.this_scene_obj_name);
						dnode.finded = new Pair<WayPoint, SceneFrame.LinkTP>(p, link);
					} else {
						dnode.finded = new Pair<WayPoint, SceneFrame.LinkTP>(p, null);
					}
				}
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		
		class ScenePanelContainer extends PanelContainer
		{
			public void update() {
				int mx = getWidth();
				int my = getHeight();
				for (SceneFrame sf : all_nodes.values()) {
					mx = (int)Math.max(mx, sf.x + sf.getWidth() + 50);
					my = (int)Math.max(my, sf.y + sf.getHeight() + 50);
				}
				setSize(mx, my);
			}
			
			
		}
		
//		-------------------------------------------------------------------------------------------------------------
		class SceneFrame extends Sprite
		{
			TextTip			tip = new TextTip();
			
			SceneGraphNode	node ;
			
			SceneNode 		snode;
			Image			snapshot;
			
			HashMap<String, LinkTP>
							tp_map;
			
			ArrayList<Pair<LinkTP, Pair<SceneFrame, LinkTP>>> 
							nexts;

			double			scalex, scaley;
			
			Pair<WayPoint, LinkTP>		
							finded;
			
			public SceneFrame(SceneGraphNode node) 
			{
				this.node 			= node;
				
				this.snode 			= Studio.getInstance().getSceneManager().getSceneNode(node.scene_id);
				
				if (snode.getIcon(false) == null) {
					snode.getIcon(true);
				}
				if (snode.getIcon(false) != null && 
					snode.getIcon(false).getImage() != null) {
					this.snapshot		= Tools.wrap_g2d(snode.getIcon(false).getImage());
				} else {
					this.snapshot		= com.g2d.Tools.createImage(80, 60);
				}
				
				this.setLocation(node.x, node.y);
				this.setSize(
						snapshot.getWidth() + 24, 
						snapshot.getHeight() + 24);

				this.scalex			= snapshot.getWidth() / node.width;
				this.scaley			= snapshot.getHeight() / node.height;

				this.enable 		= true;
				this.enable_input	= true;
				this.enable_focus	= true;
				this.enable_drag	= true;

				
				this.tp_map			= new HashMap<String, LinkTP>(node.getNexts().size());
				
				for (SceneUnit unit : snode.getData().scene_units) {
					ActorTransport tp = unit.getAbility(ActorTransport.class);
					if (tp!=null) {
						LinkTP v = new LinkTP(unit, tp);
						tp_map.put(unit.id, v);
						this.addChild(v);
					}
				}

				this.nexts 			= new ArrayList<Pair<LinkTP, Pair<SceneFrame, LinkTP>>>(node.getNexts().size());
				
				
			}

			private void addNext(SceneFrame next_sf, LinkInfo link) 
			{
				try {
					nexts.add(new Pair<LinkTP, Pair<SceneFrame, LinkTP>>(
							tp_map.get(link.this_scene_obj_name), 
							new Pair<SceneFrame, LinkTP>(
									next_sf, 
									next_sf.tp_map.get(link.next_scene_obj_name))));
				} catch (Exception err) {
					err.printStackTrace();
				}
				
			}
			
			@Override
			public void update() 
			{
				if (this.x < 0)this.x = 0;
				if (this.y < 0)this.y = 0;
				
				snode.getData().scene_node.x = (float)x;
				snode.getData().scene_node.y = (float)y;
			}
			
			
			
			@Override
			public void render(Graphics2D g) 
			{
				float d = (float)Math.abs(Math.sin(timer/10f));
				
				Color high_color = new Color(d, d, d);
				Color base_color = Color.RED;
				if (isPickedMouse()) {
					base_color = Color.WHITE;
				}
				boolean is_path = path_start == this.node || finded!=null;
				
				// draw back
				if (is_path) {
					g.setColor(high_color);
					g.fill(local_bounds);
					g.drawRect(local_bounds.x-1, local_bounds.y-1, local_bounds.width+2, local_bounds.height+2);
				} else {
					g.setColor(Color.BLACK);
					g.fill(local_bounds);
				}
				
				// draw image
				{
					g.drawImage(snapshot, 
							local_bounds.x + 2, 
							local_bounds.y + 2);
				}
				// draw next link
				{
					drawNextLink(g, high_color, base_color);
				}
				
				// draw info
				{
					g.setColor(base_color);
					g.draw(local_bounds);
					Drawing.drawString(g, "S" + node.scene_id + " G"+node.scene_group, 
							local_bounds.x + 2, 
							local_bounds.y + local_bounds.height-18);
				}
			}
			
			private void drawNextLink(Graphics2D g, Color high_color, Color base_color)
			{
				g.pushStroke();
				try{
					BasicStroke bs = new BasicStroke(3);
					for (Pair<LinkTP, Pair<SceneFrame, LinkTP>> next : nexts) {
						try{
							int sx = (int)next.getKey().getVectorX();
							int sy = (int)next.getKey().getVectorY();
							int dx = (int)((next.getValue().getKey().x - x) + next.getValue().getValue().getVectorX());
							int dy = (int)((next.getValue().getKey().y - y) + next.getValue().getValue().getVectorY());
							if (finded != null && next.getKey().equals(finded.getValue())) {
								g.setColor(high_color);
								g.setStroke(bs);
							} else {
								g.setColor(base_color);
								g.setStroke(bs);
							}
							g.drawLine(sx, sy, dx, dy);
						} catch (Exception err) {
							g.setColor(base_color);
							g.draw(local_bounds);
							Drawing.drawString(g, "NextLink Error ! " + next.getKey().tp, 
									local_bounds.x + 2, 
									local_bounds.y + local_bounds.height);
						}
					}
				}finally{
					g.popStroke();
				}
			}

			@Override
			public Tip getTip() {
				tip.setText(node.scene_name + "(" + node.scene_id + ") \nGroup="+node.scene_group);
				return tip;
			}
			
			@Override
			protected void onMouseClick(com.g2d.display.event.MouseEvent event) {
				if (event.mouseButton == MouseEvent.BUTTON_RIGHT) {
					new SceneMenu().show(getParent(), (int)x+getMouseX(), (int)y+getMouseY());
				}
			}
			
			
//			-------------------------------------------------------------------------------------------------------------
			
			class LinkTP extends Sprite
			{
				TextTip			tip = new TextTip();
				ActorTransport 	tp;
				SceneUnit 		unit;
				
				public LinkTP(SceneUnit unit, ActorTransport tp) {
					this.unit 			= unit;
					this.tp				= tp;
					this.setVectorX(2 + unit.x * scalex);
					this.setVectorY(2 + unit.y * scaley);
					this.setSize(6, 6);	
					this.local_bounds.x = -local_bounds.width / 2;
					this.local_bounds.y = -local_bounds.height / 2;
					this.enable 		= true;
					this.enable_input	= true;
					this.enable_focus	= true;
				}
				
				@Override
				public void render(Graphics2D g) {
					if (isPickedMouse()) {
						g.setColor(Color.WHITE);
					} else {
						g.setColor(Color.GREEN);
					}
					g.drawRect(this.local_bounds.x, this.local_bounds.y, this.local_bounds.width-1, this.local_bounds.height-1);
				}
				
				@Override
				public Tip getTip() {
					tip.setText((unit.id + " -> " + tp.next_scene_id + ":" + tp.next_scene_object_id));
					return tip;
				}
				
			}

//			-------------------------------------------------------------------------------------------------------------
			
			class SceneMenu extends Menu
			{
				final MenuItem START	= new MenuItemButton("设置为开始点");
				final MenuItem END		= new MenuItemButton("设置为结束点");
				
				public SceneMenu() {
					super.addMenuItem(START);
					super.addMenuItem(END);
				}
				
				@Override
				protected void onClickMenuItem(MenuItem item) {
					if (item == START) {
						path_start = SceneFrame.this.node;
						clearAStar();
					} else if (item == END) {
						if (path_start!=null) {
							findPath(path_start, SceneFrame.this.node);
						}
					}
				}
			}

//			-------------------------------------------------------------------------------------------------------------
			
		}

		
	}
	

	
//	static class SceneGraphPanel extends JDesktopPane
//	{
//		HashMap<SceneFrame, SceneGraphNode> frames = new HashMap<SceneFrame, SceneGraphNode>();
//		
//		public SceneGraphPanel(SceneGraph sg) 
//		{
//			super.setAutoscrolls(true);
//			
//			for (SceneGraphNode node : sg.getAllNodes())
//			{
//				SceneFrame scene_frame = new SceneFrame(node);
//				this.add(scene_frame);
//				frames.put(scene_frame, node);
//			}
//		}
//		
//		private void refreshSize() {
//			int mx = 0;
//			int my = 0;
//			for (JInternalFrame scene_frame : frames.keySet()) {
//				int dx = scene_frame.getX() + scene_frame.getWidth();
//				int dy = scene_frame.getY() + scene_frame.getHeight();
//				mx = Math.max(mx, dx);
//				my = Math.max(my, dy);
//			}
//			Dimension size = new Dimension(mx, my);
//			this.setPreferredSize(size);
//			this.setMinimumSize(size);
//			this.setSize(size);
//		}
//		
//		class SceneFrame extends JInternalFrame implements MouseListener
//		{
//			public SceneFrame(SceneGraphNode node) 
//			{
//				super(node.scene_name+"(" + node.scene_id + ")", 
//						false, false, false, false);
//				
//				super.setLocation(CUtil.getRandom(0, 1000), CUtil.getRandom(0, 1000));
//				super.setSize(120, 120);
//				
////				super.setLocation((int)node.x, (int)node.y);
////				super.setSize(d)
//				
//				super.setVisible(true);
//				super.setAutoscrolls(true);
//				
//				super.addMouseListener(this);
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {}
//			@Override
//			public void mouseEntered(MouseEvent e) {}
//			@Override
//			public void mouseExited(MouseEvent e) {}
//			@Override
//			public void mousePressed(MouseEvent e) {}
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				refreshSize();
//			}
//
//		}
//		
//	
//	}
}
