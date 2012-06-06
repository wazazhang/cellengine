package com.cell.io
{
	import com.cell.util.Map;
	
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;


	[Event(name=Event.COMPLETE, 			type="flash.events.Event")]  
	[Event(name=IOErrorEvent.IO_ERROR,  	type="flash.events.IOErrorEvent")]  
	[Event(name=IOErrorEvent.NETWORK_ERROR, type="flash.events.IOErrorEvent")]  
	public class LoaderQueue extends EventDispatcher
	{
		private var total 		: Number;
		private var curload 	: URLLoader;
		private var curloadurl	: URLRequest;
		private var loaded 	: Map;
		private var waited 	: Vector.<URLRequest>;
		
		public function LoaderQueue(list:Vector.<URLRequest> = null)
		{
			this.loaded = new Map();
			if (list != null) {
				this.total  = list.length;
				this.waited = list.slice();	
			} else {
				this.total  = 0;
				this.waited = new Vector.<URLRequest>();
			}
		}
		
		public function push(res:URLRequest) : void
		{
			waited.push(res);
			this.total = waited.length;
		}
		
		public function load() : void
		{
			loadNext();
		}
		
		
		/**
		 * key is URLRequest 
		 * value is URLLoader
		 */
		public function getLoaded() : Map
		{
			return loaded;
		}
		
		private function loadNext() : void
		{
			if (waited.length > 0) {
				curloadurl 	= waited[0];
				curload 	= new URLLoader(curloadurl);
				waited.splice(0, 1);
				curload.addEventListener(Event.COMPLETE, 				onComplete);
				curload.addEventListener(IOErrorEvent.IO_ERROR, 		onError);
				curload.addEventListener(IOErrorEvent.NETWORK_ERROR, 	onError);  
				curload.load(curloadurl);
			}
		}
		
		
		private function onComplete(e:Event) : void
		{
			loaded.put(curloadurl, curload);
			curload = null;
			curloadurl = null;
			if (waited.length == 0) 
			{				
				dispatchEvent(e);
			}
			else 
			{
				loadNext();
			}
		}
		
		private function onError(e:IOErrorEvent) : void
		{
			dispatchEvent(e);
		}
		
		public function get percent() : Number
		{
			if (total > 0) {
				if (curload != null) {
					var curpct : Number = (curload.bytesLoaded / (Number)(curload.bytesTotal));
					return (loaded.length + curpct) / total;
				} else {
					return loaded.length / total;
				}
			} else {
				return 0;
			}
		}
		
		public function isDone() : Boolean
		{
			return loaded.length == total;
		}
	}
}