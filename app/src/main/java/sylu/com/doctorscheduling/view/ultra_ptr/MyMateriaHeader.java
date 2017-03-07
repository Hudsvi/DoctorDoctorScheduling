package sylu.com.doctorscheduling.view.ultra_ptr;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Hudsvi on 2017/3/6 17:49.
 */
//只显示刷新的ProgressBar
public class MyMateriaHeader extends MaterialHeader {
    public MyMateriaHeader(Context context) {
        super(context, null);
    }
    public MyMateriaHeader(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }
    public MyMateriaHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
