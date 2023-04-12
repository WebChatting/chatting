package com.sxrekord.chatting.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Rekord
 * @date 2023/4/12 22:46
 */
public class TimeUtils {
    public static Date subtractDaysFromDate(Date date, int days) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate resultLocalDate = localDate.minusDays(days);
        return Date.from(resultLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
