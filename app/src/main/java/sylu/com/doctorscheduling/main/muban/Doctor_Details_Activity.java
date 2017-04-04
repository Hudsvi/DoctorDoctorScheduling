package sylu.com.doctorscheduling.main.muban;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;

/**
 * Created by Hudsvi on 2017/3/17 9:20.
 */

public class Doctor_Details_Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.main_titlebar_textview)
    TextView mainTitlebarTextview;
    @BindView(R.id.main_titlebar_user)
    ImageView mainTitlebarUser;
    @BindView(R.id.main_titlebar_menu)
    ImageView mainTitlebarMenu;
    @BindView(R.id.muban_doctor_details_recyclerview)
    RecyclerView mubanDoctorDetailsRecyclerview;
    @BindView(R.id.muban_details_layout)
    ConstraintLayout mubanDetailsLayout;
    @BindView(R.id.muban_details_update_logo)
    ImageView update_imageView;

    /*@BindView(R.id.muban_details_menu)
    FloatingActionButton mubanDetailsMenu;*/
    private Animation anim;
    private FloatingActionMenu menu;
    private FloatingActionButton fab;
    private SubActionButton delete_btn;
    private SubActionButton add_btn;
    private SubActionButton edit_btn;
    @Override
    protected void InitView(Bundle savedInstanceState) {
        mainTitlebarTextview.setText(getIntent().getStringExtra(Constants.MUBAN_DOCTOR_LIST_NAME));
        mainTitlebarUser.setVisibility(View.VISIBLE);
        mainTitlebarUser.setImageDrawable(getResources().getDrawable(R.drawable.goback));
        initFAB();
    }

    private void initFAB() {
        ImageView fab_img = new ImageView(this);
        fab_img.setImageDrawable(getResources().getDrawable(R.drawable.feather));
        FloatingActionButton.LayoutParams bg_fab_lp = new FloatingActionButton
                .LayoutParams(160, 160);
        bg_fab_lp.bottomMargin = 42;
        bg_fab_lp.rightMargin = 42;
        FloatingActionButton.LayoutParams fab_lp = new FloatingActionButton
                .LayoutParams(80, 80);
        fab = new FloatingActionButton.Builder(this)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_muban_fab))
                .setLayoutParams(bg_fab_lp)
                .setContentView(fab_img, fab_lp)
                .build();

        SubActionButton.Builder builder1 = new SubActionButton.Builder(this);
        ImageView image_del = new ImageView(this);
        image_del.setImageDrawable(getResources().getDrawable(R.drawable.menu_delete1));
        delete_btn = builder1.setContentView(image_del)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent))
                .build();

        SubActionButton.Builder builder2 = new SubActionButton.Builder(this);
        ImageView image_add = new ImageView(this);
        image_add.setImageDrawable(getResources().getDrawable(R.drawable.menu_doc_add1));
        add_btn = builder2.setContentView(image_add)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent))
                .build();

        SubActionButton.Builder builder3 = new SubActionButton.Builder(this);
        ImageView image_edit = new ImageView(this);
        image_edit.setImageDrawable(getResources().getDrawable(R.drawable.menu_edit1));
        edit_btn = builder3.setContentView(image_edit)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent))
                .build();

        //btn_menu
        menu = new FloatingActionMenu.Builder(this)
                .addSubActionView(delete_btn, 120, 120)
                .addSubActionView(add_btn, 120, 120)
                .addSubActionView(edit_btn, 120, 120)
                .setRadius(320)
                .setStartAngle(180)
                .setEndAngle(270)
                .attachTo(fab)
                .build();
        add_btn.setId(0x1233991);
        add_btn.setOnClickListener(this);
        delete_btn.setId(0x1233992);
        delete_btn.setOnClickListener(this);
        edit_btn.setId(0x1233993);
        edit_btn.setOnClickListener(this);
        menu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                mubanDetailsLayout.setAlpha(0.7f);
                startAnim();
                update_imageView.clearAnimation();
                update_imageView.setVisibility(View.GONE);

            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                mubanDetailsLayout.setAlpha(1.0f);
                fab.clearAnimation();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (menu.isOpen()) {
            menu.close(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void startAnim() {
        anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.muban_menu_open);
        LinearInterpolator lin = new LinearInterpolator();
        anim.setInterpolator(lin);
        anim.setFillAfter(true);
        fab.startAnimation(anim);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //add
            case 0x1233991:
//                Toast.makeText(this, "add_clicked", Toast.LENGTH_SHORT).show();

                break;
            //delete
            case 0x1233992:
                Animation anim=AnimationUtils.loadAnimation(this,R.anim.muban_details_update);
                AccelerateInterpolator acce=new AccelerateInterpolator(1.0f);
                anim.setInterpolator(acce);
                update_imageView.setVisibility(View.VISIBLE);
                update_imageView.startAnimation(anim);
//                Toast.makeText(this, "add_clicked", Toast.LENGTH_SHORT).show();

                break;
            //edit
            case 0x1233993:
//                Toast.makeText(this, "add_clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
