package com.cell.ui.component.listview
{
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.component.listview.ListView;
	import com.cell.ui.layout.UIRect;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.text.TextField;
	
	public class DefaultListViewItem extends UIComponent implements Item
	{
		private var columns : Array = new Array();
		
		public function DefaultListViewItem(ui:UIRect)
		{
			super(ui);
		}
		
		public function updateList(list:ListView) : void
		{
			reset(list);
		}
		
		public function setColumnItems(items:Array) : void
		{
			for each (var os : * in columns) {
				removeChild(os);
			}
			this.columns = items;
			for each (var ss : * in columns) {
				addChild(ss);
			}
		}
		
		protected function reset(list:ListView) : void
		{
			this.width = list.getBaseWidth();
			var rcount : int = list.getColumnCount();
			var ccount : int = columns.length;
			for (var i:int=0; i<ccount; i++) {
				var cc : DisplayObject = list.getColumn(i);
				columns[i].x = cc.x;
				columns[i].y = 0;
				columns[i].width  = cc.width;
				columns[i].height = height;
			}
		}
		
	}

}