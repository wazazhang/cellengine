package com.cell.ui.edit
{
	import com.cell.io.UrlManager;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.comp.UEButton;
	import com.cell.ui.edit.comp.UECanvas;
	import com.cell.ui.edit.comp.UEFileNode;
	import com.cell.ui.edit.comp.UEImageBox;
	import com.cell.ui.edit.comp.UELabel;
	import com.cell.ui.edit.comp.UERoot;
	import com.cell.ui.edit.comp.UETextBox;
	import com.cell.ui.edit.comp.UETextInput;
	import com.cell.ui.edit.comp.UEToggleButton;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.cell.util.XMLUtil;
	
	import flash.display.Bitmap;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.net.URLLoader;
	import flash.xml.XMLNode;

	public class UIEdit
	{
		
		public const  UERoot_ClassName			:String	= "com.g2d.studio.ui.edit.gui.UERoot";
		public const  UEButton_ClassName		:String	= "com.g2d.studio.ui.edit.gui.UEButton";
		public const  UEToggleButton_ClassName	:String	= "com.g2d.studio.ui.edit.gui.UEToggleButton";
		public const  UEImageBox_ClassName		:String	= "com.g2d.studio.ui.edit.gui.UEImageBox";
		public const  UELabel_ClassName		:String	= "com.g2d.studio.ui.edit.gui.UELabel";
		public const  UECanvas_ClassName		:String	= "com.g2d.studio.ui.edit.gui.UECanvas";
		public const  UETextInput_ClassName	:String	= "com.g2d.studio.ui.edit.gui.UETextInput";
		public const  UETextBox_ClassName		:String	= "com.g2d.studio.ui.edit.gui.UETextBox";
		public const  UEFileNode_ClassName		:String	= "com.g2d.studio.ui.edit.gui.UEFileNode";

		private var res_root 	: String;
		private var img_map 	: Map = new Map();
		
		
		public function UIEdit(res_root:String)
		{
			this.res_root = res_root;
		
		}
		
		public function getRoot() : String
		{
			return res_root;
		}
		
		public function createFromFile(fileName:String) : UIEditLoader
		{
			return new UIEditLoader(this, fileName);
		}
		
		public function addImage(name:String) : Loader
		{
			var loader : Loader = img_map[name];
			if (loader == null) {
				var url : String = res_root+"/res/"+name;
				loader = UrlManager.createLoader();
				loader.load(UrlManager.getUrl(url));
				img_map[name] = loader;
			}
			return loader;
		}
		
		public function getUILayerStyle(name:String) : int
		{
			switch (name) 
			{
			case "NULL":
				return UIRect.IMAGE_STYLE_NULL;
				
			case "COLOR":
				return UIRect.IMAGE_STYLE_COLOR;
				
			case "IMAGE_STYLE_ALL_9":
				return UIRect.IMAGE_STYLE_ALL_9;
				
			case "IMAGE_STYLE_ALL_8":
				return UIRect.IMAGE_STYLE_ALL_8;
				
			case "IMAGE_STYLE_H_012":
				return UIRect.IMAGE_STYLE_H_012;
				
			case "IMAGE_STYLE_V_036":
				return UIRect.IMAGE_STYLE_V_036;
				
			case "IMAGE_STYLE_HLM":
				return UIRect.IMAGE_STYLE_HLM;
				
			case "IMAGE_STYLE_VTM":
				return UIRect.IMAGE_STYLE_VTM;
				
			case "IMAGE_STYLE_BACK_4":
				return UIRect.IMAGE_STYLE_BACK_4;
				
			case "IMAGE_STYLE_BACK_4_CENTER":
				return UIRect.IMAGE_STYLE_BACK_4_CENTER;
			}
			
			return UIRect.IMAGE_STYLE_NULL;
		}
		
		
		public function getLayout(e:XMLNode, width:int, height:int) : UIRect
		{
			var style   : int    = getUILayerStyle(e.attributes["style"]);
			var imgname : String = e.attributes["img"];
			var layout  : UIRect = null; 
			if (imgname != null && imgname.length>0) {
				var clipSize : int = e.attributes["clip"];
				layout = new UIEditRect(style, addImage(imgname), clipSize, width, height);
			} else {
				layout = new UIRect(style);
			}
			layout.setColor(parseInt(e.attributes["bgc"], 16));
			//layout.back_color.setARGB(rect->getAttributeAsHexU32("bgc"));
			//layout.border_color.setARGB(rect->getAttributeAsHexU32("bdc"));
			//out_layout = layout;
			return layout;
		}
		
		///////////////////////////////////////////////////////////////////////
		
		
		public function createComponent(e:XMLNode) : Sprite
		{
			var name : String = e.nodeName;

			if (name == UERoot_ClassName) {
				return new UERoot();
			}
			if (name == UEButton_ClassName) {
				return new UEButton();
			}
			if (name == UEToggleButton_ClassName) {
				return new UEToggleButton();
			}
			if (name == UEImageBox_ClassName) {
				return new UEImageBox();
			}
			if (name == UELabel_ClassName) {
				return new UELabel();
			}
			if (name == UECanvas_ClassName) {
				return new UECanvas();
			}
			if (name == UETextInput_ClassName) {
				return new UETextInput();
			}
			if (name == UETextBox_ClassName) {
				return new UETextBox();
			}
			if (name == UEFileNode_ClassName) {
				return new UEFileNode();
			}
			return new UECanvas();
		}
		
		
		
		public function readFields(e:XMLNode, ui:UIComponent) : void
		{
			var width : int = e.attributes["width"];
			var height : int = e.attributes["height"];
			
			ui.setSize(width, height);
			
			ui.visible = StringUtil.parserBoolean(e.attributes["visible"]);
		
			ui.editName = e.attributes["name"];
			
			//ui.clip_local_bounds 	= Boolean.parseBoolean(e.getAttribute("clipbounds"));
			
			ui.setLocation(
				e.attributes["x"],
				e.attributes["y"]);
			
			ui.user_data = 
				e.attributes["userData"];
			ui.user_tag  = 
				e.attributes["userTag"];
			
			ui.mouseEnabled  = StringUtil.parserBoolean(e.attributes["enable_focus"]);

			ui.mouseChildren = ui.mouseEnabled;
			
			
//			var layout : UIRect = UILayoutManager.getInstance().createUI("com.fc.castle.ui.BaseForm", ui);
//			if (layout != null) {
//				ui.setBG(layout);
//			}
			
			var e_layout : XMLNode = XMLUtil.findChild(e, "layout");
			if (e_layout != null) {
				var layout : UIRect = getLayout(e_layout, width, height);
				if (layout != null) {
					ui.setBG(layout);
				}
			}
			
		}
		
	}
}