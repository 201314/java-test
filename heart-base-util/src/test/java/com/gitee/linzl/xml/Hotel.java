package com.gitee.linzl.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(value = "hotel")
public class Hotel {
	private int id;
	private String name;

	@XStreamAlias(value = "testHello")
	private List<RoomTypeVO> roomTypeVOs;

	public List<RoomTypeVO> getRoomTypeVOs() {
		return roomTypeVOs;
	}

	public void setRoomTypeVOs(List<RoomTypeVO> roomTypeVOs) {
		this.roomTypeVOs = roomTypeVOs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
