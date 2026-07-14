package com.xcyh.reachmax.model.bean.task;

import androidx.annotation.Keep;

/**
 * Created by haojiangfeng on 2024/12/21.
 */
@Keep
public class TaskBody {


    /**
     * task : {"id":9,"launch_id":28,"ad_id":"","campaign_id":"120216080725800013","adset_id":"","type":"","action":"PAUSED","name":"广告组定时任务","status":-1,"start_time":"2024-12-16T17:23:00+08:00","time_zone":"Asia/Shanghai","create_time":"2024-12-16T15:13:44+08:00","update_time":"2024-12-21T11:48:00+08:00"}
     */

    private TaskBean task;

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }


}
