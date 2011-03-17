package com.cell.util.anno;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigSeparator{
	String[] value();
	String fill() default "#";
}