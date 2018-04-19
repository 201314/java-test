package com.linzl.cn.kuaidi;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 详细信息
 * 
 * @author liny
 * 
 */
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

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public String getFtime() {
		return ftime;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}