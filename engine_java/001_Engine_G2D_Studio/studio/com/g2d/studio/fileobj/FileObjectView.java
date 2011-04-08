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
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.talks.TalkFile;

public abstract class FileObjectView<T extends FileObject> extends JSplitPane
{
	final protected File 		save_list_file;
	final protected File 		res_root;

	final protected HashMap<File, T> nodes_file = new HashMap<File, T>();
	final protected HashMap<String, T> nodes_name = new HashMap<String, T>();
	
	int view_index = 0;
	int[] view_modes = new int[]{
		JList.HORIZONTAL_WRAP,
		JList.VERTICAL,
		JList.VERTICAL_WRAP,
	};
	
	public FileObjectView(
			String title,
			ProgressForm progress, 
			File res_root, 
			File save_list_file)
	{
		this.save_list_file = save_list_file;
		this.res_root		= res_root;
	}

	abstract protected T			createNode(File file);

	abstract protected String 		getSaveListName(T node);

	
	synchronized public void refresh(String title, ProgressForm progress)
	{
		File 			files[]	= res_root.listFiles();
		
		Vector<T> 		added	= new Vector<T>();
		Vector<T> 		removed	= new Vector<T>(nodes_file.values());
		HashSet<T>		current = new HashSet<T>(files.length);	

		progress.setMaximum(title, files.length);
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			T node = nodes_file.get(file);
			if (node == null) {
				node = createNode(file);
				if (node != null) {
					added.add(node);
				}
			}
			if (node != null) {
				current.add(node);
				removed.remove(node);
			}
			progress.setValue(title, i);
		}
		for (T o : removed) {
			this.nodes_file.remove(o.getFile());
		}
		for (T o : added) {
			this.nodes_file.put(o.getFile(), o);
		}

		this.nodes_name.clear();
		for (T o : nodes_file.values()) {
			nodes_name.put(o.getName(), o);
		}
		
		String save_list = save_list_file.readUTF();
		if (save_list != null) {
			String list[] = CUtil.splitString(save_list, "\n");
			for (String e : list) {
				String path[] = G2DTreeNodeGroup.fromPathString(e, "/");
				if (path.length > 0) {
					T o = nodes_name.get(path[path.length-1]);
					if (o != null) {
						o.path = Arrays.copyOfRange(path, 0, path.length-1);
					}
				}
			}
		}
		
		StringBuilder new_list = new StringBuilder();
		for (T o : nodes_file.values()) {
			new_list.append(CUtil.arrayToString(o.path, "/") + getSaveListName(o) + "\n");
		}
		save_list_file.writeUTF(new_list.toString());
	}

	public T getNode(String name) {
		return nodes_name.get(name);
	}
	
	class FileList extends G2DList<T>
	{
		FileList(Vector<T> icons) {
			super(icons);
		}		
	}
	
}
