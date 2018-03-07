package day03;

public class TestFactory {
	public static Driver createInstance(String type){
		if("Lenovo".equals(type)){
			return new LenovoDriver();
		}else if("Dell".equals(type)){
			return new DellDriver();
		}else{
			return new Driver();
		}
	}
}
