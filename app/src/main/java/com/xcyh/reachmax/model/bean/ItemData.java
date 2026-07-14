package com.xcyh.reachmax.model.bean;


import com.base.util.collection.ListUtil;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.AdvItemLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/11/15.
 */
@Keep
public class ItemData implements Serializable {

    /*
        {
                "idx": 0,
                "label": "2026-07-02 - 2026-07-02",
                "uniq_id": "42128843023078774212884302307877",
                "date": 0,
                "year": 0,
                "month": 0,
                "week": 0,
                "hour": 0,
                "platform": 2,
                "launch_id": 34,
                "launch_name": "shenying",
                "ad_account": "4212884302307877",
                "ad_account_name": "飞书-ZT-web测试-260123-沈英",
                "ad_account_status": 1,
                "spend": 839.424,
                "recharge_count": 9,
                "recharge_amount": 167.937,
                "new_recharge_count": 0,
                "new_recharge_amount": 139.951,
                "old_recharge_count": 2,
                "old_recharge_amount": 27.986,
                "recharge_roi": 0.2,
                "recharge_per_price": 93.269,
                "install_count": 231,
                "new_install_count": 231,
                "old_install_count": 0,
                "install_per_price": 3.634,
                "user_advertise_revenue": 0,
                "user_advertise_revenue_roi": 0,
                "recharge_and_advertise_revenue_roi": 0.2,
                "first_recharge_count": 5,
                "first_recharge_amount": 76.965,
                "recharge_account_ids": "3568635,3568617,3567704,3565795,3568694",
                "first_recharge_account_ids": "3567704,3565795,3568617,3568635,3568694",
                "new_first_recharge_count": 3,
                "new_first_recharge_amount": 48.979,
                "old_first_recharge_count": 2,
                "old_first_recharge_amount": 27.986,
                "first_recharge_per_price": 167.885,
                "first_recharge_roi": 0.092,
                "recharge_account_count": 5,
                "first_recharge_account_count": 5,
                "clicks": 2036,
                "impressions": 24758,
                "ctr": 8.224,
                "ctm": 33.905,
                "sd_clicks": 268,
                "sd_impressions": 1087,
                "open_land_page_ratio": 53,
                "open_app_ratio": 24,
                "subscribe_account_count": 1,
                "subscribe_account_ids": "3568635",
                "click_per_price": 0.412
            }
     */

    private String label;
    private String uniq_id;
    private int date;
    private int year;
    private int month;
    private int week;
    private int hour;
    private int platform;
    private String launch_id;
    private String launch_name;

    private String ad_account;      // 账户 id
    private String ad_account_name; // 账户名称
    private String campaign_id;     // 系列 id
    private String campaign_name;   // 系列名称
    private String campaign_status; // 系列状态
    private String adset_id;        // 组 id
    private String adset_name;      // 组名称
    private String adset_status;    // 组状态
    private String ad_id;           // 计划 id
    private String ad_name;         // 计划名称
    private String ad_status;       // 计划状态



    private String daily_budget;           // 每日预算
    private double spend;               // 消耗
    private int recharge_count;         // 订单数
    private double recharge_amount;     // 充值金额
    private String recharge_account_ids;
    private int new_recharge_count;
    private double new_recharge_amount;
    private int old_recharge_count;
    private double old_recharge_amount;
    private double recharge_roi;        // roi
    private double recharge_per_price;  // 转化成本
    private int install_count;          // 安装数
    private int new_install_count;
    private int old_install_count;
    private double install_per_price;   // 安装成本
    private int first_recharge_count;
    private double first_recharge_amount;
    private String first_recharge_account_ids;
    private int new_first_recharge_count;
    private double new_first_recharge_amount;
    private int old_first_recharge_count;
    private double old_first_recharge_amount;
    private double first_recharge_per_price;
    private double first_recharge_roi;
    private int recharge_account_count; // 购物人数
    private int first_recharge_account_count;
    private int clicks;                 // 点击
    private int impressions;            // 曝光

