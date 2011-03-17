package com.g2d.studio.old;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.gfx.IImages;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSRow;
import com.cell.util.concurrent.ThreadPool;
import com.g2d.Tools;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.display.ui.UIComponent;
import com.g2d.studio.Config;
import com.g2d.studio.Resource;
import com.g2d.studio.Version;
import com.g2d.studio.cpj.CPJResourceManager;
import com.g2d.studio.gameedit.ObjectManager;
import com.g2d.studio.old.actor.FormActorViewer;
import com.g2d.studio.old.actor.FormActorViewerGroup;
import com.g2d.studio.old.scene.FormSceneViewer;
import com.g2d.studio.old.scene.FormSceneViewerGroup;
import com.g2d.studio.old.ui.FormUIComponentView;
import com.g2d.studio.res.Res;
import com.g2d.studio.sound.SoundManager;
import com.g2d.util.AbstractFrame;
import com.g2d.util.Util;

public class Studio extends AbstractFrame
{
	private static final long 		serialVersionUID = Version.VersionGS;
	
	private static Studio instance;
	
	public static Studio getInstance() {
		return instance;
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------------------------
	
	ThreadPool						thread_pool = new ThreadPool("studio project");
	
	final public File 				project_path;
	final public File 				project_file;
	final public File				project_save_path;
	final public File				project_save_path_scene;
	final public File				project_save_path_unit;
	final public File				project_save_path_avatar;
	final public File				project_save_path_effect;

	CPJResourceManager				frame_cpj_resource_manager;
	ObjectManager					frame_object_editor;
	SoundManager					frame_sound_manager;
	
	public FormActorViewerGroup		group_actor;
	public FormSceneViewerGroup		group_scene;
//	public FormUIComponentViewGroup	group_ui;
	
	// 资源视图
	public JTree 					tree;
	public DefaultMutableTreeNode	tree_root 		= new DefaultMutableTreeNode();
	
	// 当前选择的 AObjectViewer
	protected	AFormDisplayObjectViewer<?>	selected_node;
	
	private Studio(String g2d_file) throws Throwable
	{							
		CObject.initSystem(
			new CStorage("g2d_studio"), 
			new CAppBridge(
			this.getClass().getClassLoader(), 
			this.getClass()));
		
		Config.load(Config.class, g2d_file);

		JComponent.setDefaultLocale(Locale.CHINA);
		ProgressForm progress_form = new ProgressForm();
		progress_form.setVisible(true);
		JProgressBar progress = progress_form.progress;
		UIComponent.editor_mode = false;
		
		instance 			= this;
		project_file 		= new File(g2d_file);
		project_path 		= new File(project_file.getParent());
		
		project_save_path			= new File(project_file.getPath()+".save");
		project_save_path_scene		= new File(project_save_path.getPath()+File.separatorChar+"scene");
		project_save_path_unit		= new File(project_save_path.getPath()+File.separatorChar+"unit");
		project_save_path_avatar	= new File(project_save_path.getPath()+File.separatorChar+"avatar");
		project_save_path_effect	= new File(project_save_path.getPath()+File.separatorChar+"effect");
		project_save_path.mkdirs();
		project_save_path_scene.mkdirs();
		project_save_path_unit.mkdirs();
		project_save_path_avatar.mkdirs();
		project_save_path_effect.mkdirs();
		
		// sysetm init
		progress_form.setIconImage(Res.icon_edit);
		progress.setString("init g2d system...");
		
		try
		{
			//
			this.setTitle(Config.TITLE + " : " + project_path.getPath());
			this.setIconImage(Res.icon_edit);
			this.setSize(300, AbstractFrame.getScreenHeight()-60);
			this.setLocation(0, 0);
			this.setLayout(new BorderLayout());

			new SetResourceManager();

			// init jtree
			progress.setString("init objects...");
			{
				// spr view
				group_actor = new FormActorViewerGroup(this);
				tree_root.add(group_actor);
				group_scene = new FormSceneViewerGroup(this);
				tree_root.add(group_scene);
				
				tree = new JTree(tree_root);
				tree.setCellRenderer(new TreeRender());
//				tree.setRootVisible(false);
				tree.addMouseListener(new TreeMouseListener());
				tree.addTreeSelectionListener(new TreeSelect());
				//tree_resources.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
				//this.add(tree);
				JScrollPane scroll = new JScrollPane(tree);
				scroll.setVisible(true);
				this.add(scroll, BorderLayout.CENTER);		
				
				loadAll(progress);
			}
			

			initToolBar();
			
			initStateBar();
			
			this.addWindowListener(new StudioWindowListener());
			
		}
		catch (Throwable e) {
			throw e;
		} finally{
			progress_form.setVisible(false);
			progress_form.dispose();
		}
	}

	// init tool bar
	private void initToolBar()
	{
//		JToolBar tool_bar = new JToolBar();
//		
//		// res manager
//		{
//			frame_cpj_resource_manager = new CPJResourceManager();
//			JButton btn = new JButton();
//			btn.setToolTipText(frame_cpj_resource_manager.getTitle());
//			btn.setIcon(Tools.createIcon(Res.icons_bar[7]));
//			btn.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					frame_cpj_resource_manager.setVisible(true);
//				}
//			});
//			tool_bar.add(btn);
//		}
//		// unit manager
//		{
//			frame_object_editor = new ObjectManager();
//			JButton btn = new JButton();
//			btn.setToolTipText(frame_object_editor.getTitle());
//			btn.setIcon(Tools.createIcon(Res.icons_bar[4]));
//			btn.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					frame_object_editor.setVisible(true);
//				}
//			});
//			tool_bar.add(btn);
//		}
//		// sound manager
//		{
//			frame_sound_manager = new SoundManager();
//			JButton btn = new JButton();
//			btn.setToolTipText(frame_sound_manager.getTitle());
//			btn.setIcon(Tools.createIcon(Res.icons_bar[3]));
//			btn.addActionListener(new ActionListener(){
//				public void actionPerformed(ActionEvent e) {
//					frame_sound_manager.setVisible(true);
//				}
//			});
//			tool_bar.add(btn);
//		}
//		
//		this.add(tool_bar, BorderLayout.NORTH);
	}
	
	// init state bar
	private void initStateBar()
	{
		JToolBar state_bar = new JToolBar();
		
		// heap state
		{
			final JPanel mem_state = new JPanel() {
				public void paint(Graphics g) {
					super.paint(g);
					Util.drawHeapStatus(g, Color.BLACK, 1, 1, getWidth() - 2,
							getHeight() - 2);
				}
			};
			thread_pool.scheduleAtFixedRate(new Runnable() {
				public void run() {
					mem_state.repaint();
				}
			}, 2000, 2000);
			state_bar.add(mem_state);
		}

		// gc
		{
			JButton gc = new JButton("gc");
			gc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.gc();
				}
			});
			state_bar.add(gc);
		}

		this.add(state_bar, BorderLayout.SOUTH);

	}
	
	/**
	 * 得到工作空间跟目录下的文件
	 * @param path
	 * @return
	 */
	public File getFile(String path)
	{
		return new File(project_path.getPath() + "/" + path);
	}
	
	
	public void setSelectedNode(AFormDisplayObjectViewer<?> node)
	{
		selected_node = node;
	}
	
	public AFormDisplayObjectViewer<?> getSelectedNode()
	{
		return selected_node;
	}
	
	public FormActorViewer getSelectedNodeAsActor()
	{
		if (selected_node instanceof FormActorViewer) {
			return (FormActorViewer)selected_node;
		}
		return null;
	}
	
	public FormSceneViewer getSelectedNodeAsScene()
	{
		if (selected_node instanceof FormSceneViewer) {
			return (FormSceneViewer)selected_node;
		}
		return null;
	}

	public FormUIComponentView getSelectedNodeAsUI()
	{
		if (selected_node instanceof FormUIComponentView) {
			return (FormUIComponentView)selected_node;
		}
		return null;
	}
	
	public FormActorViewer getActor(String parent, String actor) {
		try {
			return group_actor.getNode(parent, actor);
		} catch (Exception e) {
			return null;
		}
	}
	public Collection<FormActorViewer> getActors() {
		ArrayList<FormActorViewer> ret = new ArrayList<FormActorViewer>();
		for (ATreeNodeSet<FormActorViewer> set : group_actor.getChilds()) {
			for (ATreeNodeLeaf<FormActorViewer> actor : set.getChilds()){
				ret.add(actor.user_data);
			}
		}
		return ret;
	}
	
