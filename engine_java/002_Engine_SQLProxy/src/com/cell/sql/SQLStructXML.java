package com.cell.sql;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import sun.font.EAttribute;

import com.cell.io.BigIODeserialize;
import com.cell.io.BigIOSerialize;
import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;


/**
 * 将java对象序列化成xml
 * @author WAZA
 * @see Serializable
 * @see Externalizable
 */
public interface SQLStructXML// extends Serializable
{
	
}
