package com.cell.gameedit.output
{
	import com.cell.gameedit.object.SpriteSet;
	import com.cell.gfx.game.CAnimates;
	import com.cell.gfx.game.CCollides;
	import com.cell.gfx.game.IImages;
	import com.cell.gfx.game.CSprite;

	public class XmlCSprite extends CSprite
	{
		public function XmlCSprite(output:XmlCOutputLoader, tiles:IImages, tsprite:SpriteSet)
		{
			if (output != null) {
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
				init(
					animates, 
					collides, 
					tsprite.AnimateNames,
					tsprite.FrameAnimate,
					tsprite.FrameAlpha,
					tsprite.FrameRotate,
					tsprite.FrameScaleX,
					tsprite.FrameScaleY,
					tsprite.FrameCDMap,
					tsprite.FrameCDAtk,
					tsprite.FrameCDDef,
					tsprite.FrameCDExt
				);
			}
		}
		
		protected override function init(
			canimates		:CAnimates, 
			ccollides		:CCollides,
			animateNames	:Array, 
			frameAnimate	:Array,
			frameAlpha	: Array,
			frameRotate	: Array,
			frameScaleX	: Array,
			frameScaleY	: Array,
			frameCDMap	: Array,
			frameCDAtk	: Array,
			frameCDDef	: Array,
			frameCDExt	: Array) : void
		{
			super.init(canimates, ccollides, animateNames, frameAnimate, 
				frameAlpha, frameRotate, frameScaleX, frameScaleY,
				frameCDMap, frameCDAtk, frameCDDef, frameCDExt);
		}
		
		public override function copy() : CSprite
		{
			var ret : XmlCSprite = new XmlCSprite(null, null, null);
			ret.init2(this);
			return ret;
		}
	}
}