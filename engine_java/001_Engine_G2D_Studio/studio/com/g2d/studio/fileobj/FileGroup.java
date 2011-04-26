package com.g2d.studio.fileobj;

import com.g2d.studio.swing.G2DListItem;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeListView.NodeGroup;

public class FileGroup<T extends FileObject> extends NodeGroup<T> 
{
	private static final long serialVersionUID = 1L;

	public FileGroup(String name) {
		super(name);
	}

	@Override
	protected G2DTreeNodeGroup<?> createGroupNode(String name) {
		return new FileGroup<T>(name);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void removeFromParent() {
		FileGroup parent = (FileGroup)getParent();
		super.removeFromParent();
		for (G2DListItem item : items.values()) {
			parent.addItem(item);
		}
	}
}
