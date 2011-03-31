package com.g2d.studio.cell.gameedit;

import java.io.File;

import com.cell.CIO;

public interface BuildExternalScript 
{
	/**
	 * 是否决定使用此导出脚本。
	 * 比如导出effect使用图块输出，导出avatar时使用整图输出。
	 * @param p					
	 * @param dir				当前工程文件夹
	 * @param cpj_file_name		cpj工程文件
	 * @param output_properties 导出脚本
	 * @return
	 */
	public boolean	selectOuputProperties(BuildProcess p, File dir, File cpj_file_name, String output_properties);
	
	/**
	 * 检查以前是否已经存在导出资源
	 * @param p
	 * @param dir				当前工程文件夹
	 * @param cpj_file_name		cpj工程文件
	 * @return
	 */
	public boolean	checkOutputExists(BuildProcess p, File dir, File cpj_file_name);
	
	/**
	 * 导出资源
	 * @param p
	 * @param dir				当前工程文件夹
	 * @param cpj_file_name		cpj工程文件
	 */
	public void 	output(BuildProcess p, File dir, File cpj_file_name);
	
}
