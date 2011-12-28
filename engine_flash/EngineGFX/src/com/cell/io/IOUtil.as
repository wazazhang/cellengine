package com.cell.io
{
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.URLLoader;

	public class IOUtil
	{
		public static function loadURL(path:String, complete:Function=null, error:Function=null) : URLLoader
		{
			var loader : URLLoader = UrlManager.createURLLoader();
			if (complete != null) {
				loader.addEventListener(Event.COMPLETE, complete);
			}
			if (error != null) {
				loader.addEventListener(IOErrorEvent.IO_ERROR, error);
				loader.addEventListener(IOErrorEvent.NETWORK_ERROR, error);
				loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, error);
			}
			loader.load(UrlManager.getUrl(path));
			return loader;
		}
	}
}