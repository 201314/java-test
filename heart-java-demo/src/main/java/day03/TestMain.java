package day03;

public class TestMain {
	public void test(Driver driver){
		driver.install();
	}
	public static void main(String[] args) {
		TestMain testMain=new TestMain();
		testMain.test(TestFactory.createInstance("Lenovo"));
		testMain.test(TestFactory.createInstance("Dell"));
		testMain.test(TestFactory.createInstance("Driver"));
		}
	}

