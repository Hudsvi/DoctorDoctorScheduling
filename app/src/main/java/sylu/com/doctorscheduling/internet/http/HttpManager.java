package sylu.com.doctorscheduling.internet.http;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sylu.com.doctorscheduling.custom.MyMultiDexApplication;
import sylu.com.doctorscheduling.custom.MySharedPreferences;
import sylu.com.doctorscheduling.internet.cookie.CookieInterceptor;
import sylu.com.doctorscheduling.utils.LogUtils;
import sylu.com.doctorscheduling.utils.NetUtils;

import static sylu.com.doctorscheduling.constants.Constants.BASE_URL;
import static sylu.com.doctorscheduling.constants.Constants.TIME_OUT;
import static sylu.com.doctorscheduling.constants.Constants.TIME_OUT_READ;

/**
 * Created by Hudsvi on 2017/3/9 20:01.
 */

/**
 * retrofit+rxjava+okhttp的使用-----------------------------------------
 * retrofit+rxjava+okhttp框架的特色和优点：retrofit框架的底层实现是okhttp3，okhttp3框架对httpurlconnection,
 * httpclient等优秀的网络访问接口进行了封装，不但使代码简洁易懂，还实现了对网络访问的异步处理，
 * 加上rxjava框架的使用，异步处理变得更加高效和简单。
 * okhttpClient使用方式：通过Builder来获得一个builder实例。在这里可以初始化超时响应和
 * 读取的时间，请求失败后是否继续发送请求，添加自定义拦截器、协议类型以及自定义缓存等。
 * retrofit使用方式：添加客户端，添加转换器（这里选择的是GSON，相比JSONOject和jackson等解析JSON的方式
 * ，GSON显得十分简单，通过序列化直接将JSON映射为相应的对象 ），添加回调适配器（这里选择的是rxjava，
 * 异步处理，以及回调进行UI操作），添加URL,然后调用create方法获得一个可以与服务端API交互的接口。
 * 注意：在使用此接口时，尽量使用单例模式，否则多对象容易造成内存吃不消等情况。
 */

public class HttpManager {
    private Retrofit retrofit;
    private HttpService httpService;
    private static final String VERSION = "1.0";

    private HttpManager() {
        Cache cache = new Cache(getCachedDir(), 10 * 1024 * 1024);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(getLogInterceptor())
                .addInterceptor(getCacheInterceptor())
                .addInterceptor(new CookieInterceptor(MyMultiDexApplication.getInstance()))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .cache(cache);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    public static HttpManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Interceptor getCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                if (!NetUtils.isNetworkAvailable(MyMultiDexApplication.getInstance())) {
                    builder = builder.cacheControl(CacheControl.FORCE_CACHE);
                }
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Accept", "*/*");
                String token = MySharedPreferences.getInstance(MyMultiDexApplication.getInstance()).getDeviceID();
                if (token != null)
                    builder.addHeader("Courier-Token", token);
                Response response = chain.proceed(builder.build());
                    int maxStale = 30*60 * 60 * 24 ;//允许最大的有效期限为一个月
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();

                return response;
            }
        };
    }

    private Interceptor getLogInterceptor() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.d(this.getClass().getSimpleName(),message);

            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

    public HttpService getHttpService() {
        return httpService;
    }
    /**
    * 注意：“public < T >”中'< T >'的作用是为了避免将一个已知对象传入一个未知对象时报错，<T>不是返回类型。
    *除了观察者在UI线程上，被观察者对象和解除订阅的操作皆在IO线程上进行。
    * */
    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public void doHttpDeal(HandleHttpResultFunc basePar) {
        basePar.getObservable(httpService)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(basePar)
                .subscribe(basePar.getmSubscriber());
    }

    private File getCachedDir() {
        return MyMultiDexApplication.getInstance().getCacheDir();
    }

    private static class SingletonHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }
}
