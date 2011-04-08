package com.g2d.studio.scene;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.cell.CIO;
import com.cell.rpg.NamedObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.item.ItemPropertyManager;
import com.cell.rpg.item.ItemPropertyTypes;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneAbilityManager;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.util.IDFactoryInteger;
import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.SaveProgressForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.instancezone.InstanceZonesManager;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.entity.SceneGroup;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class SceneManager extends JPanel implements IDynamicIDFactory<SceneNode>, ActionListener
{
	private static final long serialVersionUID = 1L;

	private static SceneManager instance;
	public static SceneManager getInstance() {
		return instance;
	}
//	------------------------------------------------------------------------------------------------------------------------------
	IDFactoryInteger<SceneNode>		id_factory			= new IDFactoryInteger<SceneNode>();
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	final private ReentrantLock		scene_lock			= new ReentrantLock();
	
	final public File				scene_dir;
	final public File				scene_list;
	final public SceneTree			g2d_tree;
	
	final private G2DWindowToolBar	tool_bar			= new G2DWindowToolBar(this);
	final private JButton			tool_scene_graph 	= new JButton(com.g2d.awt.util.Tools.createIcon(Res.icon_scene_graph));
	final private JButton			tool_instance_zones;
	
	final private InstanceZonesManager	instance_zones;
	
//	------------------------------------------------------------------------------------------------------------------------------
	
	public SceneManager(Studio studio, ProgressForm progress)
	{
		super(new BorderLayout());
		
		try {
			Class<?> cls = Class.forName(Config.DYNAMIC_SCENE_ABILITY_MANAGER_CLASS);
			SceneAbilityManager manager = (SceneAbilityManager)cls.newInstance();
			SceneAbilityManager.setManager(manager);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		progress.startReadBlock("初始化场景...");
		instance = this;
		this.scene_dir	= studio.project_save_path.getChildFile("scenes");
		this.scene_list	= scene_dir.getChildFile("scene.list");
		{
			SceneGroup tree_root = new SceneGroup("场景管理器");
			this.g2d_tree	= new SceneTree(tree_root);
			if (scene_list.exists()) {
				String[] all_scene = CIO.readAllLine(scene_list.getPath(), "UTF-8");
				progress.setMaximum("", all_scene.length);
				int i=0;
				for (String node_path : all_scene) {
					tree_root.loadPath(node_path.trim());
					progress.setValue("", i++);
				}
			}
		}
		this.g2d_tree.addMouseListener(new TreeMouseAdapter());
		JScrollPane scroll = new JScrollPane(g2d_tree);
		this.add(scroll, BorderLayout.CENTER);
		{
			this.tool_scene_graph.setToolTipText("打开场景图");
			this.tool_scene_graph.addActionListener(this);
			this.tool_bar.add(tool_scene_graph);

			this.instance_zones = new InstanceZonesManager(studio, progress);
			this.tool_instance_zones = new JButton(new ImageIcon(instance_zones.getIconImage()));
			this.tool_instance_zones.setToolTipText("副本管理器");
			this.tool_instance_zones.addActionListener(this);
			this.tool_bar.add(tool_instance_zones);
			
			this.tool_bar.setFloatable(false);
			this.add(tool_bar, BorderLayout.NORTH);
		}
		g2d_tree.setDragEnabled(true);
		getAllScenes();
		this.g2d_tree.reload();
		
		saveSceneList();
	}

	public InstanceZonesManager getInstanceZonesManager() {
		return instance_zones;
	}
	
//	------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_bar.save) {
			saveAll(new SaveProgressForm());
		}
		else if (e.getSource() == tool_scene_graph) {
			SceneGraphViewer sg = new SceneGraphViewer(this);
			sg.setVisible(true);
		} 
		else if (e.getSource() == tool_instance_zones) {
			instance_zones.setVisible(true);
		}
	}

	public SceneGraph createSceneGraph()
	{
		Vector<SceneNode> 	nodes 	= getAllScenes();
		ArrayList<Scene> 	scenes 	= new ArrayList<Scene>(nodes.size());
		for (SceneNode node : nodes) {
			if (!node.getData().is_instance_zone) {
				scenes.add(node.getData());
			}
		}
		return new SceneGraph(scenes);
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	public Vector<SceneNode> getAllScenes() {
		SceneGroup tree_root = (SceneGroup)g2d_tree.getTreeModel().getRoot();
		return G2DTree.getNodesSubClass(tree_root, SceneNode.class);
	}
	
	@Override
	public Integer createID() {
		return id_factory.createID();
	}

	public boolean containsSceneID(String id) {
		return id_factory.get(Integer.parseInt(id)) != null;
	}
	
	public SceneNode getSceneNode(String id) {
		return id_factory.get(Integer.parseInt(id));
	}
	public SceneNode getSceneNode(int id) {
		return id_factory.get(id);
	}
	
	public void addScene(SceneNode node, SceneGroup root)
	{
		if (node!=null) {
			if (id_factory.storeID(node.getIntID(), node)) {
				root.add(node);
//				if (g2d_tree!=null) {
//					g2d_tree.reload(root);
//				}
			}
		}
	}
	
	public void addScene(File scene_file, SceneGroup root)
	{
		addScene(loadScene(scene_file), root);
	}
	
	public void removeScene(SceneNode node)
	{
		SceneNode removed = id_factory.killID(node.getIntID());
		if (removed!= null) {
			SceneGroup parent = (SceneGroup)node.getParent();
			parent.remove(node);
			if (g2d_tree!=null) {
				g2d_tree.reload(parent);
			}
		}
	}

	public void resetAllResources() {
		for (SceneNode node : getAllScenes()) {
			node.cleanSceneEditor();
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

//	String getPathString(SceneNode node)
//	{
//		String path = node.getID()+".xml";
//		TreeNode p = node.getParent();
//		while (p != null) {
//			if (p.getParent()!=null) {
//				path = p.toString() + "/" + path;
//			}
//			p = p.getParent();
//		}
//		return path;
//	}
//	
//	String[] getPath(String path)
//	{
//		String[] ret = CUtil.splitString(path, "/");
//		return ret;
//	}
	
	public void saveSceneList() 
	{
		synchronized (scene_lock) {
			String list = ObjectGroup.toList(getAllScenes());
			scene_list.writeUTF(list);
//			StringBuffer all_scene = new StringBuffer();
//			File name_list_file = scene_list.getParentFile().getChildFile("name_" + scene_list.getName());
//			StringBuffer all_names = new StringBuffer();
//			for (SceneNode node : getAllScenes()) {
//				all_scene.append(SceneGroup.toPathString(node, "/") + node.getID() + ".xml" + "\n");
//				all_names.append("("+node.getData().id+")"+((NamedObject)node.getData()).getName()+"\n");
//			}
//			scene_list		.writeUTF(all_scene.toString());
//			name_list_file	.writeUTF(all_names.toString());
		}
		System.out.println("save scene list");
	}

	public void saveAll(IProgress progress) 
	{
		synchronized (scene_lock) {
			saveSceneList();
			for (File file : scene_dir.listFiles()) {
				if (file.getName().endsWith(".xml")) {
					file.delete();
				}
			}
			Vector<SceneNode> nodes = getAllScenes();
			if (progress != null) {
				progress.setMaximum("", nodes.size());
			}
			int i = 0;
			for (SceneNode node : nodes) {
				saveScene(scene_dir, node);
				i++;
				if (progress != null) {
					progress.setValue("", i);
				}
			}
		}
	}
	
	public void saveScene(SceneNode node)
	{
		saveSceneList();
		saveScene(scene_dir, node);
	}
	
//	public void loadScene(String node_path, SceneGroup group)
//	{
//		group.buildPath(node_path, group);
//	}
	
//	-------------------------------------------------------------------------------------------------------------------------

	class SceneTree extends G2DTree
	{
		private static final long serialVersionUID = 1L;

		public SceneTree(SceneGroup root) {
			super(root);
		}
	
		@Override
		public String convertValueToText(Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
			if (value instanceof SceneNode) {
				SceneNode node = (SceneNode)value;
				StringBuffer sb = new StringBuffer();
				sb.append("<html><body>");
				sb.append("<p>" + node.getData().getName() + 
						"(<font color=\"#ff0000\">" + node.getData().getIntID()  + "</font>" + ")" +
						" <font color=\"#0000ff\">" + node.getData().getAbilitiesCount() + "能力</font>" +
						"</p>");
				sb.append("<p>" + 
						"<font color=\"#0000ff\">" +
						(node.getData().is_instance_zone ? "副本" : "") + ("G" + node.getData().group) +
						"</font> " +
						"<font color=\"#808080\">" + "资源(" + node.getResourceName()+")"  + "</font>" +
						"</p>");
				sb.append("</body></html>");
				return sb.toString();
			}
			return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
		}
	}
	
	class TreeMouseAdapter extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e) {
			TreePath path = g2d_tree.getPathForLocation(e.getX(), e.getY());			
			if (path != null) {
				Object click_node = path.getLastPathComponent();
				if (click_node == g2d_tree.getSelectedNode()) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (click_node instanceof SceneGroup) {
							new RootMenu((SceneGroup)click_node).show(g2d_tree, e.getX(), e.getY());
						}
						else if (click_node instanceof SceneNode) {
							new SceneMenu((SceneNode)click_node).show(g2d_tree, e.getX(), e.getY());
						}
					}
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==2) {
						if (click_node instanceof SceneNode) {
							((SceneNode)click_node).getSceneEditor().setVisible(true);
						}
					}
				}
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	scene root node
	
	class RootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;

		JMenuItem add_scene 		= new JMenuItem("添加场景");
		JMenuItem set_world_group 	= new JMenuItem("设置世界分组");
		
		public RootMenu(SceneGroup root) {
			super(root, SceneManager.this, SceneManager.this.g2d_tree);
			add_scene.addActionListener(this);
			set_world_group.addActionListener(this);
			add(set_world_group);
			add(add_scene);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			super.actionPerformed(e);
			if (e.getSource() == add_scene) {
				AddSceneDialog dialog = new AddSceneDialog();
				CPJWorld world = dialog.showDialog();
				if (world!=null) {
					SceneNode node = new SceneNode(
							SceneManager.this,
							dialog.getSceneName(),
							Studio.getInstance().getCPJResourceManager().getNodeIndex(world));
					addScene(node, (SceneGroup)root);
					g2d_tree.reload(root);
				}
			}
			else if (e.getSource() == set_world_group) {
				String value = JOptionPane.showInputDialog(SceneManager.this, "设置世界分组", 0);
				if (value != null) {
					try {
						Integer gid = Integer.parseInt(value.trim());
						for (SceneNode tn : G2DTree.getNodesSubClass(root, SceneNode.class)) {
							tn.getData().group = gid;
						}
						g2d_tree.repaint();
					} catch (Exception err) {
						JOptionPane.showMessageDialog(SceneManager.this, err.getMessage());
					}
				}
			}
		}
	}
	
	
	class AddSceneDialog extends CPJResourceSelectDialog<CPJWorld>
	{
		private static final long serialVersionUID = 1L;
		
		TextField text = new TextField();
		
		public AddSceneDialog() {
			super(Studio.getInstance(), CPJResourceType.WORLD);
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel(" 输入场景名字 "), BorderLayout.WEST);
			panel.add(text, BorderLayout.CENTER);
			super.add(panel, BorderLayout.NORTH);
		}
		
		public String getSceneName() {
			return text.getText();
		}
		
		@Override
		protected boolean checkOK() {
			if (text.getText().length()==0) {
				JOptionPane.showMessageDialog(this, "场景名字不能为空！");
				return false;
			}
			if (getList().getSelectedItem()==null) {
				JOptionPane.showMessageDialog(this, "还未选择场景对应的资源！");
				return false;
			}
			return true;
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	scene menu
	
	class SceneMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		SceneNode node;

		JMenuItem info			= new JMenuItem();
		JMenuItem open_scene	= new JMenuItem("打开场景");
		JMenuItem rename_scene	= new JMenuItem("重命名");
		JMenuItem delete_scene	= new JMenuItem("删除");
		
		public SceneMenu(SceneNode node)
		{
			this.node = node;
			info.setText("场景 : " + node.getName() + " (" + node.getID() + ")");
			info.setEnabled(false);
			add(info);
			add(open_scene);
			add(rename_scene);
			add(delete_scene);
			open_scene.addActionListener(this);
			rename_scene.addActionListener(this);
			delete_scene.addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == open_scene) {
				node.getSceneEditor().setVisible(true);
			} 
			else if (e.getSource() == rename_scene) {
				String new_name = JOptionPane.showInputDialog(SceneManager.this, "输入场景名字", node.getData().name);
				if (new_name!=null && new_name.length()>0) {
					node.setName(new_name);
					saveScene(node);
					g2d_tree.repaint();
				}
			}
			else if (e.getSource() == delete_scene) {
				if (JOptionPane.showConfirmDialog(SceneManager.this, "确实要删除场景\"" + node.getName() + "\"！") == JOptionPane.YES_OPTION) {
					removeScene(node);
				}
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------
	

	
//	-------------------------------------------------------------------------------------------------------------------------
//	
	SceneNode loadScene(File file)
	{
		if (file.exists()) {
			Scene data = RPGObjectMap.readNode(file.getInputStream(), file.getPath(), Scene.class);
			if (data!=null) {
				SceneNode node = new SceneNode(data);
				return node;
			}
		}
		return null;
	}
	
	static void saveScene(File root, SceneNode node)
	{
		Scene data = node.getData();
		File file = root.getChildFile(node.getID()+".xml");
		file.writeUTF(RPGObjectMap.writeNode(file.getPath(), data));
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
}
