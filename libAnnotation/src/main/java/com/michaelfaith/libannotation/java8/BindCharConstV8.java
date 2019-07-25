package com.michaelfaith.libannotation.java8;

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
@Repeatable(BindCharConstRepeated.class)
public @interface BindCharConstV8 {
    String key();
    char value();
    String comment() default "";
}
