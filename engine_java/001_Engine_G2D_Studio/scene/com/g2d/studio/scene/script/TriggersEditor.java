package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.tree.TreeNode;

import com.cell.CUtil;
import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerEditable;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.Triggers;
import com.cell.rpg.scene.TriggersPackage;
import com.g2d.studio.quest.items.QuestItemNode;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.entity.SceneGroup;
import com.g2d.studio.scene.script.TriggersEditor.TriggerGroup.TriggerNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup;

@SuppressWarnings("serial")
public class TriggersEditor extends JPanel implements AncestorListener, WindowListener
{
	final TriggersPackage 	triggers_pak;
	final Triggers 			triggers;
	JDialog 				root_dialog;
	String					old_title;
	
	TriggerGroup	tree_root;
	
	JSplitPane		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

	TriggerTreeView tree_view;
	
	JPanel			edit_panel;
	
	public TriggersEditor(JDialog dialog, TriggersPackage root) 
	{
		super(new BorderLayout());
		
		this.root_dialog	= dialog;
		this.root_dialog	.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.old_title		= dialog.getTitle();
		
		this.triggers_pak 	= root;
		this.triggers		= root.getTriggersPackage();
		
		this.tree_root		= new TriggerGroup("触发器");
		this.loadList();
		
		this.tree_view		= new TriggerTreeView();
		
		this.edit_panel		= new JPanel(new BorderLayout());
		
		JScrollPane left 	= new JScrollPane(tree_view);
		left.setPreferredSize(new Dimension(250, 200));
		this.split.setLeftComponent(left);
		this.split.setRightComponent(edit_panel);
		
		this.add(split, BorderLayout.CENTER);
		
		this.tree_view.reload();
		this.tree_view.expandAll();
		
		this.addAncestorListener(this);
		this.root_dialog.addWindowListener(this);
	}
	

	@Override
	public void ancestorAdded(AncestorEvent event) {}

	@Override
	public void ancestorMoved(AncestorEvent event) {}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		saveList();
	}

	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) 
	{
		Vector<TriggerPanelScriptable> changed = new Vector<TriggerPanelScriptable>();
		for (TriggerNode tn : G2DTree.getNodesSubClass(tree_root, TriggerNode.class)) {
			if (tn.getEditPage() instanceof TriggerPanelScriptable) {
				TriggerPanelScriptable tps = (TriggerPanelScriptable)tn.getEditPage();
				if (tps.hasChanged()) {
					changed.add(tps);
				}
			}
		}
		if (!changed.isEmpty()) {
			int result = JOptionPane.showConfirmDialog(this, "关闭前保存脚本？");
			if (JOptionPane.CANCEL_OPTION == result) {
				root_dialog.setVisible(true);
			}
			else if (JOptionPane.OK_OPTION == result) {
				for (TriggerPanelScriptable tps : changed) {
					tps.save();
				}
				root_dialog.dispose();
			} else {
				root_dialog.dispose();
			}
		} else {
			e.getWindow().dispose();
		}
	}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
	

	void refreshChanged() {		
		for (TriggerNode tn : G2DTree.getNodesSubClass(tree_root, TriggerNode.class)) {
			if (tn.getEditPage() instanceof TriggerPanelScriptable) {
				TriggerPanelScriptable tps = (TriggerPanelScriptable)tn.getEditPage();
				if (tps.hasChanged()) {
					root_dialog.setTitle("*" + old_title);
					return;
				}
			}
		}
		root_dialog.setTitle(old_title);
		root_dialog.repaint();
	}
	
//	-------------------------------------------------------------------------------------------------------

	private void loadList() {
		for (SceneTrigger st : triggers.getTriggers()) {
			tree_root.loadPath(st.getTreePath() + st.getName());
		}
	}
	
	private void saveList() {
		ArrayList<String> savelist = new ArrayList<String>();
		for (TriggerNode item : G2DTree.getNodesSubClass(tree_root, TriggerNode.class)) {
			item.trigger.setTreePath(G2DTreeNodeGroup.toPathString(item, "/"));
			savelist.add(item.trigger.getName());
		}
		triggers.saveList(savelist);
	}

	private void addTrigger(TriggerGroup tg, TriggerNode tn) {
		if (triggers.addTrigger(tn.trigger)) {
			tg.add(tn);
		}
	}
	
	private void removeTrigger(TriggerGroup tg, TriggerNode tn) {
		if (triggers.removeTrigger(tn.trigger.getName())!=null) {
			tg.remove(tn);
		}
	}
	
