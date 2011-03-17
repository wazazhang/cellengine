/**
 * 
 */
package com.cell.sql.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.sql.SQLTableRow;

/**
 * 表示该字段是SQL类型，建议该类型申明为 protected 或者 public
 * @author WAZA
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLReference
{
	/** 引用的数据库表 */
	Class<? extends SQLTableRow<?>>	type();

	/** 注释 */
	String	comment() default "";
}

