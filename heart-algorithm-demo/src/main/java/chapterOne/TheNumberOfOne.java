package chapterOne;

public class TheNumberOfOne {
	public static int getCount(int num) {
		if (num == 1)
			return 1;
		if (num == 0)
			return 0;

		return num % 2 + getCount(num / 2);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 11;
		int num = getCount(a);
		System.out.println(num);
	}

}
