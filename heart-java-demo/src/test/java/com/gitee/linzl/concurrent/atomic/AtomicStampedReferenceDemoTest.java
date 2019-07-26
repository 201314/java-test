package com.gitee.linzl.concurrent.atomic;

import org.junit.Test;

public class AtomicStampedReferenceDemoTest {
	@Test
	public void testInc() {
		AtomicStampedReferenceDemo demo = new AtomicStampedReferenceDemo();
//		demo.ABAerror();

		demo.ABAright();
	}
}
