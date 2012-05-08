package com.cell.rpg.item.anno;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记道具属性里的某字段可被持久化
 * @author WAZA
 */
@Documented
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemPersistenceField
{
}
