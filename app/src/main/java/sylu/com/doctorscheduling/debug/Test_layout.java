package sylu.com.doctorscheduling.debug;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/3/11 11:33.
 */

public class Test_layout extends Activity {
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
        startActivity(new Intent(Test_layout.this,Test_Layout2.class));
    }
}
