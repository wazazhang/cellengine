package com.g2d.studio.ui.edit;

import com.g2d.Tools;
import com.g2d.display.ui.UIComponent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.studio.ui.edit.gui.UEButton;
import com.g2d.studio.ui.edit.gui.UECanvas;
import com.g2d.studio.ui.edit.gui.UEImageBox;
import com.g2d.studio.ui.edit.gui.UELabel;

public class UILayoutManager 
{
	private UILayout ui_default = new UILayout();
	
	private UILayout ui_btn_d = new UILayout();
	private UILayout ui_btn_u = new UILayout();
	
	private UILayout ui_label = new UILayout();
	private UILayout ui_canvas = new UILayout();
	
	public UILayoutManager() {
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
		
	}
	
	public void setLayout(UIComponent ui)
	{
		if (ui instanceof UEButton) {
			((UEButton)ui).setCustomLayout(ui_btn_u, ui_btn_d);
		}
		else if (ui instanceof UECanvas) {
			ui.setCustomLayout(ui_canvas);
		}
		else if (ui instanceof UELabel) {
			ui.setCustomLayout(ui_label);
		}
		else {
			ui.setCustomLayout(ui_default);
		}
		
	}
	
	public UITemplate[] getTemplates() {
		UITemplate[] templates = new UITemplate[]{
				new UITemplate(ui_btn_u, UEButton.class, "Button"),
				new UITemplate(ui_default, UEImageBox.class, "ImageBox"),
				new UITemplate(ui_label, UELabel.class, "Label"),
				new UITemplate(ui_canvas, UECanvas.class, "Canvas"),
		};
		return templates;
	}
	
}
