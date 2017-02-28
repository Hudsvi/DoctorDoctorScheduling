package sylu.com.doctorscheduling;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.BindViews;
import butterknife.ButterKnife;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.utils.manager.ActivityManager;

/**
 * Created by Administrator on 2017/1/27.
 */

public abstract class BaseActivity extends AppCompatActivity implements DialogInterface.OnCancelListener {
    private View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout= getLayoutView();
        rootView= LayoutInflater.from(this).inflate(layout,null);
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
        overridePendingTransition(R.anim.layout_stay, R.anim.layout_fade_out);
    }
    public void CheckLoginStatus(){
        
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }
}
