package com.g2d.studio.ui.edit;

import javax.swing.ImageIcon;

import com.g2d.BufferedImage;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.swing.G2DListItemData;
import com.g2d.studio.swing.G2DTreeNode;

public class UITemplate extends G2DTreeNode<UITemplate>
{
	final public String name;
	final public Class<?> uiType;
	final public UILayout rect;
	
	
	public UITemplate(UILayout rect, Class<?> uiType, String name) {
		this.name = name;
		this.rect = rect;
		this.uiType = uiType;
	}
	@Override
	protected ImageIcon createIcon() {
		BufferedImage sic = Engine.getEngine().createImage(40, 30);
		Graphics2D g = sic.createGraphics();
		g.setClip(0, 0, 40, 30);
		rect.render(g, 0, 0, 40, 30);
		g.dispose();
		return new ImageIcon(AwtEngine.unwrap(sic));
	}
	
	@Override
	public String getName() {
		return name;
	}
}
