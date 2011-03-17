package com.g2d.studio.cell.gameedit;

import java.io.File;

import com.cell.CIO;

public interface BuildExternalScript 
{
	
	/**
	 * 检查以前是否已经存在导出资源
	 * @param p
	 * @param dir
	 * @param cpj_file_name
	 * @return
	 */
	public boolean	checkOutputExists(BuildProcess p, File dir, File cpj_file_name);
	
	/**
	 * 导出资源
	 * @param p
	 * @param dir
	 * @param cpj_file_name
	 */
	public void 	output(BuildProcess p, File dir, File cpj_file_name);
	
}
