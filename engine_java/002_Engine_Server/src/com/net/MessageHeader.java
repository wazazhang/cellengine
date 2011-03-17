package com.net;

import java.io.ObjectStreamException;
import java.io.Serializable;



public abstract class MessageHeader implements Serializable
{
	private static final long serialVersionUID = 1L;

//	------------------------------------------------------------------------------------------
	
//	/**匹配Request和Response的值，如果为0，则代表为Notify*/
//	transient public int		PacketNumber	= 0;

//	------------------------------------------------------------------------------------------
	
	/***
	 * 当反序列化结束后被调用, 仅普通序列化时被调用<br>
	 * @param data
	 */
	protected void onReadResolve(){}

	/***
	 * 当序列化开始前被调用, 仅普通序列化时被调用<br>
	 * @param data
	 */
	protected void onWriteReplace(){}
	
//	------------------------------------------------------------------------------------------
	
	private Object writeReplace() throws ObjectStreamException {
		onWriteReplace();
		return this;
	}
	private Object readResolve() throws ObjectStreamException {
		onReadResolve();
		return this;
	}
	
}
