package sylu.com.doctorscheduling.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.utils.LogUtils;

import static sylu.com.doctorscheduling.constants.Constants.SHARED_PREFS_LOCATION;

/**
 * Created by Hudsvi on 2017/2/15 23:55.
 */

public class MySharedPreferences {
    private static MySharedPreferences sp;
    private static SharedPreferences spre;
    private static SharedPreferences.Editor editor;

    public static MySharedPreferences getInstance(Context context) {
        if (spre == null) {
            spre = context.getSharedPreferences(SHARED_PREFS_LOCATION, 0);
            editor = spre.edit();
        }
        if (sp == null) {
            sp = new MySharedPreferences();
        }
        return sp;
    }

    public void putIntValue(String name, int value) {
        editor.putInt(name, value);
        editor.apply();
    }

    public int getIntValue(String name) {
        int i = spre.getInt(name, 0);
        return i;
    }

    public void putBooleanValue(String name, boolean b) {
        editor.putBoolean(name, b);
        editor.apply();
    }

    public boolean getBooleanValue(String name) {
        boolean b = spre.getBoolean(name, true);
        return b;
    }

    public void putStringValue(String name, String s) {
        editor.putString(name, s);
        editor.apply();
    }

    public String getStringValue(String name) {
        String s = spre.getString(name, "");
        return s;
    }
    public void putObject(String name,Object ob){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(ob);
            // 将对象的转为base64码
            String objBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));

            editor.putString(name, objBase64).apply();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  Object  getObject(String name){
        Object ob=null;
        String stringOb=spre.getString(name,null);
        if(stringOb==null){
            return null;
        }
        byte[] bytesob=Base64.decode(stringOb,Base64.DEFAULT);
        try {
            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(bytesob));
            ob=(Object)ois.readObject();
            ois.close();
        } catch (IOException e) {
            LogUtils.e("getObject",e.toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LogUtils.e("getObject",e.toString());
            e.printStackTrace();
        }
        return ob;
    }
    public  void setDeviceID(String deviceID){
        editor.putString(Constants.LOGIN_DEVICE_ID,deviceID).apply();
    }
    public  String getDeviceID(){
        return spre.getString(Constants.LOGIN_DEVICE_ID,null);
    }
}
