package com.gitee.linzl.cls;

import org.junit.Test;

public class ClassUtilsTest {
	@Test
	public void testExist() throws Exception {
		boolean isPresent = ClassUtils.isPresent("org.apache.commons.codec.binary.Base64", ClassUtilsTest.class.getClassLoader());
		System.out.println(isPresent);
	}
}
