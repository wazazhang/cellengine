package com.cell.ui
{
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	import com.cell.math.TVector2D;
	import com.cell.util.CMath;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import flash.geom.Vector3D;

	public class SingleViewPanel extends BasePanel
	{	
		public static const MODE_HORIZONTAL	: int = 0;
		public static const MODE_VERTICAL	: int = 1;

		/**拖拽多长距离才翻页*/
		public var drag_page_distance 		: int = 32;
		
		protected var start_point			: Vector3D;
		
		protected var start_camera_point 	: Vector3D = new Vector3D();
		
		protected var speed					: Number = 16;
		private var view_index 				: int = 0;
		
		protected var mode					: int = MODE_HORIZONTAL;
		
		private var next_camera_point		: Vector3D;
		
		public function SingleViewPanel(width:int, height:int, backcolor:int=0, backalpha:Number=0)
		{
			super(width, height, backcolor, backalpha);
			
			addEventListener(MouseEvent.MOUSE_DOWN,	onMouseDown);
			addEventListener(MouseEvent.MOUSE_MOVE,	onMouseMove);
			addEventListener(MouseEvent.MOUSE_UP, 	onMouseUp);
			addEventListener(MouseEvent.MOUSE_OUT, 	onMouseUp);
		}

		public function setMode(m:int) : void
		{
			var sx : int = 0;
			var sy : int = 0;
			this.mode = m;
			for (var i:int=0; i<numChildren; ++i) {
				var o:DisplayObject = getChildAt(i);
				o.x = sx;
				o.y = sy;
				if (mode == MODE_HORIZONTAL) {
					sx += o.width;
				} else if (mode == MODE_VERTICAL) {
					sy += o.height;
				}
			}
		}
		
		public function getMode() : int
		{
			return this.mode;	
		}
		public function getViewIndex() : int
		{
			return view_index;
		}
		
		protected function onMouseDown(e:MouseEvent) : void 
		{
			start_point = new Vector3D();
			start_point.x = (parent.mouseX);
			start_point.y = (parent.mouseY);	
			start_camera_point.x = scrollRect.x;
			start_camera_point.y = scrollRect.y;
			next_camera_point = null;
		}
		
		protected function onMouseMove(e:MouseEvent) : void {
			if (start_point != null) {
				setCamera(
					start_camera_point.x + (start_point.x - parent.mouseX),
					start_camera_point.y + (start_point.y - parent.mouseY)
				);
			}
		}
		
		public function setViewIndex(i:int) : void
		{
			if (i!=view_index && i>=0 && i<numChildren) {
				view_index = i;
				var no : DisplayObject = getChildAt(i);
				next_camera_point = new Vector3D(no.x, no.y);
				speed = no.width / 8;
				start_point = null;
			}
		}
		
		public function cancelDrag() : void
		{
			var no : DisplayObject = getChildAt(view_index);
			next_camera_point = new Vector3D(no.x, no.y);
			speed = no.width / 8;
			start_point = null;
		}
		
		protected function onMouseUp(e:MouseEvent) : void {
			if (start_point != null) {
				var dx : int = start_point.x - parent.mouseX;
				var dy : int = start_point.y - parent.mouseY;
				if (dx != 0 || dy != 0) {
					var co : DisplayObject = getChildAt(view_index);
					if (mode == MODE_HORIZONTAL && Math.abs(dx)>=drag_page_distance) {
						if (dx > 0 && view_index < numChildren-1) {
							view_index ++;
						} else if (dx < 0 && view_index>0) {
							view_index --;
						}
						speed = co.width / 8;
					}
					else if (mode == MODE_VERTICAL && Math.abs(dy)>=drag_page_distance) {
						if (dy > 0 && view_index < numChildren-1) {
							view_index ++;
						} else if (dy < 0 && view_index>0) {
							view_index --;
						}
						speed = co.height / 8;
					} 
					var no : DisplayObject = getChildAt(view_index);
					next_camera_point = new Vector3D(no.x, no.y);
				}
			}
			start_point = null;
		}
		
		override protected function update(e:Event) : void
		{				
			if (next_camera_point != null) {
				var v2d : TVector2D = new TVector2D(scrollRect.x, scrollRect.y);
				if (MathVector.moveTo(v2d, next_camera_point.x, next_camera_point.y, speed)) {
					next_camera_point = null;
				}			
				setCamera(v2d.getVectorX(), v2d.getVectorY());
			}	
		}
		

	
	
	}
}