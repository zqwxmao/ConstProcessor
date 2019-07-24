package com.oeasy.libannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zhangqiaowenxiang
 * @Time: 2019/7/22
 * @Description: This is
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface OutputModulePackage {
    String module();
    String packagePath();
}
