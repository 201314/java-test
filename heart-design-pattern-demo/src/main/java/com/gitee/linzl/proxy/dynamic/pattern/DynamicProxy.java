package com.gitee.linzl.proxy.dynamic.pattern;

import com.gitee.linzl.proxy.statics.pattern.RealSubject;
import com.gitee.linzl.proxy.statics.pattern.Subject;
import net.sf.cglib.proxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等,
 * <p>
 * 动态代理的好处：同属一个接口的具体主题角色，只需要一个代理类即可。
 * <p>
 * 使用JDK动态代理:是针接口实现代理的，如果有些类并没有实现接口，则不能使用JDK代理，这就要使用cglib动态代理了
 * <p>
 * 使用Cglib动态代理:是针对类来实现代理的，原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，
 * 所以不能对final修饰的类进行代理。
 *
 * @author linzl
 */
public class DynamicProxy {
    public Object process(Object obj, Advise adv) {
        boolean hasInterface = false;
        Class<?>[] cls = obj.getClass().getInterfaces();
        for (Class<?> cl : cls) {
            if (cl.isInterface()) {
                hasInterface = true;
                break;
            }
        }

        if (obj.getClass().isInterface() || Proxy.class.isAssignableFrom(obj.getClass()) || hasInterface) {
            System.out.println("使用JAVA内置动态代理");
            return javaProxy(obj, adv);
        }

        System.out.println("使用cglib动态代理");
        return cglibProxy2(obj, adv);
    }

    private Object javaProxy(Object obj, Advise adv) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        adv.before(method, args, obj);

                        Object val = null;
                        Exception exception = null;
                        try {
                            // 直接调用被代理类的方法
                            val = method.invoke(obj, args);
                        } catch (Exception e) {
                            exception = e;
                        } finally {
                            adv.after(method, args, obj);
                        }

                        adv.afterReturnValue(val, method, args, obj);

                        if (Objects.nonNull(exception)) {
                            adv.afterThrowException(method, args, obj, exception);
                        }
                        return val;
                    }
                });
    }

    private Object cglibProxy(Object obj, Advise adv) {
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        //2.通过setSuperclass来设置父类型，即需要给哪个类创建代理类
        enhancer.setSuperclass(obj.getClass());
        // 回调方法
        enhancer.setCallbacks(new Callback[]{
                new MethodInterceptor() {
                    /**
                     * 代理对象方法拦截器
                     * @param obj 代理对象
                     * @param method 被代理的类的方法
                     * @param args 调用方法传递的参数
                     * @param proxy 方法代理对象
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                        adv.before(method, args, obj);

                        Object val = null;
                        Exception exception = null;
                        try {
                            // 直接调用被代理类的方法
                            val = proxy.invokeSuper(obj, args);
                        } catch (Exception e) {
                            exception = e;
                        } finally {
                            adv.after(method, args, obj);
                        }

                        adv.afterReturnValue(val, method, args, obj);

                        if (Objects.nonNull(exception)) {
                            adv.afterThrowException(method, args, obj, exception);
                        }
                        return val;
                    }
                }});
        return enhancer.create();
    }

    private Object cglibProxy2(Object obj, Advise adv) {
        //1.创建Enhancer对象
        Enhancer enhancer = new Enhancer();
        //2.通过setSuperclass来设置父类型，即需要给哪个类创建代理类
        enhancer.setSuperclass(obj.getClass());
        Callback one = (MethodInterceptor) (obj1, method, args, proxy) -> {
            adv.before(method, args, obj1);

            Object val = null;
            Exception exception = null;
            try {
                // 直接调用被代理类的方法
                val = proxy.invokeSuper(obj1, args);
            } catch (Exception e) {
                exception = e;
            } finally {
                adv.after(method, args, obj1);
            }

            adv.afterReturnValue(val, method, args, obj1);

            if (Objects.nonNull(exception)) {
                adv.afterThrowException(method, args, obj1, exception);
            }
            return val;
        };

        Callback two = (FixedValue) () -> "我是返回固定值，无论结果是啥都是这个结果";

        CallbackHelper callbackHelper = new CallbackHelper(obj.getClass(), null) {
            @Override
            protected Object getCallback(Method method) {
                return method.getName().startsWith("find") ? one : two;
            }
        };
        // 回调方法
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        enhancer.setCallbackFilter(callbackHelper);
        return enhancer.create();
    }

    public static void main(String[] args) {
        // 保存生成的代理类 ,直接搜索名字 $Proxy开头的class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        DynamicProxy test = new DynamicProxy();
        Subject proxy = (Subject) test.process(new RealSubject(), new TestAdvise());
        String findResult = proxy.find();
        System.out.println("findResult:" + findResult);
        String insertResult = proxy.insert();
        System.out.println("insertResult:" + insertResult);
    }
}
