package sylu.com.doctorscheduling.debug;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
