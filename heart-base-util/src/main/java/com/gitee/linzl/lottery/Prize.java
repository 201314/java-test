package com.gitee.linzl.lottery;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Prize {
	// 奖品id
	private int id;
	// 奖品名称
	private String prizeName;
	// 奖品（剩余）数量
	private int prizeAmount;
	// 奖品权重
	private int prizeWeight;

	// 保留小数点后2位
	private double weightStart;
	private double weightEnd;
}