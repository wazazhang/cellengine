package com.g2d.studio.instancezone;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.scene.instance.InstanceZone;
import com.cell.rpg.scene.instance.InstanceZone.BindedScene;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.studio.scene.editor.SceneSelectDialog;
import com.g2d.studio.scene.entity.SceneNode;
//import com.g2d.studio.scene.script.TriggerGeneratorPanel;
//import com.g2d.studio.scene.script.TriggersEditor;
import com.g2d.awt.util.*;


@SuppressWarnings("serial")
public class InstanceZoneEditor extends ObjectViewer<InstanceZoneNode> implements ActionListener, AncestorListener
{	
	JToolBar				toolbar 				= new JToolBar();
	JButton					btn_triggers_package	= new JButton(new ImageIcon(Res.icon_action));
	JButton					btn_add_scene			= new JButton(new ImageIcon(Res.icon_res_1));
	JButton					btn_reset_locations		= new JButton(new ImageIcon(Res.icon_layer));
	
	PageDiscussion			page_discussion;
	PageScenes				page_scenes;
//	PageTriggers			page_triggers;
	PageDatas				page_datas;
	
//	-------------------------------------------------------------------------------------
	
	public InstanceZoneEditor(InstanceZoneNode node) 
	{
		super(node);
		this.addAncestorListener(this);
		
		this.btn_triggers_package.addActionListener(this);
		this.btn_triggers_package.setToolTipText("发布的触发器");
		this.toolbar.add(btn_triggers_package);
		
		this.btn_add_scene.addActionListener(this);
		this.btn_add_scene.setToolTipText("添加场景");
		this.toolbar.add(btn_add_scene);
		
		this.btn_reset_locations.addActionListener(this);
		this.btn_reset_locations.setToolTipText("重置场景位置");
		this.toolbar.add(btn_reset_locations);
		
		
		this.toolbar.setFloatable(false);
		this.add(toolbar, BorderLayout.NORTH);
	}
	
	@Override
	protected void appendPages(JTabbedPane table) 
	{		
		table.removeAll();

		this.page_discussion 		= new PageDiscussion();
		this.page_scenes 			= new PageScenes();
		this.page_datas				= new PageDatas();
//		this.page_triggers 			= new PageTriggers();
		
		table.addTab("介绍", 		page_discussion);
		table.addTab("属性", 		page_object_panel);
		table.addTab("能力", 		page_abilities);
		table.addTab("场景", 		page_scenes);
		table.addTab("副本变量", 		page_datas);
//		table.addTab("绑定的触发器", 	page_triggers);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btn_triggers_package) {
//			new DialogTriggers().setVisible(true);
		}
		else if (e.getSource() == btn_add_scene) {
			SceneSelectDialog dialog = new SceneSelectDialog(this, true);
			SceneNode node = dialog.showDialog();
			if (node != null) {
				page_scenes.addScene(new BindedScene(node.getData()));
			}
		}
		else if (e.getSource() == btn_reset_locations) {
			page_scenes.resetLocations();
		}
	}
	
	@Override
	public void ancestorAdded(AncestorEvent event) {}
	@Override
	public void ancestorMoved(AncestorEvent event) {}
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		page_discussion.save();
	}
	
//	-------------------------------------------------------------------------------------


	InstanceZone getData() {
		return getRPGObject().getData();
	}
	
	void save() {
		this.page_discussion.save();
	}
	
//	-------------------------------------------------------------------------------------
	class PageDiscussion extends TextEditor
	{
		public PageDiscussion() 
		{
			Font font = new Font(StudioConfig.DEFAULT_FONT, 
					getTextPane().getFont().getStyle(), 
					getTextPane().getFont().getSize());
			getTextPane().setFont(font);
			setText(getData().getDiscussion()+"");
		}
		void save() {
			getData().setDiscussion(getText());
		}
	}
