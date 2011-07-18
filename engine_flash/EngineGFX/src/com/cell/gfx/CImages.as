package com.cell.gfx
{
	import com.cell.gameedit.Output;
	import com.cell.gameedit.object.ImagesSet;
	

	public class CImages
	{
		static public var TRANS_TABLE : Array = [
			0,//TRANS_NONE
			1,//TRANS_V
			2,//TRANS_H
			3,//TRANS_HV
			4,//TRANS_H90
			5,//TRANS_270
			6,//TRANS_90
			7 //TRANS_V90
		];

		
		protected var tiles : Array;
		
		public function CImages()
		{
		}

		public function getImage(index:int) : CImage
		{
			return (tiles[index] as CImage);
		}
		
		public function getWidth(index:int) : int
		{
			return (tiles[index] as CImage).width;
		}
		
		public function getHeight(index:int) : int
		{
			return (tiles[index] as CImage).height;
		}
		
		public function clone() : CImages
		{
			var ret : CImages = new CImages();
			for each (var tile:CImage in this.tiles) {
				ret.tiles.push(tile.clone());
			}
			return ret;
		}
		
		public function render(g:CGraphics, index:int, x:int, y:int, transform:int) : void {
			if (tiles[index]!=null){
				g.drawImage(tiles[index], x, y, transform);
			}
		}
	}
}