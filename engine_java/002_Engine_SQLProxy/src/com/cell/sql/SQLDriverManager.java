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

public class SQLDriverManager 
{
	static public String DEFAULT_TYPE_COMPARER = "com.cell.mysql.MySQLDriver";
	static private SQLDriver type_comparer;
	
	static public void init() {
		if (type_comparer == null) {
			try {
				setDriver(DEFAULT_TYPE_COMPARER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param typeComparer the type_comparer to set
	 */
	static public void setDriver(SQLDriver typeComparer) {
		type_comparer = typeComparer;
		System.out.println("SQLDriverManager : " + typeComparer.getClass().getName());
	}
	
	static public void setDriver(String typeDirver) throws Exception {
		Class<?> cls = Class.forName(typeDirver);
		type_comparer = (SQLDriver)cls.newInstance();
	}
	

	/**
	 * @return
	 */
	static public SQLDriver getDriver() {
		return type_comparer;
	}
	
}
