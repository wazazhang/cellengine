package com.net.flash.test.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;


	/**
	 * Java Class [6] [com.net.flash.test.Messages.EchoResponse]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class EchoResponse extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		public var message :  String;
		/** Java type is : <font color=#0000ff>com.net.flash.test.Messages.Data</font> */
		public var data :  com.net.flash.test.Messages.Data;
		/** Java type is : <font color=#0000ff>com.net.flash.test.Messages.Data[]</font> */
		public var datas :  Array;

		/**
		 * @param message as <font color=#0000ff>java.lang.String</font>
		 * @param data as <font color=#0000ff>com.net.flash.test.Messages.Data</font>
		 * @param datas as <font color=#0000ff>com.net.flash.test.Messages.Data[]</font>		 */
		public function EchoResponse(
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