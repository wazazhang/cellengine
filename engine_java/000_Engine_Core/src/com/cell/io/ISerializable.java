package com.cell.io;

import java.io.IOException;


public interface ISerializable
{
	public void serialize(IOutput os) throws IOException ;
	
	public void deserialize(IInput is) throws IOException ;
	
}



