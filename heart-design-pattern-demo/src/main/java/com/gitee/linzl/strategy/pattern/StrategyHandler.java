package com.gitee.linzl.strategy.pattern;

/**
 * 策略模式是在求解同一个问题的多种解法，这些不同解法之间毫无关联,控制对象使用什么策略；
 * 通过客户端入参来选择何种策略
 *
 * @param <T>
 * @param <R>
 */
public interface StrategyHandler<T, R> {
    R apply(T money);

    boolean match(Integer type);
}
