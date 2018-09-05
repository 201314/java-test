package com.gitee.linzl.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.gitee.linzl.xml.JaxbUtil.CollectionWrapper;

public class JaxbUtilTest {
	@Test
	public void toXML() {
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
		JaxbUtil requestBinder = new JaxbUtil(Hotel.class, CollectionWrapper.class);
		String retXml = requestBinder.toXml(list, "root", "utf-8");
		System.out.println("xml:" + retXml);
	}

	@Test
	public void toBean() {
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
		hotel2.setId(2);
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
		JaxbUtil requestBinder = new JaxbUtil(Hotel.class, CollectionWrapper.class);
		String retXml = requestBinder.toXml(list, "root", "utf-8");
		System.out.println("xml:" + retXml);

		// 将xml字符串转换为java对象
		JaxbUtil resultBinder = new JaxbUtil(Root.class);
		Root root = resultBinder.toBean(retXml);
		System.out.println(JSON.toJSONString(root));
	}

	@Test
	public void toFile() {
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
		JaxbUtil one = new JaxbUtil(Hotel.class, CollectionWrapper.class);
		one.writeToFile(list, "", new File("D://write-jaxb.xml"));

		Root root = new Root();
		root.setHotel(list);
		JaxbUtil two = new JaxbUtil(Root.class);
		two.writeToFile(root, new File("D://write-jaxb-two.xml"));
	}
}
