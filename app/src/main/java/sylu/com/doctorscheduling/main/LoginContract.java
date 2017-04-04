package sylu.com.doctorscheduling.main;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import sylu.com.doctorscheduling.BaseView;
import sylu.com.doctorscheduling.internet.LoginRequest;

/**
 * Created by Hudsvi on 2017/3/5 14:04.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        boolean inspectLoginParam();

        LoginRequest getLoginParam();

        void clearEditText(EditText text);
        Activity getActi();
        Context getContext();
    }

    interface Presenter{

        void attemptLogin();
    }
}
