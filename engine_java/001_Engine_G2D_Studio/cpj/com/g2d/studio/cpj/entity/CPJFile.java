package com.g2d.studio.cpj.entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.cell.CUtil;
import com.cell.gameedit.object.WorldSet;
import com.g2d.awt.util.*;

import com.g2d.studio.SaveProgressForm;
import com.g2d.studio.Studio;
import com.g2d.studio.StudioResource;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cell.gameedit.Builder;
import com.g2d.studio.cell.gameedit.EatBuilder;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTreeNode;

public class CPJFile extends G2DTreeNode<CPJObject<?>>
{	
//	final public String res_prefix;
//	final public String res_suffix;
	/** cpj dir name */
	final public String				name;	
	/** cpj file name */
	final public File				cpj_file;
	/** res root name*/
	final public String				res_root;
	final public CPJResourceType	res_type;

	private StudioResource 				set_resource;
	private HashMap<String, CPJSprite>	sprites			= new HashMap<String, CPJSprite>();
	private HashMap<String, CPJWorld>	worlds			= new HashMap<String, CPJWorld>();
	
	public CPJFile(File cpj_file, CPJResourceType res_type) throws Throwable 
	{
		this.cpj_file		= cpj_file;
		this.name			= cpj_file.getParentFile().getName();		
		this.res_root		= cpj_file.getParentFile().getParentFile().getName();
		this.res_type		= res_type;

		
//		if (!output_file.exists()) {
//			throw new IOException("path not a cpj file : " + output_file.getPath());
//		}

//		set_resource = new StudioResource(output_file, name);
		
//		refresh();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CPJFile) {
			((CPJFile) obj).cpj_file.equals(this.cpj_file);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return cpj_file.hashCode();
	}
	
	@Override
	public String getName() {
		return name;	
	}

	/**
	 * @return
	 */
	public String getCPJPath() {
		return res_root + "/" + cpj_file.getParentFile().getName() + "/" + cpj_file.getName();
//		File output_file	= getOutputFile(cpj_file);
//		if (output_file!=null && output_file.exists())  {
//			String output_properties = 
//				res_root + "/" + 
//				cpj_file.getParentFile().getName() + "/" + 
//				output_file.getParentFile().getName() + "/" +
//				output_file.getName();
//			return output_properties;
//		}
//		else{
//			String output_properties = 
//				res_root + "/" + 
//				cpj_file.getParentFile().getName() + "/" +
//				"null/" +
//				"null";
//			return output_properties;
//		}
	}
	
	@Override
	public ImageIcon createIcon() {
		return Tools.createIcon(Res.icon_cpj);
	}

	public File getFile() {
		return cpj_file;
	}
	
	public File getCPJDir() {
		return cpj_file.getParentFile();
	}
	
	public StudioResource getSetResource() {
		return set_resource;
	}

//	------------------------------------------------------------------------------------------------------------------------------

	//
	private CPJSprite loadSprite(String name, CPJResourceType res_type) {
		com.cell.gameedit.object.SpriteSet set = set_resource.getSetSprite(name);
		CPJSprite ret = sprites.get(name);
		if (ret == null) {
			try{
				if (set!=null) {
					ret = new CPJSprite(this, name, res_type);
					sprites.put(name, ret);
				}
				add(ret);
			}catch(Exception err){
				err.printStackTrace();
			}
		} else if (set != null) {
			ret.setSetObject(set);
		}
		return ret;
	}
	
	//	
	private CPJWorld loadWorld(String name) {
		WorldSet wordset = set_resource.getSetWorld(name);
		CPJWorld ret = worlds.get(name);
		if (ret == null) {
			try{
				if (wordset!=null) {
					ret = new CPJWorld(this, name);
					worlds.put(name, ret);
				}
				add(ret);
			}catch(Exception err){
				err.printStackTrace();
			}
		} else if (wordset!=null) {
			ret.setSetObject(wordset);
		}
		return ret;
	}

	private void loadAllSprite(CPJResourceType res_type)
	{
		if (set_resource != null) {
			for (String spr : set_resource.SprTable.keySet()) {
				loadSprite(spr, res_type);
			}
		}
	}

	private void loadAllWorld() {
		if (set_resource != null) {
			for (String world : set_resource.WorldTable.keySet()) {
				loadWorld(world);
			}
		}
	}

//	------------------------------------------------------------------------------------------------------------------------------


	public Collection<String> getSpriteNames() {
		return set_resource.SprTable.keySet();
	}
	
