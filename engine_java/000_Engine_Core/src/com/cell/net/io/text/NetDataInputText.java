package com.cell.net.io.text;

import java.io.DataInput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.io.ExternalizableUtil;
import com.cell.io.TextDeserialize;
import com.cell.net.io.BaseNetDataInput;
import com.cell.net.io.ExternalizableFactory;
import com.cell.net.io.ExternalizableMessage;
import com.cell.net.io.MutualMessage;
import com.cell.net.io.NetDataInput;
import com.cell.net.io.NetDataTypes;

public class NetDataInputText extends BaseNetDataInput
{	
	final TextDeserialize buffer ;

	public NetDataInputText(StringReader reader, ExternalizableFactory factory) {
		super(factory);
		this.buffer = new TextDeserialize(reader);
	}
	
	public int skipBytes(int n) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public <T> T readObject(Class<T> type) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public String readUTF() throws IOException {
		return buffer.getString();
	}

	public boolean readBoolean() throws IOException {
		return buffer.getBoolean();
	}
	
	public byte readByte() throws IOException {
		return buffer.getByte();
	}
	
	public char readChar() throws IOException {
		return buffer.getChar();
	}
	
	public double readDouble() throws IOException {
		return buffer.getDouble();
	}
	
	public float readFloat() throws IOException {
		return buffer.getFloat();
	}
	
	public void readFully(byte[] b, int off, int len) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public void readFully(byte[] b) throws IOException {
		throw new NotImplementedException("Not Support Method");
	}
	
	public int readInt() throws IOException {
		return buffer.getInt();
	}
	
	public String readLine() throws IOException {
		throw new NotImplementedException();
	}
	
	public long readLong() throws IOException {
		return buffer.getLong();
	}
	
	public short readShort() throws IOException {
		return buffer.getShort();
	}
	
	public int readUnsignedByte() throws IOException {
		return buffer.getUnsignedByte();
	}
	
	public int readUnsignedShort() throws IOException {
		return buffer.getUnsignedShort();
	}

//	--------------------------------------------------------------------------------------------------------------
	
//	--------------------------------------------------------------------------------------------------------------
	
	@Override
	public <T extends Date> T readDate(Class<T> cls) throws IOException {
		try {
			T ret = cls.newInstance();
			long time = readLong();
			ret.setTime(time);
			return ret;
		} catch (IOException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new IOException(e2);
		}
	}
}
