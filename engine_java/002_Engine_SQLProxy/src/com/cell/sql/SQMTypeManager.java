package com.cell.sql;

public class SQMTypeManager 
{
	static SQLTypeComparer type_comparer;
	
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