	public Collection<String> getWorldNames() {
		return set_resource.WorldTable.keySet();
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void onRightClicked(JTree tree, MouseEvent e) {
		new CPJFileMenu(tree).show(tree, e.getX(), e.getY());
	}
	
	

//	------------------------------------------------------------------------------------------------------------------------------

	public void refresh() throws Throwable
	{
		if (cpj_file != null && cpj_file.exists())
		{
			System.out.println("refresh resource : " + getName());
			try {
				set_resource = EatBuilder.getInstance().createResource(cpj_file);
				switch (res_type) {
				case ACTOR:	
				case AVATAR:
				case EFFECT:
					loadAllSprite(res_type);
					break;
				case WORLD:
					loadAllWorld();
					break;
				}
			} catch (Throwable e) {
//				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public void rebuild(boolean ignore_on_exist)
	{
		if (cpj_file.exists()) {
			switch (res_type) {
			case ACTOR:	
			case AVATAR:
			case EFFECT:
				Builder.getInstance().buildSprite(cpj_file, ignore_on_exist);
				break;
			case WORLD:
				Builder.getInstance().buildScene(cpj_file, ignore_on_exist);
				break;
			}
		}
	}
	
	
	public void openEdit()
	{
		if (cpj_file.exists()) {
			Builder.getInstance().openCellGameEdit(cpj_file);
		}
	}
//	------------------------------------------------------------------------------------------------------------------------------

	class CPJFileMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		JTree tree;
		JMenuItem item_open_cell_game_editor	= new JMenuItem("打开编辑器");
		JMenuItem item_rebuild_objects			= new JMenuItem("编译文件");
		
		public CPJFileMenu(JTree tree) {
			item_open_cell_game_editor.addActionListener(this);
			item_rebuild_objects.addActionListener(this);
			add(item_open_cell_game_editor);
			add(item_rebuild_objects);
			this.tree = tree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item_open_cell_game_editor) {
				openEdit();
			} else if (e.getSource() == item_rebuild_objects) {
				rebuild(false);
				try {
					refresh();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				try{
					DefaultTreeModel tm = (DefaultTreeModel)tree.getModel();
					tm.reload(CPJFile.this);
				}catch (Exception err){}
				try{
					Studio.getInstance().getCPJResourceManager().saveAll(null);
				}catch (Exception err){err.printStackTrace();}
				try{
					Studio.getInstance().getObjectManager().resetAllResources(CPJFile.this);
					Studio.getInstance().getSceneManager().resetAllResources(CPJFile.this);
				}catch (Exception err){err.printStackTrace();}
			}
		}
		
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	public static ArrayList<CPJFile> getSavedListFile(
			File save_list,
			File root, 
			CPJResourceType res_type,
			IProgress progress)
	{
		String text = save_list.readUTF();
		String[] lines = CUtil.splitString(text, "\n", true);
		progress.setMaximum(res_type.toString(), lines.length);
		LinkedHashMap<String, CPJFile> ret = new LinkedHashMap<String, CPJFile>(lines.length);
		for (String line : lines) {
			try {
				String info[] = CUtil.splitString(line, ";");
				if (info.length >= 3) {
					if (!ret.containsKey(info[0])) {
						File cpj = root.getChildFile(info[0]);
						if (cpj.exists()) {
							try {
								progress.increment();
								ret.put(info[0], new CPJFile(cpj, res_type));
							} catch(Throwable err){
								System.err.println("init cpj file error : " + cpj.getPath());
								err.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<CPJFile>(ret.values());
	}

	/**
	 * @param root			g2d resource root
	 * @param res_root 		avatar
	 * @param res_type		sprite
	 * @param prefix		item_xxxxxx
	 * @param cpj_file		item.cpj
	 * @return
	 */
	public static ArrayList<File> listRootFile (
			File root, 
			String res_root, 
			CPJResourceType res_type,
			IProgress progress)
	{
//		res_prefix = res_prefix.toLowerCase();
		ArrayList<File> ret = new ArrayList<File>();
		try{
			File root_file = Studio.getInstance().getIO().createFile(root, res_root);
			File[] files = root_file.listFiles();
			progress.setMaximum(res_root, files.length);
			for (File file : files) {
				File cpj = Builder.getInstance().getCPJFile(file, res_type);
				if (cpj != null && cpj.exists()) {
					progress.increment();
					ret.add(cpj);
				}
			}
		}catch (Exception err){
			err.printStackTrace();
		}
		return ret;
	}
//	
//	public static File getCPJFile(File file, CPJResourceType res_type) {
//		if (file.getName().startsWith("item_")) {
//			File cpj = file.getChildFile("item.cpj");
//			if (cpj.exists()) {
//				return cpj;
//			}
//		}
//		return null;
//	}
	
}

