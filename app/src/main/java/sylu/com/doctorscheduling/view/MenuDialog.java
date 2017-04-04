package sylu.com.doctorscheduling.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.utils.manager.UIManager;

/**
 * Created by Hudsvi on 2017/4/3 16:12.
 */

public class MenuDialog extends Dialog {
    private final Context context;
    private View.OnClickListener listener;
    private View rootview;
    private LinearLayout item1,item2,item3,item4,item5,item6;
    private ImageView img1,img2,img3,img4,img5,img6;
    private String stv;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    private LinearLayout back;
    public MenuDialog(Context context,View.OnClickListener listener) {
        super(context,R.style.bar_menu);
        this.context=context;
        this.listener=listener;
        initViews();//
    }
//
    public void setItemIMGandTV1(int drawable,String tv){
        if(item1.getVisibility()==View.GONE){
            item1.setVisibility(View.VISIBLE);
        }
        img1.setImageDrawable(getContext().getResources().getDrawable(drawable));
        tv1.setText(tv);
    }
    public void setItemIMGandTV2(int drawable,String tv){
        if(item2.getVisibility()==View.GONE){
            item2.setVisibility(View.VISIBLE);
        }
        img2.setImageDrawable(getContext().getResources().getDrawable(drawable));
        tv2.setText(tv);
    }
    public void setItemIMGandTV3(int drawable,String tv){
        if(item3.getVisibility()==View.GONE){
            item3.setVisibility(View.VISIBLE);
        }
        img3.setImageDrawable(getContext().getResources().getDrawable(drawable));
        tv3.setText(tv);
    }
    public void setItemIMGandTV4(int drawable,String tv){
        if(item4.getVisibility()==View.GONE){
            item4.setVisibility(View.VISIBLE);
        }
        img4.setImageDrawable(getContext().getResources().getDrawable(drawable));
        tv4.setText(tv);
    }public void setItemIMGandTV5(int drawable,String tv){
        if(item5.getVisibility()==View.GONE){
            item5.setVisibility(View.VISIBLE);
        }
        img5.setImageDrawable(getContext().getResources().getDrawable(drawable));
        tv5.setText(tv);
    }public void setItemIMGandTV6(int drawable,String tv){
        if(item6.getVisibility()==View.GONE){
            item6.setVisibility(View.VISIBLE);
        }
        img6.setImageDrawable(getContext().getResources().getDrawable(drawable));
        tv6.setText(tv);
    }

    private void initViews() {
        rootview= LayoutInflater.from(context).inflate(R.layout.menu_main_list,null);
        setContentView(rootview);
        Window dialogWindow=this.getWindow();
        WindowManager.LayoutParams lp=dialogWindow.getAttributes();
        lp.y= (int)UIManager.dipToPixels(context,73.0f);
        lp.width=(int)UIManager.dipToPixels(context,120.0f);
        lp.height=lp.WRAP_CONTENT;
        dialogWindow.setGravity(Gravity.END|Gravity.TOP);
        dialogWindow.setAttributes(lp);
        tv1=(TextView)this.findViewById(R.id.menu_main_item1_tv); //------------tv1
        tv2=(TextView)this.findViewById(R.id.menu_main_item2_tv);
        tv3=(TextView)this.findViewById(R.id.menu_main_item3_tv);
        tv4=(TextView)this.findViewById(R.id.menu_main_item4_tv);
        tv5=(TextView)this.findViewById(R.id.menu_main_item5_tv);
        tv6=(TextView)this.findViewById(R.id.menu_main_item6_tv);//----------tv6
        img1=(ImageView)this.findViewById(R.id.main_menu_item1_img);//-----------img1
        img2=(ImageView)this.findViewById(R.id.main_menu_item2_img);
        img3=(ImageView)this.findViewById(R.id.main_menu_item3_img);
        img4=(ImageView)this.findViewById(R.id.main_menu_item4_img);
        img5=(ImageView)this.findViewById(R.id.main_menu_item5_img);
        img6=(ImageView)this.findViewById(R.id.main_menu_item6_img);//---------img6
        this.item1=(LinearLayout)this.findViewById(R.id.menu_main_item1_ll);//--------linear1
        this.item2=(LinearLayout)this.findViewById(R.id.menu_main_item2_ll);
        this.item3=(LinearLayout)this.findViewById(R.id.menu_main_item3_ll);
        this.item4=(LinearLayout)this.findViewById(R.id.menu_main_item4_ll);
        this.item5=(LinearLayout)this.findViewById(R.id.menu_main_item5_ll);
        this.item6=(LinearLayout)this.findViewById(R.id.menu_main_item6_ll);//-------linear6
        this.back=(LinearLayout)this.findViewById(R.id.main_menu_back_linearlayout);
        item1.setOnClickListener(listener);
        item2.setOnClickListener(listener);
        item3.setOnClickListener(listener);
        item4.setOnClickListener(listener);
        item5.setOnClickListener(listener);
        item6.setOnClickListener(listener);
        back.setOnClickListener(listener);
    }
}
