package com.cell.rpg.formula.anno;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注该方法可以被公式编辑器调用，该方法只能返回 Number 类型
 * @author WAZA
 * @see Number
 */
@Documented
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectMethod {
	String value();
}