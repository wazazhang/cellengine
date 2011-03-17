package com.cell.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class JSerializableRef implements ISerializable 
{
	public Object object ;
	
	public JSerializableRef() {}
	
	public JSerializableRef(Object object) throws IOException{
		this.object = object;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JSerializableRef) {
			return this.object.equals(((JSerializableRef) obj).object);
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return object.toString();
	}
	
	public void deserialize(IInput is) throws IOException 
	{
		// get trunk data
		byte[] data = is.getBytes();
		
		// make object
		ByteArrayInputStream bytes = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bytes);
        try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        ois.close();
        bytes.close();
	}
	
	public void serialize(IOutput os) throws IOException 
	{
		// make trunk data
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bytes);
        oos.writeObject(object);
        byte[] data = bytes.toByteArray();
        oos.close();
        bytes.close();
        // put data
		os.putBytes(data);
	}
	
}
