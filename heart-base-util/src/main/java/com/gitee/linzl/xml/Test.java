package com.gitee.linzl.xml;

import java.util.ArrayList;
import java.util.List;

import com.gitee.linzl.xml.JaxbUtil.CollectionWrapper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

		List<RoomTypeVO> roomTypeVOs2 = new ArrayList<RoomTypeVO>();
		roomTypeVOs2.add(t1);
		roomTypeVOs2.add(t2);
		hotel2.setRoomTypeVOs(roomTypeVOs2);

		List list = new ArrayList();
		list.add(hotel);
		list.add(hotel2);

		// 将java对象转换为XML字符串
		JaxbUtil requestBinder = new JaxbUtil(Hotel.class, CollectionWrapper.class);
		// String retXml = requestBinder.toXml(list, "utf-8");
		String retXml = requestBinder.toXml(list, "root", "utf-8");
		System.out.println("xml:" + retXml);

		retXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" + "<hotel title='变化的公寓'>"
				+ "	   <id>1</id>" + "    <name>name1</name>" + "    <roomTypeVO>" + "        <price>20</price>"
				+ "        <typeid>1</typeid>" + "        <typename>typename1</typename>" + "    </roomTypeVO>"
				+ "    <roomTypeVO>" + "        <price>30</price>" + "        <typeid>2</typeid>"
				+ "        <typename>typename2</typename>" + "    </roomTypeVO>" + "</hotel>";
		// 将xml字符串转换为java对象
		JaxbUtil resultBinder = new JaxbUtil(Hotel.class, CollectionWrapper.class);
		Hotel hotelObj = resultBinder.fromXml(retXml);

		System.out.println("hotelid:" + hotelObj.getId());
		System.out.println("hotelname:" + hotelObj.getName());
		System.out.println("map属性---》" + hotelObj.getMap());
		// System.out.println("属性title--》" + hotelObj.getTitle());
		for (RoomTypeVO roomTypeVO : hotelObj.getRoomTypeVOs()) {
			System.out.println("Typeid:" + roomTypeVO.getTypeid());
			System.out.println("Typename:" + roomTypeVO.getTypename());
			System.out.println("price--:" + roomTypeVO.getPrice());
		}
	}
}
