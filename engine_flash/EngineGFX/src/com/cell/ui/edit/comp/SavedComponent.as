package com.cell.ui.edit.comp
{
	import com.cell.ui.edit.UIEdit;
	
	import flash.xml.XMLNode;
	
	
	
	public interface SavedComponent 
	{
	
		function onRead(edit:UIEdit, e:XMLNode) : void;
		
		
	}
}