/**
 * 
 */
package com.cell.sql.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.sql.SQLType;

/**
 * 表示该字段是SQL类型，建议该类型申明为 protected 或者 public
 * @author WAZA
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLField 
{
	/**得到该字段对应的SQL类型*/
	SQLType		type();

	/** 0 意味无限制，使用系统默认，有的必须限制，比如STRING类型。*/
	int			size() 				default 0;

	/**限定 NOT NULL, mysql默认为 NOT　NULL */
	boolean		not_null() 			default true;
	
	/**插入时自增*/
	boolean		auto_increment()	default false;

	/**该字段在创建数据库时的默认值*/
	String		default_value()		default "";
	
	/** 注释 */
	String		comment()			default "";
	
	/**创建表时，该字段处于什么位置，数字越大越靠前，但无论如何，主键都在最前*/
	int			index_priority()	default 0;
}
