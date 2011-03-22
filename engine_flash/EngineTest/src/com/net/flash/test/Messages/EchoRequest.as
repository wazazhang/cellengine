package com.net.flash.test.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [4] [com.net.flash.test.Messages.EchoRequest]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class EchoRequest extends Message
	{
		/** Java type is java.lang.String */
		public var message :  String;
		/** Java type is com.net.flash.test.Messages.Data */
		public var data :  com.net.flash.test.Messages.Data;
		/** Java type is com.net.flash.test.Messages.Data[] */
		public var datas :  Array;

		public function EchoRequest(
			message :  String = null,
			data :  com.net.flash.test.Messages.Data = null,
			datas :  Array = null) 
		{
			this.message = message;
			this.data = data;
			this.datas = datas;
		}
	}
}