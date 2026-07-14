package com.xcyh.reachmax.model.bean.task;

import java.io.Serializable;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/12/19.
 */
@Keep
public class TaskCenter implements Serializable {

    private com.xcyh.reachmax.model.bean.task.TaskCenterData data;


    public com.xcyh.reachmax.model.bean.task.TaskCenterData getData() {
        return data;
    }

    public void setData(com.xcyh.reachmax.model.bean.task.TaskCenterData data) {
        this.data = data;
    }


}
