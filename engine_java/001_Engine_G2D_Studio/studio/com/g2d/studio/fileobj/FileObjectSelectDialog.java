package com.g2d.studio.fileobj;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.cell.CUtil;
import com.g2d.awt.util.AbstractDialog;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeListView;
import com.g2d.studio.swing.G2DTreeListView.NodeGroup;

@SuppressWarnings("serial")
public class FileObjectSelectDialog<T extends FileObject> extends AbstractDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static HashMap<Class<?>, Object> histroy_selected = new HashMap<Class<?>, Object>();
	
	TreeListView list;
	
	JButton ok 		= new JButton("确定");
	JButton cancel 	= new JButton("取消");
	
	T object;
			
	public FileObjectSelectDialog(Component owner, FileObjectView<T> list, T default_value)
	{
		super(owner);
		super.setLayout(new BorderLayout());
		super.setSize(600, 400);
		super.setCenter();
		
		this.list = new TreeListView(list);
		this.add(list, BorderLayout.CENTER);
		
		this.object = default_value;
		
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
	
	public T getSelectedObject() {
		return object;
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
		return object;
	}
	
	protected boolean checkOK() {
		return true;
	}
	
	
	class TreeListView extends G2DTreeListView<T>
	{
		public TreeListView(FileObjectView<T> other)
		{
			super(cloneRoot(other.getRoot()));
		}
	}
	

	public NodeGroup<T> cloneRoot(NodeGroup<T> src) {
		NodeGroup<T> root = new NodeGroup<T>(src.getName());
		cloneChilds(root, src);
		return root;
	}
	
	@SuppressWarnings("unchecked")
	private void cloneChilds(NodeGroup<T> src, NodeGroup<T> dst) {
		for (G2DListItem item : src.getItems()) {
			dst.addItem(((FileObject)item).clone());
		}
		Enumeration<?> childs = src.children();
		while (childs.hasMoreElements()) {
			NodeGroup<T> s = (NodeGroup<T>)childs.nextElement();
			NodeGroup<T> d = new NodeGroup<T>(s.getName());
			dst.add(d); 
			cloneChilds(s, d) ;
		}
	}
	
}

