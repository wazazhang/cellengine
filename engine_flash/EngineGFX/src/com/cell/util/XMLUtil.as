package com.cell.util
{
	import flash.xml.XMLNode;

	public class XMLUtil
	{
		
		public static function findChild(e:XMLNode, childName:String) : XMLNode
		{
			for each (var cc:XMLNode in e.childNodes) {
				if (cc.nodeName == childName) {
					return cc;
				}
			}
			return null;
		}
	}
}