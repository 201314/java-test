package com.gitee.linzl.strategy.pattern;

// 类似  满 100 返10 元 
public class ReturnStrategy implements StrategyHandler<Double, Double> {
    // 返利条件
    private double returnCondition;
    // 返利金额
    private double returnMoney;

    public ReturnStrategy(double returnCondition, double returnMoney) {
        this.returnCondition = returnCondition;
        this.returnMoney = returnMoney;
    }

    public Double apply(Double money) {
        // 达到返利条件
        if (money >= returnCondition) {
            return money - money / returnCondition * returnMoney;
        }
        return money;
    }

    @Override
    public boolean match(Integer type) {
        return type.equals(2);
    }
}
