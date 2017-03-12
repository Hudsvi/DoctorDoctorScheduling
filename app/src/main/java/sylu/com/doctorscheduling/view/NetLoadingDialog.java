package sylu.com.doctorscheduling.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/1 9:56.
 */

public class NetLoadingDialog extends Dialog {
    private static final String TAG = NetLoadingDialog.class.getSimpleName();
    private Context context;
    private static NetLoadingDialog dialog;
    private boolean cancelable = true;

    public NetLoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public NetLoadingDialog(Context context, int theme) {
        super(context, theme);
    }


    public NetLoadingDialog build(String loadingTextHint) {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        //自定义网络加载对话框
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.netloding_dialog, null);
        dialog = new NetLoadingDialog(context, R.style.netdialog);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);//加载时禁止点击对话框外面部分
        //设置出现该对话框的Activity的Gravity属性为正中间
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        Display d = dialogWindow.getWindowManager().getDefaultDisplay();
//        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(params);

        //设置加载状态，默认加载中
        TextView loadingText = (TextView) dialog.findViewById(R.id.net_loading_dialog_textview);
        if (loadingText != null) {
            if (!TextUtils.isEmpty(loadingTextHint)) {
                loadingText.setText(loadingTextHint);
            } else {
                loadingText.setVisibility(View.GONE);
            }
        }
        //使图标旋转
        ImageView loadingImage = (ImageView) dialog.findViewById(R.id.netloading_imageView);
        Animation loadingAnim = AnimationUtils.loadAnimation(context, R.anim.loading);
        //AccelerateInterpolator acce=new AccelerateInterpolator();//加速度插入器
        LinearInterpolator lin = new LinearInterpolator();        //线性插入器
        loadingAnim.setInterpolator(lin);
        if (loadingAnim != null) {
            loadingImage.startAnimation(loadingAnim);
        }
        return dialog;
    }


    public NetLoadingDialog setLoadingImage(int res) {
        ImageView img = (ImageView) dialog.findViewById(R.id.netloading_imageView);
        if (img != null) {
            img.setImageResource(res);

        }
        return dialog;
    }

    public void dismiss() {
        try {
            if (dialog != null) {
                super.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "error:" + e);
        }
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        if (listener != null)
            super.setOnCancelListener(listener);
        else {
            cancelable = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (cancelable && dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}

