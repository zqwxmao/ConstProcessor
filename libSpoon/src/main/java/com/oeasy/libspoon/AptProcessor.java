package com.oeasy.libspoon;

import com.google.auto.service.AutoService;
import com.oeasy.libannotation.ConstPrefix;
import com.oeasy.libannotation.OutputModulePackage;
import com.oeasy.libannotation.java8.BindBooleanConstV8;
import com.oeasy.libannotation.java8.BindByteConstV8;
import com.oeasy.libannotation.java8.BindCharConstV8;
import com.oeasy.libannotation.java8.BindDoubleConstV8;
import com.oeasy.libannotation.java8.BindFloatConstV8;
import com.oeasy.libannotation.java8.BindIntConstV8;
import com.oeasy.libannotation.java8.BindLongConstV8;
import com.oeasy.libannotation.java8.BindShortConstV8;
import com.oeasy.libannotation.java8.BindStringConstV8;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import spoon.Launcher;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

/**
 * author zhangqiaowenxiang
 * time 2019/7/18
 * desc This is
 */
@AutoService(Processor.class)
public class AptProcessor extends AbstractProcessor {

    /**
     */
    private static Messager messager;
    private static final Class[] BIND_CONST_CLASSES = {
            BindStringConstV8.class,
            BindIntConstV8.class,
            BindCharConstV8.class,
            BindShortConstV8.class,
            BindBooleanConstV8.class,
            BindFloatConstV8.class,
            BindDoubleConstV8.class,
            BindLongConstV8.class,
            BindByteConstV8.class};

    private Set<ConstAnnotationBean<String>> stringConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Integer>> intConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Boolean>> booleanConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Byte>> byteConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Short>> shortConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Float>> floatConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Double>> doubleConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Long>> longConst = new LinkedHashSet<>();
    private Set<ConstAnnotationBean<Character>> charConst = new LinkedHashSet<>();

    private static String outputModule;
    private static String outputPackageName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.messager = processingEnvironment.getMessager();
        log("-----------------> AptProcessor init------------- "+hashCode());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        boolean findOutput = findOutputDirectory(roundEnvironment);
        if (findOutput) {
            Set<Element> foundRepeatedElements = findClassWithBindConst(roundEnvironment);
            boolean findAnnotation = findAnnotation(foundRepeatedElements);
            if (findAnnotation) {
                genSpoonCodeV8();
            }
        }

