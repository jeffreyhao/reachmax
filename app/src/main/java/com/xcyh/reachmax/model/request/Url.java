package com.xcyh.reachmax.model.request;

import com.xcyh.reachmax.model.constant.AdvItemLevel;

/**
 * Created by haojiangfeng on 2024/12/12.
 */
public class Url {


    public static String BASE_URL                  = "https://launch.novelsbd.com/";


    /** 登录接口 **/
    public static String API_LOGIN;

    /** 报表 **/
    public static String API_ADV_REPORT;

    /** 负责人 **/
    public static String API_LAUNCH_LIST;



    /** 广告账户 搜索查询 **/
    public static String API_ADV_ACCOUNT;

    /** 广告系列 搜索查询 **/
    public static String API_ADV_SERIAL;

    /** 广告组 搜索查询 **/
    public static String API_ADV_GROUP;

    /** 广告 搜索查询 **/
    public static String API_ADV_PLAN;



    /** 广告系列 状态开关和预算 **/
    public static String API_STATE_SERIAL;

    /** 广告组 开关 **/
    public static String API_STATE_GROUP;

    /** 广告 开关 **/
    public static String API_STATE_ADV;



    /** 添加广告系列任务 **/
    public static String API_TASK_SERIAL;

    /** 添加广告组任务 **/
    public static String API_TASK_GROUP;

    /** 添加广告任务 **/
    public static String API_TASK_ADV;

    /** 修改任务 **/
    public static String API_TASK_MODIFY;

    /** 定时任务列表 **/
    public static String API_TASK_LIST;


    static {
        reset();
    }



    public static void reset(){

        /** 登录接口 **/
        API_LOGIN            = BASE_URL + "launch/login";

        /** 报表 **/
        API_ADV_REPORT       = BASE_URL + "mobile/analysis/launch";

        /** 负责人 **/
        API_LAUNCH_LIST      = BASE_URL + "launch/list";



        /** 广告账户 搜索查询 **/
        API_ADV_ACCOUNT      = BASE_URL + "launch/account/third/filter";

        /** 广告系列 搜索查询 **/
        API_ADV_SERIAL       = BASE_URL + "third/facebook/simple/campaigns";

        /** 广告组 搜索查询 **/
        API_ADV_GROUP        = BASE_URL + "third/facebook/simple/adsets";

        /** 广告 搜索查询 **/
        API_ADV_PLAN         = BASE_URL + "third/facebook/ads";



        /** 广告系列 状态开关和预算 **/
        API_STATE_SERIAL     = BASE_URL + "mobile/update_campaign_status";

        /** 广告组 开关 **/
        API_STATE_GROUP      = BASE_URL + "mobile/update_adset_status";

        /** 广告 开关 **/
        API_STATE_ADV        = BASE_URL + "mobile/update_ad_status";



        /** 添加广告系列任务 **/
        API_TASK_SERIAL     = BASE_URL + "mobile/add_campaign_task";

        /** 添加广告组任务 **/
        API_TASK_GROUP       = BASE_URL + "mobile/add_adset_task";

        /** 添加广告任务 **/
        API_TASK_ADV         = BASE_URL + "mobile/add_ad_task";

        /** 修改任务 **/
        API_TASK_MODIFY      = BASE_URL + "mobile/update_task";

        /** 定时任务列表 **/
        API_TASK_LIST        = BASE_URL + "mobile/get_ad_tasks";
    }


    /**
     * @return 【广告系列、组、计划】创建定时器任务接口
     */
    public static String getCreateTimerApi(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_SERIAL:   return API_TASK_SERIAL;
            case AdvItemLevel.ADV_GROUP:    return API_TASK_GROUP;
            case AdvItemLevel.ADV_PLAN:     return API_TASK_ADV;
            default:                        return "";
        }
    }

    /**
     * @return 【广告系列、组、计划】开关接口
     */
    public static String getModifySwitchApi(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_SERIAL:   return API_STATE_SERIAL;
            case AdvItemLevel.ADV_GROUP:    return API_STATE_GROUP;
            case AdvItemLevel.ADV_PLAN:     return API_STATE_ADV;
            default:                        return "";
        }
    }

    /**
     * @return 【广告系列、广告组】修改预算开关接口
     */
    public static String getModifyBudgetApi(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_SERIAL:   return API_STATE_SERIAL;
            case AdvItemLevel.ADV_GROUP:    return API_STATE_GROUP;
            default:                        return "";
        }
    }


}
