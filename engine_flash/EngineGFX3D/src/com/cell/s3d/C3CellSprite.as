package com.cell.s3d
{
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.geom.Rectangle;
	
	import starling.display.Sprite;

	public class C3CellSprite extends Sprite implements IVector2D
	{
		protected var _spr 	: CSprite;
		protected var _buff	: CSpriteBuffer;
		protected var _bitmap	: Bitmap;
		
		public function C3CellSprite(spr : CSprite)
		{
			this._spr = spr;
		}
		
		public function getCSprite() : CSprite
		{
			return _spr;
		}
		
		public function getSprBuffer() : CSpriteBuffer
		{
			return this._buff;
		}
		
//		------------------------------------------------------------------------------------------------------
		
		public function update() : void 
		{	

		}
		
//		public function render() : void
//		{
//			var ca : int = _spr.getCurrentAnimate();
//			var cf : int = _spr.getCurrentFrame();
//			var cr : Rectangle	= _buff.getFrameBound(ca, cf);
//			var cb : BitmapData	= _buff.getFrameBuffer(ca, cf);
//			
//			_bitmap.x = cr.x;
//			_bitmap.y = cr.y;
//			_bitmap.bitmapData = cb;
//			_spr.setTransform(this, ca, cf);
//		}
		
		
//		------------------------------------------------------------------------------------------------------
		
		public function getCurrentFrame() : uint {
			return _spr.getCurrentFrame();
		}
		
		public function getCurrentAnimate() : uint {
			return _spr.getCurrentAnimate();
		}
		
		public function isEndFrame() : Boolean {
			return _spr.isEndFrame();
		}
		
		public function isBeginFrame() : Boolean {
			return _spr.isBeginFrame();
		}
		
		
		public function setCurrentAnimateName(anim_name:String) : int {
			return _spr.setCurrentAnimateName(anim_name);
		}
		
		public function setCurrentAnimate(anim:uint, cyc:Boolean=false) : int {
			return _spr.setCurrentAnimate(anim, cyc);
		}
		
		public function setCurrentFrame(frame:uint, cyc:Boolean=false) : int {
			return _spr.setCurrentFrame(frame, cyc);
		}
		
		public function nextFrame() : int {
			return _spr.nextFrame();
		}
		
		public function nextCycFrame() : int {
			return _spr.nextCycFrame();
		}
		
		public function prewFrame() : int {
			return _spr.prewFrame();
		}
		
		public function prewCycFrame() : int {
			return _spr.prewCycFrame();
		}
	

		
//		--------------------------------------------------------------------------------------------------------
		
		public function move(dx:Number, dy:Number) : void {
			this.x += dx;
			this.y += dy;
		}
		
		/**
		 * 通过极坐标来移动
		 * @param angle 弧度
		 * @param distance
		 */
		public function movePolar(angle:Number, distance:Number) : void {
			MathVector.movePolar(this, angle, distance);
		}
		
		/**
		 * 向目标移动
		 * @param dx
		 * @param dy
		 * @return 是否到达目标点
		 */
		public function moveTo(x:Number, y:Number, distance:Number) : Boolean {
			return MathVector.moveTo(this, x, y, distance);
		}
		
		
		public function addVectorX(dx:Number) : void {
			this.x += dx;
		}
		public function addVectorY(dy:Number) : void {
			this.y += dy;
		}
		public function setVectorX(x:Number) : void {
			this.x = x;
		}
		public function setVectorY(y:Number) : void {
			this.y = y;
		}
		public function getVectorX() : Number {
			return this.x;
		}
		public function getVectorY() : Number {
			return this.y;
		}
	}
	
}