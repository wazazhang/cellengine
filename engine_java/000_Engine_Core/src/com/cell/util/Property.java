package com.cell.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.reflect.Parser;


/**
 * @author waza
 *
 * Config.ini
 * 
 * OPTION1=123
 * OPTION2=op2
 * OPTIONS1=1,2,3,4,5
 * ...
 * 
 * Property<String> p = new Property<String>(){
 *     protected boolean putText(String k, String v){
 *         return put(k, v);
 *     }
 * };
 * p.loadText(".../Config.ini","=");
 * 
 * int Option1    = p.getInteger("OPTION1");
 * Option1 == 123
 * 
 * String Option2 = p.getString("OPTION2"); 
 * Option2 == op2
 * 
 * int[] Options1 = p.getIntegerArray("OPTIONS1",",");
 * Options1[0] == 1
 * Options1[1] == 2
 * Options1[2] == 3
 * Options1[3] == 4
 * Options1[4] == 5
 *
 * @param <T>
 */
public abstract class Property<T>
{
	public static boolean debug = false;
	public static String[] comment_text 	= {"#", "//"};
	public static String[] append_text 	= {"+", "\\"};
	public static HashMap<String, String> replace_text 	= new HashMap<String, String>();
	static
	{
		replace_text.put("\\n", "\n");
		replace_text.put("\\t", "\t");
	}
	
	
	Hashtable<String, T> Map = new Hashtable<String, T>();

	public Property(){
	}
	
	public Property(MarkedHashtable map){
		for (String key : map.keySet()) {
			putText(key, Parser.objectToString(map.get(key)));
		}
	}
	
	public Property(String text, String separator){
		loadText(text, separator);
	}
	
	public java.util.Properties toJavaProperties() {
		java.util.Properties ret = new java.util.Properties();
		ret.putAll(Map);
		return ret;
	}
	
	public void fromJavaProperties(java.util.Properties map){
		for (Entry<?, ?> e : map.entrySet()) {
			putObject(e.getKey().toString(), e.getValue());
		}
	}

	abstract public boolean putObject(String k, Object v) ;
	
	
	
	public String saveText(String separator) {
		String table = "";
		for (String key : Map.keySet()) {
			table += key + separator + Parser.objectToString(Map.get(key)) + "\n";
		}
		return table;
	}

	// 如果是注释
	public static boolean isComment(String line) {
		for (String comment : comment_text) {
			if (line.startsWith(comment)) {
				return true;
			}
		}
		return false;
	}
	
	// 如果是尾部出现 +
	public static boolean isNextAppend(String line) {
		for (String append : append_text) {
			if (line.endsWith(append)) {
				return true;
			}
		}
		return false;
	}
	
	public static String replaceValue(String value) {
		for (Entry<String, String> e : replace_text.entrySet()) {
			value = CUtil.replaceString(value, e.getKey(), e.getValue());
		}
		return value;
	}
	
	public void loadText(String text, String separator)
	{
		synchronized (Property.class) 
		{
			String[] lines = text.split("\n");
			
			String line = null;
			
			for (int i = 0; i < lines.length; i++)
			{
				try
				{
					if (line == null) {
						line = lines[i].trim();
					} else {
						line += lines[i].trim();
					}
					// 如果是注释
					if (isComment(line)){
						line = null;
						continue;
					}
					// 如果是尾部出现 +
					if (i < lines.length - 1 && isNextAppend(line)) {
						line = line.substring(0, line.length()-1);
						continue;
					}
					String kv[] = line.split(separator, 2);
					line = null;
					if (kv.length == 2) {
						putText(kv[0].trim(), replaceValue(kv[1].trim()));
						if (debug) {
							System.out.println(kv[0].trim() + "=" + kv[1].trim());
						}
					}
				}
				catch(Exception err){
					line = null;
					//err.printStackTrace();
				}
			}
			
		}
	}
	
	public String load(InputStream is) {
		try {
			String text = new String(CIO.readStream(is), CObject.ENCODING);
			loadText(text, "=");
			return text;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String load(byte[] data) {
		String text = "";
		try {
			text = new String(data, CObject.ENCODING);
			loadText(text, "=");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	abstract protected boolean putText(String k, String v);
	

	public void putAll(java.util.Map<String, String> map)
	{
		for (String k : map.keySet()) {
			putText(k, map.get(k));
		}
	}
	
	public Enumeration<String> getKeys()
	{
		return Map.keys();
	}
	
	public Enumeration<T> getValues()
	{
		return Map.elements();
	}
	
	public Set<String> keySet(){
		return Map.keySet();
	}
	
	public boolean put(String key, T value){
		return Map.put(key, value) == null;
	}

	
	public T get(String key){
		return Map.get(key);
	}
	
	
	public String getString(String key){
		T value = Map.get(key);
		if (value!=null){
			return value.toString();
		}
		return null;
	}
	
	public boolean getBoolean(String key) throws NumberFormatException{
		String v = getString(key);
		if (v==null) throw new NumberFormatException();
		return Boolean.parseBoolean(v);
	}
	
	public int getInteger(String key) throws NumberFormatException{
		String v = getString(key);
		if (v.startsWith("0x")){
			return Integer.parseInt(v,16);
		}else{
			return Integer.parseInt(v);
		}
	}
	
	public long getLong(String key) throws NumberFormatException{
		String v = getString(key);
		if (v.startsWith("0x")){
			return Long.parseLong(v,16);
		}else{
			return Long.parseLong(v);
		}
	}
	
	public float getFloat(String key){
		String v = getString(key);
		return Float.parseFloat(v);
	}
	
	// return with default
	
	
	public String getString(String key, String defaultValue){
		String ret = getString(key);
		if (ret==null){
			return defaultValue;
		}else{
			return ret;
		}
	}
	
	public boolean getBoolean(String key, boolean defaultValue){
		try{
			return getBoolean(key);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public int getInteger(String key, int defaultValue){
		try{
			return getInteger(key);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public long getLong(String key, long defaultValue){
		try{
			return getLong(key);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public float getFloat(String key, float defaultValue){
		try{
			return getFloat(key);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	
	
	public int[] getIntegerArray(String key, String separator)
	{
		String[] vs = getStringArray(key, separator);
		int[] ret = null;
		if (vs != null) {
			try {
				ret = new int[vs.length];
				for (int i = 0; i < vs.length; i++) {
					vs[i] = vs[i].trim();
					if(vs[i].startsWith("0x")){
						ret[i] = Integer.parseInt(vs[i],16);
					}else{
						ret[i] = Integer.parseInt(vs[i]);
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		return ret;
	}
	
	public String[] getStringArray(String key, String separator)
	{
		String value = getString(key);
		String[] ret = null;
		if (value != null) {
			ret = CUtil.splitString(value, separator);
		}
		return ret;
	}
}
