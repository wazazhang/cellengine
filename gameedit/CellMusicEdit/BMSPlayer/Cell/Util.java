/*
 * WAZA
 * Created on 2006-7-8
 *
 */
package Cell;


/**
 * @author WAZA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Util
{

	/**
     * 
	 * @param src
	 * @param des
	 * @param start
	 * @return
	 */
	public static int charArrayIndexOf(char[] src, char des, int start)
	{
		for (int i = start; i < src.length; i++)
		{
			if (src[i] == des) return i;
		}
		return -1;
	}

	/**
     * 
     * @param src
     * @param des
	 * @param start
	 * @return
	 */
	public static int charArrayIndexOf(char[] src, char[] des, int start)
	{
		for (int i = start; i < src.length; i++)
		{
			if (charArrayCMP(src, start, des.length, des, 0, des.length)) return i;
		}
		return -1;
	}

	/**
     * 
     * @param src
     * @param des
     * @param start
     * @return
	 */
	public static int charArrayIndexOf(char[] src, char des, int start, int len)
	{
		for (int i = start; i < start + len; i++)
		{
			if (src[i] == des) return i;
		}
		return -1;
	}

	/**
     * 
     * @param src
     * @param des
     * @param start
     * @return
     */
	public static int charArrayIndexOf(char[] src, char[] des, int start, int len)
	{
		for (int i = start; i < start + len; i++)
		{
			if (charArrayCMP(src, start, des.length, des, 0, des.length)) return i;
		}
		return -1;
	}

	/**
     * 
     * @param src
     * @param des
     * @return
     */
	public static boolean charArrayCMP(char[] src, char[] des)
	{
		if (src.length != des.length) return false;
		for (int i = src.length - 1; i >= 0; i--)
		{
			if (src[i] != des[i]) return false;
		}
		return true;
	}

	/**
 * 
 * @param src
 * @param src_start
 * @param src_len
 * @param des
 * @param des_start
 * @param des_len
 * @return
 */
	public static boolean charArrayCMP(
			char[] src, int src_start, int src_len,
						char[] des, int des_start, int des_len)
	{
		if (src_len != des_len) return false;
		if (src_start + src_len > src.length) return false;
		if (des_start + des_len > des.length) return false;
		for (int i = src_len - 1; i >= 0; i--)
		{
			if (src[src_start + i] != des[des_start + i]) return false;
		}
		return true;
	}

	/**
     * <summary> 得到连续空白长度
     * \r 回车 
     * \n 换行 
     * \f 走纸换页 
     * \t 横向跳格 
     * \b 退格
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayIsBlank(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			char ch = str[i];
			if (ch == ' ' ||
					ch == '\r' ||
					ch == '\n' ||
					ch == '\f' ||
					ch == '\t' ||
					ch == '\b')
			{
				return value;
			}
			else
			{
				value++;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续非数字字母长度
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayIsNotWordNum(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			char ch = str[i];
			if (ch >= '0' && ch <= '9' ||
				ch >= 'a' && ch <= 'z' ||
				ch >= 'A' && ch <= 'Z')
			{
				return value;
			}
			else
			{
				value++;
			}
		}
		return value;
	}

	/**
 * <summary> 得到连续字母长度
 * @param str
 * @param start
 * @param end
 * @return
 */
	public static int charArrayIsWord(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			char ch = str[i];
			if (ch >= 'a' && ch <= 'z' ||
				ch >= 'A' && ch <= 'Z')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续字母和数字长度
     * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static int charArrayIsWordNum(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			char ch = str[i];
			if (ch >= '0' && ch <= '9' ||
				ch >= 'a' && ch <= 'z' ||
				ch >= 'A' && ch <= 'Z')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续十六进制长度
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayIsDigitHex(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			char ch = str[i];
			if (ch >= '0' && ch <= '9' ||
					ch >= 'a' && ch <= 'f' ||
	ch >= 'A' && ch <= 'F')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续阿拉伯数字长度
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayIsDigit(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			char ch = str[i];
			if (ch >= '0' && ch <= '9')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayHexToInt(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			value = value * 0x10;
			switch (str[i])
			{
				case '0': value += 0x00; break;
				case '1': value += 0x01; break;
				case '2': value += 0x02; break;
				case '3': value += 0x03; break;
				case '4': value += 0x04; break;
				case '5': value += 0x05; break;
				case '6': value += 0x06; break;
				case '7': value += 0x07; break;
				case '8': value += 0x08; break;
				case '9': value += 0x09; break;
				case 'a':
				case 'A': value += 0x0a; break;
				case 'b':
				case 'B': value += 0x0b; break;
				case 'c':
				case 'C': value += 0x0c; break;
				case 'd':
				case 'D': value += 0x0d; break;
				case 'e':
				case 'E': value += 0x0e; break;
				case 'f':
				case 'F': value += 0x0f; break;
				default: return value;
			}
		}
		return value;
	}

	/**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayDigitToInt(char[] str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length; i++)
		{
			value = value * 10;
			switch (str[i])
			{
				case '0': value += 0x00; break;
				case '1': value += 0x01; break;
				case '2': value += 0x02; break;
				case '3': value += 0x03; break;
				case '4': value += 0x04; break;
				case '5': value += 0x05; break;
				case '6': value += 0x06; break;
				case '7': value += 0x07; break;
				case '8': value += 0x08; break;
				case '9': value += 0x09; break;
				default: return value;
			}
		}
		return value;
	}

	/**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int charArrayDecimalToInt(char[] str, int start, int len, int mul)
	{
		int v = 0;
		int f = 0;
		int m = mul;
		boolean p = false;

		for (int i = start; i < start + len && i < str.length; i++)
		{
			int bit = -1;
			switch (str[i])
			{
				case '0': bit = 0; break;
				case '1': bit = 1; break;
				case '2': bit = 2; break;
				case '3': bit = 3; break;
				case '4': bit = 4; break;
				case '5': bit = 5; break;
				case '6': bit = 6; break;
				case '7': bit = 7; break;
				case '8': bit = 8; break;
				case '9': bit = 9; break;
				case '.':
					if (!p)
					{
						p = true;
						bit = 0;
					}
					break;
				default:
					break;
			}
			if (bit < 0) break;
			if (p)
			{
				f += bit * m;
				m = m / 10;
			}
			else
			{
				v += bit;
				v = v * 10;
			}
		}
		v = v / 10;
		//        CSUtil.println("v="+v+" f="+f);
		return v * mul + f;
	}
	//--------------------------------------------------------------------------------------------------------    

	/**
     * <summary> 得到连续空白长度
     * \r 回车 
     * \n 换行 
     * \f 走纸换页 
     * \t 横向跳格 
	 * \b 退格
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static int stringIsBlank(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch == ' ' ||
				ch == '\r' ||
				ch == '\n' ||
				ch == '\f' ||
				ch == '\t' ||
				ch == '\b')
			{
				return value;
			}
			else
			{
				value++;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续非数字字母长度
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int stringIsNotWordNum(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch >= '0' && ch <= '9' ||
				ch >= 'a' && ch <= 'z' ||
				ch >= 'A' && ch <= 'Z')
			{
				return value;
			}
			else
			{
				value++;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续字母长度
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int stringIsWord(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch >= 'a' && ch <= 'z' ||
					ch >= 'A' && ch <= 'Z')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
 * <summary> 得到连续字母和数字长度
 * @param str
 * @param start
 * @param end
 * @return
 */
	public static int stringIsWordNum(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch >= '0' && ch <= '9' ||
				ch >= 'a' && ch <= 'z' ||
				ch >= 'A' && ch <= 'Z')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续十六进制长度
     * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static int stringIsDigitHex(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch >= '0' && ch <= '9' ||
				ch >= 'a' && ch <= 'f' ||
				ch >= 'A' && ch <= 'F')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * <summary> 得到连续阿拉伯数字长度
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int stringIsDigit(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch >= '0' && ch <= '9')
			{
				value++;
			}
			else
			{
				return value;
			}
		}
		return value;
	}

	/**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int stringHexToInt(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i < str.length(); i++)
		{
			value = value * 0x10;
			switch (str.charAt(i))
			{
				case '0': value += 0x00; break;
				case '1': value += 0x01; break;
				case '2': value += 0x02; break;
				case '3': value += 0x03; break;
				case '4': value += 0x04; break;
				case '5': value += 0x05; break;
				case '6': value += 0x06; break;
				case '7': value += 0x07; break;
				case '8': value += 0x08; break;
				case '9': value += 0x09; break;
				case 'a':
				case 'A': value += 0x0a; break;
				case 'b':
				case 'B': value += 0x0b; break;
				case 'c':
				case 'C': value += 0x0c; break;
				case 'd':
				case 'D': value += 0x0d; break;
				case 'e':
				case 'E': value += 0x0e; break;
				case 'f':
				case 'F': value += 0x0f; break;
				default: return value;
			}
		}
		return value;
	}

	/**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
	public static int stringDigitToInt(String str, int start, int len)
	{
		int value = 0;
		for (int i = start; i < start + len && i<str.length(); i++)
		{
			value = value * 10;
			switch (str.charAt(i))
			{
				case '0': value += 0x00; break;
				case '1': value += 0x01; break;
				case '2': value += 0x02; break;
				case '3': value += 0x03; break;
				case '4': value += 0x04; break;
				case '5': value += 0x05; break;
				case '6': value += 0x06; break;
				case '7': value += 0x07; break;
				case '8': value += 0x08; break;
				case '9': value += 0x09; break;
				default: return value;
			}
		}
		return value;
	}

	/**
     * 
     * @param str
     * @param start
     * @param end
     * @return
	 */
	public static int stringDecimalToInt(String str, int start, int len, int mul)
	{
		int v = 0;
		int f = 0;
		int m = mul;
		boolean p = false;

		for (int i = start; i < start + len && i < str.length(); i++)
		{
			int bit = -1;
			switch (str.charAt(i))
			{
				case '0': bit = 0; break;
				case '1': bit = 1; break;
				case '2': bit = 2; break;
				case '3': bit = 3; break;
				case '4': bit = 4; break;
				case '5': bit = 5; break;
				case '6': bit = 6; break;
				case '7': bit = 7; break;
				case '8': bit = 8; break;
				case '9': bit = 9; break;
				case '.':
					if (!p)
					{
						p = true;
						bit = 0;
					}
					break;
				default:
					break;
			}
			if (bit < 0) break;
			if (p)
			{
				f += bit * m;
				m = m / 10;
			}
			else
			{
				v += bit;
				v = v * 10;
			}
		}
		v = v / 10;
		//        CSUtil.println("v="+v+" f="+f);
		return v * mul + f;
	}

	//----------------------------------------------------------------------------------------------------   

	/** 
     * <summary> 往一个data数组里写1字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @param   	写入值
	 */
	public static void write8(byte[] data, int p, byte val)
	{
		data[p++] = val;
	}
	/** 
     * <summary> 往一个data数组里写2字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @param   	写入值
	 */
	public static void write16(byte[] data, int p, short val)
	{
		data[p + 0] = (byte)(0x00ff & (val >> 0));
		data[p + 1] = (byte)(0x00ff & (val >> 8));
	}

	/** 
	 * <summary> 往一个data数组里写3字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
	 * @param		原数组
	 * @param		位置
	 * @param   	写入值
	 */
	public static void write24(byte[] data, int p, int val)
	{
		data[p + 0] = (byte)(0x00ff & (val >> 0));
		data[p + 1] = (byte)(0x00ff & (val >> 8));
		data[p + 2] = (byte)(0x00ff & (val >> 16));
	}

	/** 
     * <summary> 往一个data数组里写4字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @param   	写入值
	 */
	public static void write32(byte[] data, int p, int val)
	{
		data[p + 0] = (byte)(0x00ff & (val >> 0));
		data[p + 1] = (byte)(0x00ff & (val >> 8));
		data[p + 2] = (byte)(0x00ff & (val >> 16));
		data[p + 3] = (byte)(0x00ff & (val >> 24));
	}

	/** 
     * <summary> 往一个data数组里读1字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @return   	返回值
	 */
	public static byte read8(byte[] data, int p)
	{
		return data[p++];
	}

	/** 
     * <summary> 往一个data数组里读2字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @return   	返回值
	 */
	public static short read16(byte[] data, int p)
	{
		short val = (short)(
				(data[p + 0] << 0) & 0x00ff |
				(data[p + 1] << 8) & 0xff00
		);

		return val;
	}

	/** 
     * <summary> 往一个data数组里读3字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @return   	返回值
	 */
	public static int read24(byte[] data, int p)
	{
		int value = (
				(data[p + 0] << 0) & 0x000000ff |
				(data[p + 1] << 8) & 0x0000ff00 |
				(data[p + 2] << 16) & 0x00ff0000
		);
		return value;
	}

	/** 
     * <summary> 往一个data数组里读4字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @return   	返回值
	 */
	public static int read32(byte[] data, int p)
	{
		int value = (
				(data[p + 0] << 0) & 0x000000ff |
				(data[p + 1] << 8) & 0x0000ff00 |
				(data[p + 2] << 16) & 0x00ff0000 |
				(data[p + 3] << 24) & 0xff000000
		);
		return value;
	}

	//-----------------------------------------------------------------------------------------------

	/** 
	 * <summary> 往一个data数组里写1字节(0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 * @param		原数组
	 * @param		位置
	 * @param   	写入值
	 */
	public static void writeMSB8(byte[] data, int p, byte val)
	{
		data[p++] = val;
	}
	/** 
	 * <summary> 往一个data数组里写2字节(0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 * @param		原数组
	 * @param		位置
	 * @param   	写入值
	 */
	public static void writeMSB16(byte[] data, int p, short val)
	{
		data[p + 0] = (byte)(0x00ff & (val >> 8));
		data[p + 1] = (byte)(0x00ff & (val >> 0));
	}
	/** 
     * <summary> 往一个data数组里写3字节(0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 * @param		原数组
	 * @param		位置
	 * @param   	写入值
	 */
	public static void writeMSB24(byte[] data, int p, int val)
	{
		data[p + 0] = (byte)(0x00ff & (val >> 16));
		data[p + 1] = (byte)(0x00ff & (val >> 8));
		data[p + 2] = (byte)(0x00ff & (val >> 0));
	}
	/** 
     * <summary> 往一个data数组里写4字节(0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
     * @param		原数组
	 * @param		位置
	 * @param   	写入值
	 */
	public static void writeMSB32(byte[] data, int p, int val)
	{
		data[p + 0] = (byte)(0x00ff & (val >> 24));
		data[p + 1] = (byte)(0x00ff & (val >> 16));
		data[p + 2] = (byte)(0x00ff & (val >> 8));
		data[p + 3] = (byte)(0x00ff & (val >> 0));
	}

	/** 
     * <summary> 往一个data数组里读1字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
	 * @param		位置
	 * @return   	返回值
	 */
	public static byte readMSB8(byte[] data, int p)
	{
		return data[p++];
	}

	/** 
     * <summary> 往一个data数组里读2字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
	 * @param		位置
	 * @return   	返回值
	 */
	public static short readMSB16(byte[] data, int p)
	{
		short val = (short)(
				(data[p + 1] << 16) & 0x00ff0000 |
				(data[p + 0] << 24) & 0xff000000
		);
		return val;
	}

	/** 
     * <summary> 往一个data数组里读3字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @return   	返回值
	 */
	public static int readMSB24(byte[] data, int p)
	{
		int value = (
				(data[p + 2] << 8) & 0x0000ff00 |
				(data[p + 1] << 16) & 0x00ff0000 |
				(data[p + 0] << 24) & 0xff000000
		);
		return value;
	}

	/** 
     * <summary> 往一个data数组里读4字节(0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		原数组
     * @param		位置
	 * @return   	返回值
	 */
	public static int readMSB32(byte[] data, int p)
	{
		int value = (
				(data[p + 3] << 0) & 0x000000ff |
				(data[p + 2] << 8) & 0x0000ff00 |
				(data[p + 1] << 16) & 0x00ff0000 |
				(data[p + 0] << 24) & 0xff000000
		);
		return value;
	}


	//---------------------------------------------------------------------------------------------

	/** 
	 *  <summary> 在一个data数组里写一个可变长度数字(0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 *  @param	  data[]    源数据
	 *  @param   p         位置
	 *  @param   vaule     数据
	 *  @return            写了多少字节
	 */
	public static int writeVLQ(byte[] data, int p, int vaule)
	{
		byte[] temp = new byte[4];
		int length = 0;
		for (int i = 0; i < 4; i++)
		{
			temp[i] = (byte)(vaule >> (7 * i));
			length++;

			if ((vaule >> (7 * (i + 1))) <= 0)
			{
				temp[i] = (byte)(temp[i] & 0x7f);
				break;
			}
			else
			{
				temp[i] = (byte)(temp[i] | 0x80);
			}
		}

		for (int i = 0; i < length; i++)
		{
			data[p++] = temp[(length - 1) - i];
		}

		return length;
	}

	/** 
	 * <summary> 从一个data数组里读一个可变长度数字(0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 * @param	data[]    源数据
	 * @param	p         位置
	 * @return	int[0]读了多少字节;int[1]值
	 */
	public static int[] readVLQ(byte[] data, int p)
	{
		int Vaule = 0;
		int length = 0;
		for (int i = 0; i < 4; i++)
		{
			byte temp = data[p++];
			Vaule = (Vaule << 7) | (temp & 0x7f);
			length++;

			if ((temp & 0x80) == 0)
			{
				break;
			}
		}
		int[] ret = new int[2];
		ret[0] = length;
		ret[1] = Vaule;
		return ret;
	}

	//--------------------------------------------------------------------------------------------

	final static public void println()
	{
		System.Console.WriteLine();
	}

	final static public void println(String str)
	{
		System.Console.WriteLine(str);
	}

	final static public void print()
	{

		System.Console.WriteLine();
	}

	final static public void print(String str)
	{

		System.Console.Write(str);
	}

	final static public void print(byte[] data)
	{

		for (int i = 0; i < data.length; i++)
		{
			System.Console.Write(data[i] + ",");
		}
	}

	final static public void print(short[] data)
	{

		for (int i = 0; i < data.length; i++)
		{
			System.Console.Write(data[i] + ",");
		}
	}

	final static public void print(int[] data)
	{

		for (int i = 0; i < data.length; i++)
		{
			System.Console.Write(data[i] + ",");
		}
	}

	final static public void print(long[] data)
	{

		for (int i = 0; i < data.length; i++)
		{
			System.Console.Write(data[i] + ",");
		}
	}

	//------------------------------------------------------------------------------------------------------------------------

	public static byte[] loadFile(String fileName)
	{
		ubyte[] data;
		byte[] ret;
		data = System.IO.File.ReadAllBytes(fileName);

		ret = new byte[data.length];
		for (int i = 0; i < data.length; i++)
		{
			ret[i] = (byte)(data[i] & 0x00ff);
		}
		return ret;
	}


}
