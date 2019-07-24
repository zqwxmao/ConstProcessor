package com.oeasy.liba;


import com.oeasy.libannotation.*;
import com.oeasy.libannotation.java8.*;

/**
 * @Author: zhangqiaowenxiang
 * @Time: 2019/7/18
 * @Description: This is
 */
@OutputModulePackage(module = "common", packagePath = "com/oeasy/common")
@BindStringConstV8(value = "keyAZqwx", key = "valueAZqwx", comment = "HOHO_JDK1.8_DOC")
@BindStringConstV8(value = "keyAMao", key = "valueAMao", comment = "HOHO_JDK1.8_DOC")
@BindStringConstV8(value = "keyAZhang", key = "valueAZhang", comment = "HOHO_JDK1.8_DOC")
@BindStringConstV8(value = "keyAQiao", key = "valueAQiao", comment = "HOHO_JDK1.8_DOC")
@BindIntConstV8(key = "zhengshuzhi", value = 3618, comment = "This is a integer number")
@BindIntConstV8(key = "zhengshuzhi", value = 3618, comment = "This is a integer number2")
@BindShortConstV8(key = "shortzhi", value = 2222, comment = "This is a short number")
@BindShortConstV8(key = "shortzhi2", value = 2223, comment = "This is a short number")
@BindBooleanConstV8(key = "booleanzhi", value = true, comment = "This is a boolean number")
@BindByteConstV8(key = "byteshuzhi", value = 126, comment = "This is a byte number")
@BindDoubleConstV8(key = "doubleshuzhi", value = 3618, comment = "This is a double number")
@BindFloatConstV8(key = "floatshuzhi", value = 3618, comment = "This is a float number")
@BindLongConstV8(key = "longshuzhi", value = 3618, comment = "This is a long number")
@BindCharConstV8(key = "charshuzi", value = 'c', comment = "This is a char number")
@ConstPrefix(prefixName = "lib_alpha")
public class TestA {
    {
//        System.out.println(Hello.keyAZqwx);
    }
}
