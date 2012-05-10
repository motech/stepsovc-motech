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

    public static String getFormattedDate(Date date) {
        return simpleDateFormat.format(date);
    }

    public static LocalDate getLocalDate(String fromDate) {
        try {
            return LocalDate.fromDateFields(DateUtils.getDate(fromDate));
        } catch (ParseException e) {
        }
        return null;
    }
}
