package sylu.com.doctorscheduling.main.muban;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.utils.DateTimeFormatUtils;
import sylu.com.doctorscheduling.view.WarningDialog;
import sylu.com.doctorscheduling.view.datetimepicker.MyDateTimePicker;

/**
 * Created by Hudsvi on 2017/4/10 23:16.
 */

public class DoctorDetails_Update_Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.muban_details_update_ok_img)
    ImageView mubanDetailsUpdateOkImg;
    @BindView(R.id.muban_detail_update_title)
    TextView mubanDetailUpdateTitle;
    @BindView(R.id.muban_update_titlebar_back)
    ImageView mubanUpdateTitlebarBack;
    @BindView(R.id.muban_update_details_days)
    TextView mubanUpdateDetailsDays;
    @BindView(R.id.muban_update_details_day_of_week)
    TextView mubanUpdateDetailsDayOfWeek;
    @BindView(R.id.muban_update_is_or_not_diagnose_am)
    Spinner diagnoseAm;
    @BindView(R.id.muban_update_start_time_am)
    TextView startAm;
    @BindView(R.id.muban_update_end_time_am)
    TextView endAm;
    @BindView(R.id.muban_update_amount_am)
    EditText countAm;
    @BindView(R.id.muban_update_is_or_not_diagnose_pm)
    Spinner diagnosePm;
    @BindView(R.id.muban_update_details_update_start_time_pm)
    TextView startPm;
    @BindView(R.id.muban_update_details_update_end_time_pm)
    TextView endPm;
    @BindView(R.id.muban_update_details_update_amount_pm)
    EditText countPm;
    private String date, week;
    private WarningDialog warn;
    private String diagnose_am, diagnose_pm;
    private String[] d_info = new String[8];
    private int type = 1;//---------判断返回图标还是返回键，默认返回键
    private int erroAt = 0;//-------------判断内容的是否正确，上午有误为1，下午有误为2，均有误为3，否则为0
    private boolean erro2 = false;
    private boolean erro1 = false;
    private static int time_signal=0;//----判断选择的是哪个时间段
private MyDateTimePicker datepicker;
    private String date1;

    @Override
    protected void InitView(Bundle savedInstanceState) {
        warn = new WarningDialog(DoctorDetails_Update_Activity.this, this);
        date = getIntent().getStringExtra(Constants.MUBAN_DOCTOR_LIST_DATE);
        week = getIntent().getStringExtra(Constants.MUBAN_WEEK);
        mubanUpdateDetailsDays.setText(date);
        mubanUpdateDetailsDayOfWeek.setText(week);
        mubanDetailUpdateTitle.setText("模板编辑");
    }

    @Override
    protected int getLayoutView() {
        return R.layout.muban_details_update;
    }


    @OnClick({R.id.muban_update_start_time_am,R.id.muban_update_end_time_am,
            R.id.muban_update_details_update_start_time_pm, R.id.muban_update_details_update_end_time_pm,
            R.id.muban_details_update_ok_img, R.id.muban_update_titlebar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.muban_update_start_time_am:
                time_signal=1;
                selectTime();
                break;
            case R.id.muban_update_end_time_am:
                time_signal=2;
                selectTime();
                break;
            case R.id.muban_update_details_update_start_time_pm:
                time_signal=3;
                selectTime();
                break;
            case R.id.muban_update_details_update_end_time_pm:
                time_signal=4;
                selectTime();
                break;
            case R.id.muban_details_update_ok_img:
                getContent();
                checkContent();
                if (erroAt==0){
                    Intent rs_intent=new Intent();
                    rs_intent.putExtra(Constants.MUBAN_DOCTOR_DETAILS,d_info);
                    setResult(101,rs_intent);
                    finish();
                }
                else if(erroAt==1){
                    toast("上午信息格式不正确");
                }
                else if(erroAt==2){
                    toast("下午信息格式不正确");
                }
                else if(erroAt==3){
                    toast("上、下午的信息格式均不正确");
                }
                    break;
            case R.id.muban_update_titlebar_back:
                type=0;
                onBackPressed();
                break;
            case R.id.warning_dialog_back:
                if (warn != null && warn.isShowing()) {
                    warn.dismiss();
                    type=1;
                }
                break;
            case R.id.warning_dialog_confirm:
                if (warn != null && warn.isShowing()) {
                    warn.dismiss();
                    super.onBackPressed();
                }

        }
    }

    private void selectTime() {
        date1 = DateTimeFormatUtils.DATE_TIME.format(new Date());
        datepicker=new MyDateTimePicker(this, new MyDateTimePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                String[] t=time.split(" ");
                if(time_signal==1){startAm.setText(t[1]);}
                if(time_signal==2){endAm.setText(t[1]);}
                if(time_signal==3){startPm.setText(t[1]);}
                if(time_signal==4){endPm.setText(t[1]);}
            }
        },"2017-4-1 00:00","2025-12-29 23:59");
        datepicker.setIsLoop(true);
        datepicker.showSpecificTime(true);
        datepicker.show(date1);
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void getContent() {
        d_info[0] = diagnoseAm.getSelectedItem().toString();
        d_info[1] = startAm.getText().toString();
        d_info[2] = endAm.getText().toString();
        d_info[3] = countAm.getText().toString();
        d_info[4] = diagnosePm.getSelectedItem().toString();
        d_info[5] = startPm.getText().toString();
        d_info[6] = endPm.getText().toString();
        d_info[7] = countPm.getText().toString();
    }

    private void checkContent() {
        if (d_info[0].equals("是") &&
                (TextUtils.isEmpty(d_info[1]) || TextUtils.isEmpty(d_info[2]) ||
                        TextUtils.isEmpty(d_info[3]))) {
            erro1 = true;
            erroAt = 1;
        }
        else if(d_info[0].equals("是")&&d_info[3]!=null&&d_info[3].equals("0")){
            erro1 = true;
            erroAt = 1;
        }
        if (d_info[4].equals("是") && (TextUtils.isEmpty(d_info[5]) || TextUtils.isEmpty(d_info[6]) ||
                TextUtils.isEmpty(d_info[7]))) {
            erro2 = true;
            erroAt = 2;
        } else if(d_info[4].equals("是")&&d_info[7]!=null&&d_info[7].equals("0")){
            erro2 = true;
            erroAt = 2;
        }
        if (erro1 && erro2) {
            erroAt = 3;
        }
    }

    @Override
    public void onBackPressed() {
        booleanExit();
    }

    private void booleanExit() {
        if (type == 0 && warn != null && !warn.isShowing()) {
            warn.setMessage("您要放弃本次的修改吗？");
            warn.show();
        }else if(type==0&& warn != null && warn.isShowing()){
            warn.dismiss();
            type=1;
        }
        if (type == 1 && warn != null && !warn.isShowing()) {
            warn.setMessage("您要放弃本次的修改吗？");
            warn.show();
            warn.setCancelable(false);
        }else if(type==1&& warn != null && warn.isShowing()){
            warn.dismiss();
        }
    }


}
