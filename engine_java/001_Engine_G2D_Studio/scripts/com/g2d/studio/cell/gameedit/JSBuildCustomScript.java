package com.g2d.studio.cell.gameedit;

import java.io.File;

import com.cell.CIO;

/**
 * 执行自定义脚本
 * @author zhangyifei
 *
 */
public interface JSBuildCustomScript 
{
	public void build(BuildProcess p, File dir);
	
	
}
