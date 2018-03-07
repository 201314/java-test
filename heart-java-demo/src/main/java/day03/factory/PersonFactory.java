package day03.factory;


//利用工厂模式获取父类的实例化
public class PersonFactory {
	public static Child createInstance(){
		return new Child();
	}
}
