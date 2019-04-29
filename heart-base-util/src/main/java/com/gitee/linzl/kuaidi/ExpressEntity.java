package com.gitee.linzl.kuaidi;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 快递单信息
 * 
 * @author liny
 *
 */
@Setter
@Getter
public class ExpressEntity {
	/**
	 * 返回信息
	 */
	private String message;

	/**
	 * 单号
	 */
	private String nu;

	/**
	 * 
	 */
	private String ischeck;

	/**
	 * 
	 */
	private String condition;

	/**
	 * 快递公司编码
	 */
	private String com;

	/**
	 * 快递公司
	 */
	private String courierCompany;

	/**
	 * 快递状态,200：正常,201:单号不存在或者已经过期,400:参数错误,001还没有寄样，002，根据taskId和taskNumber找不到邀请记录
	 */
	private String status;

	/**
	 * 
	 */
	private String state;

	/**
	 * 返回详细信息
	 */
	private List<DataEntity> data;
}