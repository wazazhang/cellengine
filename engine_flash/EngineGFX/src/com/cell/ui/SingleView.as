package com.cell.ui
{
	import com.cell.gfx.CellSprite;
	
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.events.Event;

	public class SingleView extends CellSprite
	{		
		private var panel : SingleViewPanel;
		private var vbar : SingleViewScrollBar;

		public function SingleView(vw:int, vh:int, 
								   sc_hd:BitmapData,
								   sc_back:BitmapData, 
								   txt:ImageNumber,
								   backcolor:int=0, 
								   backalpha:Number=0)
		{
			panel = new SingleViewPanel(vw, vh, backcolor, backalpha);
			addChild(panel);
			
			vbar = new SingleViewScrollBar(
				sc_hd, 
				sc_back,
				txt);
			vbar.setRange(1, panel.numChildren);
			vbar.setValue(panel.getViewIndex()+1);
			vbar.x = vw/2;
			vbar.y = vh - vbar.height-2;
			addChild(vbar);
		}
		
		public function getPanel() : SingleViewPanel
		{
			return panel;
		}
		public function getScrollBar() : SingleViewScrollBar
		{
			return vbar;
		}
		override protected function update(e:Event):void
		{
			if (vbar.getValue()!=panel.getViewIndex()+1) 
			{
				vbar.setValue(panel.getViewIndex()+1);
			}
			if (vbar.getMax() != panel.numChildren) 
			{
				vbar.setRange(1, panel.numChildren);
			}		
		}
		
		
	}
}