package com.gitee.linzl.lambda.method;

public class Father {
    public void greet() {
        System.out.println("Hello, i am function in father!");
    }

    public static void main(String[] args) {
        new Child().greet();
    }
}

class Child extends Father {
    @Override
    public void greet() {
        Runnable runnable = super::greet;
        new Thread(runnable).start();
    }

}