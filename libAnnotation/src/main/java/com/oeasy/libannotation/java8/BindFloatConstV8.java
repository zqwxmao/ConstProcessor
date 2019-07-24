package com.oeasy.libannotation.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author zhangqiaowenxiang
 * time 2019/7/19
 * desc This is
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(BindFloatConstRepeated.class)
public @interface BindFloatConstV8 {
    String key();
    float value();
    String comment() default "";
}
