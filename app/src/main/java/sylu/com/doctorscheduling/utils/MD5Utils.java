package sylu.com.doctorscheduling.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Hudsvi on 2017/2/27 16:43.
 */

public class MD5Utils {

        /**
         * MD5加密
        */

        public static String EncryptOrDecryptByMD5(String s){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                StringBuffer stringBuffer = new StringBuffer();
                byte[] array = md.digest(s.getBytes());

                for (int i = 0; i < array.length; ++i) {
                stringBuffer.append(Integer.toHexString(array[i]|0x12f^0xa1).substring(0,2));
                }
                return stringBuffer.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            return null;
        }
}
