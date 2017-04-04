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

public class MyFragmentPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> frags;
    private FragmentManager fmanager;
    private MyOnPageChangedListener listener;
    private NoScrollViewPager viewPager;
    private String[] titles;
    private FragmentTransaction fTransaction;
    private int currentView=0;//------------默认为初始位置
    public MyFragmentPagerAdapter(FragmentManager fmanager, List<Fragment> frags,NoScrollViewPager viewPager) {
        this.fmanager = fmanager;
        this.frags = frags;
        this.viewPager=viewPager;
        this.viewPager.setOnPageChangeListener(this);
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
        Fragment frag = frags.get(position);
        frag.setMenuVisibility(true);
        if (!frag.isAdded()) {
            fTransaction = fmanager.beginTransaction();
            fTransaction.add(frag, frag.getClass().getSimpleName());
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(listener !=null) {
            listener.mPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        frags.get(currentView).onPause();//-----------暂停
        if(frags.get(position).isAdded()){
            frags.get(position).onResume();//--------运行
        }
        currentView=position;
        if(listener !=null){
            listener.mPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(listener !=null) {
            listener.mPageScrollStateChanged(state);
        }
    }


    public MyOnPageChangedListener getListener() {
        return listener;
    }

    public void setListener(MyOnPageChangedListener listener) {
        this.listener = listener;
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