//	-------------------------------------------------------------------------------------------------------
	class TriggerTreeView extends G2DTree
	{
		public TriggerTreeView() {
			super(tree_root);
			super.setDragEnabled(true);
		}
		
		protected void onSelectChanged(TreeNode node) {
			Component right = edit_panel;
			if (node instanceof TriggerNode) {
				TriggerNode tn = (TriggerNode)node;
				JPanel page = tn.getEditPage();
				if (page != null) {
					right = page;
				}
			}
			if (right != split.getRightComponent()) {
				split.setRightComponent(right);
			}
		}
		
	}

	class TriggerGroup extends G2DTreeNodeGroup<TriggerNode>
	{
		public TriggerGroup(String name) {
			super(name);
		}

//		public void loadPath(String node_path) {
//			String[] id_name = CUtil.splitString(node_path, "?");
//			if (id_name.length > 1) {
//				node_path = id_name[0];
//			}
//			TriggerGroup group = this;
//			String[] path = fromPathString(node_path.trim(), "/");
//			for (int i=0; i<path.length; i++) {
//				String file_name = path[i].trim();
//				if (group.pathAddLeafNode(file_name, i, path.length)) {
//					return;
//				} else {
//					G2DTreeNodeGroup<?> g = group.findChild(file_name);
//					if (g == null) {
//						g = createGroupNode(file_name);
//						group.add(g);
//					}
//					group = (TriggerGroup)g;
//				}
//			}
//		}

		protected boolean pathAddLeafNode(String name, int index, int length) {
			if (index == length - 1) {
				SceneTrigger st = triggers.getTrigger(name);
				if (st != null) {
					this.add(new TriggerNode(st));
					return true;
				}
			}
			return false;
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new TriggerGroup(name);
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new GenerateMenu((G2DTree)tree).show(tree, e.getX(), e.getY());
			}
		}
		
//		-------------------------------------------------------------------------------------
//		@Override
//		protected ImageIcon createIcon() {
//			if (root_object instanceof Scene) {
//				return new ImageIcon(Res.icon_scene);
//			}
//			if (root_object instanceof SceneUnit) {
//				return new ImageIcon(Res.icon_hd);
//			}	
//			if (Player.class.isAssignableFrom(trigger_object_type)) {
//				return new ImageIcon(Res.icon_res_2);
//			}
//			return null;
//		}
		
