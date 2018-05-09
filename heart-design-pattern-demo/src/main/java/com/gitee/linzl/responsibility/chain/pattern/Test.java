package com.gitee.linzl.responsibility.chain.pattern;

public class Test {
	public static void main(String[] args) {
		Manager pm=new ProjectManager();
		Manager md=new Majordomo();
		Manager gm=new GeneralManager();
		
		//设置项目经理的上级
		pm.setSuperManager(md);
		//设置总监的上级
		md.setSuperManager(gm);
		
		Apply apply=new Apply();
		apply.setApplyType(1);
		apply.setNumber(5);
		
		System.out.println("请假处理结果："+pm.handleRequest(apply));
		
		apply.setApplyType(2);
		apply.setNumber(1260);
		System.out.println("申请加薪处理结果："+pm.handleRequest(apply));
	}
}
