package sylu.com.doctorscheduling.main;


import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import sylu.com.doctorscheduling.BaseView;

/**
 * Created by Hudsvi on 2017/3/25 19:52.
 */

public class ForgotPwdContract {
    interface View extends BaseView<Presenter>{
        void sendVerificationCode();//发送验证码
        boolean booleanVCodeCorrect();//验证码是否正确
        boolean booleanPwdSuccess();
        Activity getActi();
        boolean querySmsState();
        int verifyCode();
        String getPhoneNum();
        String getNewPassword();
        void doResetPWD();
        void clearEditText(EditText et);
        Context getContext();
    }
    interface Presenter{
    void findPasswoed();
    }
}
