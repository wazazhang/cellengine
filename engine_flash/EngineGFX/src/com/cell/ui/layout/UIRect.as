package com.cell.ui.layout
{
	import com.cell.gfx.game.CGraphicsBitmap;
	import com.cell.gfx.game.CGraphicsBitmapBuffer;
	import com.cell.gfx.game.Transform;
	import com.cell.util.ImageUtil;
	import com.cell.util.Util;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Graphics;
	import flash.geom.Rectangle;

	public class UIRect extends Bitmap
	{
		
		public static const IMAGE_STYLE_NULL	 		: int		= 0;
		public static const IMAGE_STYLE_COLOR 		: int		= 1;
		public static const IMAGE_STYLE_ALL_9 		: int		= 2;
		public static const IMAGE_STYLE_ALL_8 		: int		= 3;
		public static const IMAGE_STYLE_H_012 		: int		= 4; 
		public static const IMAGE_STYLE_V_036 		: int		= 5;
		public static const IMAGE_STYLE_HLM			: int		= 6;
		public static const IMAGE_STYLE_VTM			: int		= 7;
		public static const IMAGE_STYLE_BACK_4 		: int		= 8;
		public static const IMAGE_STYLE_BACK_4_CENTER	: int		= 9;
		

//		------------------------------------------------------------------------------------------------------------------------------

		// image layout
		protected var style 		: int		= 0;
		protected var color		: uint		= 0;
		protected var clipBorder	: uint		= 0;
		
		private var BorderT 	: BitmapData;
		private var BorderB 	: BitmapData;
		private var BorderL 	: BitmapData;
		private var BorderR 	: BitmapData;
		private var BackImage 	: BitmapData;
		private var BorderTL 	: BitmapData;
		private var BorderTR 	: BitmapData;
		private var BorderBL 	: BitmapData;
		private var BorderBR 	: BitmapData;
		
//		------------------------------------------------------------------------------------------------------------------------------
		
//		------------------------------------------------------------------------------------------------------------------------------
		
		public function UIRect(style:int=IMAGE_STYLE_COLOR, color:uint=0xff808080)
		{
			this.color = color;
			this.style = style;
		}
		
		public function setColor(color:uint) : void
		{
			this.color = color;		
		}
		
		public function copy() : UIRect
		{
			var ret : UIRect = new UIRect();
			ret.style 		= this.style;	
			ret.color		= this.color;
			ret.clipBorder	= this.clipBorder;
			ret.bitmapData	= this.bitmapData;
			
			ret.BorderT 	= this.BorderT;
			ret.BorderB 	= this.BorderB;
			ret.BorderL 	= this.BorderL;
			ret.BorderR 	= this.BorderR;
			ret.BackImage 	= this.BackImage;
			ret.BorderTL 	= this.BorderTL;
			ret.BorderTR 	= this.BorderTR;
			ret.BorderBL 	= this.BorderBL;
			ret.BorderBR 	= this.BorderBR;
			return ret;
		}
		
		public function getStyle() : int
		{
			return this.style;
		}
		
		public function getClipBorder() : int
		{
			return this.clipBorder;
		}
//		------------------------------------------------------------------------------------------------------------------------------
		
		public function getBorderT() : BitmapData {
			return BorderT;
		}
		public function getBorderB() : BitmapData {
			return BorderB;
		}
		public function getBorderL() : BitmapData {
			return BorderL;
		}
		public function getBorderR() : BitmapData {
			return BorderR;
		}
		public function getBackImage() : BitmapData {
			return BackImage;
		}
		public function getBorderTL() : BitmapData {
			return BorderTL;
		}
		public function getBorderTR() : BitmapData {
			return BorderTR;
		}
		public function getBorderBL() : BitmapData {
			return BorderBL;
		}
		public function getBorderBR() : BitmapData {
			return BorderBR;
		}

		
		//	------------------------------------------------------------------------------------------------------------------------------
		
		
		/**
		 * @param images
		 * <pre>
		 * Rect
		 * 
		 * TopLeft----------Top---------TopRight 
		 * |-----------------------------------| 
		 * Left---------Background---------Right 
		 * |-----------------------------------| 
		 * BottomLeft-----Bottom-----BottomRight 
		 * 
		 * images[0] images[1] images[2]         
		 * images[3] images[4] images[5]         
		 * images[6] images[7] images[8]         
		 * 
		 * 0,1,2 可以用来横向平铺
		 * 0,3,6 可以用来纵向平铺
		 * 4 可以用来缩放
		 * </pre>
		 */
		public function setImages(
			style:int, 
			borderTL 	: BitmapData,
			borderT 	: BitmapData,
			borderTR 	: BitmapData,
			borderL 	: BitmapData,
			backImage 	: BitmapData,
			borderR 	: BitmapData,
			borderBL 	: BitmapData,
			borderB 	: BitmapData,
			borderBR 	: BitmapData) : UIRect
		{
			this.BorderTL = borderTL; 
			this.BorderT  = borderT; 
			this.BorderTR = borderTR;
			this.BorderL  = borderL;
			this.BackImage = backImage; 
			this.BorderR  = borderR;
			this.BorderBL = borderBL; 
			this.BorderB  = borderB; 
			this.BorderBR = borderBR;
			
			this.style		= style;
			
			switch(style)
			{
				case IMAGE_STYLE_COLOR:
					createBuffer(1, 1);
					break;
				case IMAGE_STYLE_NULL:
					createBuffer(1, 1);
					break;
				
				case IMAGE_STYLE_ALL_8:
					createBuffer(
						BorderL.width+borderR.width,
						BorderT.height+borderB.height
					);
					break;
				
				case IMAGE_STYLE_ALL_9:
					createBuffer(
						BorderL.width+BackImage.width+borderR.width,
						BorderT.height+BackImage.height+borderB.height
					);
					break;
				
				case IMAGE_STYLE_H_012:
					createBuffer(
						BorderTL.width+BorderT.width+BorderTR.width,
						BorderT.height
					);
					break;
				
				case IMAGE_STYLE_V_036:
					createBuffer(
						BorderL.width,
						BorderTL.height+BorderL.height+BorderBL.height
					);
					break;
				
				case IMAGE_STYLE_HLM:
					createBuffer(
						BorderL.width+BackImage.width+borderL.width,
						BorderT.height+BackImage.height+borderB.height
					);
					break;
				case IMAGE_STYLE_VTM:
					createBuffer(
						BorderL.width+BackImage.width+borderR.width,
						BorderT.height+BackImage.height+borderT.height
					);
					break;
				
				case IMAGE_STYLE_BACK_4:
					createBuffer(
						BackImage.width,
						BackImage.height
					);
					break;
				
				case IMAGE_STYLE_BACK_4_CENTER:
					createBuffer(
						BackImage.width,
						BackImage.height
					);
					break;
			}
			return this;
		}
		
		
		public function setImagesClip(src:BitmapData, style:int, clipsize:int) : UIRect
		{
			this.clipBorder = clipsize;
			setImagesClipBorder(src, style, clipsize, clipsize, clipsize, clipsize);
			return this;
		}
		
		
		
		/**将一张图片按相等的上下左右切成9格*/
		public function setImagesClip9(src:BitmapData, clipsize:int) : UIRect
		{
			setImagesClipBorder(src, 
					IMAGE_STYLE_ALL_9, clipsize, clipsize, clipsize, clipsize);
			return this;
		}
		
		/**将一张图片按相等的上下左右切成9格*/
		public function setImagesClip012(src:BitmapData, clipsize:int) : UIRect
		{
			setImagesClipBorder(src, 
				IMAGE_STYLE_H_012, clipsize, clipsize, clipsize, clipsize);
			return this;
		}
		
		/**将一张图片按相等的上下左右切成9格*/
		public function setImagesClip036(src:BitmapData, clipsize:int) : UIRect
		{
			setImagesClipBorder(src, 
				IMAGE_STYLE_V_036, clipsize, clipsize, clipsize, clipsize);
			return this;
		}
		
		/**将一张图片上下左右切成9格*/
		public function setImagesClipBorder(src:BitmapData, style:int, 
											L:int, R:int, T:int, B:int) : UIRect
		{
			var BorderTL	: BitmapData = null;
			var BorderT		: BitmapData = null;
			var BorderTR 	: BitmapData = null;
			var BorderL  	: BitmapData = null;
			var BackImage 	: BitmapData = null;
			var BorderR  	: BitmapData = null;
			var BorderBL	: BitmapData = null;
			var BorderB		: BitmapData = null;
			var BorderBR	: BitmapData = null;
			
			if (src != null)
			{
				var W : int = src.width;
				var H : int = src.height;
				
				switch(style)
				{
					
					case IMAGE_STYLE_ALL_9:
						BackImage 	= ImageUtil.subImage(src, L, T, W - L - R, H - T - B);
					case IMAGE_STYLE_ALL_8:
						BorderTL 	= ImageUtil.subImage(src, 0, 0, L, T);
						BorderT 	= ImageUtil.subImage(src, L, 0, W - L - R, T);
						BorderTR 	= ImageUtil.subImage(src, W - R, 0, R, T);
						BorderL 	= ImageUtil.subImage(src, 0, T, L, H - T - B);
						BorderR 	= ImageUtil.subImage(src, W - R, T, R, H - T - B);
						BorderBL 	= ImageUtil.subImage(src, 0, H - B, L, B);
						BorderB 	= ImageUtil.subImage(src, L, H - B, W - L - R, B);
						BorderBR 	= ImageUtil.subImage(src, W - R, H - B, R, B);
						break;
					
					case IMAGE_STYLE_H_012:
						BorderTL	= ImageUtil.subImage(src, 0, 0, L, H);
						BorderT		= ImageUtil.subImage(src, L, 0, W - R - L, H);
						BorderTR 	= ImageUtil.subImage(src, W - R, 0, R, H);
						break;
					
					case IMAGE_STYLE_V_036:
						BorderTL 	= ImageUtil.subImage(src, 0, 0, W, T);
						BorderL 	= ImageUtil.subImage(src, 0, T, W, H - T - B);
						BorderBL 	= ImageUtil.subImage(src, 0, H - B, W, B);
						break;
					
					case IMAGE_STYLE_HLM:
						BorderTL 	= ImageUtil.subImage(src, 0, 0, L, T);
						BorderT 	= ImageUtil.subImage(src, L, 0, W - L, T);
						BorderL 	= ImageUtil.subImage(src, 0, T, L, H - T - B);
						BackImage 	= ImageUtil.subImage(src, L, T, W - L, H - T - B);
						BorderBL 	= ImageUtil.subImage(src, 0, H - B, L, B);
						BorderB 	= ImageUtil.subImage(src, L, H - B, W - L, B);
						
						BorderTR	= ImageUtil.transformImage(BorderTL, Transform.TRANS_H);
						BorderR		= ImageUtil.transformImage(BorderL, Transform.TRANS_H);
						BorderBR	= ImageUtil.transformImage(BorderBL, Transform.TRANS_H);
						
						
						break;
					case IMAGE_STYLE_VTM:
						BorderTL 	= ImageUtil.subImage(src, 0, 0, L, T);
						BorderT 	= ImageUtil.subImage(src, L, 0, W - L - R, T);
						BorderTR 	= ImageUtil.subImage(src, W - R, 0, R, T);
						BorderL 	= ImageUtil.subImage(src, 0, T, L, H - T);
						BackImage 	= ImageUtil.subImage(src, L, T, W - L - R, H - T);
						BorderR 	= ImageUtil.subImage(src, W - R, T, R, H - T);

						BorderBL	= ImageUtil.transformImage(BorderTL, Transform.TRANS_H);
						BorderB		= ImageUtil.transformImage(BorderT, Transform.TRANS_H);
						BorderBR	= ImageUtil.transformImage(BorderTR, Transform.TRANS_H);
						break;
					
					
					case IMAGE_STYLE_BACK_4:
					case IMAGE_STYLE_BACK_4_CENTER:
						BackImage 	= src;
						break;
				}
				this.style = style;
				
				setImages(style, 
					BorderTL, BorderT,   BorderTR, 
					BorderL,  BackImage, BorderR, 
					BorderBL, BorderB,   BorderBR);
			}		
			return this;
		}
		
		
//		---------------------------------------------------------------------------------------------------------------
		
		
		public function getTopClip() : int {
			if (BorderTL != null) {
				return BorderTL.height;
			}
			return 0;
		}
		
		public function getBottomClip() : int {
			if (BorderBL != null) {
				return BorderBL.height;
			}
			return 0;
		}
		
		
		public function getLeftClip() : int {
			if (BorderTL != null) {
				return BorderTL.width;
			}
			return 0;
		}
		
		public function getRightClip() : int {
			if (BorderTR != null) {
				return BorderTR.width;
			}
			return 0;
		}
		
		
//		---------------------------------------------------------------------------------------------------------------
		
		protected function resetBuffer(W:int, H:int) : void
		{
			var ret : BitmapData;
			
			if (style == IMAGE_STYLE_COLOR || style == IMAGE_STYLE_ALL_8) 
			{
				ret  = new BitmapData(W, H, true, color);
			} 
			else
			{
				ret  = new BitmapData(W, H, true, 0);
			}
			
			var g : CGraphicsBitmap = new CGraphicsBitmap(ret);
//			trace(style);
			switch(style)
			{
				case IMAGE_STYLE_ALL_9:
					renderAll9(g, W, H);
					break;
				
				case IMAGE_STYLE_ALL_8:
					renderAll8(g, W, H);
					break;
				
				case IMAGE_STYLE_H_012:
					renderH012(g, W, H);
					break;
				
				case IMAGE_STYLE_V_036:
					renderV036(g, W, H);
					break;
				
				
				case IMAGE_STYLE_HLM:
					renderHLM(g, W, H);
					break;
				case IMAGE_STYLE_VTM:
					renderVTM(g, W, H);
					break;
				
				
				case IMAGE_STYLE_BACK_4:
					renderBack4(g, W, H);
					break;
				
				case IMAGE_STYLE_BACK_4_CENTER:
					renderBack4Center(g, W, H);
					break;
			}
			this.bitmapData = ret;
		}
		
		public function createBuffer(W:int, H:int) : BitmapData
		{
			if (width != W || height!=H) 
			{
				resetBuffer(W, H);
				return bitmapData;
			}
			return this.bitmapData;
		}
		
		public function initBuffer(w:int, h:int) : UIRect
		{
			this.createBuffer(w, h);
			return this;
		}
		
		protected function render0123_5678(g : CGraphicsBitmap, W:int, H:int) : void
		{
			
			g.drawBitmapDataRound(BorderL,
				0, 
				BorderTL.height,
				BorderL.width, 
				H - BorderTL.height - BorderBL.height,
				false);
			
			g.drawBitmapDataRound(BorderR,
				W - BorderR.width, 
				BorderTR.height, 
				BorderR.width, 
				H - BorderTR.height - BorderBR.height, 
				false);
			
			g.drawBitmapDataRound(BorderT,
				BorderTL.width, 
				0, 
				W-BorderTL.width-BorderTR.width,
				BorderT.height,
				false);
			
			g.drawBitmapDataRound(BorderB,
				BorderBL.width, 
				H - BorderB.height, 
				W - BorderBL.width - BorderBR.width, 
				BorderB.height,
				false);
			
			g.drawBitmapData(BorderTL, 
				0, 
				0,
				false);
			
			g.drawBitmapData(BorderBL, 
				0, 
				H - BorderBL.height,
				false);
			
			g.drawBitmapData(BorderTR, 
				W - BorderTR.width, 
				0,
				false);
			
			g.drawBitmapData(BorderBR, 
				W - BorderBR.width,
				H - BorderBR.height,
				false);
			
			
			
			
		}
		
		protected function renderAll9(g : CGraphicsBitmap, W:int, H:int) : void
		{
			var mx : int = BorderL.width;
			var my : int = BorderT.height;
			var mw : int = W - BorderL.width - BorderR.width;
			var mh : int = H - BorderT.height - BorderB.height;
			
			if ( (mw > 0) && (mh > 0))
			{
				g.drawBitmapDataRound(BackImage, mx, my, mw, mh, false);
			}
			
			render0123_5678(g, W, H);
		}
		
		
		protected function renderAll8(g : CGraphicsBitmap, W:int, H:int) : void
		{
			var mx : int = BorderL.width;
			var my : int = BorderT.height;
			var mw : int = W - BorderL.width - BorderR.width;
			var mh : int = H - BorderT.height - BorderB.height;
			
			if ( (mw > 0) && (mh > 0))
			{
				
			}
			
			render0123_5678(g, W, H);
		}
		
		protected function renderH012(g : CGraphicsBitmap, W:int, H:int) : void
		{
			var bl : int = BorderTL.width;
			var br : int = BorderTR.width;
			//画血条时可能会用到，当血条长度小于左右块长度
			if (bl + br >= W) {
				var clip_scale : Number = W / Number(bl + br);
				var wl : int = (bl*clip_scale);
				var wr : int = (br*clip_scale);
				g.drawBitmapDataScale(BorderTL, 
					0, 0, wl, H);
				g.drawBitmapDataScale(BorderTR, 
					wl, 0, wr, H);
			} else {
				g.drawBitmapDataRoundH(BorderT,    bl, 0, W - bl - br, H);
				g.drawBitmapDataRegion(BorderTL,    0, 0,          bl, H, false);
				g.drawBitmapDataRegion(BorderTR, W-br, 0,          br, H, false);
			}
		}
		
		protected function renderV036(g : CGraphicsBitmap, W:int, H:int) : void
		{
			var bt : int = BorderTL.height;
			var bb : int = BorderBL.height;
			//画血条时可能会用到，当血条长度小于左右块长度
			if (bt + bb >= H) {
				var clip_scale : Number = H / Number(bt + bb);
				var ht : int = (bt*clip_scale);
				var hb : int = (bb*clip_scale);
				g.drawBitmapDataScale(BorderTL, 
					0, 0, W, ht);
				g.drawBitmapDataScale(BorderBL, 
					0, ht, W, hb);
			} else {
				g.drawBitmapDataRoundV(BorderL,  0,   bt, W, H - bt - bb);
				g.drawBitmapDataRegion(BorderTL, 0,    0, W, bt, false);
				g.drawBitmapDataRegion(BorderBL, 0, H-bb, W, bb, false);
			}
		}
		
		protected function renderHLM(g : CGraphicsBitmap, W:int, H:int) : void
		{
			var mx : int = BorderL.width;
			var my : int = BorderT.height;
			var mw : int = W - BorderL.width  - BorderL.width;
			var mh : int = H - BorderT.height - BorderB.height;
			
			if ( (mw > 0) && (mh > 0))
			{
				g.drawBitmapDataRound(BackImage, mx, my, mw, mh, false);
			}
			
			render0123_5678(g, W, H);
		}
		
		protected function renderVTM(g : CGraphicsBitmap, W:int, H:int) : void
		{
			var mx : int = BorderL.width;
			var my : int = BorderT.height;
			var mw : int = W - BorderL.width - BorderR.width;
			var mh : int = H - BorderT.height - BorderT.height;
			
			if ( (mw > 0) && (mh > 0))
			{
				g.drawBitmapDataRound(BackImage, mx, my, mw, mh, false);
			}
			
			render0123_5678(g, W, H);
		}
		/*
		protected function render012356(g : CGraphicsBitmap, W:int, H:int) : void
		{
			g.drawBitmapDataRound(BorderL,
				0, 
				BorderTL.height,
				BorderL.width, 
				H - BorderTL.height - BorderBL.height,
				false);
			
			g.drawBitmapDataRoundT(BorderL,
				W - BorderL.width, 
				BorderL.height, 
				BorderL.width, 
				H - BorderTL.height - BorderBL.height, 
				Transform.TRANS_H,
				false);
			
			g.drawBitmapDataRound(BorderT,
				BorderTL.width, 
				0, 
				W-BorderTL.width-BorderTL.width,
				BorderT.height,
				false);
			
			g.drawBitmapDataRound(BorderB,
				BorderBL.width, 
				H - BorderB.height, 
				W - BorderBL.width - BorderBL.width, 
				BorderB.height,
				false);
			
			
			g.drawBitmapData(BorderTL, 
				0, 
				0,
				false);
			
			g.drawBitmapData(BorderBL, 
				0, 
				H - BorderBL.height,
				false);
			
			g.drawBitmapDataT(BorderTL, 
				W - BorderTL.width, 
				0,
				Transform.TRANS_H,
				false);
			
			g.drawBitmapDataT(BorderBL, 
				W - BorderBL.width,
				H - BorderBL.height,
				Transform.TRANS_H,
				false);
			
			
			
		}
		
		protected function render012345(g : CGraphicsBitmap, W:int, H:int) : void
		{
			
			g.drawBitmapDataRound(BorderL,
				0, 
				BorderTL.height,
				BorderL.width, 
				H - BorderTL.height - BorderTL.height,
				false);
			
			g.drawBitmapDataRound(BorderR,
				W - BorderR.width, 
				BorderTR.height, 
				BorderR.width, 
				H - BorderTR.height - BorderTR.height, 
				false);
			
			g.drawBitmapDataRound(BorderT,
				BorderTL.width, 
				0, 
				W-BorderTL.width-BorderTR.width,
				BorderT.height,
				false);
			
			g.drawBitmapDataRoundT(BorderT,
				BorderTL.width, 
				H - BorderT.height, 
				W - BorderTL.width - BorderTR.width, 
				BorderT.height,
				Transform.TRANS_H180,
				false);
			
			g.drawBitmapData(BorderTL, 
				0, 
				0,
				false);
			
			g.drawBitmapDataT(BorderTL, 
				0, 
				H - BorderTL.height,
				Transform.TRANS_H180,
				false);
			
			g.drawBitmapData(BorderTR, 
				W - BorderTR.width, 
				0,
				false);
			
			g.drawBitmapDataT(BorderTR, 
				W - BorderTR.width,
				H - BorderTR.height,
				Transform.TRANS_H180,
				false);
			
		}
		*/
		protected function renderBack4(g : CGraphicsBitmap, W:int, H:int) : void
		{
			g.drawBitmapDataScale(BackImage, 0, 0, W, H);
		}
		
		protected function renderBack4Center(g : CGraphicsBitmap, W:int, H:int) : void
		{
			g.drawBitmapData(BackImage, 
				(W-BackImage.width)>>1,
				(H-BackImage.height)>>1,
				false);
		}
		
		
		public function render(g:Graphics, x:int, y:int, w:int, h:int) : void
		{
			
		}
		
	}
}