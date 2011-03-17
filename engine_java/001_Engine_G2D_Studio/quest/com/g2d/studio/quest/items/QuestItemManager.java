package com.g2d.studio.quest.items;

import java.io.File;
import java.util.Vector;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.quest.QuestItem;
import com.cell.util.IDFactoryInteger;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTree;

public class QuestItemManager extends IDFactoryInteger<QuestItemNode> implements IDynamicIDFactory<QuestItemNode>
{
//	File quest_items_dir;
	File quest_items_list;
	
	public QuestItemManager(Studio studio, File quest_items_list)
	{
		this.quest_items_list = quest_items_list;
		loadList();
	}
	
	@Override
	public synchronized Integer createID() {
		return super.createID();
	}

	public synchronized QuestItemNode createQuestItem(String name, boolean is_award) {
		QuestItemNode node = new QuestItemNode(this, name, is_award);
		super.storeID(node.getIntID(), node);
		return node;
	}

	public synchronized void saveAll()
	{
		saveList();
	}

	public Vector<QuestItemNode> getAllNodes() {
		Vector<QuestItemNode> ret = new Vector<QuestItemNode>();
		synchronized(this) {
			for (QuestItemNode e : this) {
				ret.add(e);
			}
		}
		return ret;
	}
	
	private void loadList()
	{
		try{
			if (quest_items_list.exists()) {
				String[] lines = CIO.readAllLine(quest_items_list.getPath(), "UTF-8");
				for (String line : lines) {
					try{
						if (line.toLowerCase().endsWith(ObjectGroup._XML)) {
							File file = new File(quest_items_list.getParentFile(), line);
							QuestItem data = RPGObjectMap.readNode(file.getPath(), QuestItem.class);
							QuestItemNode node = new QuestItemNode(data);
							super.storeID(node.getIntID(), node);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	private void saveList()
	{
		if (!quest_items_list.getParentFile().exists()) {
			quest_items_list.getParentFile().mkdirs();
		}
		StringBuffer all_objects = new StringBuffer();
		for (QuestItemNode node : this) {
			try{
				String line = node.getID()+ObjectGroup._XML;
				File xml_file = new File(quest_items_list.getParentFile(), line);
				if (RPGObjectMap.writeNode(node.getData(), xml_file)){
					all_objects.append(line + "\n");
				}
			}catch(Exception err){}
		}
		com.cell.io.CFile.writeText(quest_items_list, all_objects.toString(), "UTF-8");
	}
	
	public static class QuestItemList extends G2DList<QuestItemNode>
	{
		public QuestItemList() {
			super(Studio.getInstance().getQuestManager().getQuestItems().getAllNodes());
		}
	}
}
