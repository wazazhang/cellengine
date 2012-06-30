package com.cell.ui.edit.comp
{
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.edit.UIEdit;
	import com.cell.ui.edit.UIEditEvent;
	import com.cell.ui.edit.UIEditLoader;
	import com.cell.ui.layout.UIRect;
	
	import flash.xml.XMLNode;

	public class UEFileNode extends UIComponent implements SavedComponent
	{
		private var filename 	: String = null;
		private var croot 		: UERoot = null;
		
		public function UEFileNode() 
		{	
			super(new UIRect(UIRect.IMAGE_STYLE_NULL));
		}
		
		public function onRead(edit:UIEdit, e:XMLNode, ld:UIEditLoader) : void
		{	
			this.filename = e.attributes["fileName"];
		}
		
		public function get fileName() : String
		{
			return filename;
		}
		
		public function getContent() : UERoot
		{
			return croot;
		}
		
		public function isComplete() : Boolean
		{
			return croot != null;
		}
		
		public function onComplete(e:UIEditEvent) : void
		{
			clearEventListener(e.loader);
			this.croot = e.loader.getContent();
			this.addChild(croot);
		}
		
		public function onError(e:UIEditEvent) : void
		{
			clearEventListener(e.loader);
		}
		
		private function clearEventListener(e:UIEditLoader) : void 
		{
			e.removeEventListener(UIEditEvent.LOADED, onComplete);
			e.removeEventListener(UIEditEvent.ERROR,  onError);
		}
	}
}