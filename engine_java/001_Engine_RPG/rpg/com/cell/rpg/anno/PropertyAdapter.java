package com.cell.rpg.anno;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @since 2009-04-09
 * @author WAZA <br>
 * <font color="#ff0000"> show field in ObjectPropertyPanel </font> <br>
 * can edit field
 */
@Documented
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyAdapter{
	PropertyType value();
}
