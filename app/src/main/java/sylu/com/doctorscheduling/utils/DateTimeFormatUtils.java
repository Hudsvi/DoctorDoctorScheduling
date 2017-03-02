package sylu.com.doctorscheduling.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hudsvi on 2017/3/2 13:42.
 */

public class DateTimeFormatUtils {
    //统一时间为milliseconds的形式,方便储存。
    private Context context;
    private  static final SimpleDateFormat MONTH_AND_YEAR=new SimpleDateFormat("yyyy'年'MM'月'");
    private  static final SimpleDateFormat WEEK_OF_MONTH=new SimpleDateFormat("'第'W'周'");
    private  static final SimpleDateFormat DAY_OF_WEEK=new SimpleDateFormat("'星期'u");
    private  static final SimpleDateFormat AM_OR_PM=new SimpleDateFormat("HH:mm:ss a");
    private  static Calendar calender;
    public DateTimeFormatUtils(Context context){
        this.context=context;
        this.calender=Calendar.getInstance();
    }
    public static String getMonthOfYear(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return MONTH_AND_YEAR.format(changeMSToDate(milliseconds));
    }
    public static String getDayOfWeek(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return DAY_OF_WEEK.format(changeMSToDate(milliseconds));
    }
    public static String getWeekOfMonth(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return WEEK_OF_MONTH.format(changeMSToDate(milliseconds));
    }
    public static String getAMPMOfDay(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return AM_OR_PM.format(changeMSToDate(milliseconds));
    }
    public static Date changeMSToDate(String ms){
        return new Date(Long.valueOf(ms));
    }
    public static String changeDateToMS(int year,int month,int day,int hour,int minute,int seconds){
       if(calender!=null) {
           calender.set(year, month, day, hour, minute, seconds);
           long ms = calender.getTimeInMillis();
           return String.valueOf(ms);
       }
        return "";
    }
}
