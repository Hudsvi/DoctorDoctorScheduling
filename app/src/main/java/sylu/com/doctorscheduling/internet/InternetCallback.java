package sylu.com.doctorscheduling.internet;

/**
 * Created by Hudsvi on 2017/3/8 9:23.
 */

public interface InternetCallback<T> extends ErroHandler{
    void onRequestReceived(T t);
}
