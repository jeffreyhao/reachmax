package com.xcyh.reachmax.adv.timer;

import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.baselibrary.base.fragment.BaseDialogFragment;
import com.baidu.baselibrary.request.EmptyPresenter;
import com.baidu.baselibrary.util.date.DateUtil;
import com.baidu.baselibrary.util.ui.ToastUtils;
import com.xcyh.reachmax.app.meta.dialog.base.BaseDialog;
import com.xcyh.reachmax.R;
import com.xcyh.reachmax.databinding.FragmentBudgetTimerBinding;
import com.xcyh.reachmax.app.callback.SubmitListener;
import com.xcyh.reachmax.model.bean.ItemData;
import com.xcyh.reachmax.model.bean.task.TaskBean;
import com.xcyh.reachmax.model.constant.AdvActionState;
import com.xcyh.reachmax.model.constant.ClickAction;
import com.xcyh.reachmax.model.constant.TaskStatus;

import java.util.Date;
import java.util.Locale;

import androidx.fragment.app.Fragment;


/**
 * 创建定时预算任务
 */
public class AdvTimerBudgetFragment extends BaseDialogFragment<FragmentBudgetTimerBinding, EmptyPresenter> {



    public static AdvTimerBudgetFragment newInstance(int level, ItemData data, SubmitListener listener) {
        AdvTimerBudgetFragment chapterSheetDialogFragment = new AdvTimerBudgetFragment();
        chapterSheetDialogFragment.mSubmitListener = listener;
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);
        bundle.putSerializable("itemData", data);
        chapterSheetDialogFragment.setArguments(bundle);
        return chapterSheetDialogFragment;
    }



    private ItemData mItemData;
    private TaskBean mTask;
    private SubmitListener mSubmitListener;

    private long mSelectTimeStamp;
    private String mSelectStartTime;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget_timer;
    }

    @Override
    protected int getAnimations() {
        return R.style.bottomDialog;
    }

    @Override
    protected void initTitleBar() {

    }

    @Override
    public void initListener() {
        mBinding.tvTimerSetting.setOnClickListener(v->{
            selectTimer();
        });
        mBinding.btnCancel.setOnClickListener(v->{
            doSubmit(ClickAction.ITEM_TASK_CANCEL, TaskStatus.CANCEL);
        });
        mBinding.btnSubmit.setOnClickListener(v->{
            doSubmit(mTask == null ? ClickAction.ITEM_TASK_CREATE : ClickAction.ITEM_TASK_MODIFY, TaskStatus.NOT_START);
        });
        mBinding.ivClose.setOnClickListener(v->{
            closeDialog();
        });
    }

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

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        int level = bundle.getInt("level");
        mItemData = (ItemData) bundle.getSerializable("itemData");
        if(mItemData == null){
            ToastUtils.showToastCenter("itemData 不能为null");
            dismiss();
        }

        mTask = mItemData.getBudgetTask();
        if(mTask == null){
            mBinding.btnSubmit.setText("确认创建");
            mBinding.btnCancel.setEnabled(false);
            mSelectTimeStamp = DateUtil.getTomorrowStartTimestamp();
            mSelectStartTime = DateUtil.getDateYMDHMS(mSelectTimeStamp);
            mBinding.etTimerBudget.setText(String.valueOf(mItemData.getDaily_budget()));
        } else {
            mBinding.btnSubmit.setText("确认修改");
            mBinding.btnCancel.setEnabled(true);
            Date actionDate = DateUtil.parseDateFromISO8061(mTask.getStart_time(), mTask.getTime_zone());
            mSelectTimeStamp = actionDate.getTime();
            mSelectStartTime = DateUtil.getFormatDate(actionDate, DateUtil.formatYMDHMS, Locale.CHINESE);
            mBinding.etTimerBudget.setText(String.valueOf(mTask.getDaily_budget()));
        }

        mBinding.tvTimerSetting.setText(mSelectStartTime);
        mBinding.tvTaskName.setText(mItemData.getName(level));
    }



    private void closeDialog() {
        if (null == getActivity()) {
            return;
        }
        getActivity().runOnUiThread(this::dismiss);
    }

    private void doSubmit(@ClickAction int action, @TaskStatus int status){
        int budget = -1;
        if(action != ClickAction.ITEM_TASK_CANCEL){
            String budgetStr = mBinding.etTimerBudget.getText().toString();
            if(TextUtils.isEmpty(budgetStr)){
                ToastUtils.showToastCenter("请输入预算数字");
                return;
            }
            if(budgetStr.equals("0")){
                ToastUtils.showToastCenter("预算不能为0");
                return;
            }

            if(TextUtils.isEmpty(budgetStr)){
                ToastUtils.showToastCenter("预算不能为0");
                return;
            }
            budget = Integer.parseInt(budgetStr);
            if(budget == -1){
                ToastUtils.showToastCenter("请输入正确的预算数字");
                return;
            }
        }
        if(mSubmitListener != null){
            Bundle bundle = new Bundle();
            bundle.putInt("status", status);
            bundle.putSerializable("itemData", mItemData);
            bundle.putString("action",  AdvActionState.CHANGE_DAILY_BUDGET);
            bundle.putLong("timestamp", mSelectTimeStamp);
            bundle.putString("startTime", mSelectStartTime);
            bundle.putInt("budget", budget);
            bundle.putInt("taskId", mTask == null ? -1 : mTask.getId());
            mSubmitListener.onClickSubmit(action, bundle);
        }
        closeDialog();
    }


    private void selectTimer() {
        if(getActivity() == null){
            return;
        }
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("timer_select");
        if (fragment instanceof BaseDialog) {
            return;
        }
        AdvTimerDateSelectFragment timerDateSelectFragment = AdvTimerDateSelectFragment.newInstance(mSelectTimeStamp, new SubmitListener() {
            @Override
            public void onClickSubmit(int action, Bundle bundle) {
                mSelectTimeStamp = bundle.getLong("timestamp", 0);
                mSelectStartTime = bundle.getString("startTime", "");
                mBinding.tvTimerSetting.setText(mSelectStartTime);
            }
        });
        timerDateSelectFragment.show(getActivity().getSupportFragmentManager(), "timer_select");
    }

    @Override
    protected String className() {
        return getClass().getSimpleName();
    }
}