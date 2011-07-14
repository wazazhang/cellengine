package com.cell.net.io;

import java.io.IOException;



/**
 * <b>
 * 标记纯手动序列化/反序列化的对象<br>
 * 实现类必须注册到{@link #ExternalizableFactory}<br>
 * 实现类也必须拥有无参数公共构造函数<br>
 * </b>
 */
public interface ExternalizableMessage extends MessageHeader
{
	public void readExternal(NetDataInput in) throws IOException ;
	
	public void writeExternal(NetDataOutput out) throws IOException ;
}
