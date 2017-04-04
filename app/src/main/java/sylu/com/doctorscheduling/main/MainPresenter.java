package sylu.com.doctorscheduling.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.IconFocused;
import sylu.com.doctorscheduling.constants.IconNotfocused;
import sylu.com.doctorscheduling.fragment.fuhe.Fuhe_Fragment;
import sylu.com.doctorscheduling.fragment.muban.Muban_Fragment;
import sylu.com.doctorscheduling.fragment.paiban.Paiban_Fragment;
import sylu.com.doctorscheduling.fragment.personal.Personal_Fragment;
import sylu.com.doctorscheduling.fragment.yuyue.Yuyue_Fragment;

/**
 * Created by Hudsvi on 2017/3/30 11:55.
 */

public class MainPresenter implements MainContract.Presenter {
    private ArrayList<android.support.v4.app.Fragment> mfrags;
    private MainContract.View view;
    public MainPresenter(MainContract.View view) {
        this.view=view;
        view.setPresenter(this);
    }


    @Override
    public void setTabIcon(int position, ImageView[] imgs,TextView[] tvs) {
        for (int i = 0; i < imgs.length; i++) {
            if (i == position) {
                imgs[i].setImageDrawable(view.getContext().getResources().getDrawable(IconFocused.getIcon(i)));
                tvs[i].setTextColor(view.getContext().getResources().getColor(R.color.blue_tab_bottom));
            } else {
                Drawable d=view.getContext().getResources().getDrawable(IconNotfocused.getIcon(i));
                d.setColorFilter(R.color.gray_classic, PorterDuff.Mode.SRC_IN);
                imgs[i].setImageDrawable(d);
                tvs[i].setTextColor(view.getContext().getResources().getColor(R.color.gray_dark));
            }
        }
    }

    @Override
    public void putFragments(ImageView[] imgs, TextView[] tvs) {
        mfrags = new ArrayList<>();
        Yuyue_Fragment yuyue = new Yuyue_Fragment();
        Muban_Fragment muban = new Muban_Fragment();
        Personal_Fragment person = new Personal_Fragment();
        Fuhe_Fragment fuhe = new Fuhe_Fragment();
        Paiban_Fragment paiban = new Paiban_Fragment();
        mfrags.add(yuyue);
        mfrags.add(muban);
        mfrags.add(paiban);
        mfrags.add(fuhe);
        mfrags.add(person);
        view.setAdapter(mfrags,imgs,tvs);
    }


}
