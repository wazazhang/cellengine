package com.cell.ui.edit.comp
{
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UIRect;
	
	import flash.xml.XMLNode;
	
	
	public class UERoot extends UIComponent implements SavedComponent
	{
		public function UERoot() 
		{	
			super(new UIRect());
		}
	
		public function onRead(e : XML) : void
		{
			
		}
	
	}
}