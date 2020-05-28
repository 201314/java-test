package com.gitee.linzl.strategy.pattern;

import java.util.Map;

//通过上下文，选择合适的优惠   策略类
public class Context {
    private Strategy strategy;

    // 简单工厂 和 策略模式 的简单结合
    public Context(int selectCaseStrategy) {
        // 优惠 条件 的参数 需要 事先 定义：如0.8，100，10
        switch (selectCaseStrategy) {
            case 0:
                strategy = new NormalStrategy();
                break;
            case 1:
                strategy = new DiscountStrategy(0.8);
                break;
            case 2:
                strategy = new ReturnStrategy(100, 10);
                break;
            default:
                break;
        }
    }

    public double getPrivilegeResult(double money) {
        return strategy.caculatePreferential(money);
    }

    public Map<Integer, Strategy> strategyMap;

    public Context() {
        strategyMap.put(0, new NormalStrategy());
        strategyMap.put(1, new DiscountStrategy(0.8));
        strategyMap.put(2, new ReturnStrategy(100, 10));
    }

    public Strategy getStrategy(int selectCaseStrategy) {
        return strategyMap.get(selectCaseStrategy);
    }

}
