package com.cell.sql.struct.clob;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Vector;

import com.cell.io.TextDeserialize;
import com.cell.io.TextSerialize;
import com.cell.sql.SQLStructCLOB;

/**
 * 适合表示长数组
 * @author WAZA
 *
 */
public class IntArray extends Vector<Integer> implements SQLStructCLOB
{
	private static final long serialVersionUID = 1L;

	public IntArray() {}
	
	public IntArray(int len) {
		super(len);
	}
	
	public IntArray(Collection<Integer> another) {
		super(another);
	}
	
	public IntArray(int[] another) {
		super(another.length);
		for (int d : another) {
			super.add(d);
		}
	}
	
	
	synchronized public void decode(String text) throws IOException {
		StringReader in = new StringReader(text);
		int size = TextDeserialize.getInt(in);
		super.clear();
		super.ensureCapacity(size);
		for (int i=0; i<size; i++) {
			this.add(TextDeserialize.getInt(in));
		}
	}

	synchronized public String encode() throws IOException {
		StringWriter out = new StringWriter(this.size()*10);
		TextSerialize.putInt(out, super.size());
		for (Integer e : this) {
			TextSerialize.putInt(out, e);
		}
		return out.toString();
	}
	
	synchronized public int[] toIntArray() {
		int[] ret = new int[size()];
		int i=0;
		for (Integer d : this) {
			ret[i] = d;
			i++;
		}
		return ret;
	}
}
