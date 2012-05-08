package com.g2d.studio.instancezone;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

//import com.cell.rpg.scene.script.SceneScriptManager;
import com.g2d.studio.StudioConfig;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormDynamic;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.IProgress;
import com.g2d.studio.io.File;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

@SuppressWarnings("serial")
public class InstanceZonesManager extends ManagerFormDynamic
{
//	------------------------------------------------------------------------------------------------------------------------------
	
	final public File				zones_dir;
	final public File				zones_list;
	final public File 				script_root;
	private InstanceZonesTreeView	g2d_tree;

//	------------------------------------------------------------------------------------------------------------------------------
	
	public InstanceZonesManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "副本管理器", Res.icon_res_6);

		progress.startReadBlock("初始化副本...");

		this.zones_dir		= studio.project_save_path.getChildFile("instance_zones");
		this.zones_list		= zones_dir.getChildFile("zones.list");
		this.script_root	= Studio.getInstance().project_path;
		
		this.g2d_tree = new InstanceZonesTreeView(zones_list, progress);
		this.add(g2d_tree, BorderLayout.CENTER);
		
	}
	
	public Vector<InstanceZoneNode> getNodes() {
		return g2d_tree.getAllObject();
	}
	
	public InstanceZoneNode getNode(int id) {
		return g2d_tree.getNode(id);
	}
	
	public InstanceZoneNode getNode(Integer id) {
		if (id != null) {
			return g2d_tree.getNode(id);
		}
		return null;
	}
	
	@Override
	public void saveAll(IProgress progress) throws Throwable {
		for (InstanceZoneNode node : getNodes()) {
			try {
				node.getEditComponent().save();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		g2d_tree.saveAll(progress);
	}
	@Override
	public void saveSingle() throws Throwable {
		InstanceZoneNode node = g2d_tree.getSelected();
		if (node != null) {
			try {
				node.getEditComponent().save();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		g2d_tree.saveSingle();
	}
//	----------------------------------------------------------------------------------------------------------
//	
//	public Vector<InstanceZoneNode> getAllZones() {
//		return G2DTree.getNodesSubClass(tree_root, InstanceZoneNode.class);
//	}
//	
//	@Override
//	public Integer createID() {
//		return id_factory.createID();
//	}
//
//	public boolean containsZoneID(String id) {
//		return id_factory.get(Integer.parseInt(id)) != null;
//	}
//	
//	public InstanceZoneNode getZoneNode(String id) {
//		return id_factory.get(Integer.parseInt(id));
//	}
//	public InstanceZoneNode getZoneNode(int id) {
//		return id_factory.get(id);
//	}
//	
//	public void addZone(InstanceZoneNode node, InstanceZoneGroup root)
//	{
//		if (node!=null) {
//			if (id_factory.storeID(node.getIntID(), node)) {
//				root.add(node);
//				if (g2d_tree!=null) {
//					g2d_tree.reload(root);
//				}
//			}
//		}
//	}
//	
//	public void addZone(File xml_file, InstanceZoneGroup root)
//	{
//		addZone(loadZone(xml_file), root);
//	}
//	
//	public void removeZone(InstanceZoneNode node)
//	{
//		InstanceZoneNode removed = id_factory.killID(node.getIntID());
//		if (removed!= null) {
//			InstanceZoneNode parent = (InstanceZoneNode)node.getParent();
//			parent.remove(node);
//			if (g2d_tree!=null) {
//				g2d_tree.reload(parent);
//			}
//		}
//	}
//
////	------------------------------------------------------------------------------------------------------------------------------
//
//	public void saveAll() 
//	{
//		StringBuffer all_scene = new StringBuffer();
//		File name_list_file = new File(zones_list.getParentFile(), "name_" + zones_list.getName());
//		StringBuffer all_names = new StringBuffer();
//		for (InstanceZoneNode node : getAllZones()) {
//			all_scene.append(InstanceZoneGroup.toPathString(node, "/") + node.getID() + ".xml" + "\n");
//			all_names.append("("+node.getData().getIntID()+")"+((NamedObject)node.getData()).getName()+"\n");
//		}
//		com.cell.io.CFile.writeText(zones_list, all_scene.toString(), "UTF-8");
//		com.cell.io.CFile.writeText(name_list_file, all_names.toString(), "UTF-8");
//
//		for (InstanceZoneNode node : getAllZones()) {
//			saveScene(zones_dir, node);
//		}
//	}

//	-------------------------------------------------------------------------------------------------------------------------
////	
//	private InstanceZoneNode loadZone(File file)
//	{
//		if (file.exists()) {
//			InstanceZone data = RPGObjectMap.readNode(file.getPath(), InstanceZone.class);
//			if (data!=null) {
//				InstanceZoneNode node = new InstanceZoneNode(data);
//				return node;
//			}
//		}
//		return null;
//	}
//	
//	private void saveScene(File root, InstanceZoneNode node)
//	{
//		InstanceZone data = node.getData();
//		File file = new File(root, node.getID()+".xml");
//		RPGObjectMap.writeNode(data, file);
//	}

	
	
	
//	public QuestItemManager getQuestItems() {
//		return quest_items;
//	}
	
	
	

}
