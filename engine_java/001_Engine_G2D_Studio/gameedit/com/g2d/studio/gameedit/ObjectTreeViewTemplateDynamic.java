package com.g2d.studio.gameedit;

import com.cell.rpg.RPGObject;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;

public abstract class ObjectTreeViewTemplateDynamic<T extends DynamicNode<D>, D extends RPGObject> 
extends ObjectTreeView<T, D> implements IDynamicIDFactory<T>
{
	private static final long serialVersionUID = 1L;
	
	public ObjectTreeViewTemplateDynamic(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			String		objects_dir,
			ProgressForm progress) 
	{
		super(title, node_type, data_type, ObjectManager.toListFile(objects_dir, data_type), progress);
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
