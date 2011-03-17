package com.cell.gameedit.output;


import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CIO;
import com.cell.util.PropertyGroup;


/**
 * 如何将编辑器资源解析成单位
 * @author WAZA
 */
public class OutputPropertiesDir extends OutputProperties
{
	final public String root;
	final public String file_name;
	
	public OutputPropertiesDir(String file) throws Exception
	{
		super(file);
		this.root 			= path.substring(0, path.lastIndexOf("/")+1);
		this.file_name		= path.substring(root.length());
		
		// 读入基础属性
		byte[] conf_data = loadRes(file_name, null);
		if (conf_data == null) {
			throw new FileNotFoundException(path);
		}
		String conf = new String(conf_data, CIO.ENCODING);
		PropertyGroup config = new PropertyGroup(conf, "=");
		
		super.init(config);
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



