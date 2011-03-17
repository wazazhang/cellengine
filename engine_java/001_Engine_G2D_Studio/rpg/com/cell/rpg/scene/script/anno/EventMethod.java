package com.cell.rpg.scene.script.anno;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventMethod {
	  String[] value();
}