//		@Override
//		public String getName() {
//			return root_object.getTriggerObjectName();
//		}
//
//		@Override
//		public void onRightClicked(JTree tree, MouseEvent e) {
//			if (e.getButton() == MouseEvent.BUTTON3) {
//				new GenerateMenu((G2DTree)tree).show(tree, e.getX(), e.getY());
//			}
//		}

		class GenerateMenu extends JPopupMenu implements ActionListener
		{
			G2DTree tree;

			JMenuItem item_add_scriptable_trigger 	= new JMenuItem("添加触发器(脚本)");
			JMenuItem item_add_editable_trigger 	= new JMenuItem("添加触发器(编辑)");
			
			JMenuItem item_add_group			 	= new JMenuItem("添加过滤器");
			JMenuItem item_del_group			 	= new JMenuItem("删除过滤器");
			
			public GenerateMenu(G2DTree tree) {
				this.tree = tree;
				item_add_group				.addActionListener(this);
				item_del_group				.addActionListener(this);
				item_add_scriptable_trigger	.addActionListener(this);
				item_add_editable_trigger	.addActionListener(this);
				add(item_add_group);
				add(item_del_group);
				add(item_add_scriptable_trigger);
				add(item_add_editable_trigger);
				if (TriggerGroup.this == tree_root ||
					TriggerGroup.this.getChildCount()!=0) {
					item_del_group.setEnabled(false);
				}
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == item_add_group) {
					Object res = JOptionPane.showInputDialog(tree, "输入名字", "UnamedGroup");
					if (res != null) {
						try {
							TriggerGroup tg = new TriggerGroup(res.toString());
							TriggerGroup.this.add(tg);
							tree.reload(TriggerGroup.this);
							tree.expand(TriggerGroup.this);
						} catch (Exception err) {
							JOptionPane.showMessageDialog(tree, "重复的名字!");
						}
					}
				}
				else if (e.getSource() == item_add_group) {
					if (TriggerGroup.this != tree_root && 
						TriggerGroup.this.getChildCount()==0) {
						if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(tree, "确定 ?")) {
							TriggerGroup parent = (TriggerGroup)TriggerGroup.this.getParent();
							parent.remove(TriggerGroup.this);
							tree.reload(parent);
						}
					}
				}
				else if (e.getSource() == item_add_scriptable_trigger) {
					Object res = JOptionPane.showInputDialog(tree, "输入名字", "UnamedScript");
					if (res != null) {
						try {
							SceneTriggerScriptable sts = new SceneTriggerScriptable(triggers, res.toString());
							TriggerNode trigger = new TriggerNode(sts);
							addTrigger(TriggerGroup.this, trigger);
							tree.reload(TriggerGroup.this);
							tree.expand(TriggerGroup.this);
						} catch (Exception err) {
							JOptionPane.showMessageDialog(tree, "重复的名字!");
						}
					}
				}
				else if (e.getSource() == item_add_editable_trigger) {
					Object res = JOptionPane.showInputDialog(tree, "输入名字", "UnamedEditor");
					if (res != null) {
						try {
							SceneTriggerEditable sts = new SceneTriggerEditable(triggers, res.toString());
							TriggerNode trigger = new TriggerNode(sts);
							addTrigger(TriggerGroup.this, trigger);
							tree.reload(TriggerGroup.this);
							tree.expand(TriggerGroup.this);
						} catch (Exception err) {
							JOptionPane.showMessageDialog(tree, "重复的名字!");
						}
					}
				}
			}
			
		}
		

		/**
		 * 编辑一个触发器
		 * @author WAZA
		 */
		public class TriggerNode extends G2DTreeNode<G2DTreeNode<?>>
		{
			SceneTrigger 	trigger;
			
			TriggerPanel<?> edit_page;
			
			public TriggerNode(SceneTrigger trigger) {
				this.trigger = trigger;
			}
			
			@Override
			protected ImageIcon createIcon() {
				if (trigger instanceof SceneTriggerScriptable) {
					return new ImageIcon(Res.icon_action);
				} else if (trigger instanceof SceneTriggerEditable) {
					return new ImageIcon(Res.icon_condition);
				} else {
					return null;
				}
			}
			
			@Override
			public String getName() {
				if (trigger != null) {
					if (edit_page instanceof TriggerPanelScriptable) {
						TriggerPanelScriptable sc = (TriggerPanelScriptable)edit_page;
						if (sc.hasChanged()) {
							return "*" + trigger.getName();
						} else {
							return trigger.getName();
						}
					}
					return trigger.getName();
				}
				return "null";
			}

			@Override
			public void onRightClicked(JTree tree, MouseEvent e) {
				new TriggerNodeMenu((G2DTree)tree).show(tree, e.getX(), e.getY());
			}

			TriggerPanel<?> getEditPage() {
				if (edit_page == null) {
					if (trigger instanceof SceneTriggerScriptable) {
						edit_page = new TriggerPanelScriptable(
								triggers_pak,
								(SceneTriggerScriptable)trigger);
					} else if (trigger instanceof SceneTriggerEditable) {
						edit_page = new TriggerPanelEditable(
								triggers_pak,
								(SceneTriggerEditable)trigger);
					}
				}
				return edit_page;
			} 
			
			class TriggerNodeMenu extends JPopupMenu implements ActionListener
			{
				G2DTree tree;
				
				JMenuItem rename 	= new JMenuItem("重命名");
				JMenuItem delete 	= new JMenuItem("删除");
				
				public TriggerNodeMenu(G2DTree tree) {
					this.tree = tree;
					rename.addActionListener(this);
					delete.addActionListener(this);
					add(rename);
					add(delete);
				}
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == delete) {
						if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(tree, "确定 ?")) {
							removeTrigger(TriggerGroup.this, TriggerNode.this);
							tree.reload(TriggerGroup.this);
						}
					}
					else if (e.getSource() == rename) {
						Object res = JOptionPane.showInputDialog(tree, "输入名字", TriggerNode.this.getName());
						if (res != null) {
							if (TriggerNode.this.trigger.setName(triggers, res.toString())) {
								tree.reload(TriggerNode.this);
								tree.repaint();
							} else {
								JOptionPane.showMessageDialog(tree, "重复的名字!");
							}
						}
					}
				}
			}
			
		}
//		------------------------------------------------------------------------------------------------------
		
//		/**
//		 * 编辑一个触发器事件
//		 * @author WAZA
//		 */
//		public class TriggerEventNode extends G2DTreeNode<G2DTreeNode<?>>
//		{
//			Class<? extends Event>	event_type;
//			EventType				event_type_anno;
//			
//			public TriggerEventNode(Class<? extends Event> evt) {
//				this.event_type			= evt;
//				this.event_type_anno	= evt.getAnnotation(EventType.class);
//			}
//			
//			@Override
//			protected ImageIcon createIcon() {
//				return new ImageIcon(Res.icon_run);
//			}
//			
//			@Override
//			public String getName() {
//				return event_type_anno.comment();
//			}
	//
//			
//		}

	}

//	------------------------------------------------------------------------------------------------------
	
	

}
