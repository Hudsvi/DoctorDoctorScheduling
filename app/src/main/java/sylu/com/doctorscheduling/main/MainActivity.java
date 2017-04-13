package sylu.com.doctorscheduling.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.custom.MyFragmentPagerAdapter;
import sylu.com.doctorscheduling.custom.NoScrollViewPager;
import sylu.com.doctorscheduling.utils.manager.ActivityManager;

/**
 * Created by Hudsvi on 2017/2/18 15:41.
 */

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.main_titlebar_textview)
    TextView mainTitlebarTextview;
    @BindView(R.id.main_titlebar_user)
    ImageView mainTitlebarUser;
    @BindView(R.id.main_titlebar_menu)
    ImageView mainTitlebarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_main_view)
    View tabMainView;
    @BindView(R.id.yuyue_main_img)
    ImageView yuyueMainImg;
    @BindView(R.id.yuyue_main_tv)
    TextView yuyueMainTv;
    @BindView(R.id.yuyue_main_lin)
    LinearLayout yuyueMainLin;
    @BindView(R.id.muban_main_img)
    ImageView mubanMainImg;
    @BindView(R.id.muban_main_tv)
    TextView mubanMainTv;
    @BindView(R.id.main_titlebar_right_tv)
    TextView mainTitlebarRightTv;
    @BindView(R.id.muban_doctor_add)
    ImageView mubanDoctorAdd;
    @BindView(R.id.muban_main_lin)
    LinearLayout mubanMainLin;
    @BindView(R.id.paiban_main_img)
    ImageView paibanMainImg;
    @BindView(R.id.paiban_doctor_produce)
    ImageView paibanDoctorProduce;
    @BindView(R.id.fuhe_doctor_notify)
    ImageView fuheDoctorNotify;
    @BindView(R.id.paiban_main_tv)
    TextView paibanMainTv;
    @BindView(R.id.paiban_main_lin)
    LinearLayout paibanMainLin;
    @BindView(R.id.fuhe_main_img)
    ImageView fuheMainImg;
    @BindView(R.id.fuhe_main_tv)
    TextView fuheMainTv;
    @BindView(R.id.fuhe_main_lin)
    LinearLayout fuheMainLin;
    @BindView(R.id.person_main_img)
    ImageView personMainImg;
    @BindView(R.id.person_main_tv)
    TextView personMainTv;
    @BindView(R.id.person_main_lin)
    LinearLayout personMainLin;
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;
    @BindView(R.id.main_linearlayout)
    ConstraintLayout mainLinearlayout;
    @BindView(R.id.main_titlebar_left_tv)
    TextView mainTitlebarLeftTv;
    @BindView(R.id.main_titlebar_left_tv2)
    TextView mainTitlebarLeftTv2;
    @BindView(R.id.main_titlebar_left_tv3)
    TextView mainTitlebarLeftTv3;
    @BindView(R.id.main_titlebar_left_tv4)
    TextView mainTitlebarLeftTv4;
    @BindView(R.id.muban_date)
    ConstraintLayout muban_date;
    private String[] titles;
    private List<Fragment> mfrags;
    private MyFragmentPagerAdapter adapter;
    private MainContract.Presenter presenter;
    private int index = 0;
    private long currenTime = 0;
    public static int page;

    @Override
    protected void InitView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        presenter = new MainPresenter(this);
        ImageView[] imgs = {yuyueMainImg, mubanMainImg, paibanMainImg, fuheMainImg, personMainImg};
        TextView[] tvs = {yuyueMainTv, mubanMainTv, paibanMainTv, fuheMainTv, personMainTv};
        presenter.setTabIcon(0, imgs, tvs);
        presenter.putFragments(imgs, tvs);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }


    public static void enter(Context context) {
        context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    public Context getContext() {
        return MainActivity.this;
    }

    @Override
    public void setAdapter(List<Fragment> lists, ImageView[] imgs, TextView[] tvs) {
        mainViewpager.setOffscreenPageLimit(5);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), lists, mainViewpager);
        adapter.setListener(new MyFragmentPagerAdapter.MyOnPageChangedListener() {
            @Override
            public void mPageSelected(int position) {
                presenter.setTabIcon(position, imgs, tvs);
                index = position;
                page = mainViewpager.getCurrentItem();
                if (position == 0) {
                    mainTitlebarMenu.setVisibility(View.VISIBLE);
                    mainTitlebarLeftTv.setVisibility(View.VISIBLE);
                } else {
                    mainTitlebarMenu.setVisibility(View.GONE);
                    mainTitlebarLeftTv.setVisibility(View.GONE);
                }
                if (position == 1) {
                    mubanDoctorAdd.setVisibility(View.VISIBLE);
                    muban_date.setVisibility(View.VISIBLE);
                } else{ muban_date.setVisibility(View.GONE);
                    mubanDoctorAdd.setVisibility(View.GONE);
                }
                if(position==2){
                    mainTitlebarLeftTv2.setVisibility(View.VISIBLE);
                    paibanDoctorProduce.setVisibility(View.VISIBLE);
                    mainTitlebarLeftTv2.setText("自动排班");
                }
                else{
                    mainTitlebarLeftTv2.setVisibility(View.GONE);
                    paibanDoctorProduce.setVisibility(View.GONE);
                }
                if(position==3){
                    mainTitlebarLeftTv3.setVisibility(View.VISIBLE);
                    fuheDoctorNotify.setVisibility(View.VISIBLE);

                }else{
                    mainTitlebarLeftTv3.setVisibility(View.GONE);
                    fuheDoctorNotify.setVisibility(View.GONE);
                }
                if(position==4){
                    mainTitlebarLeftTv4.setVisibility(View.VISIBLE);
                }else{
                    mainTitlebarLeftTv4.setVisibility(View.GONE);
                }
            }

            @Override
            public void mPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void mPageScrollStateChanged(int state) {

            }
        });
        mainViewpager.setNoScroll(false);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setCurrentItem(0);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onBackPressed() {
        if (index != 0) {
            mainViewpager.setCurrentItem(index - 1, true);
        } else if (System.currentTimeMillis() - currenTime < 2000) {
            ActivityManager.getInstance().Exit();
        } else {
            currenTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.main_titlebar_textview, R.id.main_titlebar_user, R.id.yuyue_main_lin, R.id.muban_main_lin, R.id.paiban_main_lin, R.id.fuhe_main_lin, R.id.person_main_lin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_titlebar_textview:
                break;
            case R.id.main_titlebar_user:
                break;
            case R.id.yuyue_main_lin:
                mainViewpager.setCurrentItem(0, true);
                break;
            case R.id.muban_main_lin:
                mainViewpager.setCurrentItem(1, true);
                break;
            case R.id.paiban_main_lin:
                mainViewpager.setCurrentItem(2, true);
                break;
            case R.id.fuhe_main_lin:
                mainViewpager.setCurrentItem(3, true);
                break;
            case R.id.person_main_lin:
                mainViewpager.setCurrentItem(4, true);
                break;
        }
    }

   /* private void openMenu() {
        if (mainViewpager.getCurrentItem() == 0) {
            toast("select menu");
        } else if (mainViewpager.getCurrentItem() == 1) {

        } else if (mainViewpager.getCurrentItem() == 2) {

        } else if (mainViewpager.getCurrentItem() == 3) {

        } else if (mainViewpager.getCurrentItem() == 4) {

        }
    }*/

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
