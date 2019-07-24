package com.oeasy.libannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author zhangqiaowenxiang
 * time 2019/7/22
 * desc This is
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface OutputModulePackage {
    String module();
    String packagePath();
}
