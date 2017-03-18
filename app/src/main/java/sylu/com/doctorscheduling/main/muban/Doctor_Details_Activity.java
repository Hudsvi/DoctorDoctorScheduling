package sylu.com.doctorscheduling.main.muban;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;

/**
 * Created by Hudsvi on 2017/3/17 9:20.
 */

public class Doctor_Details_Activity extends BaseActivity {
    @BindView(R.id.main_titlebar_textview)
    TextView mainTitlebarTextview;
    @BindView(R.id.main_titlebar_user)
    ImageView mainTitlebarUser;
    @BindView(R.id.main_titlebar_menu)
    ImageView mainTitlebarMenu;
    @BindView(R.id.muban_doctor_details_recyclerview)
    RecyclerView mubanDoctorDetailsRecyclerview;
    @BindView(R.id.muban_details_menu)
    FloatingActionButton mubanDetailsMenu;
    private Animation anim;
private FloatingActionMenu menu;
    @Override
    protected void InitView(Bundle savedInstanceState) {
        mainTitlebarTextview.setText(getIntent().getStringExtra(Constants.MUBAN_DOCTOR_LIST_NAME));
        mainTitlebarUser.setVisibility(View.VISIBLE);
        mainTitlebarUser.setImageDrawable(getResources().getDrawable(R.drawable.goback));
        initFAB();
    }

    private void initFAB() {
        SubActionButton.Builder builder1=new SubActionButton.Builder(this);
        ImageView image_del=new ImageView(this);
        image_del.setImageDrawable(getResources().getDrawable(R.drawable.menu_delete1));
        SubActionButton delete_btn=builder1.setContentView(image_del)
                .build();

        SubActionButton.Builder builder2=new SubActionButton.Builder(this);
        ImageView image_add=new ImageView(this);
        image_add.setImageDrawable(getResources().getDrawable(R.drawable.menu_more1));
        SubActionButton add_btn=builder2.setContentView(image_add).setTheme(R.style.muban_sub_btn)
                .build();

        SubActionButton.Builder builder3=new SubActionButton.Builder(this);
        ImageView image_edit=new ImageView(this);
        image_edit.setImageDrawable(getResources().getDrawable(R.drawable.menu_edit1));
        SubActionButton edit_btn=builder3.setContentView(image_edit).build();

        //btn_menu
        menu= new FloatingActionMenu.Builder(this)
                .addSubActionView(delete_btn,120,120)
                .addSubActionView(add_btn,120,120)
                .addSubActionView(edit_btn,120,120)
                .setRadius(320)
                .setStartAngle(180)
                .setEndAngle(270)
                .attachTo(mubanDetailsMenu)
                .build();
        menu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                startAnim();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                mubanDetailsMenu.clearAnimation();
            }
        });
    }

    private void startAnim() {
        anim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.muban_menu_open);
        LinearInterpolator lin=new LinearInterpolator();
        anim.setInterpolator(lin);
        anim.setFillAfter(true);
        mubanDetailsMenu.startAnimation(anim);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.muban_doctor_details;
    }



    @Override
    protected void onResume() {
        super.onResume();
        showNetLoadingDialog("努力加載中");
    }


    @OnClick(R.id.muban_details_menu)
    public void onFloatingMenuClicked() {

    }
}
