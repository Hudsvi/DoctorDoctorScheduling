package sylu.com.doctorscheduling.fragment.yuyue;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sylu.com.doctorscheduling.R;

/**
 * Created by Hudsvi on 2017/2/17 17:21.
 */

public class Yuyue_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.yuyue,container,false);
        return view;
    }
}