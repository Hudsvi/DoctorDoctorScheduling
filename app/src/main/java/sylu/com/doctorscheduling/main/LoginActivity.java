package sylu.com.doctorscheduling.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.internet.LoginRequest;
import sylu.com.doctorscheduling.internet.SubscriberOnNextListener;
import sylu.com.doctorscheduling.internet.entity.Dept_Admin_Info;
import sylu.com.doctorscheduling.utils.PhoneNumberUtils;
import sylu.com.doctorscheduling.utils.manager.PhoneManager;

/**
 * Created by Hudsvi on 2017/3/4 20:28.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View, SubscriberOnNextListener<Dept_Admin_Info> {
    @BindView(R.id.sin_phone)
    EditText userid;
    @BindView(R.id.sin_pwd)
    EditText userpwd;
    @BindView(R.id.sin_del_user)
    ImageView clear_id;
    @BindView(R.id.sin_del_pwd)
    ImageView clear_pwd;
    @BindView(R.id.sin_enter)
    Button enter;
    @BindView(R.id.sin_sup)
    Button signup;
    @BindView(R.id.pwd_forgot)
    TextView pwd_forgot;
    private Context con;
    LoginContract.Presenter presenter;
    LoginRequest request;
    String user_id, user_pwd;

    @OnClick({R.id.sin_enter, R.id.sin_sup, R.id.sin_del_pwd, R.id.sin_del_user, R.id.pwd_forgot})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.sin_enter:
                presenter.attemptLogin();
                break;
            case R.id.sin_del_pwd:
                clearEditText(userpwd);
                break;
            case R.id.sin_del_user:
                clearEditText(userid);
                break;
            case R.id.sin_sup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;
            case R.id.pwd_forgot:
                startActivity(new Intent(LoginActivity.this, ForgotPwdActivity.class));
                break;

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = this;
    }

    @Override
    protected void InitView(Bundle savedInstanceState) {
        presenter = new LoginPresenter(this, this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.signin;
    }

    public static void enter(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public boolean inspectLoginParam() {
        user_id = userid.getText().toString();
        user_pwd = userpwd.getText().toString();
        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(user_pwd)) {
            Toast.makeText(con, "哎呀，您一不小心把账号或密码打错了。", Toast.LENGTH_SHORT).show();
        } else if (!PhoneNumberUtils.validatedNumber(user_id.trim().substring(0, 3)) || user_id.trim().length() != 11) {
            Toast.makeText(con, "客官，手机格式有误哦", Toast.LENGTH_SHORT).show();
        }else if(user_pwd.trim().length()<6){
            Toast.makeText(con, "客官，密码太短了", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public LoginRequest getLoginParam() {
        request = new LoginRequest(user_id, user_pwd, PhoneManager.getUniqueIdentifies(con));
        return request;

    }

    @Override
    public void clearEditText(EditText text) {
        text.setText("");
    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /*
    *网络的回调方法
    */
    @Override
    public void onNext(Dept_Admin_Info dept_admin_info) {
    }
}
