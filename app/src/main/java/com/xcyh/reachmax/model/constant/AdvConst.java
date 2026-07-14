package com.xcyh.reachmax.model.constant;

import com.base.util.AppUtil;

/**
 * Created by haojiangfeng on 2024/12/18.
 */
public class AdvConst {


    public static final String TXT_SPLIT       = " — ";


    public static final String PERMISSION_BROADCAST    =  AppUtil.getPackageName() +".BROADCAST";



    public static String getLevelText(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  return "广告账户";
            case AdvItemLevel.ADV_SERIAL:   return "广告系列";
            case AdvItemLevel.ADV_GROUP:    return "广告组";
            case AdvItemLevel.ADV_PLAN:     return "广告计划";
            default:                        return "";
        }
    }


    public static int getNextTabPosition(int level){
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:      return 1;
            case AdvItemLevel.ADV_SERIAL:       return 2;
            case AdvItemLevel.ADV_GROUP:        return 3;
        }
        return 4;
    }
}
