package com.g2d.studio.instancezone;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.instance.InstanceZone;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;
import com.g2d.awt.util.*;

@SuppressWarnings("serial")
final public class InstanceZoneNode extends DynamicNode<InstanceZone>
{
	public InstanceZoneNode(IDynamicIDFactory<?> factory, String name) {
		super(factory, name);
	}

	public InstanceZoneNode(InstanceZone zone) {
		super(zone, zone.getIntID(), zone.getName());
	}
	
	@Override
	protected InstanceZone newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		InstanceZone ret = new InstanceZone(getIntID());
		ret.setName(name);
		return ret;
	}
	
	@Override
	public boolean setName(String name) {
		if (super.setName(name)) {
			getData().setName(name);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected ImageIcon createIcon() {
		return new ImageIcon(Res.icon_scene);
	}

	@Override
	public void onClicked(JTree tree, MouseEvent e) {
		super.onClicked(tree, e);
	}
	
	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new Menu(tree, this).show(tree, e.getX(), e.getY());
	}
	
	@Override
	public InstanceZoneEditor getEditComponent() {
		onOpenEdit();
//		if (edit_component == null) {
			edit_component = new InstanceZoneEditor(this);
//		}
		return (InstanceZoneEditor)edit_component;
	}
	
//	---------------------------------------------------------------------------------------------------------------

	class Menu extends NodeMenu<InstanceZoneNode>
	{
		public Menu(JTree tree, InstanceZoneNode node) {
			super(node);
			super.remove(open);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rename) {
				String name = JOptionPane.showInputDialog(
						AbstractDialog.getTopWindow(getInvoker()), 
						"输入名字！",
						node.getData().getName());
				if (name!=null && name.length()>0) {
					node.setName(name);
				}
				if (tree!=null) {
					tree.reload(node);
				}
			}
			else if (e.getSource() == delete) {
				TreeNode parent = node.getParent();
				this.node.removeFromParent();
				if (tree!=null) {
					tree.reload(parent);
				}
			}
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------


}
