
package com.cell.location;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.util.Config;
import com.cell.util.Properties;

/**
 * @author WAZA <br>
 * <br>
 * System.out.println(get("there is a example string [\b],[\b]!", "1", "2"));<br>
 * there is a example string [1],[2]!<br>
 * "\b" is a FormatKey<br>
 * 
 * <br>
 * 
 * System.out.println(get("there is a example string [%2%],[%1%]!", "1", "2"));<br>
 * <br>there is a example string [2],[1]!<br>
 * 
 * "%" is a FormatArgKey <br>
 * <br>
 */

public class StringManager
{
	final protected HashMap<Object, String> Elements = new HashMap<Object, String>();

	public String[][] ReplaceTable = new String[][] { { "\\n", "\n" }, };
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public StringManager(){}
	
	
	public StringManager(Class<?> cls, String file){
		load(cls, file);
	}
	
	@SuppressWarnings("unchecked")
	public void load(Class<?> cls, String file)
	{
		if (cls.isEnum())
		{
			Class<? extends Enum> ecls = (Class<? extends Enum>)cls;
			Properties enums = new Properties();
			InputStream is = CIO.loadStream(file);
			if (is != null) {
				enums.load(is);
				for (String key : enums.keySet()){
					Enum e = Enum.valueOf(ecls, key.toString());
					put(e, enums.getString(key.toString()));
				}
				for (Object e : EnumSet.allOf(ecls)) {
					if (!Elements.containsKey(e)) {
						System.err.println("Strings key is not initialize : " + e);
					}
				}
			}
		}
		else 
		{
			try {
				Map<Field, Object> map = Config.load((Class<? extends Config>)cls, file);
				for (Field key : map.keySet()) {
					Object value = map.get(key);
					String v = ""+value;
					if (value instanceof String) {
						key.set(null, replaceText(v));
					}
					put(key, v);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String replaceText(String text) {
		for (String[] replace : ReplaceTable) {
			text = CUtil.replaceString(text, replace[0], replace[1]);
		}
		return text;
	}
	
	public void put(Object key, String text) {
		Elements.put(key, replaceText(text));
	}
	
	public String get(Object key){
		String ret = Elements.get(key);
		if (ret==null) {
			return "" + key;
		}
		return ret;
	}
	
	/**
	 * get("there is a example string [\b],[\b]!", "1", "2") => "there is a example string [1],[2]!"
	 * 
	 * get("there is a example string [%2%],[%1%]!", "1", "2") => "there is a example string [2],[1]!"
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String get(Object key, Object ... args){
		String ret = get(key);
		return StringConfig.formatString(ret, key, args);
	}
	


}


