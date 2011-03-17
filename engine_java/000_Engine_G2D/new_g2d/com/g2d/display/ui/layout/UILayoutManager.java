package com.g2d.display.ui.layout;

import java.util.Hashtable;

import com.g2d.Color;
import com.g2d.Image;
import com.g2d.Tools;
import com.g2d.display.ui.BaseButton;
import com.g2d.display.ui.Button;
import com.g2d.display.ui.CheckBox;
import com.g2d.display.ui.ComboBox;
import com.g2d.display.ui.DropDownList;
import com.g2d.display.ui.Form;
import com.g2d.display.ui.ImageBox;
import com.g2d.display.ui.ImageButton;
import com.g2d.display.ui.Label;
import com.g2d.display.ui.MessageBox;
import com.g2d.display.ui.PageSelect;
import com.g2d.display.ui.Pan;
import com.g2d.display.ui.Panel;
import com.g2d.display.ui.RichButton;
import com.g2d.display.ui.ScrollBar;
import com.g2d.display.ui.TextBox;
import com.g2d.display.ui.TextBoxSingle;
import com.g2d.display.ui.UIComponent;

public abstract class UILayoutManager 
{

	Hashtable<Class<? extends UIComponent>, UILayout> tables = new Hashtable<Class<? extends UIComponent>, UILayout>();
	
	public String save_path;
	
	public UILayoutManager(String save_path) 
	{
		this.save_path = save_path;
	}
	
	abstract public void setLayout(UIComponent component);
	
	
//	----------------------------------------------------------------------------------------------------------------------------------
//	 impl

	static UILayoutManager instance = new SimpleLayoutManager();
	
	public static UILayoutManager getInstance() {
		return instance;
	}
	
	public static void setInstance(UILayoutManager obj) {
		if (obj!=null) {
			instance = obj;
		}
	}
	
	
	public static class SimpleLayoutManager extends UILayoutManager
	{
		public  UILayout BlankLayout			= UILayout.createBlankRect();
		public  UILayout NullLayout			= UILayout.createRect(new Color(0x40,0x40,0x40,0xff));
		
		public  UILayout FormLayout			= new UILayout();
		public  UILayout FormCloseLayout		= new UILayout();
		
		public  UILayout ButtonLayout			= new UILayout();
		public  UILayout ButtonPressLayout	= new UILayout();
	
		public  UILayout LabelLayout			= new UILayout();
		
		public  UILayout TextBoxLayout		= new UILayout();
		public  UILayout TextBoxSingleLayout	= new UILayout();
		
		public  UILayout PanelLayout			= new UILayout();

		public  UILayout PageU				= new UILayout();
		public  UILayout PageD				= new UILayout();
		
		public  UILayout ScroolBarVHeadLayout	= new UILayout();
		public  UILayout ScroolBarVTailLayout	= new UILayout();
		public  UILayout ScroolBarVHeadDLayout= new UILayout();
		public  UILayout ScroolBarVTailDLayout= new UILayout();
		public  UILayout ScroolBarVBackLayout	= new UILayout();
		public  UILayout ScroolBarVStripLayout= new UILayout();
		
		public  UILayout ScroolBarHHeadLayout	= new UILayout();
		public  UILayout ScroolBarHTailLayout	= new UILayout();
		public  UILayout ScroolBarHHeadDLayout= new UILayout();
		public  UILayout ScroolBarHTailDLayout= new UILayout();
		public  UILayout ScroolBarHBackLayout	= new UILayout();
		public  UILayout ScroolBarHStripLayout= new UILayout();
		
		
		
		protected Image CheckBox_CheckImage;
		protected Image CheckBox_UncheckImage;
		
		public  UILayout ComboBoxDropButtonLayout	= new UILayout();

		protected Image MessageBox_Msg;
		protected Image MessageBox_Error;
		protected Image MessageBox_Warning;
		
		protected Image ImageBoxLoading;
		
		protected Image ImageButtonCatchedMouseMask;
		
