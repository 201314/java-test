package com.gitee.linzl.strategy.pattern;

import java.util.Arrays;
import java.util.List;

//通过上下文，选择合适的优惠   策略类
public class StrategyHandlerFactory {
    List<StrategyHandler<Double, Double>> list;

    public StrategyHandlerFactory() {
        list = Arrays.asList(new NormalStrategyHandler(),
                new DiscountStrategyHandler(0.8),
                new ReturnStrategyHandler(100, 10));
    }

    public static class StrategyFactory {
        private static StrategyHandlerFactory instance = new StrategyHandlerFactory();
    }

    public static StrategyHandlerFactory getInstance() {
        return StrategyFactory.instance;
    }

    public StrategyHandler<Double, Double> getStrategy(Integer type) {
        return list.stream().filter(strategyHandler -> strategyHandler.match(type)).findFirst().get();
    }
}
