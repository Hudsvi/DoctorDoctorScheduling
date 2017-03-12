package sylu.com.doctorscheduling.view.portrait;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import sylu.com.doctorscheduling.R;


/**
 * Created by Hudsvi on 2017/3/11 16:52.
 */

public class PortraitDialog extends Dialog{
    private TextView paizhao, xiangce,quxiao;
    private View.OnClickListener listener;
    public PortraitDialog(Context context, int themeResId, View.OnClickListener listener) {
        super(context, themeResId);
        this.listener=listener;
        this.setContentView(R.layout.portrait);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        Display di = dialogWindow.getWindowManager().getDefaultDisplay();
//        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        dialogWindow.setGravity(Gravity.BOTTOM);
        params.y = 16;
        dialogWindow.setAttributes(params);
        paizhao=(TextView)this.findViewById(R.id.paizhao);
        xiangce=(TextView)this.findViewById(R.id.xiangce);
        paizhao.setOnClickListener(listener);
        xiangce.setOnClickListener(listener);
        quxiao.setOnClickListener(listener);
    }



}
