/**
 * 版权所有[2006][张翼飞]
 *
 * 根据2.0版本Apache许可证("许可证")授权；
 * 根据本许可证，用户可以不使用此文件。
 * 用户可从下列网址获得许可证副本：
 * http://www.apache.org/licenses/LICENSE-2.0
 * 除非因适用法律需要或书面同意，
 * 根据许可证分发的软件是基于"按原样"基础提供，
 * 无任何明示的或暗示的保证或条件。
 * 详见根据许可证许可下，特定语言的管辖权限和限制。
 */
package com.cell.math;

import com.cell.CObject;


/**
 *
 * 定点数数学类
 * 
 */
public class CMathFP extends CObject{

	public static final int BITS = 8;//基本不影响精度的情况下尽量小,建议在4~6
	public static final int ONE = 1 << BITS;
	private static final int MASK = ONE - 1;
	private static final int FIX = 12 - BITS;
	private static final int E_SEED[] = new int[]{//
			4096 >> FIX,//
			11134 >> FIX, //
			30266 >> FIX, //
			0x1415e >> FIX,//
			0x36994 >> FIX//
	};
	public static final int PI = 12868 >> FIX;
	public static final int E = E_SEED[1];
	public static final int MAX_VALUE = 0x7fffffff;
	public static final int MIN_VALUE = 0x80000001;

	/**
	 * 定点数取整（四舍五入）
	 * @param n 定点数
	 * @return 取整的定点数
	 */
	public static final int round(int n) {
		return (n + (ONE >> 1)) & ~MASK;
	}
	
	/**
	 * 定点数->整数
	 * @param nFP 定点数
	 * @return 整数
	 */
	public static final int toInt(int nFP) {
		return round(nFP) >> BITS; 
//		return nFP >> BITS; 
	}
	
	/**
	 * 整数->定点数
	 * @param n 整数
	 * @return 定点数
	 */
	public static final int toFP(int n) {
		return n << BITS;
	}

	/**
	 * 定点数乘法
	 * @param i
	 * @param j
	 * @return
	 */
	public static final int mul(int i, int j) {
		long tmp = i;
		tmp = tmp * j >> BITS;
		if(tmp > MAX_VALUE || tmp < MIN_VALUE) {
			println("溢出：" + i + " * " + j + " = " + tmp);
		}
		return (int)tmp;
	}

	/**
	 * 定点数除法
	 * @param i
	 * @param j
	 * @return
	 */
	public static final int div(int i, int j) {
		long tmp = ((long)i << BITS) / j;
		if(tmp > MAX_VALUE){
			return MAX_VALUE;
		}
		if(tmp < MIN_VALUE){
			return MIN_VALUE;
		}
		return (int)(tmp);
	}

	/**
	 * 定点数开方（指定精度）
	 * @param n 
	 * @param j 精度（6-24）
	 * @return
	 */
	public static final int sqrt(int n, int j) {
		if (n < 0) {
			println("负数不能开方：" + n);
		}
		if (n == 0) {
			return 0;
		}

		int k = n + ONE >> 1;
		for (int l = 0; l < j; l++) {
			k = k + div(n, k) >> 1;
		}
		if (k < 0){
			println("开方溢出：" + n + " ^0.5 = " + k);
		}
		return k;
	}

	/**
	 * 定点数开方（默认精度16）
	 * @param i
	 * @return
	 */
	public static final int sqrt(int i) {
		return sqrt(i, 16);
	}

