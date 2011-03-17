package com.net.flash.message;

import java.io.File;
import java.io.IOException;

import com.cell.CIO;
import com.cell.io.CFile;
import com.net.ExternalizableFactory;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.mutual.MutualMessageCodec;

public class FlashMessageFactory extends ExternalizableFactory
{
	public FlashMessageFactory(MutualMessageCodec mutual_codec, Class<?>... classes) {
		super(mutual_codec, classes);
	}


	
}
