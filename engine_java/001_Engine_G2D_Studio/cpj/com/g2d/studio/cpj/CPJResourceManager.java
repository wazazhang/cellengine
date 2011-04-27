package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.cell.CObject;
import com.g2d.awt.util.*;
import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.SaveProgressBar;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.awt.util.AbstractDialog;

public class CPJResourceManager extends ManagerForm implements MouseListener
{
	private static final long serialVersionUID = 1L;

//	----------------------------------------------------------------------------------------------------------------------------

//	CPJSpriteViewer sprite_viewer = new CPJSpriteViewer();
	
	File save_actor_list;
	File save_effect_list;
	File save_avatar_list;
	File save_scene_list;
	
	DefaultMutableTreeNode unit_root;
	DefaultMutableTreeNode avatar_root;
	DefaultMutableTreeNode effect_root;
	DefaultMutableTreeNode scene_root;
	
	public CPJResourceManager(Studio studio, ProgressForm progress)
	{
		super(studio, progress, "资源管理器", Res.icons_bar[7]);

		File save_dir = Studio.getInstance().project_save_path.getChildFile("resources");
		{
			save_actor_list = save_dir.getChildFile("actor_list.list");
			save_avatar_list = save_dir.getChildFile("avatar_list.list");
			save_effect_list = save_dir.getChildFile("effect_list.list");
			save_scene_list = save_dir.getChildFile("scene_list.list");
		}
		
		com.g2d.studio.io.File path = Studio.getInstance().project_path;
		
		JTabbedPane table = new JTabbedPane();
		// actors
		{
			System.out.println("init : 单位资源");
			unit_root = new DefaultMutableTreeNode("单位模板");
			ArrayList<CPJFile> files = CPJFile.getSavedListFile(
					save_actor_list, path, 
					CPJResourceType.ACTOR,
					progress);
			progress.setMaximum("", files.size());
			for (int i=0; i<files.size(); i++) {
				unit_root.add(files.get(i));
				progress.increment();
			}
			G2DTree tree = new G2DTree(unit_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("单位", Tools.createIcon(Res.icon_res_2), scroll);
		}
		// avatars
		{
			System.out.println("init : AVATAR资源");
			avatar_root = new DefaultMutableTreeNode("AVATAR模板");
			ArrayList<CPJFile> files = CPJFile.getSavedListFile(
					save_avatar_list, path, 
					CPJResourceType.AVATAR, 
					progress);
			progress.setMaximum("", files.size());
			for (int i=0; i<files.size(); i++) {
				avatar_root.add(files.get(i));
				progress.increment();
			}
			G2DTree tree = new G2DTree(avatar_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), scroll);
		}
		// effect
		{
			System.out.println("init : 特效资源");
			effect_root = new DefaultMutableTreeNode("特效模板");
			ArrayList<CPJFile> files = CPJFile.getSavedListFile(
					save_effect_list, path, 
					CPJResourceType.EFFECT,
					progress);
			progress.setMaximum("", files.size());
			for (int i=0; i<files.size(); i++) {
				effect_root.add(files.get(i));
				progress.increment();
			}
			G2DTree tree = new G2DTree(effect_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("魔法效果/特效", Tools.createIcon(Res.icon_res_3), scroll);
		}
		// scenes
		{
			System.out.println("init : 场景资源");
			scene_root = new DefaultMutableTreeNode("场景模板");
			ArrayList<CPJFile> files = CPJFile.getSavedListFile(
					save_scene_list, path,
					CPJResourceType.WORLD, 
					progress);
			progress.setMaximum("", files.size());
			for (int i=0; i<files.size(); i++) {
				scene_root.add(files.get(i));
				progress.increment();
			}
			G2DTree tree = new G2DTree(scene_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("场景", Tools.createIcon(Res.icon_res_1), scroll);
		}
		
		this.add(table, BorderLayout.CENTER);
		
	}
	
	public <T extends CPJObject<?>> CPJIndex<T> getNodeIndex(T node)
	{
		return getNode(node.res_type, node.parent.getName(), node.getName());
	}
	
	public <T extends CPJObject<?>> CPJIndex<T> getNode(CPJResourceType type, String cpj, String set)
	{
		CPJIndex<T> index = new CPJIndex<T>(type, cpj, set);
		getNode(index);
		if (index.object!=null) {
			return index;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CPJObject<?>> T getNode(CPJIndex<T> index)
	{
		try{
			G2DTreeNode<?> node = null;
			switch(index.res_type)
			{
			case ACTOR:
				node = G2DTree.getNode(unit_root, index.cpj_file_name, index.set_object_name);
				break;
			case EFFECT:
				node = G2DTree.getNode(effect_root, index.cpj_file_name, index.set_object_name);
				break;
			case WORLD:
				node = G2DTree.getNode(scene_root, index.cpj_file_name, index.set_object_name);
				break;
			case AVATAR:
				node = G2DTree.getNode(avatar_root, index.cpj_file_name, index.set_object_name);
				break;
			}
			if (node != null) {
				index.object = (T)node;
				return (T)node;
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		return null;	
	}
	
	public <T extends CPJObject<?>> Vector<T> getNodes(CPJResourceType type, Class<T> clazz)
	{
		switch(type)
		{
		case ACTOR:
			return G2DTree.getNodesSubClass(unit_root, clazz);
		case EFFECT:
			return G2DTree.getNodesSubClass(effect_root, clazz);
		case WORLD:
			return G2DTree.getNodesSubClass(scene_root, clazz);
		case AVATAR:
			return G2DTree.getNodesSubClass(avatar_root, clazz);
		}
		return null;
	}
	
	public DefaultMutableTreeNode getRoot(CPJResourceType index) {
		switch (index) {
		case ACTOR:
			return unit_root;
		case EFFECT:
			return effect_root;
		case WORLD:
			return scene_root;
		case AVATAR:
			return avatar_root;
		}
		return null;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
//	public CPJSpriteViewer getSpriteViewer() {
//		return sprite_viewer;
//	}
//	
//	public class CPJSpriteViewer extends JFrame
//	{
//		private static final long serialVersionUID = 1L;
//		
//		DisplayObjectPanel viewer = new DisplayObjectPanel();
//		
//		CPJSpriteViewer() {
//			super.setSize(400, 400);
//			add(viewer);
//		}
//		
//		public DisplayObjectPanel getViewer() {
//			return viewer;
//		}
//	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (e.getSource() instanceof G2DTree) {
				G2DTree tree = (G2DTree)e.getSource();
				try{
					DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (tree.getSelectedNode() == root &&
						path.getLastPathComponent() == root) {
						new RootMenu(tree, root).show(tree, e.getX(), e.getY());
					}
				}catch(Exception err){}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

//	----------------------------------------------------------------------------------------------------------------------------

	public CPJResourceList<?> createObjectsPanel(CPJResourceType type)
	{
		switch(type)
		{
		case ACTOR:
			return new CPJResourceList<CPJSprite>(CPJSprite.class, unit_root);
		case EFFECT:
			return new CPJResourceList<CPJSprite>(CPJSprite.class, effect_root);
		case WORLD:
			return new CPJResourceList<CPJWorld>(CPJWorld.class, scene_root);
		case AVATAR:
			return new CPJResourceList<CPJSprite>(CPJSprite.class, avatar_root);
		}
		return null;
	}
	

//	----------------------------------------------------------------------------------------------------------------------------
	
	private String getList(Vector<? extends CPJObject<?>> objs) {
		StringBuffer list = new StringBuffer();
		for (CPJObject<?> spr : objs){
			list.append(
					spr.parent.getCPJPath() + ";" +
					spr.parent.getName() + ";" + 
					spr.getName() + ";" + 
					"\n");
		}
		return list.toString();
	}
	
	public void saveAll(IProgress progress)
	{
		String actor_list = getList(G2DTree.getNodesSubClass(unit_root, CPJSprite.class));
		save_actor_list.writeUTF(actor_list);
		
		String effect_list = getList(G2DTree.getNodesSubClass(effect_root, CPJSprite.class));
		save_effect_list.writeUTF(effect_list);
		
		String avatar_list = getList(G2DTree.getNodesSubClass(avatar_root, CPJSprite.class));
		save_avatar_list.writeUTF(avatar_list);
		
		String scene_list = getList(G2DTree.getNodesSubClass(scene_root, CPJWorld.class));
		save_scene_list.writeUTF(scene_list);

	}
	
	@Override
	public void saveSingle() throws Throwable {}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	class RootMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		G2DTree tree;
		DefaultMutableTreeNode root;
		
		JMenuItem	refresh_all		= new JMenuItem("刷新所有");
		JMenuItem	build_new		= new JMenuItem("编译新加的");
		JMenuItem	build_all		= new JMenuItem("重编译所有");
		
		public RootMenu(G2DTree tree, DefaultMutableTreeNode root) {
			this.tree = tree;
			this.root = root;
			refresh_all	.addActionListener(this);
			build_new	.addActionListener(this);
			build_all	.addActionListener(this);
			this.add	(refresh_all);
			this.add	(build_new);
			this.addSeparator();
			this.add	(build_all);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refresh_all) {
				
			}
			else if (e.getSource() == build_new) {
				new BuildResourceTask(root.children(), true).start();
			}
			else if (e.getSource() == build_all) {
				new BuildResourceTask(root.children(), false).start();
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------
	
	class RefreshTask extends AbstractDialog
	{
		SaveProgressBar	progress = new SaveProgressBar();
		
		G2DTree tree;
		DefaultMutableTreeNode root;
		
		File save_list;
		String res_root;
		CPJResourceType res_type;
		
		public RefreshTask(
				G2DTree tree, 
				DefaultMutableTreeNode root, 
				File save_list,
				String res_root, 
				CPJResourceType res_type) 
		{
			super(tree);
			super.setSize(500, 90);
			super.setLayout(new BorderLayout());
			super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			super.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			super.setAlwaysOnTop(false);
			
			this.tree = tree;
			this.root = root;
			
			this.save_list = save_list;
			this.res_root = res_root;
			this.res_type = res_type;
		}

		public void refresh()
		{
			Vector<CPJFile> cur_files = G2DTree.getNodesSubClass(root, CPJFile.class);
			
			// 查找老目录下的新文件
			progress.setMaximum(cur_files.size());
			int i = 0;
			for (CPJFile file : cur_files) {
				try {
					file.refresh();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}	
				i++;
				progress.setValue(i);
			}
			// 
			com.g2d.studio.io.File path = Studio.getInstance().project_path;
			{
				ArrayList<CPJFile> files = CPJFile.listRootFile(path, res_root,
						res_type, progress);

			}
			tree.reload(root);
			
			String list = getList(G2DTree.getNodesSubClass(root, res_type.res_type));
			save_list.writeUTF(list);
		}
		
	}

//	----------------------------------------------------------------------------------------------------------------------------
	
	class BuildResourceTask extends AbstractDialog implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		BuilderThread	thread;
		
		boolean			ignore_on_exists = false;
		
		JButton			terminate = new JButton("终止");
		
		JProgressBar	progress = new JProgressBar();
		
		public BuildResourceTask(Enumeration<?> childs, boolean ignore_on_exists) 
		{
			super(CPJResourceManager.this);
			super.setSize(500, 90);
			super.setLayout(new BorderLayout());
			super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			super.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			super.setAlwaysOnTop(false);
			
			progress.setStringPainted(true);
			super.add(progress, BorderLayout.CENTER);
			
			JPanel south = new JPanel(new FlowLayout());
			terminate.addActionListener(this);
			south.add(terminate);
			super.add(south, BorderLayout.SOUTH);

			this.ignore_on_exists = ignore_on_exists;
			this.thread = new BuilderThread(childs);
		}
		
		public void start() {
			this.thread.start();
			setVisible(true);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == terminate) {
				thread.exit();
				this.dispose();
			}
		}
		
		class BuilderThread extends Thread
		{
			boolean exit = false;
			
			Enumeration<?> childs;
			
			public BuilderThread(Enumeration<?> childs)
			{
				this.childs = childs;
			}
			
			public void exit()
			{
				exit = true;
				try {
					this.join(60000);
					if (this.isAlive()) {
						this.interrupt();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void run() 
			{
				ArrayList<CPJFile> cpjs = new ArrayList<CPJFile>();
				while (childs.hasMoreElements()) {
					Object obj = childs.nextElement();
					if (obj instanceof CPJFile) {
						cpjs.add((CPJFile)obj);
					}
				}
				long start_time = System.currentTimeMillis();
				progress.setMaximum(cpjs.size());
				int n = 0;
				for (CPJFile cpj : cpjs) {
					setTitle("rebuild : " + cpj.getCPJPath());
					if (!exit) {
						cpj.rebuild(ignore_on_exists);
						progress.setValue(n);
						progress.setString((progress.getValue() + 1) + "/" + progress.getMaximum());
					} else {
						break;
					}
					n++;
					progress.setValue(n);
				}
				setTitle("总共用时: " + CObject.timesliceToStringHour(System.currentTimeMillis()-start_time));
				terminate.setText("关闭");
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
