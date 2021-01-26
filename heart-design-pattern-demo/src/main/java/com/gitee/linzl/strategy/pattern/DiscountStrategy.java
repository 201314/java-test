package com.gitee.linzl.strategy.pattern;

//折扣类，打**折用此类
public class DiscountStrategy implements StrategyHandler<Double, Double> {
    private double discount;

    public DiscountStrategy(double discount) {
        this.discount = discount;
    }

    public Double apply(Double money) {
        // 折后价
        return discount * money;
    }

    @Override
    public boolean match(Integer type) {
        return type.equals(1);
    }
}
