package sylu.com.doctorscheduling.main.muban;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.muban.Muban_MyArrayAdapter;
import sylu.com.doctorscheduling.internet.jdbc.SQLConnector;
import sylu.com.doctorscheduling.utils.NetUtils;
import sylu.com.doctorscheduling.utils.manager.UIManager;
import sylu.com.doctorscheduling.view.WarningDialog;

/**
 * Created by Hudsvi on 2017/3/17 9:20.
 */

public class Doctor_Details_Activity extends BaseActivity implements DetailsContract.View, View.OnClickListener {
    private static final int SELECT_ERRO = 0;
    private static final int SELECT_SUCCESS = 1;
    private static final int NEED_TO_ADD_INFO = 2;
    private static final int REQUEST_EDIT_CODE = 100;
    private static final int EDIT_OK = 101;
    @BindView(R.id.muban_details_non_ll)
    LinearLayout mubanDetailsNonLl;
    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;
    @BindView(R.id.muban_titlebar_left_tv)
    TextView mubanTitlebarLeftTv;
    @BindView(R.id.muban_titlebar_back)
    ImageView mubanTitlebarBack;
    @BindView(R.id.muban_details_days)//---------日期
            TextView mubanDetailsDays;
    @BindView(R.id.muban_details_day_of_week)//---------星期
            TextView mubanDetailsDayOfWeek;
    @BindView(R.id.muban_is_or_not_diagnose_am)//----------上午是否出诊
            TextView mubanIsOrNotDiagnoseAm;
    @BindView(R.id.muban_details_start_time_am)//------------上午开始时间
            TextView mubanDetailsStartTimeAm;
    @BindView(R.id.muban_details_end_time_am)//----------------上午结束时间
            TextView mubanDetailsEndTimeAm;
    @BindView(R.id.muban_details_amount_am)//---------------------上午放号量
            TextView mubanDetailsAmountAm;
    @BindView(R.id.muban_is_or_not_diagnose_pm)//------------下午
            TextView mubanIsOrNotDiagnosePm;
    @BindView(R.id.muban_details_start_time_pm)
    TextView mubanDetailsStartTimePm;
    @BindView(R.id.muban_details_end_time_pm)
    TextView mubanDetailsEndTimePm;
    @BindView(R.id.muban_details_amount_pm)//---------------下午
            TextView mubanDetailsAmountPm;
    @BindView(R.id.muban_doctoor_time_eachone)
    TextView mubanDoctoorTimeEachone;
    @BindView(R.id.muban_doctoor_time_eachone2)
    TextView mubanDoctoorTimeEachone2;
    @BindView(R.id.muban_details_scrollview)//--------------详情列表
            ScrollView mubanDetailsScrollview;
    @BindView(R.id.muban_details_layout)
    ConstraintLayout mubanDetailsLayout;
    @BindView(R.id.muban_detail_doctor_name)//---------------医生姓名
            TextView mubanDetailDoctorName;
    /*@BindView(R.id.muban_details_menu)
        FloatingActionButton mubanDetailsMenu;*/
    private Animation anim;
    private FloatingActionMenu menu;
    private FloatingActionButton fab;
    private SubActionButton delete_btn;
    private SubActionButton add_btn;
    private SubActionButton edit_btn;
    private String dept, d_name, date, week;//------------查询医生信息时必备的数据
    private Map<String, Object> doc_detail_map;
    private WarningDialog need_to_add_dialog;//------------医生信息
    private String[]  info;//-----------------更新前的数据
    private String[] doc_info;//------------------------更新后的数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SELECT_ERRO:
                    dissmissNetLoadingDialog();
                    toast("查询失败erro:"+msg.obj.toString());
                    break;
                case SELECT_SUCCESS:
                    dissmissNetLoadingDialog();
                    if (mubanDetailsScrollview.getVisibility() == View.GONE) {
                        mubanDetailsScrollview.setVisibility(View.VISIBLE);
                    }
                    if (mubanDetailsNonLl.getVisibility() == View.VISIBLE) {
                        mubanDetailsNonLl.setVisibility(View.GONE);
                    }
                    showDoctorInfo();
                    break;
                case NEED_TO_ADD_INFO:
                    dissmissNetLoadingDialog();
                    jumpToAdd();
                    break;
            }
        }
    };

    private void showDoctorInfo() {
        mubanDetailsDays.setText(date);//---------日期
        mubanDetailsDayOfWeek.setText(week);//----------星期
        mubanIsOrNotDiagnoseAm.setText(isDiagnosing(doc_detail_map.get("am").toString()));//上午
        mubanDetailsStartTimeAm.setText(booleanEmpty((String)doc_detail_map.get("am_start")));
        mubanDetailsEndTimeAm.setText(booleanEmpty((String)doc_detail_map.get("am_end")));
        mubanDetailsAmountAm.setText(booleanEmpty((String)doc_detail_map.get("am_count")));
        mubanIsOrNotDiagnosePm.setText(isDiagnosing(doc_detail_map.get("pm").toString()));//下午
        mubanDetailsStartTimePm.setText(booleanEmpty((String)doc_detail_map.get("pm_start")));
        mubanDetailsEndTimePm.setText(booleanEmpty((String)doc_detail_map.get("pm_end")));
        mubanDetailsAmountPm.setText(booleanEmpty((String)doc_detail_map.get("pm_count")));
        info=new String[]{doc_detail_map.get("am").toString(),booleanEmpty((String)doc_detail_map.get("am_start")),
                booleanEmpty((String)doc_detail_map.get("am_end")),booleanEmpty((String)doc_detail_map.get("am_count")),
                doc_detail_map.get("pm").toString(),booleanEmpty((String)doc_detail_map.get("pm_start")),
                booleanEmpty((String)doc_detail_map.get("pm_end")),booleanEmpty((String)doc_detail_map.get("pm_count"))};
/*

    @BindView(R.id.muban_is_or_not_diagnose_pm)//------------下午
    TextView mubanIsOrNotDiagnosePm;
    @BindView(R.id.muban_details_start_time_pm)
    TextView mubanDetailsStartTimePm;
    @BindView(R.id.muban_details_end_time_pm)
    TextView mubanDetailsEndTimePm;
    @BindView(R.id.muban_details_amount_pm)//---------------下午
   */
    }

    private void jumpToAdd() {
        need_to_add_dialog = new WarningDialog(this, this);
        need_to_add_dialog.setMessage("检测到您没有初始化当前的医生模板信息，您需要立即添加吗？");
        need_to_add_dialog.setBack("确定");
        need_to_add_dialog.setConfirm("稍后再添加");
        need_to_add_dialog.show();
        need_to_add_dialog.setCancelable(false);
    }

    @Override
    protected void InitView(Bundle savedInstanceState) {
        d_name = getIntent().getStringExtra(Constants.MUBAN_DOCTOR_LIST_NAME);
        dept = getIntent().getStringExtra(Constants.MUBAN_DEPT);
        week = getWeek(getIntent().getIntExtra(Constants.MUBAN_WEEK, 7));
        date = getIntent().getStringExtra(Constants.MUBAN_DOCTOR_LIST_DATE);
        mubanDetailDoctorName.setText(d_name);//----姓名
        initFAB();
        if (NetUtils.isNetworkAvailable(this)) {
            showNetLoadingDialog("查询详情中");
            checkDoctorInfo(dept, date, d_name);
        } else {
            toast("无法连接到服务器");
        }
    }

    private void checkDoctorInfo(String dept, String date, String d_name) {
        doc_detail_map = new HashMap<>();
        new Thread() {
            @Override
            public void run() {
                conn = SQLConnector.getInstance(getContext()).initSQL();
                try {
                    pre_sta = conn.prepareStatement("select isworking,am,am_start,am_end," +
                            "am_count,pm,pm_start,pm_end,pm_count from arrange where dept_no=?" +
                            "and date=? and doctor_name=?");
                    pre_sta.setString(1, dept);
                    pre_sta.setString(2, date);
                    pre_sta.setString(3, d_name);
                    rs = pre_sta.executeQuery();
                    while (rs.next()) {
                        doc_detail_map.put("isworking", rs.getString(1));
                        doc_detail_map.put("am", rs.getString(2));
                        doc_detail_map.put("am_start", rs.getString(3));
                        doc_detail_map.put("am_end", rs.getString(4));
                        doc_detail_map.put("am_count", rs.getString(5));
                        doc_detail_map.put("pm", rs.getString(6));
                        doc_detail_map.put("pm_start", rs.getString(7));
                        doc_detail_map.put("pm_end", rs.getString(8));
                        doc_detail_map.put("pm_count", rs.getString(9));
                    }
                    String amstart = (String) doc_detail_map.get("am_start");
                    if (doc_detail_map.get("am").equals("1") && TextUtils.isEmpty(amstart)) {
                        sendmessage(NEED_TO_ADD_INFO, null);
                    } else {
                        sendmessage(SELECT_SUCCESS, null);
                    }
                } catch (Exception e) {
                    sendmessage(SELECT_ERRO, e);
                } finally {
                    closeSQL();
                }
            }
        }.start();
    }

    private void sendmessage(int what, Object o) {
        Message m = new Message();
        m.what = what;
        m.obj = o;
        handler.sendMessage(m);
    }

    private void closeSQL() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFAB() {
        ImageView fab_img = new ImageView(this);
        fab_img.setImageDrawable(getResources().getDrawable(R.drawable.menu_more));
        int btn_width = (int) UIManager.dipToPixels(this, 56f);
        int btn_width2 = (int) UIManager.dipToPixels(this, 40f);
        int fab_width = (int) UIManager.dipToPixels(this, 20f);
        int fab_width2 = (int) UIManager.dipToPixels(this, 16f);
        int btn_margin = (int) UIManager.dipToPixels(this, 16f);
        FloatingActionButton.LayoutParams bg_fab_lp = new FloatingActionButton
                .LayoutParams(btn_width, btn_width);
        FloatingActionButton.LayoutParams bg_fab_lp2 = new FloatingActionButton
                .LayoutParams(btn_width2, btn_width2);
        bg_fab_lp.bottomMargin = btn_margin;
        bg_fab_lp.rightMargin = btn_margin;
        FloatingActionButton.LayoutParams fab_lp = new FloatingActionButton
                .LayoutParams(fab_width, fab_width);
        FloatingActionButton.LayoutParams fab_lp2 = new FloatingActionButton
                .LayoutParams(fab_width2, fab_width2);
        fab_lp2.gravity = Gravity.CENTER;
        fab = new FloatingActionButton.Builder(this)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_muban_fab))
                .setLayoutParams(bg_fab_lp)
                .setContentView(fab_img, fab_lp)
                .build();

        SubActionButton.Builder builder1 = new SubActionButton.Builder(this);
        ImageView image_del = new ImageView(this);
        image_del.setImageDrawable(getResources().getDrawable(R.drawable.menu_delete));
        delete_btn = builder1
                .setContentView(image_del, fab_lp2)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_muban_sub_btn_del))
                .setLayoutParams(bg_fab_lp2)
                .build();

        SubActionButton.Builder builder2 = new SubActionButton.Builder(this);
        ImageView image_add = new ImageView(this);
        image_add.setImageDrawable(getResources().getDrawable(R.drawable.refresh));
        add_btn = builder2.setContentView(image_add, fab_lp2)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_muban_sub_btn_add))
                .setLayoutParams(bg_fab_lp2)
                .build();

        SubActionButton.Builder builder3 = new SubActionButton.Builder(this);
        ImageView image_edit = new ImageView(this);
        image_edit.setImageDrawable(getResources().getDrawable(R.drawable.menu_edit));
        edit_btn = builder3.setContentView(image_edit, fab_lp2)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_muban_sub_btn_edit))
                .setLayoutParams(bg_fab_lp2)
                .build();

        //btn_menu
        int radius = (int) UIManager.dipToPixels(this, 120f);
        menu = new FloatingActionMenu.Builder(this)
                .addSubActionView(delete_btn)
                .addSubActionView(add_btn)
                .addSubActionView(edit_btn)
                .setRadius(radius)
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
                mubanDetailsLayout.setAlpha(0.9f);
                mubanDetailsLayout.setBackgroundColor(getResources().getColor(R.color.black));
                startAnim();

            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                mubanDetailsLayout.setAlpha(1.0f);
                mubanDetailsLayout.setBackgroundColor(getResources().getColor(R.color.white));
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //立即编辑
            case R.id.warning_dialog_back:
                if (need_to_add_dialog != null && need_to_add_dialog.isShowing()) {
                    need_to_add_dialog.dismiss();
                }
                goToEditMunban();
                ////
                break;
            //稍后编辑
            case R.id.warning_dialog_confirm:
                if (need_to_add_dialog != null && need_to_add_dialog.isShowing()) {
                    need_to_add_dialog.dismiss();
                }
                break;
            //add
            case 0x1233991:
