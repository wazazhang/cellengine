package com.cell.ui.edit.comp
{
	import com.cell.ui.component.TextButton;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.layout.UIRect;
	
	import flash.xml.XMLNode;
	
	
	public class UEButton extends TextButton implements SavedComponent
	{
		public function UEButton() 
		{	
			super("");
		}
		
		public function onRead(edit:UIEdit, e:XMLNode) : void
		{
//			NodeList list = e.getChildNodes();
//			for (int i = 0; i < list.getLength(); i++) {
//				if (list.item(i) instanceof Element) {
//					Element ev = (Element) list.item(i);
//					if (ev.getNodeName().equals("layout_down")) {
//						custom_layout_down = UITreeNode.readLayout(edit, ev);
//					}
//				}
//			}
		}
		
	}
}