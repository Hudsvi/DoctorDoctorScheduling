package sylu.com.doctorscheduling.custom;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Hudsvi on 2017/2/18 13:37.
 */

public abstract class MyFragmentPagerAdapter extends PagerAdapter implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private List<android.app.Fragment> frags;
    private String[] titles;
    private android.app.FragmentManager fmanager;
    private MyOnPageChangedListener pagechanged;

    public MyFragmentPagerAdapter(android.app.FragmentManager fmanager, List<android.app.Fragment> frags, String[] titles) {
        this.fmanager = fmanager;
        this.frags = frags;
        this.titles = titles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (frags != null)
            container.removeView(frags.get(position).getView());
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        android.app.Fragment frag = frags.get(position);
        if (!frag.isAdded()) {
            android.app.FragmentTransaction fTransaction = fmanager.beginTransaction();
            fTransaction.add(frag,null);
            fTransaction.commitAllowingStateLoss();
            fmanager.executePendingTransactions();
            if (frag.getView().getParent() == null) {
                container.addView(frag.getView());
            }
        }
        return frag.getView();
    }


    @Override
    public int getCount() {
        return frags == null ? 0 : frags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles[position];
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        pagechanged.mPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        pagechanged.mPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        pagechanged.mPageScrollStateChanged(state);
    }

    @Override
    public void onClick(View v) {

    }

    public MyOnPageChangedListener getPagechanged() {
        return pagechanged;
    }

    public void setPagechanged(MyOnPageChangedListener pagechanged) {
        this.pagechanged = pagechanged;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public interface MyOnPageChangedListener {
        void mPageSelected(int position);//自定义Tab和Titles的点击事件如何处理

        void mPageScrolled(int position, float positionOffset, int positionOffsetPixels);//自定义Tab和Titles的滑动事件如何处理

        void mPageScrollStateChanged(int state);
    }

}
