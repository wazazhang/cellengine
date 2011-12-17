package com.cell.sql;

public class SQMTypeManager 
{
	static public String DEFAULT_TYPE_COMPARER = "com.cell.mysql.SQLTypeComparerMySQL";
	static private SQLTypeComparer type_comparer;
	
	static public void init() {
		if (type_comparer == null) {
			try {
				setTypeComparer(DEFAULT_TYPE_COMPARER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param typeComparer the type_comparer to set
	 */
	static public void setTypeComparer(SQLTypeComparer typeComparer) {
		type_comparer = typeComparer;
		System.out.println("SQMTypeManager : " + typeComparer.getClass().getName());
	}
	
	static public void setTypeComparer(String typeDirver) throws Exception {
		Class<?> cls = Class.forName(typeDirver);
		type_comparer = (SQLTypeComparer)cls.newInstance();
	}
	

	/**
	 * @return
	 */
	static public SQLTypeComparer getTypeComparer() {
		return type_comparer;
	}
}
