package com.cell.util.zip;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public interface ZipExtNode extends ZipNode
{
	void readExternal(ObjectInput in) throws IOException, ClassNotFoundException ;
	
	void writeExternal(ObjectOutput out) throws IOException ;
}
