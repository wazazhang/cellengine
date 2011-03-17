package com.cell.location;
import java.io.InputStream;
import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.cell.util.Properties;

public class TextCoverST 
{
	private static DefaultCovertST	convert_st;
	private static IStringCovert	convert_default;
	static {
		try {
			convert_default = convert_st = new DefaultCovertST();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public static void setDefaultStringCovert(IStringCovert covert) {
		convert_default = covert;
	}

	public static String covert(String src) {
		return convert_default.covert(src);
	}
	
	public static String covertS2T(String src) {
		return convert_st.covertST(src);
	}

	public static String covertT2S(String src) {
		return convert_st.covertTS(src);
	}
	
	abstract public static class ConvertMap implements IStringCovert
	{
		/** index of char value */
		protected char[] CharST;
		/** index of char value */
		protected char[] CharTS;
		
		public ConvertMap(Properties properties) 
		{
			CharST = new char[0xffff];
			CharTS = new char[0xffff];
			java.util.Properties map = properties.toJavaProperties();
			String string_s = map.get("STRING_S").toString();
			String string_t = map.get("STRING_T").toString();
			int count = Math.min(string_s.length(), string_t.length());
			for (int i = 0; i < count; i++) {
				char t = string_t.charAt(i);
				char s = string_s.charAt(i);
				CharST[s] = t;
				CharTS[t] = s;
			}
		}
		
		protected String covert(String src, char[] map)
		{
			if (src == null)
				return null;
			char[] dst = new char[src.length()];
			for (int i = src.length()-1; i >=0 ; --i) {
				int c = src.charAt(i);
				if (c < map.length && map[c] != 0) {
					dst[i] = ((char) map[c]);
				} else {
					dst[i] = ((char) c);
				}
			}
			return new String(dst);
		}
	}
	
	public static class DefaultCovertST extends ConvertMap
	{
		public boolean is_st = true;
		
		public DefaultCovertST() {
			super(new Properties(CIO.getInputStream("/com/cell/location/TextCoverST.properties")));
		}
		public DefaultCovertST(InputStream is) {
			super(new Properties(is));
		}
		public DefaultCovertST(Properties properties) {
			super(properties);
		}

		public String covertST(String src) {
			return super.covert(src, CharST);
		}

		public String covertTS(String src) {
			return super.covert(src, CharTS);
		}

		@Override
		public String covert(String src) {
			if (is_st) {
				return covertST(src);
			} else {
				return covertTS(src);
			}
		}
	}

	public static void main(String[] args) 
	{
		CAppBridge.init();
		
		System.out.println((new DefaultCovertST()).covert("你怎么不说话呢？"));
	}

}
