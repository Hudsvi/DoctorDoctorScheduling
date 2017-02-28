package sylu.com.doctorscheduling.utils;

import android.content.Context;
import android.net.Uri;
import android.os.StatFs;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by Hudsvi on 2017/2/27 21:50.
 */
public class OkHttp3Utils implements Downloader {
    private final Call.Factory client;
    private final Cache cache;
    public static final String PICASSO_CACHE = "picasso_cache";
    private static final int MIN_DISK_CACHE = 5* 1024 * 1024;
    private static final int MAX_DISK_CACHE = 50 * 1024 * 1024;


    public OkHttp3Utils(Context context) {
        this(makeCacheDir(context));
    }

    public OkHttp3Utils(File cacheDir) {
        this(cacheDir, calculateCache(cacheDir));
    }

    public OkHttp3Utils(File cacheDir, long maxSize) {
        this(mOkHttpClient(cacheDir, maxSize));
    }

    public OkHttp3Utils(OkHttpClient client) {
        this.client = client;
        this.cache = client.cache();
    }

    public OkHttp3Utils(Call.Factory client) {
        this.client = client;
        this.cache = null;
    }

    //创建一个app下的cache文件夹
    private static File makeCacheDir(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!cache.exists()) {
            cache.mkdir();
        }
        return cache;
    }

    //创建okhttp3默认的cache
    private static OkHttpClient mOkHttpClient(File cacheDir, long maxSize) {
        return new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, maxSize))
                .build();
    }

    //计算合适的cache大小
    private static long calculateCache(File dir) {
        long size = MIN_DISK_CACHE;
        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());//取得文件系统的完整信息，
            //并计算出可用空间
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();//可用大小=可用块总数×块大小
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }
        return Math.max(Math.min(size, MAX_DISK_CACHE), MIN_DISK_CACHE);
    }

    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        CacheControl cacheControl = null;
        if (networkPolicy != 0) {
            //未连接网络时，强制使用内存中的旧cache
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE;
            } else {
                //存在网络时，如果不能够从磁盘中读取cache,那么就调用无缓存的控制器
                CacheControl.Builder builder = new CacheControl.Builder();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                //存在网络时，如果不能向磁盘中写入cache,那么就调用无储存的控制器
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
                cacheControl = builder.build();
            }
        }
        Request.Builder builder = new Request.Builder().url(uri.toString());
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }
        //客户端调用自定义cache的request请求
        okhttp3.Response response = client.newCall(builder.build()).execute();
        int responseCode = response.code();
        if (responseCode >= 300) {
            response.body().close();
            throw new ResponseException(responseCode + " " + response.message(), networkPolicy, responseCode);
        }

        boolean fromCache = response.cacheResponse() != null;

        ResponseBody responseBody = response.body();

        return new Response(responseBody.byteStream(), fromCache, responseBody.contentLength());
    }

    @Override
    public void shutdown() {
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException ignored) {
            }
        }
    }
}