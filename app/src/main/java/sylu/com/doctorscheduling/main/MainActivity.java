package sylu.com.doctorscheduling.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.custom.MyFragmentPagerAdapter;

/**
 * Created by Hudsvi on 2017/2/18 15:41.
 */

public class MainActivity extends BaseActivity implements MyFragmentPagerAdapter.MyOnPageChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void InitView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return 0;
    }

    @Override
    public void mPageSelected(int position) {

    }

    @Override
    public void mPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void mPageScrollStateChanged(int state) {

    }
}
