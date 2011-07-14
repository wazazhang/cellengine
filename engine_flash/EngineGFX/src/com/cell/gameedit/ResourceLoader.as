package com.cell.gameedit
{
	import com.cell.gameedit.output.XmlOutput;
	import com.cell.util.StringUtil;
	
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	[Event(name=ClientEvent.CONNECTED, type="com.cell.gameedit.ResourceEvent")]  
	public class ResourceLoader extends URLLoader
	{
		private var url : URLRequest;
		private var output : Output;
		
		public function ResourceLoader(){
			this.addEventListener(Event.COMPLETE, complete);
		}
		
		override public function load(url:URLRequest) : void
		{
			this.url = url;
			super.load(url);
		}
		
		private function complete(e:Event) : void
		{
			if (StringUtil.endsOf(url.url.toLowerCase(), ".xml") {
				output = new XmlOutput();
			}
			
		}
		
		
	}
}