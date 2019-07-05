package com.gitee.linzl.proxy.dynamic.pattern;

import java.lang.reflect.Method;

public interface Advise {

	public void before(Method method, Object[] args, Object target);

//	public void aroundMethod(InvocationHandler ih);

	public void after(Method method, Object[] args, Object target);

	public void afterReturnValue(Object returnValue, Method method, Object[] args, Object target);

	public void afterThrowException(Method method, Object[] args, Object target, Exception ex);
}
