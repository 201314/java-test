package com.gitee.linzl.strategy.pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//通过上下文，选择合适的优惠   策略类
public class StrategyFactory {
	public Map<Integer, Strategy> strategyMap;

	public StrategyFactory() {
		List<Strategy> list = Arrays.asList(new NormalStrategy(), new DiscountStrategy(0.8),
				new ReturnStrategy(100, 10));
		strategyMap = list.stream().collect(Collectors.toMap(Strategy::type, strategy -> strategy));
	}

	public static class Handler{
		private static StrategyFactory instance = new StrategyFactory();
	}
	public static StrategyFactory getInstance() {
		return Handler.instance;
	}

	public Strategy getStrategy(Integer type) {
		return strategyMap.get(type);
	}
}
