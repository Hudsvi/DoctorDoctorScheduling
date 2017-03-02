package sylu.com.doctorscheduling.utils.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import java.io.File;
import java.io.IOException;

/**
 * Created by Hudsvi on 2017/3/1 21:25.
 */

public class PhoneManager {
    private final static String EMPTY = "";
    // 判断手机屏幕是否锁定
    public final static boolean isScreenOn(Context context) {
        PowerManager powermanager;
        powermanager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powermanager == null)
            return false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return powermanager.isInteractive();
        } else {
            return powermanager.isScreenOn();
        }
    }

    /**
     * 描述：
     * 获取设备唯一标识号
     * 1、IMEI
     * 2、wifi mac
     * 3、google id
     * @return
     */
    public static String getUniqueIdentifies(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        if (isEmpty(device_id)) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            device_id = mac;
        }
        if(isEmpty(device_id)){
            String id=Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
            device_id=id ;
        }
        return device_id;
    }


    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 存储卡操作
     */
    public final static class SDCardManger {

        /**
         * 获取存储卡路径
         *
         * @return 路径
         */
        public static String getSDCardPath(String pathFile, Context context) {
            if (pathFile == null || EMPTY.equals(pathFile.trim())) return EMPTY;
            String stornPath = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                if (Environment.getExternalStorageDirectory().canWrite()) {
                    stornPath = Environment.getExternalStorageDirectory().getPath();
                }
            }
            if ((stornPath == null) || (EMPTY.equalsIgnoreCase(stornPath.trim()))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    stornPath = getExtralcard(context);
                }
            }

            if (stornPath != null) {
                StringBuffer logPath = new StringBuffer();
                logPath.append(stornPath);
                logPath.append(File.separator);
                logPath.append(context.getPackageName())
                        .append(File.separator);
                logPath.append(pathFile);

                try {
                    File file = new File(logPath.toString());
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    return logPath.toString();
                } catch (IOException e) {
                    e.toString();
                }
            }
            return EMPTY;
        }

        @TargetApi(9)
        private static String getExtralcard(Context context) {
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            // 获取sdcard的路径：外置和内置
            String[] paths = null;
            try {
                paths = (String[]) sm.getClass().getMethod("getVolumePaths").invoke(sm);
            } catch (Exception e) {
                e.toString();
            }

            File file;
            String extralPath = null;
            if (paths != null) {
                for (String path : paths) {
                    file = new File(path);
                    if (file.exists() && (file.canWrite())) {
                        extralPath = path;
                        break;
                    }
                }
            }
            return extralPath;
        }
    }
}

