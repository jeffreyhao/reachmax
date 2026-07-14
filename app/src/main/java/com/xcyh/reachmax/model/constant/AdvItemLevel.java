package com.xcyh.reachmax.model.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 广告纬度：账户、系列、组、计划。
 *
 * Created by haojiangfeng on 2024/11/21.
 */
@IntDef({
        AdvItemLevel.ADV_ACCOUNT,
        AdvItemLevel.ADV_SERIAL,
        AdvItemLevel.ADV_GROUP,
        AdvItemLevel.ADV_PLAN
})
@Retention(RetentionPolicy.SOURCE)
public @interface AdvItemLevel {

    /**
     * 广告账户
     */
    int ADV_ACCOUNT     = 4;

    /**
     * 广告系列
     */
    int ADV_SERIAL      = 3;

    /**
     * 广告组
     */
    int ADV_GROUP       = 2;

    /**
     * 广告计划
     */
    int ADV_PLAN        = 1;

}
