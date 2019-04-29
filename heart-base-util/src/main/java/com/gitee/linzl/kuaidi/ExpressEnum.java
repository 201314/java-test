package com.gitee.linzl.kuaidi;

import lombok.Getter;

/**
 * 快递编码与名称
 * 
 * @author linzl
 *
 */
@Getter
public enum ExpressEnum {
	EMS("ems", "EMS快递"),

	SHEN_TONG("shentong", "申通快递"),

	SHUN_FENG("shunfeng", "顺风"),

	YUAN_TONG("yuantong", "圆通"),

	YUN_DA("yunda", "韵达"),

	BAI_SHI_HUI_TONG("huitong", "百世汇通"),

	TIAN_TIAN_KUAI_DI("tiantian", "天天快递"),

	ZHONG_TONG_KUAI_DI("zhongtong", "中通快递"),

	ZHAI_JI_SONG_KUAI_DI("zhaijisong", "宅急送快递"),

	ZHONG_GUO_YOU_ZHENG("pingyou", "中国邮政"),

	QUAN_FENG_KUAI_DI("quanfeng", "全峰快递");

	private String expressCode;
	private String expressCompany;

	private ExpressEnum(String expressCode, String expressCompany) {
		this.expressCode = expressCode;
		this.expressCompany = expressCompany;
	}

}
