package com.gitee.linzl.lambda.collection;

import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        // 一个空的Optional对象
        Optional<Student> emptyStu = Optional.empty();

        // 创建一个非空值的Optional,如果为空直接报错,of不允许为空，不建议使用
        Student stu = new Student();
        stu.setName("11");
        // Optional<Student> emptyStu2 = Optional.of(stu);

        // 如果user是null，那么得到的Optional对象就是个空对象，但不会让你导致空指针
        Optional<Student> emptyStu3 = Optional.ofNullable(stu);
        String name = emptyStu3.map(Student::getName).orElse("我是默认的名字");
        System.out.println("orElse打印名字:" + name);

        // orElseGet是orElse方法的延迟调用版,如果有值则将其返回，否则调用函数并将其返回调用结果
        // 用这种方式性能更好
        String name2 = emptyStu3.map(Student::getName).orElseGet(() -> {
            return "如果getName有值则将其返回,否则返回这里的默认值";
        });
        System.out.println("orElseGet打印名字:" + name2);

        // orElseThrow(Supplier<? extends X> exceptionSupplier)和get方法非常类似，
        // 它们遭遇Optional对象为空时都会抛出一个异常，但是使用orElseThrow你可以定制希 望抛出的异常类型。
        // String name3 = emptyStu3.map(Student::getName).orElseThrow(Exception::new);
        // System.out.println("orElseThrow打印名字:" + name3);

        // ifPresent(Consumer<? super T>)让你能在变量值存在时执行一个作为参数传入的 方法，否则就不进行任何操作。
        emptyStu3.map(Student::getName).ifPresent(item -> {
            // doSometing
            System.out.println(item);
        });

        if (emptyStu3.map(Student::getName).isPresent()) {
            System.out.println("存在");
        } else {
            // 没有查到的逻辑
            System.out.println("不存在");
        }

        OptionalDemo a = new OptionalDemo();
        a.get();
    }

    public String get() {
        String str = null;
        return Optional.ofNullable(str).orElseThrow(() -> new BusinessException("用户不存在"));
    }

}
