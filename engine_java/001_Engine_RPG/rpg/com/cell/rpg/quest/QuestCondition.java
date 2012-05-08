package com.cell.rpg.quest;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import com.cell.CIO;
import com.cell.ExtObject;
import com.cell.util.MarkedHashtable;

public class QuestCondition extends ExtObject
{
	TreeSet<String> 					item_name_list	= new TreeSet<String>();
	
	LinkedHashMap<Integer, QuestItem> 	items 			= new LinkedHashMap<Integer, QuestItem>(0);
	
	@Override
	protected void onRead(MarkedHashtable data) 
	{
		item_name_list	= data.getObject("item_name_list", new TreeSet<String>());
		// new data
		{
			int items_count = data.getInt("items_count", 0);
			items = new LinkedHashMap<Integer, QuestItem>(items_count);
			for (int i=0; i<items_count; i++) {
				QuestItem item = data.getObject("item_" + i, null);
				if (item != null) {
					items.put(item.getType(), item);
				}
			}
		}
	}
	
	@Override
	protected void onWrite(MarkedHashtable data) {
		data.put("item_name_list", item_name_list);
		// new data
		{
			int items_count = items.size();
			data.putInt("items_count", items_count);
			int i = 0;
			for (QuestItem item : items.values()) {
				data.put("item_" + i, item);
				i++;
			}
		}
	}

	public void clearQuestItem() {
		item_name_list.clear();
		items.clear();
	}
	
//	public LinkedHashMap<Integer, QuestItem> getItems() {
//		return items;
//	}
	
	public TreeSet<String> getNameList() {
		return item_name_list;
	}
	
	
	public int addQuestItem(QuestItem item) {
		items.put(item.getType(), item);
		return item.getType();
	}
	
	public QuestItem removeQuestItem(QuestItem item) {
		return items.remove(item.getType());
	}
	
	public QuestItem getQuestItem(int id) {
		return items.get(id);
	}
	
	public Collection<QuestItem> getItems() {
		return items.values();
	}
	
	
	
	public static int getQuestItemID(String list_line) {
		return Integer.parseInt(CIO.getPathName(list_line));
	}
}
