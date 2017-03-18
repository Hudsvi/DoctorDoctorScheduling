package sylu.com.doctorscheduling;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import butterknife.BindViews;
import butterknife.ButterKnife;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.utils.manager.ActivityManager;
import sylu.com.doctorscheduling.utils.manager.UIManager;
import sylu.com.doctorscheduling.view.UIForeRunner;

/**
 * Created by Administrator on 2017/1/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements DialogInterface.OnCancelListener {
    protected UIForeRunner foreRunner;
    private View rootView;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = getLayoutView();
        rootView = LayoutInflater.from(this).inflate(layout, null);
        setContentView(rootView);
        ButterKnife.bind(this);
        InitView(savedInstanceState);
        ActivityManager.getInstance().AddIntoManager(this);
    }

    protected abstract void InitView(Bundle savedInstanceState);

    protected abstract int getLayoutView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().DropFromManager(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.layout_stay, R.anim.layout_translate_out);
    }

    public void CheckLoginStatus() {

    }

    public void showNetLoadingDialog() {
        showNetLoadingDialog("");
    }

    public void showNetLoadingDialog(String loadingState) {
        foreRunner=new UIForeRunner(new WeakReference<Activity>(this));
        foreRunner.showNetLoadingDialog(loadingState,this);
    }
    public void dissmissNetLoadingDialog(){
        if(foreRunner!=null&&foreRunner.isShowing()){
            foreRunner.dissmissNetLoadingDialog();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInputMethod(ev, view)) {
                hideInputMethod(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideInputMethod(IBinder windowToken) {
        if (windowToken != null) {
            InputMethodManager inputManage = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManage.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    private boolean isShouldHideInputMethod(MotionEvent ev, View view) {
        if (view != null && (view instanceof EditText)) {
            int[] location = {0, 0};
            int s_width = location[0];
            int s_height = location[1];
            view.getLocationInWindow(location);
            int e_width = s_width + view.getWidth() + (int) UIManager.dipToPixels(this, 32.0f);
            int e_height = s_height + view.getHeight();
            if ((ev.getX() > s_width && ev.getX() < e_width && ev.getY() > s_height && ev.getY() < e_height)) {
                return false;
            } else
                return true;
        } else return false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
