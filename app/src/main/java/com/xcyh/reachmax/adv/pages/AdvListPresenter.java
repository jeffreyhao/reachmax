package com.xcyh.reachmax.adv.pages;


import android.text.TextUtils;

import com.baidu.baselibrary.base.IBaseListView;
import com.baidu.baselibrary.util.sys.LogUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.base.net.bean.ApiException;
import com.base.net.webhook.DingDingSender;
import com.xcyh.reachmax.model.bean.AdvPageBody;
import com.xcyh.reachmax.model.bean.AdvStateModifyBody;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.task.TaskListBody;
import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.AdvGroupField;
import com.xcyh.reachmax.model.constant.AdvItemLevel;
import com.xcyh.reachmax.model.constant.TaskStatus;
import com.xcyh.reachmax.model.custom.DateRange;
import com.xcyh.reachmax.model.event.GlobalAdvParams;
import com.xcyh.reachmax.model.manager.Pitcher;
import com.xcyh.reachmax.model.request.Presenter;
import com.xcyh.reachmax.model.request.RequestCallback;
import com.xcyh.reachmax.model.request.Url;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by haojiangfeng on 2024/11/12.
 */
public abstract class AdvListPresenter extends Presenter<IBaseListView<List<ItemData>>> {


    protected @AdvGroupField String groupField = AdvGroupField.ACCOUNT_ID;

    protected int mLimitPageSize = 10;
    protected int mListTotal;

    protected IAdvListView iView;


