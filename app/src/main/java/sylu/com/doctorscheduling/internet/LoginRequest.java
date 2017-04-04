package sylu.com.doctorscheduling.internet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import sylu.com.doctorscheduling.utils.MD5Utils;

/**
 * Created by Hudsvi on 2017/3/5 14:09.
 */

public class LoginRequest implements Serializable{

    private String login_name;

    private String password;

    private String device_token;

    public LoginRequest(String login_name, String password, String device_token) {
        this.login_name = login_name;
        this.password = password;
        this.device_token = device_token;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public void setPassword(String password) {
        this.password = MD5Utils.EncryptOrDecryptByMD5(password);
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public Map<String,String> getParamMap(){
        Map<String,String> map = new HashMap<>();
        map.put("signin_name",login_name);
        map.put("password", password);
        map.put("device_token",device_token);
        return map;
    }
}
