package com.dihari.majduri.DihariMajduri.mobile.common;
import java.sql.*;
import java.text.*;
public class Helper {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static String convertSqlDateToString(Date sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return sdf.format(utilDate);
    }

    public static Date convertStringToSqlDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        java.util.Date utilDate = sdf.parse(dateString);
        return new Date(utilDate.getTime());
    }
}
