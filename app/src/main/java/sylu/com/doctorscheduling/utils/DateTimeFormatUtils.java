package sylu.com.doctorscheduling.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hudsvi on 2017/3/2 13:42.
 */

public class DateTimeFormatUtils {
    //统一时间为milliseconds的形式,方便储存。
    private static Context context;
    public   static final SimpleDateFormat MONTH_AND_YEAR=new SimpleDateFormat("yyyy-MM-dd");
    public   static final SimpleDateFormat DATE_TIME=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public   static final SimpleDateFormat WEEK_OF_MONTH=new SimpleDateFormat("'第'W'周'");
//    private  static final SimpleDateFormat DAY_OF_WEEK=new SimpleDateFormat("星期U");
    private  static final SimpleDateFormat AM_OR_PM=new SimpleDateFormat("HH:mm:ss");
//    private  static final SimpleDateFormat AM_OR_PM=new SimpleDateFormat("HH:mm:ss a");//a表示上午
    private static Calendar calendar;
    private static DateTimeFormatUtils datetime=new DateTimeFormatUtils();

    public DateTimeFormatUtils(){
    }
    public DateTimeFormatUtils(Context context){
        this.context=context;
    }
    public static DateTimeFormatUtils getDatetimeInstance(){
        if(calendar==null) {
            calendar = Calendar.getInstance();
        }
        if(datetime==null){
            datetime=new DateTimeFormatUtils();
        }
        return datetime;
    }
    public DateTimeFormatUtils getDatetimeInstance(Context context){
        if(context==null) {
            context = context;
        }
        if(calendar==null) {
            calendar = Calendar.getInstance();
        }
        if(datetime==null){
            datetime=new DateTimeFormatUtils();
        }
        return datetime;
    }
    public  String getMonthOfYear(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return MONTH_AND_YEAR.format(changeMSToDate(milliseconds));
    }
    /*
    public String getDayOfWeek(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return DAY_OF_WEEK.format(changeMSToDate(milliseconds));
    }*/
    public static String getWeekOfMonth(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return WEEK_OF_MONTH.format(changeMSToDate(milliseconds));
    }
    public String getAMPMOfDay(String milliseconds){
        if(milliseconds.trim().length()!=13){
            return "";
        }
        return AM_OR_PM.format(changeMSToDate(milliseconds));
    }
    public static Date changeMSToDate(String ms){

        return new Date(Long.valueOf(ms));
    }
    public String changeDateToMS(Date date){
        if(date==null) {
           return "1000000000000";
        }
       else
        if(calendar !=null) {
           calendar.set(date.getYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds());
           long ms = calendar.getTimeInMillis();
           return String.valueOf(ms);
       }
        return "";
    }
}
