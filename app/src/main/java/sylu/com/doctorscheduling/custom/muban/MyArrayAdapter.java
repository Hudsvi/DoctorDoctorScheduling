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

public class MyArrayAdapter extends ArrayAdapter {
    private List<Doctor_List_Item> lists;
    private Context context;
    private int resourse_id;
    private ListContract contract;

    public MyArrayAdapter(Context context, int resource, List<Doctor_List_Item> lists, ListContract contract) {
        super(context, resource, lists);
        this.resourse_id = resource;
        this.context = context;
        this.contract = contract;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Doctor_List_Item info = (Doctor_List_Item) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourse_id, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (info != null) {
            holder.doctor_name.setText(info.getDoctor_name());
            holder.setName(info.getDoctor_name());
            holder.setUser_id(1234 + position);//此处模拟标识id，可将登录账号作为唯一标识
            holder.setPosition(position);
        }
        return convertView;
    }


    public class ViewHolder{

        private long user_id;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        private int position;


        private String name;
        @BindView(R.id.muban_main_doctor_list_name)
        TextView doctor_name;
        @BindView(R.id.muban_main_doctor_list_img)
        ImageView img_tag;

        @BindView(R.id.muban_main_doctor_list_del)
        ConstraintLayout delete;

        @BindView(R.id.muban_main_list_delete)
        Button delete_itme;

        @BindView(R.id.muban_main_lsit_item_constraintlayout)
        ConstraintLayout list_item_layout;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public ConstraintLayout getList_item_layout() {
            return list_item_layout;
        }

        public void setList_item_layout(ConstraintLayout list_item_layout) {
            this.list_item_layout = list_item_layout;
        }

        public Button getDelete_itme() {
            return delete_itme;
        }

        public void setDelete_itme(Button delete_itme) {
            this.delete_itme = delete_itme;
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

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.muban_main_list_delete,R.id.muban_main_list_cancel})
        void onClick(View v) {
            switch (v.getId()) {
                case R.id.muban_main_list_delete:
                    contract.delete(user_id, position,this);
                    break;
                case R.id.muban_main_list_cancel:
                    contract.cancel(this);
                    break;
                default:
                    break;
            }
        }
    }

}
