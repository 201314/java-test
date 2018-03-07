package com.linzl.prototype.pattern;

import java.text.ParseException;
/**
 * 深复制：
 * 被复制对象的所有变量都含有与原来的对象相同的值，除去那些引用其他对象的变量。
 * 那些引用其他对象的变量将指向被复制过的新对象，而不再是原有的那些被引用的对象。
 * 换言之，深复制把要复制的对象所引用的对象都复制了一遍。
 */
public class DeepTest {
	public static void main(String[] args) throws ParseException {
		DeepWorkExperience DeepWork=new DeepWorkExperience("2012-6-6","CCYV");
		DeepResume resume=new DeepResume("lzl",23,DeepWork);
		
		System.out.println("————开始克隆 深复制————");
		
		DeepResume deepResume1=(DeepResume) resume.deepClone();
		deepResume1.getWork().setCompany("1——深复制，改掉公司名称会影响父本resume简历吗——1？");
		
		DeepResume deepResume2=(DeepResume) resume.deepClone();
		deepResume2.getWork().setCompany("2——深复制，改掉公司名称会影响父本resume简历吗——2？");
	
		deepResume1.displayResume();
		deepResume2.displayResume();
		resume.displayResume();
	}
}
