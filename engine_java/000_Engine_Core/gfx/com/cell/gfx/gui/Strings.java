
package com.cell.gfx.gui;

import java.util.EnumSet;
import java.util.HashMap;

import com.cell.CUtil;

/**
 * @author waza
 *
 */
@SuppressWarnings("unchecked")
public class Strings
{
	
	final protected HashMap<Enum, String> Elements ;
	final protected HashMap<Enum, String[]> Groups ;

	final public char FormatKey;
	final public char ArgKey;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public Strings(char formatkey, char argkey, int capacity){
		FormatKey = formatkey;
		ArgKey = argkey;
		Elements = new HashMap<Enum, String>(capacity);
		Groups = new HashMap<Enum, String[]>(capacity);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String[][] getReplaceTable()
	{
		return new String[][] {
				{ "\\n", "\n" },
				{ "\\b", " " },
				{ "\\t", "    " },
				};
	}
	
	
	public void put(Enum key, String text) {
		Elements.put(key, text);
	}
	
	public void put(Enum key, String[] texts){
		Groups.put(key, texts);
	}
	
	/* defined methods */
	
	public String get(Enum key){
		String ret = Elements.get(key);
		if (ret==null) {
			try {
				throw new Exception("Can not found String Error! K = " + key);
			}catch (Exception e) {
				e.printStackTrace();
				return key.toString();
			}
		}
		return ret;
	}
	
	/**
	 * get("there is a example string [\b],[\b]!", "1", "2") => "there is a example string [1],[2]!"
	 * 
	 * get("there is a example string [%1%],[%0%]!", "1", "2") => "there is a example string [2],[1]!"
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String get(Enum key, Object ... args){
		String ret = get(key);
		String[] sargs = new String[args.length];
		for (int i=args.length-1; i>=0; --i) {
			sargs[i] = "" + args[i];
		}
		return formatString(ret, key, sargs);
	}
	
	/**
	 * get("there is a example string [\b],[\b]!", 1, 2) => "there is a example string [1],[2]!"
	 * 
	 * get("there is a example string [%1%],[%0%]!", 1, 2) => "there is a example string [2],[1]!"
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String get(Enum key, long ... args){
		String ret = get(key);
		String[] sargs = new String[args.length];
		for (int i=args.length-1; i>=0; --i) {
			sargs[i] = "" + args[i];
		}
		return formatString(ret, key, sargs);
	}
	
	public String geti(Enum key, int index)
	{
		String[] ret = Groups.get(key);
		if (ret==null) {
			try {
				throw new Exception("Can not found String Array Error! K = " + key);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		return ret[index];
	}
	
	public String[] gets(Enum key)
	{
		String[] ret = Groups.get(key);
		if (ret==null) {
			try {
				throw new Exception("Can not found String Array Error! K = " + key);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	final public String formatString(String src, Enum key, String[] args)
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
				if (ch == FormatKey) {
					if (argCount < args.length) {
						sb.append(args[argCount]);
					}
					argCount ++;
				}
				else if (ch == ArgKey) {
					int t = src.indexOf(ArgKey, h+1);
					if (t>h) {
						String argnum = src.substring(h+1, t);
						int argi = -1;
						try{
							argi = Integer.parseInt(argnum);
							if (argi<args.length) {
								sb.append(args[argi]);
								h = t;
								argCount = args.length; // ignore arg count check
							}else{
								try {
									throw new Exception(
											"Format String Error! K = " + key + " : arg index out of bounds\n" + 
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
							"Format String Error! K = " + key + "\n" + 
							"\"" + CUtil.replaceString(src, FormatKey+"", "\\b") + "\" -> \"" + CUtil.arrayToString(args) + "\"");
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			return sb.toString();
		}
	}
	
	public boolean validate(Class enumClass) {
		boolean flag = true;
		for (Object e : EnumSet.allOf(enumClass)) {
			if (Elements.containsKey(e) || Groups.containsKey(e)) {
			}else{
				System.err.println("Strings key is not initialize : " + e);
				flag = false;
			}
		}
		return flag;
	}
	
	
	public static void main(String[] args) 
	{
		Strings strs = new Strings('\b', '%', 100);
	
		System.out.println(
				strs.formatString("there is a example string [\b],[\b]!", null, 
						new String[]{
						"1", "2"
				})
				);
		
		System.out.println(
				strs.formatString("there is a example string [%1%],[%0%] [%0%]!", null, 
						new String[]{
						"1", "2"
				})
				);
	}
}


