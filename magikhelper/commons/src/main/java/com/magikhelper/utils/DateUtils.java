package com.magikhelper.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date addDaysToCurrentDate(int days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
    public static Date addDaysToDate(Date date,int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
    
    public static Timestamp currentTimeStamp(Date date){
        return new Timestamp(date.getTime());
    }
    public static Timestamp currentTimeStamp(){
        return currentTimeStamp(new Date());
    }
}
