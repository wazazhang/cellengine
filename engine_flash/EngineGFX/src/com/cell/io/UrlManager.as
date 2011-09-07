package com.cell.io
{
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
	}
}