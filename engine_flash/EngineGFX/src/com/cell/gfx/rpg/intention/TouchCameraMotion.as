package com.cell.gfx.rpg.intention
{
	import com.cell.gfx.game.ICamera;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Vector3D;

	public class TouchCameraMotion
	{
		private var start_point 		: Vector3D;
		private var move_point 			: Vector3D = new Vector3D();
		private var end_point 			: Vector3D = new Vector3D();
		private var start_camera_point 	: Vector3D = new Vector3D();
		private var speed 				: Vector3D;
		public var touch_acc 			: Vector3D = new Vector3D();
		
		public function TouchCameraMotion()
		{
			touch_acc.x = 0.9;
			touch_acc.y = 0.9;
		}
		
		
		public function updateCamera(container:DisplayObject, camera:ICamera) : void
		{
			end_point.x = (move_point.x);
			end_point.y = (move_point.y);
			move_point.x = (container.mouseX);
			move_point.y = (container.mouseY);
			
			if (speed != null) {
				if (int(speed.x) != 0 || int(speed.y) != 0) {
					camera.move(speed.x, speed.y);
				}
				speed.x *= touch_acc.x;
				speed.y *= touch_acc.y;
			}
		}
		
		public function onDragStart(container:DisplayObject, camera:ICamera) : void
		{
			start_point = new Vector3D();
			start_point.x = (container.mouseX);
			start_point.y = (container.mouseY);
			start_camera_point.x = camera.x;
			start_camera_point.y = camera.y;
			move_point.x = (container.mouseX);
			move_point.y = (container.mouseY);
			end_point.x = (container.mouseX);
			end_point.y = (container.mouseY);
			speed = null;
		}
		
		public function onDragMove(container:DisplayObject, camera:ICamera) : void
		{
			if (start_point != null) {
				camera.setPos(
					start_camera_point.x + (start_point.x - move_point.x), 
					start_camera_point.y + (start_point.y - move_point.y));
				speed = null;
			}
		}	
		
		public function onDragOver(container:DisplayObject, camera:ICamera) : void
		{
			if (start_point != null) {
				speed = new Vector3D();
				speed.x = end_point.x - move_point.x;
				speed.y = end_point.y - move_point.y;
			}
			start_point = null;
		}
		
		
	}
}