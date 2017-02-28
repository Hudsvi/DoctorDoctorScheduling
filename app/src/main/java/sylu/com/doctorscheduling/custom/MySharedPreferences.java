package sylu.com.doctorscheduling.custom;

import android.content.Context;
import android.content.SharedPreferences;
import static sylu.com.doctorscheduling.constants.Constants.SHARED_PREFS_LOCATION;

/**
 * Created by Hudsvi on 2017/2/15 23:55.
 */

public class MySharedPreferences {
    private static MySharedPreferences sp;
    private static SharedPreferences spre;
    private static SharedPreferences.Editor editor;
    public static MySharedPreferences getInstance(Context context){
        spre=context.getSharedPreferences(SHARED_PREFS_LOCATION,0);
        editor=spre.edit();
        if(sp==null){
            sp=new MySharedPreferences();
        }
        return sp;
    }
    public void putIntValue(String name,int value){
        editor.putInt(name,value);
        editor.commit();
    }
    public int getIntValue(String name){
        int i=spre.getInt(name,0);
        return i;
    }
    public void putBooleanValue(String name,boolean b){
        editor.putBoolean(name,b);
        editor.commit();
    }
    public boolean getBoolreanValue(String name){
        boolean b=spre.getBoolean(name,false);
        return b;
    }
    public void putStringValue(String name,String s){
        editor.putString(name,s);
        editor.commit();
    }
    public String getStringValue(String name){
        String s=spre.getString(name,"");
        return s;
    }
}
