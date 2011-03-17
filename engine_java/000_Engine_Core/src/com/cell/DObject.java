package com.cell;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.cell.util.MarkedHashtable;

/**
 * @author WAZA
 * 提供了对序列化支持的对象
 */
public abstract class DObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	transient private MarkedHashtable	data_group;
	
	protected DObject(){
		init_field();
		init_transient();
	}
	
	/**
	 * 初始化可序列化字段 <br>
	 * <font color="#ff0000"> 继承的子类不要忘记调用 super.init_field() </font>
	 */
	protected void init_field(){}
	
	/**
	 * 初始化不可序列化字段, 该方法将在构造函数和反序列化后调用 <br>
	 * <font color="#ff0000"> 继承的子类不要忘记调用 super.init_transient() </font>
	 */
	protected void init_transient(){}
	
	final protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	final protected Object readResolve() throws ObjectStreamException {
		init_transient();
		return this;
	}
	
//	/***
//	 * 当反序列化结束后被调用<br>
//	 * <font color="#ff0000">可以读出为保持兼容性而临时添加的字段</font>
//	 * @param data
//	 */
//	protected void onRead(MarkedHashtable data){}
//
//	/***
//	 * 当序列化开始前被调用<br>
//	 * <font color="#ff0000">可以写入为保持兼容性而临时添加的字段</font>
//	 * @param data
//	 */
//	protected void onWrite(MarkedHashtable data){}
	
//	private void writeObject(java.io.ObjectOutputStream out) throws IOException {   }   
//	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {   }
//	final public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {}
//	final public void writeExternal(ObjectOutput out) throws IOException {}
//
//	final protected Object writeReplace() throws ObjectStreamException {
//		try{
//			data_group = new MarkedHashtable();
//			onWrite(data_group);
//			System.out.println("write data group size " + data_group.size());
//		}catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return this;
//	}
//	
//	final protected Object readResolve() throws ObjectStreamException {
//		try{
//			if (data_group!=null) {
//				System.out.println("read data group size " + data_group.size());
//			}
//			onRead(data_group);
//			init_transient();
//		}catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return this;
//	}
	
//  ==Serializable==
//	writeReplace invoked 
//	writeObject invoked 
//	readObject invoked 
//	readResolve invoked 
	
//	==Externalizable==
//	writeReplace invoked 
//	writeExternal invoked 
//	readExternal invoked 
//	readResolve invoked 
	
	
}
