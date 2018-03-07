package day01;

public class Count {
	public void test() {
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i + "*" + j + "=" + i * j + "\t");
				if (j == i)
					System.out.println("\n");
			}
		}
	}

	public static void main(String args[]) {
		Count hello = new Count();
		hello.test();
	}
}
