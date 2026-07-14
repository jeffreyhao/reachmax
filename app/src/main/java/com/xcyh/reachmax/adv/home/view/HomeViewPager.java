package com.xcyh.reachmax.adv.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by haojiangfeng on 2025/1/13.
 */
public class HomeViewPager extends ViewPager {

    private boolean mCanScroll  = true;


    public HomeViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public HomeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }


    public void setCanScroll(boolean bool){
        mCanScroll = bool;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mCanScroll ? super.onInterceptTouchEvent(ev) : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mCanScroll ? super.onTouchEvent(ev) : false;
    }
}
