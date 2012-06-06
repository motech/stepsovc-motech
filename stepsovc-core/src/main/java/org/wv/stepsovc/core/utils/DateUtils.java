package org.wv.stepsovc.core.utils;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static Logger logger = Logger.getLogger(DateUtils.class);
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static Date getDate(String dateStr) {
        try {
            return new SimpleDateFormat(YYYY_MM_DD).parse(dateStr);
        } catch (ParseException e) {
            logger.error("ParseExcption while parsing date:" + e);
        }
        return null;
    }

    public static String nextDateStr(String dateStr) throws ParseException {
        LocalDate localDate = LocalDate.fromDateFields(getDate(dateStr));

        return new SimpleDateFormat(YYYY_MM_DD).format(localDate.plusDays(1).toDate());
    }

    public static Date nextDate(Date date) {
        LocalDate localDate = LocalDate.fromDateFields(date);

        return localDate.plusDays(1).toDate();
    }

    public static LocalDate prevLocalDate(String dateStr) throws ParseException {
        LocalDate localDate = LocalDate.fromDateFields(getDate(dateStr));
        return localDate.minusDays(1);
    }

    public static String getFormattedDate(Date date) {
        return new SimpleDateFormat(YYYY_MM_DD).format(date);
    }

    public static LocalDate getLocalDate(String fromDate) {
        return LocalDate.fromDateFields(DateUtils.getDate(fromDate));
    }
}
