package com.g2d.studio.io;

import java.io.InputStream;
import java.io.OutputStream;


public interface File 
{
    public String 			getName();

    public String			getParent();

    public File 			getParentFile();

    public File 			getChildFile(String name);
    

    public File[] 			listFiles();

    public boolean 			exists();
    
    public String 			getPath();

    
    public InputStream 		getInputStream();

    public OutputStream		getOutputStream();
    
    public byte[] 			readBytes();
    
    public void 			writeBytes(byte[] data); 
    
    public String 			readUTF();
    
    public void 			writeUTF(String data); 

}
