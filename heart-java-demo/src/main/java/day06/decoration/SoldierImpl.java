package day06.decoration;

public class SoldierImpl implements Soldier {//装饰器模式  的基本实现类
	public void fire() {
		System.out.println("开枪干掉小日本");
	}
	public void run() {
		System.out.println("韩国，菲律宾，越南……你们这些嚣张小国别想逃");
	}

}
