package com.linzl.cn.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hotel")
public class Hotel {
	// private String title;
	private int id;
	private String name;
	private Map map = new HashMap() {
		{
			put("1", "age");
			put("2", "height");
		}
	};

	@XmlElement(name = "roomTypeVO")
	public List<RoomTypeVO> getRoomTypeVOs() {
		return roomTypeVOs;
	}

	public void setRoomTypeVOs(List<RoomTypeVO> roomTypeVOs) {
		this.roomTypeVOs = roomTypeVOs;
	}

	private List<RoomTypeVO> roomTypeVOs;

	// @XmlElement(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// @XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @XmlAttribute
	// public String getTitle() {
	// return title;
	// }

	// public void setTitle(String title) {
	// this.title = title;
	// }

}
