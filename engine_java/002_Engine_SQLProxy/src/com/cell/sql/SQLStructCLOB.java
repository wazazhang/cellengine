package com.cell.sql;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;


/**
 * 用来描述存储在列中的简单的结构化字段，该简单是指该结构只保存java基础类型
 * @author WAZA
 */
public interface SQLStructCLOB
{
	void decode(String text) throws IOException;
	
	String encode() throws IOException;
}
