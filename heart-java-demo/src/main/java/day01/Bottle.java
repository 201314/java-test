package day01;

public class Bottle {
	private int sum;

	public Bottle(int sum) {
		this.sum = sum;
	}

	public void test(int count) {
		int i = count;
		if (i >= 3) {
			int temp = i / 3; // 兑换得到的瓶数
			int remainder = i % 3; // 不可兑换的瓶数
			sum += temp; // 总共可以喝多少瓶
			int j = temp + remainder; // 最终剩下的瓶数
			test(j);

		} else {
			System.out.println("总共可以喝多少瓶：" + sum);
			System.out.println("最终剩下的瓶数:" + i);
		}
	}

	public static void main(String args[]) {
		Bottle bottle = new Bottle(100);
		bottle.test(bottle.sum);
	}
}
