package com.g2d.studio;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.sound.SoundFile;
import com.g2d.studio.sound.SoundList;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DWindowToolBar;

import com.g2d.awt.util.*;



@SuppressWarnings("serial")
public abstract class ManagerFormList<T extends G2DListItem> extends ManagerForm implements ActionListener
{
	final protected Vector<T>	files = new Vector<T>();
	final protected G2DList<T> 	list;
	final protected File 		save_list_file;
	final protected File 		res_root;
	
	final protected G2DWindowToolBar	tool_bar 		= new G2DWindowToolBar(this);
	
	final protected JButton				btn_refresh 	= new JButton(Tools.createIcon(Res.icon_refresh));
	final protected JButton				btn_view		= new JButton(Tools.createIcon(Res.icon_event));

	int view_index = 0;
	int[] view_modes = new int[]{
		JList.HORIZONTAL_WRAP,
		JList.VERTICAL,
		JList.VERTICAL_WRAP,
	};
	
	public ManagerFormList(
			Studio studio, 
			ProgressForm progress, 
			String title, 
			BufferedImage icon, 
			File res_root, 
			File save_list_file) 
	{
		super(studio, progress, title, icon);
		
		this.save_list_file = save_list_file;
		this.res_root		= res_root;
		
		File files[]		= res_root.listFiles();
		progress.setMaximum(title, files.length);
		for (int i=0; i<files.length; i++) {
			File file = files[i];
			T node = createNode(file);
			if (node != null) {
				this.files.add(node);
			}
			progress.setValue(title, i);
		}

		this.btn_refresh.setToolTipText("刷新");
		this.btn_refresh.addActionListener(this);
		
		this.btn_view.setToolTipText("查看");
		this.btn_view.addActionListener(this);
		
		this.tool_bar.add(btn_refresh);
		this.tool_bar.add(btn_view);
		
		this.add(tool_bar, BorderLayout.NORTH);		
		
		this.list = createList(this.files);
		this.list.setVisibleRowCount(this.files.size()/10+1);
		this.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.add(new JScrollPane(list), BorderLayout.CENTER);
		
		saveAll(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_bar.save) {
			saveAll(new SaveProgressForm());
		}
		else if (e.getSource() == btn_refresh) {
			refresh();
		}
		else if (e.getSource() == btn_view) {
			view_index ++;
			view_index %= view_modes.length;
			this.list.setLayoutOrientation(view_modes[view_index]);
		}
	}
	
	public void refresh() 
	{
		Vector<T> 		added	= new Vector<T>();
		Vector<T> 		removed	= new Vector<T>();
		File 			files[]	= res_root.listFiles();
		HashSet<T>		current = new HashSet<T>(files.length);	
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String name = asNodeName(file);
			T node = getNode(name);
			if (node == null) {
				node = createNode(file);
				if (node != null) {
					added.add(node);
				}
			}
			if (node != null) {
				current.add(node);
			}
		}
		for (T node : getNodes()){
			if (!current.contains(node)) {
				removed.add(node);
			}
		}

		if (!added.isEmpty() || !removed.isEmpty()) {
			this.files.addAll(added);
			this.files.removeAll(removed);
			this.list.setListData(this.files);
			this.list.repaint();
			saveAll(new SaveProgressForm());
		}
	}
	
	abstract protected String		asNodeName(File file);
	
	abstract protected T 			createNode(File file);
	
	abstract protected G2DList<T> 	createList(Vector<T> files);
	
	abstract protected String 		getSaveListName(T node);
	
	public Vector<T> getNodes() {
		return files;
	}
	
	public T getNode(String node_name) {
		for (T n : files) {
			if (n.getListName().equals(node_name)) {
				return n;
			}
		}
		return null;
	}
	
	
	public void saveAll(IProgress progress) 
	{
		if (progress != null) {
			progress.setMaximum(getTitle(), files.size());
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (T icon : files) {
			sb.append(getSaveListName(icon)+"\n");
			i++;
			if (progress != null) {
				progress.setValue(getTitle(), i);
			}
		}
		save_list_file.writeUTF(sb.toString());
	}
	
	@Override
	public void saveSingle() throws Throwable {}
}
