package com.xcyh.reachmax.model.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * 广告系列、组、计划 的 开关状态
 *
 *
 * Created by haojiangfeng on 2024/12/13.
 */
@StringDef({
        AdvActionState.ACTIVE,
        AdvActionState.PAUSED,
        AdvActionState.CLOSE,
        AdvActionState.CHANGE_DAILY_BUDGET
})
@Retention(RetentionPolicy.SOURCE)
public @interface AdvActionState {


    String ACTIVE           = "ACTIVE";
    String PAUSED           = "PAUSED";

    /**
     *  删除和未归档，都归为 删除状态
     */
    String CLOSE           = "CLOSE";

    /**  预算修改定时任务  **/
    String CHANGE_DAILY_BUDGET = "CHANGE_DAILY_BUDGET";
}
