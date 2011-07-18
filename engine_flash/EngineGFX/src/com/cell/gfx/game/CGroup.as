package com.cell.gfx.game
{
	import com.cell.util.CMath;
	
	/**
	 * @author yifeizhang
	 * @since 2006-11-30 
	 * @version 1.0
	 */
	public class CGroup
	{
		/**short[][]*/
		internal var Frames	: Array;
		
		internal var SubIndex	: int;
		internal var SubCount 	: int;
		
		internal var w_left 	: int	= 0;
		internal var w_top 	: int	= 0;
		internal var w_bottom 	: int	= 1;
		internal var w_right 	: int	= 1;
		
		internal var w_width 	: int	= 0;
		internal var w_height 	: int	= 0;
		
		/**
		 * @param left
		 * @param top
		 * @param right
		 * @param botton 
		 */
		protected function fixArea(left:int, top:int, right:int, botton:int) : void
		{
			if (left < w_left)
				w_left = left;
			if (top < w_top)
				w_top = top;
			if (right > w_right)
				w_right = right;
			if (botton > w_bottom)
				w_bottom = botton;
			
			w_width = (w_right - w_left);
			w_height = (w_bottom - w_top);
		}
		
		/**
		 * set frame sequence, frames[frame id][part id] = groupted object. </br> 
		 * e.g. : animates's image id ; collides's CCD object ;</br>
		 * @param frames frames[frame id][part id]
		 */
		public function setFrames(frames:Array) : void {
			this.Frames = frames;
		}
		
		/**short[][]*/
		public function getFrames() : Array {
			return this.Frames;
		}
		
		/**
		 * get frames count</br>
		 * @return count
		 */
		public function getCount() : int {
			return Frames.length;
		}
		
		/**
		 * set part sequence specify frame index</br>
		 * @param frame frames[frame id][part id]
		 * @param index frame id
		 */
		public function setComboFrame(frame:Array, index:int) : void {
			Frames[index] = frame;
		}
		
		/**
		 * get frames count</br>
		 * @return count
		 */
		public function getComboFrameCount(index:int) : int {
			return Frames[index].length;
		}
	}
}
