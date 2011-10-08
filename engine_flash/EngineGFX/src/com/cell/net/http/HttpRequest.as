package com.cell.net.http
{
	import com.cell.io.UrlManager;
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;

	public class HttpRequest
	{	
		private var _request	: URLRequest;
		private var _loader		: URLLoader;
		private var _args		: URLVariables;
		
		public function HttpRequest(url : String)
		{
			_request 	= UrlManager.getUrl(url);
			_loader 	= new URLLoader();
			_args		= new URLVariables();
			
			_loader.addEventListener(Event.COMPLETE, handleLoadSuccessful);
			_loader.addEventListener(IOErrorEvent.IO_ERROR, handleLoadError);
		}
		
		public function load() : void
		{
//			scriptVars.var1 = "one";
//			scriptVars.var2 = "two";
			
			_request.method = URLRequestMethod.POST;
			_request.data = _args;
			_loader.load(_request);
		}
		
		protected function handleLoadSuccessful(evt:Event):void {
			trace("Message sent.");
			trace("DataReceived:" + evt.target.data);
		}
		
		protected function handleLoadError(evt:IOErrorEvent):void {
			trace("Message failed.");
		}
		
		
		public function get request()	: URLRequest{
			return _request;
		}
		
		public function get loader()	: URLLoader{
			return _loader;
		}
		
		public function get args()		: URLVariables{
			return _args;
		}

		

	}
}