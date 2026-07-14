package com.xcyh.reachmax.adv.home.callback;

import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.AdvRankRule;
import com.xcyh.reachmax.model.constant.AdvStateFilter;
import com.xcyh.reachmax.model.custom.DateRange;

import java.util.List;

/**
 * Created by haojiangfeng on 2024/12/23.
 */
public interface IAdvParamsController  {


    @AdvItemLevel int getLevel();

    /**
     * @return 筛选项列表
     */
    List<AdvStateFilter> getInitFilterList();

    /**
     * 设置筛选项
     * @param filter
     */
    void setSelectFilter(AdvStateFilter filter);

    /**
     * @return 获取当前的筛选项
     */
    AdvStateFilter getSelectFilter();


    void setSelectRankType(@AdvRankRule String rankRule);

    /**
     * @return 排序类型： spend、""
     */
    String getRankType();

    /**
     * @return 当前排序规则
     */
    @AdvRankRule String getRankRule();

    /**
     * @return 下一条排序规则
     */
    @AdvRankRule String getNextRankRule();

    DateRange getDateRange();
    void setDateRange(DateRange dateRange);

}
