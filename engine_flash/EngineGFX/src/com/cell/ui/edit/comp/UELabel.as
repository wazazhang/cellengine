
package com.cell.ui.edit.comp
{
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.layout.UIRect;
	
	import flash.filters.BitmapFilter;
	import flash.filters.GlowFilter;
	import flash.xml.XMLNode;
	
	
	public class UELabel extends Lable implements SavedComponent
	{
		public function UELabel() 
		{	
			super("");
		}
		
		public function onRead(edit:UIEdit, e:XMLNode) : void
		{
			
//			text="UELabel"				# 文本
//			textColor="ffffffff"		# 文本颜色
//			text_anchor="C_C"			# 文本对齐方式
			this.textField.text = e.attributes["text"];
			this.textField.textColor = parseInt(e.attributes["textColor"], 16);
			var bcolor:uint = parseInt(e.attributes["textBorderColor"], 16);
			var tfilter:BitmapFilter = new GlowFilter(bcolor);
			var tfilters:Array = new Array();
			tfilters.push(tfilter);
			this.textField.filters = tfilters;
			
			this.resize(width, height, true);
			
		}
		
	}
}