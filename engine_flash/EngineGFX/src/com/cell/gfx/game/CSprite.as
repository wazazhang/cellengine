package com.cell.gfx.game
{	
	import com.cell.gfx.CGraphics;
	import com.cell.gfx.CImage;
	import com.cell.util.CMath;
	
	import flash.display.Sprite;
	
	public class CSprite
	{
//		----------------------------------------------------------------------
		
		protected var animates 		: CAnimates;
		
		protected var collides 		: CCollides;
		
		/**String[]*/
		protected var AnimateNames	: Array;
		/**short[][]*/
		protected var FrameAnimate	: Array;
		
		
		private var CurAnimate 		: int = 0;
		private var CurFrame	 	: int = 0;
		
//		-----------------------------------------------------------------------
		
		protected function init(
			canimates:CAnimates, 
			ccollides:CCollides,
			animateNames:Array, 
			frameAnimate:Array) : void
		{
			this.animates = canimates;
			this.collides = ccollides;
			this.AnimateNames = animateNames;
			this.FrameAnimate = frameAnimate;
//			this.cg = new CGraphicsDisplay(graphics);
//			this.repaint();
		}
		
		public function copy() : CSprite
		{
			var ret : CSprite = new CSprite();
			ret.init(animates, collides, AnimateNames, FrameAnimate);
			return ret;
		}
		
		public function  getAnimates() : CAnimates{
			return animates;
		}
		
		public function  getCollides() : CCollides{
			return collides;
		}
		
		//	------------------------------------------------------------------------------------------
		
		public function getFrameImageCount(anim:int, frame:int) : int {
			return animates.Frames[FrameAnimate[anim][frame]].length;
		}
		
		public function getFrameImage(anim:int, frame:int, part:int) : CImage {
			return animates.getFrameImage(FrameAnimate[anim][frame], part);
		}
		
		public function getFrameImageX(anim:int, frame:int, part:int) : int {
			return animates.getFrameX(FrameAnimate[anim][frame], part);
		}
		
		public function getFrameImageY(anim:int, frame:int, sub:int) : int {
			return animates.getFrameY(FrameAnimate[anim][frame], sub);
		}
		
		public function getFrameImageWidth(anim:int, frame:int, part:int) : int {
			return animates.getFrameW(FrameAnimate[anim][frame], part);
		}
		
		public function getFrameImageHeight(anim:int, frame:int, part:int) : int {
			return animates.getFrameH(FrameAnimate[anim][frame], part);
		}
		
		public function getFrameImageTransform(anim:int, frame:int, part:int) : int {
			return animates.getFrameTransform(FrameAnimate[anim][frame], part);
		}
		
		public function getCurrentImage(part:int) : CImage {
			return animates.getFrameImage(FrameAnimate[CurAnimate][CurFrame], part);
		}
		
		//	------------------------------------------------------------------------------------------
		
		public function getVisibleTop() : int {
			return animates.w_top;
		}
		public function getVisibleBotton() : int {
			return animates.w_bottom;
		}
		public function getVisibleLeft() : int {
			return animates.w_left;
		}
		public function getVisibleRight() : int {
			return animates.w_right;
		}
		public function getVisibleHeight() : int {
			return animates.w_height;
		}
		public function getVisibleWidth() : int {
			return animates.w_width;
		}
		
		//	------------------------------------------------------------------------------------------
		
		public function findAnimateIndex(animate_name:String) : int {
			if (AnimateNames!=null) {
				for (var i:int = 0; i<AnimateNames.length; i++) {
					if (animate_name == (AnimateNames[i])) {
						return i;
					}
				}
			}
			return -1;
		}
		
		public function getAnimateName(anim:int) : String {
			return AnimateNames[anim];
		}
		
		public function getAnimateCount() : int {
			return FrameAnimate.length;
		}
		
		public function getFrameCount(anim:int) : int {
			return FrameAnimate[anim].length;
		}
		
		public function getCurrentFrameCount() : int {
			return FrameAnimate[CurAnimate].length;
		}
		
		public function getCurrentFrame() : int {
			return CurFrame;
		}
		
		public function getCurrentAnimate() : int {
			return CurAnimate;
		}
		
		public function getFrameAnimateData(anim:int, frame:int) : int {
			return FrameAnimate[anim][frame];
		}
		
		public function isEndFrame() : Boolean {
			return CurFrame == FrameAnimate[CurAnimate].length - 1;
		}
		
		public function isBeginFrame() : Boolean {
			return CurFrame == 0;
		}
		
		
		
		//	------------------------------------------------------------------------------------------
		
		
		public function setCurrentAnimate(anim:int) : int {
			return setCurrentFrame(anim, 0);
		}
		
		/**
		 * 
		 * @return
		 *  0 : normal <br>
		 *  1 : big than frame <br>
		 * -1 : less than frame <br>
		 *  2 : big than anim <br>
		 * -2 : less than anim <br>
		 * */
		public function setCurrentFrame(anim:uint, frame:uint, restrict:Boolean=false, cyc:Boolean=false) : int {
			if (anim != CurAnimate || CurFrame != frame) {
				if (cyc) {
					this.CurAnimate = CMath.cycNum(0, anim,  getAnimateCount());
					this.CurFrame   = CMath.cycNum(0, frame, getCurrentFrameCount());
				}
				else if (restrict) {
					if (anim >= getAnimateCount()) {
						this.CurAnimate = getAnimateCount()-1;
					}
					if (frame >= getCurrentFrameCount()) {
						this.CurFrame = getCurrentFrameCount()-1;
					}
				}
//				repaint();
			}
			return 0;
		}
		
		/**
		 * @return 是否最后一帧
		 */
		public function nextFrame() : Boolean {
			setCurrentFrame(CurAnimate, CurFrame+1, true);
			return isEndFrame();
		}
		
		/**
		 * @return 是否最后一帧
		 */
		public function nextCycFrame() : Boolean {
			setCurrentFrame(CurAnimate, CurFrame+1, false, true);
			return isEndFrame();
		}
		
		/**
		 * @return 是否第一帧
		 */
		public function prewFrame() : Boolean {
			setCurrentFrame(CurAnimate, CurFrame-1, true);
			return isBeginFrame();
		}
		
		/**
		 * @return 是否第一帧
		 */
		public function prewCycFrame() : Boolean {
			setCurrentFrame(CurAnimate, CurFrame-1, false, true);
			return isBeginFrame();
		}
		
		
		//	----------------------------------------------------------------------------------------------------
		
		//	----------------------------------------------------------------------------------------------------
		
//		public function repaint() : void 
//		{
//			graphics.clear();
//			graphics.beginFill(0x00ff00, 1);
//			graphics.drawRect(-100, -100, 200, 200);
//			graphics.endFill();
//			render(cg, 0, 0, CurAnimate, CurFrame);
//			graphics.moveTo( 0, 0);
//			if (DEBUG) {
//				graphics.lineStyle(2, 0xffff0000, 1);
//				graphics.lineTo(-8, 0); graphics.moveTo( 0, 0);
//				graphics.lineTo( 8, 0); graphics.moveTo( 0, 0);
//				graphics.lineTo( 0,-8); graphics.moveTo( 0, 0);
//				graphics.lineTo( 0, 8); graphics.moveTo( 0, 0);
//				graphics.endFill();
//			}
//			trace("a="+CurAnimate+" f="+CurFrame);
//		}
		
		
		public function render(g:CGraphics, x:int, y:int, anim:int, frame:int) : void 
		{
			if ( (anim < FrameAnimate.length) && (frame < FrameAnimate[anim].length) ) {
				animates.render(g, FrameAnimate[anim][frame], x, y);
			}
		}
		
		
		
		

		
	
	
	}
}



