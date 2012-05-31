package com.g2d.studio.ui.edit;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;

import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.studio.swing.G2DList;
import com.g2d.studio.swing.G2DTree;

public class UITemplateList extends G2DTree
{
	DefaultMutableTreeNode template = new DefaultMutableTreeNode("基础控件", true);
	
	public UITemplateList(UIEdit edit) {
		super(new DefaultMutableTreeNode());
		super.setRootVisible(false);

		for (UITemplate ut : edit.getLayoutManager().getTemplates()) {
			template.add(ut);
		}
		getRoot().add(template);
		
		reload();
		
		setDragEnabled(true);
		
	}
	
	@Override
	protected boolean checkDrag(DropTarget evt_source, Transferable trans,
			Object src, Object dst, int position) {
		return false;
	}
}
