package com.cell.sql.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.sql.SQLFieldGroup;


/**
 * 表示该字段是SQL类型
 * 如果该成员是一个{@link SQLFieldGroup}，则对该成员生成的表结构加以处理。
 * @author WAZA
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLGroupField
{	
	/** 注释 */
	String	comment_prefix() default "";
}
