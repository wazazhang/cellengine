package com.net.flash.test.Messages
{
	import com.cell.net.io.MutualMessage;
	import com.cell.net.io.MessageFactory;
	import com.cell.net.io.NetDataTypes;
	import com.cell.util.Map;


	/**
	 * 
	 * Java Class [7] [com.net.flash.test.Messages.StateInBattle]<br>
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	[Bindable] 
	public  class StateInBattle  implements MutualMessage
	{
		static public const Normal : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Normal;

		static public const Cannt_Comannd : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_Comannd;

		static public const Cannt_Physics : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_Physics;

		static public const Cannt_Magic : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_Magic;

		static public const Cannt_Action : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_Action;

		static public const Cannt_UseItem : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_UseItem;

		static public const Cannt_Escape : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_Escape;

		static public const Cannt_AttackByPhysics : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_AttackByPhysics;

		static public const Cannt_AttackByMagic : 		Unsupported type : com.net.flash.test.Messages$StateInBattle = Cannt_AttackByMagic;

		/** Java type is : <font color=#0000ff>short</font>*/
		public var value : int;





		/**
		 * @param name as <font color=#0000ff>java.lang.String</font>
		 * @param ordinal as <font color=#0000ff>int</font>
		 * @param value as <font color=#0000ff>short</font>		 */
		public function StateInBattle(
			name : String = null,
			ordinal : int = 0,
			value : int = 0) 
		{
			this.name = name;
			this.ordinal = ordinal;
			this.value = value;
		}
		


	}
}