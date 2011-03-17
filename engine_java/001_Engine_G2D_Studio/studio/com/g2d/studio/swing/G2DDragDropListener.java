package com.g2d.studio.swing;

import java.awt.Component;

public interface G2DDragDropListener<T extends Component> {

	public void onDragDrop(T comp, Object drag_node, Object drop_node);

}
