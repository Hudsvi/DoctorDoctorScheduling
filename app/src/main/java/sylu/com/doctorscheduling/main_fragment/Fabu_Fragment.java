package sylu.com.doctorscheduling.main_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Fabu_Fragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fabu,container,false);
        return view;
    }
}