//                Toast.makeText(this, "add_clicked", Toast.LENGTH_SHORT).show();

                break;
            //delete
            case 0x1233992:
                break;
            //edit
            case 0x1233993:
                if(NetUtils.isNetworkAvailable(this)) {
                    if (menu.isOpen()) {
                        menu.close(false);
                    }
                    goToEditMunban();

                }else{toast("无法连接到服务器");}
                break;
        }
    }

    private void goToEditMunban() {
        Intent intent=new Intent(Doctor_Details_Activity.this,DoctorDetails_Update_Activity.class);
        intent.putExtra(Constants.MUBAN_DOCTOR_LIST_DATE,date);
        intent.putExtra(Constants.MUBAN_WEEK,week);
        intent.putExtra(Constants.MUBAN_DETAILS_EDIT,info);
        startActivityForResult(intent,REQUEST_EDIT_CODE);
    }

    @OnClick(R.id.muban_titlebar_back)
    public void onClick() {
        finish();
    }

    @Override
    public void setDetails(Muban_MyArrayAdapter.ViewHolder holder) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {

    }

    @Override
    public void onBackPressed() {
        if (menu.isOpen()) {
            menu.close(true);
        } else {
            super.onBackPressed();
        }

    }

    public String getWeek(int p) {
        if (p == 0) {
            return "本周日";
        } else if (p == 1) {
            return "下周一";
        } else if (p == 2) {
            return "下周二";
        } else if (p == 3) {
            return "下周三";
        } else if (p == 4) {
            return "下周四";
        } else if (p == 5) {
            return "下周五";
        } else if (p == 6) {
            return "下周六";
        } else {
            return "----";
        }
    }

    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private String isDiagnosing(String s) {
        if (s.equals("1")) {
            return "是";
        } else if (s.equals("0")) {
            return "否";
        } else if (s.equals("是")) {
            return "1";
        } else if (s.equals("否")) {
            return "0";
        } else {
            return "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_EDIT_CODE&&resultCode==EDIT_OK){
            if(data!=null) {
                if (NetUtils.isNetworkAvailable(this)) {
                    doc_info = data.getStringArrayExtra(Constants.MUBAN_DOCTOR_DETAILS);
                    doUpdate(doc_info);
                }else{toast("无法连接到服务器");}
            }
        }
    }

    private void doUpdate(String[] doc_info) {
        mubanIsOrNotDiagnoseAm.setText(doc_info[0]);//上午
        mubanDetailsStartTimeAm.setText(doc_info[1]);
        mubanDetailsEndTimeAm.setText(doc_info[2]);
        mubanDetailsAmountAm.setText(doc_info[3]);
        mubanIsOrNotDiagnoseAm.setText(doc_info[4]);//下午
        mubanDetailsStartTimeAm.setText(doc_info[5]);
        mubanDetailsEndTimeAm.setText(doc_info[6]);
        mubanDetailsAmountAm.setText(doc_info[7]);
    }
    private String booleanEmpty(String s){
        if(TextUtils.isEmpty(s)){return "";}
        else{return s;}
    }
}
