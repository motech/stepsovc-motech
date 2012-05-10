package org.wv.stepsovc.utils;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDate(String dateStr) throws ParseException {
        return simpleDateFormat.parse(dateStr);
    }

    public static String nextDate(Date date){
        LocalDate localDate = LocalDate.fromDateFields(date);

        return simpleDateFormat.format(localDate.plusDays(1).toDate());
    }
}
