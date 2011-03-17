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
import com.cell.reflect.Parser;
import com.cell.sql.SQLStructCLOB;
/**
 * 适合表示短数组
 * @author WAZA
 *
 */
public class IntArrayPlane extends Vector<Integer> implements SQLStructCLOB
{
	private static final long serialVersionUID = 1L;

	public IntArrayPlane() {}
	
	public IntArrayPlane(int len) {
		super(len);
	}
	
	public IntArrayPlane(Collection<Integer> another) {
		super(another);
	}
	
	public IntArrayPlane(int[] another) {
		super(another.length);
		for (int d : another) {
			super.add(d);
		}
	}
	
	

	@Override
	public void decode(String text) throws IOException {
		String[] ds = text.split(",");
		for (String d : ds) {
			add(Parser.stringToObject(d, Integer.class));
		}
	}
	
	@Override
	public String encode() throws IOException {
		StringBuilder sb = new StringBuilder();
		for (Integer b : this) {
			sb.append(b+",");
		}
		return sb.toString();
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
