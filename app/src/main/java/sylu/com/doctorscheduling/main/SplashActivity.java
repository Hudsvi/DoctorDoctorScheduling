package sylu.com.doctorscheduling.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.fragment.Splash_Frag1;
import sylu.com.doctorscheduling.fragment.Splash_Frag2;
import sylu.com.doctorscheduling.fragment.Splash_Frag3;

/**
 * Created by Hudsvi on 2017/2/15 22:00.
 */

public class SplashActivity extends FragmentActivity {
    @BindView(R.id.splash_viewpager)
    ViewPager vPager;
    private ArrayList<android.support.v4.app.Fragment> mFrags = new ArrayList<>();
    private FragmentPagerAdapter fAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash_firsttime);
        ButterKnife.bind(this);
        boolean b3=vPager==null;
        Toast.makeText(this, String.valueOf(b3), Toast.LENGTH_LONG).show();
        putData();
        super.onCreate(savedInstanceState);
    }


    private void putData() {
        mFrags.add(new Splash_Frag1());
        mFrags.add(new Splash_Frag2());
        mFrags.add(new Splash_Frag3());
        fAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFrags.get(position);
            }

            @Override
            public int getCount() {
                return mFrags.size();
            }
        };
        vPager.setAdapter(fAdapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.layout_stay, R.anim.layout_translate_out);
    }

}
