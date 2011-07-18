package com.cell.gameedit.output
{
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gfx.game.CAnimates;
	import com.cell.gfx.game.CCollides;
	import com.cell.gfx.game.CImages;
	import com.cell.gfx.game.CSprite;

	public class XmlDirSprite extends CSprite
	{
		public function XmlDirSprite(output:XmlOutput, tiles:CImages, tsprite:SpriteSet)
		{
			var scenePartCount : int = tsprite.PartTileID.length;
			var animates : CAnimates = new CAnimates(scenePartCount, tiles);
			for(var i:int=0;i<scenePartCount;i++){
				animates.addPart(
					tsprite.PartX[i], 
					tsprite.PartY[i],
					tsprite.PartTileID[i], 
					tsprite.PartTileTrans[i]);
			}
			animates.setFrames(tsprite.Parts);
			
			var cdCount : int = tsprite.BlocksMask.length;
			var collides : CCollides = new CCollides(cdCount);
			for(var i:int=0;i<cdCount;i++){
				collides.addCDRect(
					tsprite.BlocksMask[i],
					tsprite.BlocksX1[i], 
					tsprite.BlocksY1[i],
					tsprite.BlocksW[i], 
					tsprite.BlocksH[i]);
			}
			collides.setFrames(tsprite.Blocks);
			super(
				animates, 
				collides, 
				tsprite.AnimateNames,
				tsprite.FrameAnimate
			);
		}
		
		
		
	}
}