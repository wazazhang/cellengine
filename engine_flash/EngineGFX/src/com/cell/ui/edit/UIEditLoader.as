package com.cell.ui.edit
{
	import com.cell.io.URLLoaderQueue;
	import com.cell.io.UrlManager;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.comp.SavedComponent;
	import com.cell.util.XMLUtil;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;

	[Event(name=UIEditEvent.LOADED, type="com.cell.ui.edit.UIEditEvent")]  
	[Event(name=UIEditEvent.ERROR,  type="com.cell.ui.edit.UIEditEvent")]  
	public class UIEditLoader extends EventDispatcher
	{
		private var edit : UIEdit;
		private var file : String;
		
		private var image_queue 	: URLLoaderQueue = new URLLoaderQueue();
		private var xml_loader 	: URLLoader;
		
		private var gui_xml		: XMLDocument;
		
		private var content		: Sprite;
		
		public function UIEditLoader(edit:UIEdit, fileName:String)
		{
			this.edit = edit;
			this.file = fileName;
			
			this.xml_loader = UrlManager.createURLLoader(onXMLComplete, onXMLError);
			this.xml_loader.load(UrlManager.getUrl(edit.getRoot()+fileName));
		}
		
		public function load() : void
		{
			xml_loader.load(UrlManager.getUrl(edit.getRoot()+"/" + file));
		}
		
		private function onXMLComplete(e:Event) : void
		{
			gui_xml = new XMLDocument();
			gui_xml.ignoreWhite = true;
			gui_xml.parseXML(this.xml_loader.data);
			content = readInternal(gui_xml.firstChild);
			
			dispatchEvent(new UIEditEvent(UIEditEvent.LOADED, this, edit, e));
		}
		
		private function onXMLError(e:Event) : void
		{
			dispatchEvent(new UIEditEvent(UIEditEvent.ERROR, this, edit, e));
		}
		
		internal function readInternal(e:XMLNode) : Sprite
		{
			trace("get node : " + e.nodeName);
			var comp : Sprite = edit.createComponent(e);
			if (comp is UIComponent) {
				edit.readFields(e, comp as UIComponent);
			}
			if (comp is SavedComponent) {
				var ui : SavedComponent = comp as SavedComponent;
				ui.onRead(edit, e);
				var childs : XMLNode = XMLUtil.findChild(e, "childs");
				if (childs != null) {
					for each (var child:XMLNode in childs.childNodes) {
						var cui : Sprite = readInternal(child);
						if (cui != null) {
							comp.addChild(cui);
						}
					}
				}
				
			}
			return comp;
		}
		
		public function getContent() : Sprite
		{
			return content;
		}
		
	}
}