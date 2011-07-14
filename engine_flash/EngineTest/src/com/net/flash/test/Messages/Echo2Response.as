package com.net.flash.test.Messages
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.NetDataTypes;


	/**
	 * 
	 * Java Class [3] [com.net.flash.test.Messages.Echo2Response]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public class Echo2Response implements MutualMessage
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font>*/
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var message :  String;

		/**
		 * @param message as <font color=#0000ff>java.lang.String</font>		 */
		public function Echo2Response(
			message :  String = null) 
		{
			this.message = message;
		}
	}
}