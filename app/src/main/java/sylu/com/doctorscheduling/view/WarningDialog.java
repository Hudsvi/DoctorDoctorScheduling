package sylu.com.doctorscheduling.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.utils.manager.UIManager;

/**
 * Created by Hudsvi on 2017/3/18 20:55.
 */

public class WarningDialog extends Dialog {

    private Context context;
    private View rootView;
    private View.OnClickListener listener;
    private TextView title;
    private TextView message;
    private TextView back;
    private TextView confirm;

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
    public WarningDialog(Context context, View.OnClickListener listener) {
        super(context,R.style.time_dialog);
        this.context = context;
        this.listener = listener;
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(context).inflate(R.layout.warning_dialog, null);
        this.setContentView(rootView);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width=(int) UIManager.dipToPixels(context,300.0f);
        lp.height=lp.WRAP_CONTENT;
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        this.title = (TextView) this.findViewById(R.id.warning_dialog_title);
        this.message = (TextView) this.findViewById(R.id.warning_dialog_content);
        this.back = (TextView) this.findViewById(R.id.warning_dialog_back);
        this.confirm = (TextView) this.findViewById(R.id.warning_dialog_confirm);
        this.back.setOnClickListener(listener);
        this.confirm.setOnClickListener(listener);

    }
}
