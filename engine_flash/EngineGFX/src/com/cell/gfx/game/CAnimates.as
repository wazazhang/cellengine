package com.cell.gfx.game
{
	
	/**
	 * Animates contain some frame, it have a coordinate system with all part. </br>
	 * every frame contian some part </br> 
	 * every part is Images's index value </br>
	 * every part has itself 2d coordinate x y </br>>
	 * every part has itself Flip attribute </br>
	 * @author yifeizhang
	 * @since 2006-11-29 
	 * @version 1.0
	 */
	public class CAnimates extends CGroup
	{
		protected var images : IImages;
		
		/**short[]*/
		internal var SX : Array;
		/**short[]*/
		internal var SY : Array;
		/**short[]*/
		internal var SW : Array;
		/**short[]*/
		internal var SH : Array;
		/**short[]*/
		internal var STileID : Array;
		/**byte[]*/
		internal var SFlip : Array;
		
		/**
		 * Construct Animates
		 * @param partCount size of part </br>
		 * @param images reference </br>
		 */
		public function CAnimates(partCount:int, images:IImages)
		{
			this.SubCount 	= partCount;
			this.SubIndex	= 0;
			
			this.STileID	= new Array(partCount);
			this.SFlip		= new Array(partCount);
			
			this.SW			= new Array(partCount);
			this.SH			= new Array(partCount);
			this.SX			= new Array(partCount);
			this.SY			= new Array(partCount);
			
			this.Frames 	= new Array(partCount);
			
			this.images 	= images;
		}
	
		/**
		 * Add an image part form construct images reference</br>�1�7
		 * @param px part's x coordinate </br>�1�7
		 * @param py part's y coordinate </br>�1�7
		 * @param tileid part's images index value </br>
		 * @param trans part's flip rotate paramenter
		 * 	 */
		public function addPart(px:int, py:int, tileid:int, trans:int) : void
		{
			if (SubIndex >= SubCount) {
				trace("Out Of Animate Max Count !");
				return;
			}
			if (SubIndex < SubCount) {
				STileID[SubIndex] = tileid;
				SW[SubIndex] = images.getWidth(tileid);
				SH[SubIndex] = images.getHeight(tileid);
				SX[SubIndex] = px;
				SY[SubIndex] = py;
				SFlip[SubIndex] = trans;
				switch(trans){
				case Transform.TRANS_NONE:
				case Transform.TRANS_180:
				case Transform.TRANS_H:
				case Transform.TRANS_H180:
					SW[SubIndex] = images.getWidth(tileid);
					SH[SubIndex] = images.getHeight(tileid);
					break;
				case Transform.TRANS_90:
				case Transform.TRANS_270:
				case Transform.TRANS_H90:
				case Transform.TRANS_H270:
					SW[SubIndex] = images.getHeight(tileid);
					SH[SubIndex] = images.getWidth(tileid);
					break;
				}
				
				fixArea(SX[SubIndex], SY[SubIndex], 
						SX[SubIndex] + SW[SubIndex],
						SY[SubIndex] + SH[SubIndex]);
				
				Frames[SubIndex] = [SubIndex];
				
				SubIndex++;
			}
		}
	
		//---------------------------------------------------------------------------------------------------
		
		public function getImages() : IImages
		{
			return images;
		}
		
		public function setImages(images : IImages) : IImages {
			var ori : IImages = this.images;
			this.images = images;		
			return ori;
		}
		
		/**
		 * Get image form construct images reference</br>
		 * @param index index of construct images 
		 * @return image
		 */
		public function getImage(index:int) : CImage {
			return images.getImage(index);
		}
		
		/**
		 * Get image form specify frame id and part id</br>
		 * @param frame frame id within animates</br>
		 * @param sub part id within frame</br>
		 * @return image
		 */
		public function getFrameImage(frame:int, sub:int) : CImage {
			return images.getImage(STileID[Frames[frame][sub]]);
		}
		
		public function getFrameX(frame:int, sub:int) : int {
			return SX[Frames[frame][sub]];
		}
		public function getFrameY(frame:int, sub:int) : int {
			return SY[Frames[frame][sub]];
		}
		
		public function getFrameW(frame:int, sub:int) : int {
			return SW[Frames[frame][sub]];
		}
		
		public function getFrameH(frame:int, sub:int) : int {
			return SH[Frames[frame][sub]];
		}
	
		public function getFrameTransform(frame:int, sub:int) : int {
			return SFlip[Frames[frame][sub]];
		}
		
//		public function getFrameBounds(frame:int) : CCD
//		{
//			var left	: int = int.MAX_VALUE;
//			var right	: int = int.MIN_VALUE; 
//			var top		: int = int.MAX_VALUE;
//			var bottom	: int = int.MIN_VALUE;
//			
//			for (var i:int=0; i<SX.length; i++)
//			{
//				left	= Math.min(getFrameX(frame, i), left);
//				right	= Math.max(getFrameX(frame, i) + getFrameW(frame, i), right);
//				top		= Math.min(getFrameY(frame, i), top);
//				bottom	= Math.max(getFrameY(frame, i) + getFrameH(frame, i), bottom);
//			}
//			return CCD.createCDRect2Point(0, left, top, right, bottom);
//		}
		
		
//		/**
//		 * @param index1 frame id
//		 * @param index2 frame id
//		 * @return 
//		 */
//		public function frameEqual(index1:int, index2:int) : Boolean
//		{
//			if (Frames[index1].length != Frames[index2].length) {
//				return false;
//			}
//			for(var i:int=Frames[index1].length-1; i>=0; i--){
//				var idx1 : int = Frames[index1][i];
//				var idx2 : int = Frames[index2][i];
//				if (idx1 != idx2) {
//					return false;
//				}
//			}
//			return true;
//		}
		
		
		/**
		 * Draw one frame with specify frame id</br>
		 * @param g	graphics surface 
		 * @param index frame id </br>
		 * @param x x on graphics surface</br>
		 * @param y y on graphics surface</br>
		 */
		public function render(g:IGraphics, index:int, x:int, y:int) : void {
			for(var i:int=Frames[index].length-1;i>=0;i--){
				var idx : int = Frames[index][i];
				images.render(g,
						STileID[idx], 
						x + SX[idx], //
						y + SY[idx], //
						SW[idx],
						SH[idx],
						SFlip[idx]);
			}
		}	
			
		/**
		 * Draw one part with specify frame id and part id</br>�1�7
		 * @param g graphics surface 
		 * @param index frame id </br>
		 * @param part part id </br>
		 * @param x x on graphics surface </br>�1�7
		 * @param y y on graphics surface </br>
		 */
		public function renderSub(g:IGraphics, index:int, part:int, x:int, y:int) : void {
			var idx : int = Frames[index][part];
			images.render(g,
					STileID[idx], 
					x + SX[idx], //
					y + SY[idx], //
					SW[idx],
					SH[idx],
					SFlip[idx]);
		}	
		
		/**
		 * Draw one frame with specify frame id ignore part's coordinate, 
		 * all part based zero point.</br>
		 * @param g graphics surface
		 * @param index frame id
		 * @param x x on graphics surface
		 * @param y y on graphics surface
		 */
		public function renderSingle(g:IGraphics, index:int, x:int, y:int) : void {
			for(var i:int=Frames[index].length-1;i>=0;i--){
				var idx : int = Frames[index][i];
				images.render(g,
						STileID[idx], 
						x , //
						y , //
						SW[idx],
						SH[idx],
						SFlip[idx]);
			}
		}
		
		/**
		 * Draw one part with specify frame id and part id ignore part's coordinate, 
		 * part based zero point.</br>
		 * @param g graphics surface
		 * @param index frame id
		 * @param part part id
		 * @param x x on graphics surface
		 * @param y y on graphics surface
		 */
		public function renderSingleSub(g:IGraphics, index:int, part:int, x:int, y:int) : void {
			var idx : int = Frames[index][part];
			images.render(g,
					STileID[idx], 
					x , //
					y , //
					SW[idx],
					SH[idx],
					SFlip[idx]);
	
		}
		
		public function renderTile(g:IGraphics, idx:int, x:int, y:int) : void {
			images.render(g, 
				STileID[idx],
				x , y , 
				SW[idx],
				SH[idx], 
				SFlip[idx]);
		}
		
	}
}

