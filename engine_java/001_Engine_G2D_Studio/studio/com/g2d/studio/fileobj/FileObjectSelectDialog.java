package com.g2d.studio.fileobj;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.cell.CUtil;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.fileobj.FileObject.WrapObject;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeListView;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeListView.NodeGroup;

@SuppressWarnings("serial")
public class FileObjectSelectDialog<T extends FileObject> extends AbstractDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static HashMap<Class<?>, Object> histroy_selected = new HashMap<Class<?>, Object>();
	
	TreeListView list;
	
	JButton ok 		= new JButton("确定");
	JButton cancel 	= new JButton("取消");
	
	WrapObject object;
			
	public FileObjectSelectDialog(Component owner, FileObjectView<T> v, T default_value)
	{
		super(owner);
		super.setLayout(new BorderLayout());
		super.setSize(600, 400);
		super.setCenter();
		
		this.list = new TreeListView(v);
		this.add(list, BorderLayout.CENTER);
		this.object = null;
		{
			JPanel south = new JPanel(new FlowLayout());
			
			south.add(ok);
			ok.addActionListener(this);
			
			south.add(cancel);
			cancel.addActionListener(this);
			
			this.add(south, BorderLayout.SOUTH);
		}
		
		if (default_value != null) {
			list.setSelectedItem(default_value, true);
			object = list.getSelectedItem();
		} else {
			try {
				Object tobj = list.getItems().firstElement();
				Object obj = histroy_selected.get(tobj.getClass());
				if (obj != null) {
					list.setSelectedItem(obj, true);
				}
			} catch (Exception err) {
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public T getSelectedObject() {
		if (object != null) {
			return (T) (object.src);
		}
		return null;
	}

	public TreeListView getList() {
		return list;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			if (checkOK()) {
				object = list.getSelectedItem();
				if (object != null) {
					histroy_selected.put(object.getClass(), object);
				}
				this.setVisible(false);
			}
		} else if (e.getSource() == cancel) {
			list.setSelectedItem(null, false);
			this.setVisible(false);
		}
	}
	
	public T showDialog() {		
		new Thread(){
			public void run() {
				try {
					Thread.sleep(100);
					int index = list.getList().getSelectedIndex();
					list.getList().scrollRectToVisible(list.getList().getCellBounds(index, index));
				} catch (Exception err) {}
			}
		}.start();
		super.setVisible(true);
		return getSelectedObject();
	}
	
	protected boolean checkOK() {
		return true;
	}
	
	
	class TreeListView extends G2DTreeListView<WrapObject>
	{
		public TreeListView(FileObjectView<T> other)
		{
			super(cloneRoot(other.getRoot()));
			super.getTree().setDragEnabled(false);
			super.getList().setDragEnabled(false);
			super.getList().setVisibleRowCount(other.getList().getVisibleRowCount());
			super.getList().setLayoutOrientation(other.getList().getLayoutOrientation());
		}
	}
	
	class StaticGroup extends NodeGroup<WrapObject>
	{
		public StaticGroup(String name) {
			super(name);
		}
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new StaticGroup(name);
		}
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
		}
	}

	private NodeGroup<WrapObject> cloneRoot(NodeGroup<T> src) {
		StaticGroup dst = new StaticGroup(src.getName());
		cloneChilds(src, dst);
		return dst;
	}
	
	@SuppressWarnings("unchecked")
	private void cloneChilds(NodeGroup<T> src, StaticGroup dst) {
		for (G2DListItem item : src.getItems()) {
			dst.addItem(((FileObject)item).clone());
		}
		Enumeration<?> childs = src.children();
		while (childs.hasMoreElements()) {
			NodeGroup<T> s = (NodeGroup<T>)childs.nextElement();
			StaticGroup  d = new StaticGroup(s.getName());
			dst.add(d); 
			cloneChilds(s, d) ;
		}
	}
	
}

