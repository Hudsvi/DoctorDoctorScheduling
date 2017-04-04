package sylu.com.doctorscheduling.fragment.yuyue;

import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import sylu.com.doctorscheduling.BaseFragment;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.MySharedPreferences;
import sylu.com.doctorscheduling.internet.jdbc.SQLConnector;
import sylu.com.doctorscheduling.main.yuyue.YuyueContact;
import sylu.com.doctorscheduling.main.yuyue.YuyuePresenter;
import sylu.com.doctorscheduling.utils.DateTimeFormatUtils;
import sylu.com.doctorscheduling.utils.NetUtils;
import sylu.com.doctorscheduling.utils.manager.PhoneManager;
import sylu.com.doctorscheduling.utils.manager.UIManager;
import sylu.com.doctorscheduling.view.MenuDialog;
import sylu.com.doctorscheduling.view.WarningDialog;
import sylu.com.doctorscheduling.view.datetimepicker.MyDateTimePicker;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Yuyue_Fragment extends BaseFragment implements YuyueContact.View, View.OnClickListener {


    @BindView(R.id.yuyue_spinner)//------------------------更换科室
            Spinner yuyueSpinner;
    @BindView(R.id.yuyue_view1)
    View yuyueView1;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.yuyue_date_choosing)
    TextView yuyueDateChoosing;//----------------查询的日期
    @BindView(R.id.yuyue_view2)
    View yuyueView2;
    @BindView(R.id.yuyue_add)//--------------增加
            RadioButton yuyueAdd;
    @BindView(R.id.yuyue_update)//---------------编辑
            RadioButton yuyueUpdate;
    @BindView(R.id.yuyue_delete)//-------------删除
            RadioButton yuyueDelete;
    @BindView(R.id.yuyue_bar)//-----------------单选框
            RadioGroup yuyueBar;
    @BindView(R.id.yuyue_view3)
    View yuyueView3;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.yuyue_textviwe3)
    TextView yuyueTextviwe3;
    @BindView(R.id.yuyue_textviwe4)
    TextView yuyueTextviwe4;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.yuyue_start_time)//---------开始时间
            TextView yuyueStartTime;
    @BindView(R.id.yuyue_end_time)//-------结束时间
            TextView yuyueEndTime;
    @BindView(R.id.yuyue_allowing_days)//--------允许预约天数
            EditText yuyueAllowingDays;
    @BindView(R.id.yuyue_previous_days)//------可提前预约天数
            EditText yuyuePreviousDays;
    @BindView(R.id.yuyue_default_times)//-------最大违约次数
            EditText yuyueDefaultTimes;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.yuyue_bt1)//------------提交修改
            Button yuyueBt1;
    @BindView(R.id.yuyue_bt2)//------------删除
            Button yuyueBt2;
    @BindView(R.id.yuyue_bt3)//---------------清空
            Button yuyueBt3;
    @BindView(R.id.yuyue_operator_hint)//---------------提示
            TextView yuyueOperatorHint;
    @BindView(R.id.yuyue_date_range)
    TextView yuyueDateRange;
    @BindView(R.id.yuyue_date_range1)//-----------有效期1
            TextView yuyueDateRange1;
    @BindView(R.id.yuyue_textView3)
    TextView yuyueTextView3;
    @BindView(R.id.yuyue_date_range2)//---------有效期2
            TextView yuyueDateRange2;
    @BindView(R.id.yuyue_info)
    ConstraintLayout yuyueInfo;
    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;
    private ImageView menu;//-------------------菜单
    private MenuDialog dialog;//-------------菜单View
    private String deptno, queryDate;//----------科室号，查询预约信息
    private String selected_dept;//------选中的科室
    private String date1;
    private String validateDate1;//-----有效期1
    private String validateDate2;//--------------有效期2
    private String startTime1;
    private String startTime2;
    private String allowingdays;
    private String previousdays;
    private String defaulttimes;//最大违约次数
    private WarningDialog dele;//----------------------删除预约信息提示框
    private List<String> dept_ls;//----------------科室立列表
    private Map<String, Object> reser_map;//--------------预约信息
    private MyDateTimePicker picker;
    private static final int ERRO = 0;//-----------错误处理
    private static final int DEPT_SUCCESS = 1;//-----------科室获取成功
    private static final int RESER_SUCCESS = 2;//-----------预约信息获取成功
    private static final int ADD_SUCCESS = 3;//-----------预约信息新增成功
    private static final int ADD_ERRO =4 ;//-----------------预约信息新增失败
    private static final int UPDATE_SUCCESS = 5;//-----------预约信息修改成功
    private static final int UPDATE_ERRO =6 ;//-----------------预约信息修改失败
    private static final int DELETE_SUCCESS = 7;//-----------预约信息删除成功
    private static final int DELETE_ERRO =8;//-----------------预约信息删除失败

    private YuyueContact.Presenter presenter;
    private Handler han = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERRO:
                    new AlertDialog.Builder(getContext()).setMessage("获取数据失败！请检查您的网络连接")
                            .setTitle("提示")
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                    break;
                case DEPT_SUCCESS:
                    setDepart(dept_ls);
                    break;
                case RESER_SUCCESS://--------查询预约信息成功
                    showReserInfo(reser_map);
                    break;
                case ADD_SUCCESS://----------添加成功
                    dissmissNetLoadingDialog();
                    toast("添加成功");
                    break;
                case ADD_ERRO://-----------添加失败
                    dissmissNetLoadingDialog();
                    toast("添加失败");
                    break;
                case UPDATE_SUCCESS:
                    dissmissNetLoadingDialog();
                    toast("修改成功");
                    break;
                case UPDATE_ERRO:
                    dissmissNetLoadingDialog();
                    toast("修改失败");
                    break;
                case DELETE_SUCCESS:
                    dissmissNetLoadingDialog();
                    toast("删除成功");
                    break;
                case DELETE_ERRO:
                    dissmissNetLoadingDialog();
                    toast("删除失败");
                    break;
            }
        }
    };

    private void showReserInfo(Map<String, Object> map) {
        if (map.get("end") != null) {
            yuyueDateRange2.setText(map.get("val_day2").toString());
            yuyueStartTime.setText(map.get("start").toString());
            yuyueDateRange1.setText(map.get("val_day1").toString());
            yuyueEndTime.setText(map.get("end").toString());
            yuyuePreviousDays.setText(map.get("pre_day").toString());
            yuyueAllowingDays.setText(map.get("allow_day").toString());
            yuyueDefaultTimes.setText(map.get("def_times").toString());
            dissmissNetLoadingDialog();
        } else {
            toast("查询结果不存在，请手动添加信息");
            dissmissNetLoadingDialog();
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.yuyue_details;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new YuyuePresenter(this);
        initList();
        ennableEdit(false);
        menu = (ImageView) getActivity().findViewById(R.id.main_titlebar_menu);
        menu.setOnClickListener(this);
        if (NetUtils.isNetworkAvailable(getContext())) {
            showNetLoadingDialog("加载中");
            getDepart();
        } else {
            dissmissNetLoadingDialog();
            toast("无法连接到服务器");
        }
    }


    private void initList() {
//        dept_ls = new ArrayList<>();
    }

    private void ennableEdit(boolean b) {
        yuyueAllowingDays.setEnabled(b);
        yuyueDateRange1.setEnabled(b);
        yuyueDateRange2.setEnabled(b);
        yuyueStartTime.setEnabled(b);
        yuyueEndTime.setEnabled(b);
        yuyuePreviousDays.setEnabled(b);
        yuyueDefaultTimes.setEnabled(b);
    }


    @OnClick({R.id.yuyue_bt1, R.id.yuyue_bt2, R.id.yuyue_bt3, R.id.yuyue_date_choosing,
            R.id.yuyue_add, R.id.yuyue_update, R.id.yuyue_delete, R.id.yuyue_start_time,
            R.id.yuyue_end_time, R.id.yuyue_allowing_days, R.id.yuyue_previous_days,
            R.id.yuyue_default_times, R.id.yuyue_operator_hint, R.id.yuyue_date_range,
            R.id.yuyue_date_range1, R.id.yuyue_date_range2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_titlebar_menu://-------------菜单
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                } else if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                } else if (dialog == null) {
                    dialog = new MenuDialog(getContext(), this);
                    dialog.setCanceledOnTouchOutside(true);/////
                    dialog.setItemIMGandTV1(R.drawable.fuhe, "打开设置");
                    dialog.setItemIMGandTV2(R.drawable.fuhe, "隐藏设置");
                    dialog.show();
                }
                break;
            case R.id.menu_main_item1_ll://打开预约设置
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (yuyueBar.getVisibility() == View.GONE) {
                    yuyueBar.setVisibility(View.VISIBLE);
                    yuyueAdd.setChecked(true);
                    if (yuyueBt1.getVisibility() == View.GONE)
                        yuyueBt1.setVisibility(View.VISIBLE);
                    yuyueBt1.setText("新增");
                    if (yuyueBt2.getVisibility()==View.VISIBLE)
                        yuyueBt2.setVisibility(View.GONE);
                    if (yuyueBt3.getVisibility() == View.GONE)
                        yuyueBt3.setVisibility(View.VISIBLE);
                    if (yuyueOperatorHint.getVisibility() == View.GONE)
                        yuyueOperatorHint.setVisibility(View.VISIBLE);/////
                    ennableEdit(true);
                }
                break;
            case R.id.menu_main_item2_ll://隐藏预约设置
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (yuyueBar.getVisibility() == View.VISIBLE) {
                    yuyueBar.setVisibility(View.GONE);
                    if (yuyueBt1.getVisibility() == View.VISIBLE)
                        yuyueBt1.setVisibility(View.GONE);
                    if(yuyueBt2.getVisibility()==View.VISIBLE)
                        yuyueBt2.setVisibility(View.GONE);
                        if (yuyueBt3.getVisibility() == View.VISIBLE)
                        yuyueBt3.setVisibility(View.GONE);
                    if (yuyueOperatorHint.getVisibility() == View.VISIBLE)
                        yuyueOperatorHint.setVisibility(View.GONE);/////
                    if(reser_map!=null) {
                        yuyueDateRange2.setText(reser_map.get("val_day2").toString());
                        yuyueStartTime.setText(reser_map.get("start").toString());
                        yuyueDateRange1.setText(reser_map.get("val_day1").toString());
                        yuyueEndTime.setText(reser_map.get("end").toString());
                        yuyuePreviousDays.setText(reser_map.get("pre_day").toString());
                        yuyueAllowingDays.setText(reser_map.get("allow_day").toString());
                        yuyueDefaultTimes.setText(reser_map.get("def_times").toString());
                    }
                    ennableEdit(false);
                }
                if (yuyueBar.getVisibility() == View.VISIBLE) {
                    yuyueBar.setVisibility(View.GONE);
                }
                break;
            case R.id.main_menu_back_linearlayout://隐藏对话框,返回按钮
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
            case R.id.yuyue_date_choosing:
//                String date1 = DateTimeFormatUtils.getDatetimeInstance()
//                        .getMonthOfYear(String.valueOf(new Date().getTime()));
                date1 = DateTimeFormatUtils.DATE_TIME.format(new Date());
                picker = new MyDateTimePicker(mContext, new MyDateTimePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        String[] date = time.split(" ");
                        yuyueDateChoosing.setText(date[0]);
                        showNetLoadingDialog("查询中");
                        //查询
                        if (NetUtils.isNetworkAvailable(getContext())) {
                            if (selected_dept != null && !selected_dept.equals("")) {
                                selectInfo(date[0], selected_dept);
                            } else {
                                dissmissNetLoadingDialog();
                                toast("查询失败");
                            }
                        } else {
                            dissmissNetLoadingDialog();
                            toast("无法连接到服务器");
                        }
                    }
                }, "2017-4-2 00:00", "2030-12-29 23:59");
                picker.setIsLoop(true);
                picker.showSpecificTime(false);
                picker.show(date1);
                break;
            case R.id.yuyue_add://------预约设置的增加
                if (yuyueBt2.getVisibility() == View.VISIBLE)
                    yuyueBt2.setVisibility(View.GONE);
                if (yuyueBt1.getVisibility() == View.GONE)
                    yuyueBt1.setVisibility(View.VISIBLE);
                yuyueBt1.setText("新增");
                if (yuyueBt3.getVisibility() == View.GONE)
                    yuyueBt3.setVisibility(View.VISIBLE);
                if (yuyueOperatorHint.getVisibility() == View.GONE)
                    yuyueOperatorHint.setVisibility(View.VISIBLE);/////
                break;
            case R.id.yuyue_update://------预约设置的编辑
                if (yuyueBt2.getVisibility() == View.VISIBLE)
                    yuyueBt2.setVisibility(View.GONE);
                if (yuyueBt1.getVisibility() == View.GONE)
                    yuyueBt1.setVisibility(View.VISIBLE);
                yuyueBt1.setText("提交修改");
                if (yuyueBt3.getVisibility() == View.GONE)
                    yuyueBt3.setVisibility(View.VISIBLE);
                if (yuyueOperatorHint.getVisibility() == View.GONE)
                    yuyueOperatorHint.setVisibility(View.VISIBLE);/////
                break;
            case R.id.yuyue_delete://------预约设置的删除
                if (yuyueBt2.getVisibility() == View.GONE)
                    yuyueBt2.setVisibility(View.VISIBLE);
                if (yuyueBt1.getVisibility() == View.VISIBLE)
                    yuyueBt1.setVisibility(View.GONE);
                if (yuyueBt3.getVisibility() == View.VISIBLE)
                    yuyueBt3.setVisibility(View.GONE);
                if (yuyueOperatorHint.getVisibility() == View.VISIBLE)
                    yuyueOperatorHint.setVisibility(View.GONE);/////
                break;
            case R.id.warning_dialog_back://-------------dele----warning---back
                if (dele != null && dele.isShowing())
                    dele.dismiss();
                break;
            case R.id.warning_dialog_confirm:
                if(yuyueDelete.isChecked()) {
                    showNetLoadingDialog("正在努力删除");
                    if (NetUtils.isNetworkAvailable(getContext())) {
                        doDeleteReservationInfo();//---------警告框确认按钮，删除预约信息
                    } else {
                        dissmissNetLoadingDialog();
                        toast("无法连接到服务器");
                    }
                }
                break;
            case R.id.yuyue_bt1://提交修改按钮
                if(yuyueAdd.isChecked()) {
                    showNetLoadingDialog("正在努力添加");
                    if (NetUtils.isNetworkAvailable(getContext())) {
                        addInfo();
                    } else {
                        dissmissNetLoadingDialog();
                        toast("无法连接到服务器");
                    }
                }
                else if(yuyueUpdate.isChecked()){
                    showNetLoadingDialog("正在努力修改");
                    if (NetUtils.isNetworkAvailable(getContext())) {
                        modifyInfo();
                    } else {
                        dissmissNetLoadingDialog();
                        toast("无法连接到服务器");
                    }
                }
                break;
            case R.id.yuyue_bt2://删除按钮
                dele = new WarningDialog(getContext(), this);
                dele.setMessage("确认删除以下信息？"+"【科室】"+selected_dept+"【预约日期】"
                        +yuyueDateRange.getText().toString());
                dele.show();
                break;
            case R.id.yuyue_bt3://清空按钮
                clearInfo();
                break;
            case R.id.yuyue_start_time:
                date1 = DateTimeFormatUtils.DATE_TIME.format(new Date());
                picker=new MyDateTimePicker(getContext(), new MyDateTimePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        String[] date=time.split(" ");
                        yuyueStartTime.setText(date[1]);
                    }
                },"2017-4-2 00:00","2030-12-29 23:59");
                picker.showSpecificTime(true);
                picker.setIsLoop(true);
                picker.show(date1);
                break;
            case R.id.yuyue_end_time:
                date1 = DateTimeFormatUtils.DATE_TIME.format(new Date());
                picker=new MyDateTimePicker(getContext(), new MyDateTimePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        String[] date=time.split(" ");
                        yuyueEndTime.setText(date[1]);
                    }
                },"2017-4-2 00:00","2030-12-29 23:59");
                picker.showSpecificTime(true);
                picker.setIsLoop(true);
                picker.show(date1);
                break;

            case R.id.yuyue_date_range1:
                date1 = DateTimeFormatUtils.DATE_TIME.format(new Date());
                picker=new MyDateTimePicker(getContext(), new MyDateTimePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        String[] date=time.split(" ");
                        yuyueDateRange1.setText(date[0]);
                    }
                },"2017-4-2 00:00","2030-12-29 23:59");
                picker.showSpecificTime(false);
                picker.setIsLoop(true);
                picker.show(date1);
                break;
            case R.id.yuyue_date_range2:
                date1 = DateTimeFormatUtils.DATE_TIME.format(new Date());
                picker=new MyDateTimePicker(getContext(), new MyDateTimePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        String[] date=time.split(" ");
                        yuyueDateRange2.setText(date[0]);
                    }
                },"2017-4-2 00:00","2030-12-29 23:59");
                picker.showSpecificTime(false);
                picker.setIsLoop(true);
                picker.show(date1);
                break;
        }
    }


    private void clearInfo() {
        yuyueAllowingDays.setText("");
        yuyueDateRange1.setText("");
        yuyueDateRange2.setText("");
        yuyueStartTime.setText("");
        yuyueEndTime.setText("");
        yuyuePreviousDays.setText("");
        yuyueDefaultTimes.setText("");
    }

    private void doDeleteReservationInfo() {
        if(selected_dept!=null&&!selected_dept.equals("")) {
            validateDate1 = yuyueDateRange1.getText().toString();
            new Thread() {
                @Override
                public void run() {
                    conn = SQLConnector.getInstance(getContext()).initSQL();
                    try {
                        pre_sta = conn.prepareStatement("delete from reservation where dept='?'"
                                +" and validateday1='?'");
                        pre_sta.setString(1, selected_dept);
                        pre_sta.setString(2, validateDate1);
                        pre_sta.executeUpdate();
                    } catch (Exception e) {
                        sendmessage(DELETE_ERRO, null);
                    } finally {
                        closeSQL();
                    }
                    sendmessage(DELETE_SUCCESS, null);
                }
            }.start();
        }else{
            dissmissNetLoadingDialog();
            toast("请选择科室");
        }
    }


    private void toast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDepart() {
        dept_ls = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                String phone = MySharedPreferences.getInstance(getContext()).getStringValue(Constants.LOGIN_USER_ID);
                conn = SQLConnector.getInstance(getmContext()).initSQL();
                try {
                    if (!phone.equals("")) {
                        pre_sta = conn.prepareStatement("select dept_no from" +
                                " doctor_dept where dept_user=?");
                        pre_sta.setString(1, phone);
                        rs = pre_sta.executeQuery();
                        while (rs.next()) {
                            dept_ls.add(rs.getString("dept_no"));
                        }
                    }
                } catch (Exception e) {
//                        sendmessage(ERRO, null);
                } finally {
                    closeSQL();
                }
                sendmessage(DEPT_SUCCESS, null);//--------查询成功回调
            }
        }.start();
    }

    @Override
    public void setDepart(List<String> list) {
        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, list);
        yuyueSpinner.setAdapter(adapter);
        dissmissNetLoadingDialog();
        yuyueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_dept = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

    @Override
    public String getValidity() {
        return null;
    }

    @Override
    public void selectInfo(String date, String dept) {
        reser_map = new HashMap<>();
        new Thread() {
            @Override
            public void run() {
                conn = SQLConnector.getInstance(getContext()).initSQL();
                try {
                    pre_sta = conn.prepareStatement("select * from reservation where dept=?" +
                            " and validateday1=?");
                    pre_sta.setString(1, dept);
                    pre_sta.setString(2, date);
                    rs = pre_sta.executeQuery();
                    while (rs.next()) {
                        reser_map.put("start", rs.getString(2));
                        reser_map.put("end", rs.getString(3));
                        reser_map.put("val_day1", rs.getString(4));
                        reser_map.put("val_day2", rs.getString(5));
                        reser_map.put("allow_day", rs.getString(6));
                        reser_map.put("pre_day", rs.getString(7));
                        reser_map.put("def_times", rs.getString(8));
                    }
                } catch (Exception e) {
                    sendmessage(ERRO, null);
                } finally {
                    closeSQL();
                }
                sendmessage(RESER_SUCCESS, null);
            }
        }.start();


    }

    @Override
    public void addInfo() {
/*private String validateDate1;//-----有效期1
    private String validateDate2;//--------------有效期2
    private String startTime1;
    private String startTime2;
    private String allowingdays;
    private String previousdays;
    private String defaulttimes;//最大违约次数*/
        if(selected_dept!=null&&!selected_dept.equals("")) {
            allowingdays = yuyueAllowingDays.getText().toString();
            validateDate1 = yuyueDateRange1.getText().toString();
            validateDate2 = yuyueDateRange2.getText().toString();
            startTime1 = yuyueStartTime.getText().toString();
            startTime2 = yuyueEndTime.getText().toString();
            previousdays = yuyuePreviousDays.getText().toString();
            defaulttimes = yuyueDefaultTimes.getText().toString();
            new Thread() {
                @Override
                public void run() {
                    conn = SQLConnector.getInstance(getContext()).initSQL();
                    try {
                        pre_sta = conn.prepareStatement("insert into reservation values" +
                                "('?','?','?','?','?','?','?','?')");
                        pre_sta.setString(1, selected_dept);
                        pre_sta.setString(2, startTime1);
                        pre_sta.setString(3, startTime2);
                        pre_sta.setString(4, validateDate1);
                        pre_sta.setString(5, validateDate2);
                        pre_sta.setString(6, allowingdays);
                        pre_sta.setString(7, previousdays);
                        pre_sta.setString(8, defaulttimes);
                        pre_sta.executeUpdate();
                    } catch (Exception e) {
                        sendmessage(ADD_ERRO, null);
                    } finally {
                        closeSQL();
                    }
                    sendmessage(ADD_SUCCESS, null);
                }
            }.start();
        }else{
            dissmissNetLoadingDialog();
            toast("请选择科室");
        }
    }

    @Override
    public void deleteInfo() {

    }

    @Override
    public void modifyInfo() {
        if(selected_dept!=null&&!selected_dept.equals("")) {
            allowingdays = yuyueAllowingDays.getText().toString();
            validateDate1 = yuyueDateRange1.getText().toString();
            validateDate2 = yuyueDateRange2.getText().toString();
            startTime1 = yuyueStartTime.getText().toString();
            startTime2 = yuyueEndTime.getText().toString();
            previousdays = yuyuePreviousDays.getText().toString();
            defaulttimes = yuyueDefaultTimes.getText().toString();
            new Thread() {
                @Override
                public void run() {
                    conn = SQLConnector.getInstance(getContext()).initSQL();
                    try {
                        pre_sta = conn.prepareStatement("update reservation set " +
                                "starttime='?',endtime='?',validateday1='?',validateday2='?'," +
                                "allowingdays='?',previousdays='?',defaulttimes='?' where" +
                                "dept='?'");
                        pre_sta.setString(1, startTime1);
                        pre_sta.setString(2, startTime2);
                        pre_sta.setString(3, validateDate1);
                        pre_sta.setString(4, validateDate2);
                        pre_sta.setString(5, allowingdays);
                        pre_sta.setString(6, previousdays);
                        pre_sta.setString(7, defaulttimes);
                        pre_sta.setString(8, selected_dept);
                        pre_sta.executeUpdate();
                    } catch (Exception e) {
                        sendmessage(UPDATE_ERRO, null);
                    } finally {
                        closeSQL();
                    }
                    sendmessage(UPDATE_SUCCESS, null);
                }
            }.start();
        }else{
            dissmissNetLoadingDialog();
            toast("请选择科室");
        }
    }

    @Override
    public void setInfoEnabled(boolean enabled) {

    }

    @Override
    public void setPresenter(YuyueContact.Presenter presenter) {
    }

    private void sendmessage(int what, Object ob) {
        Message m = new Message();
        m.what = what;
        m.obj = ob;
        han.sendMessage(m);
    }
}
