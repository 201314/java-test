package com.gitee.linzl.cls;

import org.junit.Test;

public class ClassUtilTest {
	@Test
	public void testExist() throws Exception {
		boolean isPresent = ClassUtil.isPresent("org.apache.commons.codec.binary.Base64", ClassUtilTest.class.getClassLoader());
		System.out.println(isPresent);
	}
}
