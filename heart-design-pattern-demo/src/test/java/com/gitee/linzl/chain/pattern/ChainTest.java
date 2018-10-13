package com.gitee.linzl.chain.pattern;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.gitee.linzl.chain.pattern.demo2.Chain;
import com.gitee.linzl.chain.pattern.demo2.ChainHandler;
import com.gitee.linzl.chain.pattern.demo2.GeneralHandler;
import com.gitee.linzl.chain.pattern.demo2.MajordomoHandler;
import com.gitee.linzl.chain.pattern.demo2.ProjectHandler;

public class ChainTest {

	@Test
	public void testDemo() {
		Manager pm = new ProjectManager();
		Manager md = new Majordomo();
		Manager gm = new GeneralManager();

		// 设置项目经理的上级
		pm.setSuperManager(md);
		// 设置总监的上级
		md.setSuperManager(gm);

		Apply apply = new Apply();
		apply.setApplyType(1);
		apply.setNumber(5);

		System.out.println("请假处理结果：" + pm.handleRequest(apply));

		apply.setApplyType(2);
		apply.setNumber(1260);
		System.out.println("申请加薪处理结果：" + pm.handleRequest(apply));
	}

	/**
	 * 此种责任模式，更能解耦，各责任方不需要知道处理不了时下一个责任方是谁。
	 */
	@Test
	public void testDemo2() {
		List<ChainHandler> handler = Arrays.asList(

				new ProjectHandler(),

				new MajordomoHandler(),

				new GeneralHandler());

		Apply apply = new Apply();
		apply.setApplyType(1);
		apply.setNumber(5);

		Chain chain = new Chain(handler, apply);
		System.out.println("请假处理结果：" + chain.proceed());

		apply.setApplyType(2);
		apply.setNumber(126000);
		chain = new Chain(handler, apply);
		System.out.println("申请加薪处理结果：" + chain.proceed());
	}
}
