package com.michaelfaith.libannotation.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface BindShortConstRepeated {
    BindShortConstV8[] value() default {};
}
