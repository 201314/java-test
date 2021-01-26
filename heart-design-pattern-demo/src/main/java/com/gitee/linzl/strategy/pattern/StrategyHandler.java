package com.gitee.linzl.strategy.pattern;

public interface StrategyHandler<T, R> {
    R apply(T money);

    boolean match(Integer type);
}
