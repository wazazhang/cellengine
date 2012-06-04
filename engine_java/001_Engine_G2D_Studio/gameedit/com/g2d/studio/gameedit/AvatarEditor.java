package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.cell.CUtil;
import com.g2d.awt.util.*;
import com.g2d.cell.CellSprite;
import com.g2d.display.Sprite;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DAvatar;


@SuppressWarnings("serial")
public class AvatarEditor extends AbstractFrame
{
	final AvatarSplit split = new AvatarSplit();
	
	public AvatarEditor(DAvatar avatar) {
		super.setSize(500, 500);
		super.add(split);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.split.setAvatar(avatar);
		this.split.stage_view.start();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				split.stage_view.stop();
			}
		});
	}
	
	
	private CPJIndex<CPJSprite> getSelectedPart() {
		return split.getSelectedPart();
	}
	
	private CellSprite getSelectedSprite() {
		return split.getSelectedSprite();
	}
	
	class AvatarSplit extends JSplitPane implements ActionListener
	{
		ReentrantLock 		lock 			= new ReentrantLock();
		DisplayObjectPanel	stage_view 		= new DisplayObjectPanel(
				new DisplayObjectPanel.ObjectStage(new com.g2d.Color(StudioConfig.DEFAULT_BACK_COLOR)));
		AvatarSprite		avatar_group	= new AvatarSprite();
		DAvatar 			current_avatar;
		
		JPanel 		right 			= new JPanel(new BorderLayout());
		JPanel 		left 			= new JPanel(new BorderLayout());
		JList 		part_list 		= new JList();
		JButton		btn_change_body	= new JButton("更换主角");
		JButton		btn_add_part	= new JButton("添加部件");
		JButton		btn_remove_part	= new JButton("删除部件");
		JButton		btn_move_up		= new JButton("上移");
		JButton		btn_move_down	= new JButton("下移");
			
		public AvatarSplit() 
		{
			super(JSplitPane.VERTICAL_SPLIT);
			
			
			super.setTopComponent(left);
			super.setBottomComponent(right);
			{
				right.add(stage_view);
				stage_view.getStage().addChild(avatar_group);
				stage_view.getCanvas().setFPS(StudioConfig.DEFAULT_FPS);
				stage_view.getSimpleCanvas().addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						part_list.clearSelection();
					}
				});
			}
			{
				left.add(part_list, BorderLayout.CENTER);
	
				JToolBar left_tools = new JToolBar();
				btn_change_body.addActionListener(this);
				btn_add_part.addActionListener(this);
				btn_remove_part.addActionListener(this);
				btn_move_up.addActionListener(this);
				btn_move_down.addActionListener(this);
				left_tools.add(btn_change_body);
				left_tools.add(btn_add_part);
				left_tools.add(btn_remove_part);
				left_tools.add(btn_move_up);
				left_tools.add(btn_move_down);
				left.add(left_tools, BorderLayout.NORTH);
			}
		}
	
		private void setAvatar(DAvatar avatar)
		{
//			synchronized(lock)
			{
				this.current_avatar = avatar;
				this.avatar_group.clear();
				this.avatar_group.setBody(avatar.getBody());
				
				ArrayList<CPJIndex<CPJSprite>> parts = current_avatar.getParts();
				for (CPJIndex<CPJSprite> index : parts) {
					avatar_group.addPart(index);
				}
				this.part_list.setListData(new Vector<CPJIndex<CPJSprite>>(parts));
			}
			this.repaint();
		}
		
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (current_avatar != null) {
				Object selected = part_list.getSelectedValue();
				if (e.getSource() == btn_change_body) {
					CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(this, 
							CPJResourceType.ACTOR).showDialog();
					if (spr != null) {
						current_avatar.setBody(Studio.getInstance().getCPJResourceManager().getNodeIndex(spr));
					}
				} 
				else if (e.getSource() == btn_add_part) {
					CPJSprite spr = new CPJResourceSelectDialog<CPJSprite>(this, 
							CPJResourceType.AVATAR).showDialog();
					if (spr != null) {
						current_avatar.addAvatarPart(Studio.getInstance().getCPJResourceManager().getNodeIndex(spr));
					}
				}
				else if (getSelectedPart()!=null)
				{
					if (e.getSource() == btn_remove_part) {
						current_avatar.removeAvatarPart(getSelectedPart());
					} else {
						if (e.getSource() == btn_move_up) {
							current_avatar.moveAvatarPart(getSelectedPart(), -1);
						} else if (e.getSource() == btn_move_down) {
							current_avatar.moveAvatarPart(getSelectedPart(), 1);
						}
					}
				}
				setAvatar(current_avatar);
				part_list.setSelectedValue(selected, true);
			}
		}
		
		@SuppressWarnings("unchecked")
		private CPJIndex<CPJSprite> getSelectedPart() {
			Object key = part_list.getSelectedValue();
			try{
				return (CPJIndex<CPJSprite>)key;
			}catch(Exception err){}
			return null;
		}
		
		private CellSprite getSelectedSprite() {
			Object key = part_list.getSelectedValue();
			CellSprite selected = key == null ? null : avatar_group.avatar_map.get(key);
			return selected;
		}
	
	}
	
	class AvatarSprite extends Sprite
	{			
		int current_animate = 0;

		Hashtable<CPJIndex<CPJSprite>, CellSprite> avatar_map = new Hashtable<CPJIndex<CPJSprite>, CellSprite>();
		
		private CellSprite			body_spr;
		
		public AvatarSprite() {
			enable 				= true;
			enable_input		= true;
			enable_focus		= true;
			enable_mouse_wheel	= true;
		}
		
		public void setBody(CPJIndex<CPJSprite>	body_index) {
			if (body_index!=null) {
				CPJSprite body = Studio.getInstance().getCPJResourceManager().getNode(body_index);
				body_spr = body.createCellSprite();
				addChild(body_spr);
			}
		}
		
		public void addPart(CPJIndex<CPJSprite> index) {
			CPJSprite spr = Studio.getInstance().getCPJResourceManager().getNode(index);
			CellSprite cspr = spr.createCellSprite();
			addChild(cspr);
			avatar_map.put(index, cspr);
		}

		public void clear() {
			this.avatar_map.clear();
			this.clearChilds();
		}

		@Override
		protected void onMouseWheelMoved(MouseWheelEvent event) {
			current_animate += event.scrollDirection;
		}
		
		@Override
		public void update() {
			int pw = getParent().getWidth();
			int ph = getParent().getHeight();
			setLocation(pw/2, ph/2);
			setLocalBounds(-getX(), -getY(), pw, ph);
		}
		
		@Override
		public void render(com.g2d.Graphics2D g) 
		{
			body_spr.getSprite().setCurrentAnimate(current_animate);
			body_spr.getSprite().nextCycFrame();
			int banim = body_spr.getSprite().getCurrentAnimate();
			int bfram = body_spr.getSprite().getCurrentFrame();
			int banimc = body_spr.getSprite().getAnimateCount();
			int bframc = body_spr.getSprite().getFrameCount(banim); 
			
			CellSprite selected = getSelectedSprite();
			Vector<CellSprite> list = getChildsSubClass(CellSprite.class);
		
			int i=0;
			for (CellSprite cspr : list) 
			{	
				int anim = 0, fram = 0, animc = 0, framc = 0; 
				boolean error = false;
				try{
					cspr.getSprite().setCurrentFrame(
							body_spr.getSprite().getCurrentAnimate(), 
							body_spr.getSprite().getCurrentFrame()
							);
					anim = cspr.getSprite().getCurrentAnimate();
					fram = cspr.getSprite().getCurrentFrame();
					animc = cspr.getSprite().getAnimateCount();
					framc = cspr.getSprite().getFrameCount(anim); 
					
					if (selected == cspr) {
						cspr.alpha = (float)Math.abs(Math.sin(timer/10f));
					} else {
						cspr.alpha = 1f;
					}
					
					if (anim == banim && fram == bfram && animc == banimc && framc == bframc) {
					} else {
						error = true;
					}
				}
				catch (Exception err) {
					error = true;
					//err.printStackTrace();
				}
				
				if (error) {
					g.setColor(com.g2d.Color.RED);
				} else if (selected == cspr) {
					g.setColor(com.g2d.Color.WHITE);
				} else {
					g.setColor(com.g2d.Color.GREEN);
				}
				
				com.g2d.util.Drawing.drawStringBorder(g, 
						CUtil.snapStringRightSize("资源:" + cspr.user_data, 20, ' ') + "   " +
						CUtil.snapStringRightSize("动画:" + anim+"/"+animc, 10, ' ') + "   " +
						CUtil.snapStringRightSize("帧:" + fram+"/"+framc, 10, ' ')   + "   " + 
						(body_spr==cspr ? "身体" : ""), 
						local_bounds.x+8, local_bounds.y+8 + i*20, 0);
				i++;
			}
		}
	
	}
}
