package com.cell.math
{
	import flash.geom.Vector3D;
	
	public class TVector2D implements IVector2D
	{
		public var v : Vector3D ;
		
		public function TVector2D(x:Number=0, y:Number=0) {
			this.v = new Vector3D(x, y, 0, 0);
		}
		
		public function addVectorX(dx:Number) : void {
			this.v.x += dx;
		}
		public function addVectorY(dy:Number) : void {
			this.v.y += dy;
		}
		public function setVectorX(x:Number) : void {
			this.v.x = x;
		}
		public function setVectorY(y:Number) : void {
			this.v.y = y;
		}
		public function getVectorX() : Number {
			return this.v.x;
		}
		public function getVectorY() : Number {
			return this.v.y;
		}
		
	}
}