        return false;
    }

    private boolean findOutputDirectory(RoundEnvironment roundEnvironment) {
        boolean findOutput = false;
        Set<? extends Element> output = roundEnvironment.getElementsAnnotatedWith(OutputModulePackage.class);
        Iterator<? extends Element> iterator = output.iterator();
        if (iterator != null && iterator.hasNext()) {
            Element element = iterator.next();
            if (element.getKind() == ElementKind.CLASS) {
                OutputModulePackage outputModulePackage = element.getAnnotation(OutputModulePackage.class);
                outputModule = outputModulePackage.module();
                outputPackageName = outputModulePackage.packagePath();
                if (!isEmpty(outputModule) && !isEmpty(outputPackageName)) {
                    findOutput = true;
                }
            }
        }

        return findOutput;
    }

    private Set<Element> findClassWithBindConst(RoundEnvironment roundEnvironment) {
        Set<Element> foundRepeatedElements = new LinkedHashSet<>();
        Set<? extends Element> tmpRepeatedElements;
        for (Class clazz : BIND_CONST_CLASSES) {
            tmpRepeatedElements = roundEnvironment.getElementsAnnotatedWith(clazz);
            for (Element element : tmpRepeatedElements) {
                if (element.getKind() == ElementKind.CLASS) {
                    foundRepeatedElements.add(element);
                }
            }
        }
        return foundRepeatedElements;
    }

    private String addPrefix(boolean hasPrefix, String prefixName, String srcName) {
        if (hasPrefix) {
            return prefixName + "_" + srcName;
        } else {
            return srcName;
        }
    }

    private boolean hasData(Collection ... collection) {
        boolean has = false;
        for (Collection coll : collection) {
            if (coll != null && coll.size() > 0) {
                has = true;
                break;
            }
        }
        return has;
    }

    private boolean findAnnotation(Set<Element> foundRepeatedElements) {
        boolean findAnnotation = false;
        String key = "";
        String comment = "";
        BindStringConstV8 bindStringConstV8;
        BindIntConstV8 bindIntConstV8;
        BindCharConstV8 bindCharConstV8;
        BindShortConstV8 bindShortConstV8;
        BindBooleanConstV8 bindBooleanConstV8;
        BindFloatConstV8 bindFloatConstV8;
        BindDoubleConstV8 bindDoubleConstV8;
        BindLongConstV8 bindLongConstV8;
        BindByteConstV8 bindByteConstV8;
        String modulePrefix = "";
        ConstPrefix constPrefix;
        boolean hasPrefix = false;
        for (Element e : foundRepeatedElements) {
            constPrefix = e.getAnnotation(ConstPrefix.class);
            hasPrefix = !isEmpty(constPrefix.prefixName());
            if (hasPrefix) {
                modulePrefix = constPrefix.prefixName();
            }
            for (Class clazz : BIND_CONST_CLASSES) {
                Annotation[] annotations = e.getAnnotationsByType(clazz);
                for (Annotation annotation : annotations) {
                    if (annotation instanceof BindStringConstV8) {
                        bindStringConstV8 = (BindStringConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindStringConstV8.key());
                        bindStringConstV8.value();
                        comment = bindStringConstV8.comment();
                        stringConst.add(new ConstAnnotationBean<>(key, bindStringConstV8.value(), comment));
                    } else if (annotation instanceof BindBooleanConstV8) {
                        bindBooleanConstV8 = (BindBooleanConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindBooleanConstV8.key());
                        bindBooleanConstV8.value();
                        comment = bindBooleanConstV8.comment();
                        booleanConst.add(new ConstAnnotationBean<>(key, bindBooleanConstV8.value(), comment));
                    } else if (annotation instanceof BindByteConstV8) {
                        bindByteConstV8 = (BindByteConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindByteConstV8.key());
                        bindByteConstV8.value();
                        comment = bindByteConstV8.comment();
                        byteConst.add(new ConstAnnotationBean<>(key, bindByteConstV8.value(), comment));
                    } else if (annotation instanceof BindShortConstV8) {
                        bindShortConstV8 = (BindShortConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindShortConstV8.key());
                        bindShortConstV8.value();
                        comment = bindShortConstV8.comment();
                        shortConst.add(new ConstAnnotationBean<>(key, bindShortConstV8.value(), comment));
                    } else if (annotation instanceof BindCharConstV8) {
                        bindCharConstV8 = (BindCharConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindCharConstV8.key());
                        bindCharConstV8.value();
                        comment = bindCharConstV8.comment();
                        charConst.add(new ConstAnnotationBean<>(key, bindCharConstV8.value(), comment));
                    } else if (annotation instanceof BindIntConstV8) {
                        bindIntConstV8 = (BindIntConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindIntConstV8.key());
                        bindIntConstV8.value();
                        comment = bindIntConstV8.comment();
                        intConst.add(new ConstAnnotationBean<>(key, bindIntConstV8.value(), comment));
                    } else if (annotation instanceof BindFloatConstV8) {
                        bindFloatConstV8 = (BindFloatConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindFloatConstV8.key());
                        bindFloatConstV8.value();
                        comment = bindFloatConstV8.comment();
                        floatConst.add(new ConstAnnotationBean<>(key, bindFloatConstV8.value(), comment));
                    } else if (annotation instanceof BindDoubleConstV8) {
                        bindDoubleConstV8 = (BindDoubleConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindDoubleConstV8.key());
                        bindDoubleConstV8.value();
                        comment = bindDoubleConstV8.comment();
                        doubleConst.add(new ConstAnnotationBean<>(key, bindDoubleConstV8.value(), comment));
                    } else if (annotation instanceof BindLongConstV8) {
                        bindLongConstV8 = (BindLongConstV8) annotation;
                        key = addPrefix(hasPrefix, modulePrefix, bindLongConstV8.key());
                        bindLongConstV8.value();
                        comment = bindLongConstV8.comment();
                        longConst.add(new ConstAnnotationBean<>(key, bindLongConstV8.value(), comment));
                    }
                }
            }
        }
        findAnnotation = hasData(byteConst, charConst, shortConst, intConst, floatConst, doubleConst, longConst, booleanConst, stringConst);
        return findAnnotation;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(BindStringConstV8.class.getCanonicalName());
        annotataions.add(BindIntConstV8.class.getCanonicalName());
        annotataions.add(BindCharConstV8.class.getCanonicalName());
        annotataions.add(BindShortConstV8.class.getCanonicalName());
        annotataions.add(BindBooleanConstV8.class.getCanonicalName());
        annotataions.add(BindFloatConstV8.class.getCanonicalName());
        annotataions.add(BindDoubleConstV8.class.getCanonicalName());
        annotataions.add(BindLongConstV8.class.getCanonicalName());
        annotataions.add(BindByteConstV8.class.getCanonicalName());
        annotataions.add(BindStringConstV8.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    private void genSpoonCodeV8() {
        Launcher launcher = new Launcher();
        String input = "./"+outputModule+"/src/main/java/"+outputPackageName;
        log("input : "+input);
        launcher.addInputResource(input);
        launcher.addProcessor(new SpoonProcessorV8(stringConst, intConst, booleanConst, byteConst, shortConst, floatConst, doubleConst, longConst, charConst));
        launcher.run();
    }

    private static class SpoonProcessorV8 extends spoon.processing.AbstractProcessor<CtClass> {

        private Set<ConstAnnotationBean<String>> stringConst;
        private Set<ConstAnnotationBean<Integer>> intConst;
        private Set<ConstAnnotationBean<Boolean>> booleanConst;
        private Set<ConstAnnotationBean<Byte>> byteConst;
        private Set<ConstAnnotationBean<Short>> shortConst;
        private Set<ConstAnnotationBean<Float>> floatConst;
        private Set<ConstAnnotationBean<Double>> doubleConst;
        private Set<ConstAnnotationBean<Long>> longConst;
        private Set<ConstAnnotationBean<Character>> charConst;

        public SpoonProcessorV8(Set<ConstAnnotationBean<String>> stringConst, Set<ConstAnnotationBean<Integer>> intConst, Set<ConstAnnotationBean<Boolean>> booleanConst, Set<ConstAnnotationBean<Byte>> byteConst, Set<ConstAnnotationBean<Short>> shortConst, Set<ConstAnnotationBean<Float>> floatConst, Set<ConstAnnotationBean<Double>> doubleConst, Set<ConstAnnotationBean<Long>> longConst, Set<ConstAnnotationBean<Character>> charConst) {
            this.stringConst = stringConst;
            this.intConst = intConst;
            this.booleanConst = booleanConst;
            this.byteConst = byteConst;
            this.shortConst = shortConst;
            this.floatConst = floatConst;
            this.doubleConst = doubleConst;
            this.longConst = longConst;
            this.charConst = charConst;
        }

        @Override
        public void process(CtClass ctClass) {
            Factory factory = ctClass.getFactory();
            String output = "./"+outputModule+"/src/main/java";
            log("output : "+output);
            factory.getEnvironment().setSourceOutputDirectory(new File(output));

            addField(factory, ctClass, byteConst, Byte.class);
            addField(factory, ctClass, shortConst, Short.class);
            addField(factory, ctClass, booleanConst, Boolean.class);
            addField(factory, ctClass, charConst, Character.class);
            addField(factory, ctClass, intConst, Integer.class);
            addField(factory, ctClass, floatConst, Float.class);
            addField(factory, ctClass, doubleConst, Double.class);
            addField(factory, ctClass, longConst, Long.class);
            addField(factory, ctClass, stringConst, String.class);
        }

        private <E> void addField(Factory factory, CtClass ctClass, Set<ConstAnnotationBean<E>> data, Class<E> eClass) {
            if (data == null) return;
            int len = data.size();
            if (len > 0) {
                Iterator<ConstAnnotationBean<E>> iterator = data.iterator();
                String key = "";
                E val;
                String doc = "";
                ConstAnnotationBean<E> constAnnotationBean;
                while (iterator.hasNext()) {
                    constAnnotationBean = iterator.next();
                    key = constAnnotationBean.getKey();
                    val = constAnnotationBean.getVal();
                    doc = constAnnotationBean.getComment();
                    if (isEmpty(key) || isEmpty(val)) {
                        continue;
                    }
                    CtField<E> ctField = factory.Core().createField();
                    String fieldName = key.toUpperCase();
                    ctField.setSimpleName(fieldName);
                    CtCodeSnippetExpression ctCodeSnippetExpression;
                    if (val instanceof String) {
                        ctCodeSnippetExpression = factory.Code().createCodeSnippetExpression("\"" + val + "\"");
                    } else if (val instanceof Float) {
                        ctCodeSnippetExpression = factory.Code().createCodeSnippetExpression("" + val + "F");
                    } else if (val instanceof Long) {
                        ctCodeSnippetExpression = factory.Code().createCodeSnippetExpression("" + val + "L");
                    } else if (val instanceof Character) {
                        ctCodeSnippetExpression = factory.Code().createCodeSnippetExpression("\'" + val + "\'");
                    } else {
                        ctCodeSnippetExpression = factory.Code().createCodeSnippetExpression("" + val);
                    }
                    ctField.setAssignment(ctCodeSnippetExpression);
                    ctField.addModifier(ModifierKind.PUBLIC);
                    ctField.addModifier(ModifierKind.STATIC);
                    ctField.addModifier(ModifierKind.FINAL);
                    final CtTypeReference<E> fieldTypeRef = factory.Code().createCtTypeReference(eClass);
                    ctField.setType(fieldTypeRef);
                    StringBuffer stringBuffer = new StringBuffer("This code is auto-generated! MUST NOT CHANGE THIS!!!");
                    if (!isEmpty(doc)) {
                        stringBuffer.append("\n"+doc);
                    }
                    CtComment ctComment = factory.Code().createComment(stringBuffer.toString(), CtComment.CommentType.JAVADOC);
                    ctField.addComment(ctComment);
                    CtField oldCtField = ctClass.getField(fieldName);
                    boolean remove = false;
                    if (oldCtField != null) {
                        remove = ctClass.removeField(oldCtField);
                    }
                    log("oldField : "+oldCtField+" - removed  : "+remove);
                    ctClass.addField(ctField);
                }
            }
        }
    }

    private static void log(String str) {
        messager.printMessage(Diagnostic.Kind.NOTE, str);
    }

    private static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    private static <T> boolean isEmpty(T str) {
        return str == null || "".equals(str);
    }
}
