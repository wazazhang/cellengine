package com.cell.sql.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.sql.SQLType;


/**
 * 表示该类型为SQL表
 * 用来描述多个字段的组合，该字段包含的字段将存储为表的字段，
 * 组合字段可以包含组合字段，这是一个递归的过程。
 * 整个体系可以被描述成一个树形结构。
 * @author WAZA
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLTable
{
	/** 数据库表主键名 */
	String	primary_key_name();
	
	/** 数据库表主键类型 */
	SQLType	primary_key_type();
	
	/** 数据库表其他约束 <br> 
	 * 比如：UNIQUE KEY `name` (`name`)*/
	String	constraint() default "";
	
	/** 不同数据库驱动需要的表属性<br>
	 * 比如MYSQL : ENGINE=MyISAM DEFAULT CHARSET=utf8 */
	String	properties() default ""; //
	
	/** 注释 */
	String	comment() default "";
}
