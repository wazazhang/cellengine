package com.g2d.studio.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.g2d.studio.res.Res;
import com.g2d.awt.util.*;


public class G2DTree extends JTree implements G2DDragDropListener<G2DTree>
{
	public static TreeNode dragged_tree_node = null;
	
	private static final long serialVersionUID = 1L;

	final protected DefaultTreeModel 	tree_model;
	
	final protected DropTarget			drop_target;

	private ArrayList<G2DDragDropListener<G2DTree>> 
										drag_drop_listeners = new ArrayList<G2DDragDropListener<G2DTree>>(1);
	private Object 						drag_location_object;
	private int							drag_drop_position	= 0;
		
	public G2DTree(DefaultMutableTreeNode tree_root) 
	{
		tree_model = new DefaultTreeModel(tree_root);
		super.setModel(tree_model);
		
		this.setCellRenderer(new TreeRender());
		this.addMouseListener(new TreeMouseListener());
		this.addTreeSelectionListener(new TreeSelect());
		this.addKeyListener(new TreeKeyListener());
		{
//			drag_source = new DragSource();
//			drag_source.addDragSourceListener(drag_adapter);
//			drag_source.createDefaultDragGestureRecognizer(this, 0, drag_adapter);		

			DropTargetAdapter drag_adapter = new DropTargetAdapter();
			drop_target = new DropTarget(this, drag_adapter);
			//super.setTransferHandler(new TransferHandler(getClass().getCanonicalName()));
			super.setDropTarget(drop_target);
		}
		this.addDragDropListener(this);
	}
	
	public DefaultTreeModel getTreeModel(){
		return tree_model;
	}
	
	public DefaultMutableTreeNode getRoot() {
		return (DefaultMutableTreeNode)tree_model.getRoot();
	}
	
	public void expandAll() {
		for (int r=0; r<getRowCount(); r++) {
			expandRow(r);
		}
	}
	
	public void expand(TreeNode node) {
		expandPath(createTreePath(node));
	}
	
	public void reload() {
		tree_model.reload();
	}
	
	public void reload(TreeNode node) {
		tree_model.reload(node);
	}

	public MutableTreeNode getSelectedNode() {
		Object value = getLastSelectedPathComponent();
		if (value instanceof MutableTreeNode){
			MutableTreeNode node = ((MutableTreeNode)value);
			return node;
		}
		return null;
	}
	
	public void addDragDropListener(G2DDragDropListener<G2DTree> listener) {
		drag_drop_listeners.add(listener);
	}
	public void removeDragDropListener(G2DDragDropListener<G2DTree> listener) {
		drag_drop_listeners.remove(listener);
	}

	protected SearchDialog openSearchDialog() {
		SearchDialog dialog = new SearchDialog(this);
		dialog.setVisible(true);
		return dialog;
	}
	
	protected void onSelectChanged(TreeNode node) {
		
	}
	
	protected void onDragDropChanged(
			MutableTreeNode src_node,
			MutableTreeNode dst_node, 
			MutableTreeNode src_parent,
			MutableTreeNode dst_parent) 
	{

	}
	
	@Override
	public void onDragDrop(G2DTree comp, Transferable t, Object src, Object dst) {
		if (checkDrag(drop_target, null, src, dst, drag_drop_position)) {
			Map<TreeNode, TreePath> expan = storeAllExpandState();
			try{
				MutableTreeNode src_node = (MutableTreeNode)src;
				MutableTreeNode dst_node = (MutableTreeNode)dst;
				MutableTreeNode src_parent = (MutableTreeNode)src_node.getParent();
				MutableTreeNode dst_parent = (MutableTreeNode)dst_node.getParent();
				
				// 添加到根节点
				if (dst_node == tree_model.getRoot()) {
					dst_node.insert(src_node, 0);
					onDragDropChanged(src_node, dst_node, src_parent, dst_parent);
					reload(dst_node);
//					System.out.println("添加到根节点");
				}
				// 如果是插入
				else if (drag_drop_position != 0) {
					src_node.removeFromParent();
					int insert_offset = drag_drop_position > 0 ? 1 : 0;
					dst_parent.insert(src_node, dst_parent.getIndex(dst_node) + insert_offset);
					onDragDropChanged(src_node, dst_node, src_parent, dst_parent);
					reload(src_parent);
					reload(dst_parent);
				}
				// 添加到组节点
				else if (dst_node instanceof G2DTreeNodeGroup<?>) {
					G2DTreeNodeGroup<?> dst_group = (G2DTreeNodeGroup<?>)dst_node;
					dst_group.add(src_node);
					onDragDropChanged(src_node, dst_node, src_parent, dst_parent);
					reload(src_parent);
					reload(dst_node);
//					System.out.println("添加到组节点");
				}
				else if (dst_node instanceof G2DTreeNode<?> && 
						dst_node.getAllowsChildren()) 
				{
					((G2DTreeNode<?>)dst_node).add(src_node);
					onDragDropChanged(src_node, dst_node, src_parent, dst_parent);
					reload(src_parent);
					reload(dst_node);
//					System.out.println("添加到组节点");
				}
				// 同一层次的移动
				else if (src_node.getParent() == dst_node.getParent()) {				
					src_node.removeFromParent();
					dst_parent.insert(src_node, dst_parent.getIndex(dst_node));
					onDragDropChanged(src_node, dst_node, src_parent, dst_parent);
					reload(dst_parent);
//					System.out.println("同一层次的移动");
				}
				// 不同层次的移动
				else {
					src_node.removeFromParent();
					dst_parent.insert(src_node, dst_parent.getIndex(dst_node));
					onDragDropChanged(src_node, dst_node, src_parent, dst_parent);
					reload(src_parent);
					reload(dst_parent);
//					System.out.println("不同层次的移动");
				}
			}
			finally{
				restoreAllExpandState(expan);
			}
		}
	}
	
