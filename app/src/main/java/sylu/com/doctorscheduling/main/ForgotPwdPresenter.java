package sylu.com.doctorscheduling.main;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.transition.Transition;
import android.widget.Toast;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.internet.ProgressSubscriber;
import sylu.com.doctorscheduling.internet.SubscriberOnNextListener;
import sylu.com.doctorscheduling.internet.jdbc.SQLConnector;

/**
 * Created by Hudsvi on 2017/3/25 19:56.
 */

public class ForgotPwdPresenter implements ForgotPwdContract.Presenter {
    private ForgotPwdContract.View view;
    private SubscriberOnNextListener listener;
    /*private String driver;
    private String url;
    private String user;
    private static Driver dr;*/
    private String password;
    private ProgressSubscriber<Boolean> subs;
    private static boolean send=false;
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;

    public ForgotPwdPresenter(ForgotPwdContract.View view, SubscriberOnNextListener listener) {
        this.view = view;
        this.listener = listener;
        view.setPresenter(this);
    }


    @Override
    public void findPasswoed() {
        subs = new ProgressSubscriber<Boolean>(listener, view.getActi());
        subs.setLoading(" 密码重置中… ");
        send=view.booleanVCodeCorrect();
        Observable.just(send)
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        if (aBoolean) {
                            try {
                                Class.forName(Constants.DRIVER);
                            } catch (ClassNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                conn = DriverManager.getConnection(Constants.BASE_URL, Constants.SQL_USER, Constants.SQL_PWD);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                conn.setAutoCommit(false);
                                pre_sta = conn.prepareStatement("update user set pword=? where user=?");
                                pre_sta.setString(1, view.getNewPassword());
                                pre_sta.setString(2, view.getPhoneNum());
                                pre_sta.execute();
                                conn.commit();
                            } catch (SQLException e1) {
                                Toast.makeText(view.getActi(), "erro", Toast.LENGTH_SHORT).show();
                                e1.printStackTrace();
                            } finally {
                                if (conn != null) {
                                    try {
                                        conn.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            return true;
                        } else {
                            Toast.makeText(view.getActi(), "验证码不正确！", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subs);

    }
}
