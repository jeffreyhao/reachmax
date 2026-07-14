package com.xcyh.reachmax.model.custom;


import androidx.annotation.NonNull;

/**
 * 日期范围
 *
 * Created by haojiangfeng on 2025/1/7.
 */
public class DateRange {


    /** 起始日期 **/
    @NonNull
    public String       dateStart;

    /** 结束日期 **/
    @NonNull
    public String       dateEnd;

    /** 日期文字 **/
    @NonNull
    public String       dateRangeText;


    public DateRange(@NonNull String dateStart, @NonNull String dateEnd, @NonNull String dateRangeText) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateRangeText = dateRangeText;
    }


    public boolean isDifferent(DateRange value) {
        if(value == null){
            return true;
        }
        return !dateStart.equals(value.dateStart)
                || !dateEnd.equals(value.dateEnd)
                || !dateRangeText.equals(value.dateRangeText);
    }
}
