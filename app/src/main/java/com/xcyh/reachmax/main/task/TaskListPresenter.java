package com.xcyh.reachmax.main.task;

import com.baidu.baselibrary.base.IBaseView;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.base.net.bean.ApiException;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.bean.task.TaskBody;
import com.xcyh.reachmax.model.bean.task.TaskCenter;
import com.xcyh.reachmax.model.constant.TaskStatus;
import com.xcyh.reachmax.model.request.Presenter;
import com.xcyh.reachmax.model.request.RequestCallback;
import com.xcyh.reachmax.model.request.Url;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by haojiangfeng on 2024/11/14.
 */
public class TaskListPresenter extends Presenter<IBaseView<List<TaskBean>>> {


    private int mPageSize = 10;
    private ITaskListView mTaskListView;



    public void setTaskListView(ITaskListView view) {
        mTaskListView = view;
    }

    /**
     * 请求任务中心列表数据
     */
    public void getData(int page, int status){
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("page", page);
        paramMap.put("pageSize", mPageSize);
        if(status != TaskStatus.ALL){
            paramMap.put("status", status);
        }

        RequestCallback<TaskCenter> requestCallback = new RequestCallback<TaskCenter>() {

            @Override
            public void onSuccess(String content, TaskCenter taskCenter) {
                if(taskCenter != null && taskCenter.getData() != null){
                    mView.onRequestSuccess(taskCenter.getData().getTask());
                } else {
                    mView.onRequestFail(new ApiException(-1, "解析错误"));
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.onRequestFail(new ApiException(e, -1));

                // TODO: 2024/11/15
//                onSuccess("", Test.getTaskCenter());
            }
        };
        get(Url.API_TASK_LIST, paramMap, page == 1, requestCallback);
    }


    /**
     * 取消任务
     */
    public void cancelTask(TaskBean taskBean) {
        LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("name", taskBean.getName());
        paramMap.put("task_id", taskBean.getId());
        paramMap.put("status", TaskStatus.CANCEL);
        paramMap.put("time_zone", taskBean.getTime_zone());
        paramMap.put("start_time", taskBean.getStart_time());
        paramMap.put("action", taskBean.getAction());

        RequestCallback<TaskBody> requestCallback = new RequestCallback<TaskBody>(){

            @Override
            public void onSuccess(String content, TaskBody pageBody) {
                if(pageBody == null){
                    ToastUtils.showToastCenterLong("定时任务取消失败");
                } else {
                    ToastUtils.showToastCenterLong("定时任务取消成功");
                    mTaskListView.onTaskCanceled(pageBody.getTask());
                }
            }

            @Override
            public void onFail(ApiException e) {
                ToastUtils.showToastCenterLong("定时任务取消失败: \n" + e.getMsg());
            }
        };
        get(Url.API_TASK_MODIFY, paramMap, false, requestCallback);
    }



}
