package sylu.com.doctorscheduling.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import sylu.com.doctorscheduling.custom.MyApplication;

/**
 * Created by Hudsvi on 2017/2/28 10:40.
 */

public class PicassoUtils {
        private static Picasso picasso = MyApplication.getPicasso();
        private static Context context = MyApplication.getInstance();

        public static void loadImage(String url, ImageView imageView){
            loadImage(context,url,imageView);
        }

        public static void loadImage(Context context, String url, ImageView imageView){
            loadImage(context,url,imageView,0);
        }

        public static void loadImage(Context context,String url, ImageView imageView, int placeholder){
            loadImage(context,url,imageView,placeholder,0);
        }

        public static void loadImage(Context context, String url, ImageView imageView, int placeholder, int errorImage){
            picasso.with(context)
                    .load(url)
                    .placeholder(ContextCompat.getDrawable(context,placeholder))
                    .error(ContextCompat.getDrawable(context,errorImage))
                    .into(imageView);
    }
}
