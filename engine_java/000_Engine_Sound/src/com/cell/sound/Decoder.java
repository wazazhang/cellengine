package com.cell.sound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Decoder
{
	abstract public String getName();
	
	abstract public SoundInfo decode(String resource, InputStream in) throws Exception;
	
	
}
