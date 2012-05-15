package org.wv.stepsovc.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDate(String dateStr) throws ParseException {
        return simpleDateFormat.parse(dateStr);
    }

    public static String nextDateStr(Date date){
        LocalDate localDate = LocalDate.fromDateFields(date);

        return simpleDateFormat.format(localDate.plusDays(1).toDate());
    }

    public static String nextDateStr(String dateStr) throws ParseException {
        LocalDate localDate = LocalDate.fromDateFields(getDate(dateStr));

        return simpleDateFormat.format(localDate.plusDays(1).toDate());
    }

    public static Date nextDate(Date date){
        LocalDate localDate = LocalDate.fromDateFields(date);

        return localDate.plusDays(1).toDate();
    }

    public static Date nextDate(String dateStr) throws ParseException {
        LocalDate localDate = LocalDate.fromDateFields(getDate(dateStr));

        return localDate.plusDays(1).toDate();
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

    public static DateTime getDateTime(String serviceDate) {
        try {
            return DateUtil.newDateTime(simpleDateFormat.parse(serviceDate));
        } catch (ParseException e) {
            throw new RuntimeException("Date format wrong:" + serviceDate, e);
        }
    }
}
