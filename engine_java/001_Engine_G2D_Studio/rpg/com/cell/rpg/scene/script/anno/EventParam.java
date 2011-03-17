package com.cell.rpg.scene.script.anno;

import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventParam {
	  String[] value();
}
