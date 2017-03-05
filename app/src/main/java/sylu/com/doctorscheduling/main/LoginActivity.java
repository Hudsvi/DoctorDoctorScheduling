package sylu.com.doctorscheduling.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/4 20:28.
 */

public class LoginActivity extends BaseActivity {
    private Context con;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
    }

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
