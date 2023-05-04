package com.gitee.linzl.lambda.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import com.gitee.linzl.lambda.constructor.Button;
import com.gitee.linzl.lambda.constructor.FunctionalInterfaceTest;
import com.gitee.linzl.lambda.constructor.MyInterface1;
import com.gitee.linzl.lambda.constructor.MyInterface2;

/**
 * 在比较器Comparator接口中定义了若干用于比较和键提取的静态方法和默认方法，默认方法的使用使得方法引用更加方便，
 * 例如使用java.util.Objects类中的静态方法isNull和nonNull
 * 可以在Stream中很方便的进行null的判定(之后会有对于stream的介绍)。 但是在接口中引入默认方法设计到一个问题，即
 * (1).接口中的默认方法和父类中方法的冲突问题
 * </p>
 * (2).接口之间引用的冲突问题
 * 对于第一个冲突，java8规定类中的方法优先级要高于接口中的默认方法，所以接口中默认方法复写Object类中的方法是没有意义的，
 * 因为所有的接口都默认继承自Object类使得默认方法一定会被覆盖。
 */
public class LambdaTest5 implements MyInterface1  {

	@Override
	public void getName() {
		MyInterface1.super.getName();
	}

	@Override
	public int add() {
		return 0;
	}

	public static void main(String[] args) {
		LambdaTest5 test4 = new LambdaTest5();
		test4.getName();
	}
}