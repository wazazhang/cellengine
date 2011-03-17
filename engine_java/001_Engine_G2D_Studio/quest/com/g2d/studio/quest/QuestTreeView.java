package com.g2d.studio.quest;

import java.util.Vector;

import com.cell.rpg.quest.Quest;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.io.File;
import com.g2d.studio.quest.group.DQuestGroup;
import com.g2d.studio.swing.G2DTree;

public class QuestTreeView extends ObjectTreeViewDynamic<QuestNode, Quest>
{
	private static final long serialVersionUID = 1L;

	public QuestTreeView(File quest_list_file, ProgressForm progress) {
		super("任务管理器", QuestNode.class, Quest.class, quest_list_file, progress);
	}
	
	@Override
	protected ObjectGroup<QuestNode, Quest> createTreeRoot(String title) {
		return new QuestNodeGroup(title, this);
	}

	@Override
	public G2DTree createTree(ObjectGroup<QuestNode, Quest> treeRoot) {
		return new QuestTree(treeRoot);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	class QuestTree extends G2DTree
	{
		public QuestTree(ObjectGroup<QuestNode, Quest> treeRoot) {
			super(treeRoot);
		}
	
		@Override
		public String convertValueToText(Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
			if (value instanceof QuestNode) {
				QuestNode node = (QuestNode)value;
				StringBuffer sb = new StringBuffer();
				sb.append("<html><body>");
				sb.append("<p>");
				sb.append(node.getName());
				sb.append("<font color=0000ff> - " + getGroupTag(node) + "</font>");
				sb.append("</p>");
				sb.append("</body></html>");
				return sb.toString();
			}
			return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
		}
		
		String getGroupTag(QuestNode node) {
			StringBuffer sb = new StringBuffer();
			Vector<DQuestGroup> groups = Studio.getInstance().getQuestGroupManager().getQuestGroups();
			for (DQuestGroup g : groups) {
				if (g.getData().quest_ids != null && g.getData().quest_ids.contains(node.getIntID())) {
					sb.append(g.getIntID()+",");
				}
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
				return "[编组:" + sb.toString()+"]";
			}
			return "";
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
}
