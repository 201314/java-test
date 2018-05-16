package chapterTwo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GregorianCalendar d = new GregorianCalendar();

		int today = d.get(Calendar.DAY_OF_MONTH);// 今天是第几号
		int month = d.get(Calendar.MONTH);// 这个月是第几个月
		int firstToday = today % 7;

		d.set(Calendar.DAY_OF_MONTH, 1);// 将1号设为一个月第一天
		int weekday = d.get(Calendar.DAY_OF_WEEK);// 今天是星期几

		System.out.println("Sun Mon Tue Wed Thu Fri Sat");

		for (int i = Calendar.SUNDAY; i <= weekday - firstToday + 1; i++)
			System.out.printf("    ");

		do {
			int day = d.get(Calendar.DAY_OF_MONTH);// 获得1号
			System.out.printf("%3d", day);

			if (day == today)
				System.out.print("*");
			else
				System.out.print(" ");

			if (weekday == Calendar.SATURDAY)
				System.out.println();

			d.add(Calendar.DAY_OF_MONTH, 1);// 在1号的基础上不断加1
			weekday = d.get(Calendar.DAY_OF_WEEK);
		} while (d.get(Calendar.MONTH) == month);

		// if(weekday!=Calendar.SUNDAY)
		// System.out.println();

	}

}
