package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.dynamic.DEffect;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup;

@SuppressWarnings("serial")
public class SelectUnitTool2 extends SelectUnitTool
{
	public SelectUnitTool2() {
		super();
	}

//	----------------------------------------------------------------------------------------------------------------

	@Override
	protected ObjectPage<CPJSprite> createCPJSpritePage() {
		return new SpritePanel();
	}
	
	@Override
	protected ObjectPage<DEffect> createDEffectPage() {
		return new EffectPanel();
	}
	
	@Override
	protected ObjectPage<XLSUnit> createXLSUnitPage() {
		return new UnitPanel();
	}
	
//	----------------------------------------------------------------------------------------------------------------
	
	protected class UnitPanel extends ObjectTreePanel<XLSUnit>
	{
		public UnitPanel() {
			super(XLSUnit.class, "单位");
		}
		
		@Override
		protected void onSelected(XLSUnit spr) {
			spr.getCPJSprite();
//			spr.getCPJSprite().getDisplayObject();
		}
		
		@Override
		public void onRefresh() {
			DefaultMutableTreeNode unit_root = Studio.getInstance().getObjectManager().getPage(XLSUnit.class).getTreeRoot();
			refreshTreeNodes(unit_root);
		}
	}

	protected class EffectPanel extends ObjectTreePanel<DEffect>
	{
		public EffectPanel() {
			super(DEffect.class, "特效");
		}
		
		@Override
		protected void onSelected(DEffect spr) {
			
		}
		
		@Override
		public void onRefresh() {
			DefaultMutableTreeNode unit_root = Studio.getInstance().getObjectManager().getPage(DEffect.class).getTreeRoot();
			refreshTreeNodes(unit_root);
		}
	}
	
	
	protected class SpritePanel extends ObjectTreePanel<CPJSprite>
	{
		public SpritePanel() {
			super(CPJSprite.class, "精灵");
		}
		
		
		@Override
		protected void onSelected(CPJSprite spr) {
//			spr.getDisplayObject();
		}
		
		@Override
		public void onRefresh() {
			final Vector<CPJSprite> sprs = Studio.getInstance().getCPJResourceManager().getNodes(CPJResourceType.ACTOR, CPJSprite.class);
			progress.setValue(0);
			progress.setMaximum(sprs.size());
			object_tree.getRoot().removeAllChildren();
			for (CPJSprite data : sprs) {
				object_tree.getRoot().add(new ObjectNode(data));
				progress.setValue(progress.getValue() + 1);
			}
			object_tree.reload();
			repaint();
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------
	
	protected abstract static class ObjectTreePanel<T extends G2DTreeNode<?>> extends JPanel implements ActionListener, ObjectPage<T>
	{	
		String			title;
		Class<T> 		type;

		JLabel 			label 			= new JLabel("单位:");
		JButton 		refresh 		= new JButton(" 刷新 ");	
		JProgressBar	progress		= new JProgressBar();
		
		JScrollPane		scroll_pan 		= new JScrollPane();
		ObjectTree		object_tree;

		T 				selected_object;
		
		protected ObjectTreePanel(Class<T> type, String title)
		{
			super(new BorderLayout());
			
			this.type = type;
			this.title = title;
			label.setText(title+":");
			JToolBar tool_bar = new JToolBar();
			tool_bar.add(label);
			tool_bar.addSeparator();
			tool_bar.add(refresh);
			refresh.addActionListener(this);
			progress.setStringPainted(true);

			object_tree = new ObjectTree();
			scroll_pan.setViewportView(object_tree);
			
			this.add(tool_bar, BorderLayout.NORTH);
			this.add(scroll_pan, BorderLayout.CENTER);
			this.add(progress, BorderLayout.SOUTH);		
			
		}
		
		
		
		public T getSelected() {
			return selected_object;
		}
		
		@Override
		public JPanel asPanel() {
			return this;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refresh) {
				onRefresh();
			} 
		}
		
		protected abstract void onSelected(T spr);
		
		protected void refreshTreeNodes(final DefaultMutableTreeNode unit_root)
		{
			progress.setValue(0);
			progress.setMaximum(G2DTree.getNodes(unit_root).size());
			object_tree.getRoot().removeAllChildren();
			Enumeration<?> childs = unit_root.children();
			while (childs.hasMoreElements()) {
				TreeNode c = (TreeNode)childs.nextElement();
				initNode(object_tree.getRoot(), c);
			}
			progress.setValue(progress.getMaximum());
			object_tree.reload();
			repaint();
		}
		
		protected void initNode(DefaultMutableTreeNode added, TreeNode node) 
		{
			if (node.getChildCount()>0) {
				DefaultMutableTreeNode g = new DefaultMutableTreeNode();
				g.setUserObject(node.toString());
				added.add(g);
				Enumeration<?> files = node.children();
				while(files.hasMoreElements()) {
					TreeNode n = (TreeNode)files.nextElement();
					initNode(g, n);
					progress.setValue(progress.getValue() + 1);
				}
			} else {
				if (type.isInstance(node)) {
					ObjectNode o = new ObjectNode(type.cast(node));
					added.add(o);
				}
			}
		}
		
//		------------------------------------------------------------------------------------------------------------
		
		protected class ObjectTree extends G2DTree
		{
			public ObjectTree() 
			{
				super(new DefaultMutableTreeNode());
				super.setRootVisible(false);
			}
		}
		

		protected class ObjectNode extends G2DTreeNode<G2DTreeNode<?>>
		{
			final T data;
			
			public ObjectNode(T data) {
				this.data = data;
			}
			
			@Override
			protected ImageIcon createIcon() {
				return data.getIcon(false);
			}
			
			@Override
			public String getName() {
				return data.getName();
			}
			
			@Override
			public void onClicked(JTree tree, MouseEvent e) {
				super.onClicked(tree, e);
				selected_object = data;
				if (selected_object != null) {
					label.setText(title + ":" + selected_object.getName());
					ObjectTreePanel.this.onSelected(selected_object);
					ObjectTreePanel.this.repaint(100);
				}
			}
		}
		
	}
	
	
	
	
	
}
