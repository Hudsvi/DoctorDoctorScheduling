package sylu.com.doctorscheduling.fragment.paiban;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import sylu.com.doctorscheduling.BaseFragment;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.custom.MyPtrHeader;
import sylu.com.doctorscheduling.custom.paiban.Doctor_Paiban_List_Item;
import sylu.com.doctorscheduling.custom.paiban.Paiban_MyRecyclerViewAdapter;
import sylu.com.doctorscheduling.internet.jdbc.SQLConnector;
import sylu.com.doctorscheduling.utils.NetUtils;
import sylu.com.doctorscheduling.view.MenuDialog;
import sylu.com.doctorscheduling.view.ultra_ptr.PtrDefaultHandler2;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Paiban_Fragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.paiban_main_ptr_content)
    PtrClassicFrameLayout paibanMainPtrContent;
    @BindView(R.id.paiban_non_ll)
    LinearLayout paibanNonLl;
    private List<Doctor_Paiban_List_Item> lists;
    private Paiban_MyRecyclerViewAdapter adapter;
    private PtrHandler ptr_handler;
    private ImageView img_menu;
    private MenuDialog menuDialog;
    private Connection conn;
    private PreparedStatement pre_sta;
    private ResultSet rs;
    private static int count = 1;//默认生成排班月数
    @BindView(R.id.paiban_list_recyclerview)
    RecyclerView paibanListRecyclerview;
    private static final int ERRO = 0;//-----------错误处理
    private static final int SUCCESS = 1;//-----------获取成功

    private Handler han = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERRO:
                    dissmissNetLoadingDialog();
                    if (mframe != null && mframe.isRefreshing()) {
                        mframe.refreshComplete();
                    }
                    toast("获取列表失败");
                    break;
                case SUCCESS:
                    dissmissNetLoadingDialog();
                    if (mframe != null && mframe.isRefreshing()) {
                        mframe.refreshComplete();
                    }
                    initAdapter();
                    break;

            }
        }
    };
    private PtrFrameLayout mframe;

    @Nullable
    @Override
    protected int getLayoutView() {
        return R.layout.paiban_lists;

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initPtr();
        initData();
        initAdapter();
    }

    private void initPtr() {
        ptr_handler = new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //load more
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mframe = frame;
                if (NetUtils.isNetworkAvailable(getContext())) {
                    showNetLoadingDialog("努力加载中");
                    selectData();
                } else {
                    toast("无法连接到服务器");
                }
            }
        };

        MyPtrHeader header = new MyPtrHeader(getContext());
        paibanMainPtrContent.setPtrHandler(ptr_handler);
        paibanMainPtrContent.setHeaderView(header);
        paibanMainPtrContent.addPtrUIHandler(header);
        paibanMainPtrContent.disableWhenHorizontalMove(true);

    }

    private void selectData() {
        lists = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                conn = SQLConnector.getInstance(getContext()).initSQL();
                try {
                    pre_sta = conn.prepareStatement("select * from arrange");
                    rs = pre_sta.executeQuery();
                    while (rs.next()) {
                        Doctor_Paiban_List_Item item = new Doctor_Paiban_List_Item();
                        item.setName(rs.getString("doctor_name"));
                        item.setTime01(rs.getString("am_start"));
                        item.setTime02(rs.getString("am_end"));
                        item.setTime21(rs.getString("am_count"));
                        item.setTime22(rs.getString("pm_start"));
                        item.setAmount1(rs.getString("pm_end"));
                        item.setAmount2(rs.getString("pm_count"));
                        lists.add(item);
                    }
                    sendmessage(SUCCESS, null);
                } catch (Exception e) {
                    sendmessage(ERRO, null);
                } finally {
                    closeSQL();
                }
            }
        }.start();
    }

    private void toast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new Paiban_MyRecyclerViewAdapter(getmContext(), lists);
            paibanListRecyclerview.setLayoutManager(new GridLayoutManager(getmContext(), 2));
            paibanListRecyclerview.setAdapter(adapter);
        } else {
            adapter.setData(lists);
            adapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        img_menu = (ImageView) getActivity().findViewById(R.id.paiban_doctor_produce);
        img_menu.setOnClickListener(this);
        menuDialog = new MenuDialog(getContext(), this);
        menuDialog.setItemIMGandTV1(R.drawable.fuhe, "排一个月");
        menuDialog.setItemIMGandTV2(R.drawable.fuhe, "排两个月");


    }


    @OnClick({R.id.paiban_list_recyclerview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.paiban_list_recyclerview:
                break;
            case R.id.paiban_doctor_produce:
                menuDialog.show();
                break;
            case R.id.main_menu_back_linearlayout:
                menuDialog.dismiss();
                break;

            case R.id.menu_main_item1_ll:
                break;
            case R.id.menu_main_item2_ll:
                break;
        }
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



}
