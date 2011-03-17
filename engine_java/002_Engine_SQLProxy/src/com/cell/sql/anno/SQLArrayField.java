package com.cell.sql.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.sql.SQLFieldArray;
import com.cell.sql.SQLType;

/**
 * 表示该字段是SQL类型
 * 如果该成员是一个{@link SQLFieldArray}，则对该成员生成的表结构加以处理。
 * @author WAZA
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLArrayField
{	
	/**
	 * 得到该字段对应的SQL类型
	 * @return
	 */
	SQLType	type();

	/**
	 * 容量
	 * @return
	 */
	int		limit();
	
	/**
	 * 该字段在创建数据库时的默认值
	 * @return
	 */
	String	defaultValue() default "";

	/**
	 * 得到创建数据库时，该字段的约束，比如 NOT NULL
	 * @return
	 */
	String	constraint() default "";

	/** 注释 */
	String	comment() default "";

}
