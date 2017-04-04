package sylu.com.doctorscheduling.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func4;
import rx.functions.Func5;
import rx.schedulers.Schedulers;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.MySharedPreferences;
import sylu.com.doctorscheduling.internet.ProgressSubscriber;
import sylu.com.doctorscheduling.internet.SubscriberOnNextListener;
import sylu.com.doctorscheduling.internet.entity.Dept_Admin_Info;
import sylu.com.doctorscheduling.internet.http.HttpMethods;
import sylu.com.doctorscheduling.utils.LogUtils;
import sylu.com.doctorscheduling.view.NetLoadingDialog;
import sylu.com.doctorscheduling.view.UIForeRunner;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Hudsvi on 2017/3/12 10:21.[
 */

public class LoginPresenter implements LoginContract.Presenter ,DialogInterface.OnCancelListener{
    private LoginContract.View view;
    private SubscriberOnNextListener listener;
    private Context context;
    private UIForeRunner foreRunner;
    private Activity acti;
    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;
    private Observable<String> observable;
    AlertDialog alert;//----------------连接服务器失败！
    private final int ALERT=1;//----------弹出对话框
    private final int ERRO_PWD=2;//----------密码或账号错误
    private final int SUCCESS=3;//------------连接数据库成功
    private String phone;//-----------编辑框的手机号
    private String net_phone;//-----------服务器的邮箱
    private String pwd;//----------编辑框的密码
    private String net_pwd;//----------服务器的密码
    private String user_identify;
    //--------异步访问网络
    private Handler login_handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ALERT :
                    alert=new AlertDialog.Builder(acti).setTitle("提示")
                            .setMessage("无法连接服务器！at:"+msg.obj.toString())
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alert.dismiss();
                                    foreRunner.dissmissNetLoadingDialog();
                                }
                            }).show();
                    break;
                case ERRO_PWD:
                    Toast.makeText(acti, "账号或者密码错误！", Toast.LENGTH_SHORT).show();
                    foreRunner.dissmissNetLoadingDialog();
                    break;
                case SUCCESS:
                    Intent login=new Intent(acti,MainActivity.class);
                    MySharedPreferences.getInstance(context).putStringValue(Constants.LOGIN_STATUS,"loged");
                    MySharedPreferences.getInstance(context).putStringValue(Constants.LOGIN_USER_ID,net_phone);
                    MySharedPreferences.getInstance(context).putStringValue(Constants.LOGIN_USER_ID,net_phone);

                    login.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(login);
                    acti.finish();
                    foreRunner.dissmissNetLoadingDialog();
                    break;
                case 4:
                    try {
                    } catch (Exception e) {
                        Toast.makeText(acti, e.toString()+"-----", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    public LoginPresenter(LoginContract.View view, SubscriberOnNextListener listener) {
        this.listener = listener;
        this.view = view;
        this.acti=view.getActi();
        this.context = view.getContext();
        view.setPresenter(this);
    }

    @Override
    public void attemptLogin() {
        if (view.inspectLoginParam()) {
            PrepareToLogin();
        }
        //弃用retrofit和okhttp框架--------------------------
        /*HttpMethods.getHttpMethods_instance().Dept_Admin_Login(view.getLoginParam(),
                new ProgressSubscriber<Dept_Admin_Info>(listener,view.getContext()));*/
    }

    private void PrepareToLogin() {
        UIRunner();
        ConnectToMySQL();
    }

    private void ConnectToMySQL() {
        LoginThread login=new LoginThread();
        login.start();
    }


    private void UIRunner() {
        foreRunner = new UIForeRunner(new WeakReference<Activity>(view.getActi()));
        foreRunner.showNetLoadingDialog("登录中……", this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }
    class LoginThread extends Thread{
        @Override
        public void run() {
            phone=view.getLoginParam().getParamMap().get("signin_name");
            pwd=view.getLoginParam().getParamMap().get("password");

            try {
                Class.forName(Constants.DRIVER);
                DriverManager.setLoginTimeout(Constants.TIME_OUT_READ);
                conn = DriverManager.getConnection(Constants.BASE_URL, Constants.SQL_USER, Constants.SQL_PWD);
//
                if(!conn.isClosed()){
                    pre_sta=conn.prepareStatement("select user,pword,identify from user where user=?");
                    pre_sta.setString(1,phone);
                    try{
                        rs=pre_sta.executeQuery();//结果集
                        while(rs.next()){
                            net_phone=rs.getString("user");
                            net_pwd=rs.getString("pword");
                            user_identify=rs.getString("identify");
                        }
                        if(phone.equals(net_phone)&&pwd.equals(net_pwd)){
                            Message m2=new Message();//------------登录成功
                            m2.what=3;
                            m2.obj=user_identify;
                            login_handler.sendMessage(m2);
                        }
                        else{
                            Message m3=new Message();//邮箱或密码不正确
                            m3.what=2;
                            login_handler.sendMessage(m3);
                        }
                    }
                    catch (Exception e){
                        Message m4=new Message();//cuowu
                        m4.what=2;
                        login_handler.sendMessage(m4);
                    }

                }
                else{
                    //-------------------连接失败
                    Message m5=new Message();
                    m5.what=1;
                    login_handler.sendMessage(m5);
                }
            }catch (Exception e){
                //
                Message m6=new Message();//无法连接到服务器
                m6.what=1;
                m6.obj=e.toString();
                login_handler.sendMessage(m6);
            }finally {
                disConnecting();
                }
                //
            }
/*
            observable = Observable.just(Constants.DRIVER);
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<String, ResultSet>() {
                        @Override
                        public ResultSet call(String s) {
                            return rs;

                        }
                    }).subscribe(subscriber);*/
        }

    private void disConnecting() {
        if(pre_sta!=null){
            try {
                pre_sta.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
}
}
