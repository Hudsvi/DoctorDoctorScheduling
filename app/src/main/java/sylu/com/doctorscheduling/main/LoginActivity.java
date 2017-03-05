package sylu.com.doctorscheduling.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/4 20:28.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void InitView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.signin;
    }
    public static void enter(Context context){
        context.startActivity(new Intent(context,LoginActivity.class));
    }
}
