package com.net.flash.test.Messages
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;


	/**
	 * 
	 * Java Class [8] [com.net.flash.test.Messages.TargetState]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class TargetState  implements MutualMessage
	{
		static public const ALL : 		Unsupported type : com.net.flash.test.Messages$TargetState = ALL;

		static public const ALIVE : 		Unsupported type : com.net.flash.test.Messages$TargetState = ALIVE;

		static public const DEAD : 		Unsupported type : com.net.flash.test.Messages$TargetState = DEAD;





		/**
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param ordinal as <font color=#0000ff>int</font>		 */
		public function TargetState(
			name : String = null,
			ordinal : int = 0) 
		{
			this.name = name;
			this.ordinal = ordinal;
		}
		


	}
}