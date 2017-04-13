package sylu.com.doctorscheduling.fragment.muban;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import sylu.com.doctorscheduling.BaseFragment;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.MyPtrHeader;
import sylu.com.doctorscheduling.custom.MySharedPreferences;
import sylu.com.doctorscheduling.custom.muban.Doctor_Muban_List_Item;
import sylu.com.doctorscheduling.custom.muban.Muban_MyArrayAdapter;
import sylu.com.doctorscheduling.internet.jdbc.SQLConnector;
import sylu.com.doctorscheduling.main.muban.Doctor_AddName;
import sylu.com.doctorscheduling.main.muban.Doctor_Details_Activity;
import sylu.com.doctorscheduling.main.muban.ListContract;
import sylu.com.doctorscheduling.utils.NetUtils;
import sylu.com.doctorscheduling.view.MenuDialog;
import sylu.com.doctorscheduling.view.WarningDialog;
import sylu.com.doctorscheduling.view.ultra_ptr.PtrDefaultHandler2;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Muban_Fragment extends BaseFragment implements ListContract, View.OnClickListener {
    private static final int REQUEST_CODE_ADD_NAME = 100;
    private static final int REQUEST_CODE_UPDATE_NAME = 101;
    private static final int PULL_TO_REFRESH = 1;
    private static final int CLICK_TO_REFRESH = 2;
    private Muban_MyArrayAdapter adapter;
    @BindView(R.id.muban_workday_state)
    RadioButton mubanWorkdayState;
    @BindView(R.id.muban_day_of_week1)//--------------------星期
            RadioButton mubanDayOfWeek1;
    @BindView(R.id.muban_day_of_week2)
    RadioButton mubanDayOfWeek2;
    @BindView(R.id.muban_day_of_week3)
    RadioButton mubanDayOfWeek3;
    @BindView(R.id.muban_day_of_week4)
    RadioButton mubanDayOfWeek4;
    @BindView(R.id.muban_day_of_week5)
    RadioButton mubanDayOfWeek5;
    @BindView(R.id.muban_day_of_week6)
    RadioButton mubanDayOfWeek6;
    @BindView(R.id.muban_day_of_week7)//------------星期---本周日
            RadioButton mubanDayOfWeek7;
    @BindView(R.id.muban_day_of_week_bar)
    RadioGroup mubanDayOfWeekBar;
    @BindView(R.id.muban_date_today)
    TextView mubanDateToday;
    @BindView(R.id.muban_main_day_of_month1)//-------------号数
            TextView mubanMainDayOfMonth1;
    @BindView(R.id.muban_main_day_of_month2)
    TextView mubanMainDayOfMonth2;
    @BindView(R.id.muban_main_day_of_month3)
    TextView mubanMainDayOfMonth3;
    @BindView(R.id.muban_main_day_of_month4)
    TextView mubanMainDayOfMonth4;
    @BindView(R.id.muban_main_day_of_month5)
    TextView mubanMainDayOfMonth5;
    @BindView(R.id.muban_main_day_of_month6)
    TextView mubanMainDayOfMonth6;
    @BindView(R.id.muban_main_day_of_month7)
    TextView mubanMainDayOfMonth7;
    @BindView(R.id.muban_main_bar_frame)
    ConstraintLayout mubanMainBarFrame;
    @BindView(R.id.muban_main_doctor_listview)
    ListView mubanMainDoctorListview;
    @BindView(R.id.muban_list_non_ll)
    LinearLayout no_list_ll;
    @BindView(R.id.muban_main_ptr_content)
    PtrClassicFrameLayout mubanMainPtrContent;
    private ImageView add_doctor;
    private Connection conn;
    private PreparedStatement pre_sta;
    private PreparedStatement pre_sta2;
    private ResultSet rs;
    private ResultSet rs2;
    private PtrHandler ptr_handler;
    private PtrFrameLayout mframe;
    private static Map<Integer, List<Doctor_Muban_List_Item>> doctor_maps;//-------------医生列表
    private WarningDialog delete_dialog;
    private List<String> workingState;//-------------未来一周的工作状态
    private List<Doctor_Muban_List_Item> current_list = new ArrayList<>();
    private Muban_MyArrayAdapter.ViewHolder muban_update_viewholder;
    private Muban_MyArrayAdapter.ViewHolder muban_delete_viewholder;
    private List<String> doctor_newname;//--------------存放新的医生信息
    private Date currentdate;//-----------------当天的日期
    private String[] date;//--------------包含年、月、日、周期和星期的信息
    private String day;//------------------当前号数
    private static int long_clicked_count = 0;//----------最大
    private static final int ERRO = 0;//-----------错误处理
    private static final int WORKING_STATE_ERRO = 1;//
    private static final int WORKING_STATE_SUCCESS = 2;
    private static final int DOCTOR_LIST_SUCCESS = 3;
    private static final int DOCTOR_LIST_ERRO = 4;
    private static final int ADD_DOCTOR_NAME_SUCCESS = 5;//-----------医生添加成功
    private static final int ADD_DOCTOR_NAMR_ERRO = 6;//-----------------医生添加失败
    private static final int MUBAN_UPDATE_SUCCESS = 7;//--------修改医生姓名成功
    private static final int MUBAN_UPDATE_ERRO = 8;
    private static final int DELETE_DOCTOR_SUCCESS = 9;//-------删除特定日期，特定医生的信息成功
    private static final int DELETE_DOCTOR_ERRO = 10;

    //    private static final int UPDATE_SUCCESS = 5;//-----------预约信息修改成功
//    private static final int UPDATE_ERRO = 6;//-----------------预约信息修改失败
//    private static final int DELETE_SUCCESS = 7;//-----------预约信息删除成功
//    private static final int DELETE_ERRO = 8;//-----------------预约信息删除失败
    private SimpleDateFormat format;
    private ConstraintLayout muban_date;//---------------标题栏日期
    private TextView t_Year, t_Month, t_Week;//---------------------年份、月份、年周
    private ImageView muban_menu;//-------menu
    private TextView muban_title;//
    private MenuDialog dialog;
    private boolean isEnnableToClick = true;
    private Handler han = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERRO:
                    dissmissNetLoadingDialog();
                    new AlertDialog.Builder(getContext()).setMessage("获取数据失败！请检查您的网络连接")
                            .setTitle("提示")
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                    dissmissNetLoadingDialog();
                                }
                            }).show();
                    break;
                case WORKING_STATE_ERRO:
                    break;

                case WORKING_STATE_SUCCESS://--------查询工作日状态成功
                    break;
                case DOCTOR_LIST_SUCCESS:
                    setDoctorAdapter((int) msg.obj);
                    break;
                case DOCTOR_LIST_ERRO:
                    toast("获取列表失败");
                    if (mframe != null && mframe.isRefreshing()) {
                        mframe.refreshComplete();
                    }
                    break;
                case ADD_DOCTOR_NAME_SUCCESS://----------添加姓名成功
                    dissmissNetLoadingDialog();
                    current_list.clear();
                    current_list.addAll(doctor_maps.get(getPosition()));
                    adapter.updateData(current_list);
                    adapter.notifyDataSetChanged();
                    toast("添加成功");
                    break;
                case ADD_DOCTOR_NAMR_ERRO://-----------添加姓名失败
                    dissmissNetLoadingDialog();
                    toast("添加失败");
                    break;
                case MUBAN_UPDATE_SUCCESS:
                    dissmissNetLoadingDialog();
                    current_list.clear();
                    current_list.addAll(doctor_maps.get(getPosition()));
                    adapter.updateData(current_list);
                    adapter.notifyDataSetChanged();
                    toast("姓名修改成功");
                    break;
                case MUBAN_UPDATE_ERRO:
                    dissmissNetLoadingDialog();
                    toast("修改姓名失败");
                    break;
                case DELETE_DOCTOR_SUCCESS:
                    dissmissNetLoadingDialog();
                    adapter.notifyDataSetChanged();
                    toast("医生信息已删除");
                    break;
                case DELETE_DOCTOR_ERRO:
                    dissmissNetLoadingDialog();
                    if (delete_dialog != null && delete_dialog.isShowing()) {
                        delete_dialog.dismiss();
                    }
                    toast("删除失败");
                    break;
            }
        }
    };


    @Override
    protected int getLayoutView() {
        return R.layout.muban_doctor_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        doctor_maps = new HashMap<>();
        initPtr();
//        selectWorkingState();
        initDateAndWeek();
        mubanDayOfWeek1.setChecked(true);
        add_doctor = (ImageView) getActivity().findViewById(R.id.muban_doctor_add);
        add_doctor.setOnClickListener(this);
        selectDoctorList(getPosition(), PULL_TO_REFRESH);

    }


    private void initPtr() {
        ptr_handler = new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mframe = frame;
                if (NetUtils.isNetworkAvailable(getContext())) {
                    selectDoctorList(getPosition(), PULL_TO_REFRESH);//
                } else {
                    toast("无法连接到服务器");
                    frame.refreshComplete();
                }

            }
        };
        MyPtrHeader header = new MyPtrHeader(getContext());
        mubanMainPtrContent.setPtrHandler(ptr_handler);
        mubanMainPtrContent.setHeaderView(header);
        mubanMainPtrContent.addPtrUIHandler(header);
        mubanMainPtrContent.disableWhenHorizontalMove(true);
    }


    @OnClick({R.id.muban_day_of_week1, R.id.muban_day_of_week2, R.id.muban_day_of_week3,
            R.id.muban_day_of_week4, R.id.muban_day_of_week5, R.id.muban_day_of_week6,
            R.id.muban_day_of_week7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.warning_dialog_confirm:
                String dept = muban_delete_viewholder.getDept();
                String doc_name = muban_delete_viewholder.getName();
                String date = muban_delete_viewholder.getDate();
                int del_position = muban_delete_viewholder.getPosition();
                showNetLoadingDialog("删除姓名中");
                if (delete_dialog != null && delete_dialog.isShowing()) {
                    delete_dialog.dismiss();
                }
                deleteItem(dept, date, doc_name, del_position);

                break;
            case R.id.warning_dialog_back:
                if (delete_dialog != null && delete_dialog.isShowing()) {
                    delete_dialog.dismiss();
                }
                break;
            case R.id.muban_day_of_week7:
                mubanWorkdayState.setChecked(false);
                mubanWorkdayState.setText("休息日");
                selectDoctorList(0, CLICK_TO_REFRESH);
                break;
            case R.id.muban_day_of_week1:
                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                selectDoctorList(1, CLICK_TO_REFRESH);
                break;
            case R.id.muban_day_of_week2:
                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                selectDoctorList(2, CLICK_TO_REFRESH);
                break;
            case R.id.muban_day_of_week3:
                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                selectDoctorList(3, CLICK_TO_REFRESH);

                break;
            case R.id.muban_day_of_week4:

                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                selectDoctorList(4, CLICK_TO_REFRESH);

                break;
            case R.id.muban_day_of_week5:

                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                selectDoctorList(5, CLICK_TO_REFRESH);

                break;
            case R.id.muban_day_of_week6:
                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                selectDoctorList(6, CLICK_TO_REFRESH);
                break;
            case R.id.muban_doctor_add:
                Intent add_doctor = new Intent(getContext(), Doctor_AddName.class);
                add_doctor.putExtra(Constants.MUBAN_NAME_TYPE, 1);//---------1为添加医生信息
                startActivityForResult(add_doctor, REQUEST_CODE_ADD_NAME);
                break;
        }
    }


    @OnItemLongClick(R.id.muban_main_doctor_listview)
    boolean longClicked(View view) {
        isEnnableToClick = false;
        if (long_clicked_count == 0) {
            Muban_MyArrayAdapter.ViewHolder listholder = (Muban_MyArrayAdapter.ViewHolder) view.getTag();
            if (listholder.getDelete().getVisibility() == View.GONE) {
                listholder.getDelete().setVisibility(View.VISIBLE);
                long_clicked_count = 1;
                return false;
            }
            return true;
        }
        return true;
    }

    @OnItemClick(R.id.muban_main_doctor_listview)
    void onListItemCliked(View view) {
        if (isEnnableToClick) {
            if (NetUtils.isNetworkAvailable(getContext())) {
                Muban_MyArrayAdapter.ViewHolder listholder = (Muban_MyArrayAdapter.ViewHolder) view.getTag();
                String date = listholder.getDate();
                String name = listholder.getName();
                String dept=listholder.getDept();
                Intent intent = new Intent(getmContext(), Doctor_Details_Activity.class);
                intent.putExtra(Constants.MUBAN_DOCTOR_LIST_NAME, name);
                intent.putExtra(Constants.MUBAN_DOCTOR_LIST_DATE, date);
                intent.putExtra(Constants.MUBAN_WEEK, getPosition());
                intent.putExtra(Constants.MUBAN_DEPT, dept);
                startActivity(intent);
            }else{toast("无法连接到服务器");}
        }
    }

    @Override
    public void delete(Muban_MyArrayAdapter.ViewHolder holder) {
        if (NetUtils.isNetworkAvailable(getContext())) {
            this.muban_delete_viewholder = holder;
            String dept = holder.getDept();
            String doc_name = holder.getName();
            String date = holder.getDate();
            delete_dialog = new WarningDialog(getContext(), this);
            delete_dialog.setMessage("删除当前模板信息？\n" +
                    "【所在科室:】" + dept + "\n" +
                    "【姓名:】" + doc_name + "\n" +
                    "【出诊日期:】" + date
            );
            delete_dialog.show();
        } else {
            toast("无法连接到服务器");
        }

    }

    private void deleteItem(String dept, String date, String doc_name, int position) {
        new Thread() {
            @Override
            public void run() {
                conn = SQLConnector.getInstance(getContext()).initSQL();
                try {
                    pre_sta = conn.prepareStatement("delete from arrange where dept_no=?" +
                            "and date=? and doctor_name=?");
                    pre_sta.setString(1, dept);
                    pre_sta.setString(2, date);
                    pre_sta.setString(3, doc_name);
                    pre_sta.executeUpdate();
//                    doctor_maps.get(getPosition()).remove(position);
                    /////
                    current_list.remove(position);
                    sendmessage(DELETE_DOCTOR_SUCCESS, null);
                } catch (Exception e) {
                    sendmessage(DELETE_DOCTOR_ERRO, null);
                } finally {
                    closeSQL();
                }
            }
        }.start();
    }

    @Override
    public void cancel(Muban_MyArrayAdapter.ViewHolder holder) {
        long_clicked_count = 0;
        isEnnableToClick = true;
        holder.getDelete().setVisibility(View.GONE);
    }

    @Override
    public void update(Muban_MyArrayAdapter.ViewHolder holder) {
        this.muban_update_viewholder = holder;
        String d_dept = holder.getDept();//科室编号
        String d_date = holder.getDate();//日期
        String d_name = holder.getName();//旧姓名
        doctor_newname = new ArrayList<>();
        doctor_newname.add(d_dept);
        doctor_newname.add(d_date);
        doctor_newname.add(d_name);//旧姓名
//        toast(d_dept+d_date+name);
        Intent modyfy_doctor = new Intent(getContext(), Doctor_AddName.class);
        modyfy_doctor.putExtra(Constants.MUBAN_NAME_TYPE, 2);//---------2为修改医生信息
        startActivityForResult(modyfy_doctor, REQUEST_CODE_UPDATE_NAME);
//        cancel(holder);//隐藏修改项
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void selectWorkingState() {
        workingState = (List<String>) MySharedPreferences.getInstance(getContext()).getObject(Constants.MUBAN_WORKING_STATE);
        if (workingState == null) {
            WorkingState();
        }
    }

    private void WorkingState() {

    }

    private void sendmessage(int what, Object ob) {
        Message m = new Message();
        m.what = what;
        m.obj = ob;
        han.sendMessage(m);
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

    private void toast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    private void initDateAndWeek() {
        TextView[] tv_days = {mubanMainDayOfMonth1, mubanMainDayOfMonth2, mubanMainDayOfMonth3,
                mubanMainDayOfMonth4, mubanMainDayOfMonth5, mubanMainDayOfMonth6, mubanMainDayOfMonth7};
        format = new SimpleDateFormat("yyyy MM dd w");//0.年、1.月、2.日、
        // 3.周（一年中的第几周） //u（星期几）   在安卓中不支持
        currentdate = new Date();
        date = format.format(currentdate).toString().split(" ");
        Calendar cal = Calendar.getInstance();
        int MaxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月的最大号数
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK) - 1;//------------星期几(0为星期日)
        int days = Integer.valueOf(date[2]);//-----号数
        int day_m = MaxDayOfMonth - 6;//判断是否超本月
        if (dayofweek == 0) {//正好为本周日
            if (day_m >= days) {//没有超过本月的最大号数
                for (int i = 0; i < 7; i++) {
                    tv_days[i].setText(String.valueOf(days + i) + "号");
                }
            } else {
                int outOfdays = days + 6 - MaxDayOfMonth;//超过最大号数的天数
                for (int i = 0; i < 7 - outOfdays; i++) {
                    tv_days[i].setText(String.valueOf(days + i) + "号");
                }
                for (int i = 1; i <= outOfdays; i++) {
                    tv_days[6 - outOfdays + i].setText(String.valueOf(i) + "号");
                }
            }
        } else {
            int needToAdd = days + 7 - dayofweek;//从本周日开始算的日期是多少号
            if (day_m >= needToAdd) {//
                for (int i = 0; i < 7; i++) {
                    tv_days[i].setText(String.valueOf(needToAdd + i) + "号");
                }
            } else {
                int outOfdays2 = needToAdd + 6 - MaxDayOfMonth;//从本周日开始算,超过最大号数的天数
                for (int i = 0; i < 7 - outOfdays2; i++) {
                    tv_days[i].setText(String.valueOf(needToAdd + i) + "号");
                }
                for (int i = 1; i <= outOfdays2; i++) {
                    tv_days[6 - outOfdays2 + i].setText(String.valueOf(i) + "号");
                }
            }
        }
        initBarDate(date);
    }

    private void initBarDate(String[] date) {
        t_Year = (TextView) getActivity().findViewById(R.id.muban_title_bar_year);
        t_Month = (TextView) getActivity().findViewById(R.id.muban_title_bar_month);
        t_Week = (TextView) getActivity().findViewById(R.id.muban_title_bar_week);
        t_Year.setText(date[0] + "年");
        t_Month.setText(date[1] + "月");
        t_Week.setText(date[3] + "周");
        Calendar cal = Calendar.getInstance();
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK) - 1;//------------星期几(0为星期日)
        mubanDateToday.setText(date[2] + "号");
    }

    private void selectDoctorList(int position, int type) {
        if (doctor_maps.containsKey(position) && type == CLICK_TO_REFRESH) {
//            toast("contain:"+position);
            setDoctorAdapter(position);
        } else if (type == PULL_TO_REFRESH || !doctor_maps.containsKey(position)) {
            String day = getDays(position);
            SimpleDateFormat queryformat = new SimpleDateFormat("yyyy-MM-");
            Date query_date = getDate(position);
            String querydate = queryformat.format(query_date).toString() + day;
            ArrayList<Doctor_Muban_List_Item> list = new ArrayList<>();
            new Thread() {
                @Override
                public void run() {
                    conn = SQLConnector.getInstance(getContext()).initSQL();
                    try {
                        pre_sta = conn.prepareStatement("select doctor_name,dept_no from arrange where date" +
                                "=?");
                        pre_sta.setString(1, querydate);
                        rs = pre_sta.executeQuery();
                        while (rs.next()) {
                            Doctor_Muban_List_Item d = new Doctor_Muban_List_Item(rs.getString(2), querydate, rs.getString(1));
                            list.add(d);
                        }
                        doctor_maps.put(position, list);
                        sendmessage(DOCTOR_LIST_SUCCESS, position);
                    } catch (Exception e) {
                        sendmessage(DOCTOR_LIST_ERRO, e);
                    } finally {
                        closeSQL();
                    }
                }
            }.start();
        }
    }

    private void setDoctorAdapter(int position) {
        List<Doctor_Muban_List_Item> list = doctor_maps.get(position);//---------此处的position是星期几的position，0为本周日，1为下周一
        if (list == null || list.size() == 0) {
            if (mubanMainDoctorListview.getVisibility() == View.VISIBLE) {
                mubanMainDoctorListview.setVisibility(View.GONE);
            }
            if (no_list_ll.getVisibility() == View.GONE) {
                no_list_ll.setVisibility(View.VISIBLE);
            }
            if (mframe != null && mframe.isRefreshing()) {
                mframe.refreshComplete();
            }
        } else {
            if (mubanMainDoctorListview.getVisibility() == View.GONE) {
                mubanMainDoctorListview.setVisibility(View.VISIBLE);
            }
            if (no_list_ll.getVisibility() == View.VISIBLE) {
                no_list_ll.setVisibility(View.GONE);
            }
            if (adapter == null) {
                current_list.clear();
                current_list.addAll(list);
                adapter = new Muban_MyArrayAdapter(getContext(), R.layout.muban_doctor_info_item, current_list, this);
                mubanMainDoctorListview.setAdapter(adapter);
                if (mframe != null && mframe.isRefreshing()) {
                    mframe.refreshComplete();
                }
            } else {
                current_list.clear();
                current_list.addAll(list);
                adapter.updateData(current_list);
                adapter.notifyDataSetChanged();
                if (mframe != null && mframe.isRefreshing()) {
                    mframe.refreshComplete();
                }
            }
        }
    }

    /**
     * 本周日为0，1～6为下周一到下周六
     */
    private String getDays(int position) {
        TextView[] tv_days = {mubanMainDayOfMonth1, mubanMainDayOfMonth2, mubanMainDayOfMonth3,
                mubanMainDayOfMonth4, mubanMainDayOfMonth5, mubanMainDayOfMonth6, mubanMainDayOfMonth7};
        String[] day = tv_days[position].getText().toString().split("号");
        return day[0];
    }

    private int getPosition() {
        if (mubanDayOfWeek7.isChecked()) {
            return 0;
        } else if (mubanDayOfWeek1.isChecked()) {
            return 1;
        } else if (mubanDayOfWeek2.isChecked()) {
            return 2;
        } else if (mubanDayOfWeek3.isChecked()) {
            return 3;
        } else if (mubanDayOfWeek4.isChecked()) {
            return 4;
        } else if (mubanDayOfWeek5.isChecked()) {
            return 5;
        } else if (mubanDayOfWeek6.isChecked()) {
            return 6;
        } else {
            return 1;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_NAME && resultCode == Constants.MUBAN_D_NAME_ADD_CODE) {
            if (data != null) {
                String name = data.getStringExtra(Constants.MUBAN_NAME_ADD);
                String dept = data.getStringExtra(Constants.MUBAN_DEPT_ADD);
                if (!TextUtils.isEmpty(name)) {
                    String day = getDays(getPosition());
                    if (NetUtils.isNetworkAvailable(getContext())) {
                        showNetLoadingDialog("添加中");
                        addNewDoctor(dept, name, day);
                    } else {
                        toast("无法连接到服务器");
                    }
                }
            }
        }
        if (requestCode == REQUEST_CODE_UPDATE_NAME && resultCode == Constants.MUBAN_D_NAME_UPDATE_CODE) {
            if (data != null) {
                String newname = data.getStringExtra(Constants.MUBAN_NAME_UPDATE);
                if (doctor_newname != null) {
                    doctor_newname.add(newname);
                    updateDoctorName(doctor_newname);
                } else {
                    toast("修改医生姓名时出错，科室和日期为空");
                }
            }
        }
    }

    private void updateDoctorName(List<String> doctor_newname) {
//        String t_dept_no, t_date, t_name, t_newname;
//        t_dept_no = doctor_newname.get(0);
//        t_date = doctor_newname.get(1);
//        t_name = doctor_newname.get(2);
//        t_newname = doctor_newname.get(3);
//        toast(t_dept_no + t_date + t_name + t_newname);
        if (NetUtils.isNetworkAvailable(getContext())) {
            showNetLoadingDialog("修改姓名中");
            String dept_no_u, date_u, name_u, newname_u;
            dept_no_u = doctor_newname.get(0);
            date_u = doctor_newname.get(1);
            name_u = doctor_newname.get(2);
            newname_u = doctor_newname.get(3);
            new Thread() {
                @Override
                public void run() {
                    conn = SQLConnector.getInstance(getContext()).initSQL();
                    try {
//                        "update arrange set doctor_name=? where " +
//                        "dept_no=? and date=? and doctor_name=?"
                        pre_sta = conn.prepareStatement("update arrange set doctor_name=? where " +
                                "dept_no=? and doctor_name=?");
                        pre_sta.setString(1, newname_u);
                        pre_sta.setString(2, dept_no_u);
                        pre_sta.setString(3, name_u);
                        pre_sta.executeUpdate();
                        doctor_maps.get(getPosition()).remove(muban_update_viewholder.getPosition());
                        Doctor_Muban_List_Item item = new Doctor_Muban_List_Item(dept_no_u, date_u, newname_u);
                        doctor_maps.get(getPosition()).add(muban_update_viewholder.getPosition(), item);
                        sendmessage(MUBAN_UPDATE_SUCCESS, null);
                    } catch (Exception e) {
                        sendmessage(MUBAN_UPDATE_ERRO, null);
                    } finally {
                        closeSQL();
                    }
                }
            }.start();
        } else {
            toast("无法连接到服务器");
        }
    }


    private Date getDate(int position) {
        if (position > 0) {
            int previousday = Integer.valueOf(getDays(position - 1));
            int today = Integer.valueOf(getDays(position));
            if (today < previousday) {
                Date query_d = new Date();
                int month = query_d.getMonth() + 1;
                query_d.setMonth(month);
                return query_d;
            } else {
                return new Date();
            }

        } else {
            return new Date();
        }
    }

    private void addNewDoctor(String dept, String name, String day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-");
        Date add_date = getDate(getPosition());
        String add_name_date = format.format(add_date) + day;

//        toast(dept + name + add_name_date);
        new Thread() {
            @Override
            public void run() {
                String name_dept="102";
                conn = SQLConnector.getInstance(getContext()).initSQL();
                try {
                    conn.setAutoCommit(false);
                    pre_sta2=conn.prepareStatement("select dept_name from admin_dept where dept_no=?");
                    pre_sta2.setString(1,dept);
                    rs2=pre_sta2.executeQuery();
                    while(rs2.next()) {
                        name_dept =rs2.getString(1);
                    }
                    pre_sta = conn.prepareStatement("insert into arrange(dept_no,date," +
                            "doctor_name,dept_name) values(?,?,?,?)");
                    pre_sta.setString(1, dept);
                    pre_sta.setString(2, add_name_date);
                    pre_sta.setString(3, name);
                    pre_sta.setString(4, name_dept);
                    pre_sta.executeUpdate();
                    conn.commit();
                    Doctor_Muban_List_Item item = new Doctor_Muban_List_Item(dept, add_name_date, name);
                    doctor_maps.get(getPosition()).add(0, item);
                    sendmessage(ADD_DOCTOR_NAME_SUCCESS, null);
                } catch (Exception e) {
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    sendmessage(ADD_DOCTOR_NAMR_ERRO, e);
                } finally {
                    closeSQL();
                }
            }
        }.start();
    }
}
