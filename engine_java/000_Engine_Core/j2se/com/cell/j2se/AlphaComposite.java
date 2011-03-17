package com.cell.j2se;





public class AlphaComposite
{
	public static enum AlphaBlendMode
	{
		CLEAR		(java.awt.AlphaComposite.CLEAR),
		DST			(java.awt.AlphaComposite.DST),
		DST_ATOP 	(java.awt.AlphaComposite.DST_ATOP),
		DST_IN 		(java.awt.AlphaComposite.DST_IN),
		DST_OUT 	(java.awt.AlphaComposite.DST_OUT),
		DST_OVER 	(java.awt.AlphaComposite.DST_OVER),
		SRC 		(java.awt.AlphaComposite.SRC),
		SRC_ATOP 	(java.awt.AlphaComposite.SRC_ATOP),
		SRC_IN 		(java.awt.AlphaComposite.SRC_IN),
		SRC_OUT 	(java.awt.AlphaComposite.SRC_OUT),
		SRC_OVER 	(java.awt.AlphaComposite.SRC_OVER),
		XOR 		(java.awt.AlphaComposite.XOR)
		;
		
		
		private final int value_;
		
		private AlphaBlendMode(int value)
		{
			this.value_ = value;
		}
		
		public int getValue()
		{
			return this.value_;
		}
		
		public static AlphaBlendMode getInstance(int value)
		{
			for ( AlphaBlendMode mode : AlphaBlendMode.values() )
			{
				if (mode.value_ == value)
					return mode;
			}
			
			return null;
		}
		
	}; // enum AlphaBlendMode



}; // class AlphaComposite

