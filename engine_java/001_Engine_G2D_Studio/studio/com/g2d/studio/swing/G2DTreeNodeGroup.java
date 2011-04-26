package com.g2d.studio.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.CUtil;



public abstract class G2DTreeNodeGroup<C extends G2DTreeNode<?>> extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = 1L;
	
	private String name ;
	
	@SuppressWarnings("unchecked")
	public G2DTreeNodeGroup(String name) {
		super(name);
		this.name = name;
		this.children = new Vector();
	}

//	-------------------------------------------------------------------------------------------------------
//
	public String getName() {
		return name;
	}
	
	public Icon getIcon() {
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		if (newChild instanceof G2DTreeNodeGroup<?>) {
			G2DTreeNodeGroup<?> subg = findChild(newChild.toString());
			if (subg!=null && subg!=newChild) {
				throw new IllegalStateException("duplicate child ! " + newChild);
			}
		}
		super.add(newChild);
	}
	
	@Override
	public void insert(MutableTreeNode newChild, int childIndex) {
		if (newChild instanceof G2DTreeNodeGroup<?>) {
			G2DTreeNodeGroup<?> subg = findChild(newChild.toString());
			if (subg!=null && subg!=newChild) {
				throw new IllegalStateException("duplicate child ! " + newChild);
			}
		}
		super.insert(newChild, childIndex);
	}
	
	public boolean setName(String name) {
		if (getParent()!=null) {
			Enumeration<?> childs = getParent().children();
			while (childs.hasMoreElements()) {
				Object obj = childs.nextElement();
				if (obj instanceof G2DTreeNodeGroup<?>) {
					if (obj.toString().equals(name)) {
						System.err.println("duplicate tree node name !");
						return false;
					}
				}
			}
		}
		this.userObject = name;
		this.name = name;
		return true;
	}
	
	public G2DTreeNodeGroup<?> findChild(String name) {
		for (Object obj : children) {
			if (obj instanceof G2DTreeNodeGroup<?>) {
				if (obj.toString().equals(name)) {
					return (G2DTreeNodeGroup<?>)obj;
				}
			}
		}
		return null;
	}

//	-------------------------------------------------------------------------------------------------------
//

	/**
	 * 有子节点加入的时候
	 * @param name
	 * @return
	 */
	abstract protected G2DTreeNodeGroup<?> createGroupNode(String name);
	
//	-------------------------------------------------------------------------------------------------------
	
	public void onClicked(JTree tree, MouseEvent e){}
	
	
//	-------------------------------------------------------------------------------------------------------
	
	/**
	 * 忽略根节点,子节点必须是叶子。
	 * @param node
	 * @param split
	 * @return
	 */
	public static String toPathString(G2DTreeNode<?> node, String split)
	{
		String path = "";
		TreeNode p = node.getParent();
		while (p != null) {
			if (p.getParent()!=null) {
				path = p.toString() + split + path;
			}
			p = p.getParent();
		}
		return path;
	}
	
	/**
	 * 忽略根节点
	 * @param node
	 * @param split
	 * @return
	 */
	public static String toPathString(TreeNode node, String split)
	{
		String path = "";
		TreeNode p = node;
		while (p != null && p.getParent() != null) {
			path = p.toString() + split + path;
			p = p.getParent();
		}
		return path;
	}
	
	public static String[] fromPathString(String path, String split)
	{
		String[] ret = CUtil.splitString(path, split);
		return ret;
	}
	
//	-------------------------------------------------------------------------------------------------------
	
	public static class GroupMenu extends JPopupMenu implements ActionListener, Externalizable
	{
		private static final long serialVersionUID = 1L;
		
		protected Component				window;
		protected G2DTreeNodeGroup<?>	root;
		protected G2DTree				g2d_tree;
		
		protected JMenuItem		info 		= new JMenuItem();
		protected JMenuItem		add_group	= new JMenuItem("添加过滤器");
		protected JMenuItem		rename 		= new JMenuItem("重命名过滤器");
		protected JMenuItem		delete 		= new JMenuItem("删除过滤器");
		
		public GroupMenu(G2DTreeNodeGroup<?> root, Component window, G2DTree g2d_tree) 
		{
			this.root 		= root;
			this.window 	= window;
			this.g2d_tree	= g2d_tree;
			
			info.setText("过滤器 : " + root.toString());
			info.setEnabled(false);

			add_group.addActionListener(this);
			rename.addActionListener(this);
			delete.addActionListener(this);
			
			add(info);
			add(add_group);
			add(rename);
			if (root.getParent()!=null) {
				add(delete);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == add_group) {
				String group_name = JOptionPane.showInputDialog(window, " 输入过滤器名字！", "未命名过滤器");
				if (group_name!=null && group_name.length()>0) {
					if (root.findChild(group_name)==null) {
						root.add(root.createGroupNode(group_name));
						g2d_tree.reload(root);
					} else {
						JOptionPane.showMessageDialog(window, "过滤器 \"" + group_name + "\" 已经存在！");
					}
				}
			}
			else if (e.getSource() == rename) {
				String group_name = JOptionPane.showInputDialog(window, " 输入过滤器名字！", root.toString());
				if (group_name!=null && group_name.length()>0) {
					if (!root.setName(group_name)) {
						JOptionPane.showMessageDialog(window, "过滤器 \"" + group_name + "\" 已经存在！");
					} else {
						g2d_tree.reload(root);
					}
					g2d_tree.repaint();
				}
			}
			else if (e.getSource() == delete) {
				if (JOptionPane.showConfirmDialog(window, "确实要删除过滤器 \"" + root + "\" ！") == JOptionPane.YES_OPTION) {
					G2DTreeNodeGroup<?> parent = (G2DTreeNodeGroup<?>)root.getParent();
					root.removeFromParent();
					g2d_tree.reload(parent);
				}
			}
		}
		
		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {}
		@Override
		public void writeExternal(ObjectOutput out) throws IOException {}

	}
	

//	-------------------------------------------------------------------------------------------------------
	
	
	public static class NodeMenu<T extends G2DTreeNode<?>> extends JPopupMenu implements ActionListener, Externalizable
	{
		private static final long serialVersionUID = 1L;
		
		final public T				node;
		
		protected JMenuItem 		info	= new JMenuItem();
		protected JMenuItem			open	= new JMenuItem("打开");
		protected JMenuItem 		rename	= new JMenuItem("重命名");
		protected JMenuItem 		delete	= new JMenuItem("删除");
		
		protected G2DTree			tree;

		public NodeMenu(T node)
		{
			this.node = node;
			
			info.setText(node.getName());
			info.setEnabled(false);
			
			add(info);
			add(open);
			add(rename);
			add(delete);
			
			open.addActionListener(this);
			rename.addActionListener(this);
			delete.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == open) {
				System.out.println(node.getName() + " - open");
			} 
			else if (e.getSource() == rename) {
				System.out.println(node.getName() + " - rename");
			}
			else if (e.getSource() == delete) {
				System.out.println(node.getName() + " - delete");
			}
		}
		
		@Override
		public void show(Component invoker, int x, int y) {
			if (invoker instanceof G2DTree) {
				this.tree = (G2DTree)invoker;
			}
			super.show(invoker, x, y);
		}
		
		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {}
		@Override
		public void writeExternal(ObjectOutput out) throws IOException {}
	}
}
