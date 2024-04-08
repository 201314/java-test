package com.gitee.linzl.lottery;

import com.gitee.linzl.math.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PrizeMathRandom {
	private List<? extends Prize> prizes;
	// 1. 计算各权重奖品的区间值
	public PrizeMathRandom computeRatio(List<? extends Prize> prizes) {
		// 1. 计算各奖品的总权重
		double sumWeight =prizes.stream().mapToDouble(Prize::getPrizeWeight).sum();

		// 2. 根据权重关系排正序
		Collections.sort(prizes, Comparator.comparingInt(Prize::getPrizeWeight));

		Prize fp = prizes.get(0);
		double curWeightEnd = BigDecimalUtil.divide(Integer.valueOf(fp.getPrizeWeight()).doubleValue(),sumWeight)
				.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
		fp.setWeightStart(0);
		fp.setWeightEnd(curWeightEnd);

		for (int idx = 1, size = prizes.size(); idx < size; idx++) {
			Prize preP = prizes.get(idx - 1);
			Prize curP = prizes.get(idx);

			double preWeightEnd = preP.getWeightEnd();
			curP.setWeightStart(preWeightEnd);

			BigDecimal preWeight =  BigDecimal.valueOf(preWeightEnd);
			BigDecimal curWeight = BigDecimalUtil.divide(Integer.valueOf(curP.getPrizeWeight()).doubleValue(),
					sumWeight);
			curWeightEnd = preWeight.add(curWeight).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			curP.setWeightEnd(curWeightEnd);
		}

		for (Prize price : prizes) {
			System.out.println(
					"start<" + price.getWeightStart() + "," + price.getPrizeName() + ",end<=" + price.getWeightEnd());
		}
		this.prizes = prizes;
		return this;
	}

	// 3. 在区间[0,总权重)内生成随机数,2分法获取奖品
	public Prize getPrizeIndex() {
		// 产生随机数
		double rv = Math.random();
		return prizes.stream().filter(prize -> prize.getWeightStart() <= rv && rv < prize.getWeightEnd()).findFirst()
				.get();
	}
}
