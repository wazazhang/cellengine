package com.cell.script.objective;

import java.util.Vector;

/**
 * @author waza
 * 提供用于解析 [instruction:param] 格式的脚本系统
 */
@SuppressWarnings("unchecked")
public abstract class IObjectiveFactory
{
	/**
	 * 调用build后,当前的编译状态
	 */
	private String 				build_state;
	
	private Vector<Objective>	Instructions = new Vector<Objective>();
	

	abstract protected Objective getObjective(String src, int begin, int end, String key, String value);
	
	/**
	 * 调用build后,当前的编译状态
	 */
	protected String getBuildState() {
		return build_state;
	}
	
	
	
	public String build(String src)
	{
		build_state = "";
		
		int count = src.length();

		for (int i=0; i<count; i++)
		{
			Objective obj = getObject(src, i);
			
			if (obj != null)
			{
				Instructions.addElement(obj);
				i = obj.Index + obj.Size - 1;
			}
			else
			{
				build_state += src.charAt(i);
			}
		}
		
		return build_state;
	}
	
	public Objective getObject(String src, int begin)
	{
		if (src.charAt(begin)=='[')
		{
			int end = src.indexOf(']', begin);
			if (end > begin) {
				String ins = src.substring(begin+1, end);
				String kv[] = ins.split(":", 2);
				String k = kv[0].trim();
				String v = kv.length>1?kv[1].trim():null;
				if (kv.length>0){
					Objective obj = getObjective(src, begin, end+1, k, v);
					if (obj!=null) {
						obj.Key = k;
						obj.Value = v;
						obj.Index = begin;
						obj.Size = end - begin + 1;
						return obj;
					}
				}
			}
		}
		return null;
	}
	
}
