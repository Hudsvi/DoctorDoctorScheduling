package sylu.com.doctorscheduling.fragment.paiban;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
