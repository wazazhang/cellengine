package com.cell.net.http
{
	import com.cell.io.UrlManager;
	
	import flash.events.Event;
	import flash.events.HTTPStatusEvent;
	import flash.events.IOErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestHeader;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;

	public class HttpRequest
	{	
		private var _request	: URLRequest;
		private var _loader		: URLLoader;
		private var _args		: URLVariables;
		
		public function HttpRequest(url : URLRequest)
		{
			_request 	= url;
			_loader 	= new URLLoader();
			_args		= new URLVariables();
			
			_loader.addEventListener(Event.COMPLETE, handleLoadSuccessful);
			_loader.addEventListener(IOErrorEvent.IO_ERROR, handleLoadError);       
			_loader.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);

		}
		
		public function post() : void
		{
//			scriptVars.var1 = "one";
//			scriptVars.var2 = "two";
			_request.method = URLRequestMethod.POST;
			_request.data = _args;
			_loader.load(_request);
		}
		
		public function get() : void
		{
			_request.method = URLRequestMethod.GET;
			_request.data = _args;
			_loader.load(_request);
		}
		
		
		public function putRequestHeader(name:String, value:String) : void
		{
			request.requestHeaders.push(new URLRequestHeader(name, value));
		}
		
		
		protected function handleLoadSuccessful(evt:Event):void
		{
//			trace("DataReceived : " + evt.target.data);
		}
		
		protected function handleLoadError(evt:IOErrorEvent):void 
		{
			trace("HttpRequestError : " + evt);
		}
		
		protected function httpStatusHandler(evt:HTTPStatusEvent) : void 
		{
//			trace("HttpStatus : " + evt);
		}
		
		
		public function get request()	: URLRequest{
			return _request;
		}
		
		public function get loader()	: URLLoader{
			return _loader;
		}
		
		public function get param()		: URLVariables{
			return _args;
		}

		

	}
}