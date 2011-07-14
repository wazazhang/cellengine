package com.cell.net.io;

import java.util.Date;


public abstract class MutualMessageCodeGenerator
{
	/**
	 * 自动生成代码文件，或编解码规则文件。
	 * @param regist_types
	 * @return
	 */
	abstract public String genMutualMessageCodec(ExternalizableFactory factory);
	
	
	public String getVersion() {
		return new Date().toString();
	}
}
