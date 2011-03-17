

package com.cell;

import java.util.Collection;
import java.util.Iterator;

/**
 * util math function none float
 * 
 * @author yifeizhang
 * @since 2006-12-1 
 * @version 1.0
 */
public class CMath extends CObject{

	final public static int sqrt(int i) {
		int l = 0;
		for (int k = 0x100000; k != 0; k >>= 2) {
			int j = l + k;
			l >>= 1;
			if (j <= i) {
				i -= j;
				l += k;
			}
		}
		return l;
	}
	
	/** Pi */
	final static public int PI256 = 804;


	/**
	 * sine*256 angle table 0~90
	 */
	private static final short[] SINES = { 0, 4, 9, 13, 18, 22, 27, 31, 36, 40,
			44, 49, 53, 58, 62, 66, 71, 75, 79, 83, 88, 92, 96, 100, 104, 108,
			112, 116, 120, 124, 129, 132, 136, 139, 143, 147, 150, 154, 158,
			161, 165, 168, 171, 175, 178, 181, 184, 187, 190, 193, 196, 199,
			202, 204, 207, 210, 212, 215, 217, 219, 222, 224, 226, 228, 230,
			232, 234, 236, 237, 239, 241, 242, 243, 245, 246, 247, 248, 249,
			250, 251, 252, 253, 254, 254, 255, 255, 255, 256, 256, 256, 256, };

	/**
	 * @param angle 0~360
	 * @return 256 point
	 */
	final public static int sinTimes256(int angle) {
		int d = angle < 0 ? -1 : 1;
		angle = Math.abs(angle % 360); // 360 degrees
		if (angle <= 90) {
			return SINES[angle] * d;
		} else if (angle <= 180) {
			return SINES[180 - angle] * d;
		} else if (angle <= 270) {
			return -SINES[angle - 180] * d;
		} else {
			return -SINES[360 - angle] * d;
		}
	}

	/**
	 * 
	 * 
	 * @param angle  0~360
	 * @return 256 
	 */
	final public static int cosTimes256(int angle) {
		return sinTimes256(angle + 90); // i.e. add 90 degrees
	}

	/**
	 *  
	 * 
	 * @param angle  0~360
	 * @return 256 
	 */
	final public static int tanTimes256(int angle) {
		int dx = cosTimes256(angle);
		if(dx==0)return Integer.MAX_VALUE;
		return sinTimes256(angle) * 256 / dx;
	}

	/**
	 *  
	 * 
	 * @param angle  0~360
	 * @return 256 
	 */
	final public static int cotTimes256(int angle) {
		int dy = sinTimes256(angle);
		if(dy==0)return Integer.MAX_VALUE;
		return cosTimes256(angle) * 256 / dy;
	}

	
	
//	--------------------------------------------------------------------------------------------------------
	//  10 degree atan array[angle/10] = x/y * 256
	private static final short[] AtanDivTable = new short[]{
		0,45,93,147,214,305,443,703,1451,Short.MAX_VALUE
	};

	final private static int div(int i, int j) {
		int tmp = ((int)i<<8) / j;
		if(tmp > Short.MAX_VALUE){
			return Short.MAX_VALUE;
		}
		if(tmp < Short.MIN_VALUE){
			return Short.MIN_VALUE;
		}
		return (int)(tmp);
	}
	
	final private static int atan(int n){
		boolean flag = n < 0;
		if(flag) {
			n = -n;
		}
		int f1 = 0, f2 = AtanDivTable.length - 1,ft = 0;
		while(f1 + 1 != f2) {
			ft = f1 + f2 >> 1;
			if(n < AtanDivTable[ft]) {
				f2 = ft;
			}else {
				f1 = ft;
			}
		}
		return (flag? -f1: f1) * 10 ;// 10 ��
	}
	
	final public static int atan2(int dy,int dx){
		if (dx > 0) {
			return atan(div(dy, dx)) ;
		} else if (dx < 0) {
			return (180) + atan(div(dy, dx)) ;
		} else {
			return (dy > 0 ? 90 : -90 );
		}
	}
	
	
// 	--------------------------------------------------------------------------------------------------------
	
