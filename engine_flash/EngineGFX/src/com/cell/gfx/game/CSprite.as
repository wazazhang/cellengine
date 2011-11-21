package com.cell.gfx.game
{	
	import com.cell.util.CMath;
	
	import flash.display.DisplayObject;
	import flash.display.Sprite;
	
	public class CSprite
	{
		static public const CD_TYPE_MAP 	: int  	= 0;
		static public const CD_TYPE_ATK  	: int 	= 1;
		static public const CD_TYPE_DEF  	: int 	= 2;
		static public const CD_TYPE_EXT  	: int 	= 3;
		

		
//		----------------------------------------------------------------------
		
		public var enable_complex : Boolean = false;
		
		protected var animates 		: CAnimates;
		
		protected var collides 		: CCollides;
		
		/**String[]*/
		protected var AnimateNames	: Array;
		/**short[][]*/
		protected var FrameAnimate	: Array;
		protected var FrameCDMap	: Array;
		protected var FrameCDAtk	: Array;
		protected var FrameCDDef	: Array;
		protected var FrameCDExt	: Array;

		protected var FrameAlpha	: Array;
		protected var FrameRotate	: Array;
		protected var FrameScaleX	: Array;
		protected var FrameScaleY	: Array;

		
		private var CurAnimate 		: uint = 0;
		private var CurFrame	 	: uint = 0;
		
//		-----------------------------------------------------------------------
		
		protected function init(
			complexMode:Boolean,
			canimates:CAnimates, 
			ccollides:CCollides,
			animateNames: Array, 
			frameAnimate: Array,     
			frameAlpha	: Array,
			frameRotate	: Array,
			frameScaleX	: Array,
			frameScaleY	: Array,
			frameCDMap	: Array,
			frameCDAtk	: Array,
			frameCDDef	: Array,
			frameCDExt	: Array
		) : void
		{
			this.enable_complex	= complexMode;
			this.animates		= canimates;
			this.collides		= ccollides;
			
			this.AnimateNames	= animateNames;
			this.FrameAnimate	= frameAnimate;
			
			this.FrameAlpha		= frameAlpha;
			this.FrameRotate	= frameRotate;	
			this.FrameScaleX	= frameScaleX;	
			this.FrameScaleY	= frameScaleY;	

			this.FrameCDMap		= frameCDMap;
			this.FrameCDAtk		= frameCDAtk;
			this.FrameCDDef		= frameCDDef;
			this.FrameCDExt		= frameCDExt;
		}
		
		protected function init2(spr:CSprite) : void
		{
			this.enable_complex	= spr.enable_complex;
			
			this.animates 		= spr.animates;
			this.collides 		= spr.collides;
			
			this.AnimateNames 	= spr.AnimateNames;
			this.FrameAnimate 	= spr.FrameAnimate;
			
			this.FrameAlpha		= spr.FrameAlpha;
			this.FrameRotate	= spr.FrameRotate;	
			this.FrameScaleX	= spr.FrameScaleX;	
			this.FrameScaleY	= spr.FrameScaleY;	
			
			this.FrameCDMap		= spr.FrameCDMap;
			this.FrameCDAtk		= spr.FrameCDAtk;
			this.FrameCDDef		= spr.FrameCDDef;
			this.FrameCDExt		= spr.FrameCDExt;
		}
		
		public function copy() : CSprite
		{
			var ret : CSprite = new CSprite();
			ret.init2(this);
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
		
		public function getVisibleXCenter() : int {
			return animates.w_left + animates.w_width / 2;
		}
		
		public function getVisibleYCenter() : int {
			return animates.w_top + animates.w_height / 2;
		}
		
		public function getFrameImageBounds( anim:uint,  frame:uint) : CCD 
		{
			var bounds : CCD = CCD.createCDRect(CCD.CD_TYPE_RECT, 0,0,0,0);
			bounds.X1 = int.MAX_VALUE;
			bounds.Y1 = int.MAX_VALUE; 
			bounds.X2 = int.MIN_VALUE;
			bounds.Y2 = int.MIN_VALUE;
			if (anim < FrameAnimate.length && frame < FrameAnimate[anim].length)
			{
				var frameid : int = FrameAnimate[anim][frame];
				var count : int = animates.getComboFrameCount(frameid);
				for (var i:int=0; i<count; i++) {
					bounds.X1 = Math.min(bounds.X1, animates.getFrameX(frameid, i));
					bounds.Y1 = Math.min(bounds.Y1, animates.getFrameY(frameid, i));
					bounds.X2 = Math.max(bounds.X2, animates.getFrameX(frameid, i)+animates.getFrameW(frameid, i)-1);
					bounds.Y2 = Math.max(bounds.Y2, animates.getFrameY(frameid, i)+animates.getFrameH(frameid, i)-1);
				}				
			}
			return bounds;
		}

		//	------------------------------------------------------------------------------------------
		
		
		public function getFrameCDCount( anim:int,  frame:int,  type:int) : int {
			switch(type){
				case CD_TYPE_MAP:
					return collides.Frames[FrameCDMap[anim][frame]].length;
				case CD_TYPE_ATK:
					return collides.Frames[FrameCDAtk[anim][frame]].length;
				case CD_TYPE_DEF:
					return collides.Frames[FrameCDDef[anim][frame]].length;
				case CD_TYPE_EXT:
					return collides.Frames[FrameCDExt[anim][frame]].length;
			}
			return -1;
		}
		
		public function getFrameCD( anim:int,  frame:int,  type:int, sub:int) : CCD {
			switch(type){
				case CD_TYPE_MAP:
					return collides.getFrameCD(FrameCDMap[anim][frame], sub);
				case CD_TYPE_ATK:
					return collides.getFrameCD(FrameCDAtk[anim][frame], sub);
				case CD_TYPE_DEF:
					return collides.getFrameCD(FrameCDDef[anim][frame], sub);
				case CD_TYPE_EXT:
					return collides.getFrameCD(FrameCDExt[anim][frame], sub);
			}
			return null;
		}
		
		public function getFrameCDBounds(anim:int,  frame:int,  type:int) : CCD {
			var bounds : CCD = CCD.createCDRect(CCD.CD_TYPE_RECT, 0,0,0,0);
			bounds.X1 = int.MAX_VALUE;
			bounds.Y1 = int.MAX_VALUE; 
			bounds.X2 = int.MIN_VALUE;
			bounds.Y2 = int.MIN_VALUE;
			var count : int = getFrameCDCount(anim, frame, type);
			for (var i:int=0; i<count; i++) {
				var cd : CCD = getFrameCD( anim,  frame,  type, i);
				bounds.X1 = Math.min(bounds.X1, cd.X1);
				bounds.Y1 = Math.min(bounds.Y1, cd.Y1);
				bounds.X2 = Math.max(bounds.X2, cd.X2);
				bounds.Y2 = Math.max(bounds.Y2, cd.Y2);
			}
			return bounds;
		}
		
		public function getCurrentCD(type:int, sub:int ) : CCD {
			switch(type){
				case CD_TYPE_MAP:
					return collides.getFrameCD(FrameCDMap[CurAnimate][CurFrame], sub);
				case CD_TYPE_ATK:
					return collides.getFrameCD(FrameCDAtk[CurAnimate][CurFrame], sub);
				case CD_TYPE_DEF:
					return collides.getFrameCD(FrameCDDef[CurAnimate][CurFrame], sub);
				case CD_TYPE_EXT:
					return collides.getFrameCD(FrameCDExt[CurAnimate][CurFrame], sub);
			}
			return null;
		}
		
		public function getCDTop() : int {
			return collides.w_top;
		}
		public function getCDBotton() : int {
			return collides.w_bottom;
		}
		public function getCDLeft() : int {
			return collides.w_left;
		}
		public function getCDRight() : int {
			return collides.w_right;
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
		
//		--------------------------------------------------------------------------------------------
		
		public function getFrameAlpha(anim:int, frame:int) : Number
		{
			return FrameAlpha[anim][frame];
		}
		
		public function getFrameRotate(anim:int, frame:int) : Number
		{
			return FrameRotate[anim][frame];
		}
		
		public function getFrameScaleX(anim:int, frame:int) : Number
		{
			return FrameScaleX[anim][frame];
		}
		
		public function getFrameScaleY(anim:int, frame:int) : Number
		{
			return FrameScaleY[anim][frame];
		}
		
		public function addTransform(o:DisplayObject, anim:int, frame:int) : void
		{
			if (enable_complex) {
				o.alpha 	*= FrameAlpha[anim][frame];
				o.rotation 	+= FrameRotate[anim][frame];
				o.scaleX 	+= FrameScaleX[anim][frame];
				o.scaleY 	+= FrameScaleY[anim][frame];
			}
		}
		
		public function setTransform(o:DisplayObject, anim:int, frame:int) : void
		{
			if (enable_complex) {
				o.alpha 	= FrameAlpha[anim][frame];
				o.rotation 	= FrameRotate[anim][frame];
				o.scaleX 	= FrameScaleX[anim][frame];
				o.scaleY 	= FrameScaleY[anim][frame];
			}
		}

//		------------------------------------------------------------------------------------------
		
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
//			if ( (anim < FrameAnimate.length) && (frame < FrameAnimate[anim].length) ) {
				animates.render(g, FrameAnimate[anim][frame], x, y);
//			}
		}
		
		
		
		

		
	
	
	}
}



