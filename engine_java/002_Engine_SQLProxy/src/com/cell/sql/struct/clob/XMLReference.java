package com.cell.sql.struct.clob;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.sql.SQLStructCLOB;

public class XMLReference<T> implements SQLStructCLOB, Serializable
{
	private static final long serialVersionUID = 1L;

	private T data;
	
	public XMLReference() {}
	
	public XMLReference(T data) {
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public void decode(String text) throws IOException 
	{
		StringReader in = new StringReader(text);
		int size = TextDeserialize.getInt(in);
		super.clear();
		for (int i = 0; i < size; i++) {
			this.add(TextDeserialize.getInt(in));
		}
	}

	public String encode() throws IOException {
		StringWriter out = new StringWriter(this.size() * 10);
		TextSerialize.putInt(out, super.size());
		for (Integer e : this) {
			TextSerialize.putInt(out, e);
		}
		return out.toString();
	}
}
