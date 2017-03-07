package sylu.com.doctorscheduling.main;

import android.os.Bundle;

import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/5 19:17.
 */

public class ForgotPwdActivity extends BaseActivity {
    @Override
    protected void InitView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.pwd_found;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
