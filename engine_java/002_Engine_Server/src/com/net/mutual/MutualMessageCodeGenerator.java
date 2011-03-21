package com.net.mutual;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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


public interface MutualMessageCodeGenerator
{
	/**
	 * 自动生成代码文件，或编解码规则文件。
	 * @param regist_types
	 * @return
	 */
	public String genMutualMessageCodec(Map<Integer, Class<?>> regist_types);
	
}
