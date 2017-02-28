package sylu.com.doctorscheduling.utils;

import android.util.Log;

/**
 * Created by Hudsvi on 2017/2/27 15:48.
 */

public  class LogUtils{
    private static final int LEVEL_OF_DEGREE=2;//默认打印等级为2及以上的日志
    private static final int VERBOSE=1;
    private static final int DEBUG=2;
    private static final int INFO=3;
    private static final int WARN=4;
    private static final int ERRO=5;
    private static final int ASSERT=6;

    public static void  v(String tag,String msg){
        if(LEVEL_OF_DEGREE>=VERBOSE){
            Log.d(tag,msg);
        }
    }
    public static void  d(String tag,String msg){
        if(LEVEL_OF_DEGREE>=DEBUG){

            Log.d(tag,msg);
        }
    }public static void  i(String tag,String msg){
        if(LEVEL_OF_DEGREE>=INFO){

            Log.i(tag,msg);
        }
    }public static void  w(String tag,String msg){
        if(LEVEL_OF_DEGREE>=WARN){

            Log.w(tag,msg);
        }
    }public static void  e(String tag,String msg){
        if(LEVEL_OF_DEGREE>=ERRO){
            Log.e(tag,msg);

        }
    }public static void  a(String tag,String msg){
        if(LEVEL_OF_DEGREE>=ASSERT){

            Log.wtf(tag,msg);
        }
    }
}
