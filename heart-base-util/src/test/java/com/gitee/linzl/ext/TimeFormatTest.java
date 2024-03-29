package com.gitee.linzl.ext;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.gitee.linzl.time.DateFormatUtil;

/**
 * 
 * @author linzl
 *
 */
public class TimeFormatTest {

	@Test
	public void testPrettyDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, 7);
		cal.set(Calendar.DAY_OF_MONTH, 28);

		System.out.println(DateFormatUtil.prettyFormat(cal.getTime()));
		System.out.println(DateFormatUtil.prettyFormat(new Date()));
		System.out.println(DateFormatUtil.prettyFormat(123));
	}

	@Test
	public void testprettyFormat() {
		System.out.println(DateFormatUtil.prettyFormat(10));
		System.out.println(DateFormatUtil.prettyFormat(61));
		System.out.println(DateFormatUtil.prettyFormat(3661));
		System.out.println(DateFormatUtil.prettyFormat(36611));
		System.out.println(DateFormatUtil.prettyFormat(366111));
		System.out.println(DateFormatUtil.prettyFormat(3661111));
		System.out.println(DateFormatUtil.prettyFormat(36611111));
	}
}
