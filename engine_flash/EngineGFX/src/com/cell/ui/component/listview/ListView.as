package com.cell.ui.component.listview
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.Panel;
	import com.cell.ui.component.ScrollBar;
	import com.cell.ui.component.ScrollPan;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.util.Map;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.geom.Rectangle;

	public class ListView extends ScrollPan
	{
		private var header 			: CellSprite;
		private var headerColumns   : Map = new Map();
		private var headerHeight	: uint = 24;
		
		private var border 			: int = 10;
		private var scrollV 		: ScrollBar;		
		private var enableScrollV 	: Boolean = true;
		
		private var autoColumn		: Boolean = true;
		
		public function ListView(w:int, h:int)
		{
			this.header = new CellSprite();
			this.scrollV = new ScrollBar(ScrollBar.STYLE_VERTICAL);
			
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.listview.ListView", this), w, h);
			
			this.border = UILayoutManager.getInstance().getProperty(
				"com.cell.ui.component.listview.ListView.border", this)
			this.headerHeight = UILayoutManager.getInstance().getProperty(
				"com.cell.ui.component.listview.ListView.headerHeight", this)
				
			this.addChild(header);
			this.addChild(scrollV);
			
			resize(w, h, true);
		}
		
		override protected function resize(w:int, h:int, flush:Boolean):Boolean
		{
			var ret : Boolean = super.resize(w, h, flush);
			if (ret) {
				reset(w, h);
			}
			return ret;
		}
		
		private function reset(w:int, h:int) : void
		{
			var border2 : int = border*2;
			var border3 : int = border*3;
			var hw : Number = w - border3-scrollV.width;
			var hx : Number = 0;
			for each (var c:UIComponent in headerColumns.keys()) {
				var pct : Number = headerColumns[c];
				c.height = headerHeight;
				if (autoColumn) {			
					c.width = hw*pct;
				} else {
					c.width = pct;
				}
				c.x = hx;
				hx += c.width;
			}
			
			scrollV.visible = enableScrollV;
			scrollV.height = h - border3 - headerHeight;
			base.resize(hw, scrollV.height);
			
		
			header.x = border;
			header.y = border;
			base.x = border;
			base.y = border2 + headerHeight;
			scrollV.x = w - scrollV.width - border;
			scrollV.y = base.y;
		}
		
		override protected function updateScroll(e:Event) : void
		{
			var mrect : Rectangle = base.getChildBounds();
			var srect : Rectangle = base.scrollRect;
			
			scrollV.setRange(mrect.y, mrect.y + mrect.height);
			scrollV.setValue(srect.y, srect.height);
			scrollV.visible = enableScrollV && scrollV.isValueIn();
			
			super.updateScroll(e);
			
			for (var i:int=base.numChildren-1; i>=0; --i) {
				var o : * = base.getChildAt(i);
				if (o is Item) {
					o.updateList(this);
				}
			}
		}
		
	
		public function setEnableScroll(v:Boolean) : void
		{
			this.enableScrollV = v;
			resize(width, height, true);
		}
		
		final public function setHeaderHeight(h:int) : void
		{
			this.headerHeight = h;
			reset(width, height);
		}
		
		final public function getHeaderHeight() : int
		{
			return headerHeight;	
		}
		
		final public function getBaseWidth() : Number
		{
			return width-(border*3)-scrollV.width;	
		}
		
//		----------------------------------------------------------------------------------------
		
		public function addColumn(html:String, pct:Number) : UIComponent
		{
			var hd : UIComponent = new DefaultListViewHeader(html);
			addColumnCompoment(hd, pct);
			return hd;
		}
		
		public function addColumnCompoment(hd:UIComponent, wp:Number) : void
		{
			headerColumns.put(hd, wp);
			header.addChild(hd); 
			reset(width, height);
		}
		
		public function getColumnCount() : int
		{
			return header.numChildren;
		}
		
		public function getColumn(index:int) : UIComponent
		{
			return header.getChildAt(index) as UIComponent;
		}
		
		public function getColumnWidth(index:int) : Number
		{
			return header.getChildAt(index).width;
		}
		
//		----------------------------------------------------------------------------------------
		
		
		
		
		
		
//		----------------------------------------------------------------------------------------
		
		public function addItem(o:DisplayObject) : void
		{
			base.addChildH(o);
		}
		
	}
	
	
	
}


