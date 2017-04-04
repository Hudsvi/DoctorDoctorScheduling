package sylu.com.doctorscheduling.fragment.fuhe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sylu.com.doctorscheduling.BaseFragment;
import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Fuhe_Fragment extends BaseFragment  {
/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fuhe,container,false);
        return view;
    }
*/

    @Override
    protected int getLayoutView() {
        return R.layout.fuhe_doctor_lists;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
