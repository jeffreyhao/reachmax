package com.xcyh.reachmax.adv;

import android.view.View;

import com.baidu.baselibrary.util.App;
import com.baidu.baselibrary.util.sys.LogUtil;

/**
 * Created by haojiangfeng on 2025/12/10.
 */
public class AdvClickUtil {


    // 连续点击次数
    public static int sContinuousClickCount = 0;
    private static final Runnable sContinuousClickRunnable = () -> sContinuousClickCount = 0;


    public static View.OnClickListener buildContinuousClickListener(View.OnClickListener onClickListener) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sContinuousClickCount++;
                LogUtil.e("sContinuousClickCount", "sContinuousClickCount: " + sContinuousClickCount);
                App.getHandler().removeCallbacks(sContinuousClickRunnable);
                if (sContinuousClickCount >= 5) {
                    sContinuousClickCount = 0;
                    if(onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                } else {
                    App.getHandler().postDelayed(sContinuousClickRunnable, 500);
                }
            }
        };
    }

}
