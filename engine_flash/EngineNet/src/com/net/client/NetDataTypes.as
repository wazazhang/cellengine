package com.net.client
{
	import flash.utils.describeType;
	import flash.utils.getDefinitionByName;
	import flash.utils.getQualifiedClassName;

	public class NetDataTypes
	{
		public static const TYPE_BOOLEAN : int	= -1;
		public static const TYPE_BYTE	 : int	= -2;
		public static const TYPE_SHORT	 : int	= -3;
		public static const TYPE_CHAR	 : int	= -4;
		public static const TYPE_INT	 : int	= -5;
//		public static const TYPE_LONG	 : int	= -6;
		public static const TYPE_FLOAT	 : int	= -7;
		public static const TYPE_DOUBLE	 : int	= -8;	
		
		public static const TYPE_STRING			: int	= -9;
//		public static const TYPE_OBJECT			: int	= -10;
		public static const TYPE_EXTERNALIZABLE	: int	= -11;

		
		public static function toTypeName(type : int) : String {
			switch (type) {
				case NetDataTypes.TYPE_EXTERNALIZABLE:
					return "TYPE_EXTERNALIZABLE";
				case NetDataTypes.TYPE_BOOLEAN:
					return "TYPE_BOOLEAN";
				case NetDataTypes.TYPE_BYTE:
					return "TYPE_BYTE";
				case NetDataTypes.TYPE_CHAR:
					return "TYPE_CHAR";
				case NetDataTypes.TYPE_SHORT:
					return "TYPE_SHORT";
				case NetDataTypes.TYPE_INT:
					return "TYPE_INT";
//				case NetDataTypes.TYPE_LONG:
//					return "TYPE_LONG";
				case NetDataTypes.TYPE_FLOAT:
					return "TYPE_FLOAT";
				case NetDataTypes.TYPE_DOUBLE:
					return "TYPE_DOUBLE";
				case NetDataTypes.TYPE_STRING:
					return "TYPE_STRING";
//				case NetDataTypes.TYPE_OBJECT:
//					return "TYPE_OBJECT";
				default:
					return null;
			}
		}
		
		public static function getCompomentType(object : Object, factory : MessageFactory) : int
		{
			var c : Class = Class(getDefinitionByName(getQualifiedClassName(object)));
			var xml : XML = describeType(new c());
			var table:String = 
				xml.metadata.(@name=="Table").arg.(@key=="name").@value;
		
			
			return 0;
		}
	}
}