	/**
	 * comput cyc number: (value+d) within 0~max scope
	 * @param value
	 * @param d
	 * @param max
	 * @return 
	 */
	final static public int cycNum(int value, int d, int max) {
		value += d;
		return (value>=0)?(value % max):((max + value % max) % max) ;
	}

	
	/**
	 * comput cyc mod: -1 mod 10 = -1
	 * @param value
	 * @param div
	 * @return 
	 */
	final static public int cycMod(int value, int div) {
		return (value/div) + (value<0?-1:0);
	}
	

	/**
	 * @param value 
	 * @return 1 or 0 or -1
	 */
	final static public int getDirect(int value) {
		return value==0?0:(value>0?1:-1);
	}
	
	
	/**
	 * comput round mod roundMode(33,8) = 5 => 33/8 + (33%8==0?:0:1)
	 * @param value
	 * @param div
	 * @return 
	 */
	final static public int roundMod(int value, int div) {
		return (value/div) + (value%div==0?0:(1*getDirect(value)));
	}
	
	
	/**
	 * 根据速度和时间段得到距离
	 * @param speed 速度 (距离/秒)
	 * @param interval_ms 毫秒
	 * @return
	 */
	final static public double getDistance(double speed, int interval_ms) {
		double rate = interval_ms / 1000d ;
		return speed * rate;
	}
	
//	-------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 * @param px
	 * @param py
	 * @param qx
	 * @param qy
	 * @return v[]
	 */
	final public static void vectorAdd(float[] out, float px, float py, float qx, float qy) {
		out[0] = px + qx;
		out[1] = py + qy;
		
	}
	
	/**
	 * 
	 * @param px
	 * @param py
	 * @param qx
	 * @param qy
	 * @return v[]
	 */
	final public static void vectorSub(float[] out, float px, float py, float qx, float qy) {
		out[0] = px - qx;
		out[1] = py - qy;
	}

	/**
	 * Vector Cross Product in Descartes reference frame 
	 * if P x Q > 0 , P above Q clockwise
	 * if P x Q < 0 , P above Q anticlockwise
	 * if P x Q = 0 , P equal Q at same line
	 * 
	 * @param p
	 * @param q
	 * @return +-
	 */
	final public static float vectorCrossProduct(float[] p, float[] q) {
		return (p[0] * q[1] - p[1] * q[0]);
	}

