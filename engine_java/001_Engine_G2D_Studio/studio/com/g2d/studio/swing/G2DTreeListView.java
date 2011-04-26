package com.g2d.studio.swing;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.TooManyListenersException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.cell.rpg.RPGObject;
import com.cell.util.IDFactoryInteger;
import com.g2d.awt.util.DragAndDrop;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.studio.io.File;

@SuppressWarnings("serial")
public abstract class G2DTreeListView<T extends G2DListItem> extends JSplitPane implements TreeSelectionListener
{
	private static final long serialVersionUID = 1L;
		
	// data
	final private NodeGroup<T> tree_root;

	final protected TreeView g2d_tree;
	final protected ListView g2d_list;

//	final private AtomicReference<T> draged_item = new AtomicReference<T>();

	final private JPanel blank_right = new JPanel();
	
	protected G2DTreeListView(NodeGroup<T> root) 
	{
		this.tree_root		= root;
		this.g2d_tree 		= createTree(tree_root);
		this.g2d_tree		.setDragEnabled(true);
		this.g2d_tree		.addTreeSelectionListener(this);
		this.g2d_tree		.setMinimumSize(new Dimension(200, 200));
		
		this.g2d_list		= createList();
		
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setLeftComponent(new JScrollPane(g2d_tree));
		this.setRightComponent(new JScrollPane(g2d_list));
		
		this.getLeftComponent()	
		.setMinimumSize(new Dimension(200, 200));
		this.getRightComponent()	
		.setMinimumSize(new Dimension(200, 200));
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	protected TreeView createTree(NodeGroup<T> tree_root) {
		return new TreeView(tree_root);
	}
	
	protected ListView createList() {
		return new ListView();
	}

	public ListView getList() {
		return g2d_list;
	}

	public TreeView getTree() {
		return g2d_tree;
	}
	
	@SuppressWarnings("unchecked")
	public void reloadList() {
		TreeNode node = g2d_tree.getSelectedNode();
		if (node instanceof NodeGroup<?>) {
			g2d_list.setListData(((NodeGroup) node).getItems());
		} else {
			g2d_list.setListData(new Object[]{});
		}
	}
	
	@SuppressWarnings("unchecked")
	public Vector<T> getItems() {
		Vector<T> ret = new Vector<T>();
		for (TreeNode n : G2DTree.getAllNodes(tree_root)) {
			if (n instanceof NodeGroup<?>) {
				ret.addAll(((NodeGroup) n).items.values());
			}
		}
		return ret;
	}
	
	public boolean addItem(NodeGroup<T> root, T item) {
		return root.addItem(item);
	}

	public NodeGroup<T> getRoot() {
		return tree_root;
	}
	
	public T getSelectedItem() {
		return g2d_list.getSelectedItem();
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public void setSelectedItem(Object item, boolean shouldScroll) {
		if (item != null) {
			for (TreeNode node : g2d_tree.getAllNodes(getRoot())) {
				NodeGroup<T> n = (NodeGroup)node;
				if (n.items.containsValue(item)) {
					g2d_list.setListData(n.getItems());
					g2d_list.setSelectedValue(item, shouldScroll);
					g2d_list.repaint();
					return;
				}
			}
		} else {
			g2d_list.setSelectedValue(null, shouldScroll);
		}
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public void removeItem(G2DListItem item) {
		if (item != null) {
			for (TreeNode node : g2d_tree.getAllNodes(getRoot())) {
				NodeGroup<T> n = (NodeGroup)node;
				if (n.items.containsValue(item)) {
					n.removeItem(item.getListName());
				}
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	
	final public void reload() {
		TreePath path = g2d_tree.getSelectionPath();
		g2d_tree.reload();
		if (path != null) {
			try{
				g2d_tree.setSelectionPath(path);
			}catch(Exception err){}
		}
	}
	
	final public void reload(NodeGroup<?> node) {
		TreePath path = g2d_tree.getSelectionPath();
		g2d_tree.reload(node);
		if (path != null) {
			try{
				g2d_tree.setSelectionPath(path);
			}catch(Exception err){}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public void valueChanged(TreeSelectionEvent e) {
		if (e.getPath().getLastPathComponent() instanceof NodeGroup<?>) {
			NodeGroup group = (NodeGroup)e.getPath().getLastPathComponent();
			this.g2d_list.setListData(new Vector(group.items.values()));
		} else {
			blank_right.setVisible(true);
			this.setRightComponent(blank_right);
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------
	
	private class DragItem implements Transferable
	{
		Object[] items;
		
		public DragItem(Object[] items) {
			this.items = items;
		}
		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return items;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] {new DataFlavor(G2DListItem.class, "g2dlistview.item")};
		}
		
		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.getHumanPresentableName().equals("g2dlistview.item");
		}
	}
	
//	private class ItemDropTarget extends DropTarget
//	{
//		ListView view;
//		public ItemDropTarget(ListView v) {
//			super(v, new DropTargetListener() {
//				@Override
//				public void dropActionChanged(DropTargetDragEvent dtde) {
//					System.out.println("dropActionChanged");
//				}
//				@Override
//				public void drop(DropTargetDropEvent dtde) {
//					System.out.println("drop");
//				}
//				@Override
//				public void dragOver(DropTargetDragEvent dtde) {
//					System.out.println("dragOver");
////					draged_item.set(getSelectedItem());
//				}
//				@Override
//				public void dragExit(DropTargetEvent dte) {
//					System.out.println("dragExit");
//				}
//				@Override
//				public void dragEnter(DropTargetDragEvent dtde) {
//					System.out.println("dragEnter");
//				}
//			});
//			this.view = v;
//		}
//	}
	
	public class ListView extends G2DList<T>
	{
		public ListView() {
			this.setDragEnabled(true);	
			this.setVisibleRowCount(0);
			this.setAutoscrolls(false);

//			super.setDropTarget(new ItemDropTarget(this));
			this.setTransferHandler(new TransferHandler("") {
				@Override
				protected Transferable createTransferable(JComponent c) {
//					return super.createTransferable(c);
					return new DragItem(getSelectedItems());
				}
				public int getSourceActions(JComponent c) {
				    return MOVE;
				}
				public void exportDone(JComponent c, Transferable t, int action) {
//				    if (action == MOVE) {
//				        c.removeSelection();
//				    }
				}
			});
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	public class TreeView extends G2DTree
	{
		
		public TreeView(NodeGroup<T> tree_root) {
			super(tree_root);
		}
		
		@Override
		protected boolean checkDrag(DropTarget evtSource, Transferable trans, Object src, Object dst, int position) {
			if (src != null && dst != null && src != dst) {
				Object[] items = DragAndDrop.getTransferData(trans, Object[].class);
				if (items != null) {
					return true;
				}
			}
			return super.checkDrag(evtSource, trans, src, dst, position);
		}
		
		@Override
		public void onDragDrop(G2DTree comp, Transferable t, Object src, Object dst) {
			Object[] items = DragAndDrop.getTransferData(t, Object[].class);
			if (items != null) {
				NodeGroup<?> sg = (NodeGroup<?>)src;
				NodeGroup<?> dg = (NodeGroup<?>)dst;
				if (getDragDropPosition() != 0) {
					dg = (NodeGroup<?>)dg.getParent();
				}
				for (Object o : items) {
					G2DListItem item = (G2DListItem)o;
					sg.removeItem(item.getListName());
					dg.addItem(item);
					reloadList();
				}
				return;
			}
			super.onDragDrop(comp, t, src, dst);
		}
		
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	abstract static public class NodeGroup<T extends G2DListItem> extends G2DTreeNodeGroup<G2DTreeNode<?>>
	{
		protected LinkedHashMap<String, G2DListItem> items = new LinkedHashMap<String, G2DListItem>();
		
		public NodeGroup(String name) {
			super(name);
		}
		
		public boolean addItem(G2DListItem item) {
			if (!items.containsKey(item.getListName())) {
				items.put(item.getListName(), item);
				return true;
			}
			return false;
		}
		
		public G2DListItem removeItem(String name) {
			return items.remove(name);
		}
		
		public Vector<G2DListItem> getItems() {
			return new Vector<G2DListItem>(items.values());
		}
		
		@Override
		protected boolean pathAddLeafNode(String name, int index, int length) {
			return false;
		}
		
		@Override
		protected G2DTreeNodeGroup<?> pathCreateGroupNode(String name) {
			return null;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new NodeGroupMenu(this, tree, (G2DTree)tree).show(tree, e.getX(), e.getY());
			}
		}
	}
	
	
	
	static public class NodeGroupMenu extends GroupMenu
	{
		public NodeGroupMenu(G2DTreeNodeGroup<?> root, Component window, G2DTree g2dTree) {
			super(root, window, g2dTree);
		}
	}
	

//	-----------------------------------------------------------------------------------------------------------------------
	

}
