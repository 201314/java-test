package jdk7.features;

/**
 * @description Switch语句支持string类型
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class SwitchDemo {

	public static void main(String[] args) {
		String test = "hello";
		switch (test) {
		case "world":
			System.out.println("我是word");
			break;
		case "hello":
			System.out.println("我是hello");
			break;
		default:
			break;
		}
	}
}
