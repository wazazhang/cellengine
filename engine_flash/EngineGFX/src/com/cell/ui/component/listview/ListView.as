package com.cell.ui.component.listview
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.component.Panel;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	
	import flash.display.DisplayObject;

	public class ListView extends UIComponent
	{
		private var header 		: CellSprite;
		
		private var rowPanel 	: Panel;
		
		private var rows   		: Array;
		
		public function ListView(w:int, h:int)
		{
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.listview.ListView", this));
			
		}
		
		public function setHeaderHeight(h:int) : void
		{
			
		}
		
		public function getHeaderHeight() : int
		{
			return 0;	
		}
		
		public function addHeader(html:String, width:int)
		{
			
		}
		
		
		public function addRow(row:DisplayObject) : void
		{
			
		}
	}
	
	
	
}
