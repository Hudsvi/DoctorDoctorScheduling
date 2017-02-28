package sylu.com.doctorscheduling;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by Hudsvi on 2017/2/18 13:22.
 */
public abstract class BaseFragment extends Fragment implements DialogInterface.OnCancelListener{

    protected View rtView;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rtView = LayoutInflater.from(getActivity()).inflate(getLayoutView(), null);
        ButterKnife.bind(this, rtView);
        initViews(savedInstanceState);
        return rtView;
    }

    protected abstract int getLayoutView();

    protected abstract void initViews(Bundle savedInstanceState);

}

