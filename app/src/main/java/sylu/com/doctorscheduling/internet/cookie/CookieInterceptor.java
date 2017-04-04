package sylu.com.doctorscheduling.internet.cookie;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sylu.com.doctorscheduling.constants.Constants;
import sylu.com.doctorscheduling.custom.MySharedPreferences;

/**
 * Created by Hudsvi on 2017/3/9 20:20.
 */

public class CookieInterceptor implements Interceptor {
    private Context context;
    public CookieInterceptor(Context context){
        super();
        this.context=context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder=chain.request().newBuilder();
        String cookies= MySharedPreferences.getInstance(context).getStringValue(Constants.COOKIE);
        if(!cookies.isEmpty()){
            builder.addHeader("APPCookie",cookies);
        }
        return chain.proceed(builder.build());
    }
}
