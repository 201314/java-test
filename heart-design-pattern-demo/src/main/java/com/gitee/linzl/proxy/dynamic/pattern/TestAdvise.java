package com.gitee.linzl.proxy.dynamic.pattern;

import java.lang.reflect.Method;

public class TestAdvise implements Advise {
	@Override
	public void before(Method method, Object[] args, Object target) {
		System.out.println("前置通知");
	}

	@Override
	public void after(Method method, Object[] args, Object target) {
		System.out.println("后置通知");
	}

	@Override
	public void afterReturnValue(Object returnValue, Method method, Object[] args, Object target) {
		System.out.println("后置返回通知的返回值:" + returnValue);
	}

	@Override
	public void afterThrowException(Method method, Object[] args, Object target, Exception ex) {
		ex.printStackTrace();
		System.out.println("发生异常!!!!!");
	}
}