//	-------------------------------------------------------------------------------------
	class PageScenes extends JDesktopPane
	{
		HashMap<Integer, SceneItem> scenes = new HashMap<Integer, SceneItem>();
		
		public PageScenes() {
			super.setAutoscrolls(true);
//			super.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
			for (BindedScene bs : getData().getScenes().values()) {
				SceneItem si = new SceneItem(bs);
				scenes.put(bs.scene_id, si);
				this.add(si);
			}
		}
		
		@Override
		public void paint(Graphics g) {
			synchronized(this) {
				super.paint(g);
			}
		}
		
		private void resetLocations() {
			for (SceneItem si : scenes.values()) {
				int fx = si.getX();
				int fy = si.getY();
				if (fx < -si.getWidth() / 2) {
					fx = -si.getWidth() / 2;
				} else if (fx > getWidth() - si.getWidth() / 2) {
					fx = getWidth() - si.getWidth() / 2;
				}
				if (fy < -si.getHeight() / 2) {
					fy = -si.getHeight() / 2;
				} else if (fy > getHeight() - si.getHeight() / 2) {
					fy = getHeight() - si.getHeight() / 2;
				}
				if (fx != si.getX() || fy != si.getY()) {
					si.setLocation(fx, fy);
				}
			}
		}
		
		@Override
		protected void paintChildren(Graphics g)
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(4));
			
			g.setColor(Color.GREEN);
			for (SceneItem si : scenes.values()) {
				si.drawLink(g2d, scenes);
			}
			
			JInternalFrame selected = getSelectedFrame();
			if (selected instanceof SceneItem) {
				g.setColor(Color.RED);
				((SceneItem)selected).drawLink(g2d, scenes);
			}
			
			
			super.paintChildren(g);
		}
		
		void addScene(BindedScene bs) {
			if (!getData().getScenes().containsKey(bs.scene_id)) {
				getData().getScenes().put(bs.scene_id, bs);
				SceneItem si = new SceneItem(bs);
				scenes.put(bs.scene_id, si);
				this.add(si);
			}
			this.repaint();
		}
		
		void removeScene(BindedScene bs) {
			getData().getScenes().remove(bs.scene_id);
			SceneItem rs = scenes.remove(bs.scene_id);
			if (rs != null) {
				this.remove(rs);
			}
			this.repaint();
		}
		
		class SceneItem extends JInternalFrame implements InternalFrameListener, ComponentListener
		{
			final BindedScene 		data;
			SceneNode 				node;
			BufferedImage 			icon;
			ArrayList<Transport>	transports = new ArrayList<Transport>();
			
			public SceneItem(BindedScene bs) 
			{
				super("", false, true, false, false);
				super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				super.addInternalFrameListener(this);
				super.addComponentListener(this);
//				super.addInternalFrameListener(this);
				super.setFrameIcon(new ImageIcon(Res.icon_res_1));
//				super.setComponentPopupMenu(null);
//				super.getDesktopIcon().setEnabled(false);
				
				this.data = bs;
				this.node = Studio.getInstance().getSceneManager().getSceneNode(bs.scene_id);
				
				int sw = 120;
				int sh = 100;
				if (node != null) {
					if (node.getIcon(false)!=null) {
						Image img = node.getIcon(false).getImage();
						if (img != null) {
							this.icon = Tools.combianImage(sw, sh, img);
						} else {
							this.icon = Tools.createImage(sw, sh);
						}
					} else {
						this.icon = Tools.createImage(sw, sh);
					}
					
					super.setSize(icon.getWidth(), icon.getHeight());
					JLabel label = new JLabel(new ImageIcon(icon));
					super.add(label);
					super.setTitle(node.getIntID() + " : " + node.getName());
					
					for (SceneUnit u : node.getData().scene_units) {
						ActorTransport tp = u.getAbility(ActorTransport.class);
						if (tp != null) {
							transports.add(new Transport(u, tp));
						}
					}
				}
				
				int frameWidth  = sw + (getInsets().left+getInsets().right);
				int frameHeight = sh + (getInsets().top+getInsets().bottom);
				this.setSize(frameWidth, frameHeight);
				this.setLocation(bs.edit_x, bs.edit_y);
				this.setVisible(true);
			}
			
			@Override
			public void paint(Graphics g) {
				super.paint(g);
			}

			
			public void internalFrameClosed(InternalFrameEvent e) {
				removeScene(data);
			}
			public void internalFrameClosing(InternalFrameEvent e) {
				if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(InstanceZoneEditor.this, "确定？")){
					this.dispose();
				}
			}
			public void componentMoved(ComponentEvent e) {
				data.edit_x	= this.getX();
				data.edit_y	= this.getY();
				PageScenes.this.repaint(100);
			}
			public void internalFrameActivated(InternalFrameEvent e) {
				PageScenes.this.repaint(100);
			}
			public void internalFrameDeactivated(InternalFrameEvent e) {
				PageScenes.this.repaint(100);
			}
			public void internalFrameDeiconified(InternalFrameEvent e) {}
			public void internalFrameIconified(InternalFrameEvent e) {}
			public void internalFrameOpened(InternalFrameEvent e) {}
			public void componentHidden(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			
			class Transport
			{
				SceneUnit unit;
				ActorTransport next;
				public Transport(SceneUnit su, ActorTransport next) {
					this.unit = su;
					this.next = next;
				}
			}
			
			void drawLink(Graphics2D g, HashMap<Integer, SceneItem> scenes) {
				for (Transport tp : transports) {
					try {
						SceneItem next = scenes.get(Integer.parseInt(tp.next.next_scene_id));
						if (next != null) {
							int sx = getX() + getWidth() / 2;
							int sy = getY() + getHeight() / 2;
							int dx = next.getX() + next.getWidth() / 2;
							int dy = next.getY() + next.getHeight() / 2;
							g.drawLine(sx, sy, dx, dy);
							g.drawRect(getX()-2, getY()-2, getWidth()+4, getHeight()+4);
						}
					} catch (Exception err) {}
				}
			}
			
			
		}
	}
//	-------------------------------------------------------------------------------------
//	class PageTriggers extends TriggerGeneratorPanel
//	{
//		public PageTriggers() 
//		{
//			super(getData().getBindedTriggers(),
//					getData().getTriggersPackage(), 
//					com.cell.rpg.scene.script.entity.InstanceZone.class);
//		}
//	}
//	-------------------------------------------------------------------------------------

	class PageDatas extends InstanceZoneDataTable
	{
		public PageDatas() {
			super(getData().getData());
		}
	}
	
//	-------------------------------------------------------------------------------------
//	public class DialogTriggers extends AbstractDialog
//	{
//		TriggersEditor triggers;
//		
//		public DialogTriggers() {
//			super(InstanceZoneEditor.this);
//			super.setTitle(getData().getName() + " : 发布的触发器");
//			super.setSize(800, 600);
//			super.setIconImage(Res.icon_action);
//			super.setCenter();
//			super.setLayout(new BorderLayout());
//			this.triggers = new TriggersEditor(this, getData());
//			this.add(triggers, BorderLayout.CENTER);
//		}
//	}
//	-------------------------------------------------------------------------------------
	
}