package com.gitee.linzl.cron;

import com.gitee.linzl.lang.StringUtil;
import com.gitee.linzl.time.DateFormatUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.CalendarUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.logging.log4j.core.util.CronExpression;
import org.apache.spark.util.CollectionsUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CronTriggerHelperTest {

    @Test
    public void helperTest() throws Exception {
        CronTriggerHelper cron = CronTriggerHelper.getInstance();
        cron.repeatExecuteSecond(1);
        cron.repeatExecuteMinute(2);
        cron.repeatExecuteHour(3);
        //cron.repeatExecuteDay(4);
        cron.repeatExecuteMonth(5);
        cron.cycleWeek(1, 3);
        //cron.dayOfWeek(1, 2);
        System.out.println(cron.generateCron());

        CronExpression cronExpression = new CronExpression("15 31 0 * * ?");
        System.out.println(DateFormatUtil.format(cronExpression.getNextValidTimeAfter(new Date()), "YYYY-MM-dd HH:mm:ss"));
    }

    @Test
    public void helperTest2() throws IOException {
        List<String> list = FileUtils.readLines(new File("C:/Users/linzhenlie-jk/Desktop/workflow.csv"), "UTF-8");
        list.remove(0);
        List<String> collect = list.stream().map(str -> {
            List<String> line = StringUtil.splitToList(str, ",");
            if (!CollectionUtils.isEmpty(line) && line.size() == 3) {
                CronExpression cronExpression;
                try {
                    cronExpression = new CronExpression(line.get(2));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                Date nextValidTimeAfter = cronExpression.getNextValidTimeAfter(new Date());
                long fragmentInHours = DateUtils.getFragmentInHours(nextValidTimeAfter, Calendar.DAY_OF_YEAR);
                if (fragmentInHours <= 7) {
                    return StringUtils.appendIfMissing(str, "," + fragmentInHours);
                }
                return null;
            }
            return null;
        }).collect(Collectors.toList());
        collect.removeAll(Collections.singleton(null));
        FileUtils.writeLines(new File("C:/Users/linzhenlie-jk/Desktop/workflow_result.csv"), collect);
    }
}
