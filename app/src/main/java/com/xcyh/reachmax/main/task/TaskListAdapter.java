package com.xcyh.reachmax.main.task;

import android.view.View;
import android.view.ViewGroup;

import com.baidu.baselibrary.recycler.BindingRecyclerAdapter;
import com.baidu.baselibrary.util.date.DateUtil;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.ItemTaskBinding;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.ClickAction;
import com.xcyh.reachmax.model.constant.TaskStatus;

/**
 * Created by haojiangfeng on 2024/11/14.
 */
public class TaskListAdapter extends BindingRecyclerAdapter<ItemTaskBinding, TaskBean> {


    public TaskListAdapter() {
        super(R.layout.item_task);
    }

    @Override
    protected void handleView(BindingViewHolder<ItemTaskBinding, TaskBean> holder, TaskBean data, int position) {
        ItemTaskBinding binding = holder.getViewDataBinding();
        setTop(binding, position);
        setData(binding, data);

        binding.btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performChildClick(holder, v, ClickAction.ITEM_TASK_CANCEL, data, position);
            }
        });
    }

    private void setTop(ItemTaskBinding binding, int position){
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.getRoot().getLayoutParams();
        if(position == 0){
            params.topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp_15);
            binding.itemTaskDivider.setVisibility(View.GONE);
        } else {
            params.topMargin = 0;
            binding.itemTaskDivider.setVisibility(View.VISIBLE);
        }
    }

    private void setData(ItemTaskBinding binding, TaskBean data) {
        binding.tvTaskName.setText(data.getName());
        binding.tvAction.setText(data.getActionString());
        // 2024-12-26T11:17:00+08:00    ->     2024-12-26 11:17:00
        binding.tvTimeSetting.setText(DateUtil.parseFromISO8061(data.getStart_time(), DateUtil.YMD_T_HMSs, DateUtil.formatYMDHMS));

        switch (data.getStatus()){
            case TaskStatus.NOT_START:
                binding.tvState.setText("未开始");
                binding.tvState.setBackgroundResource(R.drawable.bg_task_state_unplayed);
                binding.btnCancelTask.setVisibility(View.VISIBLE);
                break;
            case TaskStatus.FINISH:
                binding.tvState.setText("已完成");
                binding.tvState.setBackgroundResource(R.drawable.bg_task_state_finish);
                binding.btnCancelTask.setVisibility(View.GONE);
                break;
            case TaskStatus.CANCEL:
                binding.tvState.setText("已取消");
                binding.tvState.setBackgroundResource(R.drawable.bg_task_state_canceled);
                binding.btnCancelTask.setVisibility(View.GONE);
                break;
        }
    }


}
