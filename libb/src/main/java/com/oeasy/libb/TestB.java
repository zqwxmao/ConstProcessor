package com.oeasy.libb;

import com.oeasy.libannotation.*;
import com.oeasy.libannotation.java8.*;

/**
 * author zhangqiaowenxiang
 * time 2019/7/18
 * desc This is
 */

@OutputModulePackage(module = "common", packagePath = "com/oeasy/common")
@BindStringConstV8(value = "keyBZqwx", key = "valueBZqwx", comment = "HOHO_JDK1.8_DOC")
@BindStringConstV8(value = "keyBMao", key = "valueBMao", comment = "HOHO_JDK1.8_DOC")
@BindStringConstV8(value = "keyBZhang", key = "valueBZhang", comment = "HOHO_JDK1.8_DOC")
@BindStringConstV8(value = "keyBQiao", key = "valueBQiao")
@BindIntConstV8(key = "zhengshuzhi", value = 3618)
@BindShortConstV8(key = "shortzhi", value = 2222, comment = "This is a short number")
@BindBooleanConstV8(key = "booleanzhi", value = true, comment = "This is a boolean number")
@BindByteConstV8(key = "byteshuzhi", value = 126, comment = "This is a byte number")
@BindDoubleConstV8(key = "doubleshuzhi", value = 3618)
@BindFloatConstV8(key = "floatshuzhi", value = 3618, comment = "This is a float number")
@BindLongConstV8(key = "longshuzhi", value = 3618, comment = "This is a long number")
@ConstPrefix(prefixName = "lib_bravo")
public class TestB {
    {
//        System.out.println(Hello.keyBZqwx+" = ");
    }
}
