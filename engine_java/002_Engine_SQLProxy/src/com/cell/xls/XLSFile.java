package com.cell.xls;

import java.io.File;
import java.io.Serializable;

public class XLSFile implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	final public String xls_file;
	
	public XLSFile(String file) {
		this.xls_file = file;
	}
	
	@Override
	public String toString() {
		return xls_file;
	}
}
