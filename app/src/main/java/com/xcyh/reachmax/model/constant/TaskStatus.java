package com.xcyh.reachmax.model.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 任务状态： 未开始、已结束、取消
 *
 * Created by haojiangfeng on 2024/12/19.
 */
@IntDef({
        TaskStatus.NOT_START,
        TaskStatus.FINISH,
        TaskStatus.CANCEL,
        TaskStatus.ALL
})
@Retention(RetentionPolicy.SOURCE)
public @interface TaskStatus {

    int NOT_START   = 0;
    int FINISH      = 1;
    int CANCEL      = -1;

    int ALL         = Integer.MAX_VALUE;
}
