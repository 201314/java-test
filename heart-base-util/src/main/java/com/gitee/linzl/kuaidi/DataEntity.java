package com.gitee.linzl.kuaidi;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 详细信息
 * 
 * @author liny
 */
@Setter
@Getter
public class DataEntity {
	/**
	 * 接收时间
	 */
	private String time;

	/**
	 * 完成时间
	 */
	private String ftime;

	/**
	 * 快递环节信息
	 */
	private String context;

	/**
	 * 快递当前位置
	 */
	private String location;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}