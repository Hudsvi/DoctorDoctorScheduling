package sylu.com.doctorscheduling.view;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by Hudsvi on 2017/3/1 16:00.
 */

public class UIForeRunner {
    private WeakReference<Activity> context;
    private NetLoadingDialog dialog;

    public UIForeRunner(WeakReference<Activity> context) {
        this.context=context;
    }
    //不使用弱引用的构造器
    public UIForeRunner(Context context){
        this.context=new WeakReference<Activity>((Activity)context);
    }
    public void

}
