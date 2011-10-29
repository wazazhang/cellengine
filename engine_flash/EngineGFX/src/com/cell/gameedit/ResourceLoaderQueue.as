package com.cell.gameedit
{
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.URLLoader;

	[Event(name=ResourceEvent.LOADED, type="com.cell.gameedit.ResourceEvent")]  
	[Event(name=ResourceEvent.ERROR,  type="com.cell.gameedit.ResourceEvent")]  
	public class ResourceLoaderQueue extends EventDispatcher
	{
		private var total : Number;
		private var curload : ResourceLoader;
		private var loaded : Vector.<ResourceLoader>;
		private var waited : Vector.<ResourceLoader>;
		
		public function ResourceLoaderQueue(list:Vector.<ResourceLoader> = null)
		{
			this.loaded = new Vector.<ResourceLoader>();
			if (list != null) {
				this.total  = list.length;
				this.waited = list.slice();	
			} else {
				this.total  = 0;
				this.waited = new Vector.<ResourceLoader>();
			}
		}
		
		public function push(res:ResourceLoader) : void
		{
			waited.push(res);
			this.total = waited.length;
		}
		
		public function load() : void
		{
			loadNext();
		}
		
		private function loadNext() : void
		{
			if (waited.length > 0) {
				curload = waited[0];
				waited.splice(0, 1);
				curload.addEventListener(ResourceEvent.LOADED, onComplete);
				curload.addEventListener(ResourceEvent.ERROR,  onError);
				curload.load();
			}
		}
		
		
		private function onComplete(e:ResourceEvent) : void
		{
			curload.removeEventListener(ResourceEvent.LOADED, onComplete);
			curload.removeEventListener(ResourceEvent.ERROR,  onError);
			loaded.push(curload);
			curload = null;
			
			if (waited.length == 0) 
			{				
				dispatchEvent(e);
			}
			else 
			{
				loadNext();
			}
		}
		
		private function onError(e:ResourceEvent) : void
		{
			dispatchEvent(e);
		}
		
		public function get percent() : Number
		{
			if (total > 0) {
				if (curload != null) {
					return (loaded.length + curload.getOutput().getPercent()) / total;
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