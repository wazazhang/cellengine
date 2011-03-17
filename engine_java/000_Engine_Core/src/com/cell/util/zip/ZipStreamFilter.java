package com.cell.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;

public interface ZipStreamFilter
{
//	public ObjectOutputStream createObjectOutputStream(OutputStream os) throws IOException;
	
//	public ObjectInputStream createObjectInputStream(InputStream is) throws IOException;
	
//	public ZipExtNode createExtNode(ZipEntry entry, Class<?> type);
	
	
	public ZipNode readNode(ByteArrayInputStream bais, Class<? extends ZipNode> type) throws Exception;
	
	public void writeNode(ByteArrayOutputStream baos, ZipNode node) throws Exception ;

}

