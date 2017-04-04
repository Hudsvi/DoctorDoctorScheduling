package sylu.com.doctorscheduling.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.bean.BmobSmsState;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.QuerySMSStateListener;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.internet.ProgressSubscriber;
import sylu.com.doctorscheduling.internet.SubscriberOnNextListener;
import sylu.com.doctorscheduling.utils.NetUtils;
import sylu.com.doctorscheduling.utils.PhoneNumberUtils;
import sylu.com.doctorscheduling.utils.manager.PhoneManager;

/**
 * Created by Hudsvi on 2017/3/5 19:17.
 */

public class ForgotPwdActivity extends BaseActivity implements ForgotPwdContract.View,SubscriberOnNextListener<Boolean> {
    @BindView(R.id.pwd_forgot_back)//----------返回
            ImageView pwdForgotBack;
    @BindView(R.id.textView5)//-------------标题
            TextView textView5;
    @BindView(R.id.pwd_forgot_title_cons)//标题布局
            ConstraintLayout pwdForgotTitleCons;
    @BindView(R.id.pwd_forgot_phone_et)//编辑手机号
            EditText pwdForgotPhoneEt;
    @BindView(R.id.id102)
    ImageView id102;
    @BindView(R.id.pwd_forgot_del_phone)
    ImageView pwdForgotDelPhone;
    @BindView(R.id.id101)
    ConstraintLayout id101;
    @BindView(R.id.id107)
    ImageView id107;
    @BindView(R.id.pwd_forgot_verify_code)//编辑验证码
            EditText pwdForgotVerifyCode;
    @BindView(R.id.pwd_forgot_verify_code_del)
    ImageView pwdForgotVerifyCodeDel;
    @BindView(R.id.id106)
    ConstraintLayout id106;
    @BindView(R.id.pwd_forgot_send_ver_code)//发送验证码
            Button pwdForgotSendVerCode;
    @BindView(R.id.id105)
    ConstraintLayout id105;
    @BindView(R.id.id1012)
    ImageView id1012;
    @BindView(R.id.pwd_forgot_new_pwd)//密码1
            EditText pwdForgotNewPwd;
    @BindView(R.id.pwd_forgot_new_pwd_del)
    ImageView pwdForgotNewPwdDel;
    @BindView(R.id.id1011)
    ConstraintLayout id1011;
    @BindView(R.id.id1016)
    ImageView id1016;
    @BindView(R.id.pwd_forgot_new_pwd2)//密码2
            EditText pwdForgotNewPwd2;
    @BindView(R.id.pwd_forgot_new_pwd_del2)
    ImageView pwdForgotNewPwdDel2;
    @BindView(R.id.id1015)
    ConstraintLayout id1015;
    @BindView(R.id.sup_ok)//---------重置密码的Button
            Button supOk;
    private final int SEND_V_CODE = 1;
    private final int NO_NETWORK_CONNECTION = 2;
    private final int V_CODE_SUCCESSED = 3;//----------验证成功
    private  int v_code_success =0;//--------------------验证码是否验证成功，成功则为 1，否则为0
    private boolean v_pwd_successed=false;//---------------密码是否已经录入服务器
    private boolean v_code_send=false;//----------验证码是否发送
    private ForgotPwdContract.Presenter presenter;
    private Subscription sub;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;//----------------------手机号
    private boolean send_btn_enable = true;
    private String msm_Id="0";//------------验证码

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private String newPassword;//----------------------新密码
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEND_V_CODE:
                    break;
                case NO_NETWORK_CONNECTION:


