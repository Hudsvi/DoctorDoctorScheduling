package sylu.com.doctorscheduling.internet.http;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import sylu.com.doctorscheduling.internet.ErroHandler;

/**
 * Created by Hudsvi on 2017/3/12 19:42.
 */

public abstract class HandleHttpResultFunc<T> implements Func1<HttpResult<T>,T> {

    protected WeakReference<ErroHandler> mNetErrorHandler;

    protected Subscriber mSubscriber;

/**
 * 此接口用于判断和处理错误
* */
    public HandleHttpResultFunc(ErroHandler handler, Subscriber subscriber) {
        this.mNetErrorHandler = new WeakReference<ErroHandler>(handler);
        this.mSubscriber = subscriber;
    }


    public abstract Observable getObservable(HttpService httpService);

    public Subscriber getmSubscriber() {
        return mSubscriber;
    }

    @Override
    public T call(HttpResult<T> result) {
        ErroHandler handler = mNetErrorHandler.get();
        if (result.getCode() != 0 && handler != null) {
            handler.onErro(result);
        }
        //此处写一些数据返回后的逻辑
        //
        return result.getData();
    }

}
