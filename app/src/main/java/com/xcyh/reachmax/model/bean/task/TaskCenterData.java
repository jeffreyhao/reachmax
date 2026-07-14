package com.xcyh.reachmax.model.bean.task;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/12/27.
 */
@Keep
public class TaskCenterData implements Serializable {

    private List<TaskBean> task;

    private int count;


    public List<TaskBean> getTask() {
        return task;
    }

    public void setTask(List<TaskBean> task) {
        this.task = task;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
