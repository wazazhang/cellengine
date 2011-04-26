package com.g2d.studio.instancezone;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.scene.instance.InstanceZone;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;

public class InstanceZoneGroup extends ObjectGroup<InstanceZoneNode, InstanceZone>
{
	private static final long serialVersionUID = 1L;
	
	InstanceZonesTreeView view;
	
	public InstanceZoneGroup(String name, InstanceZonesTreeView view) {
		super(name, view.list_file, view.node_type, view.data_type);
		this.view = view;
	}
	
	@Override
	protected G2DTreeNodeGroup<?> createGroupNode(String name) {
		return new InstanceZoneGroup(name, view);
	}
	
	@Override
	protected boolean createObjectNode(String key, InstanceZone data) {
		try{
			view.addNode(this, new InstanceZoneNode(data));
			return true;
		}catch(Exception err){
			err.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			new Menu(this).show(
					view.getTree(),
					e.getX(),
					e.getY());
		}
	}
		
//	-------------------------------------------------------------------------------------
		
	class Menu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		InstanceZoneGroup root;
		JMenuItem add_zone = new JMenuItem("添加副本");
		
		public Menu(InstanceZoneGroup root) {
			super(root, view.getTree(), view.getTree());
			this.root = root;
			addSeparator();
			add_zone.addActionListener(this);
			add(add_zone);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource() == add_zone) {
				String name = JOptionPane.showInputDialog(view, "输入任务名字！", "未命名副本");
				if (name != null && name.length() > 0) {
					InstanceZoneNode node = new InstanceZoneNode(view, name);
					view.addNode(root, node);
					view.getTree().reload(root);
				}
			}
		}
	}

	
	
	
	
	
	
	
}
