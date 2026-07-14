package com.xcyh.reachmax.model.constant;


/**
 * 过滤类型
 *
 * Created by haojiangfeng on 2024/11/22.
 */
public enum AdvStateFilter {

    ALL                 (0, "",     "全部", true),
    ACCOUNT_NORMAL      (1, "",     "正常", true),
    ACCOUNT_ABNORMAL    (2, "",     "异常", false),
    ADV_PUTTING         (3, "ACTIVE",   "投放中", true),
    ADV_PAUSED          (4, "PAUSED",   "暂停", true),
    ADV_DELETED         (5, "CLOSE",    "删除", true);



    public int intValue;
    public String stringValue;
    public String text;
    public boolean enable;

    AdvStateFilter(int intValue, String stringValue, String text, boolean enable){
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.text = text;
        this.enable = enable;
    }

    public static AdvStateFilter get(int intValue){
        switch (intValue){
            default:
            case 0: return ALL;
            case 1: return ACCOUNT_NORMAL;
            case 2: return ACCOUNT_ABNORMAL;
            case 3: return ADV_PUTTING;
            case 4: return ADV_PAUSED;
            case 5: return ADV_DELETED;
        }
    }

}

