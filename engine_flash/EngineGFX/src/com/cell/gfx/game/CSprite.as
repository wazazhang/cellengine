package com.cell.gfx.game
{	
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
		
		
		private var CurAnimate 		: uint = 0;
		private var CurFrame	 	: uint = 0;
		
//		-----------------------------------------------------------------------
		
		protected function init(
			canimates:CAnimates, 
			ccollides:CCollides,
			animateNames:Array, 
			frameAnimate:Array) : void
		{
			this.animates		= canimates;
			this.collides		= ccollides;
			this.AnimateNames	= animateNames;
			this.FrameAnimate	= frameAnimate;
		}
		
		protected function init2(spr:CSprite) : void
		{
			this.animates 		= spr.animates;
			this.collides 		= spr.collides;
			this.AnimateNames 	= spr.AnimateNames;
			this.FrameAnimate 	= spr.FrameAnimate;
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
		
		public function getFrameBounds(anim:int, frame:int) : CCD
		{
			return animates.getFrameBounds(FrameAnimate[anim][frame]);
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
		
		public function getAnimateCount() : uint {
			return FrameAnimate.length;
		}
		
		public function getFrameCount(anim:int) : uint {
			return FrameAnimate[anim].length;
		}
		
		public function getCurrentFrameCount() : uint {
			return FrameAnimate[CurAnimate].length;
		}
		
		public function getCurrentFrame() : uint {
			return CurFrame;
		}
		
		public function getCurrentAnimate() : uint {
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
		/**
		 * @return
		 *  0 : 无变化 <br>
		 * >0 : 动画+ <br>
		 * <0 : 动画- <br>
		 * */
		public function setCurrentAnimateName(anim_name:String) : int {
			var anim : int = findAnimateIndex(anim_name);
			if (anim >= 0) {
				return setCurrentAnimate(anim)
			}
			return 0;
		}
		
		/**
		 * @return
		 *  0 : 无变化 <br>
		 * >0 : 动画+ <br>
		 * <0 : 动画- <br>
		 * */
		public function setCurrentAnimate(anim:uint, cyc:Boolean=false) : int {
			if (anim != CurAnimate) {
				var old_anim : uint = CurAnimate;
				if (cyc) {
					this.CurAnimate = CMath.cycNum(0, anim,  getAnimateCount());
				}
				else if (anim >= getAnimateCount()) {
					this.CurAnimate = getAnimateCount()-1;
				} 
				else {
					this.CurAnimate = anim;
				}
				return CurAnimate - old_anim;
			}
			return 0;
		}
		
		/**
		 * @return
		 *  0 : 无变化 <br>
		 * >0 : 帧+ <br>
		 * <0 : 帧- <br>
		 * */
		public function setCurrentFrame(frame:uint, cyc:Boolean=false) : int {
			if (CurFrame != frame) {
				var old_frame : uint  = CurFrame;
				if (cyc) {
					this.CurFrame   = CMath.cycNum(0, frame, getCurrentFrameCount());
				}
				else if (frame >= getCurrentFrameCount()) {
					this.CurFrame = getCurrentFrameCount()-1;
				}
				else {
					this.CurFrame = frame;
				}
				return CurFrame - old_frame;
			}
			return 0;
		}
		
		/**
		 * @see setCurrentFrame
		 * @return 是否第一帧
		 */
		public function nextFrame() : int {
			return  setCurrentFrame(CurFrame+1, false);
		}
		
		/**
		 * @see setCurrentFrame
		 * @return 是否第一帧
		 */
		public function nextCycFrame() : int {
			return setCurrentFrame(CurFrame+1, true);
		}
		
		/**
		 * @see setCurrentFrame
		 * @return 是否第一帧
		 */
		public function prewFrame() : int {
			return setCurrentFrame(CurFrame-1, false);
		}
		
		/**
		 * @see setCurrentFrame
		 * @return 是否第一帧
		 */
		public function prewCycFrame() : int {
			return setCurrentFrame(CurFrame-1, true);
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
		
		
		public function render(g:IGraphics, x:int, y:int, anim:int, frame:int) : void 
		{
			if ( (anim < FrameAnimate.length) && (frame < FrameAnimate[anim].length) ) {
				animates.render(g, FrameAnimate[anim][frame], x, y);
			}
		}
		
		
		
		

		
	
	
	}
}