    public void init(IAdvListView iView, int level) {
        this.iView = iView;
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  groupField = AdvGroupField.ACCOUNT_ID;  break;
            case AdvItemLevel.ADV_SERIAL:   groupField = AdvGroupField.CAMPAIGN_ID; break;
            case AdvItemLevel.ADV_GROUP:    groupField = AdvGroupField.ADSET_ID;    break;
            case AdvItemLevel.ADV_PLAN:     groupField = AdvGroupField.AD_ID;       break;
        }
    }


    /**
     *  创建开关定时器
     */
    public void createSwitchTimer(int level, ItemData itemData, @AdvActionState String action, String startTime) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        itemData.putTimerValueId(paramMap, level);
        paramMap.put("name", itemData.getName(level));
        paramMap.put("start_time", startTime);
        paramMap.put("time_zone", "Asia/Shanghai");
        paramMap.put("action", action);

        RequestCallback<TaskListBody> requestCallback = new RequestCallback<TaskListBody>(){

            @Override
            public void onSuccess(String content, TaskListBody taskBody) {
                if(taskBody == null){
                    ToastUtils.showToastCenterLong("定时任务创建失败");
                } else {
                    ToastUtils.showToastCenterLong("定时任务创建成功");
                    iView.onTimerModifySuccess(level, itemData, taskBody.getTask_list(), action, startTime);
                }
            }

            @Override
            public void onFail(ApiException e) {
                toastAndMonitor("定时任务创建失败\n" + e.getMsg(), "AdvListPresenter.createSwitchTimer");
            }
        };
        get(Url.getCreateTimerApi(level), paramMap, false, requestCallback);
    }

    /**
     * 创建广告系列预算定时器
     */
    public void createBudgetTimer(int level, ItemData itemData, String startTime, int budget){
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        itemData.putTimerValueId(paramMap, level);
        paramMap.put("name", itemData.getName(level));
        paramMap.put("start_time", startTime);
        paramMap.put("time_zone", "Asia/Shanghai");
        paramMap.put("action", AdvActionState.CHANGE_DAILY_BUDGET);
        paramMap.put("daily_budget", budget);

        RequestCallback<TaskListBody> requestCallback = new RequestCallback<TaskListBody>(){

            @Override
            public void onSuccess(String content, TaskListBody taskBody) {
                if(taskBody == null){
                    ToastUtils.showToastCenterLong("定时预算创建失败");
                } else {
                    ToastUtils.showToastCenterLong("定时预算创建成功");
                    iView.onBudgetTimerSuccess(itemData, taskBody.getTask_list(), startTime);
                }
            }

            @Override
            public void onFail(ApiException e) {
                toastAndMonitor("定时预算创建失败\n" + e.getMsg(), "AdvListPresenter.createBudgetTimer");
            }
        };
        get(Url.getCreateTimerApi(level), paramMap, false, requestCallback);
    }

    /**
     *  修改或取消 定时器
     */
    public void modifyTimer(int level, ItemData itemData, int taskId, @TaskStatus int status, @AdvActionState String action, String startTime, int budget) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("name", itemData.getName(level));
        paramMap.put("task_id", taskId);
        paramMap.put("time_zone", "Asia/Shanghai");
        paramMap.put("status", status);
        paramMap.put("start_time", startTime);
        paramMap.put("action", action);
        if(AdvActionState.CHANGE_DAILY_BUDGET.equals(action)){
            paramMap.put("daily_budget", budget);
        }

        RequestCallback<TaskListBody> requestCallback = new RequestCallback<TaskListBody>(){

            @Override
            public void onSuccess(String content, TaskListBody taskBody) {
                if(taskBody == null){
                    ToastUtils.showToastCenterLong("定时任务修改失败");
                } else {
                    ToastUtils.showToastCenterLong("定时任务修改成功");
                    iView.onTimerModifySuccess(level, itemData, taskBody.getTask_list(), action, startTime);
                }
            }

            @Override
            public void onFail(ApiException e) {
                toastAndMonitor("定时任务修改失败\n" + e.getMsg(), "AdvListPresenter.modifyTimer");
            }
        };
        get(Url.API_TASK_MODIFY, paramMap, false, requestCallback);
    }

    /**
     * [广告系列、广告组、广告计划] 开关
     *
     * @param itemData  item数据
     * @param status    {@link AdvActionState}
     */
    public void modifySwitch (int level, ItemData itemData, @AdvActionState String status) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("id", itemData.getId(level));
        paramMap.put("status", status);

        RequestCallback<AdvStateModifyBody> requestCallback = new RequestCallback<AdvStateModifyBody>(){

            @Override
            public void onSuccess(String content, AdvStateModifyBody object) {
                if(object != null){
                    ToastUtils.showToastCenterLong(status.equals(AdvActionState.ACTIVE) ? "已开启投放" : "已关闭投放");
                    iView.onActionStateUpdated(level, itemData, status, itemData.getDaily_budget());
                } else {
                    ToastUtils.showToastCenterLong(status.equals(AdvActionState.ACTIVE) ? "开启投放失败" : "关闭投放失败");
                    iView.onActionStateModifyFail(level, itemData);
                }
            }

            @Override
            public void onFail(ApiException e) {
                toastAndMonitor((status.equals(AdvActionState.ACTIVE) ? "开启投放失败\n" : "关闭投放失败\n") + e.getMsg(), "AdvListPresenter.modifySwitch");
                iView.onActionStateModifyFail(level, itemData);
            }
        };
        get(Url.getModifySwitchApi(level), paramMap, false, requestCallback);
    }

    /**
     * 修改预算 [广告系列、广告组]
     *
     * @param itemData  item数据
     * @param status    {@link AdvActionState}
     * @param budget    预算
     */
    public void modifyBudget(int level, ItemData itemData, String status, String budget) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("id", itemData.getId(level));
        paramMap.put("status", status);
        paramMap.put("daily_budget", budget);

        RequestCallback<AdvStateModifyBody> requestCallback = new RequestCallback<AdvStateModifyBody>(){

            @Override
            public void onSuccess(String content, AdvStateModifyBody body) {
                if(body != null && body.isSuccess()){
                    ToastUtils.showToastCenterLong("已修改预算");
                    iView.onActionStateUpdated(level, itemData, status, budget);
                } else {
                    ToastUtils.showToastCenterLong("修改预算失败");
                }
            }

            @Override
            public void onFail(ApiException e) {
                toastAndMonitor("修改预算失败\n" + e.getMsg(), "AdvListPresenter.modifyBudget");
            }
        };
        get(Url.getModifyBudgetApi(level), paramMap, false, requestCallback);
    }

    /**
     * 获取报表列表
     */
    public void getAdvReport(int level, boolean showLoading, int page, AdvParamsController filterController, AdvSearchController searchController){
        DateRange dateRange = filterController.getDateRange();
        String launchIds = GlobalAdvParams.getLaunchIds();

        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:
                getAdvReport(showLoading, page, mLimitPageSize, dateRange.dateStart, dateRange.dateEnd,  groupField, "amount", searchController.getSearchValues(), filterController.getSelectFilter().stringValue, "", "", "", "", "", "", filterController.getRankType(), filterController.getRankRule(), launchIds, "");
                break;
            case AdvItemLevel.ADV_SERIAL:
                getAdvReport(showLoading, page, mLimitPageSize, dateRange.dateStart, dateRange.dateEnd,  groupField, "amount", GlobalAdvParams.getAccounts(), "", searchController.getSearchValues(), filterController.getSelectFilter().stringValue, "", "", "", "", filterController.getRankType(), filterController.getRankRule(), launchIds, "");
                break;
            case AdvItemLevel.ADV_GROUP:
                getAdvReport(showLoading, page, mLimitPageSize, dateRange.dateStart, dateRange.dateEnd,  groupField, "amount", GlobalAdvParams.getAccounts(), "", GlobalAdvParams.getSerials(), "", searchController.getSearchValues(), filterController.getSelectFilter().stringValue, "", "", filterController.getRankType(), filterController.getRankRule(), launchIds, "");
                break;
            case AdvItemLevel.ADV_PLAN:
                getAdvReport(showLoading, page, mLimitPageSize, dateRange.dateStart, dateRange.dateEnd,  groupField, "amount", GlobalAdvParams.getAccounts(), "", GlobalAdvParams.getSerials(), "", GlobalAdvParams.getGroups(), "", searchController.getSearchValues(), filterController.getSelectFilter().stringValue, filterController.getRankType(), filterController.getRankRule(), launchIds, "");
                break;
        }
    }

    /**
     *  获取报表列表（接口文档定义的全部参数都在这）
     *
     * @param page              页码
     * @param pageSize          每页请求数
     * @param start             日期开始时间  2024-12-02
     * @param end               日期结束时间  2024-12-03
     * @param group_field        分组类型  enum: ad_id|adset_id|campaign_id|launch_ad_account_id
     * @param group             统计规则  enum: amount|day|week|month|
     *
     * @param ad_account        指定广告账号id
     * @param ad_account_status 指定广告账号状态
     * @param campaign_id       指定广告系列 id eg: 120213933195170617
     * @param campaign_status   广告系列状态: ACTIVE|CLOSE
     * @param adset_id          指定广告集合 id
     * @param adset_status      指定广告组状态
     * @param ad_id             指定广告 id eg:120213933195180617
     * @param ad_status         指定广告状态
     *
     * @param order_field        排序字段  消耗:spend
     * @param order             排序规则 asc|desc
     *
     * @param launch_id         负责人 id。
     * @param book_id           指定书籍 id。
     */
    private void getAdvReport(boolean showLoading,
                              int page,
                              int pageSize,
                              String start,
                              String end,
                              @AdvGroupField String group_field,
                              String group,
                              String ad_account,
                              String ad_account_status,
                              String campaign_id,
                              String campaign_status,
                              String adset_id,
                              String adset_status,
                              String ad_id,
                              String ad_status,
                              String order_field,
                              String order,
                              String launch_id,
                              String book_id
    ){
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("page", page);
        paramMap.put("pageSize", pageSize);
        paramMap.put("start", start);
        paramMap.put("end", end);
        if(!TextUtils.isEmpty(group_field)){
            paramMap.put("group_field", group_field);
        }
        if(!TextUtils.isEmpty(group)){
            paramMap.put("group", group);
        }
        if(!TextUtils.isEmpty(ad_account)){
            paramMap.put("ad_account", ad_account);
        }
        if(!TextUtils.isEmpty(ad_account_status)){
            paramMap.put("ad_account_status", ad_account_status);
        }
        if(!TextUtils.isEmpty(campaign_id)){
            paramMap.put("campaign_id", campaign_id);
        }
        if(!TextUtils.isEmpty(campaign_status)){
            paramMap.put("campaign_status", campaign_status);
        }
        if(!TextUtils.isEmpty(adset_status)){
            paramMap.put("adset_status", adset_status);
        }
        if(!TextUtils.isEmpty(adset_id)){
            paramMap.put("adset_id", adset_id);
        }
        if(!TextUtils.isEmpty(ad_id)){
            paramMap.put("ad_id", ad_id);
        }
        if(!TextUtils.isEmpty(ad_status)){
            paramMap.put("ad_status", ad_status);
        }
        if(!TextUtils.isEmpty(order_field)){
            paramMap.put("order_field", order_field);
        }
        if(!TextUtils.isEmpty(order)){
            paramMap.put("order", order);
        }
        if(!TextUtils.isEmpty(launch_id)){
            paramMap.put("launch_id", launch_id);
        }
        if(!TextUtils.isEmpty(book_id)){
            paramMap.put("book_id", book_id);
        }
        paramMap.put("deduct_channel_fee", "true"); // 是否扣除渠道费：true

        RequestCallback<AdvPageBody> requestCallback = new RequestCallback<AdvPageBody>(){

            @Override
            public void onSuccess(String content, AdvPageBody pageBody) {
                if(pageBody == null){
                    mView.onRequestFail(new ApiException(0, "请求失败"));
                } else {
                    mListTotal = pageBody.getTotal();
                    LogUtil.d("adv", "report.onSuccess\n        total: " + mListTotal + ", page: " + page);

                    List<ItemData> list = pageBody.getData();
                    mView.onRequestSuccess(list);
                    iView.setAmountView(list == null || list.isEmpty(), pageBody.getAmount(), pageBody.getNone_id_recharge_amount());

                    // 更新时间
                    if(pageBody.getLast_update() != null){
                        GlobalAdvParams.sUpdateTime.postValue(pageBody.getLast_update());
                    }
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.onRequestFail(new ApiException(0, "请求失败"));
            }
        };
        get(Url.API_ADV_REPORT, paramMap, showLoading, requestCallback);
    }



    private void toastAndMonitor(String text, String position){
        ToastUtils.showToastCenterLong(text);
        DingDingSender.monitorReachMaxWarning(text, position, Pitcher.getInstance().getUserName());
    }


}
