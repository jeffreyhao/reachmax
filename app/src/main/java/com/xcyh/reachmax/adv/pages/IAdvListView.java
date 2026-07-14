package com.xcyh.reachmax.adv.pages;


import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.AdvItemLevel;

import java.util.List;

/**
 * Created by haojiangfeng on 2024/12/16.
 */
public interface IAdvListView extends IAdvTotalAmountView {

    /**
     * tab页的纬度level
     */
    @AdvItemLevel int getLevel();

    /**
     * 修改定时任务成功
     */
    void onTimerModifySuccess(int level, ItemData itemData, List<TaskBean> taskList, @AdvActionState String action, String startTime);

    /**
     * 创建定时预算任务成功
     */
    void onBudgetTimerSuccess(ItemData itemData, List<TaskBean> taskList, String startTime);

    /**
     * 修改item开关 或者 广告系列预算
     */
    void onActionStateUpdated(int level, ItemData itemData, String status, String budget);

    /**
     * 修改item开关 失败（因为ui状态已切换，如果修改失败，这里需要将状态重刷）
     */
    void onActionStateModifyFail(int level, ItemData itemData);


}
