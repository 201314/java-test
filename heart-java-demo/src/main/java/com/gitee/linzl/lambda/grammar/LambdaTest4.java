package com.gitee.linzl.lambda.grammar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import com.gitee.linzl.lambda.constructor.Button;
import com.gitee.linzl.lambda.constructor.FunctionalInterfaceTest;
import com.gitee.linzl.lambda.constructor.FunctionalInterfaceTest2;
import com.gitee.linzl.lambda.constructor.MyInterface1;
import com.gitee.linzl.lambda.constructor.MyInterface2;
import com.gitee.linzl.lambda.constructor.Student;

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
public class LambdaTest4 {
    public static void main(String[] args) {
        LambdaTest4 test4 = new LambdaTest4();
        //test4.doWork1();
        //test4.doWork2();
        //test4.getName();
        test4.hasMethodSingleParam();
        //test4.lambdaConstructor();
        //test4.test();

        //new Student().greet();
    }

    public void doWork1() {
        Runnable runnable = () -> {
            System.out.println(this);
            System.out.println("lambda express run...");
        };
        new Thread(runnable).start();
        // 等价于
        new Thread(() -> System.out.println("hello, i am thread!")).start();
    }

    public void doWork2() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(this);
                System.out.println("anony function run...");
            }
        };
        new Thread(runnable).start();

        // 等价于
        new Thread(() -> System.out.println("hello, i am thread!")).start();
    }

    public void hasMethodSingleParam() {
        //根据构造器的参数来自动匹配使用哪一个构造器
        MyInterface2<Student> creator = Student::new;
        Student student = creator.create("我是stu第一个参数", 100, new BigDecimal("110"));
        System.out.println("打印结果:" + student.getName());
        // 等价
        MyInterface2<Student> creator2 = (name, score, money) -> new Student("我是stu第一个参数", 100, new BigDecimal("110"));
    }

    public void test() {
        List<String> labels = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        // ClassName::new
        Stream<Button> buttonStream = labels.stream().map(Button::new);

        Button[] buttons = buttonStream.toArray(Button[]::new);
        for (Button button : buttons) {
            System.out.println(button.getText());
        }
    }

    public void lambdaConstructor() {
        MyInterface1 res = () -> {
            return new ArrayList<>(Arrays.asList(1, 2, 3)).size();
        };
        System.out.println("集合大小:" + res.add());
        // 定义test的实现
        FunctionalInterfaceTest test = (x) -> System.out.println(x);
        test.single("传入参数,方法内实现打印");

        // 定义test2的实现
        FunctionalInterfaceTest2 test2 = String::trim;
        System.out.println("输出时去除空格:" + test2.single("   我是中国人"));

        String str = new String("123");
        FunctionalInterfaceTest2 test3 = str::concat;
        System.out.println("输出时拼接数据:" + test2.single("我是拼接的数据"));
    }
}