package com.gitee.linzl.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "roomTypeVO_how")
public class RoomTypeVO {
	private int typeid;
	private String typename;
	private String price;

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}