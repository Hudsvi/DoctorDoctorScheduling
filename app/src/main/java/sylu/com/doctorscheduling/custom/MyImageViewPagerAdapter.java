package sylu.com.doctorscheduling.custom;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Hudsvi on 2017/2/16 16:25.
 *适用于一些简单不使用Fragmnet的View,占用内存资源情况十分严重，优化方案详见MyFragmentPagerAdapter
 */

public class MyImageViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
    private ArrayList<View> mViewsLists;
    public MyImageViewPagerAdapter(ArrayList<View> viewlists) {
        this.mViewsLists=viewlists;
    }
    @Override
    public int getCount() {
        if(mViewsLists==null){return 0;}
        return mViewsLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view =mViewsLists.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewsLists.get(position));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
