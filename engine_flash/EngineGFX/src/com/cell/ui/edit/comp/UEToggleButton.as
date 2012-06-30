package com.cell.ui.edit.comp
{
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.edit.UIEditLoader;
	import com.cell.ui.layout.UIRect;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.xml.XMLNode;
	
	
	public class UEToggleButton extends UEButton
	{
		private var isChecked : Boolean = false;
		
		public function UEToggleButton() 
		{	
			baseButton.mouseEnabled = false;
//			baseButton.mouseChildren = false;
			addEventListener(MouseEvent.CLICK, onMouseDown);
		}
		
		override public function onRead(edit:UIEdit, e:XMLNode, ld:UIEditLoader) : void
		{
			super.onRead(edit, e, ld);
			setSelected(e.attributes["isChecked"] == "true");
		}
		
		
		override public function setButtonLayout(up:UIRect, down:UIRect) : void {
			bg		 	= up;
			btn_sel   	= down;
		}
		
		public function setSelected(v:Boolean) : void
		{
			if (v != isChecked) {
				isChecked = v;
				if (isChecked) {
					baseButton.setState(btn_sel, getBG());
				} else {
					baseButton.setState(getBG(), btn_sel);
				}
			}
			
		}
		
		public function getSelected() : Boolean
		{
			return isChecked;
		}
		
		private function onMouseDown(e:MouseEvent) : void
		{
//			trace(isChecked);
			setSelected(!isChecked);
//			trace(isChecked);
		}
	}
}