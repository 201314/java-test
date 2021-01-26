package com.gitee.linzl.strategy.pattern;

import java.util.Arrays;
import java.util.List;

//通过上下文，选择合适的优惠   策略类
public class StrategyFactory {
    List<StrategyHandler<Double, Double>> list;

    public StrategyFactory() {
        list = Arrays.asList(new NormalStrategy(),
                new DiscountStrategy(0.8),
                new ReturnStrategy(100, 10));
    }

    public static class Factory {
        private static StrategyFactory instance = new StrategyFactory();
    }

    public static StrategyFactory getInstance() {
        return Factory.instance;
    }

    public StrategyHandler<Double, Double> getStrategy(Integer type) {
        return list.stream().filter(strategyHandler -> strategyHandler.match(type)).findFirst().get();
    }
}
