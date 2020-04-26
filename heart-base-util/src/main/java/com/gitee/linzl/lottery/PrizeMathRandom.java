package com.gitee.linzl.lottery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PrizeMathRandom {
	// 1. 计算各奖品的总权重
	public double sumWeight(List<? extends Prize> prizes) {
		return prizes.stream().mapToDouble(Prize::getPrizeWeight).sum();
	}

	// 2. 根据权重关系排正序
	public void sort(List<? extends Prize> prizes) {
		Collections.sort(prizes, Comparator.comparingInt(Prize::getPrizeWeight));
	}

	// 3. 计算各权重奖品的区间值
	public void computeRatio(List<? extends Prize> prizes, double sumWeight) {
		Prize p = prizes.get(0);
		BigDecimal first = new BigDecimal(p.getPrizeWeight() / sumWeight);
		double weight = first.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		p.setWeightStart(0);
		p.setWeightEnd(weight);

		for (int i = 1, size = prizes.size(); i < size; i++) {
			Prize prize = prizes.get(i);
			BigDecimal pre = new BigDecimal(prizes.get(i - 1).getWeightEnd());
			BigDecimal second = new BigDecimal(prize.getPrizeWeight() / sumWeight);

			prize.setWeightStart(prizes.get(i - 1).getWeightEnd());
			prize.setWeightEnd(pre.add(second).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
		}

		for (Prize price : prizes) {
			System.out.println(
					"start<" + price.getWeightStart() + "," + price.getPrizeName() + ",end<=" + price.getWeightEnd());
		}
	}

	// 3. 在区间[0,总权重)内生成随机数,2分法获取奖品
	public Prize getPrizeIndex(List<? extends Prize> prizes) {
		// 产生随机数
		double rv = Math.random();
		return prizes.stream().filter(prize -> prize.getWeightStart() <= rv && rv < prize.getWeightEnd()).findFirst()
				.get();
	}

	public static <K> void main(String[] agrs) {
		PrizeMathRandom a = new PrizeMathRandom();
		List<Prize> prizes = new ArrayList<>();

		Prize p1 = new Prize();
		p1.setPrizeName("范冰冰海报");
		p1.setPrizeWeight(1);// 奖品的权重设置成1
		prizes.add(p1);

		Prize p2 = new Prize();
		p2.setPrizeName("上海紫园1号别墅");
		p2.setPrizeWeight(2);// 奖品的权重设置成2
		prizes.add(p2);

		Prize p3 = new Prize();
		p3.setPrizeName("奥迪a9");
		p3.setPrizeWeight(3);// 奖品的权重设置成3
		prizes.add(p3);

		Prize p4 = new Prize();
		p4.setPrizeName("双色球彩票");
		p4.setPrizeWeight(4);// 奖品的权重设置成4
		prizes.add(p4);

		System.out.println("抽奖开始");

		double sumWeight = a.sumWeight(prizes);
		a.sort(prizes);
		a.computeRatio(prizes, sumWeight);

		Map<String, AtomicInteger> map = new HashMap<>();
		// 打印100个测试概率的准确性
		for (int i = 0; i < 10000; i++) {
			Prize prize = a.getPrizeIndex(prizes);
			System.out.println("第" + i + "次抽中的奖品为：" + prize.getPrizeName());
			System.out.println("--------------------------------");
			map.computeIfAbsent(prize.getPrizeName(), key -> new AtomicInteger(0)).incrementAndGet();
		}
		System.out.println("抽奖结束");
		System.out.println("每种奖品抽到的数量为：" + map);
	}
}
