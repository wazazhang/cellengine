package com.cell.util;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileFilters
{
	protected StringFilters filter_dir;
	protected StringFilters filter_file;
	
	/**
	 * <pre>
	 * 前缀：+ 代表包含(默认)，- 代表不包含。
	 * 后缀：/ 表示是个目录。
	 * 	比如: -.svn/ (排除所有.svn目录)
	 * 多项时用 ; 分隔。
	 * 	比如: +.png;+.jpg (只匹配.png和.jpg)
	 * @param regex
	 */
	public FileFilters(String regex) 
	{
		String dir_regex = "";
		String file_regex = "";
		String[] fts = regex.trim().split(";");
		for (int i=0; i<fts.length; i++) {
			if (fts[i].endsWith("/")) {
				dir_regex += fts[i].substring(0, fts[i].length()-1);
				if (i < fts.length - 1) {
					dir_regex += ";";
				}
			} else {
				file_regex += fts[i];
				if (i < fts.length - 1) {
					file_regex += ";";
				}
			}
		}
		this.filter_dir = new StringFilters(dir_regex);
		this.filter_file = new StringFilters(file_regex);
	}

	/**
	 * @param text
	 * @return true 符合， false 排除
	 */
	public boolean accept(File file) 
    {
		if (file.isDirectory()) {
			return filter_dir.accept(file.getName());
		} else {
			return filter_file.accept(file.getName());
		}
    }
	
	/**
	 * @param text
	 * @return true 符合， false 排除
	 */
	public boolean acceptPath(File file) {
		if (file.isDirectory()) {
			return filter_dir.accept(file.getPath());
		} else {
			return filter_file.accept(file.getPath());
		}
	}
	
	public static String usage(String line_prefix)
	{
		return 
		line_prefix + "前缀：+ 代表包含(默认)，- 代表不包含。\n" +
		line_prefix + "后缀：/ 表示是个目录。\n" +
		line_prefix + "	比如:-/.svn(排除所有/.svn目录)\n" +
		line_prefix + "多项时用 ; 分隔。\n" +
		line_prefix + "	比如:+.png;+.jpg;-.bmp，(只匹配.png和.jpg，并排除.bmp)";
	}
	
	public static void main(String args[])
	{
		FileFilters ff = new FileFilters(
				"-project_tw.g2d.save$/;" +
				"-.svn$/;" +
				"+.list$;" +
				"+.properties$");
		
		validate(ff, new File("E:\\Work\\edit\\resource"));
	}
	
	private static void validate(FileFilters ff, File file) {
		if (ff.accept(file)) {
			if (file.isDirectory()) {
				for (File sub : file.listFiles()) {
					validate(ff, sub);
				}
			} else {
				System.out.println("check : " + file);
			}
		} else {
//			System.out.println("ignore : " + file);
		}
	}
}
