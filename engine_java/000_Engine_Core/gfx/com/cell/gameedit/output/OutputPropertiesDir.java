package com.cell.gameedit.output;


import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cell.CIO;
import com.cell.util.PropertyGroup;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
public class OutputPropertiesDir extends OutputProperties
{
	final private PropertyGroup config;
	final private String conf_code;
	
	final public String root;
	final public String file_name;

	final public String path;
	
	public OutputPropertiesDir(String file, PropertyGroup Config) throws Exception 
	{
		this.path 		= file.replace('\\', '/');
		this.root		= path.substring(0, path.lastIndexOf("/")+1);
		this.file_name	= path.substring(root.length());
		this.config 	= Config;
		this.conf_code	= null;
		init(Config);
	}

	public OutputPropertiesDir(String file) throws Exception
	{
		this.path 		= file.replace('\\', '/');
		this.root		= path.substring(0, path.lastIndexOf("/")+1);
		this.file_name	= path.substring(root.length());

		// 读入基础属性
		byte[] conf_data = loadRes(file_name, null);
		if (conf_data == null) {
			throw new FileNotFoundException(file);
		}
		this.conf_code = new String(conf_data, CIO.ENCODING);
		this.config = new PropertyGroup(conf_code, "=");
		super.init(config);
	}
	
	/***
	 * 是否单独输出每张图
	 * @return
	 */
	public boolean isTile() {
		String code = conf_code;
		Pattern pattern_tile = Pattern.compile("#<IMAGE TILE>\\s+\\w+");
		Matcher matcher = pattern_tile.matcher(code);
		if (matcher.find()) {
			String group = code.substring(matcher.start(), matcher.end());
			return group.endsWith("true");
		}
		return false;
	}
	
	/**
	 * 是否输出整图
	 * @return
	 */
	public boolean isGroup() {
		String code = conf_code;
		Pattern pattern_group = Pattern.compile("#<IMAGE GROUP>\\s+\\w+");
		Matcher matcher = pattern_group.matcher(code);
		if (matcher.find()) {
			String group = code.substring(matcher.start(), matcher.end());
			return group.endsWith("true");
		}
		return false;
	}
	
	/**
	 * 获得导出图片文件类型
	 * @return
	 */
	public String getImageExtentions() {
		String code = conf_code;
		Pattern pattern_tile = Pattern.compile("#<IMAGE TYPE>\\s+\\w+");
		Matcher matcher = pattern_tile.matcher(code);
		if (matcher.find()) {
			String group = code.substring(matcher.start(), matcher.end());
			String[] split = group.split("\\s");
			return split[split.length-1];
		}
		return "png";
	}
	
	@Override
	public void dispose() {}
	
	/**
	 * 读取本目录资源
	 * @param path
	 * @return
	 */
	public byte[] loadRes(String path, AtomicReference<Float> percent)
	{
		byte[] data = CIO.loadData(root + path);
		if (data == null) {
			System.err.println("SetResource : read error : " + root + path);
		} 
		return data;
	}
	
}



