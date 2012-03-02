package com.cell.ui.component
{	
	import com.cell.ui.BasePanel;
	import com.cell.ui.TouchScrollPanel;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.CMath;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import flash.geom.Vector3D;

	public class ScrollPan extends UIComponent
	{
		public var touch_acc 			: Vector3D = new Vector3D();
		
		internal var base 				: BasePanel;
		
		private var start_point 		: Vector3D;
		private var move_point 			: Vector3D = new Vector3D();
		private var end_point 			: Vector3D = new Vector3D();
		private var start_camera_point 	: Vector3D = new Vector3D();
		private var speed 				: Vector3D;
		
		
		private var touch_moved			: Boolean = false;
		
		public function ScrollPan(rect:UIRect, width:int, height:int)
		{
			super(rect);
			this.mouseChildren = true;
			this.mouseEnabled  = true;
			
			base = new BasePanel(width, height, 0, 0);
			super.addChild(base);
						
			resize(width, height, true);
			
			addEventListener(MouseEvent.MOUSE_DOWN,	onMouseDown);
			addEventListener(MouseEvent.MOUSE_MOVE,	onMouseMove);
			addEventListener(MouseEvent.MOUSE_UP, 	onMouseUp);
			addEventListener(MouseEvent.MOUSE_OUT, 	onMouseOut);
			touch_acc.x = 0.9;
			touch_acc.y = 0.9;
			
			this.addEventListener(Event.ENTER_FRAME, updateScroll);
		}
		
//		----------------------------------------------------------------------------
		
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
		
		public function removePanelChild(o:DisplayObject) : DisplayObject
		{
			return this.base.removeChild(o);
			updateScroll();
		}
		
		public function removePanelChildAt(index:int) : DisplayObject
		{
			return this.base.removeChildAt(index);
			updateScroll();
		}
		
		public function addChildEventListener(type:String, listener:Function, useCapture:Boolean=false, priority:int=0, useWeakReference:Boolean=false):void
		{
			for (var i:int=base.numChildren-1; i>=0; --i) {
				var o : * = base.getChildAt(i);
				o.addEventListener(type, listener, useCapture, priority, useWeakReference);
			}
		}
		
		public function removeChildEventListener(type:String, listener:Function, useCapture:Boolean=false):void
		{
			for (var i:int=base.numChildren-1; i>=0; --i) {
				var o : * = base.getChildAt(i);
				o.removeEventListener(type, listener, useCapture);
			}
		}
		
		

		
//		----------------------------------------------------------------------------
		
		
		public function isTouchMoved() : Boolean
		{
			return touch_moved;
		}

		protected function updateScroll(e:Event) : void
		{
			// update
			end_point.x = (move_point.x);
			end_point.y = (move_point.y);
			move_point.x = (this.mouseX);
			move_point.y = (this.mouseY);
			
			if (speed != null) {
				if (int(speed.x) != 0 || int(speed.y) != 0) {
					base.moveCamera(speed.x, speed.y);
					speed.x *= touch_acc.x;
					speed.y *= touch_acc.y;
				} else {
					speed = null;
				}
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
			
			touch_moved = false;
		}
		
		protected function onMouseMove(e:MouseEvent) : void {
			if (start_point != null) {
				var sc : Rectangle = base.getCamera();
				base.setCamera(
					start_camera_point.x + (start_point.x - move_point.x), 
					start_camera_point.y + (start_point.y - move_point.y));
				var dc : Rectangle = base.getCamera();
				if (sc.x != dc.x || sc.y != dc.y) {
					this.touch_moved = true;
				}
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