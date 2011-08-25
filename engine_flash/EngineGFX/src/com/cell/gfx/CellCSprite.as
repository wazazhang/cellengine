package com.cell.gfx
{
	import com.cell.gameedit.ResourceEvent;
	import com.cell.gfx.game.CGraphicsDisplay;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.IGraphics;
	import com.cell.gfx.game.IImageObserver;
	import com.cell.math.IVector2D;
	import com.cell.math.MathVector;
	
	import flash.display.Sprite;

	public class CellCSprite extends Sprite implements IImageObserver, IVector2D
	{
		private var spr		: CSprite;
		private var cg		: IGraphics;
		
		public function CellCSprite(spr:CSprite)
		{
			this.cg = new CGraphicsDisplay(graphics);			
			this.spr = spr;
			this.repaint();
			this.spr.getAnimates().getImages().addImageObserver(this);
		}
		
		public function get src() : CSprite
		{
			return spr;
		}
		
		public function getCSprite() : CSprite
		{
			return spr;
		}
		
		public function repaint() : void
		{
			graphics.clear();
			spr.render(cg, 0, 0, spr.getCurrentAnimate(), spr.getCurrentFrame());
			graphics.moveTo( 0, 0);
//			if (DEBUG) {
//				graphics.lineStyle(1, 0xffff0000, 1);
//				graphics.lineTo(-8, 0); graphics.moveTo( 0, 0);
//				graphics.lineTo( 8, 0); graphics.moveTo( 0, 0);
//				graphics.lineTo( 0,-8); graphics.moveTo( 0, 0);
//				graphics.lineTo( 0, 8); graphics.moveTo( 0, 0);
//				graphics.endFill();
//			}
		}
		
		public function imagesLoaded(e:ResourceEvent) : void
		{
			//			trace(e.type + " : " + e.images_set.Name);
			repaint();
		}
		
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