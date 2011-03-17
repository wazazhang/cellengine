package com.g2d.studio.gameedit.entity;


import java.util.Vector;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.io.File;



public abstract class ObjectGroup<T extends ObjectNode<D>, D extends RPGObject> extends G2DTreeNodeGroup<ObjectNode<D>>
{
	private static final long serialVersionUID = 1L;
	
	final static public String _XML	= ".xml";
	
	final public File		list_file;
	final public Class<D>	data_type;
	final public Class<T>	node_type;
	
	public ObjectGroup(String name, File list_file, Class<T> node_type, Class<D> data_type) {
		super(name);
		this.list_file = list_file;
		this.data_type = data_type;
		this.node_type = node_type;
	}

	abstract protected boolean createObjectNode(String key, D data);

	
//	----------------------------------------------------------------------------------------------------------
	
	@Override
	protected boolean pathAddLeafNode(String name, int index, int length) {
		if (name.toLowerCase().endsWith(_XML)) {
			try{
				String key = CUtil.replaceString(name, _XML, "");
				File file = Studio.getInstance().getIO().createFile(list_file.getParentFile(), name);
				D data = RPGObjectMap.readNode(file.getInputStream(), file.getPath(), data_type);
				return createObjectNode(key, data);
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		return false;
	}
	
	public void saveAll()
	{
		saveListFile();
		
		Vector<T> nodes = G2DTree.getNodesSubClass(this, node_type);
		for (T node : nodes) {
			try {
				node.onSave();
				File xml_file = Studio.getInstance().getIO().createFile(
						list_file.getParentFile(), node.getID()+_XML);
				String xml = RPGObjectMap.writeNode(xml_file.getPath(), node.getData());
				if (xml != null){
					xml_file.writeBytes(CIO.stringEncode(xml, CIO.ENCODING));
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

	public void saveSingle(T snode)
	{
		if (snode != null) {
			saveListFile();
			try {
				snode.onSave();
				File xml_file = Studio.getInstance().getIO().createFile(
						list_file.getParentFile(), snode.getID()+_XML);
				String xml = RPGObjectMap.writeNode(xml_file.getPath(), snode.getData());
				if (xml != null){
					xml_file.writeBytes(CIO.stringEncode(xml, CIO.ENCODING));
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}
	
	public void saveListFile()
	{
		try {
			Vector<T> nodes = G2DTree.getNodesSubClass(this, node_type);
			list_file.writeBytes(CIO.stringEncode(toList(nodes), CIO.ENCODING));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadList(ProgressForm progress)
	{
		try{
			if (list_file.exists()) {
				String[] lines = CIO.readAllLine(list_file.getPath(), "UTF-8");
				progress.setMaximum(list_file.getName(), lines.length);
				int i = 0;
				for (String line : lines) {
					try {
						loadPath(line.trim());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						progress.setValue(line, i);
						i++;
					}
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	
	public static<T extends ObjectNode<?>> String toList(Vector<T> nodes)
	{
		StringBuffer 	all_objects 	= new StringBuffer();
		try {
//			File name_list_file	= Studio.getInstance().getIO().createFile(
//					list_file.getParentFile(), "name_" + list_file.getName());
//			StringBuffer 	all_names 		= new StringBuffer();
//			Vector<T> 		nodes 			= G2DTree.getNodesSubClass(this, node_type);
			for (T node : nodes) {
				String line = toPathString(node, "/") + node.getID() + _XML;
				if (node.getData() instanceof NamedObject) {
//					all_names.append("("+node.getData().id+")"+((NamedObject)node.getData()).getName()+"\n");
					line += "?name=" + ((NamedObject)node.getData()).getName();
				}
				all_objects.append(line + "\r\n");
			}
//			list_file.writeBytes(CIO.stringEncode(all_objects.toString(), CIO.ENCODING));
//			if (NamedObject.class.isAssignableFrom(data_type)) {
//				name_list_file.writeBytes(CIO.stringEncode(all_names.toString(), CIO.ENCODING));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all_objects.toString();
	}
	
}
