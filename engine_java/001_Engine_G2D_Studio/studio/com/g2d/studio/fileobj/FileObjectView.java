package com.g2d.studio.fileobj;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.cell.CUtil;
import com.g2d.awt.util.Tools;
import com.g2d.studio.SaveProgressForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeListView;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.talks.TalkFile;

@SuppressWarnings("serial")
public abstract class FileObjectView<T extends FileObject> extends G2DTreeListView<T>
{
	final String title;
	
	final protected File 		save_list_file;
	final protected File 		res_root;

	final private Map<File, T> nodes_file = new HashMap<File, T>();
	final private Map<String, T> nodes_name = new HashMap<String, T>();
	
	public FileObjectView(
			String title,
			ProgressForm progress, 
			File res_root, 
			File save_list_file)
	{
		super(new FileGroup<T>(title));
		this.save_list_file = save_list_file;
		this.res_root = res_root;		
		this.title = title;
		
		refresh(progress);
		
//		this.getList().setVisibleRowCount(this.files.size()/10+1);
//		this.getList().setLayoutOrientation(JList.HORIZONTAL_WRAP);

	}

	abstract public T	createItem(File file);

//	--------------------------------------------------------------------------------------------------------------
	
	public String getListFileData() {
		StringBuilder new_list = new StringBuilder();
		for (T o : nodes_file.values()) {
			new_list.append(CUtil.arrayToString(o.path, "/") + o.getSaveListName() + "\n");
		}
		return new_list.toString();
	}
	
	public Vector<T> getNodes() {
		return new Vector<T>(nodes_name.values());
	}
	
	public T getNode(String node_name) {
		return nodes_name.get(node_name);
	}
	
	public int getNodeCount() {
		return nodes_name.size();
	}
	
	synchronized public void refresh(IProgress progress)
	{
		Vector<T> 		added	= new Vector<T>();
		Vector<T> 		removed	= new Vector<T>(nodes_file.values());
		
		File 			files[]	= res_root.listFiles();
		
		if (progress != null) progress.setMaximum(title, files.length);
		int i = 0;
		for (File file : files) {
//			System.out.println(file);
			T node = this.nodes_file.get(file);
			if (node == null) {
				node = createItem(file);
				if (node != null) {
					added.add(node);
				}
			} else {
				removed.remove(node);
			}
			if (progress != null) progress.setValue(title, i++);
		}
		
		System.out.println(title + " : add " + added.size());
		for (T item : added) {
			nodes_file.put(item.getFile(), item);
			nodes_name.put(item.getName(), item);
			addItem(getRoot(), item);
		}
		
		System.out.println(title + " : remove " + removed.size());
		for (T item : removed) {
			nodes_file.remove(item.getFile());
			nodes_name.remove(item.getName());
			removeItem(item);
		}
	
		System.out.println(title + " : nodes_file " + nodes_file.size());
		System.out.println(title + " : nodes_name " + nodes_name.size());
		
	}
	
//	--------------------------------------------------------------------------------------------------------------
	
	
}
