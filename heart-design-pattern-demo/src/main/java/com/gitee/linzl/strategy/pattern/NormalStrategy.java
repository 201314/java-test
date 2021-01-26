package com.gitee.linzl.strategy.pattern;

//没有任何 优惠活动
public class NormalStrategy implements StrategyHandler<Double, Double> {
    public Double apply(Double money) {
        return money;
    }

    @Override
    public boolean match(Integer type) {
        return type.equals(0);
    }
}
