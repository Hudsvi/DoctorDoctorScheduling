package sylu.com.doctorscheduling.fragment.paiban;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
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
import sylu.com.doctorscheduling.main.yuyue.YuyueContact;
import sylu.com.doctorscheduling.view.ultra_ptr.PtrDefaultHandler2;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Paiban_Fragment extends BaseFragment {
    @BindView(R.id.paiban_main_ptr_content)
    PtrClassicFrameLayout paibanMainPtrContent;
    private List<Doctor_Paiban_List_Item> lists;
    private Paiban_MyRecyclerViewAdapter adapter;
    private PtrHandler ptr_handler;
    @BindView(R.id.paiban_list_recyclerview)
    RecyclerView paibanListRecyclerview;
    private static final int ERRO = 0;//-----------错误处理
    private static final int DEPT_SUCCESS = 1;//-----------科室获取成功
    private static final int RESER_SUCCESS = 2;//-----------预约信息获取成功
    private static final int ADD_SUCCESS = 3;//-----------预约信息新增成功
    private static final int ADD_ERRO =4 ;//-----------------预约信息新增失败
    private static final int UPDATE_SUCCESS = 5;//-----------预约信息修改成功
    private static final int UPDATE_ERRO =6 ;//-----------------预约信息修改失败
    private static final int DELETE_SUCCESS = 7;//-----------预约信息删除成功
    private static final int DELETE_ERRO =8;//-----------------预约信息删除失败
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
                                }
                            }).show();
                    break;
                case DEPT_SUCCESS:
                    break;
                case RESER_SUCCESS://--------查询预约信息成功
                    break;
                case ADD_SUCCESS://----------添加成功
                    break;
                case ADD_ERRO://-----------添加失败
                    break;
                case UPDATE_SUCCESS:
                    break;
                case UPDATE_ERRO:
                    break;
                case DELETE_SUCCESS:
                    break;
                case DELETE_ERRO:
                    break;
            }
        }
    };
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

            }
        };

        MyPtrHeader header = new MyPtrHeader(getContext());
        paibanMainPtrContent.setPtrHandler(ptr_handler);
        paibanMainPtrContent.setHeaderView(header);
        paibanMainPtrContent.addPtrUIHandler(header);
        paibanMainPtrContent.disableWhenHorizontalMove(true);

    }

    private void initAdapter() {
        adapter = new Paiban_MyRecyclerViewAdapter(getmContext(), lists);
        paibanListRecyclerview.setLayoutManager(new GridLayoutManager(getmContext(), 2));
        paibanListRecyclerview.setAdapter(adapter);
    }

    private void initData() {
        //从数据库获取数据
        //。。。。。。。。。。。。

        //模拟加载数据
        //模拟数据
        lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Doctor_Paiban_List_Item item = new Doctor_Paiban_List_Item();
            item.setName("陈若琳");
            item.setTime01(new Date().getTime() + 3600000 * 13);
            item.setTime02(new Date().getTime() + 3600000 * 16);
            item.setTime21(new Date().getTime() + 3600000 * 19);
            item.setTime22(new Date().getTime() + 3600000 * 23);
            item.setAmount1(4);
            item.setAmount2(6);
            lists.add(item);
        }

    }

    @OnClick(R.id.paiban_list_recyclerview)
    public void onClick() {

    }
}
