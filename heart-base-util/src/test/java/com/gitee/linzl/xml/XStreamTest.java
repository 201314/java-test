package com.gitee.linzl.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XStreamTest {
	@Test
	public void toFile2() {
		// 创建java对象
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("name1");

		RoomTypeVO t1 = new RoomTypeVO();
		t1.setPrice("20");
		t1.setTypeid(1);
		t1.setTypename("typename1");

		RoomTypeVO t2 = new RoomTypeVO();
		t2.setPrice("30");
		t2.setTypeid(2);
		t2.setTypename("typename2");

		List<RoomTypeVO> roomTypeVOs = new ArrayList<RoomTypeVO>();
		roomTypeVOs.add(t1);
		roomTypeVOs.add(t2);
		hotel.setRoomTypeVOs(roomTypeVOs);

		Hotel hotel2 = new Hotel();
		hotel2.setId(1);
		hotel2.setName("name1");

		RoomTypeVO t12 = new RoomTypeVO();
		t12.setPrice("20");
		t12.setTypeid(1);
		t12.setTypename("typename1");

		RoomTypeVO t22 = new RoomTypeVO();
		t22.setPrice("30");
		t22.setTypeid(2);
		t22.setTypename("typename2");

		List<RoomTypeVO> roomTypeVOs2 = new ArrayList<>();
		roomTypeVOs2.add(t1);
		roomTypeVOs2.add(t2);
		hotel2.setRoomTypeVOs(roomTypeVOs2);

		List<Hotel> list = new ArrayList<>();
		list.add(hotel);
		list.add(hotel2);

		// 将java对象转换为XML字符串
		// XStream xstream = new XStream(new Dom4JDriver());
		// try {
		// xstream.toXML(list, new FileOutputStream(new File("D://write-jaxb2.xml")));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }

		Root root = new Root();
		root.setHotel(list);

		// 将java对象转换为XML字符串
		// 解决下划线问题
		XStream xstream2 = new XStream(new Dom4JDriver(new XmlFriendlyNameCoder("_-", "_")));
		try {
			xstream2.processAnnotations(root.getClass());
			xstream2.toXML(root, new FileOutputStream(new File("D://write-jaxb-two2.xml")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
