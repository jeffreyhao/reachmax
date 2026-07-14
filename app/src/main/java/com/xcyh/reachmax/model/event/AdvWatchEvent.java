package com.xcyh.reachmax.model.event;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
public class AdvWatchEvent {


    public static final int UPDATE_TIME         = 100001;

    public static final int ITEM_ADV_NAME       = 100002;

    /**
     * 下钻
     */
    public static final int HOME_ITEM_CLICK     = 100003;


    /**
     * 多选确定
     */
    public static final int HOME_MULTI_SUBMIT   = 100004;

    public static final int EXTEND_5_MIN        = 100005;   // onStop-onResume 超过五分钟

    public static final int EXTEND_MID_NIGHT    = 100006;   // 跨天

    public static final int ON_SCROLL           = 100007;   // 滑动
}
