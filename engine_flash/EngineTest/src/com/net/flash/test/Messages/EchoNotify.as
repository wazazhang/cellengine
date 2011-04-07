package com.net.flash.test.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [4] [com.net.flash.test.Messages.EchoNotify]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class EchoNotify extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var message :  String;

		/**
		 * @param message as <font color=#0000ff>java.lang.String</font>		 */
		public function EchoNotify(
			message :  String = null) 
		{
			this.message = message;
		}
	}
}