package sylu.com.doctorscheduling.main;

import android.os.Bundle;

import sylu.com.doctorscheduling.BaseActivity;
import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/5 18:30.
 */

public class SignupActivity extends BaseActivity {
    @Override
    protected void InitView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.signup;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
