package sylu.com.doctorscheduling.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hudsvi on 2017/2/27 18:45.
 */

public class NetUtils {
    /*是否存在可用的网络*/
    public static boolean  isNetworkAvailable(Context context){
        Set<Boolean> networks=new HashSet<Boolean>();
        ConnectivityManager conn=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo=conn.getAllNetworkInfo();
        if(conn!=null&&netInfo!=null){
                boolean e;
                for (int i=0;i<=netInfo.length;i++){
                    e=netInfo[i].getState().equals(NetworkInfo.State.CONNECTED);
                    LogUtils.d(context.getClass().getName(),
                            "网络["+netInfo[i].getState().name()+"]是否可用："+String.valueOf(e));
                    networks.add(e);
            }
            if(networks.contains(true)){
                return true;
            }
        }
        return false;
    }
    /**
     * 打开网络设置界面
     */
    public static void settingOpen(Activity activity) {
        Intent intent = new Intent();
        ComponentName cname = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cname);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
