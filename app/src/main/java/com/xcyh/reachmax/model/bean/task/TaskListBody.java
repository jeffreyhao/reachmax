package com.xcyh.reachmax.model.bean.task;

import java.util.List;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2025/3/28.
 */
@Keep
public class TaskListBody {

    private List<TaskBean> task_list;

    public List<TaskBean> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<TaskBean> task_list) {
        this.task_list = task_list;
    }
}
