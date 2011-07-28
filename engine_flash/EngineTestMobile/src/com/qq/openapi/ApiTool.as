package com.qq.openapi
{
	import com.qq.protocol.DataHead;
	import com.qq.protocol.DataHelp;
	
	import flash.display.DisplayObject;
	import flash.system.Capabilities;
	import flash.system.Security;
	import flash.utils.ByteArray;


	public class ApiTool
	{
		/**
		 *	用于判断当前运行环境是否是QQ手机浏览器。 
		 * 	@return 返回<code>true</code>为手机QQ浏览器，<code>false</code>为非手机QQ浏览器
		 * 
		 */
		public static function isQQBrowser():Boolean						
		{
			if(flash.system.Capabilities.version.indexOf("QB") != -1)
			{
				return true;
			}

			return false;
		}

		public static function isDebug():Boolean
		{
			return flash.system.Security.sandboxType == flash.system.Security.LOCAL_WITH_FILE || flash.system.Security.sandboxType == flash.system.Security.LOCAL_TRUSTED;
		}

		/**
		 *	设置开发测试环境登录身份以及游戏身份验证。
		 * 	@param token 在闪游地带地带登陆后的身份认证票据 
		 * 	@param appId 用于网络游戏请求转发的应用ID，单机游戏忽略该参数
		 * 
		 */
		public static function noVerify(token:String, appId:String = ""):void
		{
			if(flash.system.Security.sandboxType == flash.system.Security.LOCAL_WITH_FILE || flash.system.Security.sandboxType == flash.system.Security.LOCAL_TRUSTED)
			{			
				_noVerify 	= true;
				_token 		= token;
				_appId 		= appId;
			}
		}

		/**
		 * 	身份认证票据失效后的登录地址，当用户登录后会跳转回当前游戏。 
		 * 	@return 登录地址  
		 * 
		 */
		public static function get urlLogin():String	
		{ 
			return _loginURL + "&go_url=" + encodeURIComponent((isDebug()?URL_DEV:URL_REAL) + _appId);
		}

		/**
		 *	后台接入服务器地址，在网络游戏中使用，单机游戏忽略该参数。 
		 * 	@return 
		 * 
		 */		
		public static function get urlRequest():String
		{
			return isQQBrowser()?"imtts://":_requestURL;
		}

		/**
		 *	后台API服务器地址，被API内部代码使用，开发者忽略该参数。 
		 * 	@private	
		 * 	@return 
		 * 
		 */		
		public static function get urlApiProxy():String
		{
			return isQQBrowser()?URL_APIPROXY:_apiURL;
		}

		/**
		 *	获取OPENAPISERVER的APPID 
		 * 	@return AppID 
		 * 
		 */
		public static function get apiAppId():String
		{
			return AID_FORJCE;
		}

		/**
		 *	全局数据初始化函数。
		 * 
		 *  <p>程序启动时使用该函数从FlashVars获取传入的系统设定参数。</p>
		 * 	@param root Stage的root
		 * 	@return 
		 * 
		 */		
		public static function initialize(root:DisplayObject):void
		{
			if(!_noVerify)
			{
				_gameId = root.loaderInfo.parameters["gameid"];
				if (_gameId == null)
				{
					//进行跳转
					trace("Error:not found gameid, please review the api cookbook");
					return ;
				}

				_appId = root.loaderInfo.parameters["appid"];

				_token = root.loaderInfo.parameters["token"];
			}

			if (root.loaderInfo.parameters["imtts"] != null)
			{
				_requestURL = root.loaderInfo.parameters["imtts"];
			}
			else
			{
				_requestURL = "http://183.62.115.9:18200/http";
			}

			if (root.loaderInfo.parameters["imtt"] != null)
			{
				_resURL = root.loaderInfo.parameters["imtt"];
			}
			else
			{
				_resURL = "http://61.172.204.182/flash/";
			}

			if (_resURL.charAt(_resURL.length - 1) != "/")
			{
				_resURL += "/";
			}

			//资源版本号
			if (root.loaderInfo.parameters["version"] != null)
			{
				_version = root.loaderInfo.parameters["version"];
			}
			else
			{
				_version = "";
			}
		}

		/**
		 *	设置资源路径。
		 * 
		 *	@param name		资源关键字，在当前游戏中唯一标识该资源
		 *	@param local	本地测试路径，可以使用相对于当前程序的相对路径
		 * 	@param imtt		在网络服务器上相对于当前程序根目录的相对路径
		 *
		 * 	@example
		 * 	<p>开发者只需一次设置资源的路径，API会自动判断运行环境的类型，以决定从网络还是本地磁盘获取资源。</p>
		 * 	<p>使用该接口的好处显而易见，只要保证相对路径的不变，程序可以直接上线，而不必重新设置URL地址了。</p>
		 * 	<p> </p>
		 * 	<p>例如，如果程序需要动态加载素材包“HelloWorld.swf”。开发者可以这样使用接口设置该资源的URL：</p>
		 * 	<p>ApiTool.putSubResource("Hello", "../assets/HelloWorld.swf", "/HelloWorld/assets/HelloWorld.swf");</p>
		 * 	<p> </p>
		 * 	<p>当开发者需要下载资源时，可以直接使用下面的资源URL获取函数：</p>
		 * 	<p>mLoader:Loader = new Loader();</p>
		 *	<p>mLoader.load(new URLRequest(ApiTool.getSubResource("Hello")));</p>
		 */
		public static function putSubResource(name:String, local:String, imtt:String):void
		{
			//保护一下路径
			if(imtt.indexOf("/") != 0)
			{
				imtt = "/" + imtt;
			}

			var httpRes : String = _resURL;
			if(_version.length != 0)
			{
				httpRes += _version + "/";
			}
			
			httpRes += _gameId + imtt;
			
			_subResources[name] = {local:local, net: _resURL + _gameId + imtt, imtt:"imtt://" + _gameId + imtt};
		}

		/**
		 * 	获取资源地址。
		 *
		 * 	@param name 资源关键字，同函数ApiTool.putSubResource的第一参数。
		 * 	@return 欲下载资源的URL地址
		 * 
		 */		
		public static function getSubResource(name:String):String
		{
			if(flash.system.Security.sandboxType == flash.system.Security.LOCAL_WITH_FILE
				|| flash.system.Security.sandboxType == flash.system.Security.LOCAL_TRUSTED)
			{
				return _subResources[name].local;
			}
			else if(isQQBrowser())
			{
				return _subResources[name].imtt;
			}
			else
			{
				return _subResources[name].net;
			}
		}

		/**
		 * 	系统的API封包函数
		 * 	@private
		 * 
		 */		
		public static function encode(postData : ByteArray, appID:String) : ByteArray
		{
			if(isQQBrowser())
			{
				return 	postData;	
			}

			return PluginEncode("imtt://from_pc_browser", "", "token=" + _token + "; appid=" + appID + "; gameid=" + _gameId, postData);
		}

		/**
		 *	系统的API解包函数 
		 * 	@private
		 * 	@param ist
		 * 	@return 
		 * 
		 */
		public static function decode(ist:ByteArray):Object
		{
			if (isQQBrowser())
			{
				return 	{scode:0, res:ist};	
			}

			try
			{
				var res:Object = PluginDecode(ist);
				return {scode:res.code, res:res.data};
			}
			catch (e:Error)	{ }

			return {scode:-1, code:-1};
		}

		////////////////////////////////////////////////////////////////////////////////
		//以下是模拟插件的封包和解包逻辑
		private static function PluginEncode(sResUrl:String, sResMd5:String, sCookie:String, postData:ByteArray):ByteArray
		{
			var ost:ByteArray 	= new ByteArray();

			DataHelp.writeString(ost, sResUrl, 0);
			DataHelp.writeString(ost, sResMd5, 1);
			DataHelp.writeString(ost, sCookie, 2);
			DataHelp.writeVectorByte(ost, postData, 3);

			return ost;
		}

		private static function PluginDecode(ist:ByteArray):Object
		{
			var rtnCode:int			= DataHelp.readInt32(ist, 0, false);
			var rspContType:int		= DataHelp.readInt32(ist, 1, false);
			var openNew:int			= DataHelp.readInt32(ist, 2, false);
			var respData:ByteArray	= DataHelp.readVectorByte(ist, 3, false);

			if (rtnCode == -2)
			{
				_loginURL 			= respData.readMultiByte(respData.length, "cn-gb");
			}
			return {code:rtnCode, data:respData};
		}

		///////////////////////////////////////////////////////////////////////////////////////////
		//	内部常量数据
		private static const URL_APIPROXY	: String = "imtts://appid=3";
		private static const URL_DEV		: String = "http://fgdev.imtt.qq.com/flash?action=singleGame&version=v1.0&gameId=";
		private static const URL_REAL		: String = "http://fg.imtt.qq.com/flash?action=singleGame&version=v1.0&gameId=";
		private static const GID_FORTEST	: String = "180";
		private static const AID_FORJCE	: String = "3";

		///////////////////////////////////////////////////////////////////////////////////////////
		//	内部变量数据
		private static var _subResources 	: Object = new Object();
		private static var _gameId		: String = GID_FORTEST;
		private static var _appId 		: String;
		private static var _token 		: String;
		private static var _requestURL 	: String;
		private static var _apiURL		: String = "http://183.62.115.7:18200/http";
		private static var _resURL 		: String;
		private static var _version 		: String;
		private static var _loginURL		: String;
		private static var _noVerify 		: Boolean = false;
	}
}
