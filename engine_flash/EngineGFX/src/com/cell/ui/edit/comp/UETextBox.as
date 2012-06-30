package com.cell.ui.edit.comp
{
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.edit.UIEditLoader;
	import com.cell.ui.layout.UIRect;
	
	import flash.xml.XMLNode;
	
	
	public class UETextBox extends UIComponent implements SavedComponent
	{
		public function UETextBox() 
		{	
			super(new UIRect());
		}
		
		public function onRead(edit:UIEdit, e:XMLNode, ld:UIEditLoader) : void
		{
			
		}
		
	}
}