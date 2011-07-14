package com.cell.net.io;

import java.io.IOException;



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
