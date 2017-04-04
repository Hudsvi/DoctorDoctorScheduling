package sylu.com.doctorscheduling.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import sylu.com.doctorscheduling.R;
import sylu.com.doctorscheduling.constants.Constants;

/**
 * Created by Hudsvi on 2017/4/1 19:48.
 */

public class MyPtrHeader extends FrameLayout implements PtrUIHandler {
    private String ptr_pull, ptr_release, ptr_refresh, ptr_complete, ptr_failure;
    private String ptr_last_time;
    private ViewGroup header;
    private Animation anim;
    private ImageView rotate;//---------旋转
    private ImageView arrow;//-------------刷新箭头
    private TextView title, time;//-----------刷新状态,最后时间
    private Animation anim_release;

    public MyPtrHeader(Context context) {
        this(context, null);
    }

    public MyPtrHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        /*anim_pull = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim_pull.setInterpolator(new LinearInterpolator());
        anim_pull.setDuration(150);
        anim_pull.setFillAfter(true);*/

        anim_release = AnimationUtils.loadAnimation(getContext(), R.anim.ptr_arrow);
        anim_release.setRepeatCount(1);
        anim_release.setInterpolator(new AccelerateInterpolator());
        anim_release.setDuration(150);
        anim_release.setFillAfter(true);
        ptr_pull = getResources().getString(R.string.ptr_pull);
        ptr_release = getResources().getString(R.string.ptr_release);
        ptr_refresh = getResources().getString(R.string.ptr_loading);
        ptr_complete = getResources().getString(R.string.ptr_complete);//---------刷新完成
        ptr_failure = getResources().getString(R.string.ptr_failure);//---------刷新失败
        ptr_last_time = MySharedPreferences.getInstance(context).getStringValue(Constants.PTR_UPDATE_TIME);
        //初始化控件
        header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.ptr_header, this, true);
        rotate = (ImageView) header.findViewById(R.id.ptr_header_rotate);
        arrow = (ImageView) header.findViewById(R.id.ptr_header_arrow);
        time = (TextView) header.findViewById(R.id.ptr_header_update_time);
        title = (TextView) header.findViewById(R.id.ptr_header_title);
//        time.setText(ptr_last_time);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        if (time.getVisibility() == VISIBLE)
            time.setVisibility(GONE);
        if (arrow.getVisibility() == VISIBLE)
            arrow.setVisibility(GONE);
        if (rotate.getVisibility() == VISIBLE)
            rotate.setVisibility(GONE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (title.getVisibility() == GONE)
            title.setVisibility(VISIBLE);
        title.setText(ptr_pull);
        if (time.getVisibility() == GONE)
            time.setVisibility(VISIBLE);
        if (arrow.getVisibility() == GONE)
            arrow.setVisibility(VISIBLE);
        if (rotate.getVisibility() == View.VISIBLE) {
            rotate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        title.setText(ptr_refresh);
        if (arrow.getVisibility() == VISIBLE) {
            arrow.clearAnimation();
            arrow.setVisibility(View.GONE);
        }
        if (rotate.getVisibility() == GONE) {
            rotate.setVisibility(View.VISIBLE);
        }
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
            anim.setInterpolator(new LinearInterpolator());
            rotate.startAnimation(anim);

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        if (rotate.getAnimation() != null) {
            rotate.clearAnimation();
        }
        rotate.setVisibility(View.GONE);
        title.setText(ptr_complete);

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int lastPos = ptrIndicator.getLastPosY();
        int currentPos = ptrIndicator.getCurrentPosY();
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                title.setText(ptr_pull);
                arrow.clearAnimation();
            }

        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                title.setText(ptr_release);
                arrow.startAnimation(anim_release);
            }
        }
    }
}
