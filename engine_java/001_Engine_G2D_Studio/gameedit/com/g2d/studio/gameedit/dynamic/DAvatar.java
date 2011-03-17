package com.g2d.studio.gameedit.dynamic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.cell.rpg.display.Node;
import com.cell.rpg.display.UnitNode;
import com.cell.rpg.template.TAvatar;
import com.g2d.awt.util.Tools;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.AvatarEditor;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.res.Res;


final public class DAvatar extends DynamicNode<TAvatar>
{	
	private CPJIndex<CPJSprite>				body;
	private ArrayList<CPJIndex<CPJSprite>>	avatars = new ArrayList<CPJIndex<CPJSprite>>();
	
	public DAvatar(IDynamicIDFactory<DAvatar> factory, String name, CPJIndex<CPJSprite> body) 
	{
		super(factory, name);
		setBody(body);
		setParts(avatars);
	}
	
	public DAvatar(TAvatar avatar) {
		super(avatar, Integer.parseInt(avatar.id), avatar.name);
		setBody(new CPJIndex<CPJSprite>(CPJResourceType.ACTOR, 
				avatar.body.cpj_project_name, 
				avatar.body.cpj_object_id));
		for (Node node : avatar.body.sub_nodes) {
			addAvatarPart(new CPJIndex<CPJSprite>(CPJResourceType.AVATAR, 
					node.cpj_project_name, 
					node.cpj_object_id));
		}
	}
	
	@Override
	protected TAvatar newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		return new TAvatar(getIntID(), name);
	}
	
	@Override
	protected ImageIcon createIcon() {
		return Tools.createIcon(Res.icon_res_2);
	}
	
//	----------------------------------------------------------------------------------------------------
	@Override
	public boolean setName(String name) {
		if(super.setName(name)){
			getData().name = name;
			return true;
		} else {
			return false;
		}
	}
	
	public CPJIndex<CPJSprite> getBody() {
		return body;
	}
	
	public void setBody(CPJIndex<CPJSprite> body) {
		if (body!=null) {
			UnitNode old = bind_data.body;
			this.body = body;
			this.bind_data.body = new UnitNode(
					body.cpj_file_name,
					body.set_object_name);
			if (old!=null) {
				this.bind_data.body.sub_nodes.addAll(old.sub_nodes);
			}
		}
	}

//	----------------------------------------------------------------------------------------------------
	
	public ArrayList<CPJIndex<CPJSprite>> getParts() {
		synchronized(avatars) {
			return new ArrayList<CPJIndex<CPJSprite>>(avatars);
		}
	}
	
	public void setParts(ArrayList<CPJIndex<CPJSprite>> parts) {			
		avatars = parts;
		synchronized(avatars) {
			bind_data.body.sub_nodes.clear();
			for (CPJIndex<CPJSprite> part : avatars) {
				bind_data.body.sub_nodes.add(new UnitNode(part.cpj_file_name, part.set_object_name));
			}
		}
	}
	
	public void addAvatarPart(CPJIndex<CPJSprite> part) {
		synchronized(avatars) {
			avatars.add(part);
			setParts(avatars);
		}
	}
	public void removeAvatarPart(int index) {
		synchronized(avatars) {
			avatars.remove(index);
			setParts(avatars);
		}
	}
	public void removeAvatarPart(CPJIndex<CPJSprite> part) {
		synchronized(avatars) {
			avatars.remove(part);
			setParts(avatars);
		}
	}

	public void moveAvatarPart(CPJIndex<CPJSprite> src, int direct) {
		try {
			synchronized(avatars) {
				int srci = avatars.indexOf(src);
				if (srci==0 && direct<0) {
					return;
				}
				if (srci==avatars.size()-1 && direct>0) {
					return;
				}
				int dsti = srci + direct;
				CPJIndex<CPJSprite> dst = avatars.get(dsti);
				avatars.set(dsti, src);
				avatars.set(srci, dst);
				setParts(avatars);
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

//	----------------------------------------------------------------------------------------------------
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		onOpenEdit();
		if (edit_component==null) {
			edit_component = new AvatarViewer();
		}
		return edit_component;
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("serial")
	public class AvatarViewer extends ObjectViewer<DAvatar>
	{
		private static final long serialVersionUID = 1L;
		
		public AvatarViewer() {
			super(DAvatar.this);
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {
			table.addTab("预览", new ViewPanel());
		}
		
		class ViewPanel extends JPanel implements ActionListener
		{
			JButton btn_open_viewer = new JButton("预览");
			public ViewPanel() {
				btn_open_viewer.addActionListener(this);
				this.add(btn_open_viewer);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn_open_viewer) {
					AvatarEditor editor = new AvatarEditor(DAvatar.this);
					editor.setCenter();
					editor.setVisible(true);
				}
			}
		}
	}
	
}
