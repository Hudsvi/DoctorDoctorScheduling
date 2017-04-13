package sylu.com.doctorscheduling.fragment.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sylu.com.doctorscheduling.BaseFragment;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.main.LoginActivity;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Personal_Fragment extends BaseFragment {


    @BindView(R.id.person_portrait)
    ImageView personPortrait;
    @BindView(R.id.personal_constraintLayout)
    ConstraintLayout personalConstraintLayout;
    @BindView(R.id.person_exit)
    Button personExit;

    @Override
    protected int getLayoutView() {
        return R.layout.personal;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }


    @OnClick({R.id.person_portrait, R.id.person_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_portrait:

                break;
            case R.id.person_exit:
                Intent i=new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                break;
        }
    }

}
