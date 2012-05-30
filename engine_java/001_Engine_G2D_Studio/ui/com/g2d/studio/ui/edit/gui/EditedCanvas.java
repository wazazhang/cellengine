package com.g2d.studio.ui.edit.gui;

import com.g2d.Color;
import com.g2d.Graphics2D;
import com.g2d.annotation.Property;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.studio.ui.edit.UIEdit;
import com.g2d.studio.ui.edit.UITreeNode;

public class EditedCanvas extends Container implements IComponent
{
	private UITreeNode tree_node;
	private UIEdit edit;
	private String name;
	
	public EditedCanvas() 
	{
		this.enable_drag	= true;
		this.enable_drag_resize = true;
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		if (edit.getTree().getSelectedNode() == tree_node) {
			g.setColor(Color.WHITE);
			g.drawRect(0, 0, local_bounds.width - 1, local_bounds.height - 1);
		}
	}
	
	
	@Override
	public void init(UIEdit edit) {
		this.tree_node = new UITreeNode(this, edit);
		this.edit = edit;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	public UITreeNode getTreeNode() {
		return tree_node;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public UIComponent asComponent() {
		return this;
	}
	
	@Override
	protected void onMouseDown(MouseEvent event) {
		edit.onSelectTreeNode(this.tree_node);
	}
}
