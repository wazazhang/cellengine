package com.cell.util.anno;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigType{
	public String[] value();
	public boolean is_all_field() default false;
}