	/**
	 * @param evt_source
	 * @param trans TODO
	 * @param src
	 * @param dst
	 * @param position 等于 0 代表放置，大于0代表向下插入，小于0代表向下插入
	 * @return
	 */
	protected boolean checkDrag(DropTarget evt_source, Transferable trans, Object src, Object dst, int position) {
		if (dst == null) {
			return false;
		}
		// 不是本控件的节点
		if (evt_source != drop_target) {
			return false;
		}
		TreeNode tsrc	= (TreeNode)src;
		TreeNode tdst	= (TreeNode)dst;
		// 不能自己放到自己上
		if (tsrc == tdst) {
			return false;
		}
		// 根节点不能被拖动
		if (tsrc == tree_model.getRoot()) {
			return false;
		}
		// 父节点不能被拖动到子节点
		if (containsNode(tsrc, tdst)) {
			return false;
		}
		// 父节点不能被拖动到子节点
		if (!containsNode(getRoot(), tsrc)) {
			return false;
		}
		if (!containsNode(getRoot(), tdst)) {
			return false;
		}
		return true;
	}

	/**
	 * position 等于 0 代表放置，大于0代表向下插入，小于0代表向下插入
	 * @return
	 */
	public int getDragDropPosition() {
		return drag_drop_position;
	}

//	/**
//	 * @param dtde
//	 * @return 是否截获此事件
//	 */
//	protected boolean dropDirect(DropTargetDropEvent dtde, ) {
//		return false;
//	}
//	/**
//	 * @param dtde
//	 * @return 是否截获此事件
//	 */
//	protected boolean dragOverDirect(DropTargetDragEvent dtde) {
//		return false;
//	}
//	----------------------------------------------------------------------------------------------------------------------------

	public TreePath createTreePath(TreeNode node) {
		ArrayList<TreeNode> path = new ArrayList<TreeNode>();
		while (node != null) {
			path.add(0, node);
			node = node.getParent();
		} 
		return new TreePath(path.toArray());
	}
	
	public Map<TreeNode, TreePath> storeAllExpandState()
	{
		Vector<TreeNode> nodes = getNodesSubClass((TreeNode)tree_model.getRoot(), TreeNode.class);
		Map<TreeNode, TreePath> states = new HashMap<TreeNode, TreePath>();
		for (TreeNode node : nodes) {
			TreePath path = createTreePath(node);
			if (isExpanded(path)) {
				states.put(node, path);
			}
		}
		return states;
	}
	
