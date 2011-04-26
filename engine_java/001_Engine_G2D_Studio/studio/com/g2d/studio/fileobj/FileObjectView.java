package com.g2d.studio.fileobj;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.cell.CUtil;
import com.g2d.awt.util.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
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

	final protected HashMap<File, T> nodes_file = new HashMap<File, T>();
	final protected HashMap<String, T> nodes_name = new HashMap<String, T>();
	
	public FileObjectView(
			String title,
			ProgressForm progress, 
			File res_root, 
			File save_list_file)
	{
		super(new NodeGroup<T>(title));
		this.save_list_file = save_list_file;
		this.res_root = res_root;		
		this.title = title;
		
		refresh(progress);
		
//		this.getList().setVisibleRowCount(this.files.size()/10+1);
//		this.getList().setLayoutOrientation(JList.HORIZONTAL_WRAP);

	}

	abstract public NodeGroup<T> cloneRoot() ;
	
	abstract public T	createNode(File file);

	abstract protected	String getSaveListName(T t);
	
	public String getListFileData() {
		StringBuilder new_list = new StringBuilder();
		for (T o : nodes_file.values()) {
			new_list.append(CUtil.arrayToString(o.path, "/") + getSaveListName(o) + "\n");
		}
		return new_list.toString();
	}
	
	public Vector<T> getNodes() {
		return getItems();
	}
	
	public T getNode(String node_name) {
		return nodes_name.get(node_name);
	}
	
	
	synchronized public void refresh(IProgress progress)
	{
		this.nodes_file.clear();	
		this.nodes_name.clear();
		File files[] = res_root.listFiles();
		
		progress.setMaximum(title, files.length);
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			T node = createNode(file);
			if (node != null) {
				this.nodes_file.put(file, node);
				nodes_name.put(node.getName(), node);
			}
			progress.setValue(title, i);
		}
	}
	
}
