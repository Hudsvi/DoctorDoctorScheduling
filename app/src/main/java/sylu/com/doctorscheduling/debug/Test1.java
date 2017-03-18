package sylu.com.doctorscheduling.debug;

import android.app.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.view.portrait.PortraitDialog;

/*
 * Created by Hudsvi on 2017/3/8 15:25.
 */


public class Test1 extends Activity implements View.OnClickListener {
    private PortraitDialog d;

    @BindView(R.id.tttt)
    ImageView imageView;
    @BindView(R.id.tttes)
    TextView textView;
    private Uri uri, uri2;
    private static int type = 0;
    @OnClick(R.id.tttes)
    void openDialog(){
     d.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        d=new PortraitDialog(this,R.style.touxiang_dialog,this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && type == 101) {
                    Intent crop = new Intent("com.android.camera.action.CROP");
                    crop.setDataAndType(data.getData(), "image/*");
                    crop.putExtra("scale", true);
                    crop.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(crop, 2);
                } else if (resultCode == RESULT_OK) {
                    Intent crop = new Intent("com.android.camera.action.CROP");
                    crop.setDataAndType(uri, "image/*");
                    crop.putExtra("scale", true);
                    crop.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(crop, 2);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imageView.setImageBitmap(bit);
                        type = 0;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.paizhao:
            File out = new File(Environment.getExternalStorageDirectory(), "img.jpg");
            try {
                if (out.exists())
                    out.delete();
                out.createNewFile();
            } catch (Exception e) {
            }
            uri = Uri.fromFile(out);
            Intent imgt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imgt.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(imgt, 1);
            break;
        case R.id.xiangce:
            File out2 = new File(Environment.getExternalStorageDirectory(), "img2.jpg");
            try {
                if (out2.exists())
                    out2.delete();
                out2.createNewFile();
            } catch (Exception e) {
            }
            uri = Uri.fromFile(out2);
            Intent xiang = new Intent(Intent.ACTION_GET_CONTENT);
            xiang.setType("image/*");
            xiang.putExtra("crop", true);
            xiang.putExtra("scale", true);
            xiang.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            type = 101;
            startActivityForResult(xiang, 1);
            break;
        case R.id.quxiao:
            d.dismiss();
            break;
            default:break;
    }
    }
}
/*

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.view.datetimepicker.MyDateTimePicker;

*/
/**
 * Created by liuwan on 2016/9/28.
 *//*

public class Test1 extends Activity implements View.OnClickListener {

    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private MyDateTimePicker customDatePicker1, customDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);

        selectTime = (RelativeLayout) findViewById(R.id.selectTime);
        selectTime.setOnClickListener(this);
        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);

        initDatePicker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                break;

            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(currentTime.getText().toString());
                break;
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);
        currentTime.setText(now);

        customDatePicker1 = new MyDateTimePicker(this, new MyDateTimePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", "2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(true); // 不允许循环滚动

        customDatePicker2 = new MyDateTimePicker(this, new MyDateTimePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        }, "2010-01-01 00:00","2020-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

}
*/
