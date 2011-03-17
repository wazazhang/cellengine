package com.g2d.display;

import com.g2d.geom.Point;



public interface TipAnchor
{
	abstract public Point setStageLocation(Stage stage, Tip tip, int x, int y, int w, int h);
	
	public static class DefaultTipAnchor implements TipAnchor
	{
		final static public int ANCHOR_LEFT 	= 0x01;
		final static public int ANCHOR_RIGHT 	= 0x02;
		final static public int ANCHOR_HCENTER 	= 0x04;
		final static public int ANCHOR_TOP 		= 0x10;
		final static public int ANCHOR_BOTTON 	= 0x20;
		final static public int ANCHOR_VCENTER 	= 0x40;

		public int default_anchor = ANCHOR_RIGHT | ANCHOR_VCENTER;
		
		@Override
		public Point setStageLocation(Stage stage, Tip tip, int x, int y, int w, int h) 
		{
			int anchor = default_anchor;
			
			int sx = x;
			int sy = y;
			// horizontal
			{
				if ((anchor & ANCHOR_HCENTER) != 0) {
					sx = x + w/2 - tip.getWidth()/2;
				}
				if ((anchor & ANCHOR_RIGHT) != 0) {
					sx = x + w + 1;
				}
				if ((anchor & ANCHOR_LEFT) != 0) {
					sx = x - tip.getWidth() - 1;
				}
				
				if (sx + tip.getWidth() > stage.getWidth()) {
					sx = x - tip.getWidth() - 1;
				}
				if (sx < 0) {
					sx = 0;
				}
			}
			// vertical
			{
				if ((anchor & ANCHOR_VCENTER) != 0) {
					sy = y + h/2 - tip.getHeight()/2;
				}
				if ((anchor & ANCHOR_BOTTON) != 0) {
					sy = y + h + 1;
				}
				if ((anchor & ANCHOR_TOP) != 0) {
					sy = y - tip.getHeight() - 1;
				}
				
				if (sy + tip.getHeight() > stage.getHeight()) {
					sy = stage.getHeight() - tip.getHeight();
				}
				if (sy < 0) {
					sy = 0;
				}
			}
			return new Point(sx, sy);
		}
	}
}
