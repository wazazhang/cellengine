package com.cell.sql.struct.clob;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.sql.SQLStructCLOB;

/**
 * 适合表示长数组
 * @author WAZA
 *
 */
public class IntSet extends LinkedHashSet<Integer> implements SQLStructCLOB
{
	private static final long serialVersionUID = 1L;

	public IntSet() {}
	
	public IntSet(int len) {
		super(len);
	}
	
	public IntSet(Collection<Integer> another) {
		super(another);
	}
	
	synchronized public void decode(String text) throws IOException {
		StringReader in = new StringReader(text);
		int size = TextDeserialize.getInt(in);
		super.clear();
		for (int i = 0; i < size; i++) {
			this.add(TextDeserialize.getInt(in));
		}
	}

	synchronized public String encode() throws IOException {
		StringWriter out = new StringWriter(this.size() * 10);
		TextSerialize.putInt(out, super.size());
		for (Integer e : this) {
			TextSerialize.putInt(out, e);
		}
		return out.toString();
	}
}
