package com.cell.gameedit.output
{
	import com.cell.gameedit.Output;
	import com.cell.util.Map;
	import com.cell.util.NumberReference;
	
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;

	public class XmlOutput extends Loader implements Output
	{
		private var path 		: String;
		private var path_root 	: String;
		private var file_name 	: String;
		
		public function XmlOutput(url:String, xml:XML)
		{
			this.path 		= url.replace('\\', '/');
			this.path_root	= path.substring(0, path.lastIndexOf("/")+1);
			this.file_name	= path.substring(path_root.length);
			
			var urlReq:URLRequest = new URLRequest(path);
			this.contentLoaderInfo.addEventListener(Event.COMPLETE, loaded_xml);  
			this.load(urlReq);
		}
		
		
		private function loaded_xml(e:Event) : void
		{
			
		}
		
		/***
		 * 是否单独输出每张图
		 * @return
		 */
		public function isTile() : Boolean 
		{
			return null;
		}
		
		/**
		 * 是否输出整图
		 * @return
		 */
		public function isGroup() : Boolean
		{
			return null;
			
		}
		
		/**
		 * 获得导出图片文件类型
		 * @return
		 */
		public function getImageExtentions() : String 
		{
			
			return null;
		}
		
		/**
		 * 读取导出资源，比如图片什么的
		 * @param name 文件名
		 * @param percent 进度
		 * @return
		 */
		public function loadRes( name:String,  percent:NumberReference) : ByteArray
		{
			return null;
		}
		
		
		public function		getImgTable() 	: Map
		{
			return null;
		}
		
		public function		getSprTable() 	: Map
		{
			return null;
		}
		
		public function		getMapTable() 	: Map
		{
			return null;
		}
		
		public function		getWorldTable() : Map
		{
			return null;
		}
		
		/**
		 * call by {@link SetResource}.dispose()
		 */
		public function 	dispose() : void
		{
			
		}
	}
}