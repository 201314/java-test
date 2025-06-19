package com.gitee.linzl.lambda.grammar;

/**
 * 1、该注解只能标记在"有且仅有一个抽象方法"的接口上。
 * 
 * 2、JDK8接口中的静态方法和默认方法，都不算是抽象方法。
 * 
 * 3、接口默认继承java.lang.Object，所以如果接口显示声明覆盖了Object中方法，那么也不算抽象方法。
 * 
 * 4、该注解不是必须的，如果一个接口符合"函数式接口"定义，那么加不加该注解都没有影响。
 * 加上该注解能够更好地让编译器进行检查。如果编写的不是函数式接口，但是加上了@FunctionInterface，那么编译器会报错。
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年3月15日
 */
// 正确的函数式接口
@FunctionalInterface
public interface FunctionalInterfaceTest {
	// 抽象方法
	public void single(String msg);

	public static void testOneParam(FunctionalInterfaceTest myFunctionalInterface) {
		myFunctionalInterface.single("msg");
	}

	// java.lang.Object中的方法不是抽象方法
	public boolean equals(Object var1);

	// default不是抽象方法
	public default void defaultMethod() {
	}

	// static不是抽象方法
	public static void staticMethod() {
	}
}