package sylu.com.doctorscheduling.debug;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.observables.AsyncOnSubscribe;

/**
 * Created by Hudsvi on 2017/2/18 22:26.
 */

public class Test extends Activity {
    Observable<List<String>> query(String text) {
        return null;
    }
    private String admin_name;//登录者的昵称
    private String[] managed_departments;//记录科室管理员或者门诊办负责人所管理的科室。
    private int login_identifies;//判断登录者身份
    private boolean departments_bound;//判断是否绑定相应科室

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Handler h=new Handler();
        observable.subscribe(subscriber);
        int i=10;
        Observable.just("test1").map(s -> s + "_ok")
                .map(s -> s.equals("sdad"))
                .subscribe(s -> System.out.println(s));
                query("sdsa")
                        .map(strings -> i-strings.size())
                        .take(10)
                        .filter(s ->s==1234500)
                        .subscribe(s ->System.out.println());
    }

    Observable<String> observable = Observable.just("ss");

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onNext(String s) {

        }
    };
}
