package com.t4.LiveServer.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static Date getCurrentDateInUTC(){
        ZonedDateTime zdt = ZonedDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String dateStr = zdt.format(formatter);
        Date utcDate = null;
        try {
            utcDate = format.parse(dateStr);

        } catch (Exception e) {
            return new Date();
        }
        return utcDate;

    }
}
