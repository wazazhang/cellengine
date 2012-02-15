package com.cell.sql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

import com.cell.CUtil;
import com.cell.reflect.Fields;
import com.cell.sql.anno.SQLField;
import com.cell.sql.anno.SQLGroupField;
import com.cell.sql.anno.SQLTable;

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
