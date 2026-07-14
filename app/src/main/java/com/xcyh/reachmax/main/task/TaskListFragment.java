package com.xcyh.reachmax.main.task;

import android.os.Bundle;
import android.view.View;

import com.baidu.baselibrary.base.fragment.BaseListFragment;
import com.baidu.baselibrary.recycler.BindingRecyclerAdapter;
import com.baidu.baselibrary.recycler.OnItemChildClickListener;
import com.xcyh.reachmax.databinding.ItemTaskBinding;
import com.xcyh.reachmax.adv.AdvGotoHelper;
import com.xcyh.reachmax.app.callback.ConfirmListener;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.ClickAction;
import com.xcyh.reachmax.model.constant.TaskStatus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by haojiangfeng on 2024/11/14.
 */
public class TaskListFragment extends BaseListFragment<TaskBean, TaskListPresenter> implements ITaskListView, OnItemChildClickListener<ItemTaskBinding, TaskBean> {



    public static TaskListFragment getInstance(int status) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }


    private TaskListAdapter mAdapter;

    private @TaskStatus int mStatus;



    @Override
    protected void intPresenter() {
        super.intPresenter();
        mPresenter.setTaskListView(this);
    }

    @NonNull
    @Override
    public RecyclerView.Adapter<?> createAdapter() {
        mAdapter = new TaskListAdapter();
        mAdapter.setOnItemChildClickListener(this);
        return mAdapter;
    }

    @Override
    public void initData() {
        mStatus = getArguments().getInt("status");
        super.initData();
    }

    @Override
    public void getData() {
        mPresenter.getData(getPage(), mStatus);
    }


//    @Override
//    public void onRequestSuccess(@NonNull List<? extends TaskBean> list) {
//        super.onRequestSuccess(list);
//    }

    @Override
    public void onGetDataSuccess(List<TaskBean> t) {
        List<TaskBean> list = (List<TaskBean>) t;
        int page = getPage();
        if(page == 1) {
            mAdapter.setData(list);
        } else {
            mAdapter.addData(list);
        }
    }


    @Override
    public void onItemChildClick(BindingRecyclerAdapter.BindingViewHolder<ItemTaskBinding, TaskBean> holder, View view, int action, TaskBean data, int position) {
        if(action != ClickAction.ITEM_TASK_CANCEL){
            return;
        }
        AdvGotoHelper.showTimerModifyDialog(getActivity(), "确定要取消任务吗？", new ConfirmListener() {
            @Override
            public void onClickConfirm() {
                mPresenter.cancelTask(data);
            }
        });
    }

    @Override
    public void onTaskCanceled(TaskBean taskBean) {
        List<TaskBean> list = mAdapter.getData();
        if(list == null || list.size() == 0){
            return;
        }
        for(int i = 0; i < list.size(); i++){
            TaskBean task = list.get(i);
            if(task.getId() == taskBean.getId()){
                // 全刷还是局部刷？还是局刷！
                task.update(taskBean);
                mAdapter.notifyItemChanged(i);
//                clearAndRequest();
                break;
            }
        }
    }

    public void clearAndRequest(){
        mAdapter.clear();
        if(getList() != null){
            getList().clear();
        }
        setPage(1);
        getData();
    }


}
