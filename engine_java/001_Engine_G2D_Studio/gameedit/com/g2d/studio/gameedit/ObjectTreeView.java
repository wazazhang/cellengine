package com.g2d.studio.gameedit;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import com.cell.rpg.RPGObject;
import com.cell.util.IDFactoryInteger;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.io.File;

public abstract class ObjectTreeView<T extends ObjectNode<D>, D extends RPGObject> 
extends JSplitPane implements TreeSelectionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	final public File						list_file;
	
	// data
	final public Class<T>					node_type;
	final public Class<D>					data_type;
//	final private RPGObjectMap<D>			data_map;
	final private ObjectGroup<T, D>			tree_root;

	// ui
	final protected G2DTree 				g2d_tree;
	final protected JScrollPane				left 		= new JScrollPane();
	final protected JPanel					right		= new JPanel();
	transient protected Component			old_right;

	protected IDFactoryInteger<T>			node_index	= new IDFactoryInteger<T>();

	
	protected ObjectTreeView(
			String title, 
			Class<T> node_type, 
			Class<D> data_type, 
			File list_file,
			ProgressForm progress) 
	{
		this.list_file		= list_file;
		this.node_type		= node_type;
		this.data_type		= data_type;		
		
		this.tree_root		= createTreeRoot(title);
		this.g2d_tree 		= createTree(tree_root);
		
		this.g2d_tree.addTreeSelectionListener(this);
		this.g2d_tree.setMinimumSize(new Dimension(200, 200));
		
		this.left.setMinimumSize(new Dimension(200, 200));
		this.left.setViewportView(g2d_tree);
		
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setLeftComponent(left);
		this.setRightComponent(right);
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	abstract protected ObjectGroup<T, D> createTreeRoot(String title);

	public G2DTree createTree(ObjectGroup<T, D> tree_root) {
		return new G2DTree(tree_root);
	}
	
	public ObjectGroup<T, D> getTreeRoot() {
		return tree_root;
	}

	final public void saveAll() {
		tree_root.saveAll();
		System.out.println(data_type.getSimpleName() + " : save all");
	}
	
	final public T saveSingle() {
		T node = getSelected();
		if (node != null) {
			tree_root.saveSingle(node);
			System.out.println(data_type.getSimpleName() + " : save single : " + node.getListName());
		}
		return node;
	}
	
	final public void saveListFile() {
		tree_root.saveListFile();
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	final public void addNode(ObjectGroup<T, D> root, T node) {
		synchronized(node_index) {
			if (node_index.storeID(node.getIntID(), node)) {
				root.add(node);
//				getTree().reload(root);
			}
		}
	}
	
	final public void removeNode(ObjectGroup<T, D> root, T node)
	{
		Integer intID = node.getIntID();
		
		synchronized(node_index) 
		{
			if ( (node_index.get(intID)==node) && (node_index.killID(intID) == node) )
			{
				if (root.isNodeChild(node))
				{
					try
					{
						root.remove(node);
					}
					catch (Exception exp)
					{
						exp.printStackTrace();
					}
				}
				getTree().reload(root);
			}
		}		
	}
	
	final public T getNode(int id) {
		synchronized(node_index) {
			return node_index.get(id);
		}
	}
	final public T getSelected() {
		synchronized(node_index) {
			MutableTreeNode node = g2d_tree.getSelectedNode();
			if (node_type.isInstance(node)) {
				return node_type.cast(node);
			}
		}
		return null;
	}
	final public Vector<T> getAllObject() {
		synchronized(node_index) {
			return G2DTree.getNodesSubClass(tree_root, node_type);
		}
	}
	
	final public G2DTree getTree() {
		return g2d_tree;
	}
	
	final public void reload() {
		TreePath path = g2d_tree.getSelectionPath();
		g2d_tree.reload();
		if (path != null) {
			try{
				g2d_tree.setSelectionPath(path);
			}catch(Exception err){}
		}
	}

	final public String getTitle() {
		return getTreeRoot().getName();
	}
	
	public void refresh(IProgress progress)
	{
		// TODO
		// do nothing
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (node_type.isInstance(e.getPath().getLastPathComponent())) {
			int tb_index = 0;
			old_right = getRightComponent();
			if (old_right instanceof ObjectViewer<?>) {
				ObjectViewer<?> old_viewer = (ObjectViewer<?>)old_right;
				tb_index = old_viewer.table.getSelectedIndex();
			}
			T node = node_type.cast(e.getPath().getLastPathComponent());
			node.onOpenEdit();
			ObjectViewer<?> new_viewer = node.getEditComponent();
			if (new_viewer!=null) {
				new_viewer.table.setSelectedIndex(tb_index);
				new_viewer.setVisible(true);
				this.setRightComponent(new_viewer);
			}
			if (old_right != null && old_right != new_viewer) {
				old_right.setVisible(false);
			}
		} else {
			right.setVisible(true);
			this.setRightComponent(right);
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JTabbedPane) {
			JTabbedPane table = (JTabbedPane)e.getSource();
			if (old_right!=null) {
				if (table.getSelectedComponent()==this) {
					old_right.setVisible(true);
				} else {
					old_right.setVisible(false);
				}
			}
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------
	

}
