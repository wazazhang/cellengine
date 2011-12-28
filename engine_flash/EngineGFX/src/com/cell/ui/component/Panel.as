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

	public class Panel extends UIComponent
	{		
		private var base : BasePanel;
		private var border : int = 10;
		private var scrollH : ScrollBar;
		private var scrollV : ScrollBar;
		
		private var enableScrollV : Boolean = true;
		private var enableScrollH : Boolean = true;
		
		private var start_point 		: Vector3D;
		private var move_point 			: Vector3D = new Vector3D();
		private var end_point 			: Vector3D = new Vector3D();
		private var start_camera_point 	: Vector3D = new Vector3D();
		private var speed 				: Vector3D;
		public var touch_acc 			: Vector3D = new Vector3D();
		
		
		public function Panel(width:int=300, height:int=300, border:int=10)
		{
			super(UILayoutManager.getInstance().createUI("com.cell.ui.component.Panel", this));
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			this.border = border;
			
			base = new BasePanel(width, height, 0, 0);
			super.addChild(base);
			
			scrollH = new ScrollBar(ScrollBar.STYLE_HORIZONTAL);
			super.addChild(scrollH);
			
			scrollV = new ScrollBar(ScrollBar.STYLE_VERTICAL);
			super.addChild(scrollV);
			
			resize(width, height, true);
			
			addEventListener(MouseEvent.MOUSE_DOWN,	onMouseDown);
			addEventListener(MouseEvent.MOUSE_MOVE,	onMouseMove);
			addEventListener(MouseEvent.MOUSE_UP, 	onMouseUp);
			addEventListener(MouseEvent.MOUSE_OUT, 	onMouseOut);
			touch_acc.x = 0.9;
			touch_acc.y = 0.9;
			
			this.addEventListener(Event.ENTER_FRAME, updateScroll);
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
		
		public function getPanelChildCount() : int
		{
			return base.numChildren;
		}
		
		public function getPanelChild(index:int) : DisplayObject
		{
			return base.getChildAt(index);
		}
		
		public function getPanelChildIndex(o:DisplayObject) : int
		{
			return base.getChildIndex(o);
		}
		
		
		public function getPanelBounds() : Rectangle
		{
			return new Rectangle(border, border, width-border*3, height-border*3);
		}
		
		public function addPanelChild(o:DisplayObject) : DisplayObject
		{
			return this.base.addChild(o);
			updateScroll();
		}
		
		public function addPanelChildAt(o:DisplayObject, index:int) : DisplayObject
		{
			return this.base.addChildAt(o, index);
			updateScroll();
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
		
		private function updateScroll(e:Event) : void
		{
			var mrect : Rectangle = base.getChildBounds();
			var srect : Rectangle = base.scrollRect;
			
			scrollV.setRange(mrect.y, mrect.y + mrect.height);
			scrollV.setValue(srect.y, srect.height);
			scrollV.visible = enableScrollV && scrollV.isValueIn();
			
			scrollH.setRange(mrect.x, mrect.x + mrect.width);
			scrollH.setValue(srect.x, srect.width);
			scrollH.visible = enableScrollH && scrollH.isValueIn();
			
//			trace(scrollV.getValue() + "/" + scrollV.getMax());
			
			// update
			end_point.x = (move_point.x);
			end_point.y = (move_point.y);
			move_point.x = (this.mouseX);
			move_point.y = (this.mouseY);
			
			if (speed != null) {
				if (int(speed.x) != 0 || int(speed.y) != 0) {
					base.moveCamera(speed.x, speed.y);
				}
				speed.x *= touch_acc.x;
				speed.y *= touch_acc.y;
			}
		}
		
		
//		-----------------------------------------------------------------------------------
		
		protected function onMouseDown(e:MouseEvent) : void {
			base.createLocalBounds();
			start_point = new Vector3D();
			start_point.x = (this.mouseX);
			start_point.y = (this.mouseY);
			start_camera_point.x = base.scrollRect.x;
			start_camera_point.y = base.scrollRect.y;
			move_point.x = (this.mouseX);
			move_point.y = (this.mouseY);
			end_point.x = (this.mouseX);
			end_point.y = (this.mouseY);
			speed = null;
		}
		
		protected function onMouseMove(e:MouseEvent) : void {
			if (start_point != null) {
				base.setCamera(
					start_camera_point.x + (start_point.x - move_point.x),
					start_camera_point.y + (start_point.y - move_point.y)
				);
				speed = null;
			}
		}
		
		protected function onMouseUp(e:MouseEvent) : void {
			if (start_point != null) {
				speed = new Vector3D();
				speed.x = end_point.x - move_point.x;
				speed.y = end_point.y - move_point.y;
			}
			start_point = null;
		}
		
		protected function onMouseOut(e:MouseEvent) : void 
		{
			if (parent != null) {
				if (!CMath.includeRectPoint2(
					base.x, base.y, 
					base.width, base.height,
					mouseX,
					mouseY)) 
				{
					if (start_point != null) {
						speed = new Vector3D();
						speed.x = end_point.x - move_point.x;
						speed.y = end_point.y - move_point.y;
					}
					start_point = null;
				}
			} else {
				start_point = null;
			}
		}
	}
}

