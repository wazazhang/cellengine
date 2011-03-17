package com.g2d.studio.gameedit.dynamic;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.RPGObject;
import com.cell.rpg.template.TemplateNode;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTreeNodeGroup.NodeMenu;

public abstract class DynamicNode<T extends RPGObject> extends ObjectNode<T>
{
	final protected T	bind_data;
	final int			id;
	private String		name;
	
	/**
	 * 创建新节点时
	 * @param factory
	 * @param name
	 */
	public DynamicNode(IDynamicIDFactory<?> factory, String name, Object... args) {
		this.id 		= factory.createID();
		this.name 		= name;
		this.bind_data	= newData(factory, name, args);
	}
	
	/**
	 * 读取新节点时
	 * @param data
	 * @param id
	 * @param name
	 */
	protected DynamicNode(T data, int id, String name) {
		this.id			= id;
		this.name		= name;
		this.bind_data	= data;
	}
	
	
	abstract protected T newData(IDynamicIDFactory<?> factory, String name, Object... args) ;

	public T getData() {
		return bind_data;
	}
	
	final public int getIntID() {
		return id;
	}
	
	@Override
	final public String getID() {
		return id+"";
	}
	
	@Override
	final public String getName() {
		return name + "(" + id + ")";
	}
	
	public boolean setName(String name) {
		if (name!=null && name.length()>0) {
			this.name = name;
			return true;
		}
		return false;
	}

	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new DynamicNodeMenu(this).show(tree, e.getX(), e.getY());
	}
//	-------------------------------------------------------------------------------------------------------

	protected class DynamicNodeMenu extends NodeMenu<DynamicNode<?>>
	{
		public DynamicNodeMenu(DynamicNode<?> node) {
			super(node);
			remove(open);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete) {
				int result = JOptionPane.showConfirmDialog(tree, "确定要删除 : " + node);
				if (result == JOptionPane.YES_OPTION) {
					DynamicNode.this.removeFromParent();
					tree.reload();
				}
			}
			else if (e.getSource() == rename) {
				String init = node.getName();
				if (node.getData() instanceof TemplateNode) {
					init = ((TemplateNode)node.getData()).name;
				}
				String text = JOptionPane.showInputDialog("输入新的名字", init);
				if (text != null && !text.isEmpty()) {
					node.setName(text);
				}
			}
		}
	}
}
