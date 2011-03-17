package com.cell.rpg.scene.script.anno;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cell.rpg.scene.script.Scriptable;

@Documented
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventType {

	public Class<? extends Scriptable>[] trigger_type();
	
	public String comment() ;
}
