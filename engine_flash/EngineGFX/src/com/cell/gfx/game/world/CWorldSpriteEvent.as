package com.cell.gfx.game.world
{
	internal class CWorldSpriteEvent
	{
		public static const ADD		: int = 1;
		public static const REMOVE	: int = 2;
		
		
		internal var evt	:	int; 
		internal var spr	:	CWorldSprite;
		
		public function CWorldSpriteEvent(evt:int, spr:CWorldSprite)
		{
			this.evt = evt;
			this.spr = spr;
		}
	}
}