package sylu.com.doctorscheduling.custom;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.squareup.picasso.Picasso;
import okhttp3.OkHttpClient;
import sylu.com.doctorscheduling.utils.OkHttp3Utils;

/**
 * Created by Hudsvi on 2017/2/28 10:24.
 */

public class MyMultiDexApplication extends MultiDexApplication {
    private static MyMultiDexApplication instance;
    public static Picasso picasso;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Utils(new OkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);
        this.picasso = picasso;
    }


    public static MyMultiDexApplication getInstance() {
        return instance;
    }

    public static Picasso getPicasso() {
        return picasso;
    }


}