	private static final short[] SINES_X_FP = {0 >> FIX, 71 >> FIX,
			143 >> FIX, 214 >> FIX, 286 >> FIX, 357 >> FIX, 428 >> FIX,
			499 >> FIX, 570 >> FIX, 641 >> FIX, 711 >> FIX, 782 >> FIX,
			852 >> FIX, 921 >> FIX, 991 >> FIX, 1060 >> FIX, 1129 >> FIX,
			1198 >> FIX, 1266 >> FIX, 1334 >> FIX, 1401 >> FIX, 1468 >> FIX,
			1534 >> FIX, 1600 >> FIX, 1666 >> FIX, 1731 >> FIX, 1796 >> FIX,
			1860 >> FIX, 1923 >> FIX, 1986 >> FIX, 2048 >> FIX, 2110 >> FIX,
			2171 >> FIX, 2231 >> FIX, 2290 >> FIX, 2349 >> FIX, 2408 >> FIX,
			2465 >> FIX, 2522 >> FIX, 2578 >> FIX, 2633 >> FIX, 2687 >> FIX,
			2741 >> FIX, 2793 >> FIX, 2845 >> FIX, 2896 >> FIX, 2946 >> FIX,
			2996 >> FIX, 3044 >> FIX, 3091 >> FIX, 3138 >> FIX, 3183 >> FIX,
			3228 >> FIX, 3271 >> FIX, 3314 >> FIX, 3355 >> FIX, 3396 >> FIX,
			3435 >> FIX, 3474 >> FIX, 3511 >> FIX, 3547 >> FIX, 3582 >> FIX,
			3617 >> FIX, 3650 >> FIX, 3681 >> FIX, 3712 >> FIX, 3742 >> FIX,
			3770 >> FIX, 3798 >> FIX, 3824 >> FIX, 3849 >> FIX, 3873 >> FIX,
			3896 >> FIX, 3917 >> FIX, 3937 >> FIX, 3956 >> FIX, 3974 >> FIX,
			3991 >> FIX, 4006 >> FIX, 4021 >> FIX, 4034 >> FIX, 4046 >> FIX,
			4056 >> FIX, 4065 >> FIX, 4074 >> FIX, 4080 >> FIX, 4086 >> FIX,
			4090 >> FIX, 4094 >> FIX, 4095 >> FIX, 4096 >> FIX};

	/**
	 * 定点数sin
	 * @param angle
	 * @return
	 */
	public static final int sin(int angle) {
        angle %= 360 << BITS;    // 360 degrees
        if(angle < 0) {
        	angle += 360 << BITS;
        }
        if (angle <= 90 << BITS){
            return SINES_X_FP[toInt(angle)];
        }else if (angle <= 180 << BITS){
            return SINES_X_FP[180-toInt(angle)];
        }else if (angle <= 270 << BITS){
            return -SINES_X_FP[toInt(angle)-180];
        }else{
            return -SINES_X_FP[360-toInt(angle)];
        }
	}
	

	/**
	 * 定点数cos
	 * @param angle
	 * @return
	 */
	public static final int cos(int angle) {
		return sin((90 << BITS) - angle);
	}

	/**
	 * 定点数tan
	 * @param angle
	 * @return
	 */
	public static final int tan(int angle) {
		return div(sin(angle), cos(angle));
	}

	/**
	 * 定点数cotan
	 * @param angle
	 * @return
	 */
	public static final int cot(int angle) {
		return div(cos(angle), sin(angle));
	}
	
	private static final int[] ARC_TAN_X_FP = {0, 36 >> FIX, 107 >> FIX,
			179 >> FIX, 251 >> FIX, 322 >> FIX, 394 >> FIX, 467 >> FIX,
			539 >> FIX, 612 >> FIX, 685 >> FIX, 759 >> FIX, 833 >> FIX,
			908 >> FIX, 983 >> FIX, 1059 >> FIX, 1136 >> FIX, 1213 >> FIX,
			1291 >> FIX, 1371 >> FIX, 1450 >> FIX, 1531 >> FIX, 1613 >> FIX,
			1697 >> FIX, 1781 >> FIX, 1867 >> FIX, 1954 >> FIX, 2042 >> FIX,
			2132 >> FIX, 2224 >> FIX, 2317 >> FIX, 2413 >> FIX, 2510 >> FIX,
			2609 >> FIX, 2711 >> FIX, 2815 >> FIX, 2922 >> FIX, 3031 >> FIX,
			3143 >> FIX, 3258 >> FIX, 3376 >> FIX, 3498 >> FIX, 3624 >> FIX,
			3753 >> FIX, 3887 >> FIX, 4025 >> FIX, 4168 >> FIX, 4316 >> FIX,
			4470 >> FIX, 4630 >> FIX, 4796 >> FIX, 4969 >> FIX, 5149 >> FIX,
			5338 >> FIX, 5535 >> FIX, 5742 >> FIX, 5960 >> FIX, 6188 >> FIX,
			6429 >> FIX, 6684 >> FIX, 6954 >> FIX, 7240 >> FIX, 7544 >> FIX,
			7868 >> FIX, 8215 >> FIX, 8587 >> FIX, 8988 >> FIX, 9420 >> FIX,
			9889 >> FIX, 10398 >> FIX, 10955 >> FIX, 11567 >> FIX,
			12242 >> FIX, 12991 >> FIX, 13828 >> FIX, 14770 >> FIX,
			15838 >> FIX, 17061 >> FIX, 18476 >> FIX, 20132 >> FIX,
			22100 >> FIX, 24477 >> FIX, 27407 >> FIX, 31112 >> FIX,
			35950 >> FIX, 42539 >> FIX, 52045 >> FIX, 66969 >> FIX,
			93814 >> FIX, 156420 >> FIX, 469355 >> FIX, MAX_VALUE >>FIX};
	
