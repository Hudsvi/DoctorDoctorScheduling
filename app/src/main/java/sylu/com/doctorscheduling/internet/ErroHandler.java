package sylu.com.doctorscheduling.internet;

import sylu.com.doctorscheduling.internet.http.HttpResult;

/**
 * Created by Hudsvi on 2017/3/8 9:24.
 */

public interface ErroHandler {
    void onErro(HttpResult result);
}
