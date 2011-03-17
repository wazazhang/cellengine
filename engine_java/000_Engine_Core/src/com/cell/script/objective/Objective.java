package com.cell.script.objective;


/**
 * @author waza
 * [instruction:param] 格式的脚本系统的一个指令
 */
abstract public class Objective<V>
{
	public String Key;
	public String Value;
	public int Size;
	public int Index;
	

	abstract public V getValue();
	
	
//	public String getKey(){
//		return Key;
//	}
//	
//	public int getSize(){
//		return Size;
//	}
//	public int getIndex(){
//		return Index;
//	}

}
