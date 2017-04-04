package sylu.com.doctorscheduling.internet;

import rx.Subscriber;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.utils.LogUtils;
import sylu.com.doctorscheduling.view.UIForeRunner;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by Hudsvi on 2017/3/12 10:35.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements DialogInterface.OnCancelListener {
    private SubscriberOnNextListener listener;
    private UIForeRunner foreRunner;
    private Context context;
    private boolean showDialog;

    private String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    private String loading;
    public ProgressSubscriber(SubscriberOnNextListener listener, Context context) {
        this(listener, context, true);

    }

    public ProgressSubscriber(SubscriberOnNextListener listener, Context context, boolean showDialog) {
        this.listener = listener;
        this.context = context;
        this.showDialog = showDialog;
        foreRunner = new UIForeRunner(context);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showNetDialog(getLoading());

    }

    @Override
    public void onCompleted() {
        dissmissDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context,context.getString(R.string.net_connecting_erro),Toast.LENGTH_SHORT ).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context,context.getString(R.string.net_connecting_erro),Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText(context, "无法连接到服务器^_^"+e.toString(), Toast.LENGTH_SHORT).show();
            LogUtils.d(this.getClass().getSimpleName(),e.getCause() + " :" + e.getMessage());
        }
    }

    @Override
    public void onNext(Object o) {
        if (listener != null) {
            dissmissDialog();
            listener.onNext(o);
        }
    }

    public void showNetDialog(String netinfo) {
        if (showDialog)
        foreRunner.showNetLoadingDialog(netinfo, this);
    }

    public void dissmissDialog() {
        if (showDialog && foreRunner != null && foreRunner.isShowing()) {
            foreRunner.dissmissNetLoadingDialog();
        }

    }
}
