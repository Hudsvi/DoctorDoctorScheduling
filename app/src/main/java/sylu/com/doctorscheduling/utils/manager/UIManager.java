package sylu.com.doctorscheduling.utils.manager;

import android.content.Context;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Hudsvi on 2017/2/28 11:29.
 */

public class UIManager {


    public static boolean clearAnimation(View view) {
        if (view == null || view.getAnimation() == null) {
            return false;
        }
        view.clearAnimation();
        return true;
    }

    public static float dipToPixels(Context context, float dip) {
//        final float scale=new DisplayMetrics().density;
        final float scale = context.getResources().getDisplayMetrics().density;
        return dip * scale;
    }

    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (dm == null) ? 0 : dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (dm == null) ? 0 : dm.heightPixels;
    }

    public static boolean isInUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
