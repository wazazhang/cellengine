package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cell.rpg.RPGObject;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.ManagerFormDynamic;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DWindowToolBar;

@SuppressWarnings("serial")
public class ObjectManagerTree<T extends ObjectNode<D>, D extends RPGObject> extends ManagerFormDynamic
{	
	final ObjectTreeView<T, D>	tree_view;
	
	public ObjectManagerTree(Studio studio, ProgressForm progress, Image icon, ObjectTreeView<T, D> tree_view) 
	{
		super(studio, progress, tree_view.getTitle(), icon);
		this.tree_view = tree_view;
		this.add(tool_bar, BorderLayout.NORTH);
		this.add(tree_view, BorderLayout.CENTER);
	}


	@Override
	public void saveAll() throws Throwable {
		tree_view.saveAll();
	}

	@Override
	public void saveSingle() throws Throwable {
		tree_view.saveSingle();
	}
	
	void initToolbars(ObjectManager manager) {}

}
