package com.cell.ui.edit.comp
{
	import com.cell.ui.component.TextButton;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.edit.UIEditLoader;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.XMLUtil;
	
	import flash.filters.BitmapFilter;
	import flash.filters.GlowFilter;
	import flash.xml.XMLNode;
	
	
	public class UEButton extends TextButton implements SavedComponent
	{
		public function UEButton() 
		{	
			super("");
		}
		
		public function onRead(edit:UIEdit, e:XMLNode, ld:UIEditLoader) : void
		{
			var e_layout : XMLNode = XMLUtil.findChild(e, "layout_down");
			if (e_layout != null) {
				var layout : UIRect = edit.getLayout(e_layout, width, height);
				if (layout != null) {
					super.setButtonLayout(getBG(), layout);
				}
			}
			this.textField.text = e.attributes["text"];
			this.textField.textColor = parseInt(e.attributes["unfocusTextColor"], 16);
//			this.textField.filters.
			//this->text_anchor	= e->getAttribute("text_anchor");			//# 文本 对齐方式   
			//this->text_offset_x	= e->getAttributeAsInt("text_offset_x");	//# 文本 绘制偏移坐标
			//this->text_offset_y	= e->getAttributeAsInt("text_offset_y");	//# 文本 绘制偏移坐标
			var bcolor:uint = parseInt(e.attributes["textBorderColor"], 16);
			var tfilter:BitmapFilter = new GlowFilter(bcolor);
			var tfilters:Array = new Array();
			tfilters.push(tfilter);
			this.textField.filters = tfilters;
			this.textField.x += int(e.attributes["text_offset_x"]);
			this.textField.y += int(e.attributes["text_offset_y"]);
			
			this.resize(width, height, true);
		}
		
	}
}