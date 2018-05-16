package chapterOne;

import java.util.*;

public class Arrange {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();

		Scanner scan = new Scanner(System.in);

		String str = scan.nextLine();
		for (int i = 0; i < str.length(); i++) {
			list.add(i, str.substring(i, i + 1));
		}
		new Arrange().perm(list, 0, list.size() - 1);

	}

	void perm(List<String> list, int k, int m) {
		if (k == m) {
			for (int i = 0; i <= m; i++)
				System.out.print(list.get(i));
			System.out.println();
		} else {
			for (int i = k; i <= m; i++) {
				Collections.swap(list, k, i);
				perm(list, k + 1, m);
				Collections.swap(list, k, i);

			}
		}
	}
}