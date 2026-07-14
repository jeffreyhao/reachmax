package com.xcyh.reachmax.adv.pages;

import com.baidu.baselibrary.util.date.DateUtil;
import com.xcyh.reachmax.adv.home.callback.IAdvParamsController;
import com.xcyh.reachmax.adv.home.callback.OnParamsChangeListener;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.AdvRankRule;
import com.xcyh.reachmax.model.constant.AdvStateFilter;
import com.xcyh.reachmax.model.custom.DateRange;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * 过滤器中的参数。
 *
 *      包含：
 *          排序、广告状态、负责人
 *
 * Created by haojiangfeng on 2024/12/23.
 */
public class AdvParamsController implements IAdvParamsController {



    protected @AdvItemLevel int     mAdvLevel;


    protected OnParamsChangeListener mOnParamsChangeListener;



    /** 排序规则 **/
    @NonNull
    protected @AdvRankRule String   mRankRule;


    /** 本tab页面初始化的筛选状态列表 **/
    protected List<AdvStateFilter>  mInitFilterList;

    /** 当前选中的筛选状态 **/
    protected AdvStateFilter        mSelectFilter;

    /** 日期范围 **/
    protected DateRange             mDateRange;

    /**
     * 是否需要通知参数变化
     */
    protected boolean               mNeedNotifyParamsChanged;



    public AdvParamsController(int level, @NonNull List<AdvStateFilter> filters, OnParamsChangeListener listener){
        this.mAdvLevel = level;
        this.mOnParamsChangeListener = listener;
        this.mNeedNotifyParamsChanged = true;

        mRankRule = AdvRankRule.DEFAULT;
        mSelectFilter = AdvStateFilter.ALL;
        mInitFilterList = filters;

        String today = DateUtil.getDateYMD();
        mDateRange = new DateRange(today, today, "今天");
    }

    @AdvItemLevel
    @Override
    public int getLevel() {
        return mAdvLevel;
    }

    public List<AdvStateFilter> getInitFilterList(){
        return mInitFilterList;
    }

    @Override
    public AdvStateFilter getSelectFilter(){
        return mSelectFilter;
    }



    @Override
    public void setSelectFilter(AdvStateFilter filter) {
        if(mSelectFilter != filter){
            this.mSelectFilter = filter;
            onParamsChanged();
        }
    }

    @Override
    public void setSelectRankType(@AdvRankRule String rankRule) {
        if(!mRankRule.equals(rankRule)){
            mRankRule = rankRule;
            onParamsChanged();
        }
    }

    @Override
    public DateRange getDateRange() {
        return mDateRange;
    }

    @Override
    public void setDateRange(DateRange dateRange) {
        if(mDateRange.isDifferent(dateRange)){
            mDateRange = dateRange;
            onParamsChanged();
        }
    }

    @Override
    public String getRankType() {
        return AdvRankRule.DEFAULT.equals(mRankRule) ? "" : "spend";
    }

    @Override
    public String getRankRule() {
        return mRankRule;
    }

    @Override
    public String getNextRankRule() {
        switch (mRankRule){
            default:
            case AdvRankRule.ASC:       return AdvRankRule.DEFAULT;
            case AdvRankRule.DESC:      return AdvRankRule.ASC;
            case AdvRankRule.DEFAULT:   return AdvRankRule.DESC;
        }
    }


    protected void onParamsChanged(){
        if(mNeedNotifyParamsChanged && mOnParamsChangeListener != null){
            mOnParamsChangeListener.onParamsChanged();
        }
    }


    /**
     * 设置是否可以触发参数变化回调。缺省为true。
     */
    public void setNeedNotifyParamsChanged(boolean needNotify){
        this.mNeedNotifyParamsChanged = needNotify;
    }

}
