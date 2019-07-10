package com.gitee.linzl.demo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PrintCalendar {
	public static void main(String[] args) {
		GregorianCalendar d = new GregorianCalendar();
		d.set(Calendar.MONDAY, 11);
		int month = d.get(Calendar.MONTH);// 这个月是第几个月
		int today = d.get(Calendar.DAY_OF_MONTH);// 今天是第几号
		int weekday = d.get(Calendar.DAY_OF_WEEK);// 今天是星期几

		// 将1号设为一个月第一天
		d.set(Calendar.DAY_OF_MONTH, 1);
		// 1号是星期几
		int firstDay = d.get(Calendar.DAY_OF_WEEK);

		System.out.println("Sun Mon Tue Wed Thu Fri Sat");

		// 每月1号是星期几，前面有几个空格
		for (int i = Calendar.SUNDAY; i < firstDay; i++) {
			System.out.printf("    ");
		}

		do {
			int day = d.get(Calendar.DAY_OF_MONTH);// 获得1号
			System.out.printf("%3d", day);

			if (day == today) {
				System.out.print("*");
			} else {
				System.out.print(" ");
			}

			if (weekday == Calendar.SATURDAY) {// 遇到周六要换行
				System.out.println();
			}

			d.add(Calendar.DAY_OF_MONTH, 1);// 在1号的基础上不断加1，得到2号，3号直到月尾
			weekday = d.get(Calendar.DAY_OF_WEEK);
		} while (d.get(Calendar.MONTH) == month);
	}
}
