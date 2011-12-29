package com.cell.ui.component
{
	import com.cell.ui.BasePanel;
	import com.cell.ui.TouchScrollPanel;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.util.CMath;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import flash.geom.Vector3D;

	public class Panel extends ScrollPan
	{		
		private var border : int = 10;
		private var scrollH : ScrollBar;
		private var scrollV : ScrollBar;
		
		private var enableScrollV : Boolean = true;
		private var enableScrollH : Boolean = true;
		
		public function Panel(width:int=300, height:int=300, border:int=10)
		{
			this.border = border;
			scrollH = new ScrollBar(ScrollBar.STYLE_HORIZONTAL);
			scrollV = new ScrollBar(ScrollBar.STYLE_VERTICAL);
			
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.Panel", this), width, height);
			super.addChild(scrollH);
			super.addChild(scrollV);

			resize(width, height, true);
		}
		
		public function getBorder() : int 
		{
			return border;
		}
		
		public function setEnableScroll(h:Boolean, v:Boolean) : void
		{
			this.enableScrollV = v;
			this.enableScrollH = h;
			resize(width, height, true);
		}
		
		public function getPanelBounds() : Rectangle
		{
			return new Rectangle(border, border, width-border*3, height-border*3);
		}
		
		override protected function resize(w:int, h:int, flush:Boolean) : Boolean
		{
			var ret : Boolean = super.resize(w, h, flush);
			
			if (ret) 
			{
				var border2 : int = border*2;
				scrollV.visible = enableScrollV;
				scrollH.visible = enableScrollH;
				
				if (scrollV.visible) {
					scrollH.width = w - border2 - scrollV.width;
				} else {
					scrollH.width = w - border2;
				}
				if (scrollH.visible) {
					scrollV.height = h - border2 - scrollH.height;
				} else {
					scrollV.height = h - border2;
				}

				if (enableScrollH && enableScrollV) {
					base.resize(w-border2-border, h-border2-border);
				} 
				else if (!enableScrollH && enableScrollV) {
					base.resize(w-border2-border, h-border2);
				} 
				else if (enableScrollH && !enableScrollV) {
					base.resize(w-border2, h-border2-border);
				} 
				else {
					base.resize(w-border2, h-border2);
				}
			}
			
			scrollV.x = w - scrollV.width - border;
			scrollV.y = border;
			scrollH.x = border;
			scrollH.y = h - scrollH.height - border;
			
			base.x = border;
			base.y = border;
			
			return ret;
		}
		
		override protected function updateScroll(e:Event) : void
		{
			var mrect : Rectangle = base.getChildBounds();
			var srect : Rectangle = base.scrollRect;
			
			scrollV.setRange(mrect.y, mrect.y + mrect.height);
			scrollV.setValue(srect.y, srect.height);
			scrollV.visible = enableScrollV && scrollV.isValueIn();
			
			scrollH.setRange(mrect.x, mrect.x + mrect.width);
			scrollH.setValue(srect.x, srect.width);
			scrollH.visible = enableScrollH && scrollH.isValueIn();
			
			super.updateScroll(e);
		}
		
		
//		-----------------------------------------------------------------------------------
		
	
	}
}

