package com.cell.rpg.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.cell.rpg.RPGObject;

public class Util
{
	
	final static public void wirteObjects(ArrayList<RPGObject> objects, ObjectOutputStream oos) throws IOException
	{
		ArrayList<RPGObject> outputs = new ArrayList<RPGObject>(objects);
		oos.writeObject(outputs);
	}
	
	final static public ArrayList<RPGObject> readObjects(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		@SuppressWarnings("unchecked")
		ArrayList<RPGObject> outputs = (ArrayList<RPGObject>)ois.readObject();
		return outputs;
	}
	
}
