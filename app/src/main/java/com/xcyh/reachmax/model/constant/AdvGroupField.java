package com.xcyh.reachmax.model.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

/**
 * 代表广告纬度的请求常量，与 {@link AdvItemLevel} 相对应
 *
 * Created by haojiangfeng on 2024/12/13.
 */
@StringDef({
        AdvGroupField.AD_ID,
        AdvGroupField.ADSET_ID,
        AdvGroupField.CAMPAIGN_ID,
        AdvGroupField.ACCOUNT_ID
})
@Retention(RetentionPolicy.SOURCE)
public @interface AdvGroupField {

    /**
     * 广告账户
     */
    String ACCOUNT_ID       = "launch_ad_account_id";

    /**
     * 广告系列
     */
    String CAMPAIGN_ID      = "campaign_id";

    /**
     * 广告组
     */
    String ADSET_ID         = "adset_id";

    /**
     * 广告计划
     */
    String AD_ID            = "ad_id";


}
