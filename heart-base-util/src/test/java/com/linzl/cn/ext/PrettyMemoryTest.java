/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.linzl.cn.ext;

import org.junit.Test;

import com.linzl.cn.ext.PrettyMemoryUtil;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-6-2 上午6:48
 * <p>
 * Version: 1.0
 */
public class PrettyMemoryTest {
	private static final int UNIT = 1024;

	@Test
	public void testPrettyByteSize() {
		System.out.println(PrettyMemoryUtil.prettyByteSize(1023));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT * UNIT));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT * 1023));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * 1023 * 1023 * 1023));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT * UNIT * UNIT));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT * UNIT * UNIT * UNIT));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT * UNIT * UNIT * UNIT * UNIT));
		System.out.println(PrettyMemoryUtil.prettyByteSize(1L * UNIT * UNIT * UNIT * UNIT * UNIT * UNIT));
	}

}