		public SimpleLayoutManager(){
			super(".");
		}
		
		
		protected void initLayout()
		{
			// form
			{
				FormLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/form.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						64);
				FormCloseLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/btn_close_s.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						0);
			}
			
			
			// lable 
			{
				LabelLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/lable.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						4);
				LabelLayout.BorderSize = 2;
			}
			
			// button 
			{
				ButtonLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/btn1-u.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						7);
				ButtonPressLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/btn1-d.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						7);
				ImageButtonCatchedMouseMask = Tools.readImage("/com/g2d/display/ui/res/light64.png");
			}
			
			// panel
			{
				PanelLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/panel.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						16);
				PanelLayout.BorderSize = 4;
			}
			
			// text box
			{
				TextBoxLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/textbox.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						4);
				TextBoxLayout.BorderSize = 4;
			}
			
			// text box single
			{
				TextBoxSingleLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/textboxsingle.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						4);
				TextBoxSingleLayout.BorderSize = 4;
			}
			
			// page select
			{
				PageU.setImages(
						Tools.readImage("/com/g2d/display/ui/res/page-u.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						8);
				PageU.BorderSize = 4;
				PageD.setImages(
						Tools.readImage("/com/g2d/display/ui/res/page-d.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_ALL_9, 
						8);
				PageD.BorderSize = 4;
			}
			
			// combobox drop
			{
				ComboBoxDropButtonLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/combobox-dropbtn.png"),
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ComboBoxDropButtonLayout.BorderSize = 4;
			}
			
			
			// scroll
			{

				ScroolBarVHeadLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollv-head.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarVTailLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollv-tail.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarVHeadDLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollv-head.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarVTailDLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollv-tail.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarVBackLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollv-back.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_V_036, 
						15);
				ScroolBarVStripLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollv-strip.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_V_036, 
						3);
				
				
				ScroolBarHHeadLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollh-head.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarHTailLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollh-tail.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarHHeadDLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollh-head.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarHTailDLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollh-tail.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_BACK_4, 
						1);
				ScroolBarHBackLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollh-back.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_H_012, 
						15);
				ScroolBarHStripLayout.setImages(
						Tools.readImage("/com/g2d/display/ui/res/scrollh-strip.png"), 
						UILayout.ImageStyle.IMAGE_STYLE_H_012, 
						3);
			}

			// checkbox
			{
				CheckBox_CheckImage 	= Tools.readImage("/com/g2d/display/ui/res/checkbox-checkimage.png");
				CheckBox_UncheckImage	= Tools.readImage("/com/g2d/display/ui/res/checkbox-uncheckimage.png");
			}
			
			// message box
			{
				MessageBox_Msg			= Tools.readImage("/com/g2d/display/ui/res/messagebox-msg.png");
				MessageBox_Error		= Tools.readImage("/com/g2d/display/ui/res/messagebox-error.png");
				MessageBox_Warning		= Tools.readImage("/com/g2d/display/ui/res/messagebox-warning.png");
			}
			
			
			
			// image button loading
			{
				ImageBoxLoading			= Tools.readImage("/com/g2d/display/ui/res/loading.png");
			}
			
			// tip
			{
//				Tip.DefaultLayout		= new UILayout(TextBoxLayout);
//				Tip.DefaultTipSize		= 260;
			}
		}
		
		
		public void setLayout(UIComponent component) 
		{
			if(false) {}
			
			// message box
			else if (component instanceof MessageBox) 
			{
				component.setLayout(FormLayout); 
				
				MessageBox box = (MessageBox)component;
				switch(box.type){
				case MessageBox.TYPE_ERROR:
					box.icon.setImage(MessageBox_Error);
					break;
				case MessageBox.TYPE_WARNING:
					box.icon.setImage(MessageBox_Warning);
					break;
				case MessageBox.TYPE_MESSAGE:
				default:
					box.icon.setImage(MessageBox_Msg);
				}
			}
			
			// form
			else if (component instanceof Form) {
				component.setLayout(FormLayout); 
			}
			else if (component instanceof Form.ButtonClose) {
				component.setLayout(FormCloseLayout); 
			}
			
			//
			else if (component instanceof ImageBox) {
				component.setLayout(BlankLayout); 
			}
			
			// DropDownList
			else if (component instanceof DropDownList) {
				component.setLayout(PanelLayout); 
			}
			else if (component instanceof DropDownList.DropDownListContainer) {
				component.setLayout(BlankLayout); 
			}
			
			// combobox
			else if (component instanceof ComboBox) {
				component.setLayout(TextBoxSingleLayout); 
			}
			else if (component instanceof ComboBox.Item) {
				component.setLayout(LabelLayout); 
			}
			else if (component instanceof ComboBox.DropButton) {
				component.setLayout(ComboBoxDropButtonLayout); 
			}
			
			// ScrollBar
			else if (component instanceof ScrollBar) {
				((ScrollBar)component).size = 13;
			}
			// button
			else if (component instanceof BaseButton)
			{
				((BaseButton)component).mouse_catched_mask = ImageButtonCatchedMouseMask;
				
				if (component instanceof ImageButton) {
					((ImageButton)component).setLoadingImage(ImageBoxLoading);
				}
				else if (component instanceof RichButton) {
					((RichButton)component).setLayout(ButtonLayout, ButtonPressLayout); 
				}
				else if (component instanceof Button) {
					((Button)component).setLayout(ButtonLayout, ButtonPressLayout); 
				}
				else if (component instanceof ScrollBar.Head) {
					if (((ScrollBar.Head)component).isVscroll()) {
						((ScrollBar.Head) component).setLayout(ScroolBarVHeadLayout, ScroolBarVHeadDLayout);
					}else{
						((ScrollBar.Head) component).setLayout(ScroolBarHHeadLayout, ScroolBarHHeadDLayout);
					}
				}
				else if (component instanceof ScrollBar.Tail) {
					if (((ScrollBar.Tail) component).isVscroll()) {
						((ScrollBar.Tail) component).setLayout(ScroolBarVTailLayout, ScroolBarVTailDLayout);
					} else {
						((ScrollBar.Tail) component).setLayout(ScroolBarHTailLayout, ScroolBarHTailDLayout);
					}
				}
				else if (component instanceof ScrollBar.Back) {
					if (((ScrollBar.Back) component).isVscroll()) {
						((ScrollBar.Back) component).setLayout(ScroolBarVBackLayout);
					} else {
						((ScrollBar.Back) component).setLayout(ScroolBarHBackLayout);
					}
					((BaseButton)component).mouse_catched_mask = null;
				} 
				else if (component instanceof ScrollBar.Strip) {
					if (((ScrollBar.Strip) component).isVscroll()) {
						((ScrollBar.Strip) component).setLayout(ScroolBarVStripLayout);
					} else {
						((ScrollBar.Strip) component).setLayout(ScroolBarHStripLayout);
					}
					((BaseButton)component).mouse_catched_mask = null;
				}
				
			}
			// textbox
			else if (component instanceof TextBox) {
				component.setLayout(TextBoxLayout);
			}
			// textbox single
			else if (component instanceof TextBoxSingle) {
				component.setLayout(TextBoxSingleLayout);
			}
			
			// PageSelectPanel
			else if (component instanceof PageSelect<?>) {
				component.setLayout(BlankLayout);
			}
			else if (component instanceof PageSelect.Page) {
				((PageSelect.Page)component).setLayout(PageU, PageD);
			}
			
			// Panel
			else if (component instanceof Panel.PanelContainer) {
				component.setLayout(BlankLayout);
			}
			else if (component instanceof Panel) {
				component.setLayout(PanelLayout);
			}
			
			// checkbox
			else if (component instanceof CheckBox) {
				component.setLayout(LabelLayout); 
				((CheckBox)component).check_image = CheckBox_CheckImage;
				((CheckBox)component).uncheck_image = CheckBox_UncheckImage;
			}
			
			// label
			else if (component instanceof Label) {
				component.setLayout(LabelLayout); 
			}
			
			// pan
			else if (component instanceof Pan) {
				component.setLayout(BlankLayout);
			}
			
			// other
			else {
				component.setLayout(NullLayout); 
			}
			
		}

	}
	
}
