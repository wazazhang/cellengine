package com.cell.rpg.item.anno;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author WAZA
 */
@Documented
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemType
{
	public int value();
}
