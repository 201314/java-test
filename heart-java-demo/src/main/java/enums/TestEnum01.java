package enums;

public class TestEnum01 {
	public enum Num {
		a, b, c
	}

	public void test(Num num) {
		System.out.println(num);
	}

	public static void main(String[] args) {
		TestEnum01 tt = new TestEnum01();
		tt.test(Num.b);
	}

}
