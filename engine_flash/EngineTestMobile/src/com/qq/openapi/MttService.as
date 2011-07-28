package com.qq.openapi
{
	import com.qq.protocol.ProtocolHelper;
	import com.qq.utils.HttpRequest;
	import com.qq.utils.MEvent;
	
	import flash.display.DisplayObject;
	import flash.display.MovieClip;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.utils.ByteArray;

	/**
	 * 	API全局数据定义、错误码定义、数据发送等包裹类。
	 * 
	 * 	@author Tencent
	 * 
	 */	
	public class MttService
	{
		/**
		 *	后台服务发现当前用户没有登录或者回话已经过期。	 
		 */		
		public static const ELOGOUT		:int = -60000;

		/**
		 *	解码登录SERVER返回数据包时出错，不能判定因何出现。
		 */		
		public static const EPDECODE		:int = -61000;

		/**
		 *	解码业务SERVER返回数据包时出错，不能判定因何出现。
		 */
		public static const ESDECODE		:int = -61100;

		/**
		 *	网络通信出现故障。
		 */
		public static const EIOERROR		:int = -62000;

		/**
		 *	网络通信调用超时。	 
		 */		
		public static const EIOTIMEOUT	:int = -62100; 

		/**
		 *	表明某件事物不存在。比如在获取游戏数据，后台服务发现指定的key不存在时就返回该错误码。 
		 */		
		public static const ENOENT		:int = -63000;

		/**
		 *	当前用户没有登录或者登录会话已过有效期。 
		 */		
		public static const ETLOGOUT		:String = "LOOUT";

		/**
		 * 跳转到登录页面。登陆后跳转回当前游戏的进入页面。	 
		 * @param root  
		 * 
		 */	
		public static function login(root:MovieClip = null):void
		{
			if (root != null) root.stop();

			navigateToURL(new URLRequest(ApiTool.urlLogin), "_self");
		}

		/**
		 *	接口调用的网络请求超时时间。 
		 * 	@param interval 请求超时时间，单位为秒
		 * 
		 */
		public static function set timeout(value:uint):void
		{
			mTimeout = value;
		}

		/**
		 *	接口调用的网络请求超时时间，时间单位为秒。 
		 * 	@return 请求超时时间，单位为秒
		 * 
		 */		
		public static function get timeout():uint
		{
			return mTimeout;
		}

		public static function get version():uint
		{
			return 10001;
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		//	全局事件注册函数
		private static var mDispatch:EventDispatcher = new EventDispatcher();

		public static function addEventListener(type:String, listener:Function, useCapture:Boolean = false, priority:int = 0, useWeakReference:Boolean = false):void
		{
			mDispatch.addEventListener(type, listener, useCapture, priority, useWeakReference);
		}

		public static function removeEventListener(type:String, listener:Function, useCapture:Boolean = false):void
		{
			mDispatch.removeEventListener(type, listener, useCapture);
		}

		public static function dispatchEvent(event:Event):Boolean
		{
			return mDispatch.dispatchEvent(event);
		}

		/**
		 * 	发送数据请求数据	
		 * 
		 *	@private 
		 * 	@param req
		 * 	@param onFinish
		 * 
		 */		
		public static function send(req:ByteArray, onFinish:Function):void
		{
			var _http:HttpRequestItem = getIdleNetSender();
			_http.http.timeout = mTimeout * 1000;
			_http.http.addEventListener(HttpRequest.COMPLETE, 	onFinishLoad);
			_http.http.addEventListener(HttpRequest.ERROR, 		onFinishLoadError);
			_http.http.addEventListener(HttpRequest.TIMEOUT, 	onFinishLoadTimeout);
			_http.http.doRequest(req);

			function onFinishLoad(e:MEvent):void
			{
				removeListeners(e);

				var code:int 		= EPDECODE;
				var data:ByteArray	= null;
				try
				{
					var ret:Object	= ApiTool.decode(e.data as ByteArray);

					code = ret.scode == -2?ELOGOUT:ret.scode;
					data = ret.scode ==  0?ret.res:null;
				}
				catch(e:Error) {}
				onFinish && onFinish.call(null, code, data);
			}

			function onFinishLoadError(e:MEvent):void
			{
				removeListeners(e);

				onFinish && onFinish.call(null, EIOERROR, null);
			}

			function onFinishLoadTimeout(e:MEvent):void
			{
				removeListeners(e);

				onFinish && onFinish.call(null, EIOTIMEOUT, null);
			}

			function removeListeners(e:MEvent):void
			{
				_http.http.removeEventListener(HttpRequest.COMPLETE, 	onFinishLoad);
				_http.http.removeEventListener(HttpRequest.ERROR, 		onFinishLoadError);
				_http.http.removeEventListener(HttpRequest.TIMEOUT, 	onFinishLoadTimeout);
				_http.idle = true;
			}
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		//	私有数据
		private static var mTimeout 	:uint	= 30;
		private static var mHttp		:Array	= new Array();

		private static function getIdleNetSender():HttpRequestItem
		{
			for (var i:uint = 0; i < mHttp.length; i++)
			{
				if (mHttp[i].idle == true)
				{
					mHttp[i].idle == false;
					return mHttp[i];
				}
			}

			mHttp.push(new HttpRequestItem(false));
			return mHttp[mHttp.length - 1];
		}
	}
}

import com.qq.openapi.ApiTool;
import com.qq.utils.HttpRequest;

class HttpRequestItem
{
	public var idle:Boolean 		= true;
	public var http:HttpRequest	= null;

	public function HttpRequestItem(v:Boolean = true)
	{
		idle = v;
		http = new HttpRequest(ApiTool.urlApiProxy);
	}
};
