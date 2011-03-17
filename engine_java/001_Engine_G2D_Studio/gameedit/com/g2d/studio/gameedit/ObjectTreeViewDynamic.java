package com.g2d.studio.gameedit;

import com.g2d.studio.io.File;

import com.cell.rpg.RPGObject;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;

public abstract class ObjectTreeViewDynamic<T extends DynamicNode<D>, D extends RPGObject> 
extends ObjectTreeView<T, D> implements IDynamicIDFactory<T>
{
	private static final long serialVersionUID = 1L;
	
	public ObjectTreeViewDynamic(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			File		list_file, 
			ProgressForm progress) 
	{
		super(title, node_type, data_type, list_file, progress);
		getTreeRoot().loadList(progress);
		reload();
		getTree().setDragEnabled(true);
		saveListFile();
	}
	
	@Override
	public Integer createID() {
		return node_index.createID();
	}
	
//	---------------------------------------------------------------------------------------------------------------------------
	
}
