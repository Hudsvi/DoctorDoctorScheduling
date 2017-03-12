package sylu.com.doctorscheduling.internet;

/**
 * Created by Hudsvi on 2017/3/8 9:17.
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    class MyErroHandlerOnNextListener<T> implements SubscriberOnNextListener<T> {
    InternetCallback<T> callback;
        public MyErroHandlerOnNextListener(InternetCallback<T> callback){
    this.callback=callback;
    }
        @Override
        public void onNext(T t) {
            if(callback!=null)
                callback.onRequestReceived(t);
        }
        public void OnErro(Throwable e){
            if(callback!=null){
                callback.onErro(null);
            }
        }
    }
}
