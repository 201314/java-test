package day12.util;

import java.util.Objects;

public class ObjectsDemo {

	public static void main(String[] args) {
		String first = "hello";
		String second = first;
		System.out.println(Objects.equals(first, second));
		Objects.requireNonNull(first, "first must not be null");

		// 其实就是用Arrays.hashCode()
		// 如果要deep层次就用Arrays.deepHashCode()
		System.out.println(Objects.hash(first, second));
	}
}
