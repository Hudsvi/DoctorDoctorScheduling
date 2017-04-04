package sylu.com.doctorscheduling.main;

import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import sylu.com.doctorscheduling.BaseView;

/**
 * Created by Hudsvi on 2017/3/25 11:43.
 */

public class MainContract {
    interface View extends BaseView<Presenter>{
        Context getContext();
        void setAdapter(List<android.support.v4.app.Fragment> lists, ImageView[] imgs, TextView[] tvs);
    }
    interface Presenter{
        void setTabIcon(int position,ImageView[] imgs,TextView[] tvs);
        void putFragments(ImageView[] imgs,TextView[] tvs);

    }
}
