package com.xcyh.reachmax.app.meta.ui.widget;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.baidu.baselibrary.util.App;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by haojiangfeng on 2025/12/12.
 */
public class LongTouchTextView extends AppCompatTextView {



    private int mLongPressDuration = 1500;

    private OnLongClickListener mSuperLongClickListener;


    private final Runnable longPressNameRunnable = new Runnable() {
        @Override
        public void run() {

            // 执行长按事件
            performSuperLongClick();

            // 震动
            vibrate();

            // 拦截父控件，阻止继续传递事件
            ViewParent parent = getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    };


    public LongTouchTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public LongTouchTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LongTouchTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /** @noinspection deprecation*/
    private void init(){
        setOnLongClickListener(null);
    }

    /**
     * @deprecated use {@link #setSuperLongClickListener(OnLongClickListener)} instead
     */
    @Deprecated
    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }


    public void setSuperLongClickListener(OnLongClickListener listener){
        this.mSuperLongClickListener = listener;
    }

    public void setLongPressDuration(int duration){
        mLongPressDuration = duration;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startLongTouchTimer();
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                cancelLongTouchTimer();

                // 允许父控件以后再次拦截
                ViewParent group = getParent();
                if (group != null) {
                    group.requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 开始计时
     */
    private void startLongTouchTimer(){
        App.postDelayed(longPressNameRunnable, mLongPressDuration);
    }

    /**
     * 取消计时
     */
    private void cancelLongTouchTimer(){
        App.getHandler().removeCallbacks(longPressNameRunnable);
    }

    private void performSuperLongClick(){
        if(mSuperLongClickListener != null) {
            mSuperLongClickListener.onLongClick(this);
        }
    }

    private void vibrate() {
        try {
            Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            if(vibrator != null) {
                // 小米手机测试：开启触摸震动反馈后，这里才生效。
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
