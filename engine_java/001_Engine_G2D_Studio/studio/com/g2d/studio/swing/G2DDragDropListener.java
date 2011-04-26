package com.g2d.studio.swing;

import java.awt.Component;
import java.awt.datatransfer.Transferable;

public interface G2DDragDropListener<T extends Component> {

	public void onDragDrop(T comp, Transferable t, Object drag_node, Object drop_node);

}
