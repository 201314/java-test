package com.gitee.lzl;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class TestAgent {
    /**
     * pom.xml 需要配置 Premain-Class
     */
    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println("JVM启动前静态Instrument:" + arg);
        instrumentation.addTransformer(new DefineTransformer(), true);
    }

    /**
     * pom.xml 需要配置 Agent-Class
     */
    public static void agentmain(String arg, Instrumentation instrumentation) {
        System.out.println("JVM启动后动态Instrument:" + arg);
        instrumentation.addTransformer(new DefineTransformer(), true);
    }

    /**
     * 在pom中需要配置 Can-Retransform-Classes 为true
     * 在pom中需要配置 Can-Redefine-Classes 为true
     * <p>
     * 在ClassFileTransformer中会去拦截系统类和自己实现的类对象；
     * 如果你有对某些类对象进行改写，那么在拦截的时候抓住该类使用字节码编译工具即可实现。
     */
    static class DefineTransformer implements ClassFileTransformer {
        final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
        final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            if ("java/util/Date".equals(className)) {
                try {
                    // 从ClassPool获得CtClass对象
                    final ClassPool classPool = ClassPool.getDefault();
                    final CtClass clazz = classPool.get("java.util.Date");
                    String methodName = "convertToAbbr";
                    CtMethod ctMethod = clazz.getDeclaredMethod(methodName);
                    String newName = methodName + "New";
                    ctMethod.setName(newName);
                    //ctMethod.insertBefore(prefix);

                    String outputStr = "\nSystem.out.println(\"this method " + methodName
                            + " cost:\" +(endTime - startTime) +\"ms.\");";

                    CtMethod newCtMethod = CtNewMethod.copy(ctMethod, methodName, clazz, null);
                    String type = ctMethod.getReturnType().getName();
                    StringBuilder body = new StringBuilder();
                    body.append("{\n " + prefix);
                    if (!"void".equals(type)) {
                        body.append(type).append(" result = ");
                    }
                    body.append(newName).append("($$);\n");
                    body.append("Thread.sleep(1000L);\n ");
                    body.append(postfix);
                    body.append(outputStr);
                    if (!"void".equals(type)) {
                        body.append("return result;\n");
                    }
                    body.append("}");
                    newCtMethod.setBody(body.toString());
                    clazz.addMethod(newCtMethod);

                    //ctMethod.insertAfter(postfix);

                    // 返回字节码，并且detachCtClass对象
                    byte[] byteCode = clazz.toBytecode();
                    //detach的意思是将内存中曾经被javassist加载过的Date对象移除，如果下次有需要在内存中找不到会重新走javassist加载
                    clazz.detach();
                    return byteCode;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            // 如果返回null则字节码不会被修改
            return null;
        }
    }
}