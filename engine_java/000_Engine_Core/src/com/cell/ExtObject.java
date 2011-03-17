package com.cell;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.TreeMap;

import com.cell.util.MarkedHashtable;
import com.cell.util.Pair;

/**
 * @author WAZA
 * 提供了对序列化支持的对象
 */
public abstract class ExtObject implements Externalizable
{
	/***
	 * 当反序列化结束后被调用<br>
	 * @param data
	 */
	abstract protected void onRead(MarkedHashtable data);

	/***
	 * 当序列化开始前被调用<br>
	 * @param data
	 */
	abstract protected void onWrite(MarkedHashtable data);
	
//	--------------------------------------------------------------------------------------------------------
	@Override
	final public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		Object data = in.readObject();
		// old version
		if (data instanceof MarkedHashtable) {
			MarkedHashtable	data_group = (MarkedHashtable)data;
			onRead(data_group);
			return;
		}
		// new version
		else if (data instanceof ArrayList<?>) {
			ArrayList<Pair<String, Object>> stream_data = (ArrayList)data;
			MarkedHashtable	data_group = new MarkedHashtable(stream_data.size());
			for (Pair<String, Object> kv : stream_data) {
				data_group.put(kv.getKey(), kv.getValue());
			}
			onRead(data_group);
			return;
		}
	}
	
	@Override
	final public void writeExternal(ObjectOutput out) throws IOException 
	{
		MarkedHashtable	data_group = new MarkedHashtable();
		onWrite(data_group);
		TreeMap<String, Object> sortmap = new TreeMap<String, Object>(data_group);
		ArrayList<Pair<String, Object>> stream_data = new ArrayList<Pair<String,Object>>(sortmap.size());
		for (String key : sortmap.keySet()) {
			Object value = sortmap.get(key);
			stream_data.add(new Pair<String, Object>(key, value));
		}
		out.writeObject(stream_data);
	}
	
}