//-----------------------------------------------------------------------------------------------------------
// scene operator
	
	public FormSceneViewer getScene(String scene_name) {
		try {
			String[] ps = CUtil.splitString(scene_name, "/");
			return group_scene.getNode(ps[0], ps[1]);
		} catch (Exception e) {
			return null;
		}
	}
	
	public FormSceneViewer getScene(String parent, String scene) {
		try {
			return group_scene.getNode(parent, scene);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Collection<FormSceneViewer> getScenes() {
		ArrayList<FormSceneViewer> ret = new ArrayList<FormSceneViewer>();
		for (ATreeNodeSet<FormSceneViewer> set : group_scene.getChilds()) {
			for (ATreeNodeLeaf<FormSceneViewer> scene : set.getChilds()){
				ret.add(scene.user_data);
			}
		}
		return ret;
	}
	
	
	public Collection<XLSFile> getXLSFiles()
	{
		ArrayList<XLSFile> ret = new ArrayList<XLSFile>();
		
		File root_xls = getFile(Config.XLS_ROOT);
		
		for (File xls : root_xls.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name) {
				return name.lastIndexOf(Config.XLS_SUFFIX)>0;
			}
		})) {
			ret.add(new XLSFile(xls));
		}
		
		return ret;
	}

	/**
	 * 返回以 xls 的 row[c0][c1] 的集合
	 * @param xls_file
	 * @return
	 */
	public<T extends XLSRow> Collection<T> getXLSPrimaryRows(XLSFile xls_file, Class<T> cls)
	{
		File path = getFile(Config.XLS_ROOT + "/" + xls_file.xls_file);
		return XLSRow.getXLSRows(path, new AtomicReference<XLSFile>(xls_file), cls);
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	
	public FormUIComponentView getUI(String parent, String ui) {
		try {
//			return group_ui.getNode(parent, ui);
		} catch (Exception e) {}
		return null;
	}


	public void saveAll() 
	{
		group_actor.saveAll();
		group_scene.saveAll();
//		group_ui.saveAll();
	}
	
	public void loadAll(JProgressBar progress)
	{
		progress.setString("load actor objects...");
		group_actor.loadAll();

		progress.setString("load scene objects...");
		group_scene.loadAll();

//		progress.setString("load ui objects...");
//		group_ui.loadAll();
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	ui action
	
	class StudioWindowListener implements WindowListener
	{
		public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			
			int result = JOptionPane.showConfirmDialog(Studio.this, "保存并退出?");
			if (result == JOptionPane.OK_OPTION)
			{
				saveAll();
				
				System.out.println("save and exit");
				System.exit(1);
			}
			else if (result == JOptionPane.NO_OPTION)
			{
				System.out.println("exit");
				System.exit(1);
			}
			else
			{
				new Thread(){
					public void run() {
						Studio.this.setVisible(true);
					}
				}.start();
			}


			
		}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
	
	@SuppressWarnings("unchecked")
	class TreeRender extends DefaultTreeCellRenderer
	{
		private static final long serialVersionUID = 1L;
		
		ImageIcon icon = Tools.createIcon(Tools.readImage("/com/g2d/studio/icon.bmp"));
		
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
			Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus);
			
			if (value instanceof ATreeNodeLeaf){
				ATreeNodeLeaf aleaf = ((ATreeNodeLeaf)value);
				if (aleaf.user_data instanceof AFormDisplayObjectViewer) {
					AFormDisplayObjectViewer form = (AFormDisplayObjectViewer)(aleaf.user_data);
					ImageIcon aicon = form.getSnapshot(false);
					if (aicon == null) {
						setIcon(icon);
					}else{
						setIcon(aicon);
					}
				}
			}
			else if (value instanceof ATreeNodeSet){
				setIcon(Tools.createIcon(Res.icon_cpj));
			}
			//System.out.println("getTreeCellRendererComponent");
			return comp;
		}
		
		
		
	}
	
	
	class TreeSelect implements TreeSelectionListener
	{
		public void valueChanged(TreeSelectionEvent e) {
			
			MutableTreeNode node = (MutableTreeNode)tree.getLastSelectedPathComponent();

			if (node instanceof ATreeNodeLeaf){
				ATreeNodeLeaf aleaf = ((ATreeNodeLeaf)node);
				if (aleaf.user_data instanceof FormActorViewer) {
					FormActorViewer form = (FormActorViewer)(aleaf.user_data);
					form.getSnapshot(true);
				}
			}
			
//			TreePath selPath = e.getPath();
//			TreeNode node = getTreeNode(selPath.getPath());
//			System.out.println(node);
			
		}
	}
	
	class TreeMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			selected_node = null;
			
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			
			if (selRow != -1) 
			{
				Object obj = selPath.getLastPathComponent();
//				System.out.println(node);
				if (obj instanceof TreeNode) 
				{
					TreeNode node = (TreeNode)obj;
					
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (e.getClickCount() == 1) {
							node.onSelected(tree, e);
						}
						else if (e.getClickCount() == 2) {
							node.onDoubleClicked(tree, e);
						}
					}
					
					if (e.getButton() == MouseEvent.BUTTON3){
						node.onRightClicked(tree, e);
					}
					
					if (e.getButton() == MouseEvent.BUTTON1){
						if (node instanceof ATreeNodeLeaf){
							selected_node = ((ATreeNodeLeaf) node).getViewer();;
							 if (e.getClickCount() == 2) {
								if (selected_node != null) {
									selected_node.setVisible(true);
								}
							}
						}
					}
					
				}
				
				
			}
		}
	}
	
	
	
	class ProgressForm extends AbstractFrame
	{
		final public JProgressBar progress = new JProgressBar();
		
		public ProgressForm()
		{
			progress.setStringPainted(true);
			progress.setIndeterminate(true);

			this.setTitle("loading...");
			this.setSize(200, 50);
			this.add(progress);
			this.setCenter();
		}
		
		
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	resource manager
	
	static public class SetResource extends Resource
	{
		boolean is_load_resource = false;
		
		public SetResource(String setPath, String name)  throws Exception{
			super(setPath, name, false);
		}
		
		public SetResource(File file, String name) throws Exception {
			super(file, name, false);
		}
		
		@Override
		protected IImages getLocalImage(ImagesSet img) throws IOException {
			StreamTypeTiles tiles = new StreamTypeTiles(img);
			return tiles;
		}
		
		synchronized public boolean isLoadImages()
		{
			return is_load_resource;
		}
		
		synchronized public void initAllStreamImages()
		{
			is_load_resource = true;
			Enumeration<ImagesSet> imgs = ImgTable.elements();
			while (imgs.hasMoreElements()) {
				ImagesSet ts = imgs.nextElement();
				IImages images = getImages(ts);
				if (images instanceof StreamTiles) {
					((StreamTiles) images).run();
				}
			}
		}
		
		synchronized public void destoryAllStreamImages(){
			is_load_resource = false;
			if (ResourceManager!=null) {
				for (Object obj : ResourceManager.values()) {
					if (obj instanceof StreamTiles){
						StreamTiles stiles = (StreamTiles)obj;
						stiles.unloadAllImages();
					}
				}
			}
		}
	}
	
	public class SetResourceManager extends CellSetResourceManager
	{
		public SetResourceManager() {
			instance = this;
		}
		
		@Override
		public SetResource createSet(String setPath) throws Exception {
			return new SetResource(project_path.getPath() + "/" + setPath, setPath);
		}
		
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	main entry

	
	static public void main(String[] args)
	{
		try
		{
			Studio studio = null;
			
			if (args==null || args.length==0 || !new File(args[0]).exists() || !new File(args[0]).isFile()) 
			{
				//
				java.awt.FileDialog fd = new FileDialog(new JFrame(), "Open workspace", FileDialog.LOAD);
				fd.show();
				String file = fd.getDirectory() + fd.getFile();
				
				System.out.println("Chose to open this file: " + file);
				
				studio = new Studio(file);
			}
			else
			{
				System.out.println("Open: " + args[0]);
				studio = new Studio(args[0]);
			}
			
			studio.setVisible(true);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			
			String message = "Open workspace error ! \n" + e.getClass().getName() + " : " + e.getMessage() + "\n";
			for (StackTraceElement stack : e.getStackTrace()) {
				message += "\t"+stack.toString()+"\n";
			}
			JOptionPane.showMessageDialog(null, message);
			
			System.exit(1);
		}
	}
	
	
}

