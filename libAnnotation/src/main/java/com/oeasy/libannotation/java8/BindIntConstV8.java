package com.oeasy.libannotation.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zhangqiaowenxiang
 * @Time: 2019/7/19
 * @Description: This is
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Repeatable(BindIntConstRepeated.class)
public @interface BindIntConstV8 {
    String key();
    int value();
    String comment() default "";
}