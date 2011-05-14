package com.net.mutual;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Fields;
import com.cell.reflect.Parser;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;


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
