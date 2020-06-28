package com.rysh.module.utils;

import com.rysh.module.statistics.beans.TimeCount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeCale {
    public static List<TimeCount> timeList(String startTime, String endTime) throws ParseException {
        List<TimeCount> timeCounts = new ArrayList<>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateA = sf.parse(startTime);

        Instant instant = dateA.toInstant();

        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTimeA = instant.atZone(zoneId).toLocalDateTime();

        Date dateB = sf.parse(endTime);

        Instant instantB = dateB.toInstant();

        ZoneId zoneIdB = ZoneId.systemDefault();

        LocalDateTime localDateTimeB = instantB.atZone(zoneIdB).toLocalDateTime();

        Duration between = Duration.between(localDateTimeA, localDateTimeB);

        String c=startTime;
        TimeCount timeCount1 = new TimeCount();

        timeCount1.setTime(c);

        timeCount1.setCount(0);
        timeCounts.add(timeCount1);
        long l = between.toDays();
        for (long i = 0; i < l; i++) {
            TimeCount timeCount = new TimeCount();
            c=sf.format(new Date(sf.parse(c).getTime()+86400000));
            timeCount.setTime(c);
            timeCount.setCount(0);
            timeCounts.add(timeCount);
        }
        return timeCounts;
    }

    /**
     * 将当前结束时间推后一天
     * @param endTimeStr
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/22 9:48
     */
    public static String delayOneDay(String endTimeStr){
        Date endTimeDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            endTimeDate = sdf.parse(endTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endTimeLong = endTimeDate.getTime() + 86400000;//加一天
        Date newEndTime = new Date(endTimeLong);
        String endTime = sdf.format(newEndTime);
        return endTime;
    }
}
