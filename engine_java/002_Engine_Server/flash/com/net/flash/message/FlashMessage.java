package com.net.flash.message;

import java.io.IOException;

import sun.security.jca.GetInstance;

import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.mutual.MutualMessage;

/**
 * 该类将按顺序序列化所有公共字段。
 * 所以，如果想在网络上传输数据，此类成员必须声明为public。
 * 如果此类包含其他复杂对象，则此成员实现ExternalizableMessage接口。
 * @author WAZA
 */
public class FlashMessage extends MutualMessage
{
	public FlashMessage() {}
	
}
