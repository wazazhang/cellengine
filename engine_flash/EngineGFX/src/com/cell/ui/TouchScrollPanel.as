package com.cell.ui
{
	import com.cell.math.IVector2D;
	import com.cell.math.TVector2D;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Rectangle;
	import flash.geom.Vector3D;

	public class TouchScrollPanel extends BasePanel
	{
		protected var start_point : Vector3D;
		
		protected var move_point : Vector3D = new Vector3D();
		protected var end_point : Vector3D = new Vector3D();
		
		protected var start_camera_point : Vector3D = new Vector3D();
		
		protected var speed : Vector3D;
				
		public var touch_acc : Vector3D = new Vector3D();
		
		public function TouchScrollPanel(width:int, height:int)
		{
			super(width, height);

			addEventListener(MouseEvent.MOUSE_DOWN,	onMouseDown);
			addEventListener(MouseEvent.MOUSE_MOVE,	onMouseMove);
			addEventListener(MouseEvent.MOUSE_UP, 	onMouseUp);
			addEventListener(MouseEvent.MOUSE_OUT, 	onMouseUp);
			
			addEventListener(Event.ENTER_FRAME, update);
			touch_acc.x = 0.9;
			touch_acc.y = 0.9;
			
		}
		protected function onMouseDown(e:MouseEvent) : void {
			createLocalBounds();
			start_point = new Vector3D();
			start_point.x = (parent.mouseX);
			start_point.y = (parent.mouseY);
			start_camera_point.x = scrollRect.x;
			start_camera_point.y = scrollRect.y;
			move_point.x = (parent.mouseX);
			move_point.y = (parent.mouseY);
			end_point.x = (parent.mouseX);
			end_point.y = (parent.mouseY);
			speed = null;
		}
		
		protected function onMouseMove(e:MouseEvent) : void {
			if (start_point != null) {
				setCamera(
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
		
		protected function update(e:Event) : void
		{				
			end_point.x = (move_point.x);
			end_point.y = (move_point.y);
			move_point.x = (parent.mouseX);
			move_point.y = (parent.mouseY);
			
			if (speed != null) {
				if (int(speed.x) != 0 || int(speed.y) != 0) {
					moveCamera(speed.x, speed.y);
				}
				speed.x *= touch_acc.x;
				speed.y *= touch_acc.y;
			}
			
		}
		
		
	}
}