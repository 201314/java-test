package com.gitee.linzl.util;

import java.util.Objects;

import org.junit.Test;

public class TestDemo {
	@Test
	public void testObjects() {
		String first = "hello";
		String second = first;
		System.out.println(Objects.equals(first, second));
		Objects.requireNonNull(first, "first must not be null");

		// 其实就是用Arrays.hashCode()
		// 如果要deep层次就用Arrays.deepHashCode()
		System.out.println(Objects.hash(first, second));
	}

	@Test
	public void testBitSet() {

	}

	@Test
	public void testOptional() {

	}
}
