package day01;

public class Max {
	int first, second, temp;

	public void count(int first, int second) {
		if (second % first == 0) {
			System.out.println("最大公约数是" + first);
		} else {
			for (int j = 1; j <= (first / 2); j++) {
				if (first % j == 0 && second % j == 0)
					temp = j;
			}
			System.out.println("最大公约数是" + temp);
		}
	}

	public static void main(String args[]) {
		Max max = new Max();
		max.count(30, 100);// 假设输入的第一个数比第二个数小
	}
}
