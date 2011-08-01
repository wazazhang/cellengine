package com.cell.bms
{
	import com.cell.bms.file.BMSFile;
	import com.cell.bms.file.IDefineResource;
	import com.cell.bms.file.IDefineResourceManager;
	import com.cell.util.Map;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	[Event(name=BMSEvent.LOADED, type="com.cell.bms.BMSEvent")]  
	public class BMSLoader extends EventDispatcher implements IDefineResourceManager
	{
		private var path 		: String;
		private var path_root 	: String;
		private var file_name 	: String;
		private var url 		: String;
			
		private var loader		: URLLoader;
		private var bms_file	: BMSFile;
		
		private var percent		: Number = 0;
		
		private var res_map		: Map;
		private var res_count	: int = 1;
		
		
		public function BMSLoader(url:String)
		{
			this.url 		= url;
			this.path 		= url.replace('\\', '/');
			this.path_root	= path.substring(0, path.lastIndexOf("/")+1);
			this.file_name	= path.substring(path_root.length);
			this.loader		= new URLLoader();
			this.loader.addEventListener(Event.COMPLETE, output_complete);
		}
			
		public function load() : void
		{
			trace("load bms : " + url);
			this.loader.load(new URLRequest(path));
		}
		
		public function getUrl() : String
		{
			return url;
		}
		
		private function output_complete(e:Event) : void 
		{
			trace("load bms file complete : " + path);
			this.bms_file = new BMSFile(this.loader.data, this);
			this.res_map = new Map();
			this.percent = 0;
		}
		
		private function stream_complete(e:Event) : void
		{
//			var event : BMSEvent = new BMSEvent(BMSEvent.LOADED);
//			event.loader = this;
//			dispatchEvent(event);
		}
		
		
		public function createDefineResource(command: String, value: String) : IDefineResource
		{
			this.res_count ++;
			
			return null;
		}
		
		public function getLoadPercent() : Number
		{
			return percent;
		}
		
		public function dispose() : void 
		{
			
		}
	}
}



















