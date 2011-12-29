package com.cell.ui.layout
{
	import com.cell.gfx.CellScreenManager;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.UIComponent;
	import com.cell.util.Map;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.text.TextFormat;

	public class UILayoutManager
	{
		private static var instance : UILayoutManager;
		
		public static function getInstance() : UILayoutManager
		{
			return instance;
		}
		
//		---------------------------------------------------------------------------------------------------------
		
		protected var properties	: Map = new Map();
		
		private var _width 			: int = 320;
		private var _height 		: int = 480;
		private var _root 			: CellScreenManager;

		public function UILayoutManager(
			s:CellScreenManager, 
			width:int,
			height:int)
		{
			this._root		= s;
			this._width		= width;
			this._height 	= height;
			UILayoutManager.instance = this;
			
			properties.put("com.cell.ui.component.Alert.borderSize", 10);
			properties.put("com.cell.ui.component.listview.ListView.headerHeight", 25);
			properties.put("com.cell.ui.component.listview.ListView.border", 8);
			
		}
		
		public function getRoot() : CellScreenManager {
			return _root;
		}
		
		public function get ScreenWidth() : int {
			return _width;
		}
		
		public function get ScreenHeight() : int {
			return _height;
		}
		
		//////////////////////////////////////////////////////////////////////////////////
		// 
		//////////////////////////////////////////////////////////////////////////////////
		
		public function getSystemPropertyEnums() : Map
		{
			var ret : Map = new Map();
			ret.put("com.cell.ui.component.Alert", 				"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.Alert.text", 		"flash.text.TextFormat");
			ret.put("com.cell.ui.component.Alert.ok", 			"flash.display.DisplayObject");
			ret.put("com.cell.ui.component.Alert.cancel", 		"flash.display.DisplayObject");
			ret.put("com.cell.ui.component.Alert.borderSize", 	"int");
			
			ret.put("com.cell.ui.component.Lable", 				"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.Lable.text", 		"flash.text.TextFormat");
			
			ret.put("com.cell.ui.component.Pan", 				"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.Panel", 				"com.cell.ui.layout.UIRect");
			
			ret.put("com.cell.ui.component.ScrollBar.h", 		"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.ScrollBar.h.strip", 	"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.ScrollBar.v", 		"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.ScrollBar.v.strip", 	"com.cell.ui.layout.UIRect");
			
			ret.put("com.cell.ui.component.TextBox", 			"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.TextBox.text", 		"flash.text.TextFormat");
			
			ret.put("com.cell.ui.component.TextButton.up", 		"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.TextButton.down", 	"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.TextButton.text", 	"flash.text.TextFormat");
			
			ret.put("com.cell.ui.component.TextInput", 			"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.TextInput.text", 	"flash.text.TextFormat");
			
			ret.put("com.cell.ui.component.listview.ListView", 	"com.cell.ui.layout.UIRect");
			ret.put("com.cell.ui.component.listview.ListView.headerHeight", "int");
			ret.put("com.cell.ui.component.listview.ListView.border", "int");
			
			return ret;
		}
		
//		---------------------------------------------------------------------------------------------------------
		
		public function getProperty(key:String, owner:*) : *
		{
			return properties.get(key);
		}
		
//		---------------------------------------------------------------------------------------------------------
		
		
		public function createUI(key:String, owner:*) : UIRect
		{
			return new UIRect();
		}
		
		public function createTextFormat(key:String, owner:*) : TextFormat
		{
			return new TextFormat("Verdana", 12, 0);
		}

		public function createButton(key:String, owner:*) : DisplayObject
		{
			return new UIRect().initBuffer(32, 32);
		}
		

	
	
	
	}
}