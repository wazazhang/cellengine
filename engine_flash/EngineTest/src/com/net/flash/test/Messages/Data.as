package com.net.flash.test.Messages
{
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.NetDataTypes;


	/**
	 * Java Class [1] [com.net.flash.test.Messages.Data]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable]
	public class Data extends Message
	{
		/** Java type is : <font color=#0000ff>java.lang.String</font> */
		[JavaType(name="java.lang.String", leaf_type=NetDataTypes.TYPE_STRING)]
		public var message2 :  String;
		/** Java type is : <font color=#0000ff>boolean</font> */
		[JavaType(name="boolean", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var d0 :  Boolean;
		/** Java type is : <font color=#0000ff>byte</font> */
		[JavaType(name="byte", leaf_type=NetDataTypes.TYPE_BYTE)]
		public var d1 :  int;
		/** Java type is : <font color=#0000ff>short</font> */
		[JavaType(name="short", leaf_type=NetDataTypes.TYPE_SHORT)]
		public var d2 :  int;
		/** Java type is : <font color=#0000ff>char</font> */
		[JavaType(name="char", leaf_type=NetDataTypes.TYPE_CHAR)]
		public var d3 :  int;
		/** Java type is : <font color=#0000ff>int</font> */
		[JavaType(name="int", leaf_type=NetDataTypes.TYPE_INT)]
		public var d4 :  int;
		/** Java type is : <font color=#0000ff>float</font> */
		[JavaType(name="float", leaf_type=NetDataTypes.TYPE_FLOAT)]
		public var d5 :  Number;
		/** Java type is : <font color=#0000ff>java.lang.String[]</font> */
		[JavaType(name="java.lang.String[]", leaf_type=NetDataTypes.TYPE_STRING)]
		public var a_message2 :  Array;
		/** Java type is : <font color=#0000ff>boolean[]</font> */
		[JavaType(name="boolean[]", leaf_type=NetDataTypes.TYPE_BOOLEAN)]
		public var a_d0 :  Array;
		/** Java type is : <font color=#0000ff>byte[]</font> */
		[JavaType(name="byte[]", leaf_type=NetDataTypes.TYPE_BYTE)]
		public var a_d1 :  Array;
		/** Java type is : <font color=#0000ff>short[]</font> */
		[JavaType(name="short[]", leaf_type=NetDataTypes.TYPE_SHORT)]
		public var a_d2 :  Array;
		/** Java type is : <font color=#0000ff>char[]</font> */
		[JavaType(name="char[]", leaf_type=NetDataTypes.TYPE_CHAR)]
		public var a_d3 :  Array;
		/** Java type is : <font color=#0000ff>int[]</font> */
		[JavaType(name="int[]", leaf_type=NetDataTypes.TYPE_INT)]
		public var a_d4 :  Array;
		/** Java type is : <font color=#0000ff>float[]</font> */
		[JavaType(name="float[]", leaf_type=NetDataTypes.TYPE_FLOAT)]
		public var a_d5 :  Array;

		/**
		 * @param message2 as <font color=#0000ff>java.lang.String</font>
		 * @param d0 as <font color=#0000ff>boolean</font>
		 * @param d1 as <font color=#0000ff>byte</font>
		 * @param d2 as <font color=#0000ff>short</font>
		 * @param d3 as <font color=#0000ff>char</font>
		 * @param d4 as <font color=#0000ff>int</font>
		 * @param d5 as <font color=#0000ff>float</font>
		 * @param a_message2 as <font color=#0000ff>java.lang.String[]</font>
		 * @param a_d0 as <font color=#0000ff>boolean[]</font>
		 * @param a_d1 as <font color=#0000ff>byte[]</font>
		 * @param a_d2 as <font color=#0000ff>short[]</font>
		 * @param a_d3 as <font color=#0000ff>char[]</font>
		 * @param a_d4 as <font color=#0000ff>int[]</font>
		 * @param a_d5 as <font color=#0000ff>float[]</font>		 */
		public function Data(
			message2 :  String = null,
			d0 :  Boolean = false,
			d1 :  int = 0,
			d2 :  int = 0,
			d3 :  int = 0,
			d4 :  int = 0,
			d5 :  Number = 0,
			a_message2 :  Array = null,
			a_d0 :  Array = null,
			a_d1 :  Array = null,
			a_d2 :  Array = null,
			a_d3 :  Array = null,
			a_d4 :  Array = null,
			a_d5 :  Array = null) 
		{
			this.message2 = message2;
			this.d0 = d0;
			this.d1 = d1;
			this.d2 = d2;
			this.d3 = d3;
			this.d4 = d4;
			this.d5 = d5;
			this.a_message2 = a_message2;
			this.a_d0 = a_d0;
			this.a_d1 = a_d1;
			this.a_d2 = a_d2;
			this.a_d3 = a_d3;
			this.a_d4 = a_d4;
			this.a_d5 = a_d5;
		}
	}
}