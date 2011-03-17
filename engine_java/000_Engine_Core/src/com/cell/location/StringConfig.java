package com.cell.location;

import com.cell.CUtil;
import com.cell.util.Config;

public class StringConfig extends Config
{
	public static class ConfigKey
	{
		/**顺序格式化参数*/
		final public char FormatKey;
		
		/**位置格式化参数*/
		final public char FormatArgKeyStart, FormatArgKeyEnd;
		
		public ConfigKey(char key, char arg_key_start, char arg_key_end) {
			this.FormatKey			= key;
			this.FormatArgKeyStart	= arg_key_start;
			this.FormatArgKeyEnd	= arg_key_end;
		}
	}
	
	static ConfigKey default_config_key = new ConfigKey('\b', '%', '%');
	
	public static void setDefaultConfigKey(ConfigKey key) {
		if (key != null) {
			default_config_key = key;
		} else {
			throw new NullPointerException();
		}
	}
	
	/**
	 * <br>
	 * System.out.println(formatString("there is a example string [\b],[\b]!", "1", "2"));<br>
	 * there is a example string [1],[2]!<br>
	 * "\b" is a {@link ConfigKey.FormatKey}<br>
	 * <br>
	 * System.out.println(formatString("there is a example string [%2%],[%1%]!", "1", "2"));<br>
	 * <br>there is a example string [2],[1]!<br>
	 * "%" is a {@link ConfigKey.FormatArgKey} <br>
	 * <br>
	 * @param src
	 * @param args
	 * @return
	 * @author WAZA
	 */
	public static String formatString(String src, Object... args) {
		return formatKeyString(src, default_config_key, args);
	}
	
	public static String formatKeyString(String src, ConfigKey key, Object[] args)
	{
		if(args==null || args.length<=0)
		{
			return src;
		}
		else
		{
			int argCount = 0;
			
			StringBuilder sb = new StringBuilder();
			
			int size = src.length();
			for (int h = 0; h < size; h++)
			{
				char ch = src.charAt(h);
				
				if (ch == key.FormatKey) // eg: \b \b \b
				{
					if (argCount < args.length) {
						sb.append(args[argCount]);
					}
					argCount ++;
				}
				else if (ch == key.FormatArgKeyStart) // eg: %1% %2% %3%
				{
					int t = src.indexOf(key.FormatArgKeyEnd, h+1);
					if (t>h) {
						String argnum = src.substring(h+1, t);
						int argi = -1;
						try{
							argi = Integer.parseInt(argnum) - 1;
							if (argi < args.length) {
								sb.append(args[argi]);
								h = t;
								argCount = args.length; // ignore arg count check
							}else{
								try {
									throw new Exception(
											"Format String Error! (argi >= args.length) K = " + key + " : arg index out of bounds\n" + 
											"\"" + src + "\" -> \"" + CUtil.arrayToString(args) + "\"");
								}catch (Exception e) {
									e.printStackTrace();
								}
							}
						}catch (Exception e) {
						}
					}
				}
				else{
					sb.append(ch);
				}
			}
			
			if (argCount != args.length) {
				try {
					throw new Exception(
							"Format String Error! (argCount != args.length) " +
							"K = [" + key.FormatKey + ", " + key.FormatArgKeyStart + ", " + key.FormatArgKeyEnd + "]\n" + 
							"\"" + src + "\" -> \"" + CUtil.arrayToString(args) + "\"");
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return sb.toString();
		}
	}
	
	
}
