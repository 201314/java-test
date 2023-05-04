package com.gitee.linzl.lambda.constructor;

import java.math.BigDecimal;

/**
 * @author linzhenlie-jk
 * @date 2023/4/26
 */
public interface MyInterface2<T> {
    T create(String name, Integer score, BigDecimal money);

    default void getName() {
        System.out.println("myInterface2 getName");
    }
}
