package com.gitee.linzl.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "root1")
public class Root {
	@XStreamAlias(value = "hotel")
	private List<Hotel> hotel;

	public List<Hotel> getHotel() {
		return hotel;
	}

	public void setHotel(List<Hotel> hotel) {
		this.hotel = hotel;
	}
}
