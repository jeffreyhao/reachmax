package com.xcyh.reachmax.model.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * 排序规则
 *
 * Created by haojiangfeng on 2024/11/22.
 */
@StringDef({
        AdvRankRule.DEFAULT,
        AdvRankRule.ASC,
        AdvRankRule.DESC
})
@Retention(RetentionPolicy.SOURCE)
public @interface AdvRankRule {


    /**
     * 升序
     */
    String DEFAULT      = "";

    /**
     * 升序
     */
    String ASC          = "asc";

    /**
     * 降序
     */
    String DESC         = "desc";


}

