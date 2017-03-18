package sylu.com.doctorscheduling;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import sylu.com.doctorscheduling.view.UIForeRunner;

/**
 * Created by Hudsvi on 2017/2/18 13:22.
 */
public abstract class BaseFragment extends android.app.Fragment implements DialogInterface.OnCancelListener{
    protected UIForeRunner foreRunner;
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
        rtView = LayoutInflater.from(getActivity()).inflate(getLayoutView(),container,false);
        ButterKnife.bind(this, rtView);
        initViews(savedInstanceState);
        return rtView;
    }

    protected abstract int getLayoutView();

    public Context getmContext() {
        return mContext;
    }

    protected abstract void initViews(Bundle savedInstanceState);
    public void showNetLoadingDialog() {
        showNetLoadingDialog("");
    }

    public void showNetLoadingDialog(String loadingState) {
        foreRunner=new UIForeRunner(new WeakReference<Activity>(getActivity()));
        foreRunner.showNetLoadingDialog(loadingState,this);
    }
    public void dissmissNetLoadingDialog(){
        if(foreRunner!=null&&foreRunner.isShowing()){
            foreRunner.dissmissNetLoadingDialog();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        dialog.dismiss();
    }
}

