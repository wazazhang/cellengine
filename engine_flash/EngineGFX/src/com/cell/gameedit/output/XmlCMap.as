package com.cell.gameedit.output
{
	import com.cell.gameedit.object.MapSet;
	import com.cell.gfx.game.CAnimates;
	import com.cell.gfx.game.CCollides;
	import com.cell.gfx.game.IImages;
	import com.cell.gfx.game.CMap;

	public class XmlCMap extends CMap
	{
		public function XmlCMap(output:XmlCOutputLoader, tiles:IImages, tmap:MapSet)
		{
			var xcount	: int = tmap.XCount;
			var ycount	: int = tmap.YCount;
			var cellw	: int = tmap.CellW;
			var cellh	: int = tmap.CellH;
			
			var scenePartCount : int = tmap.TileID.length;
			var animates : CAnimates = new CAnimates(scenePartCount, tiles);
			for (var i:int=0;i<scenePartCount;i++){
				animates.addPart(0, 0, tmap.TileID[i], tmap.TileTrans[i]);
			}
			
			var animateCount : int = tmap.Animates.length;
			var animates_frame : Array = new Array(animateCount);
			for (var i:int=0;i<animateCount;i++){
				animates_frame[i] = new Array(tmap.Animates[i].length);
				for(var f:int=0;f<tmap.Animates[i].length;f++){
					animates_frame[i][f] = tmap.Animates[i][f];
				}
			}
			animates.setFrames(animates_frame);

			
			var tileMatrix : Array = new Array(ycount);
			for(var y=0; y<ycount; y++){
				tileMatrix[y] = new Array(xcount);
				for(var x=0; x<xcount; x++){
					tileMatrix[y][x] = tmap.TerrainScene2D[y][x];
				}
			}
			
			
			var cdCount : int = tmap.BlocksType.length;
			var collides : CCollides = new CCollides(cdCount);
			for(var i=0;i<cdCount;i++){
				var type = tmap.BlocksType[i];
				var mask = tmap.BlocksMask[i];
				var x1 	= tmap.BlocksX1[i];
				var y1 	= tmap.BlocksY1[i];
				var x2 	= tmap.BlocksX2[i];
				var y2 	= tmap.BlocksY2[i];
				var w 	= tmap.BlocksW[i];
				var h 	= tmap.BlocksH[i];
				if(type==0) collides.addCDRect(mask, x1, y1, w, h);
				if(type==1) collides.addCDLine(mask, x1, y1,x2,y2);
			}
			
			var flagMatrix : Array = new Array(ycount);
			for(var y=0; y<ycount; y++){
				flagMatrix[y] = new Array(xcount);
				for(var x=0; x<xcount; x++){
					flagMatrix[y][x] = tmap.TerrainBlock2D[y][x];
				}
			}

			init(animates, collides, tmap.CellW, tmap.CellH, tileMatrix, flagMatrix);
				
			
		}
		
		
		
	}
}