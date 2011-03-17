package com.g2d.studio.instancezone;


import java.awt.Dimension;

import com.cell.rpg.scene.instance.InstanceZone;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.io.File;
import com.g2d.studio.swing.G2DTree;

@SuppressWarnings("serial")
public class InstanceZonesTreeView extends ObjectTreeViewDynamic<InstanceZoneNode, InstanceZone>
{
	private static final long serialVersionUID = 1L;

	public InstanceZonesTreeView(File list_file, ProgressForm progress) {
		super("副本管理器", InstanceZoneNode.class, InstanceZone.class, list_file, progress);
		super.left.setMinimumSize(new Dimension(300, 300));
		super.getTree().expandAll();
	}
	
	@Override
	protected ObjectGroup<InstanceZoneNode, InstanceZone> createTreeRoot(String title) {
		return new InstanceZoneGroup(title, this);
	}

	@Override
	public G2DTree createTree(ObjectGroup<InstanceZoneNode, InstanceZone> treeRoot) {
		return new TreeView(treeRoot);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	class TreeView extends G2DTree
	{
		public TreeView(ObjectGroup<InstanceZoneNode, InstanceZone> treeRoot) {
			super(treeRoot);
		}

		@Override
		public String convertValueToText(Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
			if (value instanceof InstanceZoneNode) {
				InstanceZoneNode node = (InstanceZoneNode)value;
				StringBuffer sb = new StringBuffer();
				sb.append("<html><body><p>");
				sb.append("<font color=\"#0000ff\"> [" + node.getData().player_count  + "人]</font> ");
				sb.append(node.getData().getName());
				sb.append("(<font color=\"#ff0000\">" + node.getData().getIntID()  + "</font>" + ")");
				sb.append("</p></body></html>");
				return sb.toString();
			}
			return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------


	
	

	
}