	public void restoreAllExpandState(Map<TreeNode, TreePath> states)
	{
		for (TreePath path : states.values()) {
			try{
				expandPath(path);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
	}
	
	
	public TreePath setSelectionNode(TreeNode node)
	{
		if (node != null) {
			TreePath path = createTreePath(node);
			this.setSelectionPath(path);
			return path;
		} else {
			this.clearSelection();
			return null;
		}
	}
//	----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 获取所有叶子节点
	 * @param root
	 * @return
	 */
	static public Vector<TreeNode> getNodes(TreeNode root)
	{
		Vector<TreeNode> ret = new Vector<TreeNode>();
		getNodesSubClass(root, TreeNode.class, ret);
		return ret;
	}
	
	/**
	 * 获取所有叶子节点
	 * @param root
	 * @return
	 */
	static public<T extends TreeNode> Vector<T> getNodesSubClass(TreeNode root, Class<T> type)
	{
		Vector<T> ret = new Vector<T>();
		getNodesSubClass(root, type, ret);
		return ret;
	}
	
	/**
	 * 获取所有叶子节点
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static public<T extends TreeNode> void getNodesSubClass(TreeNode root, Class<T> type, Vector<T> list)
	{
		Enumeration files = root.children();
		while(files.hasMoreElements()) {
			MutableTreeNode node = (MutableTreeNode)files.nextElement();
			if (type.isInstance(node)) {
				list.add(type.cast(node));
			}
			//深度遍历
			getNodesSubClass(node, type, list);
		}
	}

	/**
	 * 遍历所有节点
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static public void getAllNodes(TreeNode root, Vector<TreeNode> ret)
	{
		ret.add(root);
		//深度遍历
		Enumeration files = root.children();
		while(files.hasMoreElements()) {
			TreeNode node = (TreeNode)files.nextElement();
			getAllNodes(node, ret);
		}
	}


	/**
	 * 遍历所有节点
	 * @param root
	 * @return
	 */
	static public Vector<TreeNode> getAllNodes(TreeNode root)
	{
		Vector<TreeNode> ret = new Vector<TreeNode>();
		getAllNodes(root, ret);
		return ret;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	static public G2DTreeNode<?> getNode(TreeNode root, String ... path)
	{
		TreeNode node = root;
		for (String name : path) {
			node = getNode(node, name);
			if (node == null) {
				return null;
			}
		}
		return (G2DTreeNode<?>)node;
	}
	
	@SuppressWarnings("unchecked")
	static public G2DTreeNode getNode(TreeNode root, String name) 
	{
		try{
			Enumeration files = root.children();
			while(files.hasMoreElements()) {
				G2DTreeNode node = (G2DTreeNode)files.nextElement();
				if (node.getName().equals(name)) {
					return node;
				}
			}
		}catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	static public boolean containsNode(TreeNode root, Object node) {
		if (root != null) {
			Enumeration files = root.children();
			while(files.hasMoreElements()) {
				TreeNode c = (TreeNode)files.nextElement();
				if (containsNode(c, node)) {
					return true;
				}
				if (c == node) {
					return true;
				}
			}
		}
		return false;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	
	protected class TreeRender extends DefaultTreeCellRenderer
	{
		private static final long serialVersionUID = 1L;
		
		Object current_value;
		
		Color drag_location_color = new Color(0x80000000, true);
		
		@Override
		public Component getTreeCellRendererComponent(
				JTree tree, 
				Object value,
				boolean sel, 
				boolean expanded, 
				boolean leaf,
				int row,
				boolean hasFocus) 
		{
			current_value = value;
			Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus);
			if (value instanceof G2DTreeNode<?>){
				G2DTreeNode<?> node = ((G2DTreeNode<?>)value);
				ImageIcon aicon = node.getIcon(false);
				if (aicon != null) {
					setIcon(aicon);
					comp.setSize(aicon.getIconWidth(), aicon.getIconHeight());
				}
			}
			else if (value instanceof G2DTreeNodeGroup<?>) {
				G2DTreeNodeGroup<?> group = ((G2DTreeNodeGroup<?>)value);
				Icon aicon = group.getIcon();
				if (aicon != null) {
					setIcon(aicon);
					comp.setSize(aicon.getIconWidth(), aicon.getIconHeight());
				} else {
					if (expanded) {
						aicon = getOpenIcon();
					} else {
						aicon = getClosedIcon();
					}
					setIcon(aicon);
				}
			}
			return comp;
		}
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			
			if (drag_location_object == current_value) 
			{
				if (current_value == tree_model.getRoot())
				{
					g.setColor(drag_location_color); 
					g.fillRect(0, 0, getWidth(), getHeight());
				}
				else if (((TreeNode)current_value).getAllowsChildren()) 
				{
					if (drag_drop_position == 0) {
						g.setColor(drag_location_color); 
						g.fillRect(0, 0, getWidth(), getHeight());
					} else if (drag_drop_position>0) {
						g.setColor(Color.BLACK); 
						g.fillRect(0, getHeight()-2, getWidth(), 2);
					} else if (drag_drop_position<0) {
						g.setColor(Color.BLACK); 
						g.fillRect(0, 0, getWidth(), 2);
					}
				}
				else {
					if (drag_drop_position>0) {
						g.setColor(Color.BLACK); 
						g.fillRect(0, getHeight()-2, getWidth(), 2);
					} else {
						g.setColor(Color.BLACK); 
						g.fillRect(0, 0, getWidth(), 2);
					}
				}
			}
		}
	}
	
	
	protected class TreeSelect implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e) {
			G2DTree tree = G2DTree.this;
			Object value = getLastSelectedPathComponent();
			if (value instanceof G2DTreeNode<?>){
				G2DTreeNode<?> node = ((G2DTreeNode<?>)value);
				node.onSelected(tree);
				onSelectChanged(node);
			} else {
				onSelectChanged(null);
			}
		}
	}
	
	protected class TreeKeyListener extends KeyAdapter
	{
		SearchDialog sd = null;
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
//				if (sd != null) {
//					sd.setVisible(true);
//				} else {
//					sd = 
					openSearchDialog();
//				}
			}
		}
	}
	
	protected class TreeMouseListener extends MouseAdapter
	{
		@Override
		public void mouseDragged(MouseEvent e) {
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
//			drag_location_object = null;
		}
		@Override
		public void mouseExited(MouseEvent e) {
//			drag_location_object = null;
		}
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			G2DTree tree = G2DTree.this;
			TreePath selPath = getPathForLocation(e.getX(), e.getY());
			tree.setSelectionPath(selPath);
			dragged_tree_node = getSelectedNode();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			G2DTree tree = G2DTree.this;
			int selRow = getRowForLocation(e.getX(), e.getY());
			TreePath selPath = getPathForLocation(e.getX(), e.getY());
			tree.setSelectionPath(selPath);
			
			if (selRow != -1) 
			{
				Object obj = selPath.getLastPathComponent();

				if (obj instanceof G2DTreeNode<?>) 
				{
					G2DTreeNode<?> node = (G2DTreeNode<?>)obj;
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (e.getClickCount() == 1) {
							node.onClicked(tree, e);
						}
						else if (e.getClickCount() == 2) {
							node.onDoubleClicked(tree, e);
						}
					}
					else if (e.getButton() == MouseEvent.BUTTON3){
						node.onRightClicked(tree, e);
					}
				}
				else if (obj instanceof G2DTreeNodeGroup<?>)
				{
					G2DTreeNodeGroup<?> group = (G2DTreeNodeGroup<?>)obj;
					group.onClicked(tree, e);
				}
			}
		}
		
		
	}
	
	protected class DropTargetAdapter implements DropTargetListener
	{
		@Override
		final public void dragEnter(DropTargetDragEvent dtde) {
			drag_location_object = null;
			G2DTree.this.repaint();
		}
		@Override
		final public void dragExit(DropTargetEvent dte) {
			drag_location_object = null;
			G2DTree.this.repaint();
		}
		@Override
		final public void dragOver(DropTargetDragEvent dtde) {
			drag_location_object = null;
//			if (!dragOverDirect(dtde)) {
				TreePath path = G2DTree.this.getPathForLocation(
						dtde.getLocation().x, 
						dtde.getLocation().y);
				if (path!=null) {
					{
						Rectangle comp = getPathBounds(path);
						int dy	= dtde.getLocation().y - (comp.y + comp.height/2);
						int div	= comp.height / 4;
						drag_drop_position = dy / div;
					}
					drag_location_object = path.getLastPathComponent();
					if (checkDrag((DropTarget)dtde.getSource(), 
							dtde.getTransferable(), 
							getSelectedNode(), 
							drag_location_object, 
							drag_drop_position)) {
						dtde.acceptDrag(dtde.getDropAction());
					} else {
						drag_location_object = null;
						dtde.rejectDrag();
					}
				}
//			}
			G2DTree.this.repaint();
		}
		
		@Override
		final public void drop(DropTargetDropEvent dtde) {
			drag_location_object = null;
			TreePath path = G2DTree.this.getPathForLocation(
					dtde.getLocation().x, 
					dtde.getLocation().y);
			if (path!=null) {
				{
					Rectangle comp = getPathBounds(path);
					int dy	= dtde.getLocation().y - (comp.y + comp.height/2);
					int div	= comp.height / 4;
					drag_drop_position = dy / div;
				}
				if (checkDrag((DropTarget)dtde.getSource(), 
						dtde.getTransferable(), 
						getSelectedNode(), 
						path.getLastPathComponent(),
						drag_drop_position)) {
					TreeNode sender		= G2DTree.this.getSelectedNode();
					TreeNode reciver	= (TreeNode)path.getLastPathComponent();
					for (G2DDragDropListener<G2DTree> l : drag_drop_listeners) {
						l.onDragDrop(G2DTree.this, dtde.getTransferable(), sender, reciver);
					}
				}
			}
			G2DTree.this.repaint();
		}
		@Override
		final public void dropActionChanged(DropTargetDragEvent dtde) {
			drag_location_object = null;
			G2DTree.this.repaint();
		}
		
	
	}
	
	@SuppressWarnings("serial")
	public static class SearchDialog extends AbstractFrame implements ActionListener
	{
		private static String history_text = "";
		
		private G2DTree 		tree;

//		--------------------------------------------------------------------------------------------
		protected JPanel		center 		= new JPanel(null);
		
		protected JLabel		lbl_as_name	= new JLabel("表达式");
		protected JTextField 	txt_as_name	= new JTextField(history_text);
		
//		--------------------------------------------------------------------------------------------
		protected JButton 		next 		= new JButton("下一个");	
		protected JButton 		prew		= new JButton("上一个");		
		protected JButton 		cancel 		= new JButton("取消");
//		--------------------------------------------------------------------------------------------
	
		
		public SearchDialog(G2DTree tree) 
		{
			super.setAlwaysOnTop(true);
			super.setIconImage(Res.icon_edit);
			super.setTitle("查找 "+tree.getRoot());
			super.setSize(320, 160);
			
			this.setCenter();
			this.tree = tree;
			
			add(center, BorderLayout.CENTER);
			{
				lbl_as_name	.setBounds(10, 10, 50,  30);
				txt_as_name	.setBounds(62, 12, getWidth()-20-52, 30);
				
				center		.add(lbl_as_name);
				center		.add(txt_as_name);
				
				prew		.addActionListener(this);
				next		.addActionListener(this);
				cancel		.addActionListener(this);

				next		.setBounds(getWidth()-110, 50, 100,  30);
				prew		.setBounds(getWidth()-218, 50, 100,  30);
				cancel		.setBounds(getWidth()-110, 90, 100,  30);
				
				next		.setToolTipText("Enter");
				prew		.setToolTipText("Shift+Enter");
				
				center		.add(prew);
				center		.add(next);
				center		.add(cancel);
				
				KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
				cancel.registerKeyboardAction(this, "cancel", ks1, JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				KeyStroke ks2 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_MASK);
				prew.registerKeyboardAction(this, "prew", ks2, JComponent.WHEN_IN_FOCUSED_WINDOW);
				
				KeyStroke ks3 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
				next.registerKeyboardAction(this, "next", ks3, JComponent.WHEN_IN_FOCUSED_WINDOW);
			}
			
		}
		
		public G2DTree getTree() {
			return tree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == next) {
				TreeNode node = getUserObject(false);
				if (node != null) {
					TreePath path = tree.createTreePath(node);
					tree.expand(node);
					tree.setSelectionPath(path);
					tree.scrollPathToVisible(path);
				}
			}  
			else if (e.getSource() == prew) {
				TreeNode node = getUserObject(true);
				if (node != null) {
					TreePath path = tree.createTreePath(node);
					tree.expand(node);
					tree.setSelectionPath(path);
					tree.scrollPathToVisible(path);
				}
			}
			else if (e.getSource() == cancel) {
				this.setVisible(false);
			}
		}

		protected TreeNode getUserObject(boolean reverse)
		{
			try {
				history_text = txt_as_name.getText();
				if (!txt_as_name.getText().isEmpty()) 
				{
					Pattern pattern = Pattern.compile(txt_as_name.getText());
					
					TreeNode current = tree.getSelectedNode();
					if (current == tree.getRoot()) {
						current = null;
					}
					
					Vector<TreeNode> nodes = getNodes(tree.getRoot());
					
					if (reverse) {
						Collections.reverse(nodes);
					}
					
					for (TreeNode node : nodes) {
						if (current != null) {
							if (node == current) {
								current = null;
							}
						} else {
							String text = node.toString();
							if (node instanceof G2DTreeNode<?>) {
								text = ((G2DTreeNode<?>)node).getName();
							}
							if (node instanceof G2DTreeNodeGroup<?>) {
								text = ((G2DTreeNodeGroup<?>)node).getName();
							}
							Matcher matcher_name = pattern.matcher(text);
							if(matcher_name.find()){
								System.out.println(text);
								return node;
							}
						}
					}
				}
			} catch (Exception err) {
//				err.printStackTrace();
			}
			return null;
		}

	}
	
}
