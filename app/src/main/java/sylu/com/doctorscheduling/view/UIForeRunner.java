package sylu.com.doctorscheduling.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import java.lang.ref.WeakReference;

/**
 * Created by Hudsvi on 2017/3/1 16:00.
 */

public class UIForeRunner {
    private WeakReference<Activity> context;
    private NetLoadingDialog dialog;

    public UIForeRunner(Context context){
        this.context=new WeakReference<Activity>((Activity)context);
    }
    public UIForeRunner(WeakReference<Activity> context) {
        this.context=context;
    }

    public void showNetLoadingDialog(String loadingStatus, DialogInterface.OnCancelListener listener){
        if(dialog!=null&&dialog.isShowing()){return;}
        if(dialog==null&&context.get()!=null){
            dialog=new NetLoadingDialog(context.get()).build(loadingStatus);
            dialog.setOnCancelListener(listener);
            dialog.show();
        }
    }
    public void dissmissNetLoadingDialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
    public boolean isShowing(){
        return dialog!=null&&dialog.isShowing();
    }
}