    // ===== 以下为接口新增字段 =====
    private int idx;                            // 索引
    private int ad_account_status;              // 账户状态
    private double user_advertise_revenue;      // 广告变现收益
    private double user_advertise_revenue_roi;          // 广告变现收益 ROI
    private double recharge_and_advertise_revenue_roi;  // 充值+广告变现综合 ROI
    private double ctr;                         // 点击率
    private double ctm;                         // 千次点击成本
    private int sd_clicks;                      // SD 点击数
    private int sd_impressions;                 // SD 曝光数
    private int open_land_page_ratio;           // 落地页打开率
    private int open_app_ratio;                 // App 打开率
    private int subscribe_account_count;        // 订阅账号数
    private String subscribe_account_ids;       // 订阅账号 ID
    private double click_per_price;             // 点击成本 (CPC)


    private List<TaskBean> task_list;


    private boolean isChecked = false;

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean check){
        isChecked = check;
    }

    /**
     * @return 点击率
     */
    public String getClickRate() {
        return clicks == 0 || impressions == 0 ? "0%"
                : String.format(Locale.CHINA, "%.2f", 100.00f * clicks / impressions) + "%";
    }

    /**
     * @return 安装成本
     */
    public String getInstallCost(){
        return String.valueOf(install_per_price);
    }

    /**
     * @return 独立用户成本
     */
    public String getUserCost() {
        return getSpend() == 0 || getRecharge_account_count() == 0 ? "0"
                : (String.format(Locale.CHINA, "$ %.3f", 1.000f * getSpend() / getRecharge_account_count()));
    }

    /**
     * @return 购物成本
     */
    public String getRechargeCost(){
        return getSpend() == 0 || getRecharge_count() == 0 ? "0"
                : (String.format(Locale.CHINA, "$ %.3f", 1.000f * getSpend() / getRecharge_count()));
    }


    public String getName(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  return getAd_account_name();
            case AdvItemLevel.ADV_SERIAL:   return getCampaign_name();
            case AdvItemLevel.ADV_GROUP:    return getAdset_name();
            case AdvItemLevel.ADV_PLAN:     return getAd_name();
            default:                        return "";
        }
    }

    public String getId(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  return getAd_account();
            case AdvItemLevel.ADV_SERIAL:   return getCampaign_id();
            case AdvItemLevel.ADV_GROUP:    return getAdset_id();
            case AdvItemLevel.ADV_PLAN:     return getAd_id();
            default:                        return "";
        }
    }

    public void putTimerValueId(HashMap<String, Object> map, @AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_ACCOUNT:  map.put("ad_account", getAd_account());   break;
            case AdvItemLevel.ADV_SERIAL:   map.put("campaign_id", getCampaign_id());   break;
            case AdvItemLevel.ADV_GROUP:    map.put("adset_id", getAdset_id());   break;
            case AdvItemLevel.ADV_PLAN:     map.put("ad_id", getAd_id());   break;
        }
    }

    public String getState(@AdvItemLevel int level){
        switch (level){
            case AdvItemLevel.ADV_SERIAL:   return getCampaign_status();
            case AdvItemLevel.ADV_GROUP:    return getAdset_status();
            case AdvItemLevel.ADV_PLAN:     return getAd_status();
            default:                        return "";
        }
    }

    public void setState(@AdvItemLevel int level, String state){
        switch (level){
            case AdvItemLevel.ADV_SERIAL:
                setCampaign_status(state);
                break;
            case AdvItemLevel.ADV_GROUP:
                setAdset_status(state);
                break;
            case AdvItemLevel.ADV_PLAN:
                setAd_status(state);
                break;
            default:
                break;
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUniq_id() {
        return uniq_id;
    }

    public void setUniq_id(String uniq_id) {
        this.uniq_id = uniq_id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getLaunch_id() {
        return launch_id;
    }

    public void setLaunch_id(String launch_id) {
        this.launch_id = launch_id;
    }

    public String getLaunch_name() {
        return launch_name;
    }

    public void setLaunch_name(String launch_name) {
        this.launch_name = launch_name;
    }

    public String getAd_account() {
        return ad_account;
    }

    public void setAd_account(String ad_account) {
        this.ad_account = ad_account;
    }

    public String getAd_account_name() {
        return ad_account_name;
    }

    public void setAd_account_name(String ad_account_name) {
        this.ad_account_name = ad_account_name;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getCampaign_status() {
        return campaign_status;
    }

    public void setCampaign_status(String campaign_status) {
        this.campaign_status = campaign_status;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_status() {
        return ad_status;
    }

    public void setAd_status(String ad_status) {
        this.ad_status = ad_status;
    }

    public String getAdset_id() {
        return adset_id;
    }

    public void setAdset_id(String adset_id) {
        this.adset_id = adset_id;
    }

    public String getAdset_name() {
        return adset_name;
    }

    public void setAdset_name(String adset_name) {
        this.adset_name = adset_name;
    }

    public String getAdset_status() {
        return adset_status;
    }

    public void setAdset_status(String adset_status) {
        this.adset_status = adset_status;
    }

    public String getDaily_budget() {
        return daily_budget;
    }

    public void setDaily_budget(String daily_budget) {
        this.daily_budget = daily_budget;
    }

    public double getSpend() {
        return spend;
    }

    public void setSpend(double spend) {
        this.spend = spend;
    }

    public int getRecharge_count() {
        return recharge_count;
    }

    public void setRecharge_count(int recharge_count) {
        this.recharge_count = recharge_count;
    }

    public double getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(double recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public String getRecharge_account_ids() {
        return recharge_account_ids;
    }

    public void setRecharge_account_ids(String recharge_account_ids) {
        this.recharge_account_ids = recharge_account_ids;
    }

    public int getNew_recharge_count() {
        return new_recharge_count;
    }

    public void setNew_recharge_count(int new_recharge_count) {
        this.new_recharge_count = new_recharge_count;
    }

    public double getNew_recharge_amount() {
        return new_recharge_amount;
    }

    public void setNew_recharge_amount(double new_recharge_amount) {
        this.new_recharge_amount = new_recharge_amount;
    }

    public int getOld_recharge_count() {
        return old_recharge_count;
    }

    public void setOld_recharge_count(int old_recharge_count) {
        this.old_recharge_count = old_recharge_count;
    }

    public double getOld_recharge_amount() {
        return old_recharge_amount;
    }

    public void setOld_recharge_amount(double old_recharge_amount) {
        this.old_recharge_amount = old_recharge_amount;
    }

    public double getRecharge_roi() {
        return recharge_roi;
    }

    public void setRecharge_roi(double recharge_roi) {
        this.recharge_roi = recharge_roi;
    }

    public double getRecharge_per_price() {
        return recharge_per_price;
    }

    public void setRecharge_per_price(double recharge_per_price) {
        this.recharge_per_price = recharge_per_price;
    }

    public int getInstall_count() {
        return install_count;
    }

    public void setInstall_count(int install_count) {
        this.install_count = install_count;
    }

    public int getNew_install_count() {
        return new_install_count;
    }

    public void setNew_install_count(int new_install_count) {
        this.new_install_count = new_install_count;
    }

    public int getOld_install_count() {
        return old_install_count;
    }

    public void setOld_install_count(int old_install_count) {
        this.old_install_count = old_install_count;
    }

    public double getInstall_per_price() {
        return install_per_price;
    }

    public void setInstall_per_price(double install_per_price) {
        this.install_per_price = install_per_price;
    }

    public int getFirst_recharge_count() {
        return first_recharge_count;
    }

    public void setFirst_recharge_count(int first_recharge_count) {
        this.first_recharge_count = first_recharge_count;
    }

    public double getFirst_recharge_amount() {
        return first_recharge_amount;
    }

    public void setFirst_recharge_amount(double first_recharge_amount) {
        this.first_recharge_amount = first_recharge_amount;
    }

    public String getFirst_recharge_account_ids() {
        return first_recharge_account_ids;
    }

    public void setFirst_recharge_account_ids(String first_recharge_account_ids) {
        this.first_recharge_account_ids = first_recharge_account_ids;
    }

    public int getNew_first_recharge_count() {
        return new_first_recharge_count;
    }

    public void setNew_first_recharge_count(int new_first_recharge_count) {
        this.new_first_recharge_count = new_first_recharge_count;
    }

    public double getNew_first_recharge_amount() {
        return new_first_recharge_amount;
    }

    public void setNew_first_recharge_amount(double new_first_recharge_amount) {
        this.new_first_recharge_amount = new_first_recharge_amount;
    }

    public int getOld_first_recharge_count() {
        return old_first_recharge_count;
    }

    public void setOld_first_recharge_count(int old_first_recharge_count) {
        this.old_first_recharge_count = old_first_recharge_count;
    }

    public double getOld_first_recharge_amount() {
        return old_first_recharge_amount;
    }

    public void setOld_first_recharge_amount(double old_first_recharge_amount) {
        this.old_first_recharge_amount = old_first_recharge_amount;
    }

    public double getFirst_recharge_per_price() {
        return first_recharge_per_price;
    }

    public void setFirst_recharge_per_price(double first_recharge_per_price) {
        this.first_recharge_per_price = first_recharge_per_price;
    }

    public double getFirst_recharge_roi() {
        return first_recharge_roi;
    }

    public void setFirst_recharge_roi(double first_recharge_roi) {
        this.first_recharge_roi = first_recharge_roi;
    }

    public int getRecharge_account_count() {
        return recharge_account_count;
    }

    public void setRecharge_account_count(int recharge_account_count) {
        this.recharge_account_count = recharge_account_count;
    }

    public int getFirst_recharge_account_count() {
        return first_recharge_account_count;
    }

    public void setFirst_recharge_account_count(int first_recharge_account_count) {
        this.first_recharge_account_count = first_recharge_account_count;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getImpressions() {
        return impressions;
    }

    public void setImpressions(int impressions) {
        this.impressions = impressions;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getAd_account_status() {
        return ad_account_status;
    }

    public void setAd_account_status(int ad_account_status) {
        this.ad_account_status = ad_account_status;
    }

    public double getUser_advertise_revenue() {
        return user_advertise_revenue;
    }

    public void setUser_advertise_revenue(double user_advertise_revenue) {
        this.user_advertise_revenue = user_advertise_revenue;
    }

    public double getUser_advertise_revenue_roi() {
        return user_advertise_revenue_roi;
    }

    public void setUser_advertise_revenue_roi(double user_advertise_revenue_roi) {
        this.user_advertise_revenue_roi = user_advertise_revenue_roi;
    }

    public double getRecharge_and_advertise_revenue_roi() {
        return recharge_and_advertise_revenue_roi;
    }

    public void setRecharge_and_advertise_revenue_roi(double recharge_and_advertise_revenue_roi) {
        this.recharge_and_advertise_revenue_roi = recharge_and_advertise_revenue_roi;
    }

    public double getCtr() {
        return ctr;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }

    public double getCtm() {
        return ctm;
    }

    public void setCtm(double ctm) {
        this.ctm = ctm;
    }

    public int getSd_clicks() {
        return sd_clicks;
    }

    public void setSd_clicks(int sd_clicks) {
        this.sd_clicks = sd_clicks;
    }

    public int getSd_impressions() {
        return sd_impressions;
    }

    public void setSd_impressions(int sd_impressions) {
        this.sd_impressions = sd_impressions;
    }

    public int getOpen_land_page_ratio() {
        return open_land_page_ratio;
    }

    public void setOpen_land_page_ratio(int open_land_page_ratio) {
        this.open_land_page_ratio = open_land_page_ratio;
    }

    public int getOpen_app_ratio() {
        return open_app_ratio;
    }

    public void setOpen_app_ratio(int open_app_ratio) {
        this.open_app_ratio = open_app_ratio;
    }

    public int getSubscribe_account_count() {
        return subscribe_account_count;
    }

    public void setSubscribe_account_count(int subscribe_account_count) {
        this.subscribe_account_count = subscribe_account_count;
    }

    public String getSubscribe_account_ids() {
        return subscribe_account_ids;
    }

    public void setSubscribe_account_ids(String subscribe_account_ids) {
        this.subscribe_account_ids = subscribe_account_ids;
    }

    public double getClick_per_price() {
        return click_per_price;
    }

    public void setClick_per_price(double click_per_price) {
        this.click_per_price = click_per_price;
    }

    public List<TaskBean> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<TaskBean> task_list) {
        this.task_list = task_list;
    }



    public TaskBean getSwitchTask() {
        if(ListUtil.isEmpty(task_list)){
            return null;
        }
        for(TaskBean task : task_list){
            if(!(AdvActionState.CHANGE_DAILY_BUDGET.equals(task.getAction()))){
                return task;
            }
        }
        return null;
    }

    public TaskBean getBudgetTask() {
        if(ListUtil.isEmpty(task_list)){
            return null;
        }
        for(TaskBean task : task_list){
            if(AdvActionState.CHANGE_DAILY_BUDGET.equals(task.getAction())){
                return task;
            }
        }
        return null;
    }


    public void updateTask(TaskBean task) {
        if(task == null || task.getAction() == null){
            return;
        }
        if(task_list == null) {
            task_list = new ArrayList<>();
        }
        if(task_list.size() == 0){
            task_list.add(task);
            return;
        }
        TaskBean target = null;
        for(TaskBean bean : task_list){
            if(bean.getAction() != null && task.getAction().equals(bean.getAction())){
                target = bean;
                break;
            }
        }
        if(target != null){
            task_list.remove(target);
            task_list.add(task);
        }
    }


    @Override
    public String toString() {
        return "Item数据：\n" +
                "【索引】    " + idx + "\n" +
                "【账户状态】  " + ad_account_status + "\n" +
                "【消耗】    " + spend + "\n" +
                "【点击数】  " + clicks + "\n" +
                "【曝光次数】 " + impressions + "\n" +
                "【安装数量】 " + install_count + "\n" +
                "【新安装数量】" + new_install_count + "\n" +
                "【旧安装数量】" + old_install_count + "\n" +
                "【安装单价】  " + install_per_price + "\n" +
                "【充值数】   " + recharge_count + "\n" +
                "【充值金额】  " + recharge_amount + "\n" +
                "【新充值数】  " + new_recharge_count + "\n" +
                "【新充值金额】" + new_recharge_amount + "\n" +
                "【旧充值数】  " + old_recharge_count + "\n" +
                "【旧充值金额】" + old_recharge_amount + "\n" +
                "【充值ROI】  " + recharge_roi + "\n" +
                "【充值单价】 " + recharge_per_price + "\n" +
                "【首充数量】 " + first_recharge_count + "\n" +
                "【首充金额】 " + first_recharge_amount + "\n" +
                "【新首充数量】" + new_first_recharge_count + "\n" +
                "【新首充金额】" + new_first_recharge_amount + "\n" +
                "【旧首充数量】" + old_first_recharge_count + "\n" +
                "【旧首充金额】" + old_first_recharge_amount + "\n" +
                "【首充单价】  " + first_recharge_per_price + "\n" +
                "【首充ROI】  " + first_recharge_roi + "\n" +
                "【充值账号数量】 " + recharge_account_count + "\n" +
                "【首充账号数量】 " + first_recharge_account_count + "\n" +
                "【点击率】   " + ctr + "\n" +
                "【千次点击成本】 " + ctm + "\n" +
                "【SD点击数】 " + sd_clicks + "\n" +
                "【SD曝光数】 " + sd_impressions + "\n" +
                "【落地页打开率】 " + open_land_page_ratio + "\n" +
                "【App打开率】 " + open_app_ratio + "\n" +
                "【订阅账号数】 " + subscribe_account_count + "\n" +
                "【订阅账号ID】 " + subscribe_account_ids + "\n" +
                "【点击成本】  " + click_per_price + "\n" +
                "【广告变现收益】 " + user_advertise_revenue + "\n" +
                "【广告变现ROI】 " + user_advertise_revenue_roi + "\n" +
                "【（充值+广告）综合ROI】 " + recharge_and_advertise_revenue_roi + "\n"
                ;
    }

    public String getUserIdsText(){
        StringBuilder sb = new StringBuilder();
        sb.append("充值用户（").append(recharge_account_count).append("个）：\n");
        sb.append(recharge_account_ids).append("\n");
        sb.append("首充用户（").append(first_recharge_account_count).append("个）：\n");
        sb.append(first_recharge_account_ids);
        return sb.toString();
    }

}
