package com.gitee.linzl.cron;

public class CronTriggerHelperTest {

	public static void main(String[] args) throws Exception {
		CronTriggerHelper cron = CronTriggerHelper.getInstance();
		cron.new SecondBuilder().repeatExecute(5).build();
		cron.new MinuteBuilder().repeatExecute(5).build();
		cron.new HourBuilder().repeatExecute(5).build();
		cron.new DayBuilder().repeatExecute(5).build();
		cron.new MonthBuilder().repeatExecute(5).build();
		cron.new WeekBuilder().cycle(1, 5).build();
		System.out.println(cron.generateCron());
	}
}
