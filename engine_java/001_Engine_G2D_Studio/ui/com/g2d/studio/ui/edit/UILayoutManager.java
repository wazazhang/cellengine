package com.g2d.studio.ui.edit;

import java.io.File;
import java.util.HashMap;

import com.cell.CUtil;
import com.g2d.BufferedImage;
import com.g2d.Engine;
import com.g2d.Tools;
import com.g2d.display.ui.Button;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;
import com.g2d.editor.property.PopupCellEditUILayout;
import com.g2d.geom.Rectangle;
import com.g2d.java2d.impl.AwtEngine;
import com.g2d.studio.ui.edit.gui.UEButton;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UEFileNode;
import com.g2d.studio.ui.edit.gui.UEImageBox;
import com.g2d.studio.ui.edit.gui.UELabel;
import com.g2d.studio.ui.edit.gui.UERoot;
import com.g2d.studio.ui.edit.gui.UETextBox;
import com.g2d.studio.ui.edit.gui.UETextBoxHtml;
import com.g2d.studio.ui.edit.gui.UETextInput;
import com.g2d.studio.ui.edit.gui.UEToggleButton;

public class UILayoutManager extends com.g2d.display.ui.layout.UILayoutManager
 {
	private UILayout ui_default = new UILayout(ImageStyle.COLOR);
	private UILayout ui_null = new UILayout(ImageStyle.NULL);
	
	private UILayout ui_root = new UILayout();
	private UILayout ui_btn_d = new UILayout();
	private UILayout ui_btn_u = new UILayout();
	private UILayout ui_label = new UILayout();
	private UILayout ui_canvas = new UILayout();
	private UILayout ui_imagebox = new UILayout();
	private UILayout ui_textinput = new UILayout();
	private UILayout ui_textbox = new UILayout();
	
	
	private HashMap<String, BufferedImage>	image_map	= new HashMap<String, BufferedImage>();
	
	
	
	private UIEdit edit;
	
	public UILayoutManager(UIEdit edit)
	{
		this.edit = edit;
		
		PopupCellEditUILayout.uilayout_root = edit.workdir;
		
		initLayout(ui_root, UIEditConfig.UI_DEFAULT_ROOT);
		initLayout(ui_btn_d, UIEditConfig.UI_DEFAULT_BUTTON_D);
		initLayout(ui_btn_u, UIEditConfig.UI_DEFAULT_BUTTON_U);
		initLayout(ui_label, UIEditConfig.UI_DEFAULT_LABEL);
		initLayout(ui_canvas, UIEditConfig.UI_DEFAULT_CANVAS);
		initLayout(ui_imagebox, UIEditConfig.UI_DEFAULT_IMAGEBOX);
		initLayout(ui_textinput, UIEditConfig.UI_DEFAULT_TEXTINPUT);
		initLayout(ui_textbox, UIEditConfig.UI_DEFAULT_TEXTBOX);		
		
		   
		
		setInstance(this);
	}
	
	private void initLayout(UILayout layout, String cfg) 
	{
		String[] split = CUtil.splitString(cfg, ",", true);
		ImageStyle style = ImageStyle.valueOf(split[1]);
		if (style == ImageStyle.COLOR || style == ImageStyle.NULL) {
			layout.setStyle(style);
		} else {
			layout.setImages(getImage(split[0]), style,
					Integer.parseInt(split[2]));
		}
	}
	
	public void setLayout(UIComponent ui)
	{
		if (ui instanceof UERoot) {
			ui.setLayout(ui_root);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UEButton || ui instanceof UEToggleButton) {
			((Button)ui).setLayout(ui_btn_u, ui_btn_d);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UECanvas) {
			ui.setLayout(ui_canvas);	
			ui.setSize(200, 200);
		}
		else if (ui instanceof UELabel) {
			ui.setLayout(ui_label);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UETextInput) {
			ui.setLayout(ui_textinput);
			ui.setSize(100, 100);
		}
		else if (ui instanceof UETextBox || ui instanceof UETextBoxHtml) {
			ui.setLayout(ui_textbox);
			ui.setSize(100, 30);
		}
		else if (ui instanceof UEImageBox) {
			ui.setLayout(ui_imagebox);
			ui.setSize(100, 100);
		}
		else {
			ui.setLayout(ui_default);
		}
		
		if (edit.isToolGridEnable()) 
		{
			gridSize(ui.local_bounds);
		}
	}
	
	public BufferedImage getImage(String subpath)
	{
		BufferedImage ret = image_map.get(subpath);
		if (ret == null) {
			if (subpath.startsWith("/")) {
				ret = Tools.readImage(subpath);
			} else {
				ret = Tools.readImage(edit.resdir.getPath()+"/"+subpath);
			}
			if (ret != null) {
				image_map.put(subpath, ret);
			}
		}
		return ret;
	}
	
	public BufferedImage putImage(String subpath, BufferedImage buff)
	{
		image_map.put(subpath, buff);
		try {
			File file = new File(edit.resdir, subpath);
			if (!file.exists()) {
				Tools.writeImage(file.getPath(), "png", buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image_map.get(subpath);
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
				new UITemplate(ui_btn_u, 	UEButton.class, "Button"),
				new UITemplate(ui_btn_u, 	UEToggleButton.class, "ToggleButton"),
				new UITemplate(ui_imagebox, UEImageBox.class, "ImageBox"),
				new UITemplate(ui_label, 	UELabel.class, "Label"),
				new UITemplate(ui_canvas, 	UECanvas.class, "Canvas"),
				new UITemplate(ui_textinput, UETextInput.class, "TextInput"),
				new UITemplate(ui_textbox, 	UETextBox.class, "TextBox"),
				new UITemplate(ui_textbox, 	UETextBoxHtml.class, "TextBoxHtml"),
		};
		return templates;
	}

	public ImageUILayout createLayout(
			String image_file, 
			ImageStyle style,
			int clip_border)
	{
		try {
			BufferedImage buf = getImage(image_file);
			if (buf != null) {
				return new ImageUILayout(buf, 
						new File(edit.resdir, image_file), 
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
			File file = new File(edit.resdir, layout.image_file.getName());
			if (!file.exists()) {
				Tools.writeImage(file.getPath(), "png", layout.getSrcImage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
