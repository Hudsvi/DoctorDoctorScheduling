package sylu.com.doctorscheduling.custom.muban;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.main.muban.ListContract;

/**
 * Created by Hudsvi on 2017/3/15 10:36.
 */

public class Muban_MyArrayAdapter extends ArrayAdapter {
    private List<Doctor_Muban_List_Item> lists;
    private Context context;
    private int resourse_id;
    private ListContract contract;

    public Muban_MyArrayAdapter(Context context, int resource, List<Doctor_Muban_List_Item> lists, ListContract contract) {
        super(context, resource, lists);
        this.lists = lists;
        this.resourse_id = resource;
        this.context = context;
        this.contract = contract;
    }

    public void updateData(List<Doctor_Muban_List_Item> list) {
        this.lists = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Doctor_Muban_List_Item minfo = (Doctor_Muban_List_Item) getItem(lists.size()-position-1);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourse_id, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.doctor_name.setText(minfo.getDoctor_name());
        holder.doctor_dept.setText("(所在科室:" + minfo.getDept() + ")");
        holder.setDept(minfo.getDept());
        holder.setDate(minfo.getDate());
        holder.setName(minfo.getDoctor_name());
        holder.setPosition(lists.size()-position-1);////
        return convertView;
    }


    public class ViewHolder {

        private String date;

        private int position;//医生列表的position

        private String name;

        private String dept;

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        @BindView(R.id.muban_main_doctor_list_name)
        TextView doctor_name;
        @BindView(R.id.muban_main_doctor_list_dept)
        TextView doctor_dept;
        @BindView(R.id.muban_main_doctor_list_img)
        ImageView img_tag;
        @BindView(R.id.muban_main_doctor_list_del)
        ConstraintLayout delete;
        @BindView(R.id.muban_main_list_delete)
        Button delete_item;
        @BindView(R.id.muban_main_list_update)
        Button update_item;

        @BindView(R.id.muban_main_lsit_item_constraintlayout)
        ConstraintLayout list_item_layout;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public ConstraintLayout getList_item_layout() {
            return list_item_layout;
        }

        public void setList_item_layout(ConstraintLayout list_item_layout) {
            this.list_item_layout = list_item_layout;
        }

        public Button getDelete_itme() {
            return delete_item;
        }

        public void setDelete_itme(Button delete_item) {
            this.delete_item = delete_item;
        }

        public TextView getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(TextView doctor_name) {
            this.doctor_name = doctor_name;
        }

        public ImageView getImg_tag() {
            return img_tag;
        }

        public void setImg_tag(ImageView img_tag) {
            this.img_tag = img_tag;
        }

        public ConstraintLayout getDelete() {
            return delete;
        }

        public void setDelete(ConstraintLayout delete) {
            this.delete = delete;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.muban_main_list_delete, R.id.muban_main_list_cancel, R.id.muban_main_list_update})
        void onClick(View v) {
            switch (v.getId()) {
                case R.id.muban_main_list_delete:
                    contract.cancel(this);
                    contract.delete(this);
                    break;
                case R.id.muban_main_list_cancel:
                    contract.cancel(this);
                    break;
                case R.id.muban_main_list_update:
                    contract.cancel(this);
                    contract.update(this);
                    break;
                default:
                    break;
            }
        }
    }

}
