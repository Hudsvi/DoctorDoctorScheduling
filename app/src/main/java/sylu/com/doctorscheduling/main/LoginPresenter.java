package sylu.com.doctorscheduling.main;

import sylu.com.doctorscheduling.internet.ProgressSubscriber;
import sylu.com.doctorscheduling.internet.SubscriberOnNextListener;
import sylu.com.doctorscheduling.internet.entity.Dept_Admin_Info;
import sylu.com.doctorscheduling.internet.http.HttpMethods;

/**
 * Created by Hudsvi on 2017/3/12 10:21.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View view;
    private SubscriberOnNextListener listener;
    public LoginPresenter(LoginContract.View view, SubscriberOnNextListener listener){
        this.listener=listener;
        this.view=view;
        view.setPresenter(this);
    }
    @Override
    public void attemptLogin() {
        if(view.inspectLoginParam()){}
        HttpMethods.getHttpMethods_instance().Dept_Admin_Login(view.getLoginParam(),
                new ProgressSubscriber<Dept_Admin_Info>(listener,view.getContext()));
    }
}
