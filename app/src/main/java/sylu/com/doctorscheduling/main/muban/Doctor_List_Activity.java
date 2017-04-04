package sylu.com.doctorscheduling.main.muban;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.muban.Doctor_Muban_List_Item;
import sylu.com.doctorscheduling.custom.muban.Muban_MyArrayAdapter;

/**
 * Created by Hudsvi on 2017/3/15 13:45.
 */

public class Doctor_List_Activity extends Activity implements ListContract {
    @BindView(R.id.muban_main_doctor_listview)
    ListView muban_listView;
    private Context context = getBaseContext();
    private static int signal = 0;
    List<Doctor_Muban_List_Item> lists;
    private Muban_MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muban_doctor_list);
        ButterKnife.bind(this);
        initData();
        adapter = new Muban_MyArrayAdapter(this, R.layout.muban_doctor_info_item, lists, this);
        muban_listView.setAdapter(adapter);
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
    void itemClicked(View view) {
        Muban_MyArrayAdapter.ViewHolder listholder = (Muban_MyArrayAdapter.ViewHolder) view.getTag();
        long user_id=listholder.getUser_id();
        String name=listholder.getName();
        Intent intent =new Intent(Doctor_List_Activity.this,Doctor_Details_Activity.class);
        intent.putExtra(Constants.MUBAN_DOCTOR_LIST_NAME,name);
        intent.putExtra(Constants.MUBAN_DOCTOR_LIST_ID,user_id);
        startActivity(intent);

    }

    private void initData() {
        lists = new ArrayList<Doctor_Muban_List_Item>();
        Doctor_Muban_List_Item info1 = new Doctor_Muban_List_Item("姓名1");
        Doctor_Muban_List_Item info2 = new Doctor_Muban_List_Item("姓名2");
        Doctor_Muban_List_Item info3 = new Doctor_Muban_List_Item("姓名3");
        lists.add(info1);
        lists.add(info2);
        lists.add(info3);
    }

    @Override
    public void delete(long user_id,int pos, Muban_MyArrayAdapter.ViewHolder holder) {
        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
        //
        //
    }

    @Override
    public void cancel(Muban_MyArrayAdapter.ViewHolder holder) {
        holder.getDelete().setVisibility(View.GONE);
        Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();

    }
}
