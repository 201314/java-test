package com.gitee.linzl.prototype.pattern;

import java.text.ParseException;

/**
 * 浅复制： 被复制对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用仍然指向原来的对象。
 * 换言之，浅复制仅仅复制所考虑的对象，而不复制它所引用的对象。
 */
public class SimpleTest {
	public static void main(String[] args) throws ParseException {
		SimpleWorkExperience simpleWork = new SimpleWorkExperience("2012-6-6", "CCYV");
		SimpleResume resume = new SimpleResume("lzl", 23, simpleWork);

		System.out.println("————开始克隆  浅复制————");
		SimpleResume resume2 = (SimpleResume) resume.simpleClone();
		resume2.getWork().setCompany("浅复制，改掉公司名称");

		resume.displayResume(); // 浅复制 只复制引用对象
	}
}
