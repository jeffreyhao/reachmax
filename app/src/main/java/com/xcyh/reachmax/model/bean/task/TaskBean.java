package com.xcyh.reachmax.model.bean.task;

import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.TaskStatus;

import java.io.Serializable;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/11/14.
 */
@Keep
public class TaskBean implements Serializable {

    /**
     * id : 8
     * launch_id : 28
     * campaign_id : 120214036313320093
     * adset_id :
     * type :
     * action : ACTIVE
     * name : 广告系列定时任务
     * status : 0
     * start_time : 2024-12-12T00:00:00+08:00
     * time_zone : Asia/Shanghai
     * create_time : 2024-12-12T19:42:44+08:00
     * update_time : 2024-12-12T19:42:44+08:00
     */

    private int id;
    private int launch_id;
    private int status;
    private String type;
    private String action;
    private String name;


    private String campaign_id;
//    private String campaign_name;   // 系列名称
    private String adset_id;
//    private String adset_name;      // 组名称
    private String ad_id;
//    private String ad_name;         // 计划名称



    private String start_time;
    private String time_zone;
    private String create_time;
    private String update_time;


    private int daily_budget;



    public String getActionString() {
        if(action == null){
            return "";
        }
        switch (action){
            case AdvActionState.CHANGE_DAILY_BUDGET:
                return "修改预算";
            case AdvActionState.ACTIVE:
                return "开启投放";
            default:
            case AdvActionState.PAUSED:
                return "关闭投放";
        }
    }

    public void update(TaskBean task){
        if(task == null || task.id != id){
            return;
        }
        launch_id = task.getLaunch_id();
        status = task.getStatus();
        type = task.getType();
        action = task.getAction();
        name = task.getName();
        campaign_id = task.getCampaign_id();
        adset_id = task.getAdset_id();
        ad_id = task.getAd_id();
        start_time = task.getStart_time();
        time_zone = task.getTime_zone();
        create_time = task.getCreate_time();
        update_time = task.getUpdate_time();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLaunch_id() {
        return launch_id;
    }

    public void setLaunch_id(int launch_id) {
        this.launch_id = launch_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

//    public String getCampaign_name() {
//        return campaign_name;
//    }
//
//    public void setCampaign_name(String campaign_name) {
//        this.campaign_name = campaign_name;
//    }
//
//    public String getAdset_name() {
//        return adset_name;
//    }
//
//    public void setAdset_name(String adset_name) {
//        this.adset_name = adset_name;
//    }

    public String getAdset_id() {
        return adset_id;
    }

    public void setAdset_id(String adset_id) {
        this.adset_id = adset_id;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

//    public String getAd_name() {
//        return ad_name;
//    }
//
//    public void setAd_name(String ad_name) {
//        this.ad_name = ad_name;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @TaskStatus
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String  getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getDaily_budget() {
        return daily_budget;
    }

    public void setDaily_budget(int daily_budget) {
        this.daily_budget = daily_budget;
    }
}