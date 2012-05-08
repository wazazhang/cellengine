package com.g2d.display.particle;

import java.io.Serializable;
import java.util.Random;

import com.cell.CUtil;
import com.cell.math.MathVector;
import com.cell.math.TVector;
import com.cell.math.Vector;
import com.g2d.geom.Ellipse;
import com.g2d.geom.Shape;


public interface OriginShape extends Serializable
{
	/**
	 * 得到发射位置
	 * @param random
	 * @return {x, y}
	 */
	public Vector getPosition(Random random);
	
	public Shape getShape();
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	/** 矩形  */
	public static class Rectangle implements OriginShape
	{
		private static final long serialVersionUID = 1L;
		
		public float x = -100, y = -100, w = 200, h = 200;
		
		@Override
		public Vector getPosition(Random random) {
			TVector vector = new TVector(
					CUtil.getRandom(random, x, x+w), 
					CUtil.getRandom(random, y, y+h));
			return vector;
		}
		
		@Override
		public Shape getShape() {
			return new com.g2d.geom.Rectangle((int)x, (int)y, (int)w, (int)h);
		}
	}
	
	/**
	 * 圆或环
	 */
	public static class Ring implements OriginShape
	{
		private static final long serialVersionUID = 1L;
		
		/** 内圆半径 */
		public float radius1 = 50;
		
		/** 外圆半径 */
		public float radius2 = 100;
		
		@Override
		public Vector getPosition(Random random) {
			TVector vector = new TVector(0, 0);
			MathVector.movePolar(vector, 
					CUtil.getRandom(random, 0, (float)(Math.PI*2)), 
					CUtil.getRandom(random, radius1, radius2));
			return vector;
		}
		
		@Override
		public Shape getShape() {
			float r = Math.max(radius1, radius2);
			return new Ellipse((int)-r, (int)-r, (int)r * 2, (int)r * 2);
		}
	}
}
