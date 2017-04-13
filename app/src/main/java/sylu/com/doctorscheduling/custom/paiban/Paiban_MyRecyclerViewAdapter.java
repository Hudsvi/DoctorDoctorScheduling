package sylu.com.doctorscheduling.custom.paiban;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.main.paiban.Details_Activity;
import sylu.com.doctorscheduling.utils.DateTimeFormatUtils;

/**
 * Created by Hudsvi on 2017/3/18 12:11.
 */

public class Paiban_MyRecyclerViewAdapter extends RecyclerView.Adapter<Paiban_MyRecyclerViewAdapter.ViewHolder> implements RecyclerView.OnClickListener{

    private final Context context;
    private  List<Doctor_Paiban_List_Item> lists;

    public Paiban_MyRecyclerViewAdapter(Context context, List<Doctor_Paiban_List_Item> lists) {
        this.context=context;
        this.lists=lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View view;
        view=LayoutInflater.from(context).inflate(R.layout.paiban_item,parent,false);
        holder=new ViewHolder(view);
        return holder;
    }
    public void setData(List<Doctor_Paiban_List_Item> list){
        this.lists=list;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doctor_Paiban_List_Item item=(Doctor_Paiban_List_Item)lists.get(position);
//        holder.time01.setText(DateTimeFormatUtils.getDatetimeInstance().getAMPMOfDay(String.valueOf(item.getTime01())));
        holder.d_name.setText(item.getName());
        holder.time01.setText(item.getTime01());
        holder.time02.setText(item.getTime02());
        holder.time11.setText(item.getTime21());
        holder.time12.setText(item.getTime22());
        holder.amount1.setText(item.getAmount1());
        holder.amount2.setText(item.getAmount2());
    }

    @Override
    public int getItemCount() {
        return (lists==null||lists.size()==0)?0:lists.size();

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, v.getId()+"Item Clicked", Toast.LENGTH_SHORT).show();
    }

    //RecyclerView中的ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.paiban_list_d_name)
        TextView d_name;
        @BindView(R.id.paiban_list_d_time1)
        TextView time01;
        @BindView(R.id.paiban_list_d_time2)
        TextView time02;
        @BindView(R.id.paiban_list_d_time12)
        TextView time11;
        @BindView(R.id.paiban_list_d_time22)
        TextView time12;
        @BindView(R.id.paiban_list_d_amount1)
        TextView amount1;
        @BindView(R.id.paiban_list_d_amount2)
        TextView amount2;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, Details_Activity.class);
                    context.startActivity(i);
                }
            });
        }

        public TextView getD_name() {
            return d_name;
        }

        public void setD_name(TextView d_name) {
            this.d_name = d_name;
        }

        public TextView getTime01() {
            return time01;
        }

        public void setTime01(TextView time01) {
            this.time01 = time01;
        }

        public TextView getTime02() {
            return time02;
        }

        public void setTime02(TextView time02) {
            this.time02 = time02;
        }

        public TextView getTime11() {
            return time11;
        }

        public void setTime11(TextView time11) {
            this.time11 = time11;
        }

        public TextView getTime12() {
            return time12;
        }

        public void setTime12(TextView time12) {
            this.time12 = time12;
        }

        public TextView getAmount1() {
            return amount1;
        }

        public void setAmount1(TextView amount1) {
            this.amount1 = amount1;
        }

        public TextView getAmount2() {
            return amount2;
        }

        public void setAmount2(TextView amount2) {
            this.amount2 = amount2;
        }
    }
}