	/**
	 * 定点数arctan
	 * @param n
	 * @return -90 to 90
	 */
	public static final int atan(int n) {
		boolean flag = n < 0;
		if(flag) {
			n = -n;
		}
		int f1 = 0, f2 = ARC_TAN_X_FP.length - 1,ft = 0;
		while(f1 + 1 != f2) {
			ft = f1 + f2 >> 1;
			if(n < ARC_TAN_X_FP[ft]) {
				f2 = ft;
			}else {
				f1 = ft;
			}
		}
		return (flag? -f1: f1) << BITS;
	}
	
	/**
	 * 定点数arctan
	 * @param dy
	 * @param dx
	 * @return -90 to 269
	 */
	public static final int atan2(int dy, int dx) {
		if (dx > 0) {
			return atan(div(dy, dx));
		} else if (dx < 0) {
			return (180 << BITS) + atan(div(dy, dx));
		} else {
			if (dy == 0) {
				println("反正切参数错误：dy = 0, dx = 0");
			}
			return (dy > 0 ? 90 << BITS : -90 << BITS);
		}
	}

	/**
	 * 求e的指定次幂
	 * @param i
	 * @return
	 */
	public static final int exp(int i) {
		if (i == 0)
			return ONE;
		boolean flag = i < 0;
		i = Math.abs(i);
		int j = i >> BITS;
		int k = ONE;
		for (int l = 0; l < j / 4; l++)
			k = mul(k, E_SEED[4]);

		if (j % 4 > 0)
			k = mul(k, E_SEED[j % 4]);
		i &= MASK;
		if (i > 0) {
			int i1 = ONE;
			int j1 = 0;
			int k1 = 1;
			for (int l1 = 0; l1 < 16; l1++) {
				j1 += i1 / k1;
				i1 = mul(i1, i);
				k1 *= l1 + 1;
				if (k1 > i1 || i1 <= 0 || k1 <= 0)
					break;
			}

			k = mul(k, j1);
		}
		if (flag)
			k = div(ONE, k);
		return k;
	}

	
	/**
	 * 求对数
	 * @param i
	 * @return
	 */
	public static final int log(int i) {
		if (i <= 0){
			println("对数参数错误：" + i);
		}
		int j = 0;
		//boolean flag = false;
		int l;
		for (l = 0; i >= ONE << 1; l++)
			i >>= 1;

		int i1 = l * (2839 >> FIX);
		int j1 = 0;
		if (i < ONE)
			return -log(div(ONE, i));
		i -= ONE;
		for (int k1 = 1; k1 < 20; k1++) {
			int k;
			if (j == 0)
				k = i;
			else
				k = mul(j, i);
			if (k == 0)
				break;
			j1 += ((k1 % 2 != 0 ? 1 : -1) * k) / k1;
			j = k;
		}

		return i1 + j1;
	}

	
	/**
	 * 求i的指定次幂
	 * @param i
	 * @param j
	 * @return
	 */
	public static final int pow(int i, int j) {
		boolean flag = j < 0;
		int k = ONE;
		j = Math.abs(j);
		for (int l = j >> BITS; l-- > 0;)
			k = mul(k, i);

		if (k < 0){
			println("溢出：" + i + " ^ " + j + " = " + k);
		}
		if (i != 0)
			k = mul(k, exp(mul(log(i), j & MASK)));
		else
			k = 0;
		if (flag)
			return div(ONE, k);
		else
			return k;
	}

//	/**
//	 * 排序
//	 * 
//	 * @param list
//	 * @param above
//	 *            if above <0 then min->max else max->min
//	 */
//	public static <T extends Byte,Short,Integer,Long> void sequence(T[] list, int above) {
//		T temp;//, max, min;
//		boolean tag = true;
//		for (int i = list.length - 1; i >= 0; i--) {
//			for (int j = 0; j < i; j++) {
//				if (above < 0) {
//					if (list[j] < list[j + 1]) {
//						temp = list[j];
//						list[j] = list[j + 1];
//						list[j + 1] = temp;
//						tag = true;
//					}
//				} else {
//					if (list[j] > list[j + 1]) {
//						temp = list[j];
//						list[j] = list[j + 1];
//						list[j + 1] = temp;
//						tag = true;
//					}
//				}
//			}
//			if (tag == false){
//				break;
//			}
//		}
//	}

}