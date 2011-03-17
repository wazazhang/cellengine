package com.net.mutual;

import java.io.IOException;

import com.net.NetDataInput;
import com.net.NetDataOutput;


/**
 * @see MutualMessage
 * @author WAZA
 */
public interface MutualMessageCodec
{
	public void readExternal(MutualMessage msg, NetDataInput in) throws IOException;

	public void writeExternal(MutualMessage msg, NetDataOutput out) throws IOException;

}
