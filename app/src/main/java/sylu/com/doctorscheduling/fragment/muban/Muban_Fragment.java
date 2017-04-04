package sylu.com.doctorscheduling.fragment.muban;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
import sylu.com.doctorscheduling.custom.muban.Doctor_Muban_List_Item;
import sylu.com.doctorscheduling.custom.muban.Muban_MyArrayAdapter;
import sylu.com.doctorscheduling.main.muban.Doctor_Details_Activity;
import sylu.com.doctorscheduling.main.muban.ListContract;
import sylu.com.doctorscheduling.view.ultra_ptr.PtrDefaultHandler2;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Muban_Fragment extends BaseFragment implements ListContract{
    List<Doctor_Muban_List_Item> lists;
    private Muban_MyArrayAdapter adapter;

    @BindView(R.id.muban_workday_state)
    RadioButton mubanWorkdayState;
    @BindView(R.id.muban_day_of_week1)
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
    @BindView(R.id.muban_day_of_week7)
    RadioButton mubanDayOfWeek7;
    @BindView(R.id.muban_day_of_week_bar)
    RadioGroup mubanDayOfWeekBar;
    @BindView(R.id.muban_main_day_of_month1)
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
    @BindView(R.id.muban_main_ptr_content)
    PtrClassicFrameLayout mubanMainPtrContent;
    private PtrHandler ptr_handler;
    @Override
    protected int getLayoutView() {
        return R.layout.muban_doctor_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initData();
        adapter = new Muban_MyArrayAdapter(getmContext(), R.layout.muban_doctor_info_item, lists, this);
        mubanMainDoctorListview.setAdapter(adapter);
        initPtr();
    }

    private void initPtr() {
        ptr_handler=new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        };
        MyPtrHeader header=new MyPtrHeader(getContext());
        mubanMainPtrContent.setPtrHandler(ptr_handler);
        mubanMainPtrContent.setHeaderView(header);
        mubanMainPtrContent.addPtrUIHandler(header);
        mubanMainPtrContent.disableWhenHorizontalMove(true);
    }

    private void initData() {
        lists = new ArrayList<Doctor_Muban_List_Item>();
        Doctor_Muban_List_Item info1 = new Doctor_Muban_List_Item("姓名1");
        Doctor_Muban_List_Item info2 = new Doctor_Muban_List_Item("姓名2");
        Doctor_Muban_List_Item info3 = new Doctor_Muban_List_Item("姓名3");
        Doctor_Muban_List_Item info4 = new Doctor_Muban_List_Item("姓名4");
        Doctor_Muban_List_Item info5 = new Doctor_Muban_List_Item("姓名5");
        Doctor_Muban_List_Item info6 = new Doctor_Muban_List_Item("姓名6");
        Doctor_Muban_List_Item info7 = new Doctor_Muban_List_Item("姓名7");
        lists.add(info1);
        lists.add(info2);
        lists.add(info3);
        lists.add(info4);
        lists.add(info5);
        lists.add(info6);
        lists.add(info7);
    }
    @OnClick({R.id.muban_day_of_week1, R.id.muban_day_of_week2, R.id.muban_day_of_week3,
            R.id.muban_day_of_week4, R.id.muban_day_of_week5, R.id.muban_day_of_week6, R.id.muban_day_of_week7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.muban_day_of_week1:
                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                showNetLoadingDialog("加载中");
                break;
            case R.id.muban_day_of_week2:
                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                showNetLoadingDialog("加载中");
                break;
            case R.id.muban_day_of_week3:

                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                showNetLoadingDialog("加载中");
                break;
            case R.id.muban_day_of_week4:

                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                showNetLoadingDialog("加载中");
                break;
            case R.id.muban_day_of_week5:

                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                showNetLoadingDialog("加载中");
                break;
            case R.id.muban_day_of_week6:

                mubanWorkdayState.setChecked(true);
                mubanWorkdayState.setText("工作日");
                showNetLoadingDialog("加载中");
                break;
            case R.id.muban_day_of_week7:
                mubanWorkdayState.setChecked(false);
                mubanWorkdayState.setText("休息日");
                showNetLoadingDialog("加载中");
                break;
        }
    }
    @OnItemLongClick(R.id.muban_main_doctor_listview)
    boolean longClicked(View view) {
        Muban_MyArrayAdapter.ViewHolder listholder = (Muban_MyArrayAdapter.ViewHolder) view.getTag();
        if (listholder.getDelete().getVisibility() == View.GONE) {
            listholder.getDelete().setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @OnItemClick(R.id.muban_main_doctor_listview)
    void onListItemCliked(View view){
        Muban_MyArrayAdapter.ViewHolder listholder = (Muban_MyArrayAdapter.ViewHolder) view.getTag();
        long user_id=listholder.getUser_id();
        String name=listholder.getName();
        Intent intent =new Intent(getmContext(),Doctor_Details_Activity.class);
        intent.putExtra(Constants.MUBAN_DOCTOR_LIST_NAME,name);
        intent.putExtra(Constants.MUBAN_DOCTOR_LIST_ID,user_id);
        startActivity(intent);

    }

    @Override
    public void delete(long user_id,int position, Muban_MyArrayAdapter.ViewHolder holder) {
        holder.getDelete().setVisibility(View.GONE);
        showNetLoadingDialog("删除中");
        //
        /*这里写删除医生信息的逻辑实现*/
        //
        lists.remove(position);//模拟删除医生信息
        adapter.notifyDataSetChanged();
        dissmissNetLoadingDialog();
        Toast.makeText(getmContext(), "has deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancel(Muban_MyArrayAdapter.ViewHolder holder) {
        holder.getDelete().setVisibility(View.GONE);
        Toast.makeText(getmContext(), "canceled", Toast.LENGTH_SHORT).show();
    }
}
