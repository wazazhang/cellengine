package com.cell.net.io.flash;

import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.MutualMessageCodec;

public class FlashMessageFactory extends ExternalizableFactory
{
	public FlashMessageFactory(
			MutualMessageCodec mutual_codec, 
			Class<?>... classes) 
	{
		super(mutual_codec, classes);
	}


	
}
