package com.cell.nio.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

/**
 * @since 2009-06-02
 * @author WAZA
 * 通过反射机制获得该字段是否参加序列化反序列化
 */
@Target(ElementType.FIELD)//只对应属性
@Retention(RetentionPolicy.RUNTIME)//在运行时保留
public @interface Seri {
}