                    break;
                case V_CODE_SUCCESSED:
                    presenter.findPasswoed();
                    break;
            }
        }
    };

    @Override
    protected void InitView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        BmobSMS.initialize(this, "e4e83568afaa5f602d8fcbf9c27718ba");
        presenter=new ForgotPwdPresenter(this,this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.pwd_found;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @OnClick({R.id.pwd_forgot_back, R.id.pwd_forgot_phone_et, R.id.pwd_forgot_del_phone, R.id.pwd_forgot_verify_code, R.id.pwd_forgot_verify_code_del, R.id.pwd_forgot_send_ver_code, R.id.pwd_forgot_new_pwd, R.id.pwd_forgot_new_pwd_del, R.id.pwd_forgot_new_pwd2, R.id.pwd_forgot_new_pwd_del2, R.id.sup_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pwd_forgot_back:
                super.onBackPressed();
                break;
            case R.id.pwd_forgot_phone_et://手机号码
                break;

            case R.id.pwd_forgot_verify_code://验证码
                break;

            case R.id.pwd_forgot_send_ver_code://发送验证码
                if (NetUtils.isNetworkAvailable(this)) {
                    if (pwdForgotPhoneEt.getText().toString().trim().length() == 11&&
                        PhoneNumberUtils.validatedNumber(pwdForgotPhoneEt.getText()
                                .toString().trim().substring(0,3)) ) {
                        sendVerificationCode();
//                    querySmsState();
                    } else {
                        toast("手机格式不正确！");
                    }
                } else {
                    Message me1 = new Message();
                    me1.what = 2;
                    handler.sendMessage(me1);
                }
                break;
            case R.id.pwd_forgot_new_pwd://新密码
                break;

            case R.id.pwd_forgot_new_pwd2://确定新密码
                break;

            case R.id.pwd_forgot_del_phone:
                clearEditText(pwdForgotPhoneEt);
                break;
            case R.id.pwd_forgot_verify_code_del:
                clearEditText(pwdForgotVerifyCode);
                break;
            case R.id.pwd_forgot_new_pwd_del:
                clearEditText(pwdForgotNewPwd);
                break;
            case R.id.pwd_forgot_new_pwd_del2:
                clearEditText(pwdForgotNewPwd2);
                break;
            case R.id.sup_ok://重置密码
                phone=pwdForgotPhoneEt.getText().toString();
                newPassword=pwdForgotNewPwd2.getText().toString();
                setPhone(phone);
                setNewPassword(newPassword);
                if(!getMsm_Id().equals("0")) {
//                    presenter.findPasswoed();
                    doResetPWD();//------------检验密码是否一致&&验证码是否一致
                }else{
                    toast("请先发送验证码");
                }
                break;
        }
    }


@Override
    public  void doResetPWD() {
        if (!PhoneManager.containEmpty(pwdForgotNewPwd.getText().toString()) &&
                !PhoneManager.containEmpty(pwdForgotNewPwd2.getText().toString())) {
            if (pwdForgotNewPwd2.getText().toString().equals(pwdForgotNewPwd.getText().toString())) {
                //--------------两个操作，一是验证密码的合法性、一致性，二是检测验证码的正确性。
                if(pwdForgotNewPwd2.getText().toString().length()>=6) {
                verifyCode();
                }else{
                    toast("密码不能少于6位！");
                }
            }
            else{
                toast("前后密码不一致！");
            }
        } else {
            Toast.makeText(this, "密码包含非法字符！", Toast.LENGTH_SHORT).show();
        }
    }

    private void cutdownTime(Integer id) {
        setMsm_Id(String.valueOf(id));
        toast(getMsm_Id());
        sub = countSendTime(150)
                .filter(t -> t >= 0)
                .subscribe(new Action1<Integer>() {
                               @Override
                               public void call(Integer integer) {
                                   if (send_btn_enable) {
                                       send_btn_enable = false;
                                       pwdForgotSendVerCode.setEnabled(false);
                                   }
                                   pwdForgotSendVerCode.setText("剩余(" + integer + ")秒");

                                   if (integer == 0) {
                                       send_btn_enable = true;
                                       pwdForgotSendVerCode.setEnabled(true);
                                       pwdForgotSendVerCode.setText("重新发送验证码");
                                   }
                               }
                           }
                );
    }
@Override
    public boolean querySmsState() {
    BmobSMS.querySmsState(this, Integer.valueOf(getMsm_Id()), new QuerySMSStateListener() {
        @Override
        public void done(BmobSmsState bmobSmsState, BmobException e) {
            if (e == null) {
                v_code_send = bmobSmsState.getVerifyState().equals("true");
            } else {
                Toast.makeText(ForgotPwdActivity.this, "erro at:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    });
    return this.v_code_send;
    }

    @Override
    public void sendVerificationCode() {
        BmobSMS.requestSMSCode(ForgotPwdActivity.this, pwdForgotPhoneEt.getText().toString(), "门诊医生排班", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {
                    toast("验证码发送成功！");
                    cutdownTime(smsId);
                } else {
                    toast("验证码未发送！");
                }
            }
        });

    }

    @Override
    public boolean booleanVCodeCorrect() {
        return v_code_success==1;
    }

    @Override
    public boolean booleanPwdSuccess() {
        return v_pwd_successed;
    }

@Override
    public int verifyCode() {
        BmobSMS.verifySmsCode(this, pwdForgotPhoneEt.getText().toString(),
                pwdForgotVerifyCode.getText().toString(), new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            v_code_success = 1;
                            Message m3=new Message();
                            m3.what=3;
                            handler.sendMessage(m3);
                        } else {
                            Toast.makeText(ForgotPwdActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    return v_code_success;
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearEditText(EditText et) {
        et.setText("");
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void setPresenter(ForgotPwdContract.Presenter presenter) {
        this.presenter = presenter;

    }

    private Observable<Integer> countSendTime(int time) {
        if (time < 0)
            time = 0;
        final int sendtime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(t -> sendtime - t.intValue())
                .take(sendtime + 1);
    }

    /*class MySMSCodeListener implements SMSCodeListener {

        @Override
        public void onReceive(String content) {
            if(pwdForgotVerifyCode != null){
                pwdForgotVerifyCode.setText(content);
            }
        }

    }*/
    public String getMsm_Id() {
        return msm_Id;
    }

    public void setMsm_Id(String msm_Id) {
        this.msm_Id = msm_Id;
    }

    @Override
    public void onNext(Boolean success) {
        if(success) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("温馨提示")
                    .setMessage("密码已重置，请重新登录系统！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
        else{
            Toast.makeText(this, "密码重置失败！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public Activity getActi(){
        return ForgotPwdActivity.this;
    }

    @Override
    public String getPhoneNum() {
        return phone;
    }

    @Override
    public String getNewPassword() {
        return newPassword;
    }
}
