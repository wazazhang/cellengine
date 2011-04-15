package com.cell.util;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StringFilters 
{
	ArrayList<Pattern> filters_add = new ArrayList<Pattern>();
	ArrayList<Pattern> filters_dec = new ArrayList<Pattern>();
	
	/**
	 * <pre>
	 * + 代表包含(默认)，- 代表不包含。
	 * 	比如: -.svn (排除所有.svn目录)
	 * 多项时用 ; 分隔。
	 * 	比如: +.png;+.jpg (只匹配.png和.jpg)
	 * @param regex
	 */
	public StringFilters(String regex) 
	{
		String[] fts = regex.trim().split(";");
		for (String ft : fts) {
			if (!ft.isEmpty()) {
				if (ft.startsWith("-")) {
					ft = ft.substring(1);
					filters_dec.add(Pattern.compile(ft));
		    	} else if (ft.startsWith("+")) {
		    		ft = ft.substring(1);
		    		filters_add.add(Pattern.compile(ft));
		    	} else {
		    		ft = ft.substring(1);
		    		filters_add.add(Pattern.compile(ft));
		    	}
			}
		}
	}

	/**
	 * @param text
	 * @return true 符合， false 排除
	 */
	public boolean accept(String text) 
    {
		if (!filters_dec.isEmpty()) {
    		// 判断所有需要排除的
    		for (Pattern ft : filters_dec) {
    			if (ft.matcher(text).find()) {
        			return false;
    			}
    		}
		}
		if (!filters_add.isEmpty()) {
			// 判断所有需要包含的
    		for (Pattern ft : filters_add) {
    			if (ft.matcher(text).find()) {
					return true;
				}
    		}
    		return false;
		}
    	return true;
    }
	
	public boolean accept(File file) 
	{
		String fname = file.getPath();
		return accept(fname);
	}
	
	public static String usage(String line_prefix)
	{
		return 
		line_prefix + "+ 代表包含(默认)，- 代表不包含。\n" +
		line_prefix + "	比如:-\\*+.svn(排除所有.svn目录)\n" +
		line_prefix + "多项时用 ; 分隔。\n" +
		line_prefix + "	比如:+.png;+.jpg;-.bmp，(只匹配.png和.jpg，并排除.bmp)";
	}
}
