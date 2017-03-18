package sylu.com.doctorscheduling.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnPageChange;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.custom.MyFragmentPagerAdapter;
import sylu.com.doctorscheduling.fragment.personal.Personal_Fragment;
import sylu.com.doctorscheduling.fragment.fuhe.Fuhe_Fragment;
import sylu.com.doctorscheduling.fragment.muban.Muban_Fragment;
import sylu.com.doctorscheduling.fragment.paiban.Paiban_Fragment;
import sylu.com.doctorscheduling.fragment.yuyue.Yuyue_Fragment;
import sylu.com.doctorscheduling.view.NetLoadingDialog;

/**
 * Created by Hudsvi on 2017/2/18 15:41.
 */

public class MainActivity extends BaseActivity{
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.main_titlebar_textview)
    TextView title;
    @BindViews({R.id.yuyue_main, R.id.muban_main,
            R.id.fuhe_main, R.id.paiban_main, R.id.personal_main})
    List<LinearLayout> bar_linearlayouts;
   /* @BindViews({R.id.yuyue_main_imgview, R.id.muban_main_imgview,
            R.id.fuhe_main_imgview, R.id.zidongpaiban_main_imgview, R.id.fabu_main_imgview})
    List<RadioButton> bar_images;
    @BindViews({R.id.yuyue_main_textview, R.id.muban_main_textview,
            R.id.fuhe_main_textview, R.id.zidongpaiban_main_textview, R.id.fabu_main_textview})
    List<TextView> bar_texts;*/
    private String[] titles;
    private List<android.app.Fragment> mfrags;
    private android.app.FragmentManager fragManager;
    private MyFragmentPagerAdapter adapter;
    private Context context;
    @OnPageChange(R.id.main_viewpager) void OnClick(){NetLoadingDialog d=new NetLoadingDialog(this).build("加載中....");
    d.setLoadingImage(R.drawable.delete);
    d.show();}
    @Override
    protected void InitView(Bundle savedInstanceState) {
        context=this;
        setTitles();
        putFragments();
        addAdapter();
    }

    private void addAdapter() {
        adapter=new MyFragmentPagerAdapter(fragManager,mfrags,titles){};
        adapter.setPagechanged(new MyFragmentPagerAdapter.MyOnPageChangedListener() {
            @Override
            public void mPageSelected(int position) {
                Toast.makeText(context, "position_selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void mPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(context, "position_scrolled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void mPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void putFragments() {
        mfrags = new ArrayList<>();
        fragManager =getFragmentManager();
        Yuyue_Fragment yuyue = new Yuyue_Fragment();
        Muban_Fragment muban = new Muban_Fragment();
        Personal_Fragment person = new Personal_Fragment();
        Fuhe_Fragment fuhe = new Fuhe_Fragment();
        Paiban_Fragment paiban = new Paiban_Fragment();
        mfrags.add(yuyue);
        mfrags.add(muban);
        mfrags.add(fuhe);
        mfrags.add(paiban);
        mfrags.add(person);
    }

    private void setTitles() {
        titles = new String[]{getResources().getString(R.string.main_yuyue), getResources().getString(R.string.main_muban),
                getResources().getString(R.string.main_fuhe),
                getResources().getString(R.string.main_zidongpaiban), getResources().getString(R.string.main_personal)};
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    public static void enter(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
