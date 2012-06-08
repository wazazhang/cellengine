
package com.cell.ui.edit.comp
{
	import com.cell.ui.component.TextInput;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.layout.UIRect;
	
	import flash.xml.XMLNode;
	
	
	public class UETextInput extends TextInput implements SavedComponent
	{
		public function UETextInput() 
		{	
			super("");
		}
		
		public function onRead(edit:UIEdit, e:XMLNode) : void
		{
//			isPassword="false"			# 是否显示为密码
//			is_readonly="false"			# 是否只读，不能被编辑
//			textColor="ffffffff"		# 文本颜色
			this.textField.mouseEnabled 		= Boolean(e.attributes["is_readonly"]!="true");
			this.textField.displayAsPassword 	= Boolean(e.attributes["isPassword"]=="true");
			this.textField.textColor			= parseInt(e.attributes["textColor"], 16);
			
			
			this.resize(width, height, true);
		}
		
	}
}