	/**
	 * ((Q2-Q1)��(P1-Q1))*((P2-Q1)��(Q2-Q1)) >= 0 
	 * ((P2-P1)��(Q1-P1))*((Q2-P1)��(P2-P1)) >= 0
	 * 
	 * @param p1x line 1
	 * @param p1y
	 * @param p2x
	 * @param p2y
	 * @param q1x line 2
	 * @param q1y
	 * @param q2x
	 * @param q2y
	 * @return false:true
	 */
	final static public boolean intersectLine(
			float p1x, float p1y, float p2x, float p2y,
			float q1x, float q1y, float q2x, float q2y) 
	{
		/* croe */
		float v1[] = new float[2];
		float v2[] = new float[2];
		float v3[] = new float[2];
//		float v4[] = new float[2];
		
		float v5[] = new float[2];
		float v6[] = new float[2];
		float v7[] = new float[2];
//		float v8[] = new float[2];
		
		{
			CMath.vectorSub(v1, q2x, q2y, q1x, q1y);//1
			CMath.vectorSub(v2, p1x, p1y, q1x, q1y);//2
			CMath.vectorSub(v3, p2x, p2y, q1x, q1y);//3
//			CMath.vectorSub(v4, q2x, q2y, q1x, q1y);//4=1
			
			CMath.vectorSub(v5, p2x, p2y, p1x, p1y);//5
			CMath.vectorSub(v6, q1x, q1y, p1x, p1y);//6
			CMath.vectorSub(v7, q2x, q2y, p1x, p1y);//7
//			CMath.vectorSub(v8, p2x, p2y, p1x, p1y);//8=5
			
			if (CMath.vectorCrossProduct(v1, v2) * CMath.vectorCrossProduct(v3, v1) >= 0 && 
				CMath.vectorCrossProduct(v5, v6) * CMath.vectorCrossProduct(v7, v5) >= 0) {
				return true;
			}
			return false;
		}
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean intersectRound(
			float sx, float sy, float sr, 
			float dx, float dy, float dr) 
	{
		float w = sx - dx;
		float h = sy - dy;
		float r = sr + dr;
		
		if (w*w + h*h <= r*r) {
			return true;
		}
		
		return false;
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean includeRoundPoint(
			float sx, float sy, float sr, 
			float px, float py) 
	{
		float w = sx - px;
		float h = sy - py;
		
		if (w*w + h*h <= sr*sr) {
			return true;
		}

		return false;
	}
	
//	--------------------------------------------------------------------------------------------------
	
	final static public boolean intersectRect(
			float sx1, float sy1, float sx2, float sy2, 
			float dx1, float dy1, float dx2, float dy2) {
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

	final static public boolean intersectRect2(
			float sx1, float sy1, float sw, float sh, 
			float dx1, float dy1, float dw, float dh) {
		float sx2 = sx1 + sw - 1 ;
		float sy2 = sy1 + sh - 1 ;
		float dx2 = dx1 + dw - 1 ;
		float dy2 = dy1 + dh - 1 ;
		if (sx2 < dx1)		return false;
		if (sx1 > dx2)		return false;
		if (sy2 < dy1)		return false;
		if (sy1 > dy2)		return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------
	
	final static public boolean includeRectPoint(
			float sx1, float sy1, float sx2, float sy2, 
			float dx, float dy) {
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}

	final static public boolean includeRectPoint2(
			float sx1, float sy1, float sw, float sh, 
			float dx, float dy) {
		float sx2 = sx1 + sw - 1 ;
		float sy2 = sy1 + sh - 1 ;
		if (sx2 < dx)		return false;
		if (sx1 > dx)		return false;
		if (sy2 < dy)		return false;
		if (sy1 > dy)		return false;
		return true;
	}

//	--------------------------------------------------------------------------------------------------
	
	/**
	 * if the value is in a~b or equal
	 * @param value
	 * @param a
	 * @param b
	 * @return
	 */
	public static final boolean isIncludeEqual(int value, int min, int max)
	{
		return max >= value && min <= value ;
	}

	/**
	 * if the value is in a~b not equal
	 * @param value
	 * @param a
	 * @param b
	 * @return
	 */
	public static final boolean isInclude(int value, int min, int max)
	{
		return max > value && min < value ;
	}
	
	
//	--------------------------------------------------------------------------------------------------
	
	/**
	 * sum all of elements together in the collection
	 * NOTE: how to know the element type of the collection??
	 */
	@SuppressWarnings("unchecked")
	public static final <T> double accumulate(Collection<T> values)
	{	
		if ( (values != null) && !values.isEmpty() )
		{					
			Iterator<T> it = values.iterator();
			
			T first = it.next();
			
			if (first instanceof Integer)
			{
				Integer val = 0;
				for ( Integer tmp : (Collection<Integer>)values )
					val += tmp;
				return val;
			}
			else if (first instanceof Float)
			{
				Float val = 0f;
				for ( Float tmp : (Collection<Float>)values )
					val += tmp;
				return val;
			}
			else if (first instanceof Double)
			{
				Double val = 0d;
				for ( Double tmp : (Collection<Double>)values )
					val += tmp;
				return val;
			}
			else if (first instanceof Long)
			{
				Long val = 0l;
				for ( Long tmp : (Collection<Long>)values )
					val += tmp;
				return val;
			}
			else if (first instanceof Short)
			{
				Integer val = 0;
				for ( Short tmp : (Collection<Short>)values )
					val += tmp;
				return val;
			}
			else if (first instanceof Byte)
			{
				Integer val = 0;
				for ( Byte tmp : (Collection<Byte>)values )
					val += tmp;
				return val;
			}
		}

		return 0;
	}
	
/**
 * 下面这种编码方法不再被java7支持，也被证实了是java6及旧版本的编译器的BUG
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=320514
 * @see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6182950	
 */
	
//	/**
//	 * sum all of elements together in the collection
//	 */
//	public static final float accumulate(Collection<Float> values)
//	{
//		Float val = 0f;
//		for ( Float tmp : values )
//			val += tmp;
//		return val;
//	}
//	
//	/**
//	 * sum all of elements together in the collection
//	 */
//	public static final double accumulate(Collection<Double> values)
//	{
//		Double val = 0d;
//		for ( Double tmp : values )
//			val += tmp;
//		return val;
//	}	
	
};


