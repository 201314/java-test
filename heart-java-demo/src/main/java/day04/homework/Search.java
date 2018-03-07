package day04.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Search {
	InputStream is1;
	InputStreamReader isr;
	BufferedReader buf;
	String str;
	List<String> list;

	public static final String getReplace(String str) {// 去除一些标点符号
		if (str != null) {
			str = str.replaceAll(",", " ");
			str = str.replaceAll(";", " ");// 当为 . 时，替换会出现全部被替换？
		}
		return str;
	}

	public static String input() {// 获取键盘输入信息
		Scanner scan = new Scanner(System.in);
		String fileName = scan.nextLine();
		return fileName;
	}

	public List<String> getImformation() {
		try {
			System.out.println("输入你要搜索的文件路径 ：");
			// file = new File("src/a.txt");// 使用相对路径
			// File file=new
			// File("D:\\Workspaces\\MyEclipse 8.5\\test\\src\\a.txt");//使用绝对路径
			File file = new File(input());
			InputStream in = new FileInputStream(file);
			buf = new BufferedReader(new InputStreamReader(in));
			str = getReplace(buf.readLine());
			list = new ArrayList<String>();
			while (str != null) { // 判断是否文本还有信息
				list.add(str); // 将信息添加在list
				str = getReplace(buf.readLine()); // 继续读取文本下一行信息

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				buf.close();
			} catch (Exception ee) {
				System.out.println(ee.getMessage());
			}
		}
		return list;
	}

	public void findKey(List<String> list) {
		System.out.println("输入你要搜索的内容：");
		String information = input();
			for (int i=0; i < list.size(); i++) {
				String perLineInformation[] = list.get(i).split(" ");
				for (int j = 0; j < perLineInformation.length; j++) {
					if (perLineInformation[j].contains(information)) {
						System.out.println("搜索到的信息在第" + i + "行，第" + j + "列");
					} 
				}
				if((!list.contains(information))&&(i==(list.size()-1))){//每行都搜索不到要查找的信息
					System.out.println("非常抱歉，您所查找的信息不存在");
				}
			}
	}

	public static void main(String[] args) {
		Search search = new Search();
		List<String> list = search.getImformation();
		search.findKey(list);
	}

}
