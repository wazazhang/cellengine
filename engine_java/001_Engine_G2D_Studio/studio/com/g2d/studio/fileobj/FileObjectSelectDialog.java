package com.g2d.studio.fileobj;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
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

public class FileObjectSelectDialog<T extends FileObject> extends AbstractDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static HashMap<Class<?>, Object> histroy_selected = new HashMap<Class<?>, Object>();
	
	TreeListView<T> list;
	
	JButton ok 		= new JButton("确定");
	JButton cancel 	= new JButton("取消");
	
	T object;
			
	public FileObjectSelectDialog(Component owner, FileObjectView<T> list, T default_value)
	{
		super(owner);
		super.setLayout(new BorderLayout());
		super.setSize(600, 400);
		super.setCenter();
		
		this.list = list;
		this.list.setSize(getSize());
		this.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);		
		this.list.setVisibleRowCount(0);
		this.add(new JScrollPane(list), BorderLayout.CENTER);
		
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
			list.setSelectedValue(default_value, true);
		} else {
			try {
				Object tobj = list.getModel().getElementAt(0);
				Object obj = histroy_selected.get(tobj.getClass());
				if (obj != null) {
					list.setSelectedValue(obj, true);
				}
			} catch (Exception err) {
			}
		}
	}
	
	
	
	protected G2DList<T> getList(){
		return this.list;
	}
	
	public T getSelectedObject() {
		return object;
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
			list.setSelectedValue(null, false);
			this.setVisible(false);
		}
	}
	
	public T showDialog() {		
		new Thread(){
			public void run() {
				try {
					Thread.sleep(100);
					int index = list.getSelectedIndex();
					list.scrollRectToVisible(list.getCellBounds(index, index));
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
			super(other.cloneRoot());
		}
		
		
	}
}

