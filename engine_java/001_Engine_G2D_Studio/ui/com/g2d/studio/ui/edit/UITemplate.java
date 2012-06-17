package com.g2d.studio.ui.edit;

import java.io.File;

import javax.swing.ImageIcon;

import com.g2d.BufferedImage;
import com.g2d.Engine;
import com.g2d.Graphics2D;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.swing.G2DListItemData;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UEFileNode;

public class UITemplate extends G2DTreeNode<UITemplate>
{
	final public UILayout rect;
	
	final public String name;
	
	private final Class<?> 	uiType;
	private final File 		userFile;
	
	public UITemplate(UILayout rect, Class<?> uiType, String name) {
		this.name = name;
		this.rect = rect;
		this.uiType = uiType;
		this.userFile = null;
	}
	public UITemplate(File file, String name) {
		this.name = name;
		this.rect = new UILayout(ImageStyle.NULL);
		this.uiType = UEFileNode.class;
		this.userFile = file;
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
	
	public File getUserTemplate() {
		return userFile;
	}

	public Class<?> getUIType() {
		return uiType;
	}
	
	public UIComponent createDisplay() {
		try {
			if (userFile != null) {
				return new UEFileNode(userFile.getName());
			} else {
				return (UIComponent) getUIType().newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new UECanvas();
		}
	}
	
	
	@Override
	public String getName() {
		return name;
	}
}
