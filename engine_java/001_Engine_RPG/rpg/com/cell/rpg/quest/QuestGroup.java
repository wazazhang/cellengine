package com.cell.rpg.quest;

import java.util.Collections;
import java.util.Vector;

import com.cell.rpg.template.TemplateNode;

/**
 * 一个任务的全部描述
 * @author WAZA
 */
public class QuestGroup extends TemplateNode
{
//	----------------------------------------------------------------------------------------------------------------
//	base
	
	/**任务节点序列*/
	public Vector<Integer>			quest_ids		= new Vector<Integer>();
	
//	----------------------------------------------------------------------------------------------------------------
	public QuestGroup(int int_id, String name) 
	{
		super(int_id, name);
	}
	
	synchronized public void addQuestNode(Integer id)
	{
		this.quest_ids.add(id);
	}
	
	synchronized public Vector<Integer> getQuestNodes()
	{
		return this.quest_ids;
	}
	
	synchronized public boolean isLastQuestNode(int questID)
	{
		if (this.quest_ids != null)
		{
			Integer last = this.quest_ids.lastElement();
			
			if ( (last != null) && (last == questID) )
				return true;
		}
		
		return false;
	}
	
	synchronized public void sort()
	{
		Collections.sort(this.quest_ids);
	}


	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{};
	}

}


