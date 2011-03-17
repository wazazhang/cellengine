package com.g2d.editor.property;

import java.awt.Component;
import java.util.Collection;

public interface ObjectPropertyEdit {

	/**
	 * 得到编辑器单元格编辑器插件
	 * @return
	 */
	public Collection<CellEditAdapter<?>> getAdapters();
	
	/**
	 * 得到当前编辑器对应的控件
	 * @return
	 */
	public Component getComponent();
	
}
