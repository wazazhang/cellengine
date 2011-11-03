package com.cell.xls;

import java.util.Arrays;
import java.util.HashSet;

import jxl.Sheet;

import com.cell.sql.SQLColumn;
import com.cell.sql.SQLTableRow;

public interface XLSRowFactory<V extends SQLTableRow<?>> 
{
	/**创建行实体*/
	public V createInstance();
	
//	/**接受的Sheet*/
//	public boolean accept(Sheet st);
	
	/**是否转换某个cell的文本数据*/
	public String convertField(Sheet st, SQLColumn column, String text);
	
	public static class ObjectClassFactory<V extends SQLTableRow<?>> implements XLSRowFactory<V> 
	{
		final public Class<V> type;
//		final public HashSet<String> sheet_fliter = new HashSet<String>();
		
		public ObjectClassFactory(Class<V> type) {
			this.type = type;
//			this.sheet_fliter.addAll(Arrays.asList(sheets));
		}
		
//		@Override
//		public boolean accept(Sheet st) {
//			if (!sheet_fliter.isEmpty()) {
//				return sheet_fliter.contains(st.getName());
//			}
//			return true;
//		}
		
		@Override
		public String convertField(Sheet st, SQLColumn column, String text) {
			return null;
		}
		
		@Override
		public V createInstance() {
			try {
				return type.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}