package sylu.com.doctorscheduling.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.MyImageViewPagerAdapter;
import sylu.com.doctorscheduling.custom.MySharedPreferences;

/**
 * Created by Hudsvi on 2017/2/15 22:00.
 */

public class SplashActivity extends Activity {
    @BindView(R.id.splash_pager)
    ViewPager vPager;
    @BindView(R.id.splash_nav)
    ConstraintLayout constraint;
    @BindView(R.id.splash_start_frame)
    FrameLayout start_frame;
    @BindView(R.id.splash_start)
    Button starting;
    @BindView(R.id.splash_logo)
    ImageView logo;
    private ArrayList<View> mViews;
    private MyImageViewPagerAdapter adapter;
    private boolean FirstTimeLaunched;
    private String UserLoginStatus;
    private Context context;

    @OnClick(R.id.splash_start)
    void Onclik(View v) {
        MySharedPreferences.getInstance().putBooleanValue(Constants.FIRST_TIME_LAUNCHED, false);
        LoginActivity.enter(this);
        finish();
    }
    @OnPageChange(R.id.splash_pager)
    void onPageSelected(int position){
        if(position==2){
            start_frame.setVisibility(View.VISIBLE);
        }
        else
            start_frame.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.splash);
        ButterKnife.bind(this);
        inspectLaunch();//如果是初次登陆，那么进入导航页
        if(!FirstTimeLaunched) {
            checkLoginStatus();//进入登录界面前，判断用户是否已经登陆过系统
        }

    }

    private void checkLoginStatus() {
        reckonByTime(2)
                .filter(t -> t == 0)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        UserLoginStatus = MySharedPreferences.getInstance().getStringValue(Constants.LOGIN_STATUS);
                        if (UserLoginStatus.equals("")) {
                            LoginActivity.enter(context);
                            finish();
                        } else {
                            MainActivity.enter(context);
                            finish();
                        }
                    }
                });
    }

    private void inspectLaunch() {
        FirstTimeLaunched = MySharedPreferences.getInstance().getBooleanValue(Constants.FIRST_TIME_LAUNCHED);
        if (FirstTimeLaunched) {
            logo.setVisibility(View.GONE);
            putData();
        }
    }


    private void putData() {
        mViews = new ArrayList<>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.splash_guide1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.splash_guide2, null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.splash_guide3, null);
        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);
        adapter = new MyImageViewPagerAdapter(mViews) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }



            @Override
            public void onPageSelected(int position) {

            }
        };
        vPager.setAdapter(adapter);
    }

    private Observable<Integer> reckonByTime(int time) {
        if (time < 0)
            time = 0;
        final int delayTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(t -> delayTime - t.intValue())
                .take(delayTime+1);
    }
}
