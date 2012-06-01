package com.g2d.studio.ui.edit;

import java.io.File;

import com.g2d.BufferedImage;
import com.g2d.Engine;
import com.g2d.Tools;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.property.PopupCellEditUILayout;
import com.g2d.geom.Rectangle;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.ui.edit.gui.UEButton;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UEImageBox;
import com.g2d.studio.ui.edit.gui.UELabel;
import com.g2d.studio.ui.edit.gui.UETextBox;
import com.g2d.studio.ui.edit.gui.UETextInput;

public class UILayoutManager extends com.g2d.display.ui.layout.UILayoutManager
{
	
	
	private UILayout ui_null 	= new UILayout(ImageStyle.NULL);
	private UILayout ui_default = new UILayout();

	private UILayout ui_btn_d 	= new UILayout();
	private UILayout ui_btn_u 	= new UILayout();
	
	private UILayout ui_label = new UILayout();
	private UILayout ui_canvas = new UILayout();
	
	private UIEdit edit;
	
	public UILayoutManager(UIEdit edit)
	{
		this.edit = edit;
		
		PopupCellEditUILayout.uilayout_root = edit.workdir;
		
		ui_btn_d.setImages(
				Tools.readImage("/com/g2d/studio/ui/edit/res/btn1-d.png"), 
				UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
				10);
		ui_btn_u.setImages(
				Tools.readImage("/com/g2d/studio/ui/edit/res/btn1-u.png"), 
				UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
				10);
		
		ui_label.setImages(
				Tools.readImage("/com/g2d/studio/ui/edit/res/lable.png"), 
				UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
				10);
		
		ui_canvas.setImages(
				Tools.readImage("/com/g2d/studio/ui/edit/res/panel.png"), 
				UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
				10);
		
		setInstance(this);
	}
	
	public void setLayout(UIComponent ui)
	{
		if (ui instanceof UEButton) {
			((UEButton)ui).setLayout(ui_btn_u, ui_btn_d);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UECanvas) {
			ui.setLayout(ui_canvas);	
			ui.setSize(200, 200);
		}
		else if (ui instanceof UELabel) {
			ui.setLayout(ui_null);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UETextInput) {
			ui.setLayout(ui_label);
			ui.setSize(100, 100);
		}
		else if (ui instanceof UETextBox) {
			ui.setLayout(ui_label);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UEImageBox) {
			ui.setLayout(ui_null);
			ui.setSize(100, 100);
		}
		else {
			ui.setLayout(ui_default);
		}
		
		if (edit.isGridEnable()) 
		{
			gridSize(ui.local_bounds);
		}
	}
	
	public void gridPos(UIComponent ui) 
	{
		int gw = edit.getGridSize();
		int gw2 = gw/2;
		
		int pw = ui.getX() % gw;
		int ph = ui.getY() % gw;
		if (pw < gw2) {
			ui.x = ui.x - pw;
		} else {
			ui.x = ui.x - pw + gw;
		}
		if (ph < gw2) {
			ui.y = ui.y - ph;
		} else {
			ui.y = ui.y - ph + gw;
		}
	}
	
	public void gridSize(Rectangle rect) 
	{
		int gw = edit.getGridSize();
		rect.x = ((int)rect.x)/gw*gw;
		rect.y = ((int)rect.y)/gw*gw;

		if (rect.width <= gw) {
			rect.width  = gw;
		} else {
			rect.width  = rect.width/gw*gw;
		}
		if (rect.height <= gw) {
			rect.height = gw;
		} else {
			rect.height = rect.height/gw*gw;
		}
	}
	
	public UITemplate[] getTemplates() {
		UITemplate[] templates = new UITemplate[]{
				new UITemplate(ui_btn_u, 	UEButton.class, 	"Button"),
				new UITemplate(ui_null, 	UEImageBox.class, 	"ImageBox"),
				new UITemplate(ui_null, 	UELabel.class, 		"Label"),
				new UITemplate(ui_canvas, 	UECanvas.class, 	"Canvas"),
				new UITemplate(ui_label, 	UETextInput.class, 	"TextInput"),
				new UITemplate(ui_label, 	UETextBox.class, 	"TextBox"),
		};
		return templates;
	}

	public ImageUILayout createLayout(
			String image_file, 
			ImageStyle style,
			int clip_border)
	{
		try {
			BufferedImage buf = Engine.getEngine().createImage(
					edit.getStream(image_file));
			if (buf != null) {
				return new ImageUILayout(buf, 
						edit.getSubFile(image_file), 
						style,
						clip_border);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void putLayout(ImageUILayout layout)
	{
		try {
			File file = edit.getSubFile(layout.image_file.getName());
			if (!file.exists()) {
				Tools.writeImage(file.getPath(), "png", layout.getSrcImage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
