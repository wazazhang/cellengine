package com.g2d.studio.ui.edit;

import javax.swing.ImageIcon;

import com.g2d.BufferedImage;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.swing.G2DListItemData;

public class UITemplate extends G2DListItemData<UILayout>
{
	Class<?> uiType;

	ImageIcon icon;
	
	public UITemplate(UILayout rect, Class<?> uiType, String name) {
		super(rect, name);
		this.uiType = uiType;
	}
	
	@Override
	public ImageIcon getListIcon(boolean update) {
		if (icon == null || update) {
			BufferedImage sic = Engine.getEngine().createImage(40, 30);
			Graphics2D g = sic.createGraphics();
			g.setClip(0, 0, 40, 30);
			getData().render(g, 0, 0, 40, 30);
			g.dispose();
			icon = new ImageIcon(AwtEngine.unwrap(sic));
		}
		return icon;
	}
}
