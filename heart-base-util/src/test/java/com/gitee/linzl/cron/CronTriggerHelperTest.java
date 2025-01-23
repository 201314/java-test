package com.gitee.linzl.cron;

import org.apache.logging.log4j.core.util.CronExpression;

import java.util.Date;

public class CronTriggerHelperTest {

	public static void main(String[] args) throws Exception {
		CronTriggerHelper cron = CronTriggerHelper.getInstance();
		cron.repeatExecuteSecond(1);
		cron.repeatExecuteMinute(2);
		cron.repeatExecuteHour(3);
		//cron.repeatExecuteDay(4);
		cron.repeatExecuteMonth(5);
		cron.cycleWeek(1, 3);
		//cron.dayOfWeek(1, 2);
		System.out.println(cron.generateCron());

		CronExpression cronExpression = new CronExpression(cron.generateCron());
		System.out.println(cronExpression.getNextValidTimeAfter(new Date()));
	}
}
