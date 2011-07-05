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
	public String getVersion();
	
	public void readMutual(MutualMessage msg, NetDataInput in) throws IOException;

	public void writeMutual(MutualMessage msg, NetDataOutput out) throws IOException;

}
