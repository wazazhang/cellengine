package com.cell.io
{
	import flash.display.Loader;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;

	public class UrlManager
	{
		private static var url_wrapper : UrlWrapper = null;
		
		public static function getUrl(url:String) : URLRequest
		{
			if (url_wrapper == null) {
				return new URLRequest(url);
			} else {
				return new URLRequest(url_wrapper.getWrapperUrl(url));
			}
		}
		
		public static function setUrlWrapper(w : UrlWrapper) : void
		{
			url_wrapper = w;
		}
		
		public static function createLoader() : Loader
		{
			var loader : Loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, 						s_complete);
			loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, 				s_error);
			loader.contentLoaderInfo.addEventListener(IOErrorEvent.NETWORK_ERROR, 			s_error);  
			loader.contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, 	s_error);  
			return loader;
		}
		
		public static function createURLLoader() : URLLoader
		{
			var loader : URLLoader = new URLLoader();
			loader.addEventListener(Event.COMPLETE, 					s_complete);
			loader.addEventListener(IOErrorEvent.IO_ERROR, 				s_error);
			loader.addEventListener(IOErrorEvent.NETWORK_ERROR, 		s_error);  
			loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, 	s_error);  
			return loader;
		}
		
		private static function s_complete(e:Event) : void
		{
			
		}
		
		private static function s_error(e:Event) : void
		{
			trace("Error : " + e);
		}
		
	}
}