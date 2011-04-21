package com.g2d.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @since 2011-04-21
 * @author Tilergames <br>
 * <font color="#ff0000"> show field in ObjectPropertyPanel </font> <br>
 * can edit field
 */
@Documented
@Target({TYPE, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Tenthousandth
{
	boolean value();
}
