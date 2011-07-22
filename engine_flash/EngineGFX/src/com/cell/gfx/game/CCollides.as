package com.cell.gfx.game
{
	import com.cell.gfx.game.CCD;
	
	/**
	 * Collides contian some frame, it have a coordinate system with all part. </br>
	 * every frame contian some part </br> 
	 * every part is a collision block (CCD object) </br>
	 * @author yifeizhang
	 * @since 2006-11-29 
	 * @version 1.0
	 */
	public class CCollides extends CGroup
	{
		/** CCD[] */
		internal var cds : Array;
	
		/**
		 * Construct CCD group 
		 * @param cdCount part count </br>
		 * 	 */
		public function CCollides(cdCount:int)
		{
			this.SubIndex 	= 0;
			this.SubCount 	= cdCount;
			this.cds 		= new Array(cdCount);
			this.Frames 	= new Array(cdCount);
		}
		
		/**
		 * add a rectangle collision block part </br>
		 * @param mask collision block mask
		 * @param x left
		 * @param y top
		 * @param w width
		 * @param h hight
		 */
		public function addCDRect(mask:int, x:int, y:int, w:int, h:int) : void {
			addCD(CCD.createCDRect(mask, x, y, w, h));
		}
		
		/**
		 * add a line collision block part</br>�1�7
		 * @param mask collision block mask
		 * @param px point 1 x
		 * @param py point 1 y
		 * @param qx point 2 x
		 * @param qy point 2 y
		 */
		public function addCDLine(mask:int, px:int, py:int, qx:int, qy:int) : void {
			addCD(CCD.createCDLine(mask,px,py,qx,qy));
		}
		
		/**
		 * add a collision block part</br>
		 * @param cd collision block part
		 */
		public function addCD(cd:CCD) : void {
			if (SubIndex >= SubCount) {
				trace("Out of Max CD Count !");
				return;
			}
			cds[SubIndex] = cd;
			if(cd!=null){
				fixArea(cd.X1, cd.Y1, cd.X2, cd.Y2);
			}
			Frames[SubIndex] = [SubIndex];
			SubIndex++;
		}
	
		//---------------------------------------------------------------------------------
		/**
		 * Get a collision block form group, return ccd[index]
		 * @param index ccd[index]
		 * @return collision block
		 */
		public function getCD(index:int) : CCD {
			return cds[index];
		}
		
		/**
		 * Get collision block form specify frame id and part id </br>
		 * @param frame frame id within collides
		 * @param sub part id within frame
		 * @return image
		 */
		public function getFrameCD(frame:int, sub:int) : CCD {
			return cds[Frames[frame][sub]];
		}
		
//		/**
//		 * collision test with a collides specify frame and a collision block (CCD object)</br>
//		 * @param c1 src collides 
//		 * @param index1 src frame id
//		 * @param x1 src x offset
//		 * @param y1 src y offset
//		 * @param c2 dst CCD object
//		 * @param x2 dst x offset
//		 * @param y2 dst y offset
//		 * @return 
//		 */
//		static public function touchCD(
//				c1:CCollides, index1:int , x1:int, y1:int,
//				c2:CCD, x2:int,  y2:int) : Boolean {
//			for(var i:int=c1.Frames[index1].length-1;i>=0;i--){
//				if( CCD.touch(
//						c1.cds[c1.Frames[index1][i]], x1, y1, // 
//						c2, x2, y2, //
//						true)){
//					return true;
//				}
//			}
//			return false;
//		}
//	
//		
//		/**
//		 * collision test with a collides specify frame part and a collision block (CCD object)</br>
//		 * @param c1 src collides
//		 * @param index1 src frame id
//		 * @param part1 src part id
//		 * @param x1 src x offset 
//		 * @param y1 src y offset
//		 * @param c2 dst CCD object
//		 * @param x2 dst x offset
//		 * @param y2 dst y offset
//		 * @return 
//		 */
//		static public function touchSubCD(
//				CCollides c1,int index1,int part1,int x1,int y1,
//				CCD c2,int x2,int y2){
//			if( CCD.touch(
//					c1.cds[c1.Frames[index1][part1]], x1, y1, // 
//					c2, x2, y2, //
//					true)){
//				return true;
//			}
//			return false;
//		}
//		
//		/**
//		 * collision test with 2 collides specify frame</br>
//		 * @param c1 collides 1
//		 * @param index1 frame id 1
//		 * @param x1  x offset 1
//		 * @param y1  y offset 1
//		 * @param c2 collides 2
//		 * @param index2 frame id 2
//		 * @param x2  x offset 2
//		 * @param y2  y offset 2
//		 * @return is intersect
//		 */
//		static public function touch(
//				CCollides c1,int index1,int x1,int y1,
//				CCollides c2,int index2,int x2,int y2){
//	
//			if(touchArea(c1, x1, y1, c2, x2, y2)){
//				for(int i=c1.Frames[index1].length-1;i>=0;i--){
//					for(int j=c2.Frames[index2].length-1;j>=0;j--){
//						if( CCD.touch(
//								c1.cds[c1.Frames[index1][i]], x1, y1, // 
//								c2.cds[c2.Frames[index2][j]], x2, y2, //
//								true)){
//							return true;
//						}
//					}
//				}
//			}
//			return false;
//		}
//		
//		/**
//		 * collision test with 2 collides specify frame part</br>
//		 * @param c1 collides 1
//		 * @param index1 frame id 1
//		 * @param part1 part id 1
//		 * @param x1  x offset 1
//		 * @param y1  y offset 1
//		 * @param c2 collides 2
//		 * @param index2 frame id 2
//		 * @param part2 part id 2
//		 * @param x2  x offset 2
//		 * @param y2  y offset 2
//		 * @return is intersect
//		 */
//		static public function touchSub(
//				CCollides c1,int index1,int part1,int x1,int y1,
//				CCollides c2,int index2,int part2,int x2,int y2){
//			if( CCD.touch(
//					c1.cds[c1.Frames[index1][part1]], x1, y1, // 
//					c2.cds[c2.Frames[index2][part2]], x2, y2, //
//					true)){
//				return true;
//			}
//			return false;
//		}
//		
//		
		/**
		 * draw a collides block
		 * @param g
		 * @param index frame id
		 * @param x
		 * @param y
		 * @param color 
		 */
		public function render(g:CGraphics, index:int, x:int, y:int, color:int) : void {
			for(var i:int=Frames[index].length-1;i>=0;i--){
				cds[Frames[index][i]].render(g, x, y, color);
			}
		}
		
		/**
		 * draw a collides block
		 * @param g
		 * @param index frame id
		 * @param x
		 * @param y
		 * @param color 
		 */
		public function renderSub(g:CGraphics, index:int, sub:int, x:int, y:int, color:int) : void {
			cds[Frames[index][sub]].render(g, x, y, color);
		}
		
		public function clone() : CCollides
		{
			var ret : CCollides = new CCollides(cds.length);
			super.set(ret);
			for (var i:int=0; i<cds.length; i++) {
				ret.cds[i] = this.cds[i].clone();
			}
			return ret;
		}
